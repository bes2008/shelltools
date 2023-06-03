<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>${groupId}</groupId>
    <artifactId>${artifactId}</artifactId>
    <packaging>${packaging?lower_case}</packaging>
    <version>${version}</version>
    <name>${groupId}:${artifactId}:${version}</name>

    <properties>
<#if properties??>
    <#list properties?keys as property>
        <${property}>${properties[property]}</${property}>
    </#list>
</#if>
    </properties>

    <dependencies>
<#if dependencies??>
    <#list dependencies as dependency>
        <dependency>
            <groupId>${dependency.groupId}</groupId>
            <artifactId>${dependency.artifactId}</artifactId>
            <version>${dependency.version}</version>
        <#if dependency.optional>
            <optional>true</optional>
        </#if>
        <#if dependency.packaging?lower_case != "jar">
            <optional>${dependency.packaging?lower_case}</optional>
        </#if>
    <#if dependency.excludes??>
        <#list dependency.excludes as exclusion>
            <exclusions>
                <exclusion>
                    <groupId>${exclusion.groupId!''}</groupId>
                    <artifactId>${exclusion.artifactId!''}</artifactId>
                </exclusion>
            </exclusions>
        </#list>
    </#if>
        </dependency>
    </#list>
</#if>
    </dependencies>
</project>