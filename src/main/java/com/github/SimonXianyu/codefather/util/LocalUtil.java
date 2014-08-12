package com.github.SimonXianyu.codefather.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * User: Simon Xianyu
 * Date: 13-2-2
 * Time: 下午10:11
 */
public class LocalUtil {



    /**
     * Load Properties by file.
     * @param properties
     * @param propFile
     */
    public static void readProperties(Properties properties, File propFile)
        throws IOException
    {
        if (properties == null) {
            throw new IllegalArgumentException("Properies should not be null");
        }
        if (!propFile.exists()) {
            throw new IllegalArgumentException("Properties file doesn't exist :"+propFile.getName());
        }
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(propFile);
            properties.load(fin);
        } finally {
            closeQuietly(fin);
        }
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable!=null) {
            try {
                closeable.close();
            } catch (Exception e) {

            }
        }
    }

    public static String getStringValue(Map<String, Object> map, String key) {
        return map.get(key) == null ? "":map.get(key).toString();
    }

    public static String getStringValue(Map<String, Object> map, String key, String defaultValue) {
        return map.get(key) == null ? defaultValue:map.get(key).toString();
    }
}
