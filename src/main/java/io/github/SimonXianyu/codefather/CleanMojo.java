package io.github.SimonXianyu.codefather;

import io.github.SimonXianyu.codefather.model.EntityCollector;
import io.github.SimonXianyu.codefather.model.EntityDef;
import io.github.SimonXianyu.codefather.templates.TemplateDef;
import io.github.SimonXianyu.codefather.util.LocalUtil;
import io.github.SimonXianyu.codefather.templates.TemplateConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.mvel2.templates.TemplateRuntime;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Mojo of cleaning template directory in maven life cycle.
 *
 * Created by Simon Xianyu on 2015/9/20 0020.
 * @author Simon Xianyu
 * @goal clean
 */
public class CleanMojo extends BaseCodeFatherMojo {
    public static final String DIR_CLEAN = "DIR_CLEAN";
    private EntityCollector removedEntityCollector;
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (!init()) {
            // exit if false.
            return;
        }
        readGlobalConfig();
        collectTemplate();
        collectEntities();

        removedEntityCollector = new EntityCollector(new File(codeFatherPath,"removedEntities"), false);
        removedEntityCollector.collect();

        findCleanPaths();
        findAndRemovedFiles();
    }

    private void findCleanPaths() throws MojoExecutionException {
        Set<String> cleanPath= new HashSet<String>();
        Map<String, Object> rootContext = createContext(null, globalProperties);
        for(EntityDef entityDef : entityCollector.getDefList()) {
            rootContext.put("entity", entityDef);
            for(TemplateDef templateDef : singleTemplateCollector.getTemplateList()) {
                String path = makePathWithContext(templateDef, rootContext);
                if (StringUtils.isNotBlank(path)) {
                    cleanPath.add(path);
                }
            }
        }

        for (String path : cleanPath) {
            File dir = new File(path);
            if (!dir.exists()) {
                continue;
            }
            System.out.println("Clean path :" + dir.getPath());
            File[] files = dir.listFiles();
            if (files != null && files.length>0) {
                for(File f : files) {
                    if (f.isFile() && f.exists()) {
                        f.delete();
                    }
                }
            }
        }
    }

    private String makePathWithContext(TemplateDef templateDef, Map<String, Object> rootContext) throws MojoExecutionException {
        Map<String, Object> localContext = createContext(rootContext, templateDef.getConfig());
        String  ignoreThis = LocalUtil.extractStringValue(localContext, TemplateConstants.IGNORE_THIS);
        if (Boolean.parseBoolean(ignoreThis)) {
            return null;
        }

        String outputFilePath = (String) localContext.get(TemplateConstants.OUTPUT_PATH);
        if (outputFilePath == null) {
            throw new MojoExecutionException("Failed to get outputPath for "+templateDef.getFullname());
        }
        String outputFilename = (String) localContext.get(TemplateConstants.OUTPUT_FILENAME);
        if (outputFilename == null) {
            throw new MojoExecutionException("Failed to get outputFilename: for "+templateDef.getFullname());
        }
        File outputFileDir = new File(outputFilePath);
        if (!outputFileDir.exists()) {
            return null;
        }
        File outputFile = new File(outputFileDir, outputFilename);
        if (!Boolean.parseBoolean(LocalUtil.extractStringValue(localContext, DIR_CLEAN, "false"))
                || !outputFile.getParentFile().exists() ) {
            return null;
        }
        try {
            return outputFile.getParentFile().getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputFile.getParentFile().getPath();
    }

    private void findAndRemovedFiles() throws MojoExecutionException {
        Set<File> toBeRemovedFiles = new HashSet<File>();
        Map<String, Object> rootContext = createContext(null, globalProperties);

        // collect files to be removed.
        for(EntityDef entityDef : removedEntityCollector.getDefList()) {
            rootContext.put("entity", entityDef);
            for(TemplateDef templateDef : singleTemplateCollector.getTemplateList()) {
                collectToBeRemovedFiles(templateDef, rootContext, toBeRemovedFiles);
            }
        }
        if (0<toBeRemovedFiles.size()) {
            for (File t : toBeRemovedFiles) {
                System.out.println("Remove: "+t.getPath());
                t.delete();
            }
        }
    }

    private void collectToBeRemovedFiles(TemplateDef templateDef, Map<String, Object> rootContext, Set<File> toBeRemovedFiles) {
        Map<String, Object> localContext = createContext(rootContext, templateDef.getConfig());
        String outputFilePath = (String) localContext.get(TemplateConstants.OUTPUT_PATH);
        if (outputFilePath == null) {
            return;
        }
        String outputFilename = (String) localContext.get(TemplateConstants.OUTPUT_FILENAME);
        if (outputFilename == null) {
            return;
        }
        File outputFileDir = new File(outputFilePath);
        if (!outputFileDir.exists()) {
            return;
        }
        File outputFile = new File(outputFileDir, outputFilename);
        if (Boolean.parseBoolean(LocalUtil.extractStringValue(localContext, TemplateConstants.OVERWRITE, "true"))
                && outputFile.exists()) {
            toBeRemovedFiles.add(outputFile);
        }
    }

    private Map<String, Object> createContext(Map<String, Object> upperContext, Properties config) {
        Map<String, Object> resultContext = new HashMap<String, Object>();
        if (upperContext!=null  && upperContext.size()>0 ) {
            resultContext.putAll(upperContext);
        }
        for(Map.Entry<Object, Object> entry : config.entrySet()) {
            String rawValue = entry.getValue().toString();
            try {
                Object value = TemplateRuntime.eval(rawValue, resultContext);
                resultContext.put(entry.getKey().toString(), value);
            } catch (Exception e) {
                getLog().error("Failed to process value " + rawValue + " with " + e.getMessage());
            }
        }

        return resultContext;
    }

    /**
     * Do some initializing work here.
     * If code-father directory not found, just return false to stop following work.
     * In multi-module project, code father directory may not exist in sub-module.
     *
     * @return true success, false failure.
     */
    private boolean init() {
        if (checkCodeFatherPath()) return false;

        File templateDir = new File(codeFatherDir, "templates");
        if (!templateDir.exists() || !templateDir.isDirectory()) {
            getLog().error("Failed to find template directory " + templateDir.getPath());
            return false;
        }

        return true;
    }
}
