package pageObjects.core;

import org.apache.commons.lang3.ArrayUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;
import utils.core.WebDriverFactory;

public class AbstractPortalPopup extends AbstractPopup{

    public static final String PORTLET_ERROR_XP= "//*[contains(@class,'error') or contains(@class, 'info__content')]";
	public final static String BUTTON_CLOSE_XP =	ROOT_XP + "//*[contains(@class, 'fn-close')]";
    public final static String BUTTON_DECLINE_XP =	ROOT_XP + "//*[contains(@class, 'fn-decline')]";
    public final static String BUTTON_ACCEPT_XP =   ROOT_XP + "//*[contains(@class, 'fn-accept')]";
    public final static String BUTTON_NEXT_XP =     TOP_ROOT_XP + "//*[contains(@class, 'fn-next')]";
    public final static String BUTTON_PREVIOUS_XP = TOP_ROOT_XP + "//*[contains(@class, 'fn-prev')]";
    private final static String OFF_POPUP_XP =          "//*[contains(@class, 'fn-footer-wrapper')]";
    protected final static String TITLE_XP =        "//*[@class='popup-modal__title']";
    private final static String CONTENT_XP =        "//*[@class='popup-modal__content']";

    private final static int OFFSET =               50;

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
        if(WebDriverUtils.isVisible(BUTTON_CLOSE_XP, 0)){
            clickClose();
        }else {
            clickOffPopup();
        }
        WebDriverUtils.waitFor();
    }

    public void clickOffPopup(){
        if(DataContainer.getDriverData().getBrowser().equals("safari")&&DataContainer.getDriverData().getOs().equals("mac")){
            WebDriverUtils.click(OFF_POPUP_XP);
        }else {
            WebDriverUtils.click(ROOT_XP, OFFSET);
        }
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


