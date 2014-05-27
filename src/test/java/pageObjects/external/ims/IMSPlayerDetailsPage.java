package pageObjects.external.ims;

import enums.Page;
import pageObjects.base.AbstractPage;
import utils.WebDriverUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * User: sergiich
 * Date: 7/24/13
 */

public class IMSPlayerDetailsPage extends AbstractPage{

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
	private static final String BUTTON_KILL_PLAYER=							"//*[@id='killplayer']";
	private static final String BUTTON_ADD_BONUS= 							"//*[@value='Add bonus']";
    private static final String BUTTON_FAILED_LOGINS =                      "//*[@id='failedlogins']";
    private static final String LINK_CUSTOM_FIELDS=                         "//a[contains(@onclick, 'sec_customs')]";

	public IMSPlayerDetailsPage(){
		super(new String[]{ROOT_XP, BLOCK_XP, BALANCES_XP});
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
		ArrayList<String> data=new ArrayList<String>();
		data.add(getTitle());
		data.add(getGender());
		data.add(getFirstName());
		data.add(getLastName());
		data.add(getBirthDate());
		data.add(getEmail());
		data.add(getCountry());
		data.add(getCity());
		data.add(getBillingAddress());
		data.add(getPostcode());
		data.add(getPhone());
		data.add(getMobile());
		data.add(getUsername());
		data.add(getPassword());
		data.add(getVerificationQuestion());
		data.add(getVerificationAnswer());
		data.add(getCurrency());
		return data;
	}

    public void checkAffiliateData(String advertiser, String profile, String bTag, String bTagRes, String banner, String url){
        checkAdvertiser(advertiser+" "+"("+profile+")");
        checkBTAG(bTag, bTagRes);
        checkBanner(advertiser, banner);
    }

    private void checkBanner(String advertiser, String banner){
        WebDriverUtils.click("//a[contains(text(), '"+advertiser+"')]");
        String imsBanner = new IMSAffiliatePage().navigateToAffiliateIframe().getLabelBanner();
        if(!imsBanner.equals(banner)){
            WebDriverUtils.runtimeExceptionWithLogs("Banner parameter was not correct - <div>Expected: "+banner+"</div><div>Actual: "+imsBanner+"</div>");
        }
    }

    private void checkAdvertiser(String advertiser){
        String imsAdvertiser=getAdvertiser();
        if(!advertiser.equals(imsAdvertiser)){
            WebDriverUtils.runtimeExceptionWithLogs("Advertiser was wrong. <div>Expected: "+advertiser+"Actual: "+imsAdvertiser+"</div>");
        }
    }

    private String getAdvertiser(){
        return getTextAndTrim(LABEL_ADVERTISER);
    }

    private void checkBTAG(String bTag, String bTagRes){
        WebDriverUtils.click(LINK_CUSTOM_FIELDS);
        if(!WebDriverUtils.isVisible("//*[contains(text(), '"+bTag+"')]")||!WebDriverUtils.isVisible("//*[contains(text(), '"+bTagRes+"')]")){
            WebDriverUtils.runtimeExceptionWithLogs("Parameters in custom fields were not found");
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
		return WebDriverUtils.isVisible(PLAYER_ONLINE_IMAGE);
	}

	public boolean getAllChannelsCheckboxState(){
		boolean result=true;
		if(!WebDriverUtils.isVisible(CHECKBOX_ALL_CHANNELS)){
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

	public void sendPushLogout(){
		WebDriverUtils.click(BUTTON_KILL_PLAYER);
		WebDriverUtils.acceptJavaScriptAlert();
		WebDriverUtils.waitForElement(LABEL_PLAYER_KILLED);
	}

	public void changePassword(String password){
		IMSChangePassPopup imsChangePassPopup = openChangePassPopup();
		imsChangePassPopup.changePassword(password);
		imsChangePassPopup.closePopup();
	}

    public void resetFailedLogins(){
        WebDriverUtils.click(BUTTON_FAILED_LOGINS);
        WebDriverUtils.acceptJavaScriptAlert();
        WebDriverUtils.waitForElement(LABEL_LOCK_REMOVED);
    }

	private IMSChangePassPopup openChangePassPopup(){
		WebDriverUtils.click(BUTTON_CHANGE_PASSWORD);
		return new IMSChangePassPopup(getMainWindowHandle());
	}

	private IMSAddBonusPage clickAddBonus(){
		WebDriverUtils.click(BUTTON_ADD_BONUS);
		return new IMSAddBonusPage();
	}

	public IMSPlayerDetailsPage addBonus(Page pushMessages, String amount){
		IMSAddBonusPage imsAddBonusPage = clickAddBonus();
		imsAddBonusPage.addBonus(pushMessages, amount);
		return new IMSPlayerDetailsPage();
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
		return WebDriverUtils.getCheckBoxState(xpath);
	}

	private void clickContactPreferences(){
		WebDriverUtils.click(LINK_CONTACT_PREFERENCES);
	}

	private String getTextAndTrim(String xpath){
		return WebDriverUtils.getElementText(xpath).trim();
	}

	private String getInputContent(String xpath){
		return WebDriverUtils.getInputFieldText(xpath).replaceAll("\\[|\\]|,\\s", "");
	}

	private String getDropdownValue(String xpath){
		return WebDriverUtils.getDropdownSelectedOptionText(xpath);
	}

}
