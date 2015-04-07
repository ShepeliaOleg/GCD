package pageObjects.replacers;

import pageObjects.core.AbstractPortalPage;
import utils.WebDriverUtils;
import utils.validation.ValidationUtils;

/**
 * Created by serhiist on 3/11/2015.
 */
public class PromotionalCodeReplacerPage extends AbstractPortalPage {
    private static final String ROOT_XP = "//*[contains(@class, 'form fn-promocode')]";
    public static final String INPUT_PROMOCODE_XP = "//*[@name='promocode']";
    private static final String PROMOTIONAL_CODE_TOOLTIP = "promocode";
    private static final String BUTTON_GO_XP = "//*[@class='btn']";
    private static final String ALERT_XP = "//*[@class='info__content']";
    private static final String NOT_VALID_PROMOTIONAL_CODE = "notValidPromocode";
    private static final String VALID_PROMOTIONAL_CODE = "galafree";
    public static final String NO_BONUS_GIVEN_ALERT_TXT = "No bonuses given";
    public static final String EMPTY_PROMOCODE_FIELD_TOOLTIP_TXT = "This field is required";

    public static final String INPUT_PROMOTIONAL_TK = "playtech.mobile.system.promocode.placeholder";
    public static final String PROMOTIONAL_CODE_TOOLTIP_TK = "playtech.mobile.system.tooltip.required";
    public static final String BUTTON_GO_TK = "playtech.mobile.system.promocode.submit";
    public static final String ALERT_TK = "playtech.mobile.system.promocode.info.noBonus";




    public PromotionalCodeReplacerPage() {
        super(new String[]{ROOT_XP});
    }

    private void clearAndInputTextToPromocodeField(String text){
        WebDriverUtils.clearAndInputTextToField(INPUT_PROMOCODE_XP, text);
    }

    public void clearAndLeaveEmptyPromocodeField(){
        clearAndInputTextToPromocodeField("");
        clickGoButton();
    }

    public void clearFieldAndInputNotValidPromocode(){
        clearAndInputTextToPromocodeField(NOT_VALID_PROMOTIONAL_CODE);
        clickGoButton();
    }

    public ValidPromotionalCodePopup clearFieldAndInputValidPromocode(){
        clearAndInputTextToPromocodeField(VALID_PROMOTIONAL_CODE);
        WebDriverUtils.waitFor();
        clickGoButton();
        WebDriverUtils.waitFor();
        return new ValidPromotionalCodePopup();
    }

    public void clickGoButton(){
        WebDriverUtils.click(BUTTON_GO_XP);
    }

    public String getAlertMessageText(){
        WebDriverUtils.waitForElement(ALERT_XP);
        return WebDriverUtils.getElementText(ALERT_XP);
    }

    public String getPromotionalCodeTooltipText(){
        return ValidationUtils.getTooltipText(PROMOTIONAL_CODE_TOOLTIP);
    }

    public void assertPromotionalCodeTooltipStatus(String expectedStatus, String value){
        ValidationUtils.assertTooltipStatus(PROMOTIONAL_CODE_TOOLTIP, expectedStatus, value);
    }

    public String getPromotionalCodeFieldPlaceholder(){
        return WebDriverUtils.getAttribute(INPUT_PROMOCODE_XP, "placeholder");
    }

    public String getButtonGoText() {
        return WebDriverUtils.getElementText(BUTTON_GO_XP);
    }
}
