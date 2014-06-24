package utils;

import org.openqa.selenium.Keys;
import springConstructors.ValidationRule;

public class ValidationUtils{

	private static void validateNotAllowedSymbolsDeleted(String xpath, ValidationRule rule) {
		String allNotAllowedSymbols = rule.getAllNotAllowedSymbols();
		String pre = WebDriverUtils.getInputFieldText(xpath);
		WebDriverUtils.inputTextToField(xpath, allNotAllowedSymbols);
		String post = WebDriverUtils.getInputFieldText(xpath);
		if (!post.equals(pre)) {
			WebDriverUtils.runtimeExceptionWithLogs("According to validation rule, entered string with all not allowed symbols: \"" + allNotAllowedSymbols + "\" in addition to: \"" + pre + "\" should be completely deleted, but some symbols stayed unchanged: \""+ post + "\"");
		}
	}

	private static void validateAllowedSymbolsNotDeleted(String xpath, ValidationRule rule) {
		String randomAllowed = rule.generateValidString();
		WebDriverUtils.clearAndInputTextToField(xpath, randomAllowed);
		String out = WebDriverUtils.getInputFieldText(xpath);
		if (!out.equals(randomAllowed)) {
			WebDriverUtils.runtimeExceptionWithLogs("According to validation rule, entered string generated from allowed symbols: \"" + randomAllowed + "\" should stay unchanged, but it was modified to: \""+ out + "\"");
		}
	}

	private static void validateTooShort(String xpath, ValidationRule rule) {
		int min = rule.getMinLength();

		if (min < 0) {
			WebDriverUtils.runtimeExceptionWithLogs("Minimum value for validation rule should be positive, actual value: " + min);
		}

		if (min > 1) {
			int     randomLength    = RandomUtils.generateRandomIntBetween(1, min-1);
			String  randomAllowed   = rule.generateValidString();
			String randomTooShort = randomAllowed.substring(0, randomLength);
			WebDriverUtils.clearAndInputTextToField(xpath, randomTooShort);
			validationStatusIs(xpath, "failed", randomTooShort);
		}

	}

	private static void validateTooLong(String xpath, ValidationRule rule) {
		int max = rule.getMaxLength();

		if (max < 0) {
			WebDriverUtils.runtimeExceptionWithLogs("Maximum value for validation rule should be positive, actual value: " + max);
		}

		if (max > 0) {
			String  randomTooLong   = rule.generateValidString(max);

			WebDriverUtils.clearAndInputTextToField(xpath, randomTooLong);

			String post = WebDriverUtils.getInputFieldText(xpath);

			if (!post.equals(randomTooLong.substring(0, max))) {
				WebDriverUtils.runtimeExceptionWithLogs("According to validation rule, entered string generated from allowed symbols: \"" + randomTooLong + "\" should be cut to allowed max length: \""+ max + "\" but actual length is \"" + post.length() + "\"");
			}

			validationStatusIs(xpath, "passed", post);

		}

	}

	private static void validationStatusIs(String xpath, String expectedStatus, String value) {
		WebDriverUtils.pressKey(Keys.TAB);
		String actualStatus = getValidationStatus(xpath);
		if (!actualStatus.equals(expectedStatus)) {
			WebDriverUtils.runtimeExceptionWithLogs(xpath + "input field with value \"" + value + "\" should be marked as \"" + expectedStatus + "\" but it marked as \"" + actualStatus + "\"");
		}
	}

	private static String getValidationStatus(String xpath) {
		String classValue = WebDriverUtils.getAttribute(xpath + "/..", "class");
		if (classValue.endsWith("checked")){
			return "passed";
		} else if (classValue.endsWith("error")){
			return "failed";
		} else if (classValue.equals("empty")){
			return "none";
		} else{
			WebDriverUtils.runtimeExceptionWithLogs("@class of validated WebElement by \"" + xpath + "\" has unexpected value: \"" + classValue + "\" ");
		}
		return null;
	}

	public static void validate(String xpath, ValidationRule rule) {
        /* negative */
		validateNotAllowedSymbolsDeleted(xpath, rule);
		validateTooShort(xpath, rule); // too short
		validateTooLong(xpath, rule); // too long
        /* positive */
		validateAllowedSymbolsNotDeleted(xpath, rule);
	}
}
