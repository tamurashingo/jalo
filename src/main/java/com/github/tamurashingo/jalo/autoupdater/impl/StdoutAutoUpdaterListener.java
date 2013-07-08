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

import java.util.List;

import com.github.tamurashingo.jalo.autoupdater.AutoUpdaterListener;

/**
 * sample implementation.
 * 
 * @author tamura shingo
 *
 */
public class StdoutAutoUpdaterListener implements AutoUpdaterListener {
    
    private int allcounts;
    private int current;

    @Override
    public void notifyAllfiles(List<String> allfiles) {
        allcounts = allfiles.size();
        current = 1;
    }

    @Override
    public void notifyBegin(String fileName) {
        System.out.printf("%s (%d/%d)", fileName, current, allcounts);
        System.out.flush();
    }

    @Override
    public void notifyEnd(String fileName) {
        System.out.printf("   ... ok");
        current++;
    }

    @Override
    public void notifyCancel(String fileName) {
    }

}
