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
package com.github.tamurashingo.jalo.autoupdater;

import com.github.tamurashingo.jalo.xml.AppConfigBean;
import com.github.tamurashingo.jalo.xml.BootConfigBean;


/**
 * auto updater interface.
 * 
 * <p>
 * <code><pre>
 * AutoUpdater updater = CreateAutoUpdater();
 * updater.setBootConfig(bootConfig);
 * 
 * updater.fetchAppConfig();
 * 
 * if (updater.isUpdatable()) {
 *     updater.addAutoUpdaterListener(new XXXAutoUpdaterListener());
 *     updater.download();
 *     updater.update();
 * }
 * </pre></code>
 * </p>
 * 
 * @author tamura shingo
 *
 */
public interface AutoUpdater {
    
    /**
     * set system config information.
     * 
     * @param bootConfig system config
     */
    public void setBootConfig(BootConfigBean bootConfig);
    
    
    /**
     * fetch and read application config file.
     * 
     * @return application config
     * @throws AutoUpdaterException if download error occurs.
     */
    public AppConfigBean fetchAppConfig() throws AutoUpdaterException;

    
    /**
     * download all (jar) files given by application config.
     * 
     * @throws AutoUpdaterException if download error occurs.
     */
    public void download() throws AutoUpdaterException;
    
    
    /**
     * compares current version with remote version and returns a boolean
     * which tells if the remote version is newer than current version. 
     * 
     * @return true when remote version is newer than current version.
     */
    public boolean isUpdatable(String currentVersion);
    
    
    /**
     * copy all files in temporary directory to applicaation directory.
     * 
     * @throws AutoUpdaterException if copy error occurs. 
     */
    public void update() throws AutoUpdaterException;
    
    /**
     * adds an {@code AutoUpdaterListener} to the {@code AutoUpdater}.
     * 
     * @param listener the {@AutoUpdaterListener} to be added.
     */
    public void addAutoUpdaterListener(AutoUpdaterListener listener);
    
    /**
     * removes an {@code AutoUpdaterListener} from the {@code AutoUpdater}.
     * 
     * @param listener the listener to be removed.
     */
    public void removeAutoUpdaterListener(AutoUpdaterListener listener);

}
