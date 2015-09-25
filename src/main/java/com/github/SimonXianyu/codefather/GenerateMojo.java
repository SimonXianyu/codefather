package com.github.SimonXianyu.codefather;

import com.github.SimonXianyu.codefather.model.EntityDef;
import com.github.SimonXianyu.codefather.templates.TemplateDef;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.io.File;
import java.io.IOException;

/**
 * Mojo class of code generation.
 * Created by simon on 14-6-20.
 * @author Simon Xianyu
 * @goal generate
 */
public class GenerateMojo extends BaseCodeFatherMojo {


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
        collectModules();

        renderSingleTempleForEachEntity();
        renderWithTotalEntities();

        renderForEachModule();
    }

    private void renderForEachModule() {
        // TODO
    }

    private void renderWithTotalEntities() throws MojoExecutionException {
        freemarkerRender.renderGroup(entityCollector.getEntityDefList(), contextTemplateCollector.getTemplateList(),
                this.globalProperties);
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

    /**
     * Do some initializing work here.
     * If code-father directory not found, just return false to stop following work.
     * In multi-module project, code father directory may not exist in sub-module.
     * @return true success, false failure.
     */
    private boolean init() {
        if (checkCodeFatherPath()) return false;

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

}
