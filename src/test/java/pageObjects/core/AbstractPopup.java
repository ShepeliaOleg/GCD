package pageObjects.core;

import org.apache.commons.lang3.ArrayUtils;
import utils.WebDriverUtils;
import utils.core.DataContainer;

public class AbstractPopup extends AbstractPageObject{

	public final static String ROOT_XP=			    "//*[contains(@class, 'popup popup-modal') and not(contains(@class, 'hide-popup'))]";
	public final static String BUTTON_CLOSE_XP =	ROOT_XP + "//*[contains(@class, 'fn-close')]";
    public final static String BUTTON_DECLINE_XP =	ROOT_XP + "//*[contains(@class, 'fn-decline')]";
    public final static String BUTTON_ACCEPT_XP =   ROOT_XP + "//*[contains(@class, 'fn-accept')]";
    public final static String BUTTON_NEXT_XP =     ROOT_XP + "//*[contains(@class, 'fn-next')]";
    public final static String BUTTON_PREVIOUS_XP = ROOT_XP + "//*[contains(@class, 'fn-prev')]";
    private final static String OFF_POPUP_XP =          "//*[contains(@class, 'fn-footer-wrapper')]";
    protected final static String TITLE_XP =        "//*[@class='popup-modal__title']";
    private final static String CONTENT_XP =        "//*[@class='popup-modal__content']";

    private final static int OFFSET =               50;

	public AbstractPopup(){
		this(null);
	}

	public AbstractPopup(String[] clickableBys){
		this(clickableBys, new String[]{});
	}

	public AbstractPopup(String[] clickableBys, String[] invisibleBys){
        this(clickableBys, invisibleBys, ROOT_XP);
	}

    public AbstractPopup(String[] clickableBys, String[] invisibleBys, String rootXp) {
        super(ArrayUtils.addAll(clickableBys, new String[]{rootXp}), invisibleBys);
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
        if(DataContainer.getDriverData().getBrowser().equals("safari")){
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
        return WebDriverUtils.getAttribute(ROOT_XP, "style");
    }
}


