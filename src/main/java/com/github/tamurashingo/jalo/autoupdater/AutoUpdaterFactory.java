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

import com.github.tamurashingo.jalo.JaloException;
import com.github.tamurashingo.jalo.xml.BootConfigBean;

public class AutoUpdaterFactory {
	
	private AutoUpdaterFactory() {
	}
	
	public static AutoUpdater create(BootConfigBean bootConfig) throws JaloException {
		try {
			Class<?> cls = Class.forName(bootConfig.getUpdateClass());
			Object obj = cls.newInstance();
		
			if (obj instanceof AutoUpdater) {
				return (AutoUpdater)obj;
			}
			else {
				throw new JaloException("not implement AutoUpdater:" + bootConfig.getUpdateClass());
			}
		}
		catch (ClassNotFoundException|InstantiationException|IllegalAccessException ex) {
			throw new JaloException("cannot create AutoUpdater instance:" + bootConfig.getUpdateClass(), ex);
		}
	}

}
