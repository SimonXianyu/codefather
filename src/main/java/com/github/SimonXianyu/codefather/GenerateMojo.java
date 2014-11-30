package com.github.SimonXianyu.codefather;

import com.github.SimonXianyu.codefather.model.EntitySchema;
import com.github.SimonXianyu.codefather.model.EntitySchemaParser;
import com.github.SimonXianyu.codefather.templates.TemplateCollector;
import com.github.SimonXianyu.codefather.util.EvaluateProperties;
import com.github.SimonXianyu.codefather.util.LocalUtil;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
    private EntitySchema entitySchema;

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

    private void readGlobalConfig() throws MojoExecutionException {
        // TODO
        this.codeFatherDir = new File(project.getBasedir(), codeFatherPath);
        if (!codeFatherDir.exists() || !codeFatherDir.isDirectory()) {
            throw new MojoExecutionException("codefather directory doesn't exist");
        }

        File configDir = new File(codeFatherDir, "config");
        globalProperties = new EvaluateProperties();
        InputStream in = null;
        try {
            in = new FileInputStream(new File(configDir, "global.properties"));
            globalProperties.load(in);
        } catch (IOException e) {
//            e.printStackTrace();
            throw new MojoExecutionException("Failed to load global.properties");
        } finally {
            LocalUtil.closeQuietly(in);
        }

        in = null;
        try {
            in = new FileInputStream(new File(configDir, "EntitySchema.xml"));
            EntitySchemaParser parser = new EntitySchemaParser();
            this.entitySchema = parser.parseSchema(in);
        } catch (IOException e) {
            throw new MojoExecutionException("Failed to read EntitySchema.xml",e );
        } catch (SAXException e) {
            throw new MojoExecutionException("Failed to parse EntitySchema.xml",e );
        } finally {
            LocalUtil.closeQuietly(in);
        }

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
