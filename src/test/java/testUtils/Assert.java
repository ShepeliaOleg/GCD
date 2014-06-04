package testUtils;

import utils.WebDriverUtils;

/**
 * Created by sergiich on 6/3/14.
 */
public class  Assert extends org.testng.Assert {

    public static void assertTrueWithLogs(boolean condition, java.lang.String message){
        try{
            assertTrue(condition, message);
        }catch (AssertionError e){
            WebDriverUtils.runtimeExceptionWithLogs(e.getMessage());
        }
    }

    public static void assertFalseWithLogs(boolean condition, java.lang.String message){
        try{
            assertFalse(condition, message);
        }catch (AssertionError e){
            WebDriverUtils.runtimeExceptionWithLogs(e.getMessage());
        }
    }

    public static void assertTrueWithLogs(boolean condition){
        try{
            assertTrue(condition);
        }catch (AssertionError e){
            WebDriverUtils.runtimeExceptionWithLogs(e.getMessage());
        }
    }

    public static void assertFalseWithLogs(boolean condition){
        try{
            assertFalse(condition);
        }catch (AssertionError e){
            WebDriverUtils.runtimeExceptionWithLogs(e.getMessage());
        }
    }
}
