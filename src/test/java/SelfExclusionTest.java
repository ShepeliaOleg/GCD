import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.base.AbstractPage;
import pageObjects.header.LoggedInHeader;
import pageObjects.responsibleGaming.ResponsibleGamingPage;
import pageObjects.responsibleGaming.SelfExcludeConfirmPopup;
import pageObjects.responsibleGaming.SelfExcludePopup;
import springConstructors.UserData;
import testUtils.AbstractTest;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.TypeUtils;
import utils.WebDriverUtils;

public class SelfExclusionTest extends AbstractTest{

	@Autowired
	@Qualifier("userData")
	private UserData defaultUserData;
	
	/* Positive */

    /* 1. Self-exlude and verify player is logged out*/
    @Test(groups = {"regression"})
    public void setSelfExclusion(){
        PortalUtils.registerUser(defaultUserData.getRandomUserData());
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.selfExclusion);
        SelfExcludePopup selfExcludePopup=responsibleGamingPage.navigateToSelfExclude();
        SelfExcludeConfirmPopup selfExcludeConfirmPopup=selfExcludePopup.submitSelfExclude();
        TypeUtils.assertTrueWithLogs(selfExcludeConfirmPopup.confirmationMessagesVisible(), "confirmationMessagesVisible");
        selfExcludeConfirmPopup.clickOk();
        try{
            WebDriverUtils.waitForElementToDisappear(LoggedInHeader.LOGGED_IN_XP);
        }catch(RuntimeException e){
            WebDriverUtils.runtimeExceptionWithLogs("User was not logged out. Self exclusion failed.");
        }
    }


    /* 2. Close self-exclusion popup */
	@Test(groups = {"regression"})
	public void cancelSelfExclusion(){
		ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.selfExclusion, defaultUserData.getRegisteredUserData());
		SelfExcludePopup selfExcludePopup = responsibleGamingPage.navigateToSelfExclude();
		selfExcludePopup.clickClose();
		TypeUtils.assertTrueWithLogs(new AbstractPage().isLoggedIn(), "Logged in");
	}

}
