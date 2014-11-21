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
        String result = number[0];
        for(int i=1; i<number.length;i++){
            result = String.valueOf(Double.valueOf(result)+Double.valueOf(number[i]));
        }
        if(result.substring(result.indexOf(".")).length()<3){
            result = result+"0";
        }
        return result;
    }

    public static String calculateDiff(String base, String number) {
        String result = String.valueOf(Double.valueOf(base)-Double.valueOf(number));
        if(result.substring(result.indexOf(".")).length()<3){
            result = result+"0";
        }
        return result;
    }

    public static String generateDeviceId(String username) {
        return "PT" + "P" + "36" + "#" + fakeSerial(username) + "+" + "B" + "+";
    }

/*  https://confluence.playtech.corp/display/PROP/Serial+for+mobile */

    public static final int MAX_SERIAL_LENGTH = 11;

    private static String fakeSerial(String input) {
        int strHash = input.hashCode();
        String hash = Long.toString(strHash, 36);

        if (hash.length() > MAX_SERIAL_LENGTH) {
            hash = hash.substring(0, MAX_SERIAL_LENGTH);
        } else {
            while (hash.length() < MAX_SERIAL_LENGTH - 1) {
                hash = "0" + hash;
            }
        }

        return hash;
    }

    /*Balance - Currency and Amount*/

    public static String getBalanceCurrency(String balance){
        String[] currencyAndAmount = splitBalance(balance);
        if(currencyGoesFirst(currencyAndAmount)){
            return currencyAndAmount[0];
        }else {
            return currencyAndAmount[1];
        }
    }

    public static String getBalanceAmount(String balance){
        String[] currencyAndAmount = splitBalance(balance);
        if(currencyGoesFirst(currencyAndAmount)){
            return currencyAndAmount[1];
        }else {
            return currencyAndAmount[0];
        }
    }

    private static String[] splitBalance(String balance){
        return balance.split(" ");
    }

    private static boolean currencyGoesFirst(String[] currencyAndAmount){
        return currencyAndAmount[0].length()<=3;
    }
}
