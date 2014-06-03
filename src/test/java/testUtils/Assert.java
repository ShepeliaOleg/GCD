package testUtils;

import utils.WebDriverUtils;

/**
 * Created by sergiich on 6/3/14.
 */
public class  Assert extends org.testng.Assert {

    public static void assertTrueWithLogs(boolean condition, java.lang.String message){
        assertTrue(condition, message + WebDriverUtils.getUrlAndLogs());
    }

    public static void assertFalseWithLogs(boolean condition, java.lang.String message){
        assertFalse(condition, message + WebDriverUtils.getUrlAndLogs());
    }

    public static void assertTrueWithLogs(boolean condition){
        assertTrue(condition, WebDriverUtils.getUrlAndLogs());
    }

    public static void assertFalseWithLogs(boolean condition){
        assertFalse(condition, WebDriverUtils.getUrlAndLogs());
    }
}
