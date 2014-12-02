package com.github.SimonXianyu.codefather;

import com.github.SimonXianyu.codefather.model.EntityCollector;
import com.github.SimonXianyu.codefather.model.EntityDef;
import com.github.SimonXianyu.codefather.model.EntitySchema;
import com.github.SimonXianyu.codefather.model.EntitySchemaParser;
import com.github.SimonXianyu.codefather.templates.TemplateCollector;
import com.github.SimonXianyu.codefather.templates.TemplateDef;
import com.github.SimonXianyu.codefather.util.EvaluateProperties;
import com.github.SimonXianyu.codefather.util.LocalUtil;
import freemarker.template.Template;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Mojo class of code generation.
 * Created by simon on 14-6-20.
 * @author Simon Xianyu
 * @goal generate
 */
public class GenerateMojo extends AbstractMojo {

    /**
     * Field of maven project information
     * @parameter expression="${project}"
     */
    private MavenProject project;

    /**
     * codefather path, relative to project path
     * @parameter expression="${codeFatherPath}", default-value="src/main/codefather"
     */
    private String codeFatherPath = "src/main/codefather";


    // ================ internal properties from here ======================
    private File codeFatherDir;

    private EvaluateProperties globalProperties;
    /** Entity schema for validating */
    private EntitySchema entitySchema;

    private EntityCollector entityCollector;
    private TemplateCollector singleTemplateCollector;
    private TemplateCollector contextTemplateCollector;
    private FreemarkerRender freemarkerRender;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (!init()) {
            // exit if false.
            return;
        }
        readGlobalConfig();
        collectTemplate();
        collectEntities();

        renderSingleTempleForEachEntity();
    }

    /**
     * This method visit template tree for each entity in list, try to render if necessary.
     */
    private void renderSingleTempleForEachEntity() throws MojoExecutionException {
        for(EntityDef entityDef : entityCollector.getEntityDefList()) {
            for(TemplateDef templateDef : singleTemplateCollector.getTemplateList()) {
                freemarkerRender.render(entityDef, templateDef, this.globalProperties);
            }
        }
    }

    private void collectEntities() {
        entityCollector = new EntityCollector(new File(codeFatherPath,"entities"));
        entityCollector.collect();
    }

    private void readGlobalConfig() throws MojoExecutionException {
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

    private void collectTemplate() throws MojoExecutionException {
        singleTemplateCollector = TemplateCollector.createInstance(new File(codeFatherDir,"templates"),"single");
        singleTemplateCollector.collect();

        contextTemplateCollector = TemplateCollector.createInstance(new File(codeFatherDir, "templates"), "context");
        contextTemplateCollector.collect();
    }

    /**
     * Do some initializing work here.
     * If code-father directory not found, just return false to stop following work.
     * In multi-module project, code father directory may not exist in sub-module.
     * @return true success, false failure.
     */
    private boolean init() {
        codeFatherDir = new File(project.getBasedir(), codeFatherPath);
        if (!codeFatherDir.exists() || !codeFatherDir.isDirectory()) {
            getLog().warn("Failed to find directory of code father.");
            return false;
        }

        File templateDir = new File(codeFatherDir, "templates");
        if (!templateDir.exists() || !templateDir.isDirectory()) {
            getLog().error("Failed to find template directory "+templateDir.getPath());
            return false;
        }
        try {
            freemarkerRender = new FreemarkerRender(templateDir, getLog());
        } catch (IOException e) {
            getLog().error("Failed to initialize freemarker render");
            return false;
        }

        return true;
    }

    public MavenProject getProject() {
        return project;
    }

    public void setProject(MavenProject project) {
        this.project = project;
    }
}
