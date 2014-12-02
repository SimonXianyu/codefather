<#macro out_java_type typename>
    <#assign typeMap = {'string':'String','text':'String','int':'Integer','long':'Long',
    'number':'Long','double':'Double',
    'boolean':'Boolean','date':'Date'} />
    <#t /><#attempt>${typeMap[typename?lower_case]}<#recover >Failed to find type for "${typename}"</#attempt>
</#macro>