import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.testng.annotations.Test;
import pageObjects.account.TransactionHistoryPage;
import utils.NavigationUtils;
import utils.core.AbstractTest;


/**
 * Created by Vadymfe on 4/17/2015.
 */
public class TransactionHistoryTest extends AbstractTest {

    @Test(groups = {"regression", "P3", "COR-473", "TransactionHistory"})
    public void listOfGroups(){

        TransactionHistoryPage trHistory = (TransactionHistoryPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.transactionHistory);
        assertEquals("All", trHistory.getFirstGroupText(), "First tab(group) capture was not as expected");
        assertEquals("Games", trHistory.getSecondGroupText(), "Second tab(group) capture was not as expected");
        assertEquals("General", trHistory.getThirdGroupText(), "Third tab(group) capture was not as expected");
    }
}
