package io.github.SimonXianyu.codefather.templates;

import io.github.SimonXianyu.codefather.util.CodeFatherException;
import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.plexus.util.IOUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Collector of gathering Templates
 * Created by simon on 14-7-31.
 */
public class TemplateCollector {

    private File baseDir;
    private String templateSubDirectory="";
    private boolean required;
    private TemplateNode node;
    private List<TemplateDef> templateDefList = new ArrayList<TemplateDef>();

    public static TemplateCollector createInstance(File dir, String subDirectory, boolean required) throws MojoExecutionException {
        File subDir = new File(dir, subDirectory);
        if (required) {
            if (!dir.exists() || !dir.isDirectory()) {
                throw new RuntimeException("path should be an existing directory");
            }
            if (!subDir.exists() || !subDir.isDirectory()) {
                throw new MojoExecutionException("template directory wrong: " + subDir.getPath());
            }
        }
        TemplateCollector templateCollector = new TemplateCollector(subDir, subDirectory);
        templateCollector.required = required;
        return templateCollector;
    }
    private TemplateCollector(File dir, String templateSubDirectory) {
        this.baseDir = dir;
        node = new TemplateNode("");
        this.templateSubDirectory = templateSubDirectory;
    }

    public void collect() {
        if (!required && !baseDir.exists()) {
            return;
        }
        walkInDir(baseDir, node);
    }

    private void walkInDir(File dir, TemplateNode node) {
        if (!dir.exists()) {
            return ;
        }
        if (!dir.isDirectory()) {
            return;
        }
        File[] files = dir.listFiles();
        if (null == files) {
            return;
        }
        for(File f : files) {
            if (f.isDirectory()) {
                TemplateNode child = new TemplateNode(node.getPath(),f.getName());
                walkInDir(f,child);
                if (child.hasContent()) {
                    node.appendChild(f.getName(), child);
                }
                continue;
            }
            if (f.isFile()) {
                String name = f.getName();
                int pos = name.indexOf('.');
                if (pos <0) {
                    continue;
                }
                if (pos+1>=name.length()) {
                    continue;
                }
                if (name.startsWith("inc-")) {
                    // ignore include files
                    continue;
                }
                String ext = name.substring(pos+1);
                if (!"ftl".equals(ext)) { // make sure freemarker file collected.
                    continue;
                }

                String templateName = name.substring(0, pos);
                Properties props = tryToReadConfig(dir, templateName);
                TemplateDef def = null;
                try {
                    def = TemplateDef.createNew(templateName, node, props, templateSubDirectory);
                } catch (TemplateDefCreateException e) {
//                    e.printStackTrace();
                    throw new CodeFatherException("template-create", "Failed to collect template "+templateName ,e);
                }
                node.appendTemplate(templateName, def);
                templateDefList.add(def);
            }
        }
    }

    private Properties tryToReadConfig(File dir, String tname) {
        String fname = tname + ".properties";
        File f = new File(dir, fname);
        if (!f.exists()) {
            throw new CodeFatherException( "no-properties-file","Not found : "+dir.getAbsolutePath()+"/"+fname);
        }
        Properties prop = new Properties();
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(f);
            prop.load(fin);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read properties "+dir.getAbsolutePath()+fname);
        } finally {
            IOUtil.close(fin);
        }
        return prop;
    }

    public TemplateNode getNode() {
        return node;
    }

    public void setNode(TemplateNode node) {
        this.node = node;
    }

    public List<TemplateDef> getTemplateList() {
        return templateDefList;
    }
}
