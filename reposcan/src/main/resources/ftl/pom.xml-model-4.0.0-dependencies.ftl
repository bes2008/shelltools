<#macro printDependencies dependencies>
<#if dependencies??>
    <dependencies>
    <#list dependencies as dependency>
        <dependency>
            <groupId>${dependency.groupId}</groupId>
            <artifactId>${dependency.artifactId}</artifactId>
            <version>${dependency.version}</version>
            <#if dependency.optional>
                <optional>true</optional>
            </#if>
            <#if dependency.type?lower_case != "jar">
                <optional>${dependency.type?lower_case}</optional>
            </#if>
            <#if dependency.excludes??>
                <#list dependency.exclusions as exclusion>
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
    </dependencies>
</#if>
</#macro>