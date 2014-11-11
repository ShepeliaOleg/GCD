import enums.ConfiguredPages;
import org.testng.annotations.Test;
import pageObjects.core.AbstractPage;
import pageObjects.responsibleGaming.SelfExcludePage;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.core.AbstractTest;

public class SelfExclusionTest extends AbstractTest{

	/* Positive */

    /* 1. Self-exlude and verify player is logged out*/
    @Test(groups = {"regression"})
    public void setSelfExclusion(){
        PortalUtils.registerUser();
        SelfExcludePage selfExcludePage= (SelfExcludePage) NavigationUtils.navigateToPage(ConfiguredPages.selfExclusion);
        selfExcludePage.submitSelfExclude();
//        new AbstractPage().waitForLogout();
    }
}
