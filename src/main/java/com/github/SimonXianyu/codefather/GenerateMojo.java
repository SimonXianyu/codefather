package com.github.SimonXianyu.codefather;

import com.github.SimonXianyu.codefather.templates.TemplateCollector;
import com.github.SimonXianyu.codefather.util.EvaluateProperties;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

import java.io.File;

/**
 * Mojo class of code generation
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

    private TemplateCollector collector;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (!init()) {
            // exit if false.
            return;
        }
        readGlobalConfig();
        collectTemplate();
        collectEntities();

    }

    private void collectEntities() {
        // TODO
    }

    private void readGlobalConfig() {

        // TODO

    }

    private void collectTemplate() {
        // TODO
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

        return true;
    }

}
