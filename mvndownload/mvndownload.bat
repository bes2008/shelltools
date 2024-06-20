call setEnv.bat
REM call mvndownloadsource.bat
start "sources downloading" cmd /k call mvndownloadsource.bat
REM start "javadoc downloading" cmd /k call mvndownloaddocs.bat
REM mvn clean package --settings %MAVEN_REPO_DOWNLOAD% -T 3
mvn clean package --settings %MAVEN_REPO_DOWNLOAD% -T 3
pause