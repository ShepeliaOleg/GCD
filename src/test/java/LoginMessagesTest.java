import enums.ConfiguredPages;
import enums.Page;
import enums.PlayerCondition;
import org.testng.annotations.Test;
import pageObjects.external.ims.IMSLoginDatabasePage;
import pageObjects.login.SignedOutPopup;
import pageObjects.login.WelcomePopup;
import springConstructors.UserData;
import utils.IMSUtils;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;

public class LoginMessagesTest extends AbstractTest{

    /*One login message */
	@Test(groups = {"regression", "mobile"})
	public void loginMessage(){
        IMSUtils.setLoginMessagesCount(1);
		UserData userData = DataContainer.getUserData().getRegisteredUserData();
        WelcomePopup welcomePopup = (WelcomePopup) PortalUtils.loginUser(userData, Page.welcomePopup);
        closeDefaultWelcomePopup(welcomePopup);
        assertTrue(WebDriverUtils.isVisible(IMSLoginDatabasePage.MESSAGES[0]), "Message correct '"+IMSLoginDatabasePage.MESSAGES[0]+"'");
	}

    /*No login message */
    @Test(groups = {"regression", "mobile"})
    public void noLoginMessage(){
        IMSUtils.setLoginMessagesCount(0);
        try{
            UserData userData = DataContainer.getUserData().getRegisteredUserData();
            PortalUtils.loginUser(userData);
        }catch (Exception e){
            addError(e.getMessage());
        }finally {
            IMSUtils.setLoginMessagesCount(1);
        }
    }

    /*Login messages navigation */
    @Test(groups = {"regression", "mobile"})
    public void multipleLoginMessagesNavigation(){
        IMSUtils.setLoginMessagesCount(3);
        UserData userData = DataContainer.getUserData().getRegisteredUserData();
        WelcomePopup welcomePopup = (WelcomePopup) PortalUtils.loginUser(userData, Page.welcomePopup);
        closeDefaultWelcomePopup(welcomePopup);
        assertTrue(WebDriverUtils.isVisible(IMSLoginDatabasePage.MESSAGES[0], 1), "Message correct '"+IMSLoginDatabasePage.MESSAGES[0]+"'");
        welcomePopup.clickNext();
        assertTrue(WebDriverUtils.isVisible(IMSLoginDatabasePage.MESSAGES[1], 1), "Message correct '"+IMSLoginDatabasePage.MESSAGES[1]+"'");
        welcomePopup.clickNext();
        assertTrue(WebDriverUtils.isVisible(IMSLoginDatabasePage.MESSAGES[2], 1), "Message correct '"+IMSLoginDatabasePage.MESSAGES[2]+"'");
        welcomePopup.clickPrevious();
        assertTrue(WebDriverUtils.isVisible(IMSLoginDatabasePage.MESSAGES[1], 1), "Message correct '"+IMSLoginDatabasePage.MESSAGES[1]+"'");
        welcomePopup.clickPrevious();
        assertTrue(WebDriverUtils.isVisible(IMSLoginDatabasePage.MESSAGES[0], 1), "Message correct '"+IMSLoginDatabasePage.MESSAGES[0]+"'");
    }

    /*Login messages closing from first*/
    @Test(groups = {"regression", "mobile"})
    public void multipleLoginMessagesClosingStart(){
        IMSUtils.setLoginMessagesCount(3);
        UserData userData = DataContainer.getUserData().getRegisteredUserData();
        WelcomePopup welcomePopup = (WelcomePopup) PortalUtils.loginUser(userData, Page.welcomePopup);
        closeDefaultWelcomePopup(welcomePopup);
        assertTrue(WebDriverUtils.isVisible(IMSLoginDatabasePage.MESSAGES[0], 1), "Message correct '"+IMSLoginDatabasePage.MESSAGES[0]+"'");
        welcomePopup.closePopup();
        assertTrue(WebDriverUtils.isVisible(IMSLoginDatabasePage.MESSAGES[1], 1), "Message correct '"+IMSLoginDatabasePage.MESSAGES[1]+"'");
        welcomePopup.closePopup();
        assertTrue(WebDriverUtils.isVisible(IMSLoginDatabasePage.MESSAGES[2], 1), "Message correct '"+IMSLoginDatabasePage.MESSAGES[2]+"'");
    }

    /*Login messages closing from last*/
    @Test(groups = {"regression", "mobile"})
    public void multipleLoginMessagesClosingEnd(){
        IMSUtils.setLoginMessagesCount(3);
        UserData userData = DataContainer.getUserData().getRegisteredUserData();
        WelcomePopup welcomePopup = (WelcomePopup) PortalUtils.loginUser(userData, Page.welcomePopup);
        closeDefaultWelcomePopup(welcomePopup);
        assertTrue(WebDriverUtils.isVisible(IMSLoginDatabasePage.MESSAGES[0], 1), "Message correct '"+IMSLoginDatabasePage.MESSAGES[0]+"'");
        welcomePopup.clickNext();
        assertTrue(WebDriverUtils.isVisible(IMSLoginDatabasePage.MESSAGES[1], 1), "Message correct '"+IMSLoginDatabasePage.MESSAGES[1]+"'");
        welcomePopup.closePopup();
        assertTrue(WebDriverUtils.isVisible(IMSLoginDatabasePage.MESSAGES[0], 1), "Message correct '"+IMSLoginDatabasePage.MESSAGES[0]+"'");
    }

	/*Push logout */
	@Test(groups = {"regression", "mobile"})
	public void pushLogout(){
        UserData userData = DataContainer.getUserData().getRegisteredUserData();
        NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.home, userData);
		IMSUtils.sendPushMessage(userData, Page.logout);
        validateTrue(WebDriverUtils.isVisible(SignedOutPopup.TITLE_XP, 300), "User was not logged out after 180 seconds");
		new SignedOutPopup().close().waitForLogout();
	}

    private void closeDefaultWelcomePopup(WelcomePopup welcomePopup) {
        String message;
        while (true) {
            message = welcomePopup.getTranslationText();
            if (!message.contains("AUTO")) {
                welcomePopup.closePopup();
            } else {
                break;
            }
        }
    }
}
