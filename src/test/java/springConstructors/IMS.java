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

    protected URL imsURL;
    protected String imsLogin;
    protected String imsPass;

    private static final int RETRIES = 10;

    @Autowired
    @Qualifier("userData")
    private UserData defaultUserData;

    @Autowired
    @Qualifier("affiliate")
    private AffiliateData affiliateData;


    public URL getImsURL() {
        return imsURL;
    }

    public void setImsURL(URL imsURL) {
        this.imsURL = imsURL;
    }

    public String getImsLogin() {
        return imsLogin;
    }

    public void setImsLogin(String imsLogin) {
        this.imsLogin = imsLogin;
    }

    public String getImsPass() {
        return imsPass;
    }

    public void setImsPass(String imsPass) {
        this.imsPass = imsPass;
    }

	private IMSHomePage navigateToIMS(){
        IMSHomePage imsHomePage;
        try{
            webDriver.navigate().to(getImsURL());
            if(WebDriverUtils.isVisible(IMSLoginPage.ROOT_XP, 5)){
                imsHomePage=new IMSLoginPage().logInToIMS(getImsLogin(), getImsPass());
            }else{
                imsHomePage=new IMSHomePage();
            }
            return imsHomePage;
        }catch (RuntimeException e){
            AbstractTest.skipTest("IMS issue " + e.getMessage());
        }
        return null;
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
            AbstractTest.failTest("Inbox is not available");
		}
		return navigateToPlayedDetails(userData.getUsername()).getInternalTagsData(hashMap);
	}

    public void validateNotificationCheckboxes(UserData userData, boolean state) {
        IMSPlayerDetailsPage imsPlayerDetailsPage = navigateToPlayedDetails(userData.getUsername());
        for(boolean imsState:imsPlayerDetailsPage.getNotificationCheckboxesState()){
            AbstractTest.validateNotEquals(state, imsState, "Notifications state did not update");
        }
    }

	public void validateRegisterData(UserData userData){
		IMSPlayerDetailsPage imsPlayerDetailsPage=navigateToPlayedDetails(userData.getUsername());
		ArrayList<String> imsRegisterData=imsPlayerDetailsPage.getRegisterData();
		ArrayList<String> wplRegisterData=userData.getRegisterData();
		for(int i=0; i < wplRegisterData.size(); i++){
            AbstractTest.assertEquals(wplRegisterData.get(i).toUpperCase(), imsRegisterData.get(i).toUpperCase(), "Reg data");
		}
	}

    public void validateNoAffiliate(String username){
        validateNoAffiliate(username, affiliateData.getDefaultAdvertiser(), affiliateData.getNoProfile());
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

    private void validateNoAffiliate(String username, String advertiser, String noProfile){
        navigateToPlayedDetails(username).checkAffiliateData(advertiser, noProfile);
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
            AbstractTest.skipTest("IMS issue "+e.getMessage());
        }
	}

    public void freezeWelcomeMessages(){
        navigateToTemplateTools().clickLoginDatabase().navigateToWelcomeMessage().setFreezeStateAndUpdate(true);
    }

    public void unFreezeWelcomeMessages(){
        navigateToTemplateTools().clickLoginDatabase().navigateToWelcomeMessage().setFreezeStateAndUpdate(false);

    }

    public String getClientType(UserData userData){
        return navigateToPlayedDetails(userData.getUsername()).getClientType();
    }

}
