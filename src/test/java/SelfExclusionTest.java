import utils.core.AbstractTest;

public class SelfExclusionTest extends AbstractTest{

	/* Positive */

    /* 1. Self-exlude and verify player is logged out*/
//    @Test(groups = {"regression"})
//    public void setSelfExclusion(){
//        PortalUtils.registerUser(defaultUserData.getRandomUserData());
//        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.selfExclusion);
//        SelfExcludePopup selfExcludePopup=responsibleGamingPage.navigateToSelfExclude();
//        SelfExcludeConfirmPopup selfExcludeConfirmPopup=selfExcludePopup.submitSelfExclude();
//        TypeUtils.assertTrueWithLogs(selfExcludeConfirmPopup.confirmationMessagesVisible(), "confirmationMessagesVisible");
//        selfExcludeConfirmPopup.clickOk();
//        try{
//            WebDriverUtils.waitForElementToDisappear(Header.BALANCE_AREA);
//        }catch(RuntimeException e){
//            WebDriverUtils.runtimeExceptionWithUrl("User was not logged out. Self exclusion failed.");
//        }
//    }
//
//
//    /* 2. Close self-exclusion popup */
//	@Test(groups = {"regression"})
//	public void cancelSelfExclusion(){
//		ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.selfExclusion, defaultUserData.getRegisteredUserData());
//		SelfExcludePopup selfExcludePopup = responsibleGamingPage.navigateToSelfExclude();
//		selfExcludePopup.closePopup();
//		TypeUtils.assertTrueWithLogs(new AbstractPage().isLoggedIn(), "Logged in");
//	}

}
