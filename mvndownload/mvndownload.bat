call setEnv.bat
REM call mvndownloadsource.bat
start "sources downloading" cmd /k call mvndownloadsource.bat
REM start "javadoc downloading" cmd /k call mvndownloaddocs.bat
REM mvn clean package --settings %MAVEN_REPO_DOWNLOAD% -T 3
mvn clean package --settings %MAVEN_REPO_DOWNLOAD% -T 1

REM start "sources downloading" cmd /k call mvndownloadsource.bat

timeouts /t 60000 > nul
pause