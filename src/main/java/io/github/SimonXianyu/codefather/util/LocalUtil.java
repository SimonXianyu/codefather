package io.github.SimonXianyu.codefather.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Collection of commonly used static methods.
 * User: Simon Xianyu
 * Since 0.0.3
 */
public class LocalUtil {

    /**
     * Load Properties from file.
     * @param properties Properties instance
     * @param propFile java.io.File instance of target file.
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

    /** Close closeable if variable is not null without throw Exception.*/
    public static void closeQuietly(Closeable closeable) {
        if (closeable!=null) {
            try {
                closeable.close();
            } catch (Exception e) {// just ignore
            }
        }
    }

    /** Extract a String value if possible without causing NullException */
    public static String extractStringValue(Map<String, Object> map, String key) {
        return map.get(key) == null ? "":map.get(key).toString();
    }

    /** Extract a String value if possible, return default value when no value for target key. */
    public static String extractStringValue(Map<String, Object> map, String key, String defaultValue) {
        return map.get(key) == null ? defaultValue:map.get(key).toString();
    }

    public static boolean isEmpty(Set<?> keyDefSet) {
        return keyDefSet == null || keyDefSet.size() == 0;
    }
}
