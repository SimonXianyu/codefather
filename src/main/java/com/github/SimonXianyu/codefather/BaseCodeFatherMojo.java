package com.github.SimonXianyu.codefather;

import com.github.SimonXianyu.codefather.model.EntityCollector;
import com.github.SimonXianyu.codefather.model.EntitySchema;
import com.github.SimonXianyu.codefather.model.EntitySchemaParser;
import com.github.SimonXianyu.codefather.templates.TemplateCollector;
import com.github.SimonXianyu.codefather.util.EvaluateProperties;
import com.github.SimonXianyu.codefather.util.LocalUtil;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;

/**
 * Created by simon on 2015/1/16.
 */
public abstract class BaseCodeFatherMojo extends AbstractMojo {
    /**
     * Field of maven project information
     * @parameter property="project"
     */
    protected MavenProject project;
    /**
     * codefather path, relative to project path
     * @parameter property="codeFatherPath", default-value="src/main/codefather"
     */
    protected String codeFatherPath = "src/main/codefather";
    // ================ internal properties from here ======================
    protected File codeFatherDir;
    protected EvaluateProperties globalProperties;
    protected EntityCollector entityCollector;
    protected TemplateCollector singleTemplateCollector;
    protected TemplateCollector contextTemplateCollector;
    /** Entity schema for validating */
    private EntitySchema entitySchema;

    protected void collectEntities() {
        entityCollector = new EntityCollector(new File(codeFatherPath,"entities"));
        entityCollector.collect();
    }

    protected void readGlobalConfig() throws MojoExecutionException {
        File configDir = new File(codeFatherDir, "config");
        globalProperties = new EvaluateProperties();

        try {
            LocalUtil.readProperties(globalProperties, new File(configDir, "global.properties"));
        } catch (IOException e) {
            throw new MojoExecutionException("Failed to load global.properties");
        }

        EntitySchemaParser entitySchemaParser = new EntitySchemaParser();
        entitySchema = entitySchemaParser.readEntitySchema(configDir);

    }

    protected void collectTemplate() throws MojoExecutionException {
        singleTemplateCollector = TemplateCollector.createInstance(new File(codeFatherDir, "templates"), "single");
        singleTemplateCollector.collect();

        contextTemplateCollector = TemplateCollector.createInstance(new File(codeFatherDir, "templates"), "context");
        contextTemplateCollector.collect();
    }

    public MavenProject getProject() {
        return project;
    }

    public void setProject(MavenProject project) {
        this.project = project;
    }

    protected boolean checkCodeFatherPath() {
        codeFatherDir = new File(project.getBasedir(), codeFatherPath);
        if (!codeFatherDir.exists() || !codeFatherDir.isDirectory()) {
            getLog().warn("Failed to find directory of code father.");
            return true;
        }
        return false;
    }
}
