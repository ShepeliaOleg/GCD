import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.InternalTagsPage;
import pageObjects.account.BalancePage;
import springConstructors.IMS;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.TypeUtils;
import utils.core.AbstractTest;

import java.util.HashMap;

/**
 * User: sergiich
 * Date: 4/10/14
 */
public class InternalTagsTest extends AbstractTest{

	@Autowired
	@Qualifier("iMS")
	private IMS iMS;

	@Autowired
	@Qualifier("userData")
	private UserData defaultUserData;

//    /*1. Internal tags are replaced with "-" for guest user.*/
//	@Test(groups = {"regression"})
//	public void checkInternalTagsForGuestUser(){
//        InternalTagsPage internalTagsPage = (InternalTagsPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.internalTags);
//        internalTagsPage.compareTags(true, null);
//        internalTagsPage.compareTags(false, null);
//	}
//
//    /*2. Internal tags availability on web content portlet*/
//	@Test(groups = {"regression"})
//	public void checkWorkingInternalTagsOnWebContentPortlet(){
//        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.home, defaultUserData.getRegisteredUserData());
//		HashMap imsData = iMS.getInternalTagsData();
//        InternalTagsPage internalTagsPage = (InternalTagsPage) NavigationUtils.navigateToPage(ConfiguredPages.internalTags);
//        internalTagsPage.compareTags(true, imsData);
//	}
//
//    /*3. Internal tags availability on multiview portlet*/
//	@Test(groups = {"regression"})
//	public void checkWorkingInternalTagsOnMultiviewPortlet(){
//        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.home, defaultUserData.getRegisteredUserData());
//		HashMap imsData = iMS.getInternalTagsData();
//        InternalTagsPage internalTagsPage = (InternalTagsPage) NavigationUtils.navigateToPage(ConfiguredPages.internalTags);
//        internalTagsPage.compareTags(false, imsData);
//	}
//
//    /*4. Balance is valid in balance portlet*/
//	@Test(groups = {"regression"})
//	public void validBalanceCashier(){
//		UserData userData=defaultUserData.getRandomUserData();
//        PortalUtils.registerUser(userData);
//		BalancePage balancePortlet= (BalancePage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.balance, userData);
//        TypeUtils.assertTrueWithLogs(balancePortlet.BalancesAreEqualTo(userData), "balances are equal");
//	}
}
