call setEnv.bat
call mvndownloadsource.bat
REM start "sources downloading" cmd /k call mvndownloadsource.bat
start "javadoc downloading" cmd /k call mvndownloaddocs.bat
mvn clean package --settings %MAVEN_REPO_DOWNLOAD%
pause