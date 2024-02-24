# Vika Touch
Vika Touch это клиент соц. сети "ВКонтакте" для смартфонов с сенсорным экраном и простых "звонилок" на Java ME платформе.

## Setup

1. Install [Oracle JDK 1.8 32-bit (x86)](https://www.oracle.com/cis/java/technologies/downloads/#java8).
2. Install [Sun Java Wireless Toolkit (WTK) 2.5.2](https://www.oracle.com/java/technologies/java-archive-downloads-javame-downloads.html).
3. Install [IntelliJ IDEA](https://www.jetbrains.com/idea/download/?section=windows).

## Build & Run

### IntelliJ IDEA

1. Open the project folder in IDEA.
2. Compile sources with javac (`Build => Build/Rebuild Project`).
3. Run [preverify.bat](preverify.bat) to perform [J2ME class file preverification](https://docs.oracle.com/javame/dev-tools/jme-sdk-3.0-win/html-helpset/z400001e1296162.html).
4. Package classes in a JAR file (`Build => Build Artifacts…`).
5. Artifacts are located in `out/artifacts` folder.
6. Generate JAD file using [JADMaker](JADMaker/JADMaker.exe) or manually.
7. Run the app in WTK emulator clicking JAD file.

### Sun WTK

1. Open the project folder in WTK.
2. Compile sources with javac (`Project => Build`)
3. Run the app in WTK emulator (`Project => Run`).

## Debug

### WTK + IDEA

1. Open the project folder in Sun WTK.
2. Run the app in debug mode (`Project => Debug`).
3. Open the project folder in IDEA.
4. Create `Remote JVM Debug` configuration (`Run => Edit Configurations…`).
5. Use the same remote debugging port in both WTK and IDEA.
6. Run the created configuration (`Run => Debug…`).


# Список дел
<s>Глав. меню</s><br/>
<s>Новости</s><br/>
<s>Список диалогов</s><br/>
<s>Чат</s><br/>
<s>Анимация загрузки</s><br/>
<s>Профили</s><br/>
<s>Список документов</s><br/>
<s>Список друзей</s><br/>
<s>Список групп</s><br/>
<s>Страницы групп</s><br/>
<s>Настройки</s><br/>
<s>Возможность ответить на сообщение</s><br/>
<s>Круглые аватарки</s><br/>
<s>Анимации переходов</s><br/>
<s>Предпросмотр фото</s><br/>
<s>Список видео</s><br/>
<s>Нормальные значки</s><br/>
<s>Возможность проигрывать видео</s><br/>
<s>Ссылки в сообщениях</s><br/>
<s>Кинетическая прокрутка</s><br/>
<s>Вложения в посте</s><br/>
<s>Репосты в новостях</s><br/>
<s>Вложения в сообщениях</s><br/>
<s>Локализация</s><br/>
<s>Возможность начать диалог c группой или с человеком в профиле</s><br/>
<s>Стена пользователя</s><br/>
<s>Стена группы</s><br/>
<s>Несколько фотографий в посте</s><br/>
<s>Полная поддержка клавиш</s><br/>
Пересылка сообщений<br/>
<s>Музыкальный плеер</s><br/>
<s>Удобная прокрутка на кнопках</s><br/>
<s>Форматы времени для других стран</s><br/>
<s>Подгрузка элементов по мере прокрутки</s><br/>
<s>Больше переводов на разные языки (как минимум 2)</s><br/>
Возможность импортировать языки из памяти телефона<br/>
<s>Моя музыка</s><br/>
Мои фото<br/>
<s>Уведомления с вибрацией</s><br/>
<s>Встроенный файл-менеджер и возможность загрузки фото</s> и документов<br/>
<s>Оптимизация под S40/SE</s><br/>
<s>Редактирование сообщений</s><br/>
<s>Запись и отправка голосовых сообщений</s><br/>
Поиск друзей<br/>
<s>Поиск музыки</s><br/>
Поиск пабликов<br/>
<s>Подсвечивание упоминаний</s><br/>
<s>Репост в беседе</s><br/>
<s>Действия в беседе (баны, приглашения)</s><br/>
<s>Мгновенная отправка фотографии (Встроенная камера)</s><br/>
<br/>
# Исправить
<s>"Error 25. Token confirmation required" в музыке</s><br/>
<s>Багучий файл менеджер</s><br/>
<br/>

# Разработчики
@Shinovon (vk.com/shinovon) Арман Джусупгалиев (Автор)<br/>
@Feodor0090 (vk.com/feodor0090) Фёдор Рыжов<br/>
@Azreale1997 (vk.com/yrii.belov1997) Юрий Белов<br/>
@Computershik73 (vk.com/okabe2011) Илья Высоцкий<br/>
