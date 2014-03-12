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
package com.github.tamurashingo.jalo.autoupdater.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.List;

import com.github.tamurashingo.jalo.autoupdater.AutoUpdater;
import com.github.tamurashingo.jalo.autoupdater.AutoUpdaterException;
import com.github.tamurashingo.jalo.autoupdater.AutoUpdaterListener;
import com.github.tamurashingo.jalo.xml.AppConfigBean;
import com.github.tamurashingo.jalo.xml.BootConfigBean;
import com.github.tamurashingo.jalo.xml.XMLReaderException;


/**
 * The class provides a skeletal implementation of the {@code AutoUpdater} interface.
 * 
 * <p>
 * To implement {@code AutoUpdater}, the programmer needs only to extend this class
 * and provide an implementation for {@code beginFetch}, {@code fetchFile} {@code endFetch} methods.
 * </p>
 * 
 * @author tamura shingo
 *
 */
public abstract class AbstractAutoUpdaterImpl implements AutoUpdater {

    /** BootConfig */
    protected BootConfigBean bootConfig;
    
    /**
     * application configuration data.
     * {@link #fetchAppConfig()} sets this value.
     */
    protected AppConfigBean appConfig;
    
    /** callback list for AutoUpdaterListner */
    protected List<AutoUpdaterListener> callbackList = new LinkedList<>();
    
    
    /**
     * open/connect/start transaction...
     * @throws AutoUpdaterException some error occurs.
     */
    abstract void beginFetch() throws AutoUpdaterException;
    
    /**
     * download/copy the given file from {@code BootConfigBean.getUrl()} to {@code BootConfigBean.getTmpDir()}.
     * 
     * @param filename fetch this file.
     * @throws AutoUpdaterException if fetch error occurs.
     */
    abstract void fetchFile(String filename) throws AutoUpdaterException;
    
    /**
     * close/disconnect/commit...
     * @throws AutoUpdaterException some error occurs.
     */
    abstract void endFetch() throws AutoUpdaterException;

    
    @Override
    public void setBootConfig(BootConfigBean bootConfig) {
        this.bootConfig = bootConfig;
    }
    
    @Override
    public AppConfigBean fetchAppConfig() throws AutoUpdaterException {
        deleteTmpDir();
        createTempDir();
        try {
            fetchFile(AppConfigBean.DEFAULT_FILENAME);
            
            appConfig = new AppConfigBean(bootConfig.getTmpDir());
            appConfig.read(Paths.get(bootConfig.getBaseDir(), bootConfig.getTmpDir(), AppConfigBean.DEFAULT_FILENAME).toString());
            return appConfig;
        }
        catch (XMLReaderException ex) {
            throw new AutoUpdaterException(ex);
        }
    }
    
    @Override
    public boolean isUpdatable(String currentVersion) {
    	return appConfig.getVersion().compareTo(currentVersion) > 0;
    }
    
    
    @Override
    public void download() throws AutoUpdaterException {
        notifyAllfiles();
        beginFetch();
        for (String file: appConfig.getClasspath()) {
            notifyBegin(file);
            
            fetchFile(file);
            
            notifyEnd(file);
        }
        endFetch();
    }
    
    @Override
    public void update() throws AutoUpdaterException {
    	deleteAppDir();
    	createAppDir();
        copyAllFiles();
    }
    
    @Override
    public void addAutoUpdaterListener(AutoUpdaterListener listener) {
        callbackList.add(listener);
    }
    
    @Override
    public void removeAutoUpdaterListener(AutoUpdaterListener listener) {
        callbackList.remove(listener);
    }

    
    /**
     * delete the application directory.
     */
    protected void deleteAppDir() {
    	File appDir = new File(bootConfig.getBaseDir(), bootConfig.getApplicationDir());
    	deleteAll(appDir);
    }
    
    /**
     * delete the temporary directory.
     */
    protected void deleteTmpDir() {
        File tmpDir = new File(bootConfig.getBaseDir(), bootConfig.getTmpDir());
        deleteAll(tmpDir);
    }
    
    
    /**
     * delete directories and files.
     * 
     * <p>
     * if {@code dir} is Directory, delete all files and directories in {@code dir}
     * and delete {@code dir} directory.
     * </p>
     * <p>
     * if {@code dir} is File, delete this.
     * </p>
     * 
     * @param dir File/Directory
     */
    private void deleteAll(File dir) {
        if (dir.exists() == false) {
            return ;
        }
        else if (dir.isFile()) {
            dir.delete();
        }
        else if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file: files) {
                deleteAll(file);
            }
        }
    }
    
    /**
     * create the application directory.
     * @return
     */
    protected boolean createAppDir() {
    	File appDir = new File(bootConfig.getBaseDir(), bootConfig.getApplicationDir());
    	return appDir.mkdir();
    }

    
    /**
     * create the temporary directory.
     * @return
     */
    protected boolean createTempDir() {
        File tmpDir = new File(bootConfig.getBaseDir(), bootConfig.getTmpDir());
        return tmpDir.mkdir();
    }
    
    /**
     * copy files from temporary directory to application directory.
     * 
     * @throws AutoUpdaterException if I/O error occurs.
     */
    protected void copyAllFiles() throws AutoUpdaterException {
        FileSystem fileSystem = FileSystems.getDefault();

        List<String> classpath = appConfig.getClasspath();
        classpath.add(AppConfigBean.DEFAULT_FILENAME);
        
        for (String filename: appConfig.getClasspath()) {
            Path src = fileSystem.getPath(bootConfig.getBaseDir(), bootConfig.getTmpDir(), filename);
            Path dst = fileSystem.getPath(bootConfig.getBaseDir(), bootConfig.getApplicationDir(), filename);
            try {
                Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING);
            }
            catch (IOException ex) {
                throw new AutoUpdaterException("Failed to copy file:" + filename, ex);
            }
        }
    }
    
    /**
     * notify listeners of all file names.
     */
    protected void notifyAllfiles() {
        for (AutoUpdaterListener listener: callbackList) {
            listener.notifyAllfiles(appConfig.getClasspath());
        }
    }
    
    /**
     * notify listeners of beginning the download.
     * 
     * @param filename
     */
    protected void notifyBegin(String filename) {
        for (AutoUpdaterListener listener: callbackList) {
            listener.notifyBegin(filename);
        }
    }
    
    /**
     * notify listeners of ending the download.
     * 
     * @param filename
     */
    protected void notifyEnd(String filename) {
        for (AutoUpdaterListener listener: callbackList) {
            listener.notifyEnd(filename);
        }
    }
    
}
