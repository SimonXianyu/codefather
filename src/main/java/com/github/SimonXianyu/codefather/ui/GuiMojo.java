package com.github.SimonXianyu.codefather.ui;

import com.github.SimonXianyu.codefather.BaseCodeFatherMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Simon Xianyu on 2014/11/4.
 * @goal gui
 */
public class GuiMojo extends BaseCodeFatherMojo {

    private AtomicBoolean runFlag = new AtomicBoolean(false);

    private final Object lock = new Object();

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (!init()) {
            return;
        }

        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages/main");

        MainPanel mainPanel = new MainPanel();
        mainPanel.setResourceBundle(resourceBundle);
        mainPanel.initGui();

        readGlobalConfig();
//        collectTemplate();
        collectEntities();

        runFlag.set(true);
        Thread waitingThread= new Thread() {
            @Override
            public void run() {
                synchronized (lock) {
                    while (runFlag.get() ) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
//                            e.printStackTrace();
                            // no need care.
                        }
                    }
                }
            }
        };
        waitingThread.start();

        JFrame mainFrame = new JFrame("Code father gui");
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                runFlag.set(false);
            }
        });
        mainFrame.setContentPane(mainPanel);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        try {
            waitingThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean init() {
        return !checkCodeFatherPath();
    }

}
