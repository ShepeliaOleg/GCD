import enums.BonusType;
import enums.ConfiguredPages;
import enums.Page;
import enums.PlayerCondition;
import org.testng.annotations.Test;
import pageObjects.admin.AdminPageAdmin;
import pageObjects.admin.settings.GamesManagementPopup;
import pageObjects.bonus.AcceptDeclineBonusPopup;
import pageObjects.external.ims.IMSBonusTemplateInfoPage;
import pageObjects.external.ims.IMSPlayerBonusInfoPage;
import pageObjects.external.ims.IMSPlayerDetailsPage;
import pageObjects.gamesPortlet.GamesPortletPage;
import pageObjects.replacers.*;
import springConstructors.UserData;
import utils.*;
import utils.core.AbstractTest;
import utils.core.DataContainer;
import utils.core.WebDriverFactory;
import utils.validation.ValidationUtils;

/**
 * Created by serhiist on 3/11/2015.
 */
public class ReplacersTest extends AbstractTest {
    private static final String AMOUNT = "1.00";
    private static final String MARKETING_NAME = "Nika";

    /**
     * PROMOTIONAL CODE REPLACER START
     */

    /*1. Promotional code is a replaser*/
    @Test(groups = {"regression"})
    public static void isPomotionalCodeReplaserDisplayed() {
        try {
            PromotionalCodeReplacerPage promotionalCodeReplacerPage = (PromotionalCodeReplacerPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.promotional_code_replacer);
        } catch (Exception e) {
            skipTest();
        }
    }

    /*2. Bonus is not given if promotional code is blank*/
    @Test(groups = {"regression"})
    public static void bonusIsNotGivenToPlayerIfPromotionalCodeIsBlank() {
        PromotionalCodeReplacerPage promotionalCodeReplacerPage = (PromotionalCodeReplacerPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.promotional_code_replacer);
        Float balanceAmount = Float.valueOf(promotionalCodeReplacerPage.getBalanceAmount());
        promotionalCodeReplacerPage.clearAndLeaveEmptyPromocodeField();
        assertEquals(PromotionalCodeReplacerPage.EMPTY_PROMOCODE_FIELD_TOOLTIP_TXT, promotionalCodeReplacerPage.getPromotionalCodeTooltipText(), "Wrong tooltip for promotional code field displayed. ");
        promotionalCodeReplacerPage.assertPromotionalCodeTooltipStatus(ValidationUtils.STATUS_FAILED, "empty");
        assertTrue(Float.compare(balanceAmount, Float.valueOf(promotionalCodeReplacerPage.getBalanceAmount())) == 0, "Balance changed for empty promotional code");
    }

    /*3. Bonus is not given if promotional code is invalid*/
    @Test(groups = {"regression"})
    public static void bonusIsNotGivenToPlayerIfPromotionalCodeIsInvalid() {
        PromotionalCodeReplacerPage promotionalCodeReplacerPage = (PromotionalCodeReplacerPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.promotional_code_replacer);
        Float balanceAmount = Float.valueOf(promotionalCodeReplacerPage.getBalanceAmount());
        promotionalCodeReplacerPage.clearFieldAndInputNotValidPromocode();
        promotionalCodeReplacerPage.assertPromotionalCodeTooltipStatus(ValidationUtils.STATUS_NONE, "invalid promocode");
        assertEquals(PromotionalCodeReplacerPage.NO_BONUS_GIVEN_ALERT_TXT, promotionalCodeReplacerPage.getAlertMessageText(), "Wrong alert message for invalid promocode displayed. ");
        assertTrue(Float.compare(balanceAmount, Float.valueOf(promotionalCodeReplacerPage.getBalanceAmount())) == 0, "Balance changed for invalid promotional code");
    }

