package utils.validation;

import org.openqa.selenium.Keys;
import pageObjects.registration.RegistrationPage;
import springConstructors.ValidationRule;
import utils.RandomUtils;
import utils.WebDriverUtils;

import java.util.ArrayList;

public class ValidationUtils{

    private static final String PLACEHOLDER =     "$PLACEHOLDER$";
    private static final String NO_TOOLTIP =     "N/A";
    public static final String STATUS_PASSED = "valid";
    private static final String STATUS_FAILED = "invalid";
    public static final String STATUS_NONE = "fn-validate";
    private static final String TOOLTIP_STATUS_ERROR = "tooltip-error";
    public static final String PASSED = "Passed";
    private static final String TOOLTIP_XP = "//*[contains(@class, 'tooltip')]";
    private static final String TOOLTIP_CONTAINER_XP = "//*[@data-tooltip-owner='"+PLACEHOLDER+"']";
    private static final String TOOLTIP_LABEL_XP = TOOLTIP_CONTAINER_XP+"//span";

    private static ArrayList<String> validateClick(String xpath, ValidationRule rule, ArrayList<String> results, String tooltipID) {
        clickField(xpath);
        results.add(validationStatusIs(xpath, STATUS_NONE, ""));
        String tooltip = rule.getTooltipPositive();
        if(!tooltip.equals(NO_TOOLTIP)){
            results.add(tooltipStatusIs(tooltipID, STATUS_PASSED, ""));
            results.add(tooltipTextIs(tooltipID, tooltip , ""));
        }
        return results;
    }

    private static ArrayList<String> validateEmpty(String xpath, ValidationRule rule, ArrayList<String> results, String tooltipID) {
        inputFieldAndRefocus(xpath);
        if(rule.getIsMandatory().equals("true")){
            results = validateStatusAndToolTips(results, rule.getTooltipNegativeEmpty(), xpath, tooltipID, "", STATUS_FAILED);
        }else {
            results = validateStatusAndToolTips(results, rule.getTooltipPositive(), xpath, tooltipID, "", STATUS_PASSED);
        }
        return results;
    }

    private static ArrayList<String> validateEmptyDropdown(String xpath, ValidationRule rule, ArrayList<String> results, String tooltipID) {
        refocusDropdown(xpath);
        if(rule.getIsMandatory().equals("true")){
            results = validateStatusAndToolTips(results, rule.getTooltipNegativeEmpty(), xpath, tooltipID, "", STATUS_FAILED);
        }else{
            results = validateStatusAndToolTips(results, STATUS_NONE, xpath, tooltipID, "", STATUS_PASSED);
        }
        return results;
    }

	private static ArrayList<String> validateNotAllowedSymbols(String xpath, ValidationRule rule, ArrayList<String> results, String tooltipID) {
        String tooltip = rule.getTooltipNegativeInvalid();
        String value;
        String allNotAllowedSymbols = rule.getAllNotAllowedSymbols();
        String validationStatus="";
        String tooltipStatus="";
        String tooltipText="";
        for(char a: allNotAllowedSymbols.toCharArray()) {
            char validChar = rule.getValidChar();
            String character = String.valueOf(a);
            if(character.equals("\\")||character.equals("^")||character.equals("$")||character.equals(".")
                    ||character.equals("|")||character.equals("?")||character.equals("*")||character.equals("+")
                    ||character.equals("(")||character.equals(")")||character.equals("[")||character.equals("]")
                    ||character.equals("{")){
                character = "\\" + character;
            }
            value = rule.generateValidMinLengthUnifiedString().replaceAll("(?<="+validChar+")"+validChar+"(?="+validChar+")", character);
            inputFieldAndRefocus(xpath, value);
            if(!validationStatusIs(xpath, STATUS_FAILED, value).equals(PASSED)){
                validationStatus+=a;
            };
            if(!tooltip.equals(NO_TOOLTIP)){
                if(!tooltipStatusIs(tooltipID, STATUS_FAILED, value).equals(PASSED)){
                    tooltipStatus+=a;
                };
                if(!tooltipTextIs(tooltipID, tooltip, value).equals(PASSED)){
                    tooltipText+=a;
                };
            }
        }
        if(validationStatus.length()>0){
            results.add("For symbols = '" + validationStatus + "' validation status should be '"+STATUS_FAILED+"'");
        }
        if(tooltipStatus.length()>0){
            results.add("For symbols = '" + tooltipStatus + "' tooltip status should be '"+STATUS_FAILED+"'");
        }
        if(tooltipText.length()>0){
            results.add("For symbols = '" + tooltipText + "' tooltip text should be '"+tooltip+"'");
        }
        return results;
    }

