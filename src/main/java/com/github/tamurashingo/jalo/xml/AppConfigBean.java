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
package com.github.tamurashingo.jalo.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This class has configuration data about the application.
 * 
 * @author tamura shingo
 *
 */
public class AppConfigBean implements java.io.Serializable, XMLReader {
    
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** default configuration file name */
    public static final String DEFAULT_FILENAME = "app.xml";
    
    /** application name */
    private String applicationName;
    
    /** directory stored jar files */
    private String applicationDir;
    
    /** application version */
    private String version;
    
    /** class path(jar files) */
    private List<String> classpath;
    
    /** application main class */
    private String mainClass;
    
    /**
     * Constructor.
     */
    public AppConfigBean() {
        classpath = new LinkedList<>();
        this.applicationDir = "";
    }
    
    /**
     * Constructor.
     * @param applicationDir directory stored class/jar files
     */
    public AppConfigBean(String applicationDir) {
        this();
        this.applicationDir = applicationDir;
    }
    
//    /**
//     * Construct and read config.
//     * @param bootConfig system configuration.
//     * @throws XMLReaderException if error occurs. 
//     */
//    public AppConfigBean(BootConfigBean bootConfig) throws XMLReaderException {
//        this(bootConfig.getApplicationDir());
//        read(bootConfig.getApplicationDir() + "/" + DEFAULT_FILENAME);
//    }
    

    /**
     * Create and read config.
     * @param bootConfig system configuration
     * @return AppConfigBean. if app.xml is not found, return null.
     * @throws XMLReaderException if error occurs.
     */
    public static AppConfigBean createConfig(BootConfigBean bootConfig) throws XMLReaderException {
    	AppConfigBean appConfig = new AppConfigBean(bootConfig.getApplicationDir());
    	
    	File file = new File(appConfig.applicationDir, DEFAULT_FILENAME);
    	if (file.exists()) {
    		appConfig.read(file.getPath());
    		return appConfig;
    	}
    	else {
    		return null;
    	}
    }

    
    @Override
    public void read(String filename) throws XMLReaderException {
        try (FileInputStream in = new FileInputStream(filename)) {
            
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(in);
            XPath xpath = XPathFactory.newInstance().newXPath();
            
            readApplicationName(xpath, doc);
            readVersion(xpath, doc);
            readClasspath(xpath, doc);
            readMainClass(xpath, doc);
        }
        catch (FileNotFoundException ex) {
            throw new XMLReaderException(filename, ex);
        }
        catch (ParserConfigurationException|SAXException|IOException ex) {
            throw new XMLReaderException("XML Error", ex);
        }
    }
    
    /**
     * read ant set the application name.
     * 
     * @param xpath XPath
     * @param doc Document
     * @throws XMLReaderException if parse error occurs.
     */
    private void readApplicationName(XPath xpath, Document doc) throws XMLReaderException {
        try {
            String applicationName = (String)xpath.evaluate("/app/name", doc, XPathConstants.STRING);
            mandatoryCheck(applicationName, "name");
            this.setApplicationName(applicationName);
        }
        catch (XPathExpressionException ex) {
            throw new XMLReaderException("cannot read '/app/name'", ex);
        }
    }
    
    /**
     * read and set the version.
     * 
     * @param xpath XPath
     * @param doc Document
     * @throws XMLReaderException if parse error occurs.
     */
    private void readVersion(XPath xpath, Document doc) throws XMLReaderException {
        try {
            String version = (String)xpath.evaluate("/app/version", doc, XPathConstants.STRING);
            mandatoryCheck(version, "version");
            this.setVersion(version);
        }
        catch (XPathExpressionException ex) {
            throw new XMLReaderException("cannot read '/app/version'", ex);
        }
    }
    
    /**
     * read and set the classpath.
     * 
     * @param xpath XPath
     * @param doc Document
     * @throws XMLReaderException if parse error occurs.
     */
    private void readClasspath(XPath xpath, Document doc) throws XMLReaderException {
        try {
            NodeList classpath = (NodeList)xpath.evaluate("/app/classpath/path", doc, XPathConstants.NODESET);
            mandatoryCheck(classpath, "classpath/path");
            for (int ix = 0; ix < classpath.getLength(); ix++) {
                String cls = classpath.item(ix).getTextContent();
                this.classpath.add(cls);
            }
        }
        catch (XPathExpressionException ex) {
            throw new XMLReaderException("cannot read '/app/classpath/path'");
        }
    }
    
    /**
     * read and set the main class.
     * @param xpath XPath
     * @param doc Document
     * @throws XMLReaderException if parse error occurs.
     */
    private void readMainClass(XPath xpath, Document doc) throws XMLReaderException {
        try {
            String mainClass = (String)xpath.evaluate("/app/mainclass", doc, XPathConstants.STRING);
            mandatoryCheck(mainClass, "mainclass");
            this.setMainClass(mainClass);
        }
        catch (XPathExpressionException ex) {
            throw new XMLReaderException("cannot read '/app/mainclass'", ex);
        }
    }
    
    /**
     * mandatory check method.
     * @param item target
     * @param itemName for error message
     * @throws XMLReaderException if item is null or empty.
     */
    private void mandatoryCheck(String item, String itemName) throws XMLReaderException {
        if (item == null || item.isEmpty()) {
            throw new XMLReaderException("mandatory item `" + itemName + "' not set.");
        }
    }
    
    /**
     * mandatory check method.
     * @param node target
     * @param nodeName for error message
     * @throws XMLReaderException if node is null or empty
     */
    private void mandatoryCheck(NodeList node, String nodeName) throws XMLReaderException {
        if (node == null || node.getLength() == 0) {
            throw new XMLReaderException("mandatory item `" + nodeName + "' not set.");
        }
    }


    /**
     * @return the applicationName
     */
    public String getApplicationName() {
        return applicationName;
    }


    /**
     * @param applicationName the applicationName to set
     */
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }


    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }


    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }


    /**
     * @return the classpath
     */
    public List<String> getClasspath() {
        return classpath;
    }


    /**
     * @param classpath the classpath to set
     */
    public void setClasspath(List<String> classpath) {
        this.classpath = classpath;
    }


    /**
     * @return the mainClass
     */
    public String getMainClass() {
        return mainClass;
    }


    /**
     * @param mainClass the mainClass to set
     */
    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    /**
     * @return the applicationDir
     */
    public String getApplicationDir() {
        return applicationDir;
    }

    /**
     * @param applicationDir the applicationDir to set
     */
    public void setApplicationDir(String applicationDir) {
        this.applicationDir = applicationDir;
    }

}
    