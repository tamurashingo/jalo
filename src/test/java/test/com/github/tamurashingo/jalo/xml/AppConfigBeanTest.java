package test.com.github.tamurashingo.jalo.xml;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import com.github.tamurashingo.jalo.xml.AppConfigBean;
import com.github.tamurashingo.jalo.xml.BootConfigBean;

public class AppConfigBeanTest {

    /*-
     * normal pattern
     */
    @Test
    public void test001() throws Exception {
        AppConfigBean bean = new AppConfigBean();
        String file = AppConfigBeanTest.class.getClassLoader().getResource("appconfig/configtest001.xml").toURI().getPath();
        bean.read(file);
        
        assertEquals("test001", bean.getApplicationName());
        assertEquals("0.1", bean.getVersion());
        
        assertEquals(2, bean.getClasspath().size());
        assertEquals("test01.jar", bean.getClasspath().get(0));
        assertEquals("test02.jar", bean.getClasspath().get(1));
        
        assertEquals("com.github.tamurashingo.jalo.Test", bean.getMainClass());
        
        assertNotEquals("", bean.getApplicationDir());
    }
    
    /*-
     * construct with application directory
     */
    @Test
    public void test002() throws Exception {
        AppConfigBean bean = new AppConfigBean("base");
        String file = AppConfigBeanTest.class.getClassLoader().getResource("appconfig/configtest001.xml").toURI().getPath();
        bean.read(file);
        
        assertEquals("test001", bean.getApplicationName());
        assertEquals("0.1", bean.getVersion());
        
        assertEquals(2, bean.getClasspath().size());
        assertEquals("test01.jar", bean.getClasspath().get(0));
        assertEquals("test02.jar", bean.getClasspath().get(1));
        
        assertEquals("com.github.tamurashingo.jalo.Test", bean.getMainClass());
        
        assertNotEquals("", bean.getApplicationDir());
    }
    
    /*-
     * construct with bootconfigbean
     */
    @Test
    public void test003() throws Exception {
        BootConfigBean bootConfig = new BootConfigBean();
        String path = new File(AppConfigBeanTest.class.getClassLoader().getResource("appconfig/test003/app.xml").toURI()).getParent();
        bootConfig.setApplicationDir(path);
        bootConfig.setBaseDir("");
        
        AppConfigBean bean = AppConfigBean.createConfig(bootConfig);
        assertEquals("test003", bean.getApplicationName());
        assertEquals("0.1", bean.getVersion());
        
        assertEquals(2, bean.getClasspath().size());
        assertEquals("test01.jar", bean.getClasspath().get(0));
        assertEquals("test02.jar", bean.getClasspath().get(1));
        
        assertEquals("com.github.tamurashingo.jalo.Test", bean.getMainClass());
        
        assertEquals(path, bean.getApplicationDir());
    }

}
