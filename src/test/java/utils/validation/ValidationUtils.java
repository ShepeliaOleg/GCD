package utils.validation;

import org.openqa.selenium.Keys;
import springConstructors.ValidationRule;
import utils.RandomUtils;
import utils.WebDriverUtils;

import java.util.ArrayList;

public class ValidationUtils{

    private static final String PLACEHOLDER =     "$PLACEHOLDER$";
    private static final String NO_TOOLTIP =     "N/A";
    private static final String STATUS_PASSED = "valid";
    private static final String STATUS_FAILED = "invalid";
    private static final String STATUS_NONE = "fn-validate";
    private static final String TOOLTIP_STATUS_ERROR = "tooltip-error";
    private static final String PASSED = "Passed";
    private static final String TOOLTIP_XP = "//*[contains(@class, 'tooltip')]";
    private static final String TOOLTIP_CONTAINER_XP = "//*[@data-tooltip-owner='"+PLACEHOLDER+"']";
    private static final String TOOLTIP_LABEL_XP = TOOLTIP_CONTAINER_XP+"//span";

    private static ArrayList<String> validateClick(String xpath, ValidationRule rule, ArrayList<String> results, String tooltipID) {
        clickAndWait(xpath);
        results.add(validationStatusIs(xpath, STATUS_NONE, ""));
        String tooltip = rule.getTooltipPositive();
        if(!tooltip.equals(NO_TOOLTIP)){
            results.add(tooltipStatusIs(tooltipID, STATUS_PASSED, ""));
            results.add(tooltipTextIs(tooltipID, tooltip , ""));
        }
        return results;
    }

    private static ArrayList<String> validateEmpty(String xpath, ValidationRule rule, ArrayList<String> results, String tooltipID) {
        inputAndSwitch(xpath);
        if(rule.getIsMandatory().equals("true")){
            results = validateStatusAndToolTips(results, rule.getTooltipNegativeEmpty(), xpath, tooltipID, "", STATUS_FAILED);
        }else {
            results = validateStatusAndToolTips(results, rule.getTooltipPositive(), xpath, tooltipID, "", STATUS_PASSED);
        }
        return results;
    }

    private static ArrayList<String> validateEmptyDropdown(String xpath, ValidationRule rule, ArrayList<String> results, String tooltipID) {
        switchToDropdown(xpath);
        if(rule.getIsMandatory().equals("true")){
            results = validateStatusAndToolTips(results, rule.getTooltipNegativeEmpty(), xpath, tooltipID, "", STATUS_FAILED);
        }else{
            results = validateStatusAndToolTips(results, rule.getTooltipPositive(), xpath, tooltipID, "", STATUS_PASSED);
        }
        return results;
    }

	private static ArrayList<String> validateNotAllowedSymbols(String xpath, ValidationRule rule, ArrayList<String> results, String tooltipID) {
        String value;
        String allNotAllowedSymbols = rule.getAllNotAllowedSymbols();
        for(char a: allNotAllowedSymbols.toCharArray()) {
            value = "";
            for (int i = 0; i < rule.getMinLength(); i++) {
                value += a;
            }
            inputAndSwitch(xpath, value);
            results = validateStatusAndToolTips(results, rule.getTooltipNegativeInvalid(), xpath, tooltipID, value, STATUS_FAILED);
        }
        return results;
    }

    private static ArrayList<String> validateNotAllowedDropdown(String xpath, ValidationRule rule, ArrayList<String> results, String tooltipID) {
        String value;
        String allNotAllowedSymbols = rule.getAllNotAllowedSymbols();
        for(char a: allNotAllowedSymbols.toCharArray()) {
            value = "";
            for (int i = 0; i < rule.getMinLength(); i++) {
                value += a;
            }
            inputAndSwitch(xpath, value);
            results = validateStatusAndToolTips(results, rule.getTooltipNegativeInvalid(), xpath, tooltipID, value, STATUS_FAILED);
        }
        return results;
    }

    private static ArrayList<String> validateAllowedSymbols(String xpath, ValidationRule rule, ArrayList<String> results, String tooltipID) {
        String value;
        String allAllowedSymbols = rule.getAllAllowedSymbols();
        for(char a: allAllowedSymbols.toCharArray()){
            value="";
            for(int i=0; i<rule.getMinLength();i++){
                value+=a;
            }
            inputAndSwitch(xpath, value);
            results = validateStatusAndToolTips(results, rule.getTooltipPositive(), xpath, tooltipID, value, STATUS_PASSED);
        }
        return results;
    }

