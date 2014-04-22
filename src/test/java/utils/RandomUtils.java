package utils;/*
 * User: ivanva
 * Date: 6/7/13
 */

import nl.flotsam.xeger.Xeger;
import org.apache.commons.codec.binary.Base64;

import java.util.*;

public class RandomUtils{
   private static String allSymbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 .@_#$/'&+()={|}~*`;!¡?¿,-%^üõöäÜÖÄßĶķŪūŽžŅņĻļĢģÀàÈèÙùËëÏïÜüŸÿÂâÊêÎîÔôÛûЫыЪъЭэЁёЬьЙй";

    public static String generateString(String characters, int length) {
        char[] text = new char[length];
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(random.nextInt(characters.length()));
        }
        return new String(text);
    }

    public static String generateStringByRegexp(String regex) {
        Xeger generator = new Xeger(regex);
        String result = generator.generate();
        assert result.matches(regex);

        return result;
    }

    public static String generateEmail(String username, String domain) {
        return username.concat(domain);
    }

    private static List<Integer> getRandomIndexes(int number, int max) {
        Random random = new Random();
        List<Integer> indexesList = new ArrayList<Integer>();
        int index;
        for (int i=0; i<number; i++) {
            do {
                index = random.nextInt(max);
            } while (indexesList.contains(index));
            indexesList.add(index);
        }
        return indexesList;
    }

    public static int generateRandomIntBetween(int min, int max) {
        Random random = new Random();

        return min + random.nextInt(max - min + 1);
    }

    private static <T> List<T> getElementsFromListByIndexes(List<T> list, List<Integer> indexes) {

        List<T> selected = new ArrayList<T>();
        final int listSize = list.size();

        for (Integer idx : indexes) {
            if (idx >= listSize) {
				WebDriverUtils.runtimeExceptionWithLogs("Index " + idx + " can't be more than " + listSize);
            }
            T elt = list.get(idx);
            selected.add(elt);
        }
        return selected;
    }

    public static <T> List<T> getRandomElementsFromList(List<T> list, int number) {
        if (number >= list.size()) {
			WebDriverUtils.runtimeExceptionWithLogs("You're requested more items than array had");
        }
        List<Integer> randomIndexes = getRandomIndexes(number, list.size());
        List<T> randomItems = getElementsFromListByIndexes(list, randomIndexes);
        return randomItems;
    }

    public static String getOption(String optionName, String optionDefaultValue) {
        String option = System.getProperty(optionName);
        if (option == null) {
            option = optionDefaultValue;
            }
        if (option == null) {
			WebDriverUtils.runtimeExceptionWithLogs(optionName + " parameter is not defined");
        }
        return option;
        }

    public static String decodeBase64(String encodedString) {
        return new String(Base64.decodeBase64(encodedString));

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
    public static <T> Collection<T> getSameElementsFromLists(Collection<T> list1, Collection<T> list2) {
        Collection<T> similar = new HashSet<T>(list1);
        similar.retainAll(list2);

        return similar;
    }

    public static <T> Collection<T> getDiffElementsFromLists(Collection<T> list1, Collection<T> list2) {
        Collection<T> different = new HashSet<T>();
        different.addAll(list1);
        different.addAll(list2);
        Collection<T> same = getSameElementsFromLists(list1, list2);
        different.removeAll(same);

        return different;
    }
}
