import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.testng.annotations.Test;
import pageObjects.replacers.PromotionalCodeReplacerPage;
import pageObjects.replacers.ValidPromotionalCodePopup;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;
import utils.validation.ValidationUtils;

/**
 * Created by serhiist on 3/11/2015.
 */
public class ReplacersTest extends AbstractTest {
    /**
     * PROMOTIONAL CODE REPLACER START
     * */

    /*Promotional code is a replaser*/
     @Test
    public static void isPomotionalCodeReplaserDisplayed(){
        try {
            PromotionalCodeReplacerPage promotionalCodeReplacerPage = (PromotionalCodeReplacerPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.promotional_code_replacer);
        }catch (Exception e){
            skipTest();
        }
    }

    /*1. Bonus is not given if promotional code is blank*/
    @Test
    public static void bonusIsNotGivenToPlayerIfPromotionalCodeIsBlank(){
        PromotionalCodeReplacerPage promotionalCodeReplacerPage = (PromotionalCodeReplacerPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.promotional_code_replacer);
        Float balanceAmount = Float.valueOf(promotionalCodeReplacerPage.getBalanceAmount());
        promotionalCodeReplacerPage.clearAndLeaveEmptyPromocodeField();
        assertEquals(PromotionalCodeReplacerPage.EMPTY_PROMOCODE_FIELD_TOOLTIP_TXT, promotionalCodeReplacerPage.getPromotionalCodeTooltipText(), "Wrong tooltip for promotional code field displayed. ");
        promotionalCodeReplacerPage.assertPromotionalCodeTooltipStatus(ValidationUtils.STATUS_FAILED, "empty");
        assertTrue(Float.compare(balanceAmount, Float.valueOf(promotionalCodeReplacerPage.getBalanceAmount())) == 0, "Balance changed for empty promotional code");
    }

    /*2. Bonus is not given if promotional code is invalid*/
    @Test
    public static void bonusIsNotGivenToPlayerIfPromotionalCodeIsInvalid(){
        PromotionalCodeReplacerPage promotionalCodeReplacerPage = (PromotionalCodeReplacerPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.promotional_code_replacer);
        Float balanceAmount = Float.valueOf(promotionalCodeReplacerPage.getBalanceAmount());
        promotionalCodeReplacerPage.clearFieldAndInputNotValidPromocode();
        promotionalCodeReplacerPage.assertPromotionalCodeTooltipStatus(ValidationUtils.STATUS_NONE, "invalid promocode");
        assertEquals(PromotionalCodeReplacerPage.NO_BONUS_GIVEN_ALERT_TXT, promotionalCodeReplacerPage.getAlertMessageText(), "Wrong alert message for invalid promocode displayed. ");
        assertTrue(Float.compare(balanceAmount, Float.valueOf(promotionalCodeReplacerPage.getBalanceAmount())) == 0, "Balance changed for invalid promotional code");
    }

    /*3. Bonus is given if promotional code is valid*/
    @Test
    public static void bonusIsGivenToPlayerIfPromotionalCodeIsValid(){
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
}
