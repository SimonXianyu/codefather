package com.github.SimonXianyu.codefather;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

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

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
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

}
