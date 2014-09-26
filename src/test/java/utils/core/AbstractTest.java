package utils.core;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import utils.WebDriverUtils;

import java.util.ArrayList;

@ContextConfiguration(locations={"/spring-config.xml"})
@Listeners(Listener.class)
public class AbstractTest extends AbstractTestNGSpringContextTests{

    public static ArrayList<String> results = new ArrayList<>();

	@BeforeClass(alwaysRun = true)
	protected void setUp() throws Exception{
		new WebDriverFactory().initializeWebDrivers();
	}

    @BeforeMethod(alwaysRun = true)
    protected void clean(){
        results.clear();
    }

	@AfterClass(alwaysRun = true)
	protected void tearDown() throws Exception{
		WebDriverFactory.shutDown();
	}

    protected static void validate() {
        String message = collectResults();
        if(!message.isEmpty()){
            WebDriverUtils.runtimeExceptionWithUrl(message);
        }
    }

    protected static String collectResults() {
        String message = "";
        for(String result:results){
            message += "<div>" + result + "</div>";
        }
        return message;
    }

    protected void addError(String message){
        results.add(message);
    }

    protected void failTest(String message){
        addError(message);
        validate();
    }

    protected void assertTrue(boolean actual, String message){
        if(!actual){
            addError("Should be true, but it is false. "+message);
        }
    }

    protected void validateTrue(boolean actual, String message){
        if(!actual){
            failTest("Should be true, but it is false. "+message);
        }
    }

    protected void assertFalse(boolean actual, String message){
        if(actual){
            results.add("Should be false, but it is true. "+message);
        }
    }

    protected void validateFalse(boolean actual, String message){
        assertFalse(actual, message);
        validate();
    }

    protected void assertEquals(Object expected, Object actual, String message){
        if(!expected.equals(actual)){
            results.add("Should be equal: Expected '" + expected.toString() + "', actual '" + actual.toString() + "'. " + message);
        }
    }

    protected void validateEquals(Object expected, Object actual, String message){
        assertEquals(expected, actual, message);
        validate();
    }

    protected void assertNotEquals(Object expected, Object actual, String message){
        if(expected.equals(actual)){
            results.add("Should not be equal: Expected '"+expected.toString()+"', actual '"+actual.toString()+"'. "+message);
        }
    }

    protected void validateNotEquals(Object expected, Object actual, String message){
        assertNotEquals(expected, actual, message);
        validate();
    }


}
