import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.core.AbstractPage;
import pageObjects.header.Header;
import pageObjects.responsibleGaming.ResponsibleGamingPage;
import pageObjects.responsibleGaming.SelfExcludeConfirmPopup;
import pageObjects.responsibleGaming.SelfExcludePopup;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.TypeUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

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
            WebDriverUtils.waitForElementToDisappear(Header.BALANCE_AREA);
        }catch(RuntimeException e){
            WebDriverUtils.runtimeExceptionWithUrl("User was not logged out. Self exclusion failed.");
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
