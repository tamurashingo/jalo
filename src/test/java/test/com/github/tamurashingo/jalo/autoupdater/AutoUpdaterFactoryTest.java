package test.com.github.tamurashingo.jalo.autoupdater;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.tamurashingo.jalo.JaloException;
import com.github.tamurashingo.jalo.autoupdater.AutoUpdater;
import com.github.tamurashingo.jalo.autoupdater.AutoUpdaterFactory;
import com.github.tamurashingo.jalo.xml.BootConfigBean;

public class AutoUpdaterFactoryTest {

	@Test
	public void testHttpAutoUpdater() throws Exception {
		BootConfigBean bootConfig = new BootConfigBean();
		bootConfig.setUpdateClass("com.github.tamurashingo.jalo.autoupdater.impl.HttpAutoUpdater");
		AutoUpdater updater = AutoUpdaterFactory.create(bootConfig);
		
		assertNotNull(updater);
	}

	@Test
	public void testFileAutoUpdater() throws Exception {
		BootConfigBean bootConfig = new BootConfigBean();
		bootConfig.setUpdateClass("com.github.tamurashingo.jalo.autoupdater.impl.FileAutoUpdater");
		AutoUpdater updater = AutoUpdaterFactory.create(bootConfig);
		
		assertNotNull(updater);
	}
	
	@Test
	public void testNotFoundClass() {
		BootConfigBean bootConfig = new BootConfigBean();
		bootConfig.setUpdateClass("not.found.class");
		try {
			AutoUpdaterFactory.create(bootConfig);
			fail("do not reach.");
		}
		catch (JaloException ex) {
			// ok
			ex.printStackTrace();
		}
	}
	
	@Test
	public void testNotAutoUpdaterInterface() {
		BootConfigBean bootConfig = new BootConfigBean();
		bootConfig.setUpdateClass("com.github.tamurashingo.jalo.xml.BootConfigBean");
		try {
			AutoUpdaterFactory.create(bootConfig);
			fail("do not reach.");
		}
		catch (JaloException ex) {
			// ok
			ex.printStackTrace();
		}
	}
}
