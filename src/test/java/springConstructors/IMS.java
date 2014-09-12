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
import utils.core.WebDriverObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class IMS extends WebDriverObject{

    protected URL imsURL;

    protected URL defaultImsURL;
    private static final int RETRIES = 10;

    @Autowired
    @Qualifier("userData")
    private UserData defaultUserData;

    public URL getImsURL() {
        if(imsURL!=null){
            return imsURL;
        }else {
            return getDefaultImsURL();
        }
    }

    public void setImsURL(URL imsURL) {
        this.imsURL = imsURL;
    }

    public URL getDefaultImsURL() {
        return defaultImsURL;
    }

    public void setDefaultImsURL(URL defaultImsURL) {
        this.defaultImsURL = defaultImsURL;
    }

	private IMSHomePage navigateToIMS(){
        try{
            IMSHomePage imsHomePage;
            webDriver.navigate().to(imsURL);
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

	public boolean validateRegisterData(UserData userData){
		IMSPlayerDetailsPage imsPlayerDetailsPage=navigateToPlayedDetails(userData.getUsername());
		ArrayList<String> imsRegisterData=imsPlayerDetailsPage.getRegisterData();
		ArrayList<String> wplRegisterData=userData.getRegisterData();
		boolean allValuesAreCorrect=true;

		for(int i=0; i < wplRegisterData.size(); i++){
			allValuesAreCorrect=wplRegisterData.get(i).equalsIgnoreCase(imsRegisterData.get(i));
			if(allValuesAreCorrect == false){
				String errorMessage="\n Error in line " + i + "\n";
				for(int x=0; x < wplRegisterData.size(); x++){
					errorMessage=errorMessage.concat("<div>(" + x + ") - " + wplRegisterData.get(x) + " = " + imsRegisterData.get(x) + "</div>\n");
				}
				WebDriverUtils.runtimeExceptionWithUrl(errorMessage);
			}
		}
		return allValuesAreCorrect;
	}

    public void validateAffiliate(String username, String advert, String banner, String profile, String url, String customTitle, String customValue){
        navigateToPlayedDetails(username).checkAffiliateData(advert, profile, customTitle, customValue, banner, url);
    }

    public void validateAffiliate(String username, String advert){
        navigateToPlayedDetails(username).checkAffiliateData(advert);
    }

	public IMSPlayerDetailsPage navigateToPlayedDetails(String username){
		return navigateToIMS().clickPlayerManagement().clickPlayerSearch().search(username);
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
}
