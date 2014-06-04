package utils;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;


public class TypeUtils extends Assert {

    private static int getIntegerValueFromString(String string){
        int value;
        String text=string.replaceAll("\\s", "");
        if(text.matches("[0-9]+")){
            value=Integer.parseInt(text); // NumberFormatException
        }else{
            value=0;
        }
        return value;
    }

    public static List<Integer> convertWebElementListToIntegerList(List<WebElement> webElementList) {

        List<Integer> integerList=new ArrayList<Integer>();

        for (int i = 0; i < webElementList.size(); i++) {
            integerList.add(getIntegerValueFromString(webElementList.get(i).getText()));
        }

        return integerList;
    }

    public static List<String> convertIntegerListToStringList(List<Integer> integerList) {

        List<String> stringList=new ArrayList<String>();

        for (int i = 0; i < integerList.size(); i++) {
            stringList.add(String.valueOf(integerList.get(i)));
        }

        return stringList;
    }

    public static boolean listAContainsListB(List<String> listA, List<String> listB){
        return listA.containsAll(listB);
    }

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