    private static ArrayList<String> validateAllowedSymbols(String xpath, ValidationRule rule, ArrayList<String> results, String tooltipID) {
        String value;
        String allAllowedSymbols = rule.getAllAllowedSymbols();
        String validationStatus="";
        String tooltipStatus="";
        for(char a: allAllowedSymbols.toCharArray()){
            char validChar = rule.getValidChar();
            String character = String.valueOf(a);
            if(character.equals("@")&&rule.getRegexp().contains("[@]")){
                continue;
            }
            if(character.equals("\\")||character.equals("^")||character.equals("$")||character.equals(".")
                    ||character.equals("|")||character.equals("?")||character.equals("*")||character.equals("+")
                    ||character.equals("(")||character.equals(")")||character.equals("[")||character.equals("]")
                    ||character.equals("{")){
                character = "\\" + character;
            }
            value = rule.generateValidMinLengthUnifiedString().replaceAll("(?<="+validChar+")"+validChar+"(?="+validChar+")", character);
            inputFieldAndRefocus(xpath, value);
            if(!validationStatusIs(xpath, STATUS_PASSED, value).equals(PASSED)){
                validationStatus+=a;
            };
            if(!tooltipVisibilityIs(tooltipID, value, false).equals(PASSED)){
                tooltipStatus+=a;
            };
        }
        if(validationStatus.length()>0){
            results.add("For symbols = '" + validationStatus + "' validation status should be '"+STATUS_PASSED+"'");
        }
        if(tooltipStatus.length()>0){
            results.add("For symbols = '" + tooltipStatus + "' positive tooltip should not appear on click");
        }
        return results;
    }

    private static ArrayList<String> validateValidFieldInput(String xpath, ValidationRule rule, ArrayList<String> results, String tooltipID) {
        String randomAllowed = rule.generateValidString();
        inputFieldAndRefocus(xpath, randomAllowed);
        results = validateStatusAndToolTips(results, STATUS_NONE, xpath, tooltipID, randomAllowed, STATUS_PASSED);
        return results;
    }

    private static ArrayList<String> validateValidDropdownInput(String xpath, ValidationRule rule, ArrayList<String> results, String tooltipID) {
        if(rule.getRegexp().equals("DOB")){
            inputDateOfBirthAndSwitch(xpath);
            results = validateStatusAndToolTips(results, STATUS_NONE, xpath, tooltipID, "Valid Date Of Birth", STATUS_PASSED);
        }else{
            for(String value:rule.getDropdownValues()){
                inputDropdownAndRefocus(xpath, value);
                results = validateStatusAndToolTips(results, STATUS_NONE, xpath, tooltipID, value, STATUS_PASSED);
            }
        }
        return results;
    }

	private static ArrayList<String> validateTooShort(String xpath, ValidationRule rule, ArrayList<String> results, String tooltipID) {
		int min = rule.getMinLength();
		if (min < 0) {
            throw new RuntimeException("Minimum value for validation rule should be positive, actual value: " + min);
        } else if (min > 1) {
			int randomLength = RandomUtils.generateRandomIntBetween(1, min - 1);
			String randomTooShort = rule.generateValidStringWithMinSymbols().substring(0, randomLength);
            inputFieldAndRefocus(xpath, randomTooShort);
            results = validateStatusAndToolTips(results, rule.getTooltipNegativeShort(), xpath, tooltipID, randomTooShort, STATUS_FAILED);
        }else {
            results.add(PASSED);
        }
        return results;
	}

