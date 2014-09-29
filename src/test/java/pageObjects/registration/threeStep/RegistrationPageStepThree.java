package pageObjects.registration.threeStep;

import pageObjects.registration.RegistrationPage;
import springConstructors.UserData;
import springConstructors.ValidationRule;
import utils.WebDriverUtils;
import utils.validation.ValidationUtils;

public class RegistrationPageStepThree extends RegistrationPage {

    private static final String ROOT_XP = 											"//*[contains(@class, 'portlet-registration__step')][3]";
    private final static String FIELD_QUESTION_NAME = 								"verificationQuestion";
    private final static String FIELD_ANSWER_NAME = 								"verificationAnswer";
    private static final String CHECKBOX_TERMS_AND_CONDITION_XP = 					ROOT_XP + "//*[@id='terms-checkbox']";
    private final static String BUTTON_PREVIOUS_XP=                                 ROOT_XP + "//button[contains(@class, 'fn-prev')]";

    public static final String LOADING_ANIMATION_XP =                               "//*[contains(@class, 'progress')]";

    public RegistrationPageStepThree(){
		super(new String[]{ROOT_XP, BUTTON_PREVIOUS_XP,BUTTON_SUBMIT_XP});
	}

    public static void fillDataAndSubmit(UserData userData, boolean termsAndConditions, boolean isReceiveBonusesChecked, String bonusCode){
        fillData(userData,termsAndConditions, isReceiveBonusesChecked, bonusCode);
        clickSubmit();
    }

    public static void fillData(UserData userData, boolean termsAndConditions, boolean isReceiveBonusesChecked, String bonusCode){
        fillUsername(userData.getUsername());
        fillPassword(userData.getPassword());
        fillPasswordVerification(userData.getPassword());
        fillQuestion(userData.getVerificationQuestion());
        fillAnswer(userData.getVerificationAnswer());
        setCurrency(userData.getCurrency());
        setTermsCheckbox(termsAndConditions);
        fillBonusAndPromotional(isReceiveBonusesChecked, bonusCode);
        WebDriverUtils.waitFor(1000);
    }

    protected static void fillQuestion(String username){
        WebDriverUtils.clearAndInputTextToField(getXpathByName(FIELD_QUESTION_NAME), username);
    }

    protected static void fillAnswer(String username){
        WebDriverUtils.clearAndInputTextToField(getXpathByName(FIELD_ANSWER_NAME), username);
    }

    public void validateQuestionField(ValidationRule rule) {
        ValidationUtils.validateField(getXpathByName(FIELD_QUESTION_NAME), rule, FIELD_QUESTION_NAME);
    }

    public void validateAnswerField(ValidationRule rule) {
        ValidationUtils.validateField(getXpathByName(FIELD_ANSWER_NAME), rule, FIELD_ANSWER_NAME);
    }

    public static RegistrationPageStepTwo clickPrevious(){
        WebDriverUtils.click(BUTTON_PREVIOUS_XP);
        return new RegistrationPageStepTwo();
    }

    private static void setTermsCheckbox(boolean state){
        WebDriverUtils.setCheckBoxState(CHECKBOX_TERMS_AND_CONDITION_XP, state);
    }

    public static boolean getTermsCheckbox(){
        return WebDriverUtils.getCheckBoxState(CHECKBOX_TERMS_AND_CONDITION_XP);
    }

    public boolean setTermsCheckbox(){
        return WebDriverUtils.getCheckBoxState(CHECKBOX_TERMS_AND_CONDITION_XP);
    }

}
