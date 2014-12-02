package com.github.SimonXianyu.codefather.templates;

import com.github.SimonXianyu.codefather.util.CodeFatherException;
import org.apache.maven.plugin.MojoExecutionException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Test class
 * Created by simon on 14-8-6.
 */
public class TemplateCollectorTest {

    private TemplateCollector collector;
    private String workDir;

    @Before
    public void init() {
        workDir = System.getProperty("user.dir");
    }

    @Test(expected = CodeFatherException.class)
    public void testMissingPropertiesFile() {
        collector = createInstance("src/test/template-test","missing-properties-file");
        collector.collect();
    }

    @Test(expected = CodeFatherException.class)
    public void testMissingProperty() {
        collector = createInstance("src/test/template-test","missing-properties");
        collector.collect();
    }

    @Test
    public void testNormalCollect() {
        collector = createInstance("src/test/codefather/templates","single");
        collector.collect();

        assertTrue(collector.getNode()!=null);
        TemplateNode node = collector.getNode().getChild("db");
        assertNotNull(node);
        assertEquals("db", node.getPath());
        assertEquals(2,node.getTemplateDefMap().size());
        TemplateDef repoTemplate = node.getTemplateDefMap().get("repo");
        assertNotNull(repoTemplate);
        assertEquals("repo", repoTemplate.getName());
    }

    @Test
    public void testCollectPath() {
        collector = createInstance("src/test/template-test","test-path");
        collector.collect();

        assertTrue(collector.getNode()!=null);
        TemplateNode node = collector.getNode().getChild("java");
        assertNotNull(node);
        assertEquals("java", node.getPath());
        assertEquals(1,node.getTemplateDefMap().size());
    }

    private TemplateCollector createInstance(String path, String subPath) {
        try {
            return TemplateCollector.createInstance(new File(workDir,path),subPath);
        } catch (MojoExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
