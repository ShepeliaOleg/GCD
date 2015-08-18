package pageObjects.core;

import org.apache.commons.lang3.ArrayUtils;
import utils.Locator;
import utils.WebDriverUtils;
import utils.core.AbstractTest;
import utils.core.WebDriverFactory;

public class AbstractPortalPopup extends AbstractPopup{

    public static final String  PORTLET_ERROR_XP =   "//*[contains(@class,'error') or contains(@class, 'info__content')]";
	//public final static Locator BUTTON_CLOSE_XP =	 new Locator("fn-close",   ROOT_XP + "//*[contains(@class, 'button_type_cancel')]", ".popup-modal__button.fn-close");
    public final static Locator BUTTON_CLOSE_XP =	 new Locator("fn-close", "//*[contains(@class, 'fn-close close-popup')]", ".popup-modal__button.fn-close");
    public final static Locator BUTTON_DECLINE_XP =	 new Locator("fn-decline", ROOT_XP + "//*[contains(@class, 'fn-decline')]",         null);
    public final static Locator BUTTON_ACCEPT_XP =   new Locator("fn-accept",  ROOT_XP + "//*[contains(@class, 'fn-accept')]",          null);
    public final static Locator BUTTON_NEXT_XP =     new Locator("fn-next",    TOP_ROOT_XP + "//*[contains(@class, 'fn-next')]",        null);
    public final static Locator BUTTON_PREVIOUS_XP = new Locator("fn-prev",    TOP_ROOT_XP + "//*[contains(@class, 'fn-prev')]",        null);
    public final static String  TITLE_XP =           "//*[@class='popup-modal__title']";
    public final static String  CONTENT_XP =         "//*[@class='popup-modal__content']";

	public AbstractPortalPopup(){
		this(null);
	}

	public AbstractPortalPopup(String[] clickableBys){
		this(clickableBys, new String[]{});
	}

	public AbstractPortalPopup(String[] clickableBys, String[] invisibleBys){
        this(clickableBys, invisibleBys, ROOT_XP);
	}

    public AbstractPortalPopup(String[] clickableBys, String[] invisibleBys, String rootXp) {
        super(WebDriverFactory.getPortalDriver(), ArrayUtils.addAll(clickableBys, new String[]{rootXp}), invisibleBys);
        WebDriverUtils.waitFor();
    }

    private void clickClose(){
		WebDriverUtils.click(BUTTON_CLOSE_XP);
        WebDriverUtils.waitFor();
	}

    public void clickDecline(){
        WebDriverUtils.click(BUTTON_DECLINE_XP);
        WebDriverUtils.waitFor();
    }

    public void clickAccept(){
        WebDriverUtils.click(BUTTON_ACCEPT_XP);
        WebDriverUtils.waitFor();
    }

    public void closePopup(){
        if(WebDriverUtils.isVisible(BUTTON_CLOSE_XP.getXpath(), 0)){
            clickClose();
        }else {
            clickOffPopup();
        }
        WebDriverUtils.waitFor();
    }

    public void clickOffPopup() {
        WebDriverUtils.clickWithOffset(ROOT_XP);
        WebDriverUtils.waitFor();
    }

    public String getTranslationText() {
        return getContentText();
    }

    protected String getContentText() {
        return WebDriverUtils.getElementText(CONTENT_XP);
    }

    protected String getTitleText() {
        return WebDriverUtils.getElementText(TITLE_XP);
    }

    public void clickNext() {
        WebDriverUtils.click(BUTTON_NEXT_XP);
        WebDriverUtils.waitFor();
    }

    public void clickPrevious() {
        WebDriverUtils.click(BUTTON_PREVIOUS_XP);
        WebDriverUtils.waitFor();
    }

    public String getStyle() {
        return WebDriverUtils.getAttribute(TOP_ROOT_XP, "style");
    }

    public boolean isPortletErrorVisible(){
        return WebDriverUtils.isVisible(WebDriverFactory.getPortalDriver(), PORTLET_ERROR_XP, 2);
    }

    public String getPortletErrorMessage() {
        if(isPortletErrorVisible()){
            return WebDriverUtils.getElementText(WebDriverFactory.getPortalDriver(), PORTLET_ERROR_XP);
        }else {
            AbstractTest.failTest("Expected error message, but it did not appear");
            return null;
        }
    }
}


