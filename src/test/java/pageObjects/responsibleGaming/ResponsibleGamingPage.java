package pageObjects.responsibleGaming;


import org.openqa.selenium.WebElement;
import pageObjects.base.AbstractPage;
import utils.RandomUtils;
import utils.TypeUtils;
import utils.WebDriverUtils;

import java.util.Collections;
import java.util.List;

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
	private static final String DROPDOWN_DAILY_XP =                                 	"//select[@id='limitDepositDaily']";
	private static final String DROPDOWN_WEEKLY_XP =                                	"//select[@id='limitDepositWeekly']";
	private static final String DROPDOWN_MONTHLY_XP =                               	"//select[@id='limitDepositMonthly']";
    private static final String DROPDOWN_TIME_PER_SESSION_XP =                     	 	"//select[@id='limitSessionPerSession']";
	private static final String LINK_SELF_EXCLUDE = 									"//p[@class='self-exclude']/a";

	public ResponsibleGamingPage(){
		super(new String[]{RESPONSIBLE_GAMING_ROOT_XP, BUTTON_DEPOSIT_XP, DROPDOWN_DAILY_XP, DROPDOWN_WEEKLY_XP, DROPDOWN_MONTHLY_XP});
	}

    private void setDepositLimit(String xpath, String limitValue) {
        if (limitValue.equalsIgnoreCase("0")) {
           WebDriverUtils.setDropdownOptionByValue(xpath, "0");
        } else {
           WebDriverUtils.setDropdownOptionByText(xpath, limitValue);
        }
    }

	private void setDailyDepositLimit(String depositLimitValue){
		setDepositLimit(DROPDOWN_DAILY_XP, depositLimitValue);
	}

	private void setWeeklyDepositLimit(String depositLimitValue){
        setDepositLimit(DROPDOWN_WEEKLY_XP, depositLimitValue);
	}

	private void setMonthlyDepositLimit(String depositLimitValue){
		setDepositLimit(DROPDOWN_MONTHLY_XP, depositLimitValue);
	}

	private WebElement getDailyDepositLimitSelectedOption(){
		return WebDriverUtils.getDropdownSelectedOption(DROPDOWN_DAILY_XP);
	}

	private WebElement getWeeklyDepositLimitSelectedOption(){
		return WebDriverUtils.getDropdownSelectedOption(DROPDOWN_WEEKLY_XP);
	}

	private WebElement getMonthlyDepositLimitSelectedOption(){
		return WebDriverUtils.getDropdownSelectedOption(DROPDOWN_MONTHLY_XP);
	}


	private List<WebElement> getDailyDepositLimitOptions(){
		return WebDriverUtils.getDropdownOptions(DROPDOWN_DAILY_XP);
	}

	private List<WebElement> getWeeklyDepositLimitOptions(){
		return WebDriverUtils.getDropdownOptions(DROPDOWN_WEEKLY_XP);
	}

	private List<WebElement> getMonthlyDepositLimitOptions(){
		return WebDriverUtils.getDropdownOptions(DROPDOWN_MONTHLY_XP);
	}

    private WebElement getMaximumDepositLimitOption(String xpath){
        return WebDriverUtils.getDropdownLastOption(xpath);
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

	private WebElement getTimePerSessionSelectedOption(){
		return WebDriverUtils.getDropdownSelectedOption(DROPDOWN_TIME_PER_SESSION_XP);
	}

	private List<WebElement> getTimePerSessionLimitOptions(){
		return WebDriverUtils.getDropdownOptions(DROPDOWN_TIME_PER_SESSION_XP);
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

    public boolean dailyFutureChangeNotificationVisible(){
        return WebDriverUtils.isVisible(FUTURE_CHANGE_NOTIFICATION_DAILY_XP);
    }

    public boolean weeklyFutureChangeNotificationVisible(){
        return WebDriverUtils.isVisible(FUTURE_CHANGE_NOTIFICATION_WEEKLY_XP);
    }

    public boolean monthlyFutureChangeNotificationVisible(){
        return WebDriverUtils.isVisible(FUTURE_CHANGE_NOTIFICATION_MONTHLY_XP);
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
			questionnairePopup.clickClose();
		}
	}

	public void setDepositLimits(String dailyLimit, String weeklyLimit, String monthlyLimit){
		setDailyDepositLimit(dailyLimit);
		setWeeklyDepositLimit(weeklyLimit);
		setMonthlyDepositLimit(monthlyLimit);
	}

    private List<WebElement> getRandomLimitsList(int number){
		List<WebElement> allOptions=getDailyDepositLimitOptions();

        if (allOptions.size() < number) {
			WebDriverUtils.runtimeExceptionWithLogs("Requested number of random elements cannot be more than limit options available");
        }

		List<WebElement> randomOptionsWebElements=RandomUtils.getRandomElementsFromList(allOptions, number);

		return randomOptionsWebElements;
	}
//
//    public List<String> getSortedRandomLimitsList() {
//        return getSortedRandomLimitsList(3);
//    }

//    public List<String> getSortedRandomLimitsList(int number) {
//        List<WebElement> randomOptionsWebElements = getRandomLimitsList(number);
//
//        return sortLimits(randomOptionsWebElements);
//    }

//    private List<String> sortLimits(List<WebElement> options) {
//        List<Integer> optionsInteger = TypeUtils.convertWebElementListToIntegerList(options);
//
//        Collections.sort(optionsInteger);
//
//        return TypeUtils.convertIntegerListToStringList(optionsInteger);
//    }

	public void setRandomTimePerSession(){
		List<WebElement> allOptions=getTimePerSessionLimitOptions();
		WebElement randomOptionsWebElements=RandomUtils.getRandomElementsFromList(allOptions, 1).get(0);

		setTimePerSession(randomOptionsWebElements.getText());
	}

	public List<String> getSortedRandomLimitsList() {
		return getSortedRandomLimitsList(4);
	}

	public List<String> getSortedRandomLimitsList(int number) {
		List<WebElement> randomOptionsWebElements = getRandomLimitsList(number);

		return sortLimits(randomOptionsWebElements);
	}

	private List<String> sortLimits(List<WebElement> options) {
		List<Integer> optionsInteger = TypeUtils.convertWebElementListToIntegerList(options);

		Collections.sort(optionsInteger);

		return TypeUtils.convertIntegerListToStringList(optionsInteger);
	}


}
