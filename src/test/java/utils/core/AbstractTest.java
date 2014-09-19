package utils.core;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

@ContextConfiguration(locations={"/spring-config.xml"})
@Listeners(Listener.class)
public class AbstractTest extends AbstractTestNGSpringContextTests{


	@BeforeClass(alwaysRun = true)
	protected void setUp() throws Exception{
		new WebDriverFactory().initializeWebDrivers();
	}

	@AfterClass(alwaysRun = true)
	protected void tearDown() throws Exception{
		WebDriverFactory.shutDown();
	}
}
