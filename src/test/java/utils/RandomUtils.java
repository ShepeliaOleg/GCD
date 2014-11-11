package utils;

import enums.PaymentMethod;
import org.apache.commons.lang3.RandomStringUtils;
import utils.core.AbstractTest;
import utils.core.WebDriverFactory;
import utils.validation.RegexNode;
import utils.validation.ValidationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomUtils{
   private static String allSymbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 .@_#$/'&+()={|}~*`;!¡?¿,-%^üõöÖÄß";

    public static String generateString(String characters, int length) {
        char[] text = new char[length];
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(random.nextInt(characters.length()));
        }
        return new String(text);
    }

//    public static String generateStringByRegexp(String regex) {
//        Xeger generator = new Xeger(regex);
//        String result = generator.generate();
//        assert result.matches(regex);
//        return result;
//    }

    public static String generateStringByRegexp(String regex) {
        String result = "";
        int min=0;
        int max=0;
        int length=0;
        String symbols="";
        RegexNode tempNode=null;
        try{
            for(RegexNode node: ValidationUtils.splitToNodes(regex)){
                tempNode = node;
                min = node.getMin();
                max = node.getMax();
                length = generateRandomIntBetween(min, max);
                symbols = node.getSymbols();
                result+=RandomStringUtils.random(length, symbols);
            }
        }catch (ArrayIndexOutOfBoundsException e){
            AbstractTest.failTest("Failed to generate by rule '"+regex+"'," +
                    " failed on '"+tempNode+"': min '"+min+"'," +
                    " max '"+max+"', length '"+length+"'," +
                    " symbols '"+symbols+"'");
        }
        return result;
    }

    public static String generateEmail(String username, String domain) {
        return username.concat(domain);
    }

	private static List<Integer> getRandomIndexes(int number, int max){
		return getRandomIndexes(number, max, 0);
	}

    private static List<Integer> getRandomIndexes(int number, int max, int offset) {
		Random random = new Random();
		List<Integer> indexesList = new ArrayList<>();
		int index;
		for (int i=0; i<number; i++) {
			do{
				index = random.nextInt(max-offset)+offset;
			}while (indexesList.contains(index));
			indexesList.add(index);
		}
		return indexesList;
	}

	public static <T> List<T> getRandomElementsFromList(List<T> list, int number) {
		return getRandomElementsFromList(list, number, 0);
	}

	public static <T> List<T> getRandomElementsFromList(List<T> list, int number, int offset) {
		if (number >= list.size()) {
            AbstractTest.failTest("You're requested more items than array had");
		}
		List<Integer> randomIndexes = getRandomIndexes(number, list.size(),offset);
		List<T> randomItems = getElementsFromListByIndexes(list, randomIndexes);
		return randomItems;
	}

    public static int generateRandomIntBetween(int min, int max) {
        if(max==0){
            return 0;
        }else if(max==min){
            return max;
        }else {
            Random random = new Random();
            return min + random.nextInt(max - min);
        }
    }

    private static <T> List<T> getElementsFromListByIndexes(List<T> list, List<Integer> indexes) {
        List<T> selected = new ArrayList<T>();
        final int listSize = list.size();
        for (Integer idx : indexes) {
            if (idx >= listSize) {
                AbstractTest.failTest("Index " + idx + " can't be more than " + listSize);
            }
            T elt = list.get(idx);
            selected.add(elt);
        }
        return selected;
    }

    public static String getOption(String optionName, String optionDefaultValue) {
        String option = System.getProperty(optionName);
        if (option == null) {
            option = optionDefaultValue;
            }
        if (option == null) {
            AbstractTest.failTest(optionName + " parameter is not defined");
        }
        return option;
        }


    public static String getAllExcept(String substring) {
        String result = allSymbols;
        for (Character c : substring.toCharArray()) {
            if (result.contains(c.toString())) {
                result = result.replace(c.toString(), "");
            }
        }

        return result;
    }

    public static String getValidCardNumber(PaymentMethod paymentMethod){
        String card = "mastercard";
        if(paymentMethod.equals(PaymentMethod.Visa)){
            card="visa";
        }
        WebDriverFactory.switchToAdditionalWebDriver();
        WebDriverUtils.navigateToURL("http://www.getcreditcardnumbers.com/");
        WebDriverUtils.waitForElement("//*[@action='/generated-credit-card-numbers']");
        String result = WebDriverUtils.getElementText("//*[contains(@class, 'visa')]/li");
        WebDriverFactory.switchToMainWebDriver();
        return result;
    }
}
