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

import com.github.tamurashingo.jalo.autoupdater.AutoUpdater;
import com.github.tamurashingo.jalo.autoupdater.AutoUpdaterException;
import com.github.tamurashingo.jalo.xml.AppConfigBean;
import com.github.tamurashingo.jalo.xml.BootConfigBean;


/**
 * <p>
 * This class updates jar files.
 * </p>
 * 
 * 
 * @author tamura shingo
 *
 */
public class JaloUpdater {
	
	private BootConfigBean bootConfig;
	
	private AutoUpdater updater;
	
	public JaloUpdater(AutoUpdater updater) {
		this.updater = updater;
	}
	
	public void setBootConfig(BootConfigBean bootConfig) {
		this.bootConfig = bootConfig;
	}
	
	public void download() throws AutoUpdaterException {
		updater.setBootConfig(bootConfig);
		updater.fetchAppConfig();
	}
	
	public boolean isUpdate(AppConfigBean currentConfig) {
		if (currentConfig == null) {
			return true;
		}
		else {
			return updater.isUpdatable(currentConfig.getVersion());
		}
	}
	
	public void update() throws AutoUpdaterException {
		updater.download();
		updater.update();
	}

}
