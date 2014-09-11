package pageObjects.registration.threeStep;

import enums.Page;
import pageObjects.core.AbstractPage;
import pageObjects.core.AbstractPageObject;
import pageObjects.registration.RegistrationPage;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.WebDriverUtils;

/**
 * User: ivan
 * Date: 7/31/13
 */

public class RegistrationPageStepThree extends RegistrationPage {

    private static final String ROOT_XP = 											"//*[contains(@class, 'portlet-registration__step')][3]";
    private static final String CHECKBOX_TERMS_AND_CONDITION_XP = 					ROOT_XP + "//*[@id='terms-checkbox']";
    private final static String BUTTON_PREVIOUS_XP=                                 ROOT_XP + "//button[contains(@class, 'fn-prev')]";

    public RegistrationPageStepThree(){
		super(new String[]{ROOT_XP, BUTTON_PREVIOUS_XP,BUTTON_SUBMIT_XP});
	}

    public static void fillDataAndSubmit(UserData userData, boolean termsAndConditions, boolean isReceiveBonusesChecked, String bonusCode){
        fillUsername(userData.getUsername());
        fillPassword(userData.getPassword());
        fillPasswordVerification(userData.getPassword());
        setCurrency(userData.getCurrency());
        setTermsCheckbox(termsAndConditions);
        fillBonusAndPromotional(isReceiveBonusesChecked, bonusCode);
        WebDriverUtils.waitFor(1000);
        clickSubmit();
    }

    private static RegistrationPageStepTwo clickPrevious(){
        WebDriverUtils.click(BUTTON_PREVIOUS_XP);
        return new RegistrationPageStepTwo();
    }

    private static void setTermsCheckbox(boolean state){
        WebDriverUtils.setCheckBoxState(CHECKBOX_TERMS_AND_CONDITION_XP, state);
    }

    public boolean setTermsCheckbox(){
        return WebDriverUtils.getCheckBoxState(CHECKBOX_TERMS_AND_CONDITION_XP);
    }

}
