Code Father简要文档
===================

Code-Father 是我按照 模板跟着项目走，entity的field的属性可以动态增加的思路设计的maven 代码生成插件。

*目录*

*  [项目中配置和文件路径](#config)
	*  [Maven中配置](#conf_maven)
	*  [相关文件路径](#conf_path)
	*  [生成的文件说明](#gen_files)
*  [entity定义文件说明](#entity_files)
	*  [entity](#def_entity)
	*  [property](#def_key)
	*  [property](#def_property)
	
	
<a id="config"/>
##项目中配置和文件路径

<a id="conf_maven"/>
###maven配置

在pom.xml中添加如下内容即可：

<pre>
&lt;build&gt;
	&lt;plugins&gt;
		&lt;plugin&gt;
			&lt;groupId&gt;person.xianyu.haiping.dev&lt;/groupId&gt;
				&lt;artifactId&gt;code-father-plugin&lt;/artifactId&gt;
				&lt;version&gt;0.0.3-SNAPSHOT&lt;/version&gt;
				&lt;executions&gt;
					&lt;execution&gt;
					&lt;id&gt;generate&lt;/id&gt;
					&lt;phase&gt;validate&lt;/phase&gt;
					&lt;goals&gt;
						&lt;goal&gt;code-generate&lt;/goal&gt;
					&lt;/goals&gt;
				&lt;/execution&gt;
			&lt;/executions&gt;
		&lt;/plugin&gt;
	&lt;/plugins&gt;
&lt;/build&gt;
</pre>   

validate Phase在compile之前，这样保障了代码生成在编译之前。

<a id="conf_path"/>
###相关文件路径

默认在 src/main/codefather下，有如下的目录结构

 |- global.xml 一些全局定义   
 |- code-father-config.properties  全局的参数定义   
 |----entities   数据结构定义XML文件存放的位置   
 |----templates   模板文件目录，模板使用的是freemarker格式的   
 		|------ context   基于全部entity生成文件的模板   
 		|------ single    为entity单独生成文件的模板   

<a id="gen_files" />
###生成的文件说明

生成的文件的文件名和位置 按模板同名的properties中的定义。

<a id="entity_files" />
##entity定义文件说明
 
entity 定义文件使用的是xml 格式。默认使用xml的文件名作为Entity的类名和表名(表名可以在xml中用table属性重新定义)。   
 
**标签层次如下：**  

* entity  
  * key  
  * property  
  
每个标签有一些固定属性。但可以在项目中 自定义新的属性。比如使用了customAttr，则在模板中，可以使用 entity.attrMap.customAttr 的形式访问。  
因此，扩展属性其实也是跟着项目走的。最好要看模板中如何使用，到模板文件中查找更直观。
  
<a id="def_entity" />
###entity的属性
######固定属性：

* text    中文名词
* table   数据表名，不指定时，则是entity的文件名。
* urlname  生成的controller对应的 url路径  

######扩展属性

* customButtons  生成的jsp文件中定义扩展按钮
* moreFunc       扩展自定义方法
* ignoreJspIncList  不生成 inc-list.jsp	
* ignoreController	不生成 backend里的controller	

<a id="def_key" />
###key标签属性说明

* name    键名，单词，用下划线分割
* type    字段类型
* generated  true/false  是否是自动生成。 键是组合时不起作用。
* length   在类定义的@Column annotation中声明的长度

<a id="def_property" />
###property标签属性说明

* name     字段英文名
* text     字段中文名
* type     字段类型
* length   在类定义的@Column annotation中声明的长度
* nullable   可否为null
* nogrid     扩展属性，数值为 1 时，表示不在生成的页面表格列中显示




 
 
 
