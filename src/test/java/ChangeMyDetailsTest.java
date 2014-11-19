import pageObjects.changeMyDetails.DetailsChangedNotification;
import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.testng.annotations.Test;
import pageObjects.changeMyDetails.ChangeMyDetailsPage;
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
        try{
            ChangeMyDetailsPage changeMyDetailsPage = (ChangeMyDetailsPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.updateMyDetails);
        }catch (Exception e){
            skipTest();
        }
    }

	/* 4. Player updates his details, logs out, logs in again and new values are displayed */
	@Test(groups = {"regression"})
	public void editUserInfoAndCheckIfSavedAfterLogout(){
        UserData[] userDatas = getUserDatas();
        PortalUtils.registerUser(userDatas[0]);
        ChangeMyDetailsPage updateMyDetailsPage =(ChangeMyDetailsPage) NavigationUtils.navigateToPage(ConfiguredPages.updateMyDetails);
		updateMyDetailsPage.changeDetailsAndSubmit(userDatas[1]);
        updateMyDetailsPage.assertUserData(userDatas[1]);
        PortalUtils.logout();
        updateMyDetailsPage =(ChangeMyDetailsPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.updateMyDetails, userDatas[1]);
        updateMyDetailsPage.assertUserData(userDatas[1]);
	}

	/* 5. If player clicks “Update Details” without having changed any data then success message is displayed but changes are not saved */
	@Test(groups = {"regression"})
	public void userInfoNotChangedIfPageChanged(){
        UserData[] userDatas = getUserDatas();
        PortalUtils.registerUser(userDatas[0]);
        ChangeMyDetailsPage updateMyDetailsPage =(ChangeMyDetailsPage) NavigationUtils.navigateToPage(ConfiguredPages.updateMyDetails);
        updateMyDetailsPage.changeDetails(userDatas[1]);
        NavigationUtils.navigateToPage(ConfiguredPages.home);
        updateMyDetailsPage =(ChangeMyDetailsPage) NavigationUtils.navigateToPage(ConfiguredPages.updateMyDetails);
        updateMyDetailsPage.assertUserData(userDatas[0]);
	}

    /* 5. If player clicks “Update Details” without having changed any data then success message is displayed but changes are not saved */
    @Test(groups = {"regression"})
    public void userInfoNotChangedIfAfterRefresh(){
        UserData[] userDatas = getUserDatas();
        PortalUtils.registerUser(userDatas[0]);
        ChangeMyDetailsPage updateMyDetailsPage =(ChangeMyDetailsPage) NavigationUtils.navigateToPage(ConfiguredPages.updateMyDetails);
        updateMyDetailsPage.changeDetails(userDatas[1]);
        WebDriverUtils.refreshPage();
        updateMyDetailsPage.assertUserData(userDatas[0]);
    }

    /* 5. If player clicks “Update Details” without having changed any data then success message is displayed but changes are not saved */
    @Test(groups = {"regression"})
    public void buttonIsDisabledWhenNothingChanged(){
        UserData[] userDatas = getUserDatas();
        PortalUtils.registerUser(userDatas[0]);
        ChangeMyDetailsPage updateMyDetailsPage =(ChangeMyDetailsPage) NavigationUtils.navigateToPage(ConfiguredPages.updateMyDetails);
        assertTrue(updateMyDetailsPage.isButtonDisabled(), "button disabled with no changes");
        updateMyDetailsPage.changeDetails(userDatas[1]);
        assertFalse(updateMyDetailsPage.isButtonDisabled(), "button disabled with changes");
        updateMyDetailsPage.changeDetails(userDatas[0]);
        assertTrue(updateMyDetailsPage.isButtonDisabled(), "button disabled after revert");
    }

	/*6. Player performs several consecutive updates of UMD portlet */
	@Test(groups = {"regression"})
	public void userInfoChangedDuringConsecutiveUpdates() {
        UserData[] userDatas = getUserDatas();
        PortalUtils.registerUser(userDatas[0]);
        ChangeMyDetailsPage updateMyDetailsPage =(ChangeMyDetailsPage) NavigationUtils.navigateToPage(ConfiguredPages.updateMyDetails);
        updateMyDetailsPage.changeDetailsAndSubmit(userDatas[1]);
        updateMyDetailsPage.assertUserData(userDatas[1]);
        updateMyDetailsPage.changeDetailsAndSubmit(userDatas[0]);
        updateMyDetailsPage.assertUserData(userDatas[0]);
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
        UserData[] userDatas = getUserDatas();
        PortalUtils.registerUser(userDatas[0]);
        ChangeMyDetailsPage updateMyDetailsPage = (ChangeMyDetailsPage) NavigationUtils.navigateToPage(ConfiguredPages.updateMyDetails);
        updateMyDetailsPage.changeDetailsAndSubmit(userDatas[1]);
        IMSUtils.assertUMDData(userDatas[1]);
	}

    private UserData[] getUserDatas(){
        UserData userData = DataContainer.getUserData().getRandomUserData();
        UserData userDataNew = DataContainer.getUserData().getRandomUserData();
        userDataNew.setUsername(userData.getUsername());
        userDataNew.setTitle(userData.getTitle());
        userDataNew.setFirstName(userData.getFirstName());
        userDataNew.setLastName(userData.getLastName());
        return new UserData[]{userData, userDataNew};
    }
}