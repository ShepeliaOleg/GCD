import enums.ConfiguredPages;
import enums.Page;
import enums.PlayerCondition;
import enums.SettingsTab;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.admin.settings.SiteConfigurationPopup;
import pageObjects.core.AbstractPortalPage;
import pageObjects.core.AbstractPortalPopup;
import pageObjects.pageInPopup.PageInPopupPage;
import pageObjects.pageInPopup.PageInPopupPopup;
import pageObjects.registration.AdultContentPopup;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;

public class GeneralTest extends AbstractTest {

    private static final String POPUP_SCROLL_WIDTH = "40%";
    private static final String POPUP_A_WIDTH =      "50%";
    private static final String POPUP_B_WIDTH =      "60%";
    private static final String POPUP_CHILD_WIDTH =  "70%";
    private static final String POPUP_SCROLL_HEIGHT ="80%";

    /*Session is saved after visiting external resource*/
    @Test(groups = {"regression"})
    public void sessionIsSavedAfterVisitingExternalResource(){
        NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.home);
        WebDriverUtils.navigateToURL("http://www.google.com/");
        WebDriverUtils.waitForPageToLoad();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(ConfiguredPages.home);
        assertTrue(homePage.isLoggedIn(), "Is still logged in");
    }


    /*B-12855 Show any page as popup*/
    /*3*/
    @Test(groups = {"regression"})
    public void openDirectlyByUrl(){
        PageInPopupPopup pageInPopupPopup = (PageInPopupPopup) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.page_in_popup_a, Page.pageInPopup);
        assertPageInPopup(ConfiguredPages.page_in_popup_a, pageInPopupPopup, POPUP_A_WIDTH, ConfiguredPages.home);
        PageInPopupPage pageInPopupPage = (PageInPopupPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.page_in_popup_disabled);
        pageInPopupPopup = (PageInPopupPopup) pageInPopupPage.clickButton();
        assertPageInPopup(ConfiguredPages.page_in_popup_a, pageInPopupPopup, POPUP_A_WIDTH, ConfiguredPages.page_in_popup_disabled);
    }

    /*4 current*/
    @Test(groups = {"regression"})
    public void currentPageOnBackground(){
        PageInPopupPage pageInPopupPage = (PageInPopupPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.page_in_popup_disabled);
        PageInPopupPopup pageInPopupPopup = (PageInPopupPopup) pageInPopupPage.clickButton();
        assertPageInPopup(ConfiguredPages.page_in_popup_a, pageInPopupPopup, POPUP_A_WIDTH, ConfiguredPages.page_in_popup_disabled);
        AdultContentPopup adultContentPopup = (AdultContentPopup) pageInPopupPopup.clickButton();
        assertPageInPopup(ConfiguredPages.page_in_popup_b, adultContentPopup, POPUP_B_WIDTH, ConfiguredPages.page_in_popup_disabled);
    }

    /*5 root*/
    @Test(groups = {"regression"})
    public void rootPageOnBackground(){
        PageInPopupPopup pageInPopupPopup = (PageInPopupPopup) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.page_in_popup_a, Page.pageInPopup);
        assertPageInPopup(ConfiguredPages.page_in_popup_a, pageInPopupPopup, POPUP_A_WIDTH, ConfiguredPages.home);
        AdultContentPopup adultContentPopup = (AdultContentPopup) pageInPopupPopup.clickButton();
        assertPageInPopup(ConfiguredPages.page_in_popup_b, adultContentPopup, POPUP_B_WIDTH, ConfiguredPages.home);
    }

    /*6 parent*/
    @Test(groups = {"regression"})
    public void parentPageOnBackground(){
        skipTestWithIssues("D-15797");
        PageInPopupPopup pageInPopupPopup = (PageInPopupPopup) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.page_in_popup_child, Page.pageInPopup);
        assertPageInPopup(ConfiguredPages.page_in_popup_child, pageInPopupPopup, POPUP_CHILD_WIDTH, ConfiguredPages.page_in_popup_parent);
        PageInPopupPage pageInPopupPage = (PageInPopupPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.page_in_popup_link_to_child);
        pageInPopupPopup = (PageInPopupPopup) pageInPopupPage.clickButton();
        assertPageInPopup(ConfiguredPages.page_in_popup_child, pageInPopupPopup, POPUP_CHILD_WIDTH, ConfiguredPages.page_in_popup_link_to_child);
    }

