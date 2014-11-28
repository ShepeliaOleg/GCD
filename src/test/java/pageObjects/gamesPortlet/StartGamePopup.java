package pageObjects.gamesPortlet;

import pageObjects.core.AbstractPortalPopup;
import utils.WebDriverUtils;

public class StartGamePopup extends AbstractPortalPopup{

    private static final String BUTTON_PLAY_REAL_XP=			ROOT_XP + "//*[contains(@class, 'popup-modal__button__play')]";
    private static final String BUTTON_PLAY_DEMO_XP=			ROOT_XP + "//*[contains(@class, 'popup-modal__button__demo')]";
    private static final String BUTTON_FAVOURITE_XP =           ROOT_XP + "//*[contains(@class, 'fn-favorites')]";
    private static final String BUTTON_FAVOURITE_ACTIVE_XP =           BUTTON_FAVOURITE_XP + "[contains(@class, 'active')]";

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

    private void clickFavourite() {
        WebDriverUtils.click(BUTTON_FAVOURITE_XP);
    }

    public void favourite(){
        clickFavourite();
        new AddedToFavouritesNotification();
    }

    public void unFavourite(){
        clickFavourite();
        new RemovedFromFavouritesNotification();
    }

    public boolean isFavourite() {
        return WebDriverUtils.isVisible(BUTTON_FAVOURITE_ACTIVE_XP, 1);
    }
}