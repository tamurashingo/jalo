/*-
 * The MIT License
 * 
 * Copyright (c) 2013 tamura shingo
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject
 * to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 */
package com.github.tamurashingo.jalo;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import com.github.tamurashingo.jalo.xml.AppConfigBean;

/**
 * <p>
 * This class creates a classloader and run the application.
 * </p>
 * 
 * @author tamura shingo
 *
 */
public class JaloRunner {

    /** application configuration */
    private AppConfigBean appConfig;
    /** class loader for invoking application */
    private ClassLoader loader;
    
    /**
     * create new instance.
     * 
     * @param config configuration data
     */
    public JaloRunner(AppConfigBean appConfig) {
        this.appConfig = appConfig;
    }
    
    
    /**
     * create classloader to boot the application.
     * 
     * @throws JaloException
     */
    public void createClassLoader() throws JaloException {
        String baseDir = appConfig.getApplicationDir();
        if (baseDir == null || baseDir.isEmpty()) {
            baseDir = "./";
        }
        else if (!baseDir.endsWith("/")) {
            baseDir = baseDir + "/";
        }
        
        URL[] url = new URL[appConfig.getClasspath().size()];

        try {
            int ix = 0;
            for (String fileName: appConfig.getClasspath()) {
                File file = new File(baseDir, fileName);
                url[ix++] = file.toURI().toURL();
            }
        }
        catch (IOException ex) {
            throw new JaloException(ex);
        }
        
        ClassLoader parent = ClassLoader.getSystemClassLoader();
        this.loader = new URLClassLoader(url, parent);
    }
    
    
    /**
     * run the application by using created classloader.
     * 
     * @throws JaloException
     */
    public void runApp() throws JaloException {
        try {
            Class<?> cls = Class.forName(appConfig.getMainClass(), true, this.loader);
            Method method = cls.getMethod("main", new Class[]{String[].class});
            method.invoke(null, new Object[]{null});
        }
        catch (ClassNotFoundException|NoSuchMethodException|SecurityException|IllegalAccessException|IllegalArgumentException|InvocationTargetException ex) {
            throw new JaloException("cannot run application:" + appConfig.getMainClass(), ex);
        }
    }

}
