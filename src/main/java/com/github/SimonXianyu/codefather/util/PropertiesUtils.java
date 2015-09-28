package com.github.SimonXianyu.codefather.util;

import com.github.SimonXianyu.codefather.ConfigConstants;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PropertiesUtils {
    private static Properties prop;

    static {
        prop = new Properties();
        try {
            prop.load(new FileInputStream(new File(ConfigConstants.CONFIG)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 2015年3月12日 下午8:29:40
     * @return
     * Get table names from config.properties
     */
    public static List<String> getTableList() {
        List<String> list = new ArrayList<String>();
        try {
            String tables = prop.getProperty("tables");
            String[] tableArr = tables.split(",");
            for (String str : tableArr) {
                str = str.trim();
                if (!"".equals(str)) {
                    list.add(str);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 2015年3月12日 下午8:55:48
     * @return
     * If precision is high,floating type is BigDecimal, and else
     *              is Double
     */
    public static String getPrecision() {
        String precision = prop.getProperty("precision");
        if (precision == null) {
            precision = "";
        }
        return precision.toLowerCase().trim();
    }

    /**
     * 2015年3月12日 下午9:22:29
     * @return
     */
    public static String getLocation() {
        String location = prop.getProperty("location");
        if (location != null) {
            location = location.trim();
        }
        String project = getProject();
        if (project != null && !"".equals(project)) {
            location = location + "/" + project;
        }
        return location;
    }

    /**
     * 2015年3月12日 下午9:23:00
     * @return
     */
    public static String getProject() {
        String project = prop.getProperty("project");
        if (project != null) {
            project = project.trim();
        }
        return project;
    }

    /**
     * 2015年3月12日 下午9:27:17
     * @return
     */
    public static String getPackage() {
        String cgpackage = prop.getProperty("package");
        if (cgpackage != null) {
            cgpackage = cgpackage.trim();
        }
        return cgpackage;
    }

    /**
     * 2015年3月12日 下午9:47:10
     * @return
     */
    public static String getLayers() {
        String layers = prop.getProperty("layers");
        if (layers == null || "".equals(layers)) {
            layers = "dbschema,dao,mapper,service,controller,model,jsp,test";
        }
        return layers.toLowerCase().trim();
    }

    /**
     * 2015年3月12日 下午9:47:14
     * @return
     */
    public static String getTablePrefix() {
        String tablePrefix = prop.getProperty("tablePrefix");
        if (tablePrefix == null) {
            tablePrefix = "";
        }
        return tablePrefix.toLowerCase().trim();
    }

}
