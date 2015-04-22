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
    public void shouldSeeListOfGroups(){

        TransactionHistoryPage trHistory = (TransactionHistoryPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.transactionHistory);

        assertEquals("All", trHistory.getGroupText(TransactionHistoryPage.ALL_OPT), "First tab(group) capture was not as expected");
        assertEquals("Games", trHistory.getGroupText(TransactionHistoryPage.GAME_OPT), "Second tab(group) capture was not as expected");
        assertEquals("General", trHistory.getGroupText(TransactionHistoryPage.GENERAL_OPT), "Third tab(group) capture was not as expected");
    }

    @Test
    public void playerShouldHasGeneralTransactions(){
        TransactionHistoryPage trHistory = (TransactionHistoryPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.transactionHistory);

        trHistory.selectPeriod(TransactionHistoryPage.ONE_DAY);
        trHistory.clickGroups(TransactionHistoryPage.GENERAL_OPT);
        trHistory.clickViewHistoryBtn();

        System.out.println(trHistory.getTransactionCount());
        //ToDo check
        assertTrue(trHistory.getTransactionCount() > 0, "");
        System.out.println("CHECK FINISHED");
    }

    @Test
    public void playerShouldHasGameTransactions(){
        TransactionHistoryPage trHistory = (TransactionHistoryPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.transactionHistory);

        trHistory.selectPeriod(TransactionHistoryPage.ONE_DAY);
        trHistory.clickGroups(TransactionHistoryPage.GAME_OPT);
        trHistory.clickViewHistoryBtn();

        System.out.println(trHistory.getTransactionCount());
        //ToDo check
        assertTrue(trHistory.getTransactionCount() > 0, "");
        System.out.println("CHECK FINISHED");
    }

    @Test
    public void playerShouldHasAllTransactions(){
        TransactionHistoryPage trHistory = (TransactionHistoryPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.transactionHistory);

        trHistory.selectPeriod(TransactionHistoryPage.ONE_DAY);
        trHistory.clickGroups(TransactionHistoryPage.ALL_OPT);
        trHistory.clickViewHistoryBtn();

        System.out.println(trHistory.getTransactionCount());
        //ToDo check
        assertTrue(trHistory.getTransactionCount() > 0, "");
        System.out.println("CHECK FINISHED");
    }
}
