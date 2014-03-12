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

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import com.github.tamurashingo.jalo.autoupdater.AutoUpdaterException;


/**
 * local-file-copy implementation of the {@code AutoUpdater} interface.
 * 
 * @author tamura shingo
 *
 */
public class FileAutoUpdater extends AbstractAutoUpdaterImpl {
    
    private FileSystem fileSystem = FileSystems.getDefault();

    @Override
    void beginFetch() throws AutoUpdaterException {
    }

    @Override
    void fetchFile(String filename) throws AutoUpdaterException {
        Path src = fileSystem.getPath(bootConfig.getUrl(), filename);
        Path dst = fileSystem.getPath(bootConfig.getBaseDir(), bootConfig.getTmpDir(), filename);
        try {
            Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException ex) {
            throw new AutoUpdaterException("failed to download:" + filename, ex);
        }
    }

    @Override
    void endFetch() throws AutoUpdaterException {
    }

}
