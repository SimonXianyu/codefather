package com.github.SimonXianyu.codefather.generator;

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After; 

/** 
* SchemaGenerator Tester. 
* 
* @author <Authors name> 
* @since <pre>8/19, 2015</pre>
* @version 1.0 
*/ 
public class SchemaGeneratorTest {
    private SchemaGenerator generator;

    @Before
    public void before() throws Exception {
        generator = new SchemaGenerator();
    } 

    @After
    public void after() throws Exception { 
    } 

    /** 
     * 
     * Method: generatorSchemas() 
     * 
     */ 
    @Test
    public void testGeneratorSchemas() throws Exception {
        generator.generatorSchemas();
    } 


    /** 
     * 
     * Method: generateSchema(String tableName, Map<String, String> pkMap) 
     * 
     */ 
    @Test
    public void testGenerateSchema() throws Exception { 
        //TODO: Test goes here... 
    /* 
    try { 
       Method method = SchemaGenerator.getClass().getMethod("generateSchema", String.class, Map<String,.class); 
       method.setAccessible(true); 
       method.invoke(<Object>, <Parameters>); 
    } catch(NoSuchMethodException e) { 
    } catch(IllegalAccessException e) { 
    } catch(InvocationTargetException e) { 
    } 
    */ 
    } 

    /** 
     * 
     * Method: setTableList() 
     * 
     */ 
    @Test
    public void testSetTableList() throws Exception { 
        //TODO: Test goes here... 
    /* 
    try { 
       Method method = SchemaGenerator.getClass().getMethod("setTableList"); 
       method.setAccessible(true); 
       method.invoke(<Object>, <Parameters>); 
    } catch(NoSuchMethodException e) { 
    } catch(IllegalAccessException e) { 
    } catch(InvocationTargetException e) { 
    } 
    */ 
    } 

} 
