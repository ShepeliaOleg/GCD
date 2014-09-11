package pageObjects.gamesPortlet;

import pageObjects.core.AbstractPopup;
import utils.WebDriverUtils;

public class StartGamePopup extends AbstractPopup{

    private static final String BUTTON_PLAY_REAL_XP=			ROOT_XP + "//*[contains(@class, 'popup-modal__button__play')]";
    private static final String BUTTON_PLAY_DEMO_XP=			ROOT_XP + "//*[contains(@class, 'popup-modal__button__demo')]";

    public StartGamePopup(){
        super();
    }

    public void clickDemo(){
        WebDriverUtils.click(BUTTON_PLAY_DEMO_XP);
    }

    public void clickReal(){
        WebDriverUtils.click(BUTTON_PLAY_REAL_XP);
    }

    public boolean isDemoPresent(){
        boolean result = WebDriverUtils.isVisible(BUTTON_PLAY_DEMO_XP, 0);
        clickOffPopup();
        return result;
    }

    public boolean isRealPresent(){
        boolean result = WebDriverUtils.isVisible(BUTTON_PLAY_REAL_XP, 0);
        clickOffPopup();
        return result;
    }
}