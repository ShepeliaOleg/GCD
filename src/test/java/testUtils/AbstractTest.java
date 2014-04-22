package testUtils;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import utils.core.WebDriverFactory;

/**
 * User: sergiich
 * Date: 4/10/14
 */
@ContextConfiguration(locations={"../spring-config.xml"})
@Listeners(Listener.class)
public class AbstractTest extends AbstractTestNGSpringContextTests{


	@BeforeClass(alwaysRun = true)
	protected void setUp() throws Exception{
		new WebDriverFactory().initializeWebDriver();
	}

	@AfterClass(alwaysRun = true)
	protected void tearDown() throws Exception{
		WebDriverFactory.shutDown();
	}
}
