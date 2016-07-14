package io.github.SimonXianyu.codefather.model;

import io.github.SimonXianyu.codefather.util.LocalUtil;
import org.apache.commons.digester3.Digester;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;

/**
 *
 * Created by Simon Xianyu on 2015/9/22 0022.
 */
public abstract class AbstractDefParser<T> {
    protected Class<T> clazz;

    public AbstractDefParser() {
        clazz = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Parse entity with specified file.
     * The filename (without extension ) will be used as default name.
     **/
    public T parse(File sourceFile) {
        String name = sourceFile.getName().substring(0, sourceFile.getName().lastIndexOf('.'));
        InputStream in = null;
        try {
            in = new FileInputStream(sourceFile);
            return this.parseWithJaxb(name, in);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse while reading file:"+sourceFile.getName(),e);
        } finally {
            LocalUtil.closeQuietly(in);
        }
    }

    public T parse(String name, InputStream in) throws IOException {
        Digester dig = createDig();
        try {
            Object resultObj = dig.parse(in);

            if (clazz.isInstance(resultObj) ) {
                T def = clazz.cast(resultObj);
                processName(name, def);
                return def;
            }
            throw new RuntimeException("Failed to parse entity :"+name);
        } catch (SAXException e) {
            throw new RuntimeException("Failed to parse because xml error in entity :"+name,e);
        }
    }
    public T parseWithJaxb(String name, InputStream in) throws IOException {
        try {
            Unmarshaller unmarshaller = JAXBContext.newInstance(clazz).createUnmarshaller();
            Object resultObj = unmarshaller.unmarshal(in);
            if (clazz.isInstance(resultObj) ) {
                T def = clazz.cast(resultObj);
                processName(name, def);
                return def;
            }
            throw new RuntimeException("Failed to parse entity :"+name);
        } catch (JAXBException e) {
//            e.printStackTrace();
            throw new RuntimeException("Failed to parse because xml error in entity :"+name,e);
        }
    }

    abstract protected void processName(String name, T def);

    protected abstract Digester createDig();
}
