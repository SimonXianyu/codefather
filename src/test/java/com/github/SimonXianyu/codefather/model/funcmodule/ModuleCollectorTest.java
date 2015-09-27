package com.github.SimonXianyu.codefather.model.funcmodule;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 *
 * Created by simon on 15/9/27.
 */
public class ModuleCollectorTest {
    private ModuleCollector moduleCollector;
    private File baseDir = new File("src/text/module-test");

    @Before
    public void setUp() {
    }

    @Test
    public void testNormalCollect() {
        moduleCollector = new ModuleCollector(new File(baseDir,"normal"));
    }
}
