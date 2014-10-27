package utils;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;


public class TypeUtils{

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

    public static String decodeBase64(String encodedString) {
        return new String(Base64.decodeBase64(encodedString));

    }

    public static <T> Collection<T> getSameElementsFromLists(Collection<T> list1, Collection<T> list2) {
        Collection<T> similar = new HashSet<T>(list1);
        similar.retainAll(list2);

        return similar;
    }

    public static <T> Collection<T> getDiffElementsFromLists(Collection<T> expected, Collection<T> actual) {
        Collection<T> different = new HashSet<T>();
        different.addAll(expected);
        different.addAll(actual);
        Collection<T> same = getSameElementsFromLists(expected, actual);
        different.removeAll(same);
        if(different.contains("")){
            different.remove("");
        }
        return different;
    }

    public static String[] splitNumbers(String string){
        return string.replace("}", "").replace("{", "").split(",");
    }

    public static String calculateSum(String... number) {
        String result = null;
        for(String s:number){
            result = String.valueOf(Double.valueOf(result)+Double.valueOf(s));
        }
        return result;
    }

    public static String calculateDiff(String base, String number) {
        return String.valueOf(Double.valueOf(base)-Double.valueOf(number));
    }
}
