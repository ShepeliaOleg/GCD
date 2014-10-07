package pageObjects.responsibleGaming;


import enums.DepositLimits;
import org.openqa.selenium.WebElement;
import pageObjects.core.AbstractPage;
import utils.RandomUtils;
import utils.TypeUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ResponsibleGamingPage extends AbstractPage{
	private static final String RESPONSIBLE_GAMING_ROOT_XP =                        	"//*[contains(@id,'portlet_responsiblegaming')]";
	private static final String BUTTON_DEPOSIT_XP =                                 	"//button[@id='limitDepositMonthly']";
	private static final String BUTTON_TIME_PER_SESSION_XP =                        	"//button[@id='limitSessionPerSession']";
	private static final String LABEL_SUCCESS_NOTIFICATION_MESSAGE_DEPOSIT_XP =         "//*[@id='saveDepositLimitsForm']//tr[last()]//*[contains(@class,'success')]";
	private static final String LABEL_SUCCESS_NOTIFICATION_MESSAGE_TIMEPERSESSION_XP =  "//*[@id='limitSessionPerSessionStatus']/div[contains(@class, 'success')]";
	private static final String LABEL_ERROR_DAILY_XP =                                  "//*[contains(@class,'day')]//*[contains(@class,'validateFail')]";
	private static final String LABEL_ERROR_WEEKLY_XP =                                 "//*[contains(@class,'week')]//*[contains(@class,'validateFail')]";
	private static final String LABEL_ERROR_MONTHLY_XP =                                "//*[contains(@class,'month')]//*[contains(@class,'validateFail')]";
    private static final String FUTURE_CHANGE_NOTIFICATION_DAILY_XP =               	"//*[@class='depositLimitChange']/div[contains(text(),'daily')]";
    private static final String FUTURE_CHANGE_NOTIFICATION_WEEKLY_XP =              	"//*[@class='depositLimitChange']/div[contains(text(),'weekly')]";
    private static final String FUTURE_CHANGE_NOTIFICATION_MONTHLY_XP =             	"//*[@class='depositLimitChange']/div[contains(text(),'monthly')]";
	public static final String DROPDOWN_DAILY_XP =                                 		"//select[@id='limitDepositDaily']";
	public static final String DROPDOWN_WEEKLY_XP =                                		"//select[@id='limitDepositWeekly']";
	public static final String DROPDOWN_MONTHLY_XP =                              	 	"//select[@id='limitDepositMonthly']";
    private static final String DROPDOWN_TIME_PER_SESSION_XP =                     	 	"//select[@id='limitSessionPerSession']";
	private static final String LINK_SELF_EXCLUDE = 									"//p[@class='self-exclude']/a";

	public ResponsibleGamingPage(){
		super(new String[]{RESPONSIBLE_GAMING_ROOT_XP, BUTTON_DEPOSIT_XP, DROPDOWN_DAILY_XP, DROPDOWN_WEEKLY_XP, DROPDOWN_MONTHLY_XP});
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
		WebDriverUtils.click(BUTTON_DEPOSIT_XP);
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

	public SelfExcludePopup navigateToSelfExclude(){
		WebDriverUtils.click(LINK_SELF_EXCLUDE);
		return new SelfExcludePopup();
	}

	public void submitDepositLimit(){
		submitDepositLimitConfirmButton();
		closeQuestionnaire();
	}

	public void submitTimePerSession(){
		submitTimePerSessionConfirmButton();
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
		submitDepositLimit();
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
