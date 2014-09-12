import enums.ConfiguredPages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.bingoSchedule.BingoSchedulePage;
import springConstructors.BingoScheduleData;
import utils.NavigationUtils;
import utils.TypeUtils;
import utils.core.AbstractTest;

/**
 * User: sergiich
 * Date: 4/14/14
 */
public class BingoScheduleTest extends AbstractTest{

	@Autowired
	@Qualifier("bingoScheduleData")
	private BingoScheduleData bingoScheduleData;

	/*POSITIVE*/
//
//	/* 1. Portlet is available */
//	@Test(groups = {"smoke"})
//	public void portletIsDisplayed() {
//		BingoSchedulePage bingoSchedulePage = (BingoSchedulePage) NavigationUtils.navigateToPage(ConfiguredPages.bingoLobbyFeed);
//	}
//
//	/* 2. Proper columns are displayed */
//	@Test(groups = {"regression"})
//	public void columns() {
//		BingoSchedulePage bingoSchedulePage = (BingoSchedulePage) NavigationUtils.navigateToPage(ConfiguredPages.bingoLobbyFeed);
//		Integer actualColumnsNumber = bingoSchedulePage.getColumnsNumber();
//		Integer expectedColumnsNumber = bingoScheduleData.getColumnsNumber();
//        TypeUtils.assertTrueWithLogs(actualColumnsNumber.equals(expectedColumnsNumber), "Column number is correct");
//	}
}