    /*4. Bonus is given if promotional code is valid*/
    @Test(groups = {"regression"})
    public static void bonusIsGivenToPlayerIfPromotionalCodeIsValid() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        userData.setCurrency("USD");
        PortalUtils.registerUser(userData);
        PromotionalCodeReplacerPage promotionalCodeReplacerPage = (PromotionalCodeReplacerPage) NavigationUtils.navigateToPage(ConfiguredPages.promotional_code_replacer);
        Float balanceAmount = Float.valueOf(promotionalCodeReplacerPage.getBalanceAmount());
        ValidPromotionalCodePopup validPromotionalCodePopup = promotionalCodeReplacerPage.clearFieldAndInputValidPromocode();
        assertEquals(ValidPromotionalCodePopup.BONUS_GIVEN_MESSAGE_TXT, validPromotionalCodePopup.getMessage(), "Wrong message for valid promotional code displayed. ");
        validPromotionalCodePopup.closePopup();
        NavigationUtils.refreshPage();
        assertTrue(Float.valueOf(promotionalCodeReplacerPage.getBalanceAmount()) - balanceAmount == 7.0, "Balance not changed for valid promotional code");
    }

    /**
     * PROMOTIONAL CODE REPLACER END
     * */

    /**
     * [TOTAL_BONUS_BALANCE] REPLACER START
     */

    /*5. Bonus history page is displayed*/
    @Test(groups = {"regression"})
    public static void isBonusHistoryDisplayed() {
        try {
            BonusHistoryPage bonusHistoryPage = (BonusHistoryPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.bonusHistory);
        } catch (Exception e) {
            skipTest();
        }
    }

    /*6. Total Bonus Balance update for non acceptable bonus*/
    @Test(groups = {"regression"})
    public static void isTotalBonusBalanceUpdatedForNonAcceptableBonus() {
        BonusHistoryPage bonusHistoryPage = (BonusHistoryPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.bonusHistory);
        String initialBalance = bonusHistoryPage.getTotalBonusBalance();
        String initialBalanceAmount = TypeUtils.getBalanceAmount(initialBalance);
        IMSPlayerDetailsPage imsPlayerDetailsPage = IMSUtils.navigateToPlayedDetails(DataContainer.getUserData().getRegisteredUserData().getUsername());
        assertEquals(imsPlayerDetailsPage.getTotalBonusAmount(), initialBalanceAmount, "Initial amount of total bonus should be equals to value on IMS");
        assertEquals(imsPlayerDetailsPage.getTotalBonusCurrency(), TypeUtils.getBalanceCurrency(initialBalance), "Currency of total bonus should be equals to value on IMS");
        imsPlayerDetailsPage.addBonus(Page.okBonus, AMOUNT);
        NavigationUtils.refreshPage();
        String finalBalanceAmount = TypeUtils.getBalanceAmount(bonusHistoryPage.getTotalBonusBalance());
        assertEquals(imsPlayerDetailsPage.getTotalBonusAmount(), finalBalanceAmount, "Amount of total bonus should be equals to value on IMS after bonus applying");
        assertTrue(Float.parseFloat(finalBalanceAmount) - Float.parseFloat(initialBalanceAmount) == Float.parseFloat(AMOUNT), "Right bonus amount was added");
    }

    /*7. Total Bonus Balance update for accepted bonus*/
    @Test(groups = {"regression"})
    public static void isTotalBonusBalanceUpdatedForAcceptedBonus() {
        BonusHistoryPage bonusHistoryPage = (BonusHistoryPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.bonusHistory);
        String initialBalance = bonusHistoryPage.getTotalBonusBalance();
        IMSPlayerDetailsPage imsPlayerDetailsPage = IMSUtils.navigateToPlayedDetails(DataContainer.getUserData().getRegisteredUserData().getUsername());
        assertEquals(imsPlayerDetailsPage.getTotalBonusAmount(), TypeUtils.getBalanceAmount(initialBalance), "Initial amount of total bonus should be equals to value on IMS");
        assertEquals(imsPlayerDetailsPage.getTotalBonusCurrency(), TypeUtils.getBalanceCurrency(initialBalance), "Currency of total bonus should be equals to value on IMS");
        imsPlayerDetailsPage.addBonus(Page.acceptDeclineBonus, AMOUNT);
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup) NavigationUtils.closeAllPopups(Page.acceptDeclineBonus);
        acceptDeclineBonusPopup.clickAccept();
        String finalBalanceAmount = TypeUtils.getBalanceAmount(bonusHistoryPage.getTotalBonusBalance());
        assertEquals(imsPlayerDetailsPage.getTotalBonusAmount(), finalBalanceAmount, "Amount of total bonus should be equals to value on IMS after bonus accepting");
    }

    /*8. Total Bonus Balance update for declined bonus*/
    @Test(groups = {"regression"})
    public static void isTotalBonusBalanceNotUpdatedForDeclineBonus() {
        BonusHistoryPage bonusHistoryPage = (BonusHistoryPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.bonusHistory);
        String initialBalance = bonusHistoryPage.getTotalBonusBalance();
        IMSPlayerDetailsPage imsPlayerDetailsPage = IMSUtils.navigateToPlayedDetails(DataContainer.getUserData().getRegisteredUserData().getUsername());
        assertEquals(imsPlayerDetailsPage.getTotalBonusAmount(), TypeUtils.getBalanceAmount(initialBalance), "Initial amount of total bonus should be equals to value on IMS");
        assertEquals(imsPlayerDetailsPage.getTotalBonusCurrency(), TypeUtils.getBalanceCurrency(initialBalance), "Currency of total bonus should be equals to value on IMS");
        imsPlayerDetailsPage.addBonus(Page.acceptDeclineBonus, AMOUNT);
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup) NavigationUtils.closeAllPopups(Page.acceptDeclineBonus);
        acceptDeclineBonusPopup.clickDecline();
        String finalBalanceAmount = TypeUtils.getBalanceAmount(bonusHistoryPage.getTotalBonusBalance());
        assertEquals(imsPlayerDetailsPage.getTotalBonusAmount(), finalBalanceAmount, "Amount of total bonus should be equals to value on IMS after bonus declining");
    }

    /*9. Bonus history page with 3 symbol currency */
    @Test(groups = {"regression"})
    public static void CurrencyOnBonusHistoryPageWithThreeSymbolCurrencyDisplayedRight() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        userData.setCurrency("AUD");
        PortalUtils.registerUser(userData);
        BonusHistoryPage bonusHistoryPage = (BonusHistoryPage) NavigationUtils.navigateToPage(ConfiguredPages.bonusHistory);
        assertEquals(userData.getCurrency(), TypeUtils.getBalanceCurrency(bonusHistoryPage.getTotalBonusBalance()), "3 symbol currency at total bonus balance displayed right");
    }

    /**
     * [TOTAL_BONUS_BALANCE] REPLACER END
     * */

    /**
     * [BONUS_DETAIL_BALANCE] REPLACER START
     */
    /*10. Bonus Detail balance updated for non acceptable bonus*/
    @Test(groups = {"regression"})
    public static void bonusDetailsDisplayedForNonAcceptableBonus() {
        Page bonusType = Page.okBonus;
        UserData userData = DataContainer.getUserData().getBonusUserData();
        userData.setCurrency("USD");
        PortalUtils.loginUser(userData);
        BonusHistoryPage bonusHistoryPage = (BonusHistoryPage) NavigationUtils.navigateToPage(ConfiguredPages.bonusHistory);
        IMSPlayerDetailsPage imsPlayerDetailsPage = IMSUtils.navigateToPlayedDetails(userData.getUsername());
        imsPlayerDetailsPage.addBonus(bonusType, AMOUNT);
        WebDriverUtils.refreshPage();
        bonusHistoryPage.clickOnBonus(bonusType);
        assertTrue(bonusHistoryPage.isBonusAddedToList(bonusType) && bonusHistoryPage.getBonusAmount(bonusType).equals(AMOUNT), "Non acceptable bonus is added to bonus detail balance");
        assertEquals(AMOUNT, bonusHistoryPage.getCurrentBalanceValue(bonusType), "Current balance value");
        assertEquals("$", bonusHistoryPage.getCurrentBalanceCurrency(bonusType), "Current balance currency");
    }

    /*11. Bonus Detail balance updated for accepted bonus*/
    @Test(groups = {"regression"})
    public static void bonusDetailsDisplayedForAcceptedBonus() {
        Page bonusType = Page.acceptDeclineBonus;
        UserData userData = DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData);
        BonusHistoryPage bonusHistoryPage = (BonusHistoryPage) NavigationUtils.navigateToPage(ConfiguredPages.bonusHistory);
        IMSPlayerDetailsPage imsPlayerDetailsPage = IMSUtils.navigateToPlayedDetails(userData.getUsername());
        imsPlayerDetailsPage.addBonus(bonusType, AMOUNT);
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup) NavigationUtils.closeAllPopups(Page.acceptDeclineBonus);
        acceptDeclineBonusPopup.clickAccept();
        WebDriverUtils.refreshPage();
        bonusHistoryPage.clickOnBonus(bonusType);
        assertTrue(bonusHistoryPage.isBonusAddedToList(bonusType) && bonusHistoryPage.getBonusAmount(bonusType).equals(AMOUNT), "Accept/decline bonus is added to bonus detail balance");
        assertEquals("0.00", bonusHistoryPage.getCurrentBalanceValue(bonusType), "Current balance value");
    }

    /*12. Bonus Detail balance updated for declined bonus*/
    @Test(groups = {"regression"})
    public static void bonusDetailsDisplayedForDeclinedBonus() {
        Page bonusType = Page.acceptDeclineBonus;
        UserData userData = DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData);
        BonusHistoryPage bonusHistoryPage = (BonusHistoryPage) NavigationUtils.navigateToPage(ConfiguredPages.bonusHistory);
        IMSPlayerDetailsPage imsPlayerDetailsPage = IMSUtils.navigateToPlayedDetails(userData.getUsername());
        imsPlayerDetailsPage.addBonus(bonusType, AMOUNT);
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup) NavigationUtils.closeAllPopups(Page.acceptDeclineBonus);
        acceptDeclineBonusPopup.clickDecline();
        WebDriverUtils.refreshPage();
        bonusHistoryPage.clickOnBonus(bonusType);
        assertTrue(bonusHistoryPage.isBonusAddedToList(bonusType) && bonusHistoryPage.getBonusAmount(bonusType).equals(AMOUNT), "Non acceptable bonus is added to bonus detail balance");
        assertEquals("0.00", bonusHistoryPage.getCurrentBalanceValue(bonusType), "Current balance value");
    }

    /*13. Bonus Detail balance check 3 symbol currency*/
    @Test(groups = {"regression"})
    public static void bonusDetailsDisplayedCheckThreeSymbolCurrency() {
        Page bonusType = Page.okBonus;
        UserData userData = DataContainer.getUserData().getBonusUserData();
        userData.setCurrency("AUD");
        PortalUtils.loginUser(userData);
        BonusHistoryPage bonusHistoryPage = (BonusHistoryPage) NavigationUtils.navigateToPage(ConfiguredPages.bonusHistory);
        IMSPlayerDetailsPage imsPlayerDetailsPage = IMSUtils.navigateToPlayedDetails(userData.getUsername());
        imsPlayerDetailsPage.addBonus(bonusType, AMOUNT);
        WebDriverUtils.refreshPage();
        bonusHistoryPage.clickOnBonus(bonusType);
        assertEquals(userData.getCurrency(), bonusHistoryPage.getCurrentBalanceCurrency(bonusType), "Current balance with 3 symbol currency");
    }

    /*14. Bonus Detail balance check bonus list scrolling*/
    @Test(groups = {"regression"})
    public static void bonusDetailsDisplayedCheckBonusListScrolling() {
        UserData userData = getBonusUser();
        PortalUtils.loginUser(userData);
        BonusHistoryPage bonusHistoryPage = (BonusHistoryPage) NavigationUtils.navigateToPage(ConfiguredPages.bonusHistory);
        IMSPlayerDetailsPage imsPlayerDetailsPage = IMSUtils.navigateToPlayedDetails(userData.getUsername());
        IMSPlayerBonusInfoPage imsPlayerBonusInfoPage = imsPlayerDetailsPage.clickPlayerDetailsInfo();
        int totalBonusesAmount = imsPlayerBonusInfoPage.getCountOfBonuses();
        WebDriverUtils.refreshPage();
        WebDriverUtils.waitFor(500);
        bonusHistoryPage.clickLoadMoreButton();
        assertEquals(totalBonusesAmount, bonusHistoryPage.getNumberOfBonusesInList(), "Right number of bonuses displayed");
    }
    /**
     * [BONUS_DETAIL_BALANCE] REPLACER END
     * */

    /**
     * [TOTAL_FREE_SPIN_BALANCE] REPLACER START
     */

    /*15. Total free spins balance is displayed*/
    @Test(groups = {"regression"})
    public static void isTotalFreeSpinReplaserDisplayed() {
        try {
            TotalFreeSpinsBalancePage totalFreeSpinsBalancePage = (TotalFreeSpinsBalancePage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.freeSpinsBalanceTotal);
        } catch (Exception e) {
            skipTest();
        }
    }

    /*16. Total free spins amount for new user*/
    @Test(groups = {"regression"})
    public static void totalFreeSpinsAmountNewUser() {
        UserData userData = DataContainer.getUserData().getInternalRandomUserData();
        PortalUtils.registerUser(userData);
        TotalFreeSpinsBalancePage totalFreeSpinsBalancePage = (TotalFreeSpinsBalancePage) NavigationUtils.navigateToPage(ConfiguredPages.freeSpinsBalanceTotal);
        assertEquals("0", totalFreeSpinsBalancePage.getTotalFreeSpinBalance(), "Total free spins balance for new user equals to '0'");
    }

    /*17. Total free spins amount for new user*/
    @Test(groups = {"regression"})
    public static void addTotalFreeSpins() {
        String spinsAmount = "1";
        UserData userData = DataContainer.getUserData().getInternalRandomUserData();
        PortalUtils.registerUser(userData);
        IMSUtils.navigateToPlayedDetails(userData.getUsername()).addBonus(Page.freeSpins, spinsAmount, BonusType.freeSpins);
        TotalFreeSpinsBalancePage totalFreeSpinsBalancePage = navigateToTotalFreeSpinsBalance();
        assertEquals(spinsAmount, totalFreeSpinsBalancePage.getTotalFreeSpinBalance(), "Total free spins balance for user equals to amount added on IMS");
    }

    /*18. Using of Total free spins*/
    @Test(groups = {"regression"})
    public static void usingFreeSpins() {
        String spinsAmount = "1";
        UserData userData = DataContainer.getUserData().getInternalRandomUserData();
        PortalUtils.registerUser(userData);
        IMSUtils.navigateToPlayedDetails(userData.getUsername()).addBonus(Page.freeSpins, spinsAmount, BonusType.freeSpins);
        TotalFreeSpinsBalancePage totalFreeSpinsBalancePage = navigateToTotalFreeSpinsBalance();
        assertEquals(spinsAmount, totalFreeSpinsBalancePage.getTotalFreeSpinBalance(), "Total free spins balance for user equals to amount added on IMS");
        doSpinInGame();
        NavigationUtils.navigateToPage(ConfiguredPages.freeSpinsBalanceTotal);
        assertEquals("0", totalFreeSpinsBalancePage.getTotalFreeSpinBalance(), "Total free spins balance after using them");
    }

    /*19.  Total free spins amount displayed properly after relogin*/
    @Test(groups = {"regression"})
    public static void checkFreeSpinsAmountAfrerRelogin() {
        String spinsAmount = "1";
        UserData userData = DataContainer.getUserData().getInternalRandomUserData();
        PortalUtils.registerUser(userData);
        IMSUtils.navigateToPlayedDetails(userData.getUsername()).addBonus(Page.freeSpins, spinsAmount, BonusType.freeSpins);
        TotalFreeSpinsBalancePage totalFreeSpinsBalancePage = navigateToTotalFreeSpinsBalance();
        assertEquals(spinsAmount, totalFreeSpinsBalancePage.getTotalFreeSpinBalance(), "Total free spins balance for user equals to amount added on IMS");
        totalFreeSpinsBalancePage.logout();
        PortalUtils.loginUser(userData);
        NavigationUtils.navigateToPage(ConfiguredPages.freeSpinsBalanceTotal);
        WebDriverUtils.refreshPage();
        WebDriverUtils.waitFor();
        assertEquals(spinsAmount, totalFreeSpinsBalancePage.getTotalFreeSpinBalance(), "Total free spins balance for user is shown properly after relogin");
    }

    /*20. Total free spins amount for several new users displayed properly*/
    @Test(groups = {"regression"})
    public static void checkFreeSpinsAmountForDifferentUsers() {
        String spinsAmount = "1";
        String spinsAmount2 = "5";
        UserData userData1 = DataContainer.getUserData().getInternalRandomUserData();
        PortalUtils.registerUser(userData1);
        IMSUtils.navigateToPlayedDetails(userData1.getUsername()).addBonus(Page.freeSpins, spinsAmount, BonusType.freeSpins);
        TotalFreeSpinsBalancePage totalFreeSpinsBalancePage = navigateToTotalFreeSpinsBalance();
        assertEquals(spinsAmount, totalFreeSpinsBalancePage.getTotalFreeSpinBalance(), "Total free spins balance for user1 equals to amount added on IMS");
        totalFreeSpinsBalancePage.logout();

        UserData userData2 = DataContainer.getUserData().getInternalRandomUserData();
        PortalUtils.registerUser(userData2);
        IMSUtils.navigateToPlayedDetails(userData2.getUsername()).addBonus(Page.freeSpins, spinsAmount2, BonusType.freeSpins);
        navigateToTotalFreeSpinsBalance();
        assertEquals(spinsAmount2, totalFreeSpinsBalancePage.getTotalFreeSpinBalance(), "Total free spins balance for user2 equals to amount added on IMS");
        totalFreeSpinsBalancePage.logout();

        PortalUtils.loginUser(userData1);
        NavigationUtils.navigateToPage(ConfiguredPages.freeSpinsBalanceTotal);
        WebDriverUtils.refreshPage();
        WebDriverUtils.waitFor();
        assertEquals(spinsAmount, totalFreeSpinsBalancePage.getTotalFreeSpinBalance(), "Total free spins balance for user1 is shown properly after relogin");
    }

    /**
     * [TOTAL_FREE_SPIN_BALANCE] REPLACER END
     * */

     private static UserData getBonusUser() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        userData.setUsername("bonusUser");
        return userData;
    }

    private static void doSpinInGame(){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesFavourites);
        gamesPortletPage.playReal(2);
        gamesPortletPage.waitGameLoaded();
        gamesPortletPage.doSpin();
    }

    private static TotalFreeSpinsBalancePage navigateToTotalFreeSpinsBalance(){
        TotalFreeSpinsBalancePage totalFreeSpinsBalancePage = (TotalFreeSpinsBalancePage) NavigationUtils.navigateToPage(ConfiguredPages.freeSpinsBalanceTotal);
        new AcceptDeclineBonusPopup().clickAccept();
        WebDriverUtils.refreshPage();
        WebDriverUtils.waitFor();
        return totalFreeSpinsBalancePage;
    }
 }
