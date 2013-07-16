package test.com.github.tamurashingo.jalo.xml;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.tamurashingo.jalo.xml.BootConfigBean;
import com.github.tamurashingo.jalo.xml.XMLReaderException;

public class BootConfigBeanTest {

    
    /*-
     * normal pattern
     */
    @Test
    public void test001() throws Exception {
        BootConfigBean bean = new BootConfigBean();
        String file = BootConfigBeanTest.class.getClassLoader().getResource("bootconfig/configtest001.xml").toURI().getPath();
        bean.read(file);
        
        assertEquals("test001", bean.getApplicationName());
        assertEquals("app", bean.getApplicationDir());
        
        assertEquals("http://www.yahoo.co.jp/", bean.getUrl());
        assertEquals(false, bean.isAutoUpdate());
        assertEquals("tmp", bean.getTmpDir());
    }
    
    
    /*-
     * not mandatory check.
     * expect no error.
     */
    @Test
    public void test002() throws Exception {
        BootConfigBean bean = new BootConfigBean();
        String file = BootConfigBeanTest.class.getClassLoader().getResource("bootconfig/configtest002.xml").toURI().getPath();
        bean.read(file);
    }
    
    
    /*-
     * mandatory check error for 'application-name'
     */
    @Test
    public void test003() throws Exception {
        BootConfigBean bean = new BootConfigBean();
        try {
            String file = BootConfigBeanTest.class.getClassLoader().getResource("bootconfig/configtest003.xml").toURI().getPath();
            bean.read(file);
            fail("don't reach");
        }
        catch (XMLReaderException ex) {
            // ok
        }
    }
    
    /*-
     * mandatory check error for 'application-directory'
     */
    @Test
    public void test004() throws Exception {
        BootConfigBean bean = new BootConfigBean();
        try {
            String file = BootConfigBeanTest.class.getClassLoader().getResource("bootconfig/configtest004.xml").toURI().getPath();
            bean.read(file);
            fail("don't reach");
        }
        catch (XMLReaderException ex) {
            // ok
        }
    }
    
}
