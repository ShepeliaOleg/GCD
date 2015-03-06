package utils;

import enums.ConfiguredPages;
import enums.Page;
import pageObjects.external.ims.*;
import pageObjects.inbox.InboxPage;
import springConstructors.AffiliateData;
import springConstructors.IMS;
import springConstructors.UserData;
import utils.core.AbstractTest;
import utils.core.DataContainer;
import utils.core.WebDriverFactory;

import java.util.ArrayList;
import java.util.HashMap;

import static utils.core.AbstractTest.assertEquals;
import static utils.core.AbstractTest.assertTrue;

public class IMSUtils {

    private static final int RETRIES = 10;

    private static IMSHomePage navigateToIMS(){
        IMSHomePage imsHomePage;
        IMS ims = DataContainer.getIms();
        try{
            WebDriverUtils.navigateToURL(WebDriverFactory.getServerDriver(), ims.getImsURL());
            if(WebDriverUtils.isVisible(WebDriverFactory.getServerDriver(), IMSLoginPage.ROOT_XP, 5)){
                imsHomePage=new IMSLoginPage().logInToIMS(ims.getImsLogin(), ims.getImsPass());
            }else{
                imsHomePage=new IMSHomePage();
            }
            return imsHomePage;
        }catch (RuntimeException e){
            AbstractTest.skipTest("IMS issue " + e.getMessage());
        }
        return null;
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

    public static void assertRegisterData(UserData userData){
        IMSPlayerDetailsPage imsPlayerDetailsPage=navigateToPlayedDetails(userData.getUsername());
        ArrayList<String> imsRegisterData=imsPlayerDetailsPage.getRegisterData();
        ArrayList<String> wplRegisterData=userData.getRegisterData();
        for(int i=0; i < wplRegisterData.size(); i++){
            assertEquals(wplRegisterData.get(i).toUpperCase(), imsRegisterData.get(i).toUpperCase(), "Reg data");
        }
    }

    public static void assertUMDData(UserData userData){
        ArrayList<String> imsRegisterData=navigateToPlayedDetails(userData.getUsername()).getUMDData();
        ArrayList<String> wplRegisterData=userData.getUMDData();
        for(int i=0; i < wplRegisterData.size(); i++){
            assertEquals(wplRegisterData.get(i).toUpperCase(), imsRegisterData.get(i).toUpperCase(), "UMD data");
        }
    }

    public static void validateNoAffiliate(String username){
        AffiliateData affiliateData = DataContainer.getAffiliateData();
        validateNoAffiliate(username, affiliateData.getDefaultAdvertiser(), affiliateData.getNoProfile());
    }

    public static void validateAffiliate(String username, AffiliateData affiliateData){
        validateAffiliate(username, affiliateData, true);
    }

    public static void validateAffiliate(String username, AffiliateData affiliateData, boolean creferrerIsExists) {
        validateAffiliate(username, affiliateData.getAdvertiser(), affiliateData.getBanner(), affiliateData.getProfile(), affiliateData.getUrl(), affiliateData.getCreferrer(), creferrerIsExists);
    }

    private static void validateAffiliate(String username, String advert, String banner, String profile, String url, String creferrer, boolean creferrerIsExists){
        navigateToPlayedDetails(username).checkAffiliateData(advert, banner, profile, url, creferrer, creferrerIsExists);
    }

    private static void validateNoAffiliate(String username, String advertiser, String noProfile){
        navigateToPlayedDetails(username).checkAffiliateData(advertiser, noProfile);
    }

    public static void validateCreferrer(String username, String creferrer) {
        navigateToPlayedDetails(username).checkCreferrer(creferrer);
    }

    public static IMSPlayerDetailsPage navigateToPlayedDetails(String username){
        return navigateToIMS().clickPlayerManagement().clickAdvancedPlayerSearch().search(username);
    }

    public static IMSTemplateToolsPage navigateToTemplateTools(){
        return navigateToIMS().clickTemplateTools();
    }

    public static boolean isPlayerLoggedIn(String username){
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

    public static void checkPlayerHasEnabledOptInBonus(String username, String optInID) {
        IMSPlayerDetailsPage imsPlayerDetailsPage = navigateToPlayedDetails(username);
        IMSPlayerBonusInfoPage imsPlayerBonusInfoPage = imsPlayerDetailsPage.clickPlayerDetailsInfo();
        try {
            assertTrue(imsPlayerBonusInfoPage.isVisibleActiveBonus(optInID), "Expected opt-in bonus is not visible");
            assertEquals(imsPlayerBonusInfoPage.getNameActiveBonus(optInID), "Test Opt-in bonus 1", "Actual bonus has unexpected name");
        } finally {
            WebDriverFactory.getServerDriver().switchTo().defaultContent();
        }
    }

    public static void checkPlayerHasDisabledOptInBonus(String username, String optInID) {
        IMSPlayerDetailsPage imsPlayerDetailsPage = navigateToPlayedDetails(username);
        IMSPlayerBonusInfoPage imsPlayerBonusInfoPage = imsPlayerDetailsPage.clickPlayerDetailsInfo();
        try {
            assertTrue(imsPlayerBonusInfoPage.isVisibleNoActiveBonus(optInID), "Expected text not found");
            assertTrue(!imsPlayerBonusInfoPage.isVisibleActiveBonus(optInID), "Opt-in bonus is visible, it's not as expected");
        } finally {
            WebDriverFactory.getServerDriver().switchTo().defaultContent();
        }
    }

    public static void sendPushMessage(UserData userData, Page pushMessages){
        sendPushMessage(userData, null, pushMessages);
    }

    public static void sendPushMessage(UserData userData, String amount, Page...pushMessages){
        try{
            IMSPlayerDetailsPage imsPlayerDetailsPage = navigateToPlayedDetails(userData.getUsername());
            for(Page message:pushMessages) {
                switch (message) {
                    case logout:
                        imsPlayerDetailsPage.sendPushLogout();
                        break;
                    case changePasswordPopup:
                        imsPlayerDetailsPage.changePassword(userData.getPassword());
                        break;
                    case okBonus:
                    case acceptDeclineBonus:
                    case loseOnWithdraw:
                    case ringfencing:
                        imsPlayerDetailsPage.addBonus(message, amount);
                        break;
                }
            }
        }catch(RuntimeException e) {
            AbstractTest.skipTest("IMS issue: " + e.getMessage());
        }
    }

    public static void setLoginMessagesCount(int count){
        IMSLoginDatabasePage imsLoginDatabasePage = navigateToTemplateTools().clickLoginDatabase();
        if(count==3){
            imsLoginDatabasePage.unfreeze(count);
        }else {
            imsLoginDatabasePage.freezeAll();
            if(count>0){
                imsLoginDatabasePage.unfreeze(count);
            }
        }
    }

    public static String getClientType(UserData userData){
        return navigateToPlayedDetails(userData.getUsername()).getClientType();
    }
}
