@echo off
rem
rem This batch file preverifies the code.
rem

if "%OS%" == "Windows_NT" setlocal

echo *** Preverifying class files ***

set LIBS=libs\cldcapi11.jar;libs\midpapi20.jar;libs\jsr75.jar;libs\jsr234.jar;libs\mmapi.jar;libs\nokia-api20.jar

preverify.exe -classpath %LIBS% -d out\production\ViKaTouch out\production\ViKaTouch

echo *** Preverifying complete ***

:end
if "%OS%" == "Windows_NT" endlocal

rem do a "pause" always
pause
