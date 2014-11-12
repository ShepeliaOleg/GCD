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
	private static final String BUTTON_LIMITS_XP =                                 	    "//*[contains(@class, 'fn-deposit-limits')]//button";
	private static final String BUTTON_TIME_PER_SESSION_XP =                        	"//*[contains(@class, 'fn-session-timer')]//button";
	private static final String LABEL_SUCCESS_NOTIFICATION_MESSAGE_DEPOSIT_XP =         "//*[@id='saveDepositLimitsForm']//tr[last()]//*[contains(@class,'success')]";
	private static final String LABEL_SUCCESS_NOTIFICATION_MESSAGE_TIMEPERSESSION_XP =  "//*[@id='limitSessionPerSessionStatus']/div[contains(@class, 'success')]";
	private static final String LABEL_ERROR_DAILY_XP =                                  "//*[contains(@class,'day')]//*[contains(@class,'validateFail')]";
	private static final String LABEL_ERROR_WEEKLY_XP =                                 "//*[contains(@class,'week')]//*[contains(@class,'validateFail')]";
	private static final String LABEL_ERROR_MONTHLY_XP =                                "//*[contains(@class,'month')]//*[contains(@class,'validateFail')]";
	public static final String DROPDOWN_DAILY_XP =                                 		"//select[@id='daydepositlimit']";
	public static final String DROPDOWN_WEEKLY_XP =                                		"//select[@id='weekdepositlimit']";
	public static final String DROPDOWN_MONTHLY_XP =                              	 	"//select[@id='monthdepositlimit']";
    private static final String DROPDOWN_TIME_PER_SESSION_XP =                     	 	"//select[@id='sessionduration']";

	public ResponsibleGamingPage(){
		super(new String[]{RESPONSIBLE_GAMING_ROOT_XP});
	}

    // currency

    private String getCurrencySign(String xpath) {
        return WebDriverUtils.getElementText(xpath);
    }

    public String getDailyLimitCurrency() {
        return getCurrencySign(DROPDOWN_DAILY_XP + "/..");
    }

    public String getWeeklyLimitCurrency() {
        return getCurrencySign(DROPDOWN_WEEKLY_XP + "/..");
    }

    public String getMonthlyLimitCurrency() {
        return getCurrencySign(DROPDOWN_MONTHLY_XP + "/..");
    }

    /*
    time per session
     */

	private void setTimePerSession(String timePerSessionValue){
		WebDriverUtils.setDropdownOptionByText(DROPDOWN_TIME_PER_SESSION_XP, timePerSessionValue);
	}

	private String getTimePerSessionSelectedOption(){
		return WebDriverUtils.getDropdownSelectedOptionText(DROPDOWN_TIME_PER_SESSION_XP);
	}

	private List<String> getTimePerSessionLimitOptionsText(){
		return WebDriverUtils.getDropdownOptionsText(DROPDOWN_TIME_PER_SESSION_XP);
	}

    /*
    confirmation buttons
     */

	private void submitDepositLimitConfirmButton(){
		WebDriverUtils.click(BUTTON_LIMITS_XP);
	}

	public void submitTimePerSessionConfirmButton(){
		WebDriverUtils.click(BUTTON_TIME_PER_SESSION_XP);
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
        AbstractTest.assertEquals(daily, dailyValidationErrorMessageVisible(), "Daily Error Visible");
        AbstractTest.assertEquals(weekly, weeklyValidationErrorMessageVisible(), "Weekly Error Visible");
        AbstractTest.assertEquals(monthly, monthlyValidationErrorMessageVisible(), "Monthly Error Visible");
	}

}
