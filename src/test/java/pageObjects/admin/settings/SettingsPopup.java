package pageObjects.admin.settings;

import org.apache.commons.lang3.ArrayUtils;
import pageObjects.core.AbstractLiferayPopup;
import utils.WebDriverUtils;

public class SettingsPopup extends AbstractLiferayPopup {

    private final static long   TIMEOUT =           60;
    public  final static String MENU_XP =           ROOT_XP + "//*[@id='orgSettingsMenu']";
    private final static String MENU_ITEM_XP =      MENU_XP + "/ul/li/a[contains(@data-params,'" + PLACEHOLDER+ "')]";
    private final static String SITE_TAB_XP =       MENU_ITEM_XP.replace(PLACEHOLDER, "site");
    private final static String HEADER_TAB_XP =     MENU_ITEM_XP.replace(PLACEHOLDER, "header");
    private final static String BUTTON_SAVE_XP =     "//button[@class='orgSettingsLink']";
    private final static String MESSAGE_SAVED_XP =   "//*[@class='portlet-msg-success']";


    public SettingsPopup(){
        super(new String[]{MENU_XP});
    }

    public SettingsPopup(String[] clickableBys){
        super(ArrayUtils.addAll(clickableBys, new String[]{MENU_XP}));
    }


    public SiteConfigurationPopup openSiteConfiguration(){
        WebDriverUtils.click(SITE_TAB_XP);
        WebDriverUtils.waitForElement(SiteConfigurationPopup.TITLE_XP, TIMEOUT);
        return new SiteConfigurationPopup();
    }

    protected void saveSettings(){
        WebDriverUtils.click(BUTTON_SAVE_XP);
        WebDriverUtils.waitForElement(MESSAGE_SAVED_XP, TIMEOUT);
    }
}
