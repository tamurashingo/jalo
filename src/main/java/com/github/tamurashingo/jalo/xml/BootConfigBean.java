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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;


/**
 * This class has configuration data about loading and invoking.
 * 
 * @author tamura shingo
 *
 */
public class BootConfigBean implements java.io.Serializable, XMLReader {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** default file name */
	public static final String DEFAULT_FILENAME = "jalo.xml";
	
	/** application name */
	private String applicationName;
	
	/** directory stored the appconfig.xml file */
	private String applicationDir;
	
	
    /** update automatically? */
    private boolean autoUpdate;
    
	/** download from */
	private String url;
	
	/** download temporary directory */
	private String tmpDir;
	
	
    @Override
    public void read(String filename) throws XMLReaderException {
        try (FileInputStream in = new FileInputStream(filename)) {
            
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(in);
            XPath xpath = XPathFactory.newInstance().newXPath();
            
            readApplicationName(xpath, doc);
            readApplicationDir(xpath, doc);
            
            readAutoUpdate(xpath, doc);
            readUrl(xpath, doc);
            readTmpDir(xpath, doc);
        }
        catch (FileNotFoundException ex) {
            throw new XMLReaderException(ex);
        }
        catch (ParserConfigurationException|SAXException|IOException ex) {
            throw new XMLReaderException(ex);
        }
    }

    
    /**
     * read and set the application name.
     *  
     * @param xpath XPath
     * @param doc Document
     * @throws XMLReaderException if parse error occurs.
     */
    private void readApplicationName(XPath xpath, Document doc) throws XMLReaderException {
        try {
            String applicationName = (String)xpath.evaluate("/jalo/application-name", doc, XPathConstants.STRING);
            mandatoryCheck(applicationName, "application-name");
            this.setApplicationName(applicationName);
        }
        catch (XPathExpressionException ex) {
            throw new XMLReaderException("cannot read '/jalo/application-name'", ex);
        }
    }
    
    
    /**
     * read and set the application directory.
     * 
     * @param xpath XPath
     * @param doc Document 
     * @throws XMLReaderException if parse error occurs.
     */
    private void readApplicationDir(XPath xpath, Document doc) throws XMLReaderException {
        try {
            String applicationDir = (String)xpath.evaluate("/jalo/application-directory", doc, XPathConstants.STRING);
            mandatoryCheck(applicationDir, "application-directory");
            this.setApplicationDir(applicationDir);
        }
        catch (XPathExpressionException ex) {
            throw new XMLReaderException("cannot read '/jalo/application-directory'", ex);
        }
    }

    
    /**
     * read and set the url.
     * 
     * @param xpath XPath
     * @param doc Document
     * @throws XMLReaderException if parse error occurs.
     */
    private void readUrl(XPath xpath, Document doc) throws XMLReaderException {
        try {
            String url = (String)xpath.evaluate("/jalo/url", doc, XPathConstants.STRING);
            this.setUrl(url);
        }
        catch (XPathExpressionException ex) {
            throw new XMLReaderException("cannot read '/jalo/url'", ex);
        }
    }

    
    /**
     * read and set the auto-update flag.
     * 
     * @param xpath XPath
     * @param doc Document
     * @throws XMLReaderException if parse error occurs.
     */
    private void readAutoUpdate(XPath xpath, Document doc) throws XMLReaderException {
        try {
            String autoUpdate = (String)xpath.evaluate("/jalo/auto-update", doc, XPathConstants.STRING);
            if (autoUpdate == null || autoUpdate.isEmpty()) {
                this.setAutoUpdate(false);
            }
            else if (autoUpdate.equals("1") || autoUpdate.equalsIgnoreCase("true")) {
                this.setAutoUpdate(true);
            }
            else {
                this.setAutoUpdate(false);
            }
        }
        catch (XPathExpressionException ex) {
            throw new XMLReaderException("cannot read '/jalo/auto-update'", ex);
        }
    }
    
    
    /**
     * read and set the temporary directory name.
     * 
     * @param xpath XPath
     * @param doc Document
     * @throws XMLReaderException if parse error occurs.
     */
    private void readTmpDir(XPath xpath, Document doc) throws XMLReaderException {
        try {
            String tmpDir = (String)xpath.evaluate("/jalo/temporary-directory", doc, XPathConstants.STRING);
            this.setTmpDir(tmpDir);
        }
        catch (XPathExpressionException ex) {
            throw new XMLReaderException("cannot read '/jalo/temporary-directory", ex);
        }
    }
    
    private void mandatoryCheck(String item, String itemName) throws XMLReaderException {
        if (item == null || item.isEmpty()) {
            throw new XMLReaderException("mandatory item `" + itemName + "' not set." );
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


    /**
     * @return the autoUpdate
     */
    public boolean isAutoUpdate() {
        return autoUpdate;
    }


    /**
     * @param autoUpdate the autoUpdate to set
     */
    public void setAutoUpdate(boolean autoUpdate) {
        this.autoUpdate = autoUpdate;
    }


    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }


    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }


    /**
     * @return the tmpDir
     */
    public String getTmpDir() {
        return tmpDir;
    }


    /**
     * @param tmpDir the tmpDir to set
     */
    public void setTmpDir(String tmpDir) {
        this.tmpDir = tmpDir;
    }
    

}
