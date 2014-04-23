package springConstructors;

import enums.ConfiguredPages;
import enums.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import pageObjects.external.ims.*;
import pageObjects.inbox.InboxPage;
import utils.NavigationUtils;
import utils.WebDriverUtils;
import utils.core.WebDriverObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * User: sergiich
 * Date: 8/6/13
 */

public class IMS extends WebDriverObject{

	protected URL imsURL;

    @Autowired
    @Qualifier("userData")
    private UserData defaultUserData;

	public void setImsURL(URL imsURL){
		this.imsURL=imsURL;
	}

	private IMSHomePage navigateToIMS(){
		IMSHomePage imsHomePage;
		webDriver.navigate().to(imsURL);
		if(WebDriverUtils.isVisible(IMSLoginPage.ROOT_XP, 5)){
			IMSLoginPage imsLoginPage=new IMSLoginPage();
			imsHomePage=imsLoginPage.logInToIMS();
		}else{
			imsHomePage=new IMSHomePage();
		}
		return imsHomePage;
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
			WebDriverUtils.runtimeExceptionWithLogs("Inbox is not available");
		}
		return navigateToPlayedDetails(userData.getUsername()).getInternalTagsData(hashMap);
	}

	public boolean validateRegisterData(UserData userData){
		IMSPlayerDetailsPage imsPlayerDetailsPage=navigateToPlayedDetails(userData.getUsername());
		ArrayList<String> imsRegisterData=imsPlayerDetailsPage.getRegisterData();
		ArrayList<String> wplRegisterData=userData.getRegisterData();
		boolean allValuesAreCorrect=true;

		for(int i=0; i < wplRegisterData.size(); i++){
			allValuesAreCorrect=wplRegisterData.get(i).equals(imsRegisterData.get(i));
			if(allValuesAreCorrect == false){
				String errorMessage="\n Error in line " + i + "\n";
				for(int x=0; x < wplRegisterData.size(); x++){
					errorMessage=errorMessage.concat("(" + x + ") - " + wplRegisterData.get(x) + " = " + imsRegisterData.get(x) + "\n");
				}
				WebDriverUtils.runtimeExceptionWithLogs(errorMessage);
			}
		}
		return allValuesAreCorrect;
	}

	public IMSPlayerDetailsPage navigateToPlayedDetails(String username){
		return navigateToIMS().clickPlayerManagement().clickPlayerSearch().search(username);
	}

	public IMSSystemManagementPage navigateToSystemManagement(){
		return navigateToIMS().clickSystemManagement();
	}

	public IMSTemplateToolsPage navigateToTemplateTools(){
		return navigateToIMS().clickTemplateTools();
	}

	public boolean isPlayerLoggedIn(String username){
		return navigateToPlayedDetails(username).isPlayerOnline();
	}

	public void freezeNewTermsAndConditions(){
		navigateToSystemManagement().clickTermsAndConditions().navigateToSearchIframe().openTermsAndConditions().navigateToSearchIframe().setFreezeStateAndUpdate("Version #3", true);
	}

	public void unFreezeNewTermsAndConditions(String termsAndConditionsText){
		navigateToSystemManagement().clickTermsAndConditions().navigateToSearchIframe().openTermsAndConditions().navigateToSearchIframe().setFreezeStateAndUpdate(termsAndConditionsText, false);

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
	}

    public void freezeWelcomeMessages(){
        navigateToTemplateTools().clickLoginDatabase().navigateToWelcomeMessage().setFreezeStateAndUpdate(true);
    }

    public void unFreezeWelcomeMessages(){
        navigateToTemplateTools().clickLoginDatabase().navigateToWelcomeMessage().setFreezeStateAndUpdate(false);

    }
}
