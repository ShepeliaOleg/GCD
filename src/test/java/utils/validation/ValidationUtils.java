package utils.validation;

import org.openqa.selenium.Keys;
import pageObjects.registration.RegistrationPage;
import springConstructors.ValidationRule;
import utils.RandomUtils;
import utils.WebDriverUtils;
import utils.core.WebDriverObject;

import java.util.ArrayList;

public class ValidationUtils extends WebDriverObject{

    private static final String PLACEHOLDER =               "$PLACEHOLDER$";
    private static final String NO_TOOLTIP =                "N/A";
    public static final String STATUS_PASSED =              " valid";
    public static final String STATUS_FAILED =              "invalid";
    public static final String STATUS_NONE =                "NONE";
    private static final String TOOLTIP_STATUS_ERROR =      "tooltip-error";
    public static final String PASSED =                     "Passed";

    private static final String FIELD_STATUS_XP =           "//*[@data-validation-type='"+PLACEHOLDER+"']";
    protected final static String TOOLTIP_ERROR_BASE_XP =   "//*[contains(@class,'error-tooltip')]";
    private final static String TOOLTIP_ERROR_MOBILE_XP =   FIELD_STATUS_XP + TOOLTIP_ERROR_BASE_XP;
    private final static String TOOLTIP_ERROR_DESKTOP_XP =   "//*[@data-tooltip-owner='"+PLACEHOLDER+"']";

    private static ArrayList<String> validateClick(String xpath, ValidationRule rule, ArrayList<String> results, String tooltipID) {
        clickField(xpath);
        results.add(validationStatusIs(tooltipID, STATUS_NONE, "click"));
        if(platform.equals(PLATFORM_DESKTOP)) {
            String tooltip = rule.getTooltipPositive();
            results = validateToolTips(results, tooltip, tooltipID, "", STATUS_PASSED);
        }
        return results;
    }

    private static ArrayList<String> validateEmpty(String xpath, ValidationRule rule, ArrayList<String> results, String tooltipID) {
        inputFieldAndRefocus(xpath);
        String passedStatus = STATUS_PASSED;
        if(platform.equals(PLATFORM_MOBILE)){
            passedStatus = STATUS_NONE;
        }
        if(rule.getIsMandatory().equals("true")){
            results = validateStatusAndToolTips(results, rule.getTooltipNegativeEmpty(), tooltipID, "empty", STATUS_FAILED, STATUS_FAILED);
        }else {
            results = validateStatusAndToolTips(results, rule.getTooltipPositive(), tooltipID, "empty", passedStatus, STATUS_PASSED);
        }
        return results;
    }