    private static ArrayList<String> validateValidFieldInput(String xpath, ValidationRule rule, ArrayList<String> results, String tooltipID) {
        String randomAllowed = rule.generateValidString();
        inputAndSwitch(xpath, randomAllowed);
        results = validateStatusAndToolTips(results, rule.getTooltipPositive(), xpath, tooltipID, randomAllowed, STATUS_PASSED);
        return results;
    }

    private static ArrayList<String> validateValidDropdownInput(String xpath, ValidationRule rule, ArrayList<String> results, String tooltipID) {
        for(String value:rule.getDropdownValues()){
            inputDropdownAndSwitch(xpath, value);
            results = validateStatusAndToolTips(results, rule.getTooltipPositive(), xpath, tooltipID, value, STATUS_PASSED);
        }
        return results;
    }

	private static ArrayList<String> validateTooShort(String xpath, ValidationRule rule, ArrayList<String> results, String tooltipID) {
		int min = rule.getMinLength();
		if (min < 0) {
            throw new RuntimeException("Minimum value for validation rule should be positive, actual value: " + min);
        } else if (min > 1) {
			int randomLength = RandomUtils.generateRandomIntBetween(1, min - 1);
			String randomTooShort = rule.generateValidString().substring(0, randomLength);
            inputAndSwitch(xpath, randomTooShort);
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
            inputAndSwitch(xpath, randomTooLong);
            results = validateStatusAndToolTips(results, rule.getTooltipNegativeLong(), xpath, tooltipID, randomTooLong, STATUS_FAILED);
        }
        return results;
	}

    private static ArrayList<String> validateStatusAndToolTips(ArrayList<String> results, String tooltip, String xpath, String tooltipID, String value, String status ){
        results.add(validationStatusIs(xpath, status, value));
        if(!tooltip.equals(NO_TOOLTIP)){
            results.add(tooltipStatusIs(tooltipID, status, value));
            results.add(tooltipTextIs(tooltipID, tooltip, value));
        }
        return results;
    }

	private static String validationStatusIs(String xpath, String expectedStatus, String value) {
		String actualStatus = getValidationStatus(xpath);
		if (!actualStatus.equals(expectedStatus)) {
			return (xpath + " input field with value '" + value + "' should be marked as '" + expectedStatus + "' but it marked as '" + actualStatus + "'");
		} else {
            return PASSED;
        }
	}

    private static String tooltipStatusIs(String id, String expectedStatus, String value) {
        if(getTooltipStatus(id).contains(TOOLTIP_STATUS_ERROR)){
            if(expectedStatus.equals(STATUS_PASSED)){
                return "For input field '"+ id + "' with value '" + value + "' tooltip should be positive, but it is negative";
            }else {
                return PASSED;
            }
        }else {
            if(expectedStatus.equals(STATUS_PASSED)){
                return PASSED;
            }else {
                return "For input field '"+ id + "' with value '" + value + "' tooltip should be negative, but it is positive";
            }
        }
    }

    private static String tooltipTextIs(String id, String expectedText, String value) {
        String actualText = getTooltipText(id);
        if(actualText.equals(expectedText)){
            return PASSED;
        }else {
            return "For input field '"+ id + "' with value '" + value + "' tooltip should be '"+expectedText+"', but it is '"+actualText+"'";
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

	private static String getValidationStatus(String xpath) {
		String classValue = WebDriverUtils.getAttribute(xpath + "/..", "class");
		String[] classParametes = classValue.split(" ");
		return classParametes[classParametes.length-1];
	}

    private static void inputAndSwitch(String xpath, String input){
        WebDriverUtils.clearAndInputTextToField(xpath, input);
        WebDriverUtils.pressKey(Keys.TAB);
        clickAndWait(xpath);
    }

    private static void inputAndSwitch(String xpath){
        inputAndSwitch(xpath, " ");
    }

    private static void clickAndWait(String xpath){
        WebDriverUtils.click(xpath);
        WebDriverUtils.waitForElement(TOOLTIP_XP);
    }

    private static void inputDropdownAndSwitch(String xpath, String value){
        WebDriverUtils.setDropdownOptionByValue(xpath, value);
        switchToDropdown(xpath);
    }

    private static void switchToDropdown(String xpath){
        WebDriverUtils.pressKey(Keys.TAB);
        //this is not a typo, tab has to be clicked twice
        WebDriverUtils.pressKey(Keys.TAB);
        WebDriverUtils.click(xpath);
        WebDriverUtils.waitForElement(TOOLTIP_XP);
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
        results = validateValidDropdownInput(xpath, rule, results, tooltipID);
        results = validateEmptyDropdown(xpath, rule,results, tooltipID);
        if(!rule.getTooltipNegativeInvalid().equals("N/A")){
            results = validateNotAllowedDropdown(xpath, rule,results, tooltipID);
        }
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
