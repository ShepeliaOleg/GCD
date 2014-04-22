import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.account.LoginPopup;
import pageObjects.gamesPortlet.GameLaunchPopup;
import pageObjects.liveCasino.DealerImagePopup;
import pageObjects.liveCasino.LiveCasinoPage;
import springConstructors.UserData;
import testUtils.AbstractTest;
import utils.NavigationUtils;
import utils.RandomUtils;

/**
 * User: sergiich
 * Date: 4/10/14
 */

public class LiveTableFinderTest extends AbstractTest{

	@Autowired
	@Qualifier("userData")
	private UserData defaultUserData;

	/*2. Choose game section – scope selection (check and uncheck categories) - should be made for all the categories*/
	@Test(groups = {"regression"})
	public void clickCheckboxesAndCheckGameTypesDisappear(){
		HomePage homePage=NavigationUtils.navigateToHome();
		LiveCasinoPage liveCasinoPage=homePage.navigateToLiveCasino();
		boolean result=true;
		for(int i=1; i <= liveCasinoPage.getNumberOfCheckBoxes(); i++){
			liveCasinoPage.clickCheckbox(i);
			boolean uncheckedVisible=liveCasinoPage.isCheckboxResultsVisible(i);
			liveCasinoPage.clickCheckbox(i);
			boolean checkedVisible=liveCasinoPage.isCheckboxResultsVisible(i);
			if(uncheckedVisible || !checkedVisible){
				result=false;
				break;
			}
		}
		Assert.assertTrue(result);
	}

    /*3. Find a table – sorting by Game Name*/
	@Test(groups = {"regression"})
	public void sortingNameColumnCorrectlyWorks(){
		HomePage homePage=NavigationUtils.navigateToHome();
		LiveCasinoPage liveCasinoPage=homePage.navigateToLiveCasino();
		liveCasinoPage.sortName();
		String initialName=liveCasinoPage.getNameElement(1);
		liveCasinoPage.sortName();
		Assert.assertTrue(initialName.equals(liveCasinoPage.getNameElement(liveCasinoPage.getNumberOfRows())));
	}

    /*4. Find a table – sorting by game type*/
	@Test(groups = {"regression"})
	public void sortingGameTypeColumnCorrectlyWorks(){
		HomePage homePage=NavigationUtils.navigateToHome();
		LiveCasinoPage liveCasinoPage=homePage.navigateToLiveCasino();
		liveCasinoPage.sortGameType();
		String initialName=liveCasinoPage.getGameTypeElement(1);
		liveCasinoPage.sortGameType();
		Assert.assertTrue(initialName.equals(liveCasinoPage.getGameTypeElement(liveCasinoPage.getNumberOfRows())));
	}

    /*5. Find a table – sorting by dealer’s name*/
	@Test(groups = {"regression"})
	public void sortingDealersNameColumnCorrectlyWorks(){
		HomePage homePage=NavigationUtils.navigateToHome();
		LiveCasinoPage liveCasinoPage=homePage.navigateToLiveCasino();
		liveCasinoPage.sortDealerName();
		String initialName=liveCasinoPage.getDealerName(1);
		liveCasinoPage.sortDealerName();
		Assert.assertTrue(initialName.equals(liveCasinoPage.getDealerName(liveCasinoPage.getNumberOfRows())));
	}

	/*6. Find a table – launch game as a player and play*/
	@Test(groups = {"regression"})
	public void loggedInUserPlayGame(){
		UserData userData=defaultUserData.getRegisteredUserData();
		HomePage homePage=(HomePage)NavigationUtils.navigateToPortal(true).login(userData);
		LiveCasinoPage liveCasinoPage=homePage.navigateToLiveCasino();
		int index=RandomUtils.generateRandomIntBetween(1, liveCasinoPage.getNumberOfRows());
		GameLaunchPopup gameLaunchPopup=(GameLaunchPopup) liveCasinoPage.clickPlay(index, true);
		boolean isUrlValid=gameLaunchPopup.isUrlValid();
		gameLaunchPopup.closePopup();
		Assert.assertTrue(isUrlValid);
	}

	/*7. Find a table – launch game as a guest, login and play*/
	@Test(groups = {"regression"})
	public void loggedOutUserPlayGame(){
		UserData userData=defaultUserData.getRegisteredUserData();
		HomePage homePage=NavigationUtils.navigateToPortal(true);
		LiveCasinoPage liveCasinoPage=homePage.navigateToLiveCasino();
		int index=RandomUtils.generateRandomIntBetween(1, liveCasinoPage.getNumberOfRows());
		LoginPopup loginPopup=(LoginPopup) liveCasinoPage.clickPlay(index, false);
		homePage = loginPopup.login(userData);
		GameLaunchPopup gameLaunchPopup = homePage.switchToGameWindow();
		boolean isUrlValid=gameLaunchPopup.isUrlValid();
		gameLaunchPopup.closePopup();
		Assert.assertTrue(isUrlValid);
	}

    /*8. Find a table – View Info about a dealer*/
	@Test(groups = {"regression"})
	public void checkDealerImagesAppearInPopup(){
		HomePage homePage=NavigationUtils.navigateToHome();
		LiveCasinoPage liveCasinoPage=homePage.navigateToLiveCasino();
		int index=RandomUtils.generateRandomIntBetween(1, liveCasinoPage.getNumberOfRows());
		DealerImagePopup dealerImagePopup=liveCasinoPage.clickInfo(index);
		Assert.assertTrue(dealerImagePopup.imageNotNull());
	}

    /*9. Verify dealer's name inside of Info popup*/
	@Test(groups = {"regression"})
	public void checkDealerNameFromListCorrespondsToDealerNameInPopup(){
		HomePage homePage=NavigationUtils.navigateToHome();
		LiveCasinoPage liveCasinoPage=homePage.navigateToLiveCasino();
		int index=RandomUtils.generateRandomIntBetween(1, liveCasinoPage.getNumberOfRows());
		String dealerName=liveCasinoPage.getDealerName(index);
		DealerImagePopup dealerImagePopup=liveCasinoPage.clickInfo(index);
		Assert.assertTrue(dealerName.equalsIgnoreCase(dealerImagePopup.getDealerName()));
	}


}
