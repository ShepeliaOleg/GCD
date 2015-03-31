package utils.core;

import enums.PlatForm;
import org.testng.SkipException;
import utils.TypeUtils;
import utils.WebDriverUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AbstractTest extends AbstractTestRunner{

    protected static String INVALID_BONUS_CODE_MESSAGE = "Coupon code is not found or not available";

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
        results.add(message+getScreenshot());
    }

    private static String getScreenshot(){
        String fileName = name + String.valueOf(counter++);
        String[] names = Listener.createScreenshot(fileName);
        return "(<a href='"+names[0]+"' target='_blank'>P</a>/<a href='"+names[1]+"' target='_blank'>L</a>/<a href='"+WebDriverUtils.getCurrentUrl()+"' target='_blank'>URL</a>)";
    }

    public static void failTest(String message){
        addError(message);
        validate();
    }

    private static void skipTest(String message, boolean getScreenshot){
        String results = collectResults();
        String details = "";
        if (getScreenshot) {
            details = getScreenshot();
        }
        if(!results.isEmpty()){
            throw new SkipException(message + details + "Errors found: " + results);
        }else {
            throw new SkipException(message + details);
        }
    }

    public static void skipTest() {
        skipTest("");
    }

    public static void skipTest(String message){
        skipTest(message, true);
    }

    public static void skipTestWithIssues(String issues){
        List<String> issuesList = new ArrayList<>();
        if (issues.contains(",")) {
            issuesList = TypeUtils.splitBy(issues, ",");
        }
        if (issuesList.isEmpty()) {
            issues = getIssueLink(issues);
        } else {
            issues = "";
            for (String issue:issuesList) {
                issues += getIssueLink(issue.trim());
            }

        }

        skipTest(issues, false);
    }

    private static String getIssueLink(String issueId) {
        if (issueId.startsWith("D-")) {
            return issueId;
        } else return getJiraIssueLink(issueId);
    }

    private static String getJiraIssueLink(String issueId) {
        return "<a href='https://portal-jira.playtech.corp/browse/" + issueId + "' target='_blank'>" + issueId + "</a> ";
    }

    public static void skipTestWithIssues(PlatForm platform, String issues){
        if (platform.equals(WebDriverFactory.getPlatform())) {
            skipTestWithIssues(issues);
        }
    }

    public static boolean assertTrue(boolean actual, String message){
        return addErrorIf(!actual, "TRUE", "FALSE", message);
    }

    public static boolean assertUrl(String expected, String message){
//        return assertEquals(expected, WebDriverUtils.getCurrentUrl().replace(WebDriverUtils.getCurrentUrl(), ""), message);
        return assertTrue(WebDriverUtils.getCurrentUrl().endsWith(expected), message);
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
        return assertTrue(WebDriverUtils.isTextVisible(text), message);
    }

    public static boolean assertTextVisible(String text, String message, int timeOutInSeconds){
        return assertTrue(WebDriverUtils.isTextVisible(WebDriverFactory.getPortalDriver(), text, timeOutInSeconds), message);
    }

    public static boolean assertTextInvisible(String text, String message){
        return assertFalse(WebDriverUtils.isTextVisible(text), message);
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

    public String getСurrencySymbol(String currencyKey){
        for(Object el: DataContainer.getDefaults().getCurrencyList()){
            String line = (String ) el;
            if (line.contains(currencyKey)) {
                return line.split("@")[1];
            }
        }
        return null;
    }
}