//    /*7 scroll*/
//    @Test(groups = {"regression"})
//    public void longPageInPopupScroll(){
//        PageInPopupPopup pageInPopupPopup = (PageInPopupPopup) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.page_in_popup_scroll);
//        assertPageInPopup(ConfiguredPages.page_in_popup_scroll, pageInPopupPopup, POPUP_SCROLL_WIDTH, ConfiguredPages.home);
//
//    }

    private void assertPageInPopup(ConfiguredPages popupPage, AbstractPortalPopup popup, String popupWidth, ConfiguredPages backgroundPage) {
        assertEquals(popup.getStyle(), ("min-width: " + popupWidth + ";"), "Popup displayed with width " + popupWidth);
        NavigationUtils.getConfiguredPageObject(backgroundPage);
        assertUrl(popupPage.toString(), "Page in popup url.");
    }

    /*B-12847 404 custom page*/
    /*1*/
    @Test(groups = {"admin"})
    public void redirect404toRoot() {
        SiteConfigurationPopup siteConfigurationPopup = (SiteConfigurationPopup) PortalUtils.openSettings(SettingsTab.siteConfiguration);
        siteConfigurationPopup.setUseCdnState(false);
        siteConfigurationPopup.setDirectToRoot();
        NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        WebDriverUtils.navigateToInternalURL("not_existing_page");
        new HomePage();
    }

    /*2, 7*/
    @Test(groups = {"admin"})
    public void redirect404toPageLanguages(){
        SiteConfigurationPopup siteConfigurationPopup = (SiteConfigurationPopup) PortalUtils.openSettings(SettingsTab.siteConfiguration);
        siteConfigurationPopup.setUseCdnState(false);
        siteConfigurationPopup.setDirectToPage("/24/7-support");
        NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        WebDriverUtils.navigateToInternalURL("not_existing_page");
        AbstractPortalPage abstractPortalPage = new AbstractPortalPage();
        String defaultLanguageCode = DataContainer.getDefaults().getDefaultLanguage();
        assertEquals(DataContainer.getDriverData().getCurrentUrl() + "24/7-support", WebDriverUtils.getCurrentUrl(), "Redirect to internal URL for default language.");
        for (String languageCode : DataContainer.getDefaults().getLanguageCodesList()) {
            String shortLanguageCode = DataContainer.getDefaults().getLanguageUrlByLanguageCode(languageCode);
            abstractPortalPage.setLanguage(languageCode);
            WebDriverUtils.navigateToInternalURL("not_existing_page");
            WebDriverUtils.waitFor();
            if (!languageCode.equals(defaultLanguageCode)) {
                assertEquals(DataContainer.getDriverData().getCurrentUrl() + shortLanguageCode + "/24/7-support", WebDriverUtils.getCurrentUrl(), "Redirect to internal URL for " + languageCode + " language.");
            }

        }
    }

    /*3*/
    @Test(groups = {"admin"})
    public void redirect404toUrl() {
        final String externalUrl = "http://www.wikipedia.org/";
        SiteConfigurationPopup siteConfigurationPopup = (SiteConfigurationPopup) PortalUtils.openSettings(SettingsTab.siteConfiguration);
        siteConfigurationPopup.setDirectToUrl(externalUrl);
        NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        WebDriverUtils.navigateToInternalURL("not_existing_page");
        WebDriverUtils.waitForPageToLoad();
        assertEquals(externalUrl, WebDriverUtils.getCurrentUrl(), "Redirect to external URL.");
    }

    /*4*/
    @Test(groups = {"public"})
    public void redirect404toPageWithUseCdn() {
        SiteConfigurationPopup siteConfigurationPopup = (SiteConfigurationPopup) PortalUtils.openSettings(SettingsTab.siteConfiguration);
        siteConfigurationPopup.setUseCdnState(true);
        siteConfigurationPopup.setDirectToPage("/24/7-support");
        NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        WebDriverUtils.navigateToInternalURL("not_existing_page");
        WebDriverUtils.waitForPageToLoad();
        assertEquals(DataContainer.getDriverData().getCdnNode() + "24/7-support", WebDriverUtils.getCurrentUrl(), "Redirect to external URL.");
    }

}