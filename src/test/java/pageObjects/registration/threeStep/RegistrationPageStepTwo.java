package pageObjects.registration.threeStep;

import pageObjects.registration.RegistrationPage;
import springConstructors.UserData;
import springConstructors.ValidationRule;
import utils.RandomUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;
import utils.validation.ValidationUtils;

public class RegistrationPageStepTwo extends RegistrationPage {

    private static final String ROOT_XP = 											"//*[contains(@class, 'portlet-registration__step')][2]";
    private final static String BUTTON_NEXT_XP=                                     ROOT_XP + "//button[contains(@class, 'fn-next')]";
    private final static String BUTTON_PREVIOUS_XP=                                 ROOT_XP + "//button[contains(@class, 'fn-prev')]";
    private final static String FIELD_PHONE_MOBILE_VALIDATION_NAME = 				"phone";
    private final static String FIELD_PHONE_MOBILE_NAME = 					        "cellphone";
    private final static String FIELD_PHONE_COUNTRY_CODE_NAME  = 					"area";
    private final static String BUTTON_FIND_XP =                                    "//*[@class='btn fr fn-find']";
    private final static String DROPDOWN_ADDRESS_XP =                               "//*[@name='address4']";
    private final static String POSTCODE_ONE_SIX_ADDRESSES =                        "SW1Y 4DP";
    private final static String POSTCODE_MANY_ADDRESSES =                           "N9 0EF";
    private final static String POSTCODE_UNKNOWN_ADDRESSES =                        "SW1E 4DP";
    private final static String TOOLTIP_EMPTY_POSTCODE_UK =                         "Please enter your zip/postal code";
    private final static String TOOLTIP_INVALID_FORMAT_POSTCODE_UK =                "Postcode format is invalid";
    private final static String TOOLTIP_UNKNOWN_POSTCODE_UK =                       "Sorry, there were no results";




    public RegistrationPageStepTwo(){
		super(new String[]{ROOT_XP, BUTTON_NEXT_XP, BUTTON_PREVIOUS_XP});
	}

    public RegistrationPageStepThree fillDataAndSubmit(UserData userData){
        fillCountry(userData.getCountry());
        fillAddress(userData.getAddress());
        fillCity(userData.getCity());
        fillPostCode(userData.getPostCode());
        fillPhoneAreaCode(userData.getMobileAreaCode());
        fillMobile(userData.getMobile());
//        fillPhone(userData.getPhone());
        WebDriverUtils.waitFor(1000);
        clickNext();
        return new RegistrationPageStepThree();
    }

    private void fillPhone(String mobile) {
        WebDriverUtils.clearAndInputTextToField(getXpathByName(FIELD_PHONE_MOBILE_VALIDATION_NAME), mobile);
    }

    public void clickNext(){
        WebDriverUtils.click(BUTTON_NEXT_XP);
    }

    public RegistrationPageStepOne clickPrevious(){
        WebDriverUtils.click(BUTTON_PREVIOUS_XP);
        return new RegistrationPageStepOne();
    }

    public void clickFind(){
        WebDriverUtils.click(BUTTON_FIND_XP);
        WebDriverUtils.waitFor(2000);
    }

    private static void fillMobile(String text){
        WebDriverUtils.clearAndInputTextToField(getXpathByName(FIELD_PHONE_MOBILE_NAME), text);
    }

    public static void fillPhoneAreaCode(String phoneAreaCode){
        WebDriverUtils.clearAndInputTextToField(getXpathByName(FIELD_PHONE_COUNTRY_CODE_NAME), phoneAreaCode);
    }

    public void fillPostcodeWithOneSixAddresses(){
        fillPostCode(POSTCODE_ONE_SIX_ADDRESSES);
    }

    public void fillPostcodeWithManyAddresses(){
        fillPostCode(POSTCODE_MANY_ADDRESSES);
    }

    public void validatePhoneAreaCodeField(ValidationRule rule) {
        ValidationUtils.validateField(getXpathByName(FIELD_PHONE_COUNTRY_CODE_NAME), rule, FIELD_PHONE_COUNTRY_CODE_NAME);
    }

    public void validatePhoneField(ValidationRule rule) {
        ValidationUtils.validateField(getXpathByName(FIELD_PHONE_MOBILE_NAME), rule, FIELD_PHONE_MOBILE_VALIDATION_NAME);
    }

    public boolean isFindButtonVisible(){
        return WebDriverUtils.isVisible(BUTTON_FIND_XP);
    }

    public void selectUKCountry(){
        fillCountry("GB");
    }

    private int addressListSize (){
        return WebDriverUtils.getDropdownItemsAmount(DROPDOWN_ADDRESS_XP);
    }

    public void selectRandomAddressFromDropdown() {
        WebDriverUtils.setDropdownOptionByIndex(DROPDOWN_ADDRESS_XP, RandomUtils.generateRandomIntBetween(1, addressListSize() - 1));
    }

    public boolean isAddressFieldsEditableAndEmpty(){
       return WebDriverUtils.isEditableNew(getXpathByName(FIELD_ADDRESS_NAME)) &&
              WebDriverUtils.isEditableNew(getXpathByName(FIELD_CITY_NAME)) &&
              WebDriverUtils.isEditableNew(getXpathByName(FIELD_HOUSE_NAME)) &&
              !WebDriverUtils.getInputFieldText(getXpathByName(FIELD_ADDRESS_NAME)).isEmpty() &&
              !WebDriverUtils.getInputFieldText(getXpathByName(FIELD_CITY_NAME)).isEmpty();
    }

    public void validatePostcodeUK() {
        fillCountry("GB");
        fillPostCode("");
        clickFind();
        String emptyPostcodeTooltip = ValidationUtils.getTooltipText(FIELD_POSTCODE_NAME);
        AbstractTest.assertEquals(TOOLTIP_EMPTY_POSTCODE_UK, emptyPostcodeTooltip, "Wrong tooltip for empty postcode UK.");
        fillPostCode(RandomUtils.generateLiteralString(12));
        clickFind();
        String invalidPostcodeTooltip = ValidationUtils.getTooltipText(FIELD_POSTCODE_NAME);
        AbstractTest.assertEquals(TOOLTIP_INVALID_FORMAT_POSTCODE_UK, invalidPostcodeTooltip, "Wrong tooltip for not valid postcode UK.");
        fillPostCode(POSTCODE_UNKNOWN_ADDRESSES);
        clickFind();
        String unknownPostcodeTooltip = ValidationUtils.getTooltipText(FIELD_POSTCODE_NAME);
        AbstractTest.assertEquals(TOOLTIP_UNKNOWN_POSTCODE_UK, unknownPostcodeTooltip, "Wrong tooltip for unknown postcode UK.");
    }
}
