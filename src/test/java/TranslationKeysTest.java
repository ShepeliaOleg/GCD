import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.testng.annotations.*;
import pageObjects.replacers.PromotionalCodeReplacerPage;
import pageObjects.replacers.ValidPromotionalCodePopup;
import utils.NavigationUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

/**
 * Created by serhiist on 3/12/2015.
 */
public class TranslationKeysTest extends AbstractTest{
    private static void showTranslationKeys(String page){
        WebDriverUtils.navigateToInternalURL(page + "?showTranslationKeys=1");
    }

    @AfterMethod
    public void hideTranslationKeys(){
        NavigationUtils.navigateToPage(ConfiguredPages.home);
        WebDriverUtils.navigateToInternalURL("?showTranslationKeys=0");
    }

    /*All element are translation keys*/
    @Test(groups = {"regression"})
    public static void promotionalCode(){
        PromotionalCodeReplacerPage promotionalCodeReplacerPage = (PromotionalCodeReplacerPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.promotional_code_replacer);
        showTranslationKeys(String.valueOf(ConfiguredPages.promotional_code_replacer));
        WebDriverUtils.waitForElement(PromotionalCodeReplacerPage.INPUT_PROMOCODE_XP);
        assertEquals(PromotionalCodeReplacerPage.INPUT_PROMOTIONAL_TK, promotionalCodeReplacerPage.getPromotionalCodeFieldPlaceholder(), "No translation key for promocode field placeholder. ");
        assertEquals(PromotionalCodeReplacerPage.BUTTON_GO_TK, promotionalCodeReplacerPage.getButtonGoText(), "No translation key for button 'Go'. ");
        promotionalCodeReplacerPage.clearAndLeaveEmptyPromocodeField();
        assertEquals(PromotionalCodeReplacerPage.PROMOTIONAL_CODE_TOOLTIP_TK, promotionalCodeReplacerPage.getPromotionalCodeTooltipText(), "No translation key for tooltip of promotional code field. ");
        promotionalCodeReplacerPage.clearFieldAndInputNotValidPromocode();
        assertEquals(PromotionalCodeReplacerPage.ALERT_TK, promotionalCodeReplacerPage.getAlertMessageText(), "No translation key for alert message for invalid promocode. ");
        ValidPromotionalCodePopup validPromotionalCodePopup = promotionalCodeReplacerPage.clearFieldAndInputValidPromocode();
        assertEquals(ValidPromotionalCodePopup.TITLE_TK, validPromotionalCodePopup.getTitleText(), "No translation key for title in congratulations popup. ");
    }


}