	private static ArrayList<String> validateTooLong(String xpath, ValidationRule rule, ArrayList<String> results, String tooltipID) {
		int max = rule.getMaxLength();
		if (max <= 0) {
			throw new RuntimeException("Maximum value for validation rule should be larger than 0, actual value: " + max);
		}else {
            String randomTooLong = rule.generateValidStringOverMaxSymbols();
            inputFieldAndRefocus(xpath, randomTooLong);
            results = validateStatusAndToolTips(results, rule.getTooltipNegativeLong(), xpath, tooltipID, randomTooLong, STATUS_FAILED);
        }
        return results;
	}

    public static ArrayList<String> validateStatusAndToolTips(ArrayList<String> results, String tooltip, String xpath, String tooltipID, String value, String status ){
        results.add(validationStatusIs(xpath, status, value));
        if(tooltip.equals(STATUS_NONE)){
            results.add(tooltipVisibilityIs(tooltipID, value, false));
        }else if(!tooltip.equals(NO_TOOLTIP)){
            results.add(tooltipStatusIs(tooltipID, status, value));
            results.add(tooltipTextIs(tooltipID, tooltip, value));
        }
        return results;
    }

	public static String validationStatusIs(String xpath, String expectedStatus, String value) {
		String actualStatus = getValidationStatus(xpath);
		if (!actualStatus.equals(expectedStatus)) {
			return ("Value = '" + value + "' should be '" + expectedStatus + "', but it is '" + actualStatus + "'");
		} else {
            return PASSED;
        }
	}

    public static String tooltipStatusIs(String id, String expectedStatus, String value) {
        if(getTooltipStatus(id).contains(TOOLTIP_STATUS_ERROR)){
            if(expectedStatus.equals(STATUS_PASSED)){
                return "Value = '" + value + "' tooltip should be positive, but it is negative";
            }else {
                return PASSED;
            }
        }else {
            if(expectedStatus.equals(STATUS_PASSED)){
                return PASSED;
            }else {
                return "Value = '" + value + "' tooltip should be negative, but it is positive";
            }
        }
    }

    public static String tooltipTextIs(String id, String expectedText, String value) {
        String actualText = getTooltipText(id);
        if(actualText.equals(expectedText)){
            return PASSED;
        }else {
            return "Value = '" + value + "' tooltip should be '"+expectedText+"', but it is '"+actualText+"'";
        }
    }

    private static String getTooltipStatus(String id) {
        String classValue = WebDriverUtils.getAttribute(TOOLTIP_CONTAINER_XP.replace(PLACEHOLDER, id), "class");
        String[] classParametes = classValue.split(" ");
        return classParametes[0];
    }

    private static String getTooltipText(String id) {
        return WebDriverUtils.getElementText(TOOLTIP_LABEL_XP.replace(PLACEHOLDER, id));
    }

    private static String tooltipVisibilityIs(String id, String value, boolean expectedStatus) {
        boolean actualStatus = isTooltipVisible(id);
        if(!actualStatus==expectedStatus){
            return "Value = '" + value + "' tooltip visibility should be '"+expectedStatus+"', but it is '"+actualStatus+"'";
        }else {
            return PASSED;
        }
    }

    private static boolean isTooltipVisible(String id) {
        return WebDriverUtils.isVisible(TOOLTIP_LABEL_XP.replace(PLACEHOLDER, id), 0);
    }

	private static String getValidationStatus(String xpath) {
        String classValue;
        if(xpath.equals(RegistrationPage.DROPDOWN_BIRTHDAY_XP)){
            classValue = WebDriverUtils.getAttribute(xpath + "/../../..", "class");
        }else {
            classValue = WebDriverUtils.getAttribute(xpath + "/..", "class");
        }
		String[] classParametes = classValue.split(" ");
		return classParametes[classParametes.length-1];
	}

