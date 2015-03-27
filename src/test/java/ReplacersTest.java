import enums.ConfiguredPages;
import enums.Page;
import enums.PlayerCondition;
import org.testng.annotations.Test;
import pageObjects.bonus.AcceptDeclineBonusPopup;
import pageObjects.external.ims.IMSPlayerBonusInfoPage;
import pageObjects.external.ims.IMSPlayerDetailsPage;
import pageObjects.replacers.BonusHistoryPage;
import pageObjects.replacers.PromotionalCodeReplacerPage;
import pageObjects.replacers.ValidPromotionalCodePopup;
import springConstructors.UserData;
import utils.*;
import utils.core.AbstractTest;
import utils.core.DataContainer;
import utils.validation.ValidationUtils;

/**
 * Created by serhiist on 3/11/2015.
 */
public class ReplacersTest extends AbstractTest {
    private static final String AMOUNT = "1.00";

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
        assertTrue(Float.valueOf(promotionalCodeReplacerPage.getBalanceAmount()) - balanceAmount == 7.0, "Balance not changed for valid promotional code");
    }

    /**
     * PROMOTIONAL CODE REPLACER END
     * */

    /**
     * [TOTAL_BONUS_BALANCE] REPLACER START
     */

    /*Bonus history page is displayed*/
    @Test(groups = {"regression"})
    public static void isBonusHistoryDisplayed() {
        try {
            BonusHistoryPage bonusHistoryPage = (BonusHistoryPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.bonusHistory);
        } catch (Exception e) {
            skipTest();
        }
    }

    /*Total Bonus Balance update for non acceptable bonus*/
    @Test(groups = {"regression"})
    public static void isTotalBonusBalanceUpdatedForNonAcceptableBonus() {
        BonusHistoryPage bonusHistoryPage = (BonusHistoryPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.bonusHistory);
        String initialBalance = bonusHistoryPage.getTotalBonusBalance();
        String initialBalanceAmount = TypeUtils.getBalanceAmount(initialBalance);
        IMSPlayerDetailsPage imsPlayerDetailsPage = IMSUtils.navigateToPlayedDetails(DataContainer.getUserData().getRegisteredUserData().getUsername());
        assertEquals(imsPlayerDetailsPage.getTotalBonusAmount(), initialBalanceAmount, "Initial amount of total bonus should be equals to value on IMS");
        assertEquals(imsPlayerDetailsPage.getTotalBonusCurrency(), TypeUtils.getBalanceCurrency(initialBalance), "Currency of total bonus should be equals to value on IMS");
        imsPlayerDetailsPage.addBonus(Page.okBonus, AMOUNT);
        String finalBalanceAmount = TypeUtils.getBalanceAmount(bonusHistoryPage.getTotalBonusBalance());
        assertEquals(imsPlayerDetailsPage.getTotalBonusAmount(), finalBalanceAmount, "Amount of total bonus should be equals to value on IMS after bonus applying");
        assertTrue(Float.parseFloat(finalBalanceAmount) - Float.parseFloat(initialBalanceAmount) == Float.parseFloat(AMOUNT), "Right bonus amount was added");
    }

    /*Total Bonus Balance update for accepted bonus*/
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

    /*Total Bonus Balance update for declined bonus*/
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

    /*Bonus history page with 3 symbol currency */
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
     * */
    /*Bonus Detail balance updated for non acceptable bonus*/
    @Test(groups = {"regression"})
    public static void bonusDetailsDisplayedForNonAcceptableBonus() {
        Page bonusType = Page.okBonus;
        UserData userData = DataContainer.getUserData().getRandomUserData();
        userData.setCurrency("USD");
        PortalUtils.registerUser(userData);
        BonusHistoryPage bonusHistoryPage = (BonusHistoryPage) NavigationUtils.navigateToPage(ConfiguredPages.bonusHistory);
        IMSPlayerDetailsPage imsPlayerDetailsPage = IMSUtils.navigateToPlayedDetails(userData.getUsername());
        imsPlayerDetailsPage.addBonus(bonusType, AMOUNT);
        WebDriverUtils.refreshPage();
        bonusHistoryPage.clickOnBonus(bonusType);
        assertTrue(bonusHistoryPage.isBonusAddedToList(bonusType) && bonusHistoryPage.getBonusAmount(bonusType).equals(AMOUNT), "Non acceptable bonus is added to bonus detail balance");
        assertEquals(AMOUNT, bonusHistoryPage.getCurrentBalanceValue(bonusType), "Current balance value");
        assertEquals("$", bonusHistoryPage.getCurrentBalanceCurrency(bonusType), "Current balance currency");
    }

    /*Bonus Detail balance updated for accepted bonus*/
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

    /*Bonus Detail balance updated for declined bonus*/
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

    /*Bonus Detail balance check 3 symbol currency*/
    @Test(groups = {"regression"})
    public static void bonusDetailsDisplayedCheckThreeSymbolCurrency() {
        Page bonusType = Page.okBonus;
        UserData userData = DataContainer.getUserData().getRandomUserData();
        userData.setCurrency("AUD");
        PortalUtils.registerUser(userData);
        BonusHistoryPage bonusHistoryPage = (BonusHistoryPage) NavigationUtils.navigateToPage(ConfiguredPages.bonusHistory);
        IMSPlayerDetailsPage imsPlayerDetailsPage = IMSUtils.navigateToPlayedDetails(userData.getUsername());
        imsPlayerDetailsPage.addBonus(bonusType, AMOUNT);
        WebDriverUtils.refreshPage();
        bonusHistoryPage.clickOnBonus(bonusType);
        assertEquals(userData.getCurrency(), bonusHistoryPage.getCurrentBalanceCurrency(bonusType), "Current balance with 3 symbol currency");
    }

    /*Bonus Detail balance check bonus list scrolling*/
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

    private static UserData getBonusUser(){
        UserData userData = DataContainer.getUserData().getRandomUserData();
        userData.setUsername("bonusUser");
        return userData;
    }

    /**
     * [BONUS_DETAIL_BALANCE] REPLACER END
     * */
 }
