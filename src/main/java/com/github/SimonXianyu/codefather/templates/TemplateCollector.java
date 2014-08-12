package com.github.SimonXianyu.codefather.templates;

import com.github.SimonXianyu.codefather.util.CodeFatherException;
import com.github.SimonXianyu.codefather.util.EvaluateProperties;
import org.codehaus.plexus.util.IOUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by simon on 14-7-31.
 */
public class TemplateCollector {

    private File baseDir;
    private TemplateNode node;

    public static TemplateCollector createInstance(File dir) {
        if (!dir.exists() || !dir.isDirectory()) {
            throw new RuntimeException("path should be an existing directory");
        }
        return new TemplateCollector(dir);
    }
    public static TemplateCollector createInstance(String pathString) {
        File dir = new File(pathString);
        return createInstance(dir);
    }
    private TemplateCollector(File dir) {
        this.baseDir = dir;
        node = new TemplateNode("");
    }

    public void collect() {
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

                String tname = name.substring(0, pos);
                Properties props = tryToReadConfig(dir, tname);
                TemplateDef def = null;
                try {
                    def = TemplateDef.createNew(tname, node, props);
                } catch (TemplateDefCreateException e) {
//                    e.printStackTrace();
                    throw new CodeFatherException("template-create", "Failed to collect template "+tname ,e);
                }
                node.appendTemplate(tname, def);

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
}
