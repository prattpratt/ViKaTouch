package vikatouch.screens.temp;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Enumeration;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

import ru.nnproject.vikaui.menu.items.PressableUIItem;
import ru.nnproject.vikaui.screen.ScrollableCanvas;
import ru.nnproject.vikaui.utils.ColorUtils;
import ru.nnproject.vikaui.utils.DisplayUtils;
import ru.nnproject.vikaui.utils.images.IconsManager;
import vikatouch.VikaTouch;
import vikatouch.items.fm.FileItem;
import vikatouch.items.fm.FileManagerItem;
import vikatouch.items.fm.FolderItem;
import vikatouch.locale.TextLocal;
import vikatouch.screens.ChatScreen;
import vikatouch.utils.VikaUtils;
import vikatouch.utils.emulatordetect.EmulatorDetector;

public class FileManagerScreen extends ScrollableCanvas {
	
	private ChatScreen chat;
	private FileManagerItem selectedItem;
	private String folder;
	private boolean root;
	private FileConnection fileconn;

	public FileManagerScreen(ChatScreen chat) {
		this.chat = chat;
		load();
		currentItem = 0;
	}
	
	public void load() {
		root();
	}
	
	public void press(int x, int y) {
		super.press(x, y);
	}
	
	public void release(int x, int y) {
		if(y > DisplayUtils.height - 36) {
			if(x < 36) {
				back();
			} else if(x > DisplayUtils.width - 36) {
				if(selectedItem != null && selectedItem.isImage()) {
					selectedItem.keyPressed(-6);
				}
			} else if(x > (DisplayUtils.width - 36) / 2 && x < (DisplayUtils.width - 36) / 2 + 18) {
				if(selectedItem != null && selectedItem.isImage()) {
					send(selectedItem);
				}
			}
		}
		try {
			if (y > 24 && y < DisplayUtils.height - 24) {
				int h = uiItems[0].getDrawHeight();
				int yy1 = y - (scrolled + 24);
				int i = yy1 / h;
				if (i < 0)
					i = 0;
				if (!dragging) {
					uiItems[i].tap(x, yy1 - (h * i));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		super.release(x, y);
	}
	
	public void press(int key) {
		if(key == -6) {
			if(uiItems[currentItem] != null)
				uiItems[currentItem].keyPressed(-6);
		} else if(key == -7) {
			back();
		} else if(key == -5) {
			if(uiItems[currentItem] != null)
				uiItems[currentItem].keyPressed(-5);
		} else {
			super.press(key);
		}
	}

	private void back() {
		if(root) {
			this.invalidate();
			VikaTouch.setDisplay(chat, -1);
		} else {
			root();
		}
	}

	private void invalidate() {
		if(fileconn != null) {
			try {
				fileconn.close();
			} catch (IOException e) {
			}
			fileconn = null;
		}
		uiItems = null;
	}

	public void draw(Graphics g) {
		itemsh = itemsCount * (DisplayUtils.compact ? 24 : 50);
		ColorUtils.setcolor(g, ColorUtils.BACKGROUND);
		g.fillRect(0, 0, DisplayUtils.width, DisplayUtils.height);
		update(g);
		ColorUtils.setcolor(g, ColorUtils.TEXT);
		g.setFont(Font.getFont(0, 0, 8));
		int y = 24;
		try {
			for(int i = 0; i < itemsCount; i++) {
				if(uiItems[i] != null) {
					uiItems[i].paint(g, y, scrolled);
					y += uiItems[i].getDrawHeight();
				}
			}
		} catch (Exception e) {
			
		}
		g.translate(0, -g.getTranslateY());
		ColorUtils.setcolor(g, ColorUtils.BACKGROUND);
		g.fillRect(0, 0, DisplayUtils.width, 24);
		ColorUtils.setcolor(g, ColorUtils.TEXT);
		g.drawString(folder, 2, 0, 0);
		if(keysMode) {
			ColorUtils.setcolor(g, ColorUtils.BACKGROUND);
			g.fillRect(0, DisplayUtils.height - 24, DisplayUtils.width, 24);
			g.setColor(0x505050);
			g.drawString(TextLocal.inst.get("back"), DisplayUtils.width - 2 - g.getFont().stringWidth(TextLocal.inst.get("back")), DisplayUtils.height - 18, 0);
			if(uiItems[currentItem] != null && ((FileManagerItem) uiItems[currentItem]).isImage()) {
				g.drawString(TextLocal.inst.get("msg.send"), (DisplayUtils.width - g.getFont().stringWidth(TextLocal.inst.get("msg.send"))) / 2, DisplayUtils.height - 18, 0);
				g.drawString(TextLocal.inst.get("count.view"), 2, DisplayUtils.height - 18, 0);
			} else if(uiItems[currentItem] != null && ((FileManagerItem) uiItems[currentItem]).isDirectory()) {
				g.drawString(TextLocal.inst.get("select"), 0, DisplayUtils.height - 18, 0);
			}
		} else {
			ColorUtils.setcolor(g, ColorUtils.BACKGROUND);
			g.fillRect(0, DisplayUtils.height - 24, DisplayUtils.width, 24);
			g.drawImage(IconsManager.selIco[IconsManager.BACK], 2, DisplayUtils.height - 24, 0);
			if(selectedItem != null && selectedItem.isImage()) {
				g.drawImage(IconsManager.selIco[IconsManager.APPLY], (DisplayUtils.width / 2) - 12, DisplayUtils.height - 24, 0);
				g.drawImage(IconsManager.selIco[IconsManager.PHOTOS], DisplayUtils.width - 26, DisplayUtils.height - 24, 0);
			} else {
				g.drawImage(IconsManager.ico[IconsManager.APPLY], (DisplayUtils.width / 2) - 12, DisplayUtils.height - 24, 0);
				g.drawImage(IconsManager.ico[IconsManager.PHOTOS], DisplayUtils.width - 26, DisplayUtils.height - 24, 0);
			}
		}
	}
	
	private void send(byte[] data) throws IOException, InterruptedException {
		VikaUtils.sendPhoto(chat.peerId, data, chat.inputText);
		chat.inputText = "";
		VikaTouch.setDisplay(chat, -1);
	}

	protected void scrollHorizontally(int deltaX) {

	}

	public void send(FileManagerItem fileManagerItem) {
		String path = fileManagerItem.getPath();
		if(!path.startsWith("file://")) {
			path = "file://" + path;
		}
		FileConnection fileconn;
		try {
			fileconn = (FileConnection) Connector.open(path);
			DataInputStream dis;
			dis = fileconn.openDataInputStream();
			byte[] bytes = new byte[(int) fileconn.fileSize()];
			dis.readFully(bytes, 0, (int) fileconn.fileSize());
			dis.close();
			send(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void selected(FileManagerItem fileManagerItem) {
		if(selectedItem != null)
			selectedItem.setSelected(false);
		selectedItem = fileManagerItem;
		selectedItem.setSelected(true);
	}

	public void root() {
		selectedItem = null;
		root = true;
		folder = "main";
		boolean hasgallery = true;
		boolean hascard = true;
		boolean hasc = true;
		boolean hasroot = true;
		boolean hasdiske = false;
		String gallery = System.getProperty("fileconn.dir.photos");
		String memcard = System.getProperty("fileconn.dir.memorycard");
		String c = "file:///c:/";
		String root = "file:///";
		String e = "file:///e:/";
		if(memcard == null || memcard.length() == 0) {
			memcard = "file:///e:/";
			hascard = true;
		}
		if(memcard.indexOf("e:") == -1) {
			hasdiske = true;
		}
		if(gallery == null || gallery.length() == 0 || gallery.equalsIgnoreCase(memcard)) {
			hasgallery = false;
		}
		int len = 0;
		if(hasc) {
			len++;
		}
		if(hascard) {
			len++;
		}
		if(hasgallery) {
			len++;
		}
		if(hasdiske) {
			len++;
		}
		if(hasroot) {
			len++;
		}
		uiItems = new PressableUIItem[len];
		currentItem = 0;
		for(int i = 0; i < len; i++) {
			if(hasc) {
				uiItems[i] = new FolderItem(this, c, "C:");
				hasc = false;
				continue;
			}
			if(hasdiske) {
				uiItems[i] = new FolderItem(this, e, "E:");
				hasdiske = false;
				continue;
			}
			if(hascard) {
				uiItems[i] = new FolderItem(this, memcard, "Memory card");
				hascard = false;
				continue;
			}
			if(hasgallery) {
				uiItems[i] = new FolderItem(this, gallery, "Gallery");
				hasgallery = false;
				continue;
			}
			if(hasroot) {
				uiItems[i] = new FolderItem(this, root, "Root folder");
				hasroot = false;
				continue;
			}
		}
		itemsCount = (short) len;

		if(keysMode && uiItems[0] != null)
			uiItems[0].setSelected(true);
	}

	public void openFolder(String path) {
		selectedItem = null;
		uiItems = new PressableUIItem[30];
		currentItem = 0;
		boolean createNew = false;
		root = false;
		if(path.startsWith("file://"))
			path = path.substring("file://".length());

		if(folder == null)
			createNew = true;
		else if(path.indexOf(folder) == -1)
			createNew = true;
		String parent = null;
		try {
			parent = VikaUtils.replace(path, folder, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(folder.indexOf(path) != -1) {
			parent = "../";
			createNew = false;
		}
		folder = path;
		System.out.println("set "+path+ " parent:" + parent+".");
		boolean s40 = VikaTouch.isS40() && !EmulatorDetector.isEmulator;
		try {
			try {
				if(fileconn == null || createNew || parent == null) {
					System.out.println(path);
					fileconn = (FileConnection) Connector.open("file://" + path);
				} else {
					System.out.println("not new "+parent);
					fileconn.setFileConnection(parent);
				}
			} catch (IllegalArgumentException e) {
				System.out.println(path);
				fileconn = (FileConnection) Connector.open("file://" + path);
			}
			Enumeration var3 = fileconn.list("*", true);
			int i = 0;
			for (; var3.hasMoreElements(); i++) {
				String var4 = (String) var3.nextElement();
				long var5;
				FileConnection fc = null;
				if(var4.endsWith("/")) {
					uiItems[i] = new FolderItem(this, path + var4, var4.substring(0, var4.length() - 1));
				} else if(s40) {
					uiItems[i] = new FileItem(this, path + var4, var4, 0);
				} else if ((fc = (FileConnection) Connector.open("file://" + path + var4)).isDirectory()) {
					uiItems[i] = new FolderItem(this, path + var4, var4.substring(0, var4.length() - 1));
				} else {
					var5 = fc.fileSize();
					uiItems[i] = new FileItem(this, path + var4, var4, (int)var5);
				}
				if(fc != null) {
					fc.close();
				}
			}
			itemsCount = (short) i;
		} catch (Exception e) {
			uiItems[0] = new FolderItem(this, path, e.toString());
			e.printStackTrace();
		}
		if(keysMode && uiItems[0] != null)
			uiItems[0].setSelected(true);
	}

	public void scrollToSelected() {
		
	}

	public void selectCentered() {
		
	}

	protected void keysScroll(int dir) {
		
	}

}