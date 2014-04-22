import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.account.BalancePage;
import pageObjects.account.MyAccountPage;
import springConstructors.IMS;
import springConstructors.UserData;
import testUtils.AbstractTest;
import utils.NavigationUtils;

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

    /*1. Internal tags are replaced with "-" for guest user.*/
	@Test(groups = {"regression"})
	public void checkInternalTagsForGuestUser(){
		HomePage homePage=NavigationUtils.navigateToPortal(true);
		homePage.navigateToInternalTagsPage().compareTags(true, null);
		homePage.navigateToInternalTagsPage().compareTags(false, null);
	}

    /*2. Internal tags availability on web content portlet*/
	@Test(groups = {"regression"})
	public void checkWorkingInternalTagsOnWebContentPortlet(){
		UserData userData = defaultUserData.getRegisteredUserData();
		NavigationUtils.navigateToPortal(true).login(userData);
		HashMap imsData = iMS.getInternalTagsData(userData);
		NavigationUtils.navigateToHome().navigateToInternalTagsPage().compareTags(true,imsData);
	}

    /*3. Internal tags availability on multiview portlet*/
	@Test(groups = {"regression"})
	public void checkWorkingInternalTagsOnMultiviewPortlet(){
		UserData userData = defaultUserData.getRegisteredUserData();
		NavigationUtils.navigateToPortal(true).login(userData);
		HashMap imsData = iMS.getInternalTagsData(userData);
		NavigationUtils.navigateToHome().navigateToInternalTagsPage().compareTags(false, imsData);
	}

    /*4. Balance is valid in balance portlet*/
	@Test(groups = {"regression"})
	public void validBalanceCashier(){
		HomePage homePage=NavigationUtils.navigateToPortal(true);
		UserData userData=defaultUserData.getRegisteredUserData().cloneUserData();
		homePage=(HomePage)homePage.login(userData);
		MyAccountPage myAccountPage=homePage.navigateToMyAccount();
		BalancePage balancePortlet=myAccountPage.navigateToBalancePortlet();
		boolean defaultBalances=balancePortlet.BalancesAreEqualTo(userData);
		Assert.assertTrue(defaultBalances);
	}
}
