call setEnv.bat
mvn dependency:sources -Dclassifier=javadoc -T 3 -s %MAVEN_REPO_DOWNLOAD%