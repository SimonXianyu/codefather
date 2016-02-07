<#list entityList as entity>
  <#list entity.propertyList as prop>
${entity.javaPackage}.${entity.name}.${prop.name}=<@n2a prop.text! />
  </#list>

</#list>