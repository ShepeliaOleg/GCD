import enums.ConfiguredPages;
import enums.Page;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.external.ims.IMSLoginDatabasePage;
import pageObjects.login.ForceLogoutPopup;
import pageObjects.login.SignedOutPopup;
import pageObjects.login.WelcomePopup;
import springConstructors.IMS;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

public class LoginMessagesTest extends AbstractTest{

    @Autowired
    @Qualifier("userData")
    private UserData defaultUserData;

    @Autowired
    @Qualifier("iMS")
    private IMS iMS;

    /*One login message */
	@Test(groups = {"regression", "push"})
	public void loginMessage(){
        iMS.setLoginMessagesCount(1);
		UserData userData = defaultUserData.getRegisteredUserData();
        WelcomePopup welcomePopup = (WelcomePopup) PortalUtils.loginUser(userData, Page.welcomePopup);
        assertTrue(WebDriverUtils.isVisible(IMSLoginDatabasePage.MESSAGES[0]), "Message correct '"+IMSLoginDatabasePage.MESSAGES[0]+"'");
	}

    /*No login message */
    @Test(groups = {"regression", "push"})
    public void noLoginMessage(){
        iMS.setLoginMessagesCount(0);
        try{
            UserData userData = defaultUserData.getRegisteredUserData();
            PortalUtils.loginUser(userData);
        }catch (Exception e){
            addError(e.getMessage());
        }finally {
            iMS.setLoginMessagesCount(1);
        }
    }

    /*Login messages navigation */
    @Test(groups = {"regression", "push"})
    public void multipleLoginMessagesNavigation(){
        iMS.setLoginMessagesCount(3);
        UserData userData = defaultUserData.getRegisteredUserData();
        WelcomePopup welcomePopup = (WelcomePopup) PortalUtils.loginUser(userData, Page.welcomePopup);
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
    @Test(groups = {"regression", "push"})
    public void multipleLoginMessagesClosingStart(){
        iMS.setLoginMessagesCount(3);
        UserData userData = defaultUserData.getRegisteredUserData();
        WelcomePopup welcomePopup = (WelcomePopup) PortalUtils.loginUser(userData, Page.welcomePopup);
        assertTrue(WebDriverUtils.isVisible(IMSLoginDatabasePage.MESSAGES[0], 1), "Message correct '"+IMSLoginDatabasePage.MESSAGES[0]+"'");
        welcomePopup.closePopup();
        assertTrue(WebDriverUtils.isVisible(IMSLoginDatabasePage.MESSAGES[1], 1), "Message correct '"+IMSLoginDatabasePage.MESSAGES[1]+"'");
        welcomePopup.closePopup();
        assertTrue(WebDriverUtils.isVisible(IMSLoginDatabasePage.MESSAGES[2], 1), "Message correct '"+IMSLoginDatabasePage.MESSAGES[2]+"'");
    }

    /*Login messages closing from last*/
    @Test(groups = {"regression", "push"})
    public void multipleLoginMessagesClosingEnd(){
        iMS.setLoginMessagesCount(3);
        UserData userData = defaultUserData.getRegisteredUserData();
        WelcomePopup welcomePopup = (WelcomePopup) PortalUtils.loginUser(userData, Page.welcomePopup);
        assertTrue(WebDriverUtils.isVisible(IMSLoginDatabasePage.MESSAGES[0], 1), "Message correct '"+IMSLoginDatabasePage.MESSAGES[0]+"'");
        welcomePopup.clickNext();
        assertTrue(WebDriverUtils.isVisible(IMSLoginDatabasePage.MESSAGES[1], 1), "Message correct '"+IMSLoginDatabasePage.MESSAGES[1]+"'");
        welcomePopup.closePopup();
        assertTrue(WebDriverUtils.isVisible(IMSLoginDatabasePage.MESSAGES[0], 1), "Message correct '"+IMSLoginDatabasePage.MESSAGES[0]+"'");
    }

	/*Push logout */
	@Test(groups = {"regression", "push"})
	public void pushLogout(){
        UserData userData = defaultUserData.getRegisteredUserData();
        NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.home, userData);
		iMS.sendPushMessage(userData, Page.logout);
        validateTrue(WebDriverUtils.isVisible(SignedOutPopup.TITLE_XP, 300), "User was not logged out after 180 seconds");
		new SignedOutPopup().close().waitForLogout();
	}
}
