import enums.ConfiguredPages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.bingoSchedule.BingoSchedulePage;
import springConstructors.BingoScheduleData;
import testUtils.AbstractTest;
import utils.NavigationUtils;

/**
 * User: sergiich
 * Date: 4/14/14
 */
public class BingoScheduleTest extends AbstractTest{

	@Autowired
	@Qualifier("bingoScheduleData")
	private BingoScheduleData bingoScheduleData;

	/*POSITIVE*/

	/* 1. Portlet is available */
	@Test(groups = {"smoke"})
	public void portletIsDisplayed() {
		BingoSchedulePage bingoSchedulePage = NavigationUtils.navigateToBingoSchedulePage(true, ConfiguredPages.bingoLobbyFeed);
	}

	/* 2. Proper columns are displayed */
	@Test(groups = {"regression"})
	public void columns() {
		BingoSchedulePage bingoSchedulePage = NavigationUtils.navigateToPortal(true)
				.navigateToBingoSchedulePage(ConfiguredPages.bingoLobbyFeed);
		Integer actualColumnsNumber = bingoSchedulePage.getColumnsNumber();
		Integer expectedColumnsNumber = bingoScheduleData.getColumnsNumber();
		boolean columnsNumberIsCorrect = (actualColumnsNumber == expectedColumnsNumber);
		columnsNumberIsCorrect = actualColumnsNumber.equals(expectedColumnsNumber);
		columnsNumberIsCorrect = (actualColumnsNumber == expectedColumnsNumber) ? true : false;
		Assert.assertTrue(columnsNumberIsCorrect);
	}
}
