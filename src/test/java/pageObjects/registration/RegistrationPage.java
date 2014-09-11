package pageObjects.registration;

import pageObjects.core.AbstractPage;

public class RegistrationPage extends AbstractPage{

    protected final static String ROOT_XP =                                             "//*[contains(@class, 'fn-register-content')]";
    protected final static String FIELD_BASE_XP =                                       ROOT_XP + "//*[@name='"+PLACEHOLDER+"']";
    protected final static String TOOLTIP_ERROR_XP =                                    "//div[contains(@class,'error-tooltip')]";
    protected final static String TOOLTIP_ERROR_BY_ID_XP =                              TOOLTIP_ERROR_XP +
                                                                                        "[@data-tooltip-owner = '"+PLACEHOLDER+"']//span " +
                                                                                        "| //*[@data-validation-type='"+PLACEHOLDER+"']"+TOOLTIP_ERROR_XP;
    private final static String BONUS_CODE_VALID = 			                            "TEST";
    private final static String BONUS_CODE_INVALID = 		                            "HELL";

    private final static String DROPDOWN_GENDER_NAME=		                            "sex";
    private final static String FIELD_FIRSTNAME_NAME =				 				    "firstname";
    private final static String FIELD_LASTNAME_NAME = 								    "lastname";
    private final static String DROPDOWN_BIRTHDAY_NAME=								    "birthDay";
    public final static String DROPDOWN_BIRTHMONTH_NAME=								"birthMonth";
    public final static String DROPDOWN_BIRTHYEAR_NAME=								    "birthYear";
    private final static String FIELD_EMAIL_NAME = 									    "email";
    public final static String FIELD_EMAIL_VERIFICATION_NAME = 						    "confirmEmail";
    private final static String FIELD_ADDRESS_NAME = 								    "address";
    private final static String FIELD_CITY_NAME = 									    "city";
    private final static String FIELD_POSTCODE_NAME = 								    "zip";
    private final static String DROPDOWN_COUNTRY_NAME = 								"countrycode";


    protected final static String LABEL_USERNAME_SUGGESTION_XP = 						"//*[@class='fn-suggestion']/..";
    protected final static String CHECKBOX_TERMS_AND_CONDITION_XP = 					"//*[@id='terms-checkbox']";

    protected final static String CHECKBOX_TERMS_AND_CONDITION_VALIDATION_ERROR_XP = 	CHECKBOX_TERMS_AND_CONDITION_XP+TOOLTIP_ERROR_XP;
    protected final static String LINK_TERMS_AND_CONDITION_XP = 						"//*[@data-title='Terms & Conditions']";
    private final static String BUTTON_SUBMIT_XP = 									    ROOT_XP + "//*[contains(@class,'fn-submit')]";

    //desktop only
    private final static String LABEL_GENDER_XP=									    "//label[@for='gender']";
    private final static String LABEL_FIRSTNAME_XP =								    "//label[@for='firstname']";
    private final static String LABEL_LASTNAME_XP=									    "//label[@for='lastname']";
    private final static String LABEL_BIRTHDAY_XP=									    "//span[following-sibling::div/div[@class='date-section']]";
    private final static String LABEL_EMAIL_XP=										    "//label[@for='email']";
    private final static String LABEL_EMAIL_VERIFICATION_XP=						    "//label[@for='confirmEmail']";
    private final static String LABEL_ADDRESS_XP =									    "//label[@for='address']";
    private final static String LABEL_CITY_XP=										    "//label[@for='city']";
    private final static String LABEL_POSTCODE_XP=									    "//label[@for='zip']";
    private final static String LABEL_COUNTRY_XP=									    "//span[following-sibling::div/*[@name='countrycode']]";














    public RegistrationPage(String[] elements){
        super(elements);
    }
}
