package pageObjects.registration.threeStep;

import enums.DepositLimits;
import enums.PromoCode;
import org.openqa.selenium.Keys;
import pageObjects.external.ims.IMSPlayerDetailsPage;
import pageObjects.registration.RegistrationPage;
import springConstructors.UserData;
import springConstructors.ValidationRule;
import utils.IMSUtils;
import utils.RandomUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;
import utils.validation.ValidationUtils;

import java.util.List;
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
    private final static String DEPOSIT_LIMIT_XP =                                  "//*[contains(@class, 'deposit-limit')]";
    private static final String CHECKBOX_TERMS_AND_CONDITION_XP = 					ROOT_XP + "//*[@id='terms-checkbox']";
    private final static String BUTTON_PREVIOUS_XP=                                 ROOT_XP + "//button[contains(@class, 'fn-prev')]";
    private static final String TOOLTIP_ERROR_XP =                                  "//*[contains(@class,'error-tooltip')]";
    private static final String LABEL_ERROR_DAILY_XP =                              "//*[contains(@class,'daily')]" + TOOLTIP_ERROR_XP;
    private static final String LABEL_ERROR_WEEKLY_XP =                             "//*[contains(@class,'weekly')]" + TOOLTIP_ERROR_XP;
    private static final String LABEL_ERROR_MONTHLY_XP =                            "//*[contains(@class,'monthly')]" + TOOLTIP_ERROR_XP;

    public RegistrationPageStepThree(){
		super(new String[]{ROOT_XP, BUTTON_PREVIOUS_XP, BUTTON_SUBMIT_XP});
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

    public void isDepositLimitsDropdownsVisible(){
        AbstractTest.assertTrue(WebDriverUtils.isElementVisible(DEPOSIT_LIMIT_XP, 2), "Deposit limits are not visible");
    }

    public String getSelectedDailyDepositLimit(){
        return WebDriverUtils.getDropdownSelectedOptionValue(DAILY_DEPOSIT_LIMIT_XP);
    }

    public String getSelectedWeeklyDepositLimit(){
        return WebDriverUtils.getDropdownSelectedOptionValue(WEEKLY_DEPOSIT_LIMIT_XP);
    }

    public String getSelectedMonthlyDepositLimit(){
        return WebDriverUtils.getDropdownSelectedOptionValue(MONTHLY_DEPOSIT_LIMIT_XP);
    }

    public void setDepositLimits(int daily, int weekly, int monthly){
        int counter=0;
        int big, med, small;
        Map<DepositLimits,Integer> initialLimits=new TreeMap<>();
        Map<Integer,DepositLimits> filteredOrderedLimits=new TreeMap<>();
        Map<DepositLimits,Integer> finalLimits=new TreeMap<>();
        initialLimits.put(DepositLimits.daily, daily);
        initialLimits.put(DepositLimits.weekly, weekly);
        initialLimits.put(DepositLimits.monthly, monthly);
        for(Map.Entry<DepositLimits,Integer> entry:initialLimits.entrySet()){
            if(entry.getValue()==0){
                setDepositLimitNotDefined(entry.getKey());
            }else{
                filteredOrderedLimits.put(entry.getValue(), entry.getKey());
            }
        }
        switch(filteredOrderedLimits.size()){
            case 3:
                do{
                    big = parseLimit(filteredOrderedLimits.get(3));
                    med = parseLimit(filteredOrderedLimits.get(2));
                    small = parseLimit(filteredOrderedLimits.get(1));
                    counter++;
                    if(counter>=300){
                        AbstractTest.failTest("Wrong deposit limits configuration");
                    }
                }while(big<=med || med<=small);
                finalLimits.put(filteredOrderedLimits.get(3),big);
                finalLimits.put(filteredOrderedLimits.get(2),med);
                finalLimits.put(filteredOrderedLimits.get(1),small);
                break;
            case 2:
                do{
                    med = parseLimit(filteredOrderedLimits.get(2));
                    small = parseLimit(filteredOrderedLimits.get(1));
                    counter++;
                    if(counter>=300){
                        AbstractTest.failTest("Wrong deposit limits configuration");
                    }
                }while(med<=small);
                finalLimits.put(filteredOrderedLimits.get(2),med);
                finalLimits.put(filteredOrderedLimits.get(1),small);
                break;
            case 1:
                finalLimits.put(filteredOrderedLimits.get(1), parseLimit(filteredOrderedLimits.get(1)));
                break;
            case 0:
                break;
        }
        for(Map.Entry<DepositLimits,Integer> entry:finalLimits.entrySet()){
            setDepositLimit(entry.getKey(), Integer.toString(entry.getValue()));
        }
        System.out.println(daily +" "+weekly+" "+monthly+" | "+ finalLimits.toString());
        WebDriverUtils.pressKey(Keys.ESCAPE);
        WebDriverUtils.pressKey(Keys.TAB);
        WebDriverUtils.waitFor(200);
    }

    private void setDepositLimitNotDefined(DepositLimits type){
        setDepositLimit(type, "0");
    }

    private int parseLimit(DepositLimits type){
        return Integer.parseInt(getRandomLimit(type));
    }

    private void setDepositLimit(DepositLimits type, String limit){
        WebDriverUtils.setDropdownOptionByValue(type.getXpath(), limit);
    }

    private List<String> getDepositLimits(DepositLimits type){
        List<String> fullList = WebDriverUtils.getDropdownOptionsValue(type.getXpath());
        //Remove 'not defined' and '0' values
        fullList.remove(0);
        fullList.remove(0);
        return fullList;
    }

    private String getRandomLimit(DepositLimits type){
        return RandomUtils.getRandomElementsFromList(getDepositLimits(type), 1, 1).get(0);
    }

    public void checkErrors(boolean daily, boolean weekly, boolean monthly){
        if(daily || weekly || monthly) {
            WebDriverUtils.waitForElement(TOOLTIP_ERROR_XP, 2000);
        }
        AbstractTest.assertEquals(daily, dailyValidationErrorMessageVisible(), "Daily Error Visible");
        AbstractTest.assertEquals(weekly, weeklyValidationErrorMessageVisible(), "Weekly Error Visible");
        AbstractTest.assertEquals(monthly, monthlyValidationErrorMessageVisible(), "Monthly Error Visible");
    }

    public boolean dailyValidationErrorMessageVisible(){
        return WebDriverUtils.isVisible(LABEL_ERROR_DAILY_XP);
    }

    public boolean weeklyValidationErrorMessageVisible(){
        return WebDriverUtils.isVisible(LABEL_ERROR_WEEKLY_XP);
    }

    public boolean monthlyValidationErrorMessageVisible(){
        return WebDriverUtils.isVisible(LABEL_ERROR_MONTHLY_XP);
    }

    public void validateDepositLimitsIMS(String username, String dayLimit, String weekLimit, String monthLimit){
        IMSPlayerDetailsPage imsPlayerDetailsPage = IMSUtils.navigateToPlayedDetails(username);
        WebDriverUtils.waitFor(2000);
        AbstractTest.assertEquals(dayLimit, imsPlayerDetailsPage.getDayLimit(), "Day limit successfully saved to IMS");
        AbstractTest.assertEquals(weekLimit, imsPlayerDetailsPage.getWeekLimit(), "Week limit successfully saved to IMS");
        AbstractTest.assertEquals(monthLimit, imsPlayerDetailsPage.getMonthLimit(), "Month limit successfully saved to IMS");
    }

    public void validateDepositLimitsFields() {
        /*VALID LIMITS*/
        //daily = weekly = monthly
        setDepositLimits(1, 1, 1);
        checkErrors(false, false, false);

        //daily < weekly < monthly
        setDepositLimits(1, 2, 3);
        checkErrors(false, false, false);

        //daily < weekly, monthly - not set
        setDepositLimits(1, 2, 0);
        checkErrors(false, false, false);

        //daily - not set, weekly < monthly
        setDepositLimits(0, 1, 2);
        checkErrors(false, false, false);

        //daily < monthly, weekly - not set
        setDepositLimits(1, 0, 2);
        checkErrors(false, false, false);

        //daily, weekly - not set, monthly - not set
        setDepositLimits(1, 0, 0);
        checkErrors(false, false, false);

        //daily - not set, weekly, monthly - not set
        setDepositLimits(0, 1, 0);
        checkErrors(false, false, false);

        //daily - not set, weekly - not set, monthly
        setDepositLimits(0, 0, 1);
        checkErrors(false, false, false);

        /*INVALID LIMITS*/
        //daily > monthly, weekly - not set
        setDepositLimits(2, 0, 1);
        checkErrors(true, false, true);

        //daily > weekly, monthly - not set
        setDepositLimits(2, 1, 0);
        checkErrors(true, true, false);

        //daily - not set, weekly > monthly
        setDepositLimits(0, 2, 1);
        checkErrors(false, true, true);

        //daily > weekly > monthly
        setDepositLimits(3, 2, 1);
        checkErrors(true, true, true);
    }
}
