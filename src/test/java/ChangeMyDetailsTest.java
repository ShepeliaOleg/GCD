import changeMyDetails.DetailsChangedPopup;
import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.testng.annotations.Test;
import changeMyDetails.ChangeMyDetailsPage;
import springConstructors.UserData;
import utils.IMSUtils;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;

public class ChangeMyDetailsTest extends AbstractTest {


	/*POSITIVE*/

    /* 1. Portlet is displayed */
    @Test(groups = {"smoke"})
    public void portletIsDisplayedOnMyAccountChangeMyDetailsPage() {
        ChangeMyDetailsPage changeMyDetailsPage = (ChangeMyDetailsPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.updateMyDetails);
    }

	/* 4. Player updates his details, logs out, logs in again and new values are displayed */
	@Test(groups = {"regression"})
	public void editUserInfoAndCheckIfSavedAfterLogout(){
        UserData userData=DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData);
        String userName = userData.getUsername();
        ChangeMyDetailsPage updateMyDetailsPage =(ChangeMyDetailsPage) NavigationUtils.navigateToPage(ConfiguredPages.updateMyDetails);
		userData = DataContainer.getUserData().getRandomUserData();
        userData.setUsername(userName);
		DetailsChangedPopup detailsChangedPopup = updateMyDetailsPage.changeDetailsAndSubmit(userData);
        detailsChangedPopup.closePopup();
        updateMyDetailsPage.assertUserData(userData);
        PortalUtils.logout();
        updateMyDetailsPage =(ChangeMyDetailsPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.updateMyDetails, userData);
        updateMyDetailsPage.assertUserData(userData);
	}

	/* 5. If player clicks “Update Details” without having changed any data then success message is displayed but changes are not saved */
	@Test(groups = {"regression"})
	public void userInfoNotChangedIfPageChanged(){
        UserData userData = DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser();
        NavigationUtils.navigateToPage(ConfiguredPages.updateMyDetails);
        NavigationUtils.navigateToPage(ConfiguredPages.home);
        ChangeMyDetailsPage updateMyDetailsPage =(ChangeMyDetailsPage) NavigationUtils.navigateToPage(ConfiguredPages.updateMyDetails);
        updateMyDetailsPage.assertUserData(userData);
	}

    /* 5. If player clicks “Update Details” without having changed any data then success message is displayed but changes are not saved */
    @Test(groups = {"regression"})
    public void userInfoNotChangedIfAfterRefresh(){
        UserData userData = DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser();
        ChangeMyDetailsPage updateMyDetailsPage =(ChangeMyDetailsPage) NavigationUtils.navigateToPage(ConfiguredPages.updateMyDetails);
        WebDriverUtils.refreshPage();
        updateMyDetailsPage.assertUserData(userData);
    }

    /* 5. If player clicks “Update Details” without having changed any data then success message is displayed but changes are not saved */
    @Test(groups = {"regression"})
    public void userInfoNotChangedIfNoChangesSaved(){
        UserData oldUserData=DataContainer.getUserData().getRandomUserData();
        UserData newUserData = DataContainer.getUserData().getRandomUserData();
        newUserData.setUsername(oldUserData.getUsername());
        PortalUtils.registerUser();
        ChangeMyDetailsPage updateMyDetailsPage =(ChangeMyDetailsPage) NavigationUtils.navigateToPage(ConfiguredPages.updateMyDetails);
        assertTrue(updateMyDetailsPage.isButtonDisabled(), "button disabled");
        updateMyDetailsPage.changeDetails(newUserData);
        assertFalse(updateMyDetailsPage.isButtonDisabled(), "button disabled");
        updateMyDetailsPage.changeDetails(oldUserData);
        assertTrue(updateMyDetailsPage.isButtonDisabled(), "button disabled");
    }

	/*6. Player performs several consecutive updates of UMD portlet */
	@Test(groups = {"regression"})
	public void userInfoChangedDuringConsecutiveUpdates() {
        UserData userData=DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData);
        ChangeMyDetailsPage updateMyDetailsPage =(ChangeMyDetailsPage) NavigationUtils.navigateToPage(ConfiguredPages.updateMyDetails);
		userData = DataContainer.getUserData().getRandomUserData();
        DetailsChangedPopup detailsChangedPopup = updateMyDetailsPage.changeDetailsAndSubmit(userData);
        detailsChangedPopup.closePopup();
        updateMyDetailsPage.assertUserData(userData);
		userData = DataContainer.getUserData().getRandomUserData();
         detailsChangedPopup = updateMyDetailsPage.changeDetailsAndSubmit(userData);
        detailsChangedPopup.closePopup();
        updateMyDetailsPage.assertUserData(userData);
	}

//	/*7. Logs*/
//	@Test(groups = {"regression", "logs"})
//	public void logsChangeDetails(){
//        try{
//            UserData userData=DataContainer.getUserData().getRandomUserData();
//            LogCategory[] logCategories = new LogCategory[]{LogCategory.SetPlayerInfoRequest, LogCategory.SetPlayerInfoResponse};
//            String[] parameters = {"objectIdentity="+userData.getUsername()+"-playtech81001",
//                    "KV(1, playtech81001)",
//                    "KV(2, "+userData.getUsername()+")",
//                    "KV(7, "+userData.getCity()+")",
//                    "KV(19, "+userData.getEmail()+")",
//                    "KV(21, "+userData.getFirstName()+")",
//                    "KV(24, "+userData.getLastName()+")",
//                    "KV(27, "+userData.getPhoneAreaCode()+userData.getPhone()+")",
//                    "KV(34, "+userData.getPostCode()+")"};
//            PortalUtils.registerUser(userData);
//            ChangeMyDetailsPage updateMyDetailsPage =(ChangeMyDetailsPage) NavigationUtils.navigateToPage(ConfiguredPages.updateMyDetails);
//            updateMyDetailsPage.changeDetailsAndSubmit(userData);
//            Log log = LogUtils.getCurrentLogs(logCategories);
//            log.doResponsesContainErrors();
//            LogEntry request = log.getEntry(LogCategory.SetPlayerInfoRequest);
//            request.containsParameters(parameters);
//        }catch (RuntimeException e){
//            if(e.getMessage().contains("Not all registration logs appeared") || e.toString().contains("Logs have not been updated")){
//                throw new SkipException("Log page issue"+WebDriverUtils.getUrlAndLogs());
//            }else{
//                throw new RuntimeException(e.getMessage());
//            }
//        }
//	}

	/*8. IMS player details are updated*/
	@Test(groups = {"regression"})
	public void iMSPlayerInfoIsUpdatedAfterPlayerDetailsChanged() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData);
        ChangeMyDetailsPage updateMyDetailsPage = (ChangeMyDetailsPage) NavigationUtils.navigateToPage(ConfiguredPages.updateMyDetails);
        String username = userData.getUsername();
        userData = DataContainer.getUserData().getRandomUserData();
        userData.setUsername(username);
        DetailsChangedPopup detailsChangedPopup = updateMyDetailsPage.changeDetailsAndSubmit(userData);
        detailsChangedPopup.closePopup();
        updateMyDetailsPage.assertUserData(userData);
        IMSUtils.assertRegisterData(userData);
	}
}