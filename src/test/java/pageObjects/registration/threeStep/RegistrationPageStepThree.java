package pageObjects.registration.threeStep;

import enums.DepositLimits;
import enums.PromoCode;
import pageObjects.external.ims.IMSPlayerDetailsPage;
import pageObjects.registration.RegistrationPage;
import pageObjects.responsibleGaming.ResponsibleGamingPage;
import springConstructors.UserData;
import springConstructors.ValidationRule;
import utils.IMSUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;
import utils.validation.ValidationUtils;

import java.util.Map;
import java.util.TreeMap;

public class RegistrationPageStepThree extends RegistrationPage {

    private static final String ROOT_XP = 											"//*[contains(@class, 'portlet-registration__step')][3]";
    private final static String FIELD_QUESTION_NAME = 								"verificationQuestion";
    private final static String FIELD_ANSWER_NAME = 								"verificationAnswer";
    private final static String FIELD_LIMITS_NAME =                                 "limits";
    private final static String DAILY_DEPOSIT_LIMIT_XP =                            "//*[@id='daydepositlimit']";
    private final static String WEEKLY_DEPOSIT_LIMIT_XP =                           "//*[@id='weekdepositlimit']";
    private final static String MONTHLY_DEPOSIT_LIMIT_XP =                          "//*[@id='monthdepositlimit']";
    private static final String CHECKBOX_TERMS_AND_CONDITION_XP = 					ROOT_XP + "//*[@id='terms-checkbox']";
    private final static String BUTTON_PREVIOUS_XP=                                 ROOT_XP + "//button[contains(@class, 'fn-prev')]";

    public RegistrationPageStepThree(){
		super(new String[]{ROOT_XP, BUTTON_PREVIOUS_XP,BUTTON_SUBMIT_XP});
	}

    public void fillDataAndSubmit(UserData userData, boolean termsAndConditions, boolean isReceiveBonusesChecked, PromoCode promoCode){
        fillData(userData,termsAndConditions, isReceiveBonusesChecked, promoCode);
        clickSubmit();
    }

    public void fillData(UserData userData, boolean termsAndConditions, boolean isReceiveBonusesChecked, PromoCode promoCode){
        setTermsCheckbox(termsAndConditions);
        fillBonusAndPromotional(isReceiveBonusesChecked, promoCode);
        fillUsername(userData.getUsername());
        fillPassword(userData.getPassword());
        fillPasswordVerification(userData.getPassword());
        fillQuestion(userData.getVerificationQuestion());
        fillAnswer(userData.getVerificationAnswer());
        setCurrency(userData.getCurrencyName());
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

    public RegistrationPageStepTwo clickPrevious(){
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

    public static void setDailyDepositLimit(String limitValue) {
        WebDriverUtils.setDropdownOptionByValue(DAILY_DEPOSIT_LIMIT_XP, limitValue);
    }

    public static void setWeeklyDepositLimit(String limitValue) {
        WebDriverUtils.setDropdownOptionByValue(WEEKLY_DEPOSIT_LIMIT_XP, limitValue);
    }

    public static void setMonthlyDepositLimit(String limitValue) {
        WebDriverUtils.setDropdownOptionByValue(MONTHLY_DEPOSIT_LIMIT_XP, limitValue);
    }

    public void validateDepositLimitsFields(){
        String id = FIELD_LIMITS_NAME;
        setMonthlyDepositLimit("100");
        setWeeklyDepositLimit("20");
        setDailyDepositLimit("50");
        ValidationUtils.assertTooltipText(id, "Daily limit should not exceed Weekly limit", "daily deposit limit less than weekly");
        setWeeklyDepositLimit("200");
        ValidationUtils.assertTooltipText(id, "Weekly limit should not exceed Monthly limit", "weekly deposit limit less than monthly");
    }

    public void setDepositLimits() {
        setDailyDepositLimit("10");
        setWeeklyDepositLimit("20");
        setMonthlyDepositLimit("50");
    }

    public void validateDepositLimitsIMS(String username){
        IMSPlayerDetailsPage imsPlayerDetailsPage = IMSUtils.navigateToPlayedDetails(username);
        AbstractTest.assertEquals("10", imsPlayerDetailsPage.getDayLimit(), "Day limit successfully saved to IMS");
        AbstractTest.assertEquals("20", imsPlayerDetailsPage.getWeekLimit(), "Week limit successfully saved to IMS");
        AbstractTest.assertEquals("50", imsPlayerDetailsPage.getMonthLimit(), "Month limit successfully saved to IMS");
    }
}
