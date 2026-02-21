@REM ----------------------------------------------------------------------------
@REM Maven Wrapper
@REM ----------------------------------------------------------------------------

@echo off
title %0
if "%HOME%" == "" (set "HOME=%HOMEDRIVE%%HOMEPATH%")

@setlocal
set ERROR_CODE=0

if not "%JAVA_HOME%" == "" goto OkJHome
echo.
echo ERRO: JAVA_HOME nao definido. Configure a variavel JAVA_HOME com o caminho do JDK.
echo.
goto error

:OkJHome
if not exist "%JAVA_HOME%\bin\java.exe" (
  echo ERRO: JAVA_HOME invalido - %JAVA_HOME%
  goto error
)

set EXEC_DIR=%CD%
set WDIR=%EXEC_DIR%
:findBaseDir
IF EXIST "%WDIR%"\.mvn goto baseDirFound
cd ..
IF "%WDIR%"=="%CD%" goto baseDirNotFound
set WDIR=%CD%
goto findBaseDir

:baseDirFound
set MAVEN_PROJECTBASEDIR=%WDIR%
cd "%EXEC_DIR%"
goto endDetectBaseDir

:baseDirNotFound
set MAVEN_PROJECTBASEDIR=%EXEC_DIR%
cd "%EXEC_DIR%"

:endDetectBaseDir

SET MAVEN_JAVA_EXE="%JAVA_HOME%\bin\java.exe"
set WRAPPER_JAR="%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar"
set WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain
set WRAPPER_URL="https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.3.0/maven-wrapper-3.3.0.jar"

FOR /F "usebackq tokens=1,2 delims==" %%A IN ("%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.properties") DO (
  IF "%%A"=="wrapperUrl" SET WRAPPER_URL=%%B
)

if exist %WRAPPER_JAR% goto run
echo Baixando Maven Wrapper...
powershell -NoProfile -Command "[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; (New-Object Net.WebClient).DownloadFile('%WRAPPER_URL%', '%WRAPPER_JAR%')"
if not exist %WRAPPER_JAR% (
  echo Falha ao baixar maven-wrapper.jar
  goto error
)

:run
set MAVEN_CMD_LINE_ARGS=%*
%MAVEN_JAVA_EXE% -classpath %WRAPPER_JAR% "-Dmaven.multiModuleProjectDirectory=%MAVEN_PROJECTBASEDIR%" %WRAPPER_LAUNCHER% %*
if ERRORLEVEL 1 goto error
goto end

:error
set ERROR_CODE=1

:end
@endlocal
exit /B %ERROR_CODE%
