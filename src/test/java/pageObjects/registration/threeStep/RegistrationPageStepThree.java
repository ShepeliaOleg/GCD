package pageObjects.registration.threeStep;

import enums.Page;
import pageObjects.core.AbstractPage;
import pageObjects.core.AbstractPageObject;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.WebDriverUtils;

/**
 * User: ivan
 * Date: 7/31/13
 */

public class RegistrationPageStepThree extends AbstractPage {

    private static final String ROOT_XP = 											"//*[contains(@class, 'portlet-registration__step')][3]";
    private final static String FIELD_USERNAME_XP = 								ROOT_XP + "//*[@name='userName']";
    private final static String FIELD_PASSWORD_XP = 								ROOT_XP + "//*[@name='password']";
    private final static String FIELD_PASSWORD_VERIFICATION_XP = 					ROOT_XP + "//*[@name='passwordVerify']";
    private final static String DROPDOWN_CURRENCY_XP = 								ROOT_XP + "//*[@name='currencyCode']";
    private final static String FIELD_BONUSCODE_XP = 								ROOT_XP + "//*[@name='coupon']";
    private static final String CHECKBOX_TERMS_AND_CONDITION_XP = 					ROOT_XP + "//*[@id='terms-checkbox']";
    private static final String LINK_TERMS_AND_CONDITION_XP = 					    ROOT_XP + "//*[@data-title='Terms & Conditions']";
    private final static String BUTTON_PREVIOUS_XP=                                 ROOT_XP + "//button[contains(@class, 'fn-prev')]";
    private static final String BUTTON_SUBMIT_XP = 									ROOT_XP + "//*[contains(@class,'fn-submit')]";
    private final static String LABEL_TOOLTIP_XP=                                   ROOT_XP + "//*[@class='error-tooltip']";
    public final static String LABEL_MESSAGE_ERROR_XP=                              "//*[@class='message error']";
    public final static String LABEL_ERROR_TIMEOUT_XP =                             LABEL_MESSAGE_ERROR_XP + "[contains(text(), 'timeout')]";


    public RegistrationPageStepThree(){
		super(new String[]{ROOT_XP, FIELD_USERNAME_XP,FIELD_PASSWORD_XP,FIELD_PASSWORD_VERIFICATION_XP,
                DROPDOWN_CURRENCY_XP,FIELD_BONUSCODE_XP,CHECKBOX_TERMS_AND_CONDITION_XP,
                LINK_TERMS_AND_CONDITION_XP,BUTTON_PREVIOUS_XP,BUTTON_SUBMIT_XP});
	}

    public static AbstractPageObject fillDataAndSubmit(UserData userData){
        fillUsername(userData.getUsername());
        fillPassword(userData.getPassword());
        fillPasswordVerification(userData.getPassword());
        setCurrency(userData.getCurrency());
        setTermsCheckbox(true);
        WebDriverUtils.waitFor(1000);
        clickSubmit();
        return NavigationUtils.closeAllPopups(Page.homePage);
    }

    private static RegistrationPageStepTwo clickPrevious(){
        WebDriverUtils.click(BUTTON_PREVIOUS_XP);
        return new RegistrationPageStepTwo();
    }

    private static void clickSubmit(){
        WebDriverUtils.click(BUTTON_SUBMIT_XP);
    }

    private static void fillUsername(String text){
        WebDriverUtils.clearAndInputTextToField(FIELD_USERNAME_XP, text);
    }

    private static void fillPassword(String text){
        WebDriverUtils.clearAndInputTextToField(FIELD_PASSWORD_XP, text);
    }

    private static void fillPasswordVerification(String text){
//        WebDriverUtils.inputTextToField(FIELD_PASSWORD_VERIFICATION_XP, text, 100);
        WebDriverUtils.executeScript("document.getElementsByName('passwordVerify')[0].value='" + text + "'");
    }

    private static void setCurrency(String text){
        WebDriverUtils.setDropdownOptionByValue(DROPDOWN_CURRENCY_XP, text);
    }

    private static void setTermsCheckbox(boolean state){
        WebDriverUtils.setCheckBoxState(CHECKBOX_TERMS_AND_CONDITION_XP, state);
    }
}
