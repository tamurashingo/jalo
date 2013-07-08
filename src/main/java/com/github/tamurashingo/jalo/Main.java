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

import com.github.tamurashingo.jalo.xml.AppConfigBean;
import com.github.tamurashingo.jalo.xml.BootConfigBean;


/**
 * Main.
 * 
 * @author tamura shingo
 * @version $Id$
 *
 */
public class Main {
    
    /**
     * parse args and get BootConfig filename.
     * if argument isn't given, returns default filename.
     * 
     * @param args
     * @return
     */
    public static String getBootFileName(String[] args) {
        if (args == null || args.length < 1) {
            return BootConfigBean.DEFAULT_FILENAME;
        }
        else {
            return args[0];
        }
    }
    
    
    /**
     * entry point.
     * 
     * @param args BootConfig filename
     * @throws Exception if error occurs.
     */
    public static void main(String... args) throws Exception {
        BootConfigBean bootConfig = new BootConfigBean();
        String bootFile = getBootFileName(args);
        bootConfig.read(bootFile);
        
        AppConfigBean appConfig = new AppConfigBean(bootConfig);
        Jalo jalo = new Jalo(appConfig);
        jalo.createClassLoader();
        jalo.runApp();
    }

}
