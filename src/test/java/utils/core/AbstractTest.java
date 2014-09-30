package utils.core;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.*;
import utils.TypeUtils;
import utils.WebDriverUtils;

import java.util.ArrayList;
import java.util.Collection;

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

    public static boolean assertTrue(boolean actual, String message){
        return addErrorIf(!actual, "TRUE", "FALSE", message);
    }

    protected void validateTrue(boolean actual, String message){
        if(assertTrue(actual, message)){
            validate();
        }
    }

    public static boolean assertFalse(boolean actual, String message){
        return addErrorIf(actual, "FALSE", "TRUE", message);
    }

    protected void validateFalse(boolean actual, String message){
        if(assertFalse(actual, message)){
            validate();
        }
    }

    public static boolean assertEquals(Object expected, Object actual, String message){
        return addErrorIf(!equals(expected, actual), expected, actual, message);
    }

    protected void validateEquals(Object expected, Object actual, String message){
        if(assertEquals(expected, actual, message)){
            validate();
        }
    }

    protected boolean assertNotEquals(Object expected, Object actual, String message){
        return addErrorIf(equals(expected, actual), expected, actual, message);
    }

    protected void validateNotEquals(Object expected, Object actual, String message){
        if(assertNotEquals(expected, actual, message)){
            validate();
        }
    }

    public static boolean assertEqualsCollections(Collection expected, Collection actual, String message){
        Collection diff = TypeUtils.getDiffElementsFromLists(expected, actual);
        return addErrorIf(!diff.isEmpty(), expected, actual, "(Diff: " + diff.toString() + ") "+message);
    }

    public static void validateEqualsCollections(Collection expected, Collection actual, String message){
        if(assertEqualsCollections(expected, actual, message)){
            validate();
        }
    }

    private static boolean equals(Object expected, Object actual) {
        return expected.equals(actual);
    }

    private static boolean addErrorIf(boolean condition, Object expected, Object actual, String message) {
        if(condition){
            addError(message + " - Expected '" + expected.toString() + "', Actual '" + actual.toString() + "'.");
            return true;
        }else {
            return false;
        }
    }

}
