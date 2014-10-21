package pageObjects.gamesPortlet;

import pageObjects.core.AbstractPage;
import utils.WebDriverUtils;

public class GameIncorrectId extends AbstractPage {

    private static final String ROOT_XP =           "//*[@id='pnlError']";
    private static final String LABEL_XP=           "//*[@class='cp-lblError']";
    private static final String BUTTON_OK_XP=       "//*[@class='cp-pnlErrorButton']/button";

    public GameIncorrectId(){
        super(new String[]{ROOT_XP, LABEL_XP, BUTTON_OK_XP});
    }

}