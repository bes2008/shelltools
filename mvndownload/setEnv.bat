@echo on
echo set maven settings: 
REM set MAVEN_REPO_DOWNLOAD="D:\Program Files\apache\apache-maven-3.5.2\conf\settings-download-test.xml"
set MAVEN_REPO_DOWNLOAD="%~dp0/maven-settings.xml"
echo %MAVEN_REPO_DOWNLOAD%