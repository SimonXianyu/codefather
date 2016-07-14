package io.github.SimonXianyu.codefather.model.funcmodule;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

/**
 *
 * Created by simon on 15/9/27.
 */
public class ModuleCollectorTest {
    private ModuleCollector collector;
    private File baseDir = new File("src/test/module-test");

    @Before
    public void setUp() {
    }

    @Test
    public void testNormalCollect() {
        collector = new ModuleCollector(new File(baseDir,"normal"));
        collector.collect();
        List<ModuleDef> defList = collector.getDefList();
        assertNotNull(defList);
        assertEquals("count should be 1", 1, defList.size());

        ModuleDef moduleDef = collector.getDefByName("Sys");
        assertNotNull("SysModule should exists", moduleDef);
        assertEquals("Sys", moduleDef.getName());
        assertEquals("系统用户", moduleDef.getText());

        assertTrue(moduleDef.getGroups().size()>0);

    }
}
