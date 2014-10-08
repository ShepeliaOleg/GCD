package utils.core;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import utils.RandomUtils;
import utils.TypeUtils;
import utils.WebDriverUtils;
import java.util.ArrayList;
import java.util.Collection;

@ContextConfiguration(locations={"/spring-config.xml"})
@Listeners(Listener.class)
public class AbstractTest extends AbstractTestNGSpringContextTests{

    public static ArrayList<String> results = new ArrayList<>();
    private static int counter;
    private static String name;

	@BeforeClass(alwaysRun = true)
	protected void setUp() throws Exception{
		new WebDriverFactory().initializeWebDrivers();
	}

    @BeforeMethod(alwaysRun = true)
    protected void clean(){
        results.clear();
        counter = 0;
        name = RandomUtils.generateString("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", 15);
    }

	@AfterClass(alwaysRun = true)
	protected void tearDown() throws Exception{
		WebDriverFactory.shutDown();
	}

    protected static void validate() {
        String message = collectResults();
        if(!message.isEmpty()){
            throw new RuntimeException(message);
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
        String fileName = name + String.valueOf(counter++);
        String[] names = Listener.createScreenshot(fileName);
        results.add(message+"(<a href='"+names[0]+"'>P</a>/<a href='"+names[1]+"'>L</a>/<a href='"+WebDriverUtils.getCurrentUrl()+"'>URL</a>)");
    }

    public static void failTest(String message){
        addError(message);
        validate();
    }

    public static void skipTest(String message){
        validate();
        throw new SkipException(message + "<a href='"+WebDriverUtils.getCurrentUrl()+"'>URL</a>");
    }

    public static boolean assertTrue(boolean actual, String message){
        return addErrorIf(!actual, "TRUE", "FALSE", message);
    }

    public static void validateTrue(boolean actual, String message){
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

    protected static void validateEquals(Object expected, Object actual, String message){
        if(assertEquals(expected, actual, message)){
            validate();
        }
    }

    protected static boolean assertNotEquals(Object expected, Object actual, String message){
        return addErrorIf(equals(expected, actual), expected, actual, message);
    }

    public static void validateNotEquals(Object expected, Object actual, String message){
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

    public static boolean assertTextVisible(String text, String message){
        return addErrorIf(!WebDriverUtils.isTextVisible(text), "TRUE", "FALSE", message);
    }

    public static boolean assertTextInvisible(String text, String message){
        return addErrorIf(WebDriverUtils.isTextVisible(text), "TRUE", "FALSE", message);
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
