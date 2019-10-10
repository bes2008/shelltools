call setEnv.bat
call mvndownloadsource.bat
start "javadoc downloading" cmd /k call mvndownloaddocs.bat
mvn clean package -s %MAVEN_REPO_DOWNLOAD%
pause