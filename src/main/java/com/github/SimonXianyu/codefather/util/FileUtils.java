package com.github.SimonXianyu.codefather.util;

import com.github.SimonXianyu.codefather.ConfigConstants;

import java.io.*;

public class FileUtils {
    /**
     * 2015年3月12日 下午9:49:21
     */
    public static void createSchemaDirectory() {
        /*String location = PropertiesUtils.getLocation();
        String project = PropertiesUtils.getProject();
        if (project != null && !"".equals(project)) {
            location = location + "/src";
        }
        String dbSchemaDir = location + "/dbschema";*/

        String dbSchemaDir = ConfigConstants.SCHEMA_PATH;
        mkdir(dbSchemaDir);
    }

    /**
     * 2015年3月12日 下午9:49:15
     * @param dir
     */
    private static void mkdir(String dir) {
        File file;
        file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 2015年3月12日 下午9:49:25
     * @param template
     * @return
     * Get the template file
     */
    public static String getTemplate(String template) {
        String path = ConfigConstants.TEMPLATE_PATH + "/single";
        File file = new File(path + template);
        return read(file);
    }

    /**
     * 2015年3月12日 下午9:49:30
     * @param file
     * @return
     */
    public static String read(File file) {
        StringBuilder res = new StringBuilder();
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int i = 0;
            while ((line = reader.readLine()) != null) {
                if (i != 0) {
                    res.append('\n');
                }
                res.append(line);
                i++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res.toString();
    }

    /**
     * 2015年3月13日 上午1:04:52
     * @param content
     * @param path
     * @return
     */
    public static boolean write(String content, String path) {
        try {
            File file = new File(path);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(content);
            writer.flush();
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
