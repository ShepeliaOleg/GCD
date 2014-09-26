package utils.core;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.*;
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

    public static void addError(String message){
        results.add(message);
    }

    protected void failTest(String message){
        addError(message);
        validate();
    }

    public static void assertTrue(boolean actual, String message){
        if(!actual){
            addError(message + " - Expected TRUE, actual FALSE.");
        }
    }

    protected void validateTrue(boolean actual, String message){
        if(!actual){
            failTest(message + " - Expected TRUE, actual FALSE.");
        }
    }

    public static void assertFalse(boolean actual, String message){
        if(actual){
            addError(message + " - Expected FALSE, actual TRUE.");
        }
    }

    protected void validateFalse(boolean actual, String message){
        if(actual){
            failTest(message + " - Expected FALSE, actual TRUE.");
        }
    }

    public static void assertEquals(Object expected, Object actual, String message){
        if(!expected.equals(actual)){
            addError(message + " - Expected '" + expected.toString() + "', actual '" + actual.toString() + "'.");
        }
    }

    protected void validateEquals(Object expected, Object actual, String message){
        if(!expected.equals(actual)){
            failTest(message + " - Expected '" + expected.toString() + "', actual '" + actual.toString() + "'.");
        }
    }

    protected void assertNotEquals(Object expected, Object actual, String message){
        if(expected.equals(actual)){
            addError(message + " - Expected '" + expected.toString() + "' to be not equal to '" + actual.toString() + "'.");
        }
    }

    protected void validateNotEquals(Object expected, Object actual, String message){
        if(expected.equals(actual)){
            failTest(message + " - Expected '" + expected.toString() + "' to be not equal to '" + actual.toString() + "'.");
        }
    }


}
