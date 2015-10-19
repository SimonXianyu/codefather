package io.github.SimonXianyu.codefather.templates;

import org.apache.commons.lang.StringUtils;

import java.util.Properties;

/**
 * Class to hold template path name and configuration in properties file form
 * Created by simon on 14-7-31.
 */
public class TemplateDef {
    private String path = "";
    private String name;
    private String fullname;
    private String baseSubDir;
    /**
     * Properties defined for the template.
     * The values could be an expression which is evaluated before process the template according to entity context.
     */
    private Properties config;

    public static TemplateDef createNew(String templateName, TemplateNode node, Properties config, String baseSubDir) throws TemplateDefCreateException {
        if (config == null) {
            throw new RuntimeException("each template must have a configuration properties file.");
        }
        assertPropertyExist(config, "outputPath");
        assertPropertyExist(config, "outputFilename");
        return new TemplateDef(templateName, node, config, baseSubDir);
    }

    private static void assertPropertyExist(Properties config, String key) throws TemplateDefCreateException {
        if (!config.containsKey(key)) {
            throw new TemplateDefCreateException(" Configuration must include "+key);
        }
        String o = config.getProperty(key);
        if (StringUtils.isBlank(StringUtils.trimToNull(o))) {
            throw new TemplateDefCreateException(" Configuration item "+key+" should not be blank");
        }
    }

    public TemplateDef(String templateName, TemplateNode node, Properties config, String baseSubDir) {
        this.path = node.getPath();
        this.name = templateName;
        this.fullname = node.getPath()+'/'+templateName;
        this.config = config;
        this.baseSubDir = baseSubDir;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Properties getConfig() {
        return config;
    }

    public void setConfig(Properties config) {
        this.config = config;
    }

    public String getLocationName() {
        if (path ==null|| path.length()==0) {
            return baseSubDir+ "/"+name+".ftl";
        }
        return baseSubDir+"/"+fullname+".ftl";
    }
}
