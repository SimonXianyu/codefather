package com.github.SimonXianyu.codefather.ui;

import com.github.SimonXianyu.codefather.BaseCodeFatherMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import javax.swing.*;

/**
 * Created by Simon Xianyu on 2014/11/4.
 * @goal gui
 */
public class GuiMojo extends BaseCodeFatherMojo {
    private MainPanel mainPanel;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (!init()) {
            return;
        }

        mainPanel = new MainPanel();
        mainPanel.initGui();

        readGlobalConfig();
//        collectTemplate();
        collectEntities();


        JFrame mainFrame = new JFrame("Code father gui");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setContentPane(mainPanel);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private boolean init() {
        return !checkCodeFatherPath();
    }
}
