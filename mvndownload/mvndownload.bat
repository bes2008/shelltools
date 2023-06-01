call setEnv.bat
REM call mvndownloadsource.bat
start "sources downloading" cmd /k call mvndownloadsource.bat
REM start "javadoc downloading" cmd /k call mvndownloaddocs.bat
mvn clean package --settings %MAVEN_REPO_DOWNLOAD%
pause