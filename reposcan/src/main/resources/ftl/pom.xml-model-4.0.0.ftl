<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>${groupId}</groupId>
    <artifactId>${artifactId}</artifactId>
<#if packaging??>
    <packaging>${packaging?lower_case}</packaging>
<#else >
    <packaging>jar</packaging>
</#if>

    <version>${version}</version>
    <name>${groupId}:${artifactId}:${version}</name>

    <properties>
<#if properties??>
    <#list properties?keys as property>
        <${property}>${properties[property]}</${property}>
    </#list>
</#if>
    </properties>
<#import "pom.xml-model.4.0.0-dependencies.ftl" as deps>
<#if dependencyManagement?? && dependencyManagement?size gt 0 && dependencyManagement.dependencies?? && dependencyManagement.dependencies?size gt 0>
    <dependencyManagement>
        <@deps.printDependencies dependencyManagement.dependencies/>
    </dependencyManagement>
</#if>
    <@deps.printDependencies dependencies/>
</project>