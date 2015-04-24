import enums.ConfiguredPages;
import enums.Page;
import enums.PlayerCondition;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.account.TransactionHistoryPage;
import utils.IMSUtils;
import utils.NavigationUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Vadymfe on 4/17/2015.
 */
public class TransactionHistoryTest extends AbstractTest {

    private static final String BONUS_AMOUNT = "0.50";
    private TransactionHistoryPage trHistory;
    private DateFormat dateFormat;
    private Date currentTime;
    private HomePage homePage;
    private String oldBalance;
    private String currencySymbol = " ";
    private String curDate;

    @Test(groups = {"regression", "P3", "COR-473", "TransactionHistory"})
    public void shouldSeeListOfGroups(){

        trHistory = (TransactionHistoryPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.transactionHistory);

        assertEquals("All", trHistory.getGroupText(TransactionHistoryPage.ALL_OPT), "First tab(group) capture was not as expected");
        assertEquals("Games", trHistory.getGroupText(TransactionHistoryPage.GAME_OPT), "Second tab(group) capture was not as expected");
        assertEquals("General", trHistory.getGroupText(TransactionHistoryPage.GENERAL_OPT), "Third tab(group) capture was not as expected");
    }

    @Test(groups = {"regression", "P1", "COR-474", "TransactionHistory"})
    public void shouldSeeLastGeneralTransactions(){

        homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.home);
        prepareBonus();
        trHistory = (TransactionHistoryPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.transactionHistory);

        trHistory.selectPeriod(TransactionHistoryPage.ONE_DAY);
        trHistory.clickGroups(TransactionHistoryPage.GENERAL_OPT);
        trHistory.clickViewHistoryBtn();

        //ToDo check
        //assertTrue(trHistory.getEntryTime(0).contains(curDate), "Transaction History entry time was not as expected!");
        assertTrue(trHistory.isEntryTimeVisible(0, currentTime), "Transaction History entry time was not as expected!");
        assertEquals("Add bonus", trHistory.getEntryType(0), "Transaction History entry type was not as expected!");
        assertEquals(BONUS_AMOUNT + currencySymbol, trHistory.getEntryAmount(0), "Transaction History entry amount was not as expected!");
        assertEquals(homePage.getBalanceAmount() + currencySymbol, trHistory.getEntryBalance(0), "Transaction History entry amount was not as expected!");
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
    }

    private void prepareBonus(){
        oldBalance = homePage.getBalanceAmount();
        currencySymbol += DataContainer.getUserData().getCurrencySign();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        currentTime = new Date();
        curDate = dateFormat.format(currentTime);
        curDate = curDate.substring(0, curDate.length() - 1);
        //System.out.println(curDate);
        IMSUtils.sendPushMessage(DataContainer.getUserData(), BONUS_AMOUNT, Page.okBonus);
    }
}
