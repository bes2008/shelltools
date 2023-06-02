<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId></groupId>
    <artifactId></artifactId>
    <packaging></packaging>
    <version></version>
    <name></name>

    <properties>

    </properties>

    <dependencies>
        <dependency>
            <groupId>io.github.bes2008.solution.easyjson</groupId>
            <artifactId>easyjson-jackson</artifactId>
            <version>${easyjson.version}</version>
            <exclusions>
                <exclusion>
                    <groupId>io.github.bes2008.solution.langx</groupId>
                    <artifactId>langx-java</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
    </dependencies>

</project>