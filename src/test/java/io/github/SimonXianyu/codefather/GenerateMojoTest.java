package io.github.SimonXianyu.codefather;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Test class for mojo.
 * Created by simon on 2014/12/1.
 */
public class GenerateMojoTest {

    GenerateMojo mojo;

    @Before
    public void setUp() {
        mojo = new GenerateMojo();
    }

    @Test
    public void testGen() throws MojoFailureException, MojoExecutionException {
//        mojo.setAutoMkdir(true);
//        mojo.setConfigPath("src/test/code-father");
//        mojo.setEntitiesPath("src/test/code-father/entities");
//        mojo.setTemplatesPath("src/test/code-father/templates");
        MavenProject project = new MavenProject();
        String baseDir = System.getProperty("user.dir");
        System.out.println(baseDir);
        project.setFile(new File(baseDir, "pom.xml"));
        mojo.setProject(project);

        mojo.execute();
    }

}
