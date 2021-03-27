call setEnv.bat
echo settings:  %MAVEN_REPO_DOWNLOAD%
mvn dependency:sources -Dclassifier=sources -T 1 --settings %MAVEN_REPO_DOWNLOAD%