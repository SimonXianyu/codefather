package com.github.SimonXianyu.codefather.model;

import java.util.Properties;

/**
 * Created by simon on 14-7-28.
 */
public class EntityPackage {
    private String name;
    private String location;

    private Properties packageConfig = new Properties();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Properties getPackageConfig() {
        return packageConfig;
    }

    public void setPackageConfig(Properties packageConfig) {
        this.packageConfig = packageConfig;
    }
}
