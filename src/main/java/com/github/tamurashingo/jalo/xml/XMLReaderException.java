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

import com.github.tamurashingo.jalo.JaloException;

/**
 * Signals that XML read error.
 * 
 * @author tamura shingo
 *
 */
public class XMLReaderException extends JaloException {
    
    /**
     * This exception {@code serialVersionUID}.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Construct a new <code>XMLReaderException</code>.
     */
    public XMLReaderException() {
        super();
    }
    
    /**
     * Construct a new <code>XMLReaderException</code>
     * with the specified short message.
     * 
     * @param message the short message
     */
    public XMLReaderException(String message) {
        super(message);
    }
    
    /**
     * Construct a new <code>XMLReaderException</code>
     * with the specified message and cause.
     *  
     * @param message the short message
     * @param cause the cause
     */
    public XMLReaderException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Construct a new <code>XMLReaderException</code>
     * with the cause.
     * 
     * @param cause the cause
     */
    public XMLReaderException(Throwable cause) {
        super(cause);
    }

}