    public static void inputFieldAndRefocus(String xpath, String input){
        if(xpath.contains(RegistrationPage.FIELD_PHONE_COUNTRY_CODE_XP)){
            WebDriverUtils.clearAndInputTextToField(RegistrationPage.FIELD_PHONE_XP, "111111");
        }else if(xpath.contains(RegistrationPage.FIELD_PHONE_XP)){
            WebDriverUtils.clearAndInputTextToField(RegistrationPage.FIELD_PHONE_COUNTRY_CODE_XP, "+111");
        }
        WebDriverUtils.clearAndInputTextToField(xpath, input);
        WebDriverUtils.pressKey(Keys.TAB);
        clickField(xpath);
    }

    private static void inputFieldAndRefocus(String xpath){
        inputFieldAndRefocus(xpath, " ");
    }

    private static void clickField(String xpath){
        WebDriverUtils.click(xpath);
    }

    private static void inputDropdownAndRefocus(String xpath, String value){
        WebDriverUtils.setDropdownOptionByValue(xpath, value);
        refocusDropdown(xpath);
    }

    private static void inputDateOfBirthAndSwitch(String xpath){
        WebDriverUtils.setDropdownOptionByValue(RegistrationPage.DROPDOWN_BIRTHDAY_XP, "01");
        WebDriverUtils.setDropdownOptionByValue(RegistrationPage.DROPDOWN_BIRTHMONTH_XP, "01");
        WebDriverUtils.setDropdownOptionByValue(RegistrationPage.DROPDOWN_BIRTHYEAR_XP, "1980");
        refocusDropdown(xpath);
    }

    private static void refocusDropdown(String xpath){
        WebDriverUtils.pressKey(Keys.TAB);
        //this is not a typo, tab has to be clicked twice
        WebDriverUtils.pressKey(Keys.TAB);
        clickField(xpath);
    }

	public static void validateField(String xpath, ValidationRule rule, String tooltipID) {
        ArrayList<String> results = new ArrayList();
        String message = "";
        results = validateClick(xpath, rule, results, tooltipID);
        results = validateValidFieldInput(xpath, rule, results, tooltipID);
        results = validateEmpty(xpath, rule,results, tooltipID);
        results = validateNotAllowedSymbols(xpath, rule,results, tooltipID);
        results = validateAllowedSymbols(xpath, rule,results, tooltipID);
        results = validateTooShort(xpath, rule,results, tooltipID);
        results = validateTooLong(xpath, rule,results,tooltipID);
        for(String result:results){
            if(!result.equals(PASSED)){
                message += "<div>" + result + "</div>";
            }
        }
        if(!message.isEmpty()){
            WebDriverUtils.runtimeExceptionWithLogs(message);
        }
	}

    public static void validateDropdown(String xpath, ValidationRule rule, String tooltipID) {
        ArrayList<String> results = new ArrayList();
        String message = "";
        results = validateClick(xpath, rule, results, tooltipID);
        results = validateEmptyDropdown(xpath, rule,results, tooltipID);
        results = validateValidDropdownInput(xpath, rule, results, tooltipID);
        for(String result:results){
            if(!result.equals(PASSED)){
                message += "<div>" + result + "</div>";
            }
        }
        if(!message.isEmpty()){
            WebDriverUtils.runtimeExceptionWithLogs(message);
        }
    }

    public static ArrayList<RegexNode> splitToNodes(String regex){
        String tempRegExp = regex;
        ArrayList<RegexNode> nodes = new ArrayList<>();
        while(tempRegExp.indexOf("[")!=-1){
            int end = tempRegExp.indexOf("}")+1;
            if(tempRegExp.contains("}]")){
                end = tempRegExp.indexOf("}", end)+1;
            }
            nodes.add(new RegexNode(tempRegExp.substring(tempRegExp.indexOf("["), end)));
            tempRegExp=tempRegExp.substring(end);
        }
        return nodes;
    }

    public static String generateRegExpFromNodes(ArrayList<RegexNode> nodes) {
        String result = "";
        for(RegexNode node:nodes){
            result+=node.toString();
        }
        return result;
    }
}
