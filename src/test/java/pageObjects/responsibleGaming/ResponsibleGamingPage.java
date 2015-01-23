package pageObjects.responsibleGaming;


import enums.DepositLimits;
import org.openqa.selenium.WebElement;
import pageObjects.core.AbstractPortalPage;
import utils.RandomUtils;
import utils.TypeUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ResponsibleGamingPage extends AbstractPortalPage {
	private static final String RESPONSIBLE_GAMING_ROOT_XP =                        	"//*[contains(@class,'portlet-ngresponsiblegaming')]";
    private static final String SESSION_TIMER_FORM_XP =                                 RESPONSIBLE_GAMING_ROOT_XP + "//*[contains(@class,'fn-form-session-timer')]";
    private static final String DEPOSIT_LIMITS_FORM_XP =                                RESPONSIBLE_GAMING_ROOT_XP + "//*[contains(@class,'fn-form-limits')]";

    private static final String DROPDOWN_SESSION_TIMER_XP =                     	 	"//select[@id='sessionduration']";

    public static final String DROPDOWN_DAILY_XP =                                 		"//select[@id='daydepositlimit']";
    public static final String DROPDOWN_WEEKLY_XP =                                		"//select[@id='weekdepositlimit']";
    public static final String DROPDOWN_MONTHLY_XP =                              	 	"//select[@id='monthdepositlimit']";

    private static final String BUTTON_SESSION_TIMER_XP =                        	    SESSION_TIMER_FORM_XP + "//button";
    private static final String BUTTON_DEPOSIT_LIMITS_XP =                              DEPOSIT_LIMITS_FORM_XP + "//button";

    private static final String LABEL_SUCCESS_NOTIFICATION_MESSAGE_DEPOSIT_XP =         "//*[@id='saveDepositLimitsForm']//tr[last()]//*[contains(@class,'success')]";
    private static final String LABEL_SUCCESS_NOTIFICATION_MESSAGE_TIMEPERSESSION_XP =  "//*[@id='limitSessionPerSessionStatus']/div[contains(@class, 'success')]";

    private static final String TOOLTIP_ERROR_XP =                                      "//*[contains(@class,'error-tooltip')]";
    private static final String LABEL_ERROR_DAILY_XP =                                  "//*[contains(@class,'daily')]" + TOOLTIP_ERROR_XP;
    private static final String LABEL_ERROR_WEEKLY_XP =                                 "//*[contains(@class,'weekly')]" + TOOLTIP_ERROR_XP;
	private static final String LABEL_ERROR_MONTHLY_XP =                                "//*[contains(@class,'monthly')]" + TOOLTIP_ERROR_XP;

	public ResponsibleGamingPage(){
		super(new String[]{RESPONSIBLE_GAMING_ROOT_XP});
	}

    // currency

    private String getCurrencySign(String xpath) {
        String selectedText = WebDriverUtils.getDropdownSelectedOptionText(xpath);
        return TypeUtils.getBalanceCurrency(selectedText);
    }

    public String getDailyLimitCurrency() {
        return getCurrencySign(DROPDOWN_DAILY_XP);
    }

    public String getWeeklyLimitCurrency() {
        return getCurrencySign(DROPDOWN_WEEKLY_XP);
    }

    public String getMonthlyLimitCurrency() {
        return getCurrencySign(DROPDOWN_MONTHLY_XP);
    }

    /*
    time per session
     */

	private void setTimePerSession(String timePerSessionValue){
		WebDriverUtils.setDropdownOptionByText(DROPDOWN_SESSION_TIMER_XP, timePerSessionValue);
	}

	private String getTimePerSessionSelectedOption(){
		return WebDriverUtils.getDropdownSelectedOptionText(DROPDOWN_SESSION_TIMER_XP);
	}

	private List<String> getTimePerSessionLimitOptionsText(){
		return WebDriverUtils.getDropdownOptionsText(DROPDOWN_SESSION_TIMER_XP);
	}

    /*
    confirmation buttons
     */

	private void submitDepositLimitConfirmButton(){
		WebDriverUtils.click(BUTTON_DEPOSIT_LIMITS_XP);
	}

	public void submitTimePerSessionConfirmButton(){
		WebDriverUtils.click(BUTTON_SESSION_TIMER_XP);
	}

	public boolean depositsChangedSuccessfullyMessageVisible(){
		return WebDriverUtils.isVisible(LABEL_SUCCESS_NOTIFICATION_MESSAGE_DEPOSIT_XP);
	}

	public boolean timePerSessionChangedSuccessfullyMessageVisible(){
		return WebDriverUtils.isVisible(LABEL_SUCCESS_NOTIFICATION_MESSAGE_TIMEPERSESSION_XP);
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

	private void closeQuestionnaire(){
		if(WebDriverUtils.isVisible(QuestionnairePopup.BUTTON_SUBMIT_XP)){
			QuestionnairePopup questionnairePopup=new QuestionnairePopup();
			questionnairePopup.closePopup();
		}
	}

	public void setRandomTimePerSession(){
		List<String> allOptions= getTimePerSessionLimitOptionsText();
		String randomOptionText=RandomUtils.getRandomElementsFromList(allOptions, 1).get(0);

		setTimePerSession(randomOptionText);
        submitTimePerSessionConfirmButton();
        new TimePerSessionNotification(randomOptionText);
	}

	private List<String> sortLimits(List<WebElement> options) {
		List<Integer> optionsInteger = TypeUtils.convertWebElementListToIntegerList(options);

		Collections.sort(optionsInteger);

		return TypeUtils.convertIntegerListToStringList(optionsInteger);
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
		submitDepositLimitConfirmButton();
	}

	private int parseLimit(DepositLimits type){
		return Integer.parseInt(getRandomLimit(type));
	}

	private void setDepositLimit(DepositLimits type, String limit){
		WebDriverUtils.setDropdownOptionByValue(type.getXpath(), limit);
	}

	private void setDepositLimitNotDefined(DepositLimits type){
		setDepositLimit(type, "0");
	}

	private List<String> getDepositLimits(DepositLimits type){
		return WebDriverUtils.getDropdownOptionsValue(type.getXpath());
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

}
