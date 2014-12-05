package pageObjects.admin.settings;

import utils.WebDriverUtils;

public class SiteConfigurationPopup extends SettingsPopup {

    public  final static String TITLE_XP =                   "//*[@id='orgSettingsModuleConf']//strong";
    private final static String CHECKBOX_404_USE_CDN_XP =   "//*[contains(@id,'redirect') and contains(@type,'checkbox')]";
    private final static String RADIO_404_ROOT_XP =         "//*[contains(@id,'root') and contains(@type,'radio')]";
    private final static String RADIO_404_PAGE_XP =         "//*[contains(@id,'page') and contains(@type,'radio')]";
    private final static String DROPDOWN_404_PAGE_XP =      "//select[contains(@id,'page')]";
    private final static String RADIO_404_URL_XP =          "//*[contains(@id,'url')  and contains(@type,'radio')]";
    private final static String INPUT_404_URL_XP =          "//*[contains(@id,'url')  and contains(@type,'text')]";


    public SiteConfigurationPopup(){
        super(new String[]{CHECKBOX_404_USE_CDN_XP});
    }

    public void setUseCdnState(boolean desiredState) {
        WebDriverUtils.setCheckBoxState(CHECKBOX_404_USE_CDN_XP, desiredState);
    }

    private void setDirectToRootRadio() {
        WebDriverUtils.click(RADIO_404_ROOT_XP);
    }

    private void setDirectToPageRadio() {
        WebDriverUtils.click(RADIO_404_PAGE_XP);
    }

    private void setDirectToPageValue(String page) {
        WebDriverUtils.setDropdownOptionByValue(DROPDOWN_404_PAGE_XP, page);
    }

    private void setDirectToUrlRadio() {
        WebDriverUtils.click(RADIO_404_URL_XP);
    }

    private void setDirectToUrlValue(String url) {
        WebDriverUtils.clearAndInputTextToField(INPUT_404_URL_XP ,url);
    }

    public void setDirectToRoot() {
        setDirectToRootRadio();
        saveSettings();
    }

    public void setDirectToPage(String page) {
        setDirectToPageRadio();
        setDirectToPageValue(page);
        saveSettings();
    }

    public void setDirectToUrl(String url) {
        setDirectToUrlRadio();
        setDirectToUrlValue(url);
        saveSettings();
    }

}
