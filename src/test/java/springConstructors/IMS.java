package springConstructors;

import enums.ConfiguredPages;
import enums.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.SkipException;
import pageObjects.external.ims.*;
import pageObjects.inbox.InboxPage;
import utils.NavigationUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;
import utils.core.WebDriverObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class IMS extends WebDriverObject{

    protected static URL imsURL;
    protected static URL defaultImsURL;
    protected static String imsLogin;
    protected static String imsPass;
    protected static String defaultImsLogin;
    protected static String defaultImsPass;

    private static final int RETRIES = 10;

    @Autowired
    @Qualifier("userData")
    private UserData defaultUserData;

    public static URL getImsURL() {
        if(imsURL!=null){
            return imsURL;
        }else {
            return getDefaultImsURL();
        }
    }

    public static void setImsURL(URL imsURL) {
        IMS.imsURL = imsURL;
    }

    public static String getImsLogin() {
        if(imsLogin!=null){
            return imsLogin;
        }else {
            return getDefaultImsLogin();
        }
    }

    public static void setImsLogin(String imsLogin) {
        IMS.imsLogin = imsLogin;
    }

    public static String getImsPass() {
        if(imsPass!=null){
            return imsPass;
        }else {
            return getDefaultImsPass();
        }
    }

    public static void setImsPass(String imsPass) {
        IMS.imsPass = imsPass;
    }

    public static URL getDefaultImsURL() {
        return defaultImsURL;
    }

    public static void setDefaultImsURL(URL defaultImsURL) {
        IMS.defaultImsURL = defaultImsURL;
    }

    public static String getDefaultImsLogin() {
        return defaultImsLogin;
    }

    public static void setDefaultImsLogin(String defaultImsLogin) {
        IMS.defaultImsLogin = defaultImsLogin;
    }

    public static String getDefaultImsPass() {
        return defaultImsPass;
    }

    public static void setDefaultImsPass(String defaultImsPass) {
        IMS.defaultImsPass = defaultImsPass;
    }

	private IMSHomePage navigateToIMS(){
        try{
            IMSHomePage imsHomePage;
            webDriver.navigate().to(getImsURL());
            if(WebDriverUtils.isVisible(IMSLoginPage.ROOT_XP, 5)){
                IMSLoginPage imsLoginPage=new IMSLoginPage();
                imsHomePage=imsLoginPage.logInToIMS();
            }else{
                imsHomePage=new IMSHomePage();
            }
            return imsHomePage;
        }catch (RuntimeException e){
            if (e.getMessage().contains(IMSHomePage.LINK_TAB_PLAYER_MANAGEMENT_XP)){
                throw new SkipException("IMS timeout"+e.getMessage());
            }else{
                throw new RuntimeException(e.getMessage());
            }
        }
	}

    public HashMap getInternalTagsData(){
        return getInternalTagsData(defaultUserData.getRegisteredUserData());
    }

	public HashMap getInternalTagsData(UserData userData){
		HashMap hashMap = new HashMap<String, String>();
		try{
            InboxPage inboxPage = (InboxPage) NavigationUtils.navigateToPage(ConfiguredPages.inbox);
			hashMap.put("messages", String.valueOf(inboxPage.getNumberOfUnreadMessages()));
		}catch(RuntimeException e){
			WebDriverUtils.runtimeExceptionWithUrl("Inbox is not available");
		}
		return navigateToPlayedDetails(userData.getUsername()).getInternalTagsData(hashMap);
	}

    public void validateNotificationCheckboxes(UserData userData, boolean state) {
        IMSPlayerDetailsPage imsPlayerDetailsPage = navigateToPlayedDetails(userData.getUsername());
        for(boolean imsState:imsPlayerDetailsPage.getNotificationCheckboxesState()){
            if(imsState==state){
                WebDriverUtils.runtimeExceptionWithUrl("Notifications state did not update<div>Expected: " + state + ", Found: " + !imsState + "</div>");
            }
        }
    }

	public void validateRegisterData(UserData userData){
		IMSPlayerDetailsPage imsPlayerDetailsPage=navigateToPlayedDetails(userData.getUsername());
		ArrayList<String> imsRegisterData=imsPlayerDetailsPage.getRegisterData();
		ArrayList<String> wplRegisterData=userData.getRegisterData();
		for(int i=0; i < wplRegisterData.size(); i++){
            AbstractTest.assertEquals(wplRegisterData.get(i).toUpperCase(), imsRegisterData.get(i).toUpperCase(), "Reg data - ");
		}
	}

    public void validateNoAffiliate(String username, AffiliateData affiliateData){
        validateNoAffiliate(username, affiliateData.getDefaultAdvertiser());
    }

    public void validateAffiliate(String username, AffiliateData affiliateData){
        validateAffiliate(username, affiliateData, true);
    }

    public void validateAffiliate(String username, AffiliateData affiliateData, boolean creferrerIsExists) {
        validateAffiliate(username, affiliateData.getAdvertiser(), affiliateData.getBanner(), affiliateData.getProfile(), affiliateData.getUrl(), affiliateData.getCreferrer(), creferrerIsExists);
    }

    private void validateAffiliate(String username, String advert, String banner, String profile, String url, String creferrer, boolean creferrerIsExists){
        navigateToPlayedDetails(username).checkAffiliateData(advert, banner, profile, url, creferrer, creferrerIsExists);
    }

    private void validateNoAffiliate(String username, String advertiser){
        navigateToPlayedDetails(username).checkAffiliateData(advertiser);
    }

    public void validateCreferrer(String username, String creferrer) {
        navigateToPlayedDetails(username).checkCreferrer(creferrer);
    }

	public IMSPlayerDetailsPage navigateToPlayedDetails(String username){
		return navigateToIMS().clickPlayerManagement().clickAdvancedPlayerSearch().search(username);
	}

	public IMSTemplateToolsPage navigateToTemplateTools(){
		return navigateToIMS().clickTemplateTools();
	}

	public boolean isPlayerLoggedIn(String username){
		IMSPlayerDetailsPage imsPlayerDetailsPage = navigateToPlayedDetails(username);
		for(int i=0; i<RETRIES; i++){
			if(imsPlayerDetailsPage.isPlayerOnline()){
				return true;
			}
			WebDriverUtils.waitFor();
			WebDriverUtils.refreshPage();
		}
		return false;
	}

    public void sendPushMessage(Page pushMessages){
        this.sendPushMessage(defaultUserData.getRegisteredUserData(), pushMessages, null);
    }

    public void sendPushMessage(Page pushMessages, String amount){
        this.sendPushMessage(defaultUserData.getRegisteredUserData(), pushMessages, amount);
    }

    public void sendPushMessage(UserData userData, Page pushMessages){
        this.sendPushMessage(userData, pushMessages, null);
    }

	public void sendPushMessage(UserData userData, Page pushMessages, String amount){
		try{
			switch (pushMessages){
				case logout:
					try{
						WebDriverUtils.openAdditionalSession();
						navigateToPlayedDetails(userData.getUsername()).sendPushLogout();
					}finally{
						WebDriverUtils.closeAdditionalSession();
					}
					break;
				case changePasswordPopup:
					navigateToPlayedDetails(userData.getUsername()).changePassword(userData.getPassword());
					break;
				case okBonus:
					try{
						WebDriverUtils.openAdditionalSession();
						navigateToPlayedDetails(userData.getUsername()).addBonus(Page.okBonus, amount);
					}finally{
						WebDriverUtils.closeAdditionalSession();
					}
					break;
				case acceptDeclineBonus:
					try{
						WebDriverUtils.openAdditionalSession();
						navigateToPlayedDetails(userData.getUsername()).addBonus(Page.acceptDeclineBonus, amount);
					}finally{
						WebDriverUtils.closeAdditionalSession();
					}
					break;
			}
		}catch(RuntimeException e){
			if (e.getMessage().contains(IMSChangePassPopup.LABEL_PASSWORD_CHANGED)){
				throw new SkipException("IMS timeout "+e.getMessage());
			}else{
				throw new RuntimeException(e.getMessage());
			}
		}
	}

    public void freezeWelcomeMessages(){
        navigateToTemplateTools().clickLoginDatabase().navigateToWelcomeMessage().setFreezeStateAndUpdate(true);
    }

    public void unFreezeWelcomeMessages(){
        navigateToTemplateTools().clickLoginDatabase().navigateToWelcomeMessage().setFreezeStateAndUpdate(false);

    }

    public String getClientType(UserData userData){
        return navigateToPlayedDetails(userData.getUsernameUppercase()).getClientType();
    }

}