    private static ArrayList<String> validateEmptyDropdown(String xpath, ValidationRule rule, ArrayList<String> results, String tooltipID) {
        refocusDropdown(xpath);
        if(rule.getIsMandatory().equals("true")){
            results = validateStatusAndToolTips(results, rule.getTooltipNegativeEmpty(), tooltipID, "empty", STATUS_FAILED, STATUS_FAILED);
        }else{
            results = validateStatusAndToolTips(results, NO_TOOLTIP, tooltipID, "empty", STATUS_PASSED, STATUS_NONE);
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
            character = escapeSymbol(character);
            value = rule.generateValidMinLengthUnifiedString().replaceAll("(?<="+validChar+")"+validChar+"(?="+validChar+")", character);
            inputFieldAndRefocus(xpath, value);
            if(!validationStatusIs(tooltipID, STATUS_FAILED, value).equals(PASSED)){
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
        if(!validationStatus.isEmpty()){
            results.add("For symbols = '" + validationStatus + "' validation status should be '"+STATUS_FAILED+"'");
        }
        if(!tooltipStatus.isEmpty()){
            results.add("For symbols = '" + tooltipStatus + "' tooltip status should be '"+STATUS_FAILED+"'");
        }
        if(!tooltipText.isEmpty()){
            results.add("For symbols = '" + tooltipText + "' tooltip text should be '"+tooltip+"'");
        }
        return results;
    }

    private static ArrayList<String> validateAllowedSymbols(String xpath, ValidationRule rule, ArrayList<String> results, String tooltipID) {
        String value;
        String allAllowedSymbols = rule.getAllAllowedSymbols();
        String validationStatusString="";
        String tooltipStatusString="";
        for(char a: allAllowedSymbols.toCharArray()){
            char validChar = rule.getValidChar();
            String character = String.valueOf(a);
            if(excludeSpecificChar(rule, character)){
                continue;
            }
            character = escapeSymbol(character);
            value = rule.generateValidMinLengthUnifiedString().replaceAll("(?<="+validChar+")"+validChar+"(?="+validChar+")", character);
            inputFieldAndRefocus(xpath, value);
            if(!validationStatusIs(tooltipID, STATUS_PASSED, value).equals(PASSED)){
                validationStatusString+=a;
            };
            if(!tooltipStatusIs(tooltipID, STATUS_NONE, value).equals(PASSED)){
                tooltipStatusString+=a;
            };
        }
        if(!validationStatusString.isEmpty()){
            results.add("For symbols = '" + validationStatusString + "' validation status should be '"+STATUS_PASSED+"'");
        }
        if(!tooltipStatusString.isEmpty()){
            results.add("For symbols = '" + tooltipStatusString + "' tooltip should not appear on click");
        }
        return results;
    }

    private static ArrayList<String> validateValidFieldInput(String xpath, ValidationRule rule, ArrayList<String> results, String tooltipID) {
        String randomAllowed = rule.generateValidString();
        inputFieldAndRefocus(xpath, randomAllowed);
        results = validateStatusAndToolTips(results, NO_TOOLTIP, tooltipID, randomAllowed, STATUS_PASSED, STATUS_NONE);
        return results;
    }

    private static ArrayList<String> validateValidDropdownInput(String xpath, ValidationRule rule, ArrayList<String> results, String tooltipID) {
        if(rule.getRegexp().equals("DOB")){
            inputDateOfBirthAndRefocus(xpath);
            results = validateStatusAndToolTips(results, NO_TOOLTIP, tooltipID, "Valid Date Of Birth", STATUS_PASSED, STATUS_NONE);
        }else{
            String status = STATUS_PASSED;
            if(platform.equals(PLATFORM_MOBILE)){
                status = STATUS_NONE;
            }
            for(String value:rule.getDropdownValues()){
                inputDropdownAndRefocus(xpath, value);
                results = validateStatusAndToolTips(results, NO_TOOLTIP, tooltipID, value, status, STATUS_NONE);
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
            results = validateStatusAndToolTips(results, rule.getTooltipNegativeShort(), tooltipID, randomTooShort, STATUS_FAILED, STATUS_FAILED);
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
            results = validateStatusAndToolTips(results, rule.getTooltipNegativeLong(), tooltipID, randomTooLong, STATUS_FAILED, STATUS_FAILED);
        }
        return results;
	}

    public static ArrayList<String> validateStatusAndToolTips(ArrayList<String> results, String tooltip, String tooltipID, String value, String fieldStatus, String tooltipStatus ){
        results.add(validationStatusIs(tooltipID, fieldStatus, value));
        results = validateToolTips(results, tooltip, tooltipID, value, tooltipStatus);
        return results;
    }

    private static ArrayList<String> validateToolTips(ArrayList<String> results, String tooltip, String tooltipID, String value, String tooltipStatus ){
        if(tooltip.equals(NO_TOOLTIP)){
            results.add(tooltipStatusIs(tooltipID, STATUS_NONE, value));
        }else {
            results.add(tooltipStatusIs(tooltipID, tooltipStatus, value));
            if(!getTooltipStatus(tooltipID).equals(STATUS_NONE)){
                results.add(tooltipTextIs(tooltipID, tooltip, value));
            }else {
                results.add("Value = '"+value+"', tooltip should be '"+tooltip+"', but it did not appear");
            }
        }
        return results;
    }

	public static String validationStatusIs(String id, String expectedStatus, String value) {
		String actualStatus = getValidationStatus(id);
		if (!actualStatus.equals(expectedStatus)) {
			return ("Value = '" + value + "' should be '" + expectedStatus + "', but it is '" + actualStatus + "'");
		} else {
            return PASSED;
        }
	}

    public static String tooltipStatusIs(String id, String expectedStatus, String value) {
        String actualStatus = getTooltipStatus(id);
        if(actualStatus.equals(expectedStatus)){
            return PASSED;
        }else {
            return "Value = '" + value + "' tooltip should be '"+expectedStatus+"', but it is '"+actualStatus+"'";
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
        String xpath = getTooltipXpath(id);
        if(WebDriverUtils.isVisible(xpath, 0)){
            if(platform.equals(PLATFORM_DESKTOP)){
                String classValue = WebDriverUtils.getAttribute(xpath, "class");
                if(classValue.contains(TOOLTIP_STATUS_ERROR)){
                    return STATUS_FAILED;
                }else {
                    return STATUS_PASSED;
                }
            }else {
                return STATUS_FAILED;
            }
        }else {
            return STATUS_NONE;
        }
    }

    public static String getTooltipText(String id) {
        if(WebDriverUtils.isVisible(getTooltipXpath(id))){
            return WebDriverUtils.getElementText(getTooltipXpath(id));
        }else {
            return "No tooltip";
        }
    }

	private static String getValidationStatus(String id) {
        String xpath = FIELD_STATUS_XP.replace(PLACEHOLDER, id);
        String classValue = WebDriverUtils.getAttribute(xpath, "class");
        if(classValue.contains(STATUS_FAILED)){
            return STATUS_FAILED;
        }else if(classValue.contains(STATUS_PASSED)){
            return STATUS_PASSED;
        }else {
            return STATUS_NONE;
        }
	}

    private static void inputFieldAndRefocus(String xpath){
        inputFieldAndRefocus(xpath, " ");
    }

    public static void inputFieldAndRefocus(String xpath, String input){
        if(platform.equals(PLATFORM_DESKTOP)){
            if(xpath.contains(RegistrationPage.FIELD_PHONE_COUNTRY_CODE_DESKTOP_XP)){
                WebDriverUtils.clearAndInputTextToField(RegistrationPage.FIELD_PHONE_XP, "111111");
            }else if(xpath.contains(RegistrationPage.FIELD_PHONE_XP)){
                WebDriverUtils.clearAndInputTextToField(RegistrationPage.FIELD_PHONE_COUNTRY_CODE_DESKTOP_XP, "+111");
            }
        }
        WebDriverUtils.clearAndInputTextToField(xpath, input);
        WebDriverUtils.pressKey(Keys.TAB);
        if(platform.equals(PLATFORM_DESKTOP)){
            clickField(xpath);
        }
        WebDriverUtils.waitFor(1000);
    }

    private static void clickField(String xpath){
        WebDriverUtils.click(xpath);
    }

    private static void inputDropdownAndRefocus(String xpath, String value){
        WebDriverUtils.setDropdownOptionByValue(xpath, value);
        refocusDropdown(xpath);
    }

    private static void inputDateOfBirthAndRefocus(String xpath){
        WebDriverUtils.setDropdownOptionByValue(RegistrationPage.DROPDOWN_BIRTHDAY_XP, "01");
        WebDriverUtils.setDropdownOptionByValue(RegistrationPage.DROPDOWN_BIRTHMONTH_XP, "01");
        WebDriverUtils.setDropdownOptionByValue(RegistrationPage.DROPDOWN_BIRTHYEAR_XP, "1980");
        refocusDropdown(xpath);
    }

    private static void refocusDropdown(String xpath){
        WebDriverUtils.pressKey(Keys.TAB);
        if(platform.equals(PLATFORM_DESKTOP)){
            WebDriverUtils.pressKey(Keys.TAB);
            clickField(xpath);
        }
        WebDriverUtils.waitFor(1000);
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
            WebDriverUtils.runtimeExceptionWithUrl(message);
        }
	}

    public static void validateDropdown(String xpath, ValidationRule rule, String tooltipID) {
        ArrayList<String> results = new ArrayList();
        String message = "";
        results = validateClick(xpath, rule, results, tooltipID);
        results = validateEmptyDropdown(xpath, rule, results, tooltipID);
        results = validateValidDropdownInput(xpath, rule, results, tooltipID);
        for(String result:results){
            if(!result.equals(PASSED)){
                message += "<div>" + result + "</div>";
            }
        }
        if(!message.isEmpty()){
            WebDriverUtils.runtimeExceptionWithUrl(message);
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

    private static String getTooltipXpath(String id){
        if(platform.equals(PLATFORM_MOBILE)){
            return TOOLTIP_ERROR_MOBILE_XP.replace(PLACEHOLDER, id);
        }else {
            return TOOLTIP_ERROR_DESKTOP_XP.replace(PLACEHOLDER, id);
        }
    }

    private static String escapeSymbol(String character){
        if(character.equals("\\")||character.equals("^")||character.equals("$")||character.equals(".")
                ||character.equals("|")||character.equals("?")||character.equals("*")||character.equals("+")
                ||character.equals("(")||character.equals(")")||character.equals("[")||character.equals("]")
                ||character.equals("{")){
            character  = "\\" + character;
        }
        return character;
    }

    private static boolean excludeSpecificChar(ValidationRule rule, String character) {
        boolean email = ((character.equals("@")||character.equals("."))&&rule.getRegexp().contains("[@]"));
        boolean city = (character.equals("-")&&rule.getRegexp().contains("[-]"));
        return (email||city);
    }

}
