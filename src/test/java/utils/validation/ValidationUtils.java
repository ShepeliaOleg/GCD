package utils.validation;

import org.openqa.selenium.Keys;
import pageObjects.registration.RegistrationPage;
import springConstructors.ValidationRule;
import utils.RandomUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;
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

    private static void validateClick(String xpath, ValidationRule rule, String tooltipID) {
        clickField(xpath);
        assertValidationStatus(tooltipID, STATUS_NONE, "click");
        if(platform.equals(PLATFORM_DESKTOP)) {
            assertToolTips(rule.getTooltipPositive(), tooltipID, "", STATUS_PASSED);
        }
    }

    private static void validateEmpty(String xpath, ValidationRule rule, String tooltipID) {
        inputFieldAndRefocus(xpath);
        String status = STATUS_PASSED;
        if(platform.equals(PLATFORM_MOBILE)){
            status = STATUS_NONE;
        }
        if(rule.getIsMandatory().equals("true")){
            validateStatusAndToolTips(rule.getTooltipNegativeEmpty(), tooltipID, "empty", STATUS_FAILED, STATUS_FAILED);
        }else {
            validateStatusAndToolTips(rule.getTooltipPositive(), tooltipID, "empty", STATUS_PASSED, status);
        }
    }

    private static void validateEmptyDropdown(String xpath, ValidationRule rule, String tooltipID) {
        refocusDropdown(xpath);
        if(rule.getIsMandatory().equals("true")){
            validateStatusAndToolTips(rule.getTooltipNegativeEmpty(), tooltipID, "empty", STATUS_FAILED, STATUS_FAILED);
        }else{
            validateStatusAndToolTips(NO_TOOLTIP, tooltipID, "empty", STATUS_PASSED, STATUS_NONE);
        }
    }

	private static void validateNotAllowedSymbols(String xpath, ValidationRule rule, String tooltipID) {
        String tooltip = rule.getTooltipNegativeInvalid();
        for(char a: rule.getAllNotAllowedSymbols().toCharArray()) {
            char validChar = rule.getValidChar();
            String character = String.valueOf(a);
            character = escapeSymbol(character);
            String value = rule.generateValidMinLengthUnifiedString().replaceAll("(?<="+validChar+")"+validChar+"(?="+validChar+")", character);
            inputFieldAndRefocus(xpath, value);
            assertValidationStatus(tooltipID, STATUS_FAILED, value);
            if(!tooltip.equals(NO_TOOLTIP)){
                assertTooltipStatus(tooltipID, STATUS_FAILED, value);
                assertTooltipText(tooltipID, tooltip, value);
            }
        }
    }

    private static void validateAllowedSymbols(String xpath, ValidationRule rule, String tooltipID) {
        for(char a: rule.getAllAllowedSymbols().toCharArray()){
            char validChar = rule.getValidChar();
            String character = String.valueOf(a);
            if(excludeSpecificChar(rule, character)){
                continue;
            }
            character = escapeSymbol(character);
            String value = rule.generateValidMinLengthUnifiedString().replaceAll("(?<="+validChar+")"+validChar+"(?="+validChar+")", character);
            inputFieldAndRefocus(xpath, value);
            assertValidationStatus(tooltipID, STATUS_PASSED, value);
            assertTooltipStatus(tooltipID, STATUS_NONE, value);
        }
    }

    private static void validateValidFieldInput(String xpath, ValidationRule rule, String tooltipID) {
        String randomAllowed = rule.generateValidString();
        inputFieldAndRefocus(xpath, randomAllowed);
        validateStatusAndToolTips(NO_TOOLTIP, tooltipID, randomAllowed, STATUS_PASSED, STATUS_NONE);
    }

    private static void validateValidDropdownInput(String xpath, ValidationRule rule, String tooltipID) {
        if(rule.getRegexp().equals("DOB")){
            inputDateOfBirthAndRefocus(xpath);
            validateStatusAndToolTips(NO_TOOLTIP, tooltipID, "Valid Date Of Birth", STATUS_PASSED, STATUS_NONE);
        }else{
            for(String value:rule.getDropdownValues()){
                inputDropdownAndRefocus(xpath, value);
                validateStatusAndToolTips(NO_TOOLTIP, tooltipID, value, STATUS_PASSED, STATUS_NONE);
            }
        }
    }

	private static void validateTooShort(String xpath, ValidationRule rule, String tooltipID) {
		int min = rule.getMinLength();
		if (min < 0) {
            throw new RuntimeException("Minimum value for validation rule should be positive, actual value: " + min);
        } else if (min > 1) {
			int randomLength = RandomUtils.generateRandomIntBetween(1, min - 1);
			String randomTooShort = rule.generateValidStringWithMinSymbols().substring(0, randomLength);
            inputFieldAndRefocus(xpath, randomTooShort);
            validateStatusAndToolTips(rule.getTooltipNegativeShort(), tooltipID, randomTooShort, STATUS_FAILED, STATUS_FAILED);
        }
	}

	private static void validateTooLong(String xpath, ValidationRule rule, String tooltipID) {
		int max = rule.getMaxLength();
		if (max <= 0) {
			throw new RuntimeException("Maximum value for validation rule should be larger than 0, actual value: " + max);
		}else {
            String randomTooLong = rule.generateValidStringOverMaxSymbols();
            inputFieldAndRefocus(xpath, randomTooLong);
            validateStatusAndToolTips(rule.getTooltipNegativeLong(), tooltipID, randomTooLong, STATUS_FAILED, STATUS_FAILED);
        }
	}

    public static void validateStatusAndToolTips(String tooltip, String tooltipID, String value, String fieldStatus, String tooltipStatus ){
        assertValidationStatus(tooltipID, fieldStatus, value);
        assertToolTips(tooltip, tooltipID, value, tooltipStatus);
    }

    private static void assertToolTips(String tooltip, String tooltipID, String value, String tooltipStatus){
        if(tooltip.equals(NO_TOOLTIP)||tooltip.equals(STATUS_NONE)||tooltipStatus.equals(STATUS_NONE)){
            assertTooltipStatus(tooltipID, STATUS_NONE, value);
        }else {
            assertTooltipStatus(tooltipID, tooltipStatus, value);
            if(!getTooltipStatus(tooltipID).equals(STATUS_NONE)){
                assertTooltipText(tooltipID, tooltip, value);
            }else {
                AbstractTest.addError("For '"+value+"' expected tooltip '"+tooltip+"', but it did not appear");
            }
        }
    }

	public static void assertValidationStatus(String id, String expectedStatus, String value) {
        AbstractTest.assertEquals(expectedStatus, getValidationStatus(id), "Validation status for '"+value+"'");
	}

    public static void assertTooltipStatus(String id, String expectedStatus, String value) {
        AbstractTest.assertEquals(expectedStatus, getTooltipStatus(id), "Tooltip status for '"+value+"'");
    }

    public static void assertTooltipText(String id, String expectedText, String value) {
        AbstractTest.assertEquals(expectedText, getTooltipText(id), "Tooltip text for '"+value+"'");
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
            return STATUS_NONE;
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
        WebDriverUtils.waitFor(500);
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
        WebDriverUtils.pressKey(Keys.TAB);
        if(platform.equals(PLATFORM_DESKTOP)){
            clickField(xpath);
        }
        WebDriverUtils.waitFor(500);
    }

	public static void validateField(String xpath, ValidationRule rule, String tooltipID) {
        validateClick(xpath, rule, tooltipID);
        validateValidFieldInput(xpath, rule, tooltipID);
        validateEmpty(xpath, rule, tooltipID);
        validateNotAllowedSymbols(xpath, rule, tooltipID);
        validateAllowedSymbols(xpath, rule, tooltipID);
        validateTooShort(xpath, rule, tooltipID);
        validateTooLong(xpath, rule, tooltipID);
	}

    public static void validateDropdown(String xpath, ValidationRule rule, String tooltipID) {
        validateClick(xpath, rule, tooltipID);
        validateEmptyDropdown(xpath, rule, tooltipID);
        validateValidDropdownInput(xpath, rule, tooltipID);
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
        boolean areaCode = (character.equals("+")&&rule.getRegexp().contains("[+]"));
        return (email||city||areaCode);
    }

}
