package pageObjects.external.ims;

import enums.Page;
import pageObjects.core.AbstractServerPage;
import springConstructors.AffiliateData;
import utils.WebDriverUtils;
import utils.core.AbstractTest;
import utils.core.WebDriverFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class IMSPlayerDetailsPage extends AbstractServerPage {

	private static final String ROOT_XP=									"//*[@id='playerHeaderZone']";
	private static final String BLOCK_XP=									"//*[@id='ssec_balsheet']//td[contains(text(), 'Balance sheet')]";
	private static final String BALANCES_XP=								"//*[@class='t-data-grid']//th[contains(text(), 'Published balance')]";
	private static final String PLAYER_ONLINE_IMAGE=						"//*[@title='Player is online']";
	private static final String CHECKBOX_ALL_CHANNELS=						"//*[@id='contact_preferences']//tr[1]/td[2]//input";
	private static final String LABEL_PLAYER_KILLED=						"//div[contains(text(), 'Kill player')]";
	private static final String BUTTON_CHANGE_PASSWORD=						"//input[@id='changepwd']";
	private static final String LABEL_COMP_POINTS=							"//tr/td[following-sibling::td[contains(text(), 'Total comps')] and preceding-sibling::td[contains(text(), 'Comp points')]]";
	private static final String LABEL_USER_CURRENCY_SIGN=					"//tr[@id='ssec_balsheet']//td[preceding-sibling::td[contains(text(), 'Total')]]";
	private static final String LABEL_USER_CURRENCY=						"//*[@id='balance_information']//tr[1]/td[1]/span";
	private static final String LABEL_BINGO_GAMING_BALANCE=					"//td[contains(text(), 'Bingo gaming balance')]";
	private static final String LABEL_POKER_GAMING_BALANCE=					"//td[contains(text(), 'Poker gaming balance')]";
	private static final String LABEL_SPORTSBOOK_GAMING_BALANCE=			"//td[contains(text(), 'Sportsbook gaming balance')]";
	private static final String LABEL_INTERNAL_WALLET_TOTAL_REAL_BALANCE=	"//td[contains(text(), 'Internal wallet total real balance')]";
	private static final String LABEL_CASINO_GAMING_BALANCE=				"//td[contains(text(), 'Casino gaming balance')]";
	private static final String LABEL_USERNAME=								"//td[preceding-sibling::td[contains(text(), 'Username:')]][1]";
	private static final String LABEL_PASSWORD=								"//td[preceding-sibling::td[contains(text(), 'Non-encrypted password:')]]";
	private static final String LABEL_WITHDRAWABLE_BALANCE=					"//td[contains(text(), 'Withdrawable balance')]";
	private static final String LABEL_TOTAL_BONUS_BALANCE=					"//td[contains(text(), 'Total bonus balance')]";
	private static final String LABEL_IS_CREDIT_USER=						"//tr/td[preceding-sibling::td[contains(text(), 'Credit limit')]][1]";
    private static final String LABEL_ADVERTISER =                          "//tr/td[preceding-sibling::td[contains(text(), 'Referrer advertiser:')]][1]";
	private static final String LABEL_LOCK_REMOVED =                        "//*[contains(text(), 'Temporary Lock (NTries) has been removed.')]";
    private static final String LINK_CONTACT_PREFERENCES=					"//*[@id='contact_preferences']//a";
	private static final String CHECKBOX_EMAIL=								"//input[@id='communicationoptouts[1][2]']";
	private static final String CHECKBOX_SMS=								"//input[@id='communicationoptouts[2][2]']";
	private static final String CHECKBOX_DIRECT_MAIL=						"//input[@id='communicationoptouts[3][2]']";
	private static final String CHECKBOX_PHONE=								"//input[@id='communicationoptouts[4][2]']";
    private static final String CHECKBOX_DO_NOT_EMAIL=						"//input[@id='communicationoptouts[1][1]']";
    private static final String CHECKBOX_DO_NOT_SMS=						"//input[@id='communicationoptouts[2][1]']";
    private static final String CHECKBOX_DO_NOT_PHONE=						"//input[@id='communicationoptouts[4][1]']";
	private static final String FIELD_FIRST_NAME=							"//*[@id='firstname']";
	private static final String FIELD_LAST_NAME=							"//*[@id='lastname']";
	private static final String FIELD_BIRTH_DATE=							"//*[@id='birthdate']";
	private static final String FIELD_EMAIL=								"//*[@id='email']";
	private static final String FIELD_TITLE=								"//*[@id='title']";
	private static final String FIELD_GENDER=								"//*[@id='gender']";
	private static final String FIELD_CITY=									"//*[@id='city']";
	private static final String FIELD_COUNTRY=								"//*[@id='countrycode']";
	private static final String FIELD_ZIP=									"//*[@id='zip']";
	private static final String FIELD_BILLING_ADDRESS=						"//*[@id='address']";
	private static final String FIELD_PHONE=								"//*[@id='phone']";
	private static final String FIELD_MOBILE=								"//*[@id='cellphone']";
	private static final String FIELD_VERIFICATION_QUESTION=				"//*[@id='verificationquestion']";
	private static final String FIELD_VERIFICATION_ANSWER=					"//*[@id='verificationanswer']";
    private static final String FIELD_CLIENT_TYPE=                          "//*[preceding-sibling::*[contains(text(), 'Sign up client type:')]][1]";
    private static final String FIELD_LAST_LOGIN_DEVICE_TYPE =              "//*[@id='ssec_supinfo' and contains(., 'Last login device type:')]/td[4]";
	private static final String BUTTON_KILL_PLAYER=							"//*[@id='killplayer']";
	private static final String BUTTON_ADD_BONUS= 							"//*[@value='Add bonus']";
    private static final String BUTTON_FAILED_LOGINS =                      "//*[@id='failedlogins']";
	private static final String LINK_CUSTOM_FIELDS=                         "//a[contains(@onclick, 'sec_customs')]";
    private static final String REFERRER_XP=                                "//*[@id='ssec_supinfo']/*[preceding-sibling::*[contains(text(),'Referrer')]]//a";
    private static final String DEVICE_ID_XP =                              "//td[contains(text(),'Sign up serial:')]";
	private static final String FIELD_DAY_LIMIT_XP = 						"//*[@id='depositlimitday']";
	private static final String FIELD_WEEK_LIMIT_XP = 						"//*[@id='depositlimitweek']";
	private static final String FIELD_MONTH_LIMIT_XP = 						"//*[@id='depositlimitmonth']";
	private static final String BUTTON_PLAYER_DETAILS_INFO_XP =				"//input[@id='wageringinfo']";

	public IMSPlayerDetailsPage(){
		super(new String[]{ROOT_XP, BUTTON_FAILED_LOGINS});
	}

	public HashMap getInternalTagsData(HashMap hashMap){
		hashMap.put("casino-gaming-balance", getCasinoGamingBalance());
		hashMap.put("bingo-gaming-balance", getBingoGamingBalance());
		hashMap.put("poker-gaming-balance", getPokerGamingBalance());
		hashMap.put("sportsbook-gaming-balance", getSportsbookGamingBalance());
		hashMap.put("total-bonus-balance", getTotalBonusBalance());
		hashMap.put("withdrawable-balance", getWithdrawableBalance());
		hashMap.put("internal-wallet-total-real-balance", getInternalWalletTotalRealBalance());
		hashMap.put("user-currency", getCurrencySign());
		hashMap.put("user-name", getUsername());
		hashMap.put("comp-points", getCompPoints());
        hashMap.put("user-first-name", getFirstName());
        hashMap.put("user-last-name", getLastName());

		return hashMap;
	}

	public ArrayList<String> getRegisterData(){
		ArrayList<String> data=new ArrayList<>();
		data.add(getTitle());
		data.add(getGender());
		data.add(getFirstName());
		data.add(getLastName());
		data.add(getBirthDate());
		data.add(getEmail());
		data.add(getCountryCode());
		data.add(getCity());
		data.add(getBillingAddress());
		data.add(getPostcode());
		data.add(getPhone());
//		data.add(getMobile());
		data.add(getUsername());
//		data.add(getPassword());
		//data.add(getVerificationQuestion());
		//data.add(getVerificationAnswer());
		data.add(getCurrency());
		return data;
	}

    public ArrayList<String> getUMDData(){
        ArrayList<String> data=new ArrayList<>();
        data.add(getTitle());
        data.add(getFirstName());
        data.add(getLastName());
        data.add(getEmail());
        data.add(getCountryCode());
        data.add(getCity());
        data.add(getBillingAddress());
        data.add(getPostcode());
        data.add(getPhone());
//		data.add(getMobile());
        return data;
    }

    public void checkAffiliateData(String advertiser, String banner, String profile, String url, String creferrer, boolean creferrerIsExists){
        checkAdvertiser(advertiser+" ("+profile+")");
        checkUrl(url);
        checkCreferrer(creferrer, creferrerIsExists);
        checkBanner(advertiser, banner, profile);
    }

    public void  checkCreferrer(String creferrer) {
        checkCreferrer(creferrer, true);
    }

    private void checkCreferrer(String creferrer, boolean creferrerIsExists) {
        if (!creferrer.isEmpty()) {
            for (String item : parseCreferrer(creferrer)) {
                List<String> creferrerNameValue = getCreferrerNameValue(item);
                if (creferrerIsExists) {
                    checkCreferrerCustomField(creferrerNameValue.get(0), creferrerNameValue.get(1));
                } else {
                    checkNoCreferrerCustomField(creferrerNameValue.get(0));
                }
            }
        }
    }

    public void checkAffiliateData(String advertiser, String noProfile){
        checkAdvertiser(advertiser + " (" + noProfile + ")");
    }

    private void checkBanner(String advertiser, String banner, String profile){
        if (banner.isEmpty()) {
            banner = "-";
        }
        WebDriverUtils.click(WebDriverFactory.getServerDriver(), "//a[contains(text(), '"+advertiser+"')]");
        IMSAffiliateIframe imsAffiliateIframe = new IMSAffiliatePage().navigateToAffiliateIframe();
        AbstractTest.assertEquals(banner, imsAffiliateIframe.getLabelBanner(), "Banner");
        AbstractTest.assertEquals(profile, imsAffiliateIframe.getLabelProfile(), "Profile");
    }

    private void checkAdvertiser(String advertiser){
        AbstractTest.assertEquals(advertiser, getAdvertiser(), "Advertiser");
    }

    private String getAdvertiser(){
        return getTextAndTrim(LABEL_ADVERTISER);
    }

    private void checkUrl(String url) {
        if (!url.isEmpty()) {
            if (WebDriverUtils.isElementVisible(WebDriverFactory.getServerDriver(), REFERRER_XP, 1)) {
                String imsUrl = WebDriverUtils.getAttribute(WebDriverFactory.getServerDriver(), REFERRER_XP, "href");
                AbstractTest.assertEquals("javascript:displ('" + url + "');", imsUrl, "Url");
            } else {
                AbstractTest.addError("Referrer URL is not by xpath: "+REFERRER_XP);
            }
        } else {
            AbstractTest.assertFalse(WebDriverUtils.isElementVisible(WebDriverFactory.getServerDriver(), REFERRER_XP, 1), "Url value is not present on player details page, because it was not sent.");
        }
    }

    private void checkCreferrerCustomField(String name, String value){
        String nameXpath = "*[contains(text(), '"+name+"')]";
        if (!WebDriverUtils.isVisible(WebDriverFactory.getServerDriver(), "//"+ nameXpath, 0)) {
            WebDriverUtils.click(WebDriverFactory.getServerDriver(), LINK_CUSTOM_FIELDS);
            AbstractTest.assertTrue(WebDriverUtils.isVisible(WebDriverFactory.getServerDriver(), "//" + nameXpath, 1), "Custom field '//" + nameXpath + "' is visible -");
        }
        AbstractTest.assertTrue(WebDriverUtils.isVisible(WebDriverFactory.getServerDriver(), "//*[preceding-sibling::" + nameXpath + " and contains(text(), '" + value + "')]"), "Parameters in custom fields are present");
    }

    private void checkNoCreferrerCustomField(String name){
        String nameXpath = "//*[contains(text(), '"+name+"')]";
        if (!WebDriverUtils.isVisible(WebDriverFactory.getServerDriver(), nameXpath, 0)){
            WebDriverUtils.click(WebDriverFactory.getServerDriver(), LINK_CUSTOM_FIELDS);
        }
        AbstractTest.assertFalse(WebDriverUtils.isVisible(WebDriverFactory.getServerDriver(), nameXpath, 1), "Non-existing custom field was created");
    }

    private List<String> parseCreferrer(String creferrerFull) {
        List<String> creferrerList = new ArrayList<>();
        if (creferrerFull.contains(AffiliateData.ASCII_CODE_SEMICOLON)) {
            creferrerList = Arrays.asList(creferrerFull.split(AffiliateData.ASCII_CODE_SEMICOLON));
        } else if (creferrerFull.contains(AffiliateData.SEMICOLON)) {
            creferrerList = Arrays.asList(creferrerFull.split(AffiliateData.SEMICOLON));
        } else {
            creferrerList.add(creferrerFull);
        }
        return creferrerList;
    }

    private List<String> getCreferrerNameValue(String creferrer) {
        if (!creferrer.contains(AffiliateData.ASCII_CODE_COLON) && !creferrer.contains(AffiliateData.COLON)) {
            AbstractTest.failTest("Creferrer should be properly defined as 'name:value' pair. Actual creferrer value is '" + creferrer + "'");
        }
        if (creferrer.contains(AffiliateData.ASCII_CODE_COLON)) {
            return Arrays.asList(creferrer.split(AffiliateData.ASCII_CODE_COLON));
        } else {
            return Arrays.asList(creferrer.split(AffiliateData.COLON));
        }
    }

	private String getUsername(){
		return getTextAndTrim(LABEL_USERNAME);
	}

	private String getCurrencySign(){
		return Character.toString(getTextAndTrim(LABEL_USER_CURRENCY_SIGN).charAt(0));
	}

	private String getCurrency(){
		return getTextAndTrim(LABEL_USER_CURRENCY).replaceAll("[^A-Z]", "");
	}

	private String getTotalBonusBalance(){
		return getDigitsAndAddCurrency(getTextAndTrim(LABEL_TOTAL_BONUS_BALANCE));
	}

	private String getWithdrawableBalance(){
		return getDigitsAndAddCurrency(getTextAndTrim(LABEL_WITHDRAWABLE_BALANCE));
	}

	private String getCasinoGamingBalance(){
		return getDigitsAndAddCurrency(getTextAndTrim(LABEL_CASINO_GAMING_BALANCE));
	}

	private String getCompPoints(){
		return removeDecimal(getTextAndTrim(LABEL_COMP_POINTS));
	}

	private String getBingoGamingBalance(){
		return getDigitsAndAddCurrency(getTextAndTrim(LABEL_BINGO_GAMING_BALANCE));
	}

	private String getPokerGamingBalance(){
		return getDigitsAndAddCurrency(getTextAndTrim(LABEL_POKER_GAMING_BALANCE));
	}

	private String getSportsbookGamingBalance(){
		return getDigitsAndAddCurrency(getTextAndTrim(LABEL_SPORTSBOOK_GAMING_BALANCE));
	}

	private String getInternalWalletTotalRealBalance(){
		return getDigitsAndAddCurrency(getTextAndTrim(LABEL_INTERNAL_WALLET_TOTAL_REAL_BALANCE));
	}

	private String getFirstName(){
		return getInputContent(FIELD_FIRST_NAME);
	}

	private String getLastName(){
		return getInputContent(FIELD_LAST_NAME);
	}

	private String getBirthDate(){
		return getInputContent(FIELD_BIRTH_DATE);
	}

	private String getEmail(){
		return getInputContent(FIELD_EMAIL);
	}

	private String getTitle(){
		return getInputContent(FIELD_TITLE);
	}

	private String getGender(){
		return getDropdownValue(FIELD_GENDER);
	}

	private String getCity(){
		return getInputContent(FIELD_CITY);
	}

	private String getCountry(){
		return getDropdownValue(FIELD_COUNTRY);
	}

    private String getCountryCode(){
        return WebDriverUtils.getDropdownSelectedOptionValue(WebDriverFactory.getServerDriver(), FIELD_COUNTRY);
    }


    private String getPostcode(){
		return getInputContent(FIELD_ZIP);
	}

	//house number + Address1 + Address2
	private String getBillingAddress(){
		return getInputContent(FIELD_BILLING_ADDRESS);
	}

	private String getPhone(){
		return getInputContent(FIELD_PHONE);
	}

	private String getMobile(){
		return getInputContent(FIELD_MOBILE);
	}

	public String getPassword(){
		return getTextAndTrim(LABEL_PASSWORD);
	}

	private String getVerificationQuestion(){
		return getInputContent(FIELD_VERIFICATION_QUESTION);
	}

	private String getVerificationAnswer(){
		return getInputContent(FIELD_VERIFICATION_ANSWER);
	}

	public boolean isPlayerOnline(){
		return WebDriverUtils.isVisible(WebDriverFactory.getServerDriver(), PLAYER_ONLINE_IMAGE);
	}

    public String getClientType() {
        return WebDriverUtils.getElementText(WebDriverFactory.getServerDriver(), FIELD_CLIENT_TYPE);
    }

	public boolean getAllChannelsCheckboxState(){
		boolean result=true;
		if(!WebDriverUtils.isVisible(WebDriverFactory.getServerDriver(), CHECKBOX_ALL_CHANNELS)){
			clickContactPreferences();
		}
		boolean email=getCheckboxState(CHECKBOX_EMAIL);
		boolean sms=getCheckboxState(CHECKBOX_SMS);
		boolean dirMail=getCheckboxState(CHECKBOX_DIRECT_MAIL);
		boolean phone=getCheckboxState(CHECKBOX_PHONE);
		if(!email || !sms || !dirMail || !phone){
			result=false;
		}
		return result;
	}

    public boolean[] getNotificationCheckboxesState(){
        boolean result[]=new boolean[3];
        if(!WebDriverUtils.isVisible(WebDriverFactory.getServerDriver(), CHECKBOX_ALL_CHANNELS)){
            clickContactPreferences();
        }
        result[0] =getCheckboxState(CHECKBOX_DO_NOT_EMAIL);
        result[1]= getCheckboxState(CHECKBOX_DO_NOT_SMS);
        result[2]= getCheckboxState(CHECKBOX_DO_NOT_PHONE);
        return result;
    }

	public void sendPushLogout(){
		WebDriverUtils.click(WebDriverFactory.getServerDriver(), BUTTON_KILL_PLAYER);
		WebDriverUtils.acceptJavaScriptAlert(WebDriverFactory.getServerDriver());
		WebDriverUtils.isVisible(WebDriverFactory.getServerDriver(), LABEL_PLAYER_KILLED);
	}

	public void changePassword(String password){
		changePassword(password, false);
	}

	public void changePassword(String password, boolean checkboxDontRequirePasswordChangeUponNextLogon){
		String mainWindow = WebDriverUtils.getWindowHandle(WebDriverFactory.getServerDriver());
		IMSChangePassPopup imsChangePassPopup = openChangePassPopup();
		imsChangePassPopup.changePassword(password, checkboxDontRequirePasswordChangeUponNextLogon);
		imsChangePassPopup.close(WebDriverFactory.getServerDriver(), mainWindow);
	}

    public void resetFailedLogins(){
        WebDriverUtils.click(WebDriverFactory.getServerDriver(), BUTTON_FAILED_LOGINS);
        WebDriverUtils.acceptJavaScriptAlert(WebDriverFactory.getServerDriver());
        WebDriverUtils.isVisible(WebDriverFactory.getServerDriver(), LABEL_LOCK_REMOVED);
    }

	public IMSPlayerBonusInfoPage clickPlayerDetailsInfo(){
		WebDriverUtils.click(WebDriverFactory.getServerDriver(), BUTTON_PLAYER_DETAILS_INFO_XP);
		return new IMSPlayerBonusInfoPage();
	}

    public void addBonus(Page pushMessages, String amount, int quantity){
        for(int i=0;i<quantity;i++){
            addBonus(pushMessages, amount);
        }
    }

	public IMSPlayerDetailsPage addBonus(Page pushMessages, String amount){
		IMSAddBonusPage imsAddBonusPage = clickAddBonus();
		return imsAddBonusPage.addBonus(pushMessages, amount);
    }

	private String getDigitsAndAddCurrency(String label){
		String digitsWithPrice=getCurrencySign() + label.replaceAll("[^0-9.]", "");
		if(!digitsWithPrice.contains(".")){
			digitsWithPrice=digitsWithPrice + ".00";
		}
		return digitsWithPrice;
	}

	private String removeCurrency(String label){
		label=label.replace(getCurrencySign(), "");
		return label;
	}

	private String removeDecimal(String label){
		label=label.replaceAll("\\.0*$", "");
		return label;
	}

	private boolean getCheckboxState(String xpath){
		return WebDriverUtils.getCheckBoxState(WebDriverFactory.getServerDriver(), xpath);
	}

	private void clickContactPreferences(){
		WebDriverUtils.click(WebDriverFactory.getServerDriver(), LINK_CONTACT_PREFERENCES);
	}

	private String getTextAndTrim(String xpath){
		return WebDriverUtils.getElementText(WebDriverFactory.getServerDriver(), xpath).trim();
	}

	private String getInputContent(String xpath){
		return WebDriverUtils.getInputFieldText(WebDriverFactory.getServerDriver(), xpath).replaceAll("\\[|\\]|,\\s", "");
	}

	private String getDropdownValue(String xpath){
		return WebDriverUtils.getDropdownSelectedOptionText(WebDriverFactory.getServerDriver(), xpath);
	}

    private IMSChangePassPopup openChangePassPopup(){

		WebDriverUtils.click(WebDriverFactory.getServerDriver(), BUTTON_CHANGE_PASSWORD);
        return new IMSChangePassPopup(getMainWindowHandle());
    }

    private IMSAddBonusPage clickAddBonus(){
        WebDriverUtils.click(WebDriverFactory.getServerDriver(), BUTTON_ADD_BONUS);
        return new IMSAddBonusPage();
    }

    public String getDeviceIdRegistration() {
        String value = WebDriverUtils.getElementText(WebDriverFactory.getServerDriver(), WebDriverUtils.getFollowingElement(DEVICE_ID_XP, 1));
        return value.substring(0, value.indexOf(" "));
    }

    public String getDeviceIdLogin() {
        return WebDriverUtils.getElementText(WebDriverFactory.getServerDriver(), WebDriverUtils.getFollowingElement(DEVICE_ID_XP, 3));
    }

	public String getDayLimit () {
		return getInputContent(FIELD_DAY_LIMIT_XP);
	}

	public String getWeekLimit () {
		return getInputContent(FIELD_WEEK_LIMIT_XP);
	}

	public String getMonthLimit () {
		return getInputContent(FIELD_MONTH_LIMIT_XP);
	}


    public String getDeviceType() {
        WebDriverUtils.waitFor(500);
        return WebDriverUtils.getElementText(WebDriverFactory.getServerDriver(), FIELD_LAST_LOGIN_DEVICE_TYPE);
    }
}
