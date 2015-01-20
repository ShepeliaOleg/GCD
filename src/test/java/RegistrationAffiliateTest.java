import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.registration.RegistrationPage;
import springConstructors.AffiliateData;
import springConstructors.UserData;
import utils.IMSUtils;
import utils.NavigationUtils;
import utils.cookie.AffiliateCookie;
import utils.core.AbstractTest;
import utils.core.DataContainer;

public class RegistrationAffiliateTest extends AbstractTest {

    @Autowired
    @Qualifier("affiliate")
    private AffiliateData affiliateData;

    /*B-10271*/
    /*1*/
    @Test(groups = {"registration", "regression", "affiliate", "cookie"})
    public void affiliateCookieSingleCreferrer() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        AffiliateCookie affiliateCookie = new AffiliateCookie(affiliateDataSingle);
        affiliateCookie.add();
        registrationPage.registerUser(userData);
        IMSUtils.validateAffiliate(userData.getUsername(), affiliateDataSingle);
    }

    /*2*/
    @Test(groups = {"registration", "regression", "affiliate", "cookie"})
    public void affiliateCookieAdvertiserNotExists() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        affiliateDataSingle.setAdvertiser("notExists");
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        AffiliateCookie affiliateCookie = new AffiliateCookie(affiliateDataSingle);
        affiliateCookie.add();
        registrationPage.registerUser(userData);
        affiliateDataSingle.setAdvertiser(affiliateData.getDefaultAdvertiser());
        IMSUtils.validateAffiliate(userData.getUsername(), affiliateDataSingle);
    }

    /*3*/
    @Test(groups = {"registration", "regression", "affiliate", "cookie"})
    public void affiliateCookieCreferrerNotExists() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        affiliateDataSingle.setCreferrer("notExists:123");
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        AffiliateCookie affiliateCookie = new AffiliateCookie(affiliateDataSingle);
        affiliateCookie.add();
        registrationPage.registerUser(userData);
        IMSUtils.validateAffiliate(userData.getUsername(), affiliateDataSingle, false);
    }

    /*4*/
    @Test(groups = {"registration", "regression", "affiliate", "cookie"})
    public void affiliateCookieMultipleCreferrer() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        AffiliateData affiliateDataMultiple = affiliateData.getAffiliateDataMultiple();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        AffiliateCookie affiliateCookie = new AffiliateCookie(affiliateDataMultiple);
        affiliateCookie.add();
        registrationPage.registerUser(userData);
        IMSUtils.validateAffiliate(userData.getUsername(), affiliateDataMultiple);
    }

    /*5*/
    @Test(groups = {"registration", "regression", "affiliate", "cookie"})
    public void affiliateCookieNoReferrerUrl() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        affiliateDataSingle.setUrl("");
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        AffiliateCookie affiliateCookie = new AffiliateCookie(affiliateDataSingle);
        affiliateCookie.add();
        registrationPage.registerUser(userData);
        IMSUtils.validateAffiliate(userData.getUsername(), affiliateDataSingle);
    }

    /*6*/
    @Test(groups = {"registration", "regression", "affiliate", "cookie"})
    public void affiliateCookieBannerIsRegexp() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        affiliateDataSingle.setBanner("*");
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        AffiliateCookie affiliateCookie = new AffiliateCookie(affiliateDataSingle);
        affiliateCookie.add();
        registrationPage.registerUser(userData);
        IMSUtils.validateAffiliate(userData.getUsername(), affiliateDataSingle);
    }

    /*7*/
    @Test(groups = {"registration", "regression", "affiliate", "cookie"})
    public void affiliateCookieNoBannerNoProfileNoCreferrer() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        affiliateDataSingle.setBanner("");
        affiliateDataSingle.setProfile("");
        affiliateDataSingle.setCreferrer("");
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        AffiliateCookie affiliateCookie = new AffiliateCookie(affiliateDataSingle);
        affiliateCookie.add();
        registrationPage.registerUser(userData);
        IMSUtils.validateAffiliate(userData.getUsername(), affiliateDataSingle);
    }

    /*8*/
    @Test(groups = {"registration", "regression", "affiliate", "cookie"})
    public void affiliateCookieNoAdvertiserNoReferrerUrl() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        affiliateDataSingle.setAdvertiser("");
        affiliateDataSingle.setUrl("");
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        AffiliateCookie affiliateCookie = new AffiliateCookie(affiliateDataSingle);
        affiliateCookie.add();
        registrationPage.registerUser(userData);
        affiliateDataSingle.setAdvertiser(affiliateData.getDefaultAdvertiser());
        IMSUtils.validateAffiliate(userData.getUsername(), affiliateDataSingle);
    }

    /*9*/
    @Test(groups = {"registration", "regression", "affiliate", "cookie"})
    public void affiliateCookieThreeFirstParametersOnly() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        affiliateDataSingle.setUrl("");
        affiliateDataSingle.setCreferrer("");
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        AffiliateCookie affiliateCookie = new AffiliateCookie(affiliateDataSingle.getAdvertiser() + "," + affiliateDataSingle.getBanner() + "," + affiliateDataSingle.getProfile() + ",,");
        affiliateCookie.add();
        registrationPage.registerUser(userData);
        IMSUtils.validateAffiliate(userData.getUsername(), affiliateDataSingle);
    }

    /*10*/
    @Test(groups = {"registration", "regression", "affiliate", "cookie"})
    public void affiliateCookieIncorrectValueFormatNotEnoughCommas() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        AffiliateCookie affiliateCookie = new AffiliateCookie(affiliateDataSingle.getAdvertiser() + "," + affiliateDataSingle.getProfile() + "," + affiliateDataSingle.getCreferrerSingle());
        affiliateCookie.add();
        registrationPage.registerUser(userData);
        IMSUtils.validateNoAffiliate(userData.getUsername());
    }

    /*11*/
    @Test(groups = {"registration", "regression", "affiliate", "cookie"})
    public void affiliateCookieEmptyValue() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        AffiliateCookie affiliateCookie = new AffiliateCookie("");
        affiliateCookie.add();
        registrationPage.registerUser(userData);
        IMSUtils.validateNoAffiliate(userData.getUsername());
    }

    /*12*/
    @Test(groups = {"registration", "regression", "affiliate", "cookie"})
    public void affiliateCookieAfterCookie() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        AffiliateData affiliateDataCookie1 = affiliateData.getAffiliateDataSingle();
        affiliateDataCookie1.setAdvertiser("advertiser");
        affiliateDataCookie1.setBanner("banner");
        affiliateDataCookie1.setProfile("profile");
        affiliateDataCookie1.setUrl("url");
        affiliateDataCookie1.setCreferrer("creferrer");
        AffiliateData affiliateDataCookie2 = affiliateData.getAffiliateDataSingle();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        AffiliateCookie affiliateCookie1 = new AffiliateCookie(affiliateDataCookie1);
        affiliateCookie1.add();
        AffiliateCookie affiliateCookie2 = new AffiliateCookie(affiliateDataCookie2);
        affiliateCookie2.add();
        registrationPage.registerUser(userData);
        IMSUtils.validateAffiliate(userData.getUsername(), affiliateDataCookie2);
    }

    /*13*/
    @Test(groups = {"registration", "regression", "affiliate", "url"})
    public void affiliateUrlSingleCreferrer() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToAffiliateURL(ConfiguredPages.register, affiliateDataSingle);
        registrationPage.registerUser(userData);
        IMSUtils.validateAffiliate(userData.getUsername(), affiliateDataSingle);
    }

    /*14*/
    @Test(groups = {"registration", "regression", "affiliate", "url"})
    public void affiliateUrlAdvertiserNotExists() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        affiliateDataSingle.setAdvertiser("notExists");
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToAffiliateURL(ConfiguredPages.register, affiliateDataSingle);
        registrationPage.registerUser(userData);
        affiliateDataSingle.setAdvertiser(affiliateData.getDefaultAdvertiser());
        IMSUtils.validateAffiliate(userData.getUsername(), affiliateDataSingle);
    }

    /*15*/
    @Test(groups = {"registration", "regression", "affiliate", "url"})
    public void affiliateUrlCreferrerNotExists() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        affiliateDataSingle.setCreferrer("notExists:123");
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToAffiliateURL(ConfiguredPages.register, affiliateDataSingle);
        registrationPage.registerUser(userData);
        IMSUtils.validateAffiliate(userData.getUsername(), affiliateDataSingle, false);
    }

    /*16*/
    @Test(groups = {"registration", "regression", "affiliate", "url"})
    public void affiliateUrlMultipleCreferrer() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        AffiliateData affiliateDataMultiple = affiliateData.getAffiliateDataMultiple();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToAffiliateURL(ConfiguredPages.register, affiliateDataMultiple);
        registrationPage.registerUser(userData);
        IMSUtils.validateAffiliate(userData.getUsername(), affiliateDataMultiple);
    }

    /*17*/
    @Test(groups = {"registration", "regression", "affiliate", "url"})
    public void affiliateUrl4Parameters() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        affiliateDataSingle.setUrl("");
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToAffiliateURL(ConfiguredPages.register, affiliateDataSingle);
        registrationPage.registerUser(userData);
        IMSUtils.validateAffiliate(userData.getUsername(), affiliateDataSingle);
    }

    /*18*/
    @Test(groups = {"registration", "regression", "affiliate", "url"})
    public void affiliateUrl3Parameters() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        affiliateDataSingle.setBanner("");
        affiliateDataSingle.setAdvertiser("");
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToAffiliateURL(ConfiguredPages.register, affiliateDataSingle);
        registrationPage.registerUser(userData);
        affiliateDataSingle.setAdvertiser(affiliateData.getDefaultAdvertiser());
        IMSUtils.validateAffiliate(userData.getUsername(), affiliateDataSingle);
    }

    /*19*/
    @Test(groups = {"registration", "regression", "affiliate", "url"})
    public void affiliateUrl2Parameters() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        affiliateDataSingle.setProfile("");
        affiliateDataSingle.setUrl("");
        affiliateDataSingle.setCreferrer("");
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToAffiliateURL(ConfiguredPages.register, affiliateDataSingle);
        registrationPage.registerUser(userData);
        IMSUtils.validateAffiliate(userData.getUsername(), affiliateDataSingle);
    }

    /*20*/
    @Test(groups = {"registration", "regression", "affiliate", "url"})
    public void affiliateUrl1Parameter() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        affiliateDataSingle.setAdvertiser("advertiser");
        affiliateDataSingle.setBanner("banner");
        affiliateDataSingle.setProfile("profile");
        affiliateDataSingle.setUrl("url");
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToAffiliateURL(ConfiguredPages.register, affiliateDataSingle);
        registrationPage.registerUser(userData);
        affiliateDataSingle.setAdvertiser(affiliateData.getDefaultAdvertiser());
        IMSUtils.validateAffiliate(userData.getUsername(), affiliateDataSingle);
    }

    /*21*/
    @Test(groups = {"registration", "regression", "affiliate", "url"})
    public void affiliateUrlAfterUrl() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        AffiliateData affiliateDataUrl1 = affiliateData.getAffiliateDataSingle();
        affiliateDataUrl1.setAdvertiser("advertiser");
        affiliateDataUrl1.setBanner("banner");
        affiliateDataUrl1.setProfile("profile");
        affiliateDataUrl1.setUrl("url");
        affiliateDataUrl1.setCreferrer("creferrer");
        AffiliateData affiliateDataUrl2 = affiliateData.getAffiliateDataSingle();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToAffiliateURL(ConfiguredPages.register, affiliateDataUrl1);
                         registrationPage = (RegistrationPage) NavigationUtils.navigateToAffiliateURL(ConfiguredPages.register, affiliateDataUrl2);
        registrationPage.registerUser(userData);
        IMSUtils.validateAffiliate(userData.getUsername(), affiliateDataUrl2);
    }

    /*22*/
    @Test(groups = {"registration", "regression", "affiliate", "cookie", "url"})
    public void affiliateCookieAndUrl() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        AffiliateData affiliateDataUrl = affiliateData.getAffiliateDataSingle();
        AffiliateData affiliateDataCookie = affiliateData.getAffiliateDataSingle();
        affiliateDataCookie.setAdvertiser("advertiser");
        affiliateDataCookie.setBanner("banner");
        affiliateDataCookie.setProfile("profile");
        affiliateDataCookie.setUrl("url");
        affiliateDataCookie.setCreferrer("name:value");
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        AffiliateCookie affiliateCookie = new AffiliateCookie(affiliateDataCookie);
        affiliateCookie.add();
        NavigationUtils.refreshPage();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToAffiliateURL(ConfiguredPages.register, affiliateDataUrl);
        registrationPage.registerUser(userData);
        IMSUtils.validateAffiliate(userData.getUsername(), affiliateDataUrl);
    }


    /*B-11324 Creferrer*/
    /*2*/
    @Test(groups = {"registration", "regression", "affiliate", "creferrer"})
    public void affiliateCreferrerRegistrationPreference() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        String creferrerRegistration = affiliateData.getCreferrerRegistrationPortletProperty();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.registerClientTypeCreferrer);
        registrationPage.registerUser(userData);
        IMSUtils.validateCreferrer(userData.getUsername(), creferrerRegistration);
    }

    /*3*/
    @Test(groups = {"registration", "regression", "affiliate", "creferrer", "cookie"})
    public void affiliateCreferrerRegistrationPreferenceAndCookie() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.registerClientTypeCreferrer);
        AffiliateCookie affiliateCookie = new AffiliateCookie(affiliateDataSingle);
        affiliateCookie.add();
        registrationPage.registerUser(userData);
        affiliateDataSingle.addCreferrer(affiliateData.getCreferrerRegistrationPortletProperty());
        IMSUtils.validateAffiliate(userData.getUsername(), affiliateDataSingle);
    }

    /*4*/
    @Test(groups = {"registration", "regression", "affiliate", "url"})
    public void affiliateCreferrerRegistrationPreferenceAndUrl() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToAffiliateURL(ConfiguredPages.registerClientTypeCreferrer, affiliateDataSingle);
        registrationPage.registerUser(userData);
        affiliateDataSingle.addCreferrer(affiliateData.getCreferrerRegistrationPortletProperty());
        IMSUtils.validateAffiliate(userData.getUsername(), affiliateDataSingle);
    }

    /*5.1*/
    @Test(groups = {"registration", "regression", "affiliate", "cookie", "url"})
    public void affiliateCreferrerRegistrationPreferenceAndCookieAndUrlCookieFirst() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        AffiliateData affiliateDataCookie = affiliateData.getAffiliateDataSingle();
        affiliateDataCookie.setAdvertiser("advertiser");
        affiliateDataCookie.setBanner("banner");
        affiliateDataCookie.setProfile("profile");
        affiliateDataCookie.setUrl("url");
        affiliateDataCookie.setCreferrer("creferrer");
        AffiliateData affiliateDataUrl = affiliateData.getAffiliateDataSingle();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        AffiliateCookie affiliateCookie = new AffiliateCookie(affiliateDataCookie);
        affiliateCookie.add();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToAffiliateURL(ConfiguredPages.registerClientTypeCreferrer, affiliateDataUrl);
        registrationPage.registerUser(userData);
        affiliateDataUrl.addCreferrer(affiliateData.getCreferrerRegistrationPortletProperty());
        IMSUtils.validateAffiliate(userData.getUsername(), affiliateDataUrl);
    }

    /*5.2*/
    @Test(groups = {"registration", "regression", "affiliate", "cookie", "url"})
    public void affiliateCreferrerRegistrationPreferenceAndCookieAndUrlUrlFirst() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        AffiliateData affiliateDataUrl = affiliateData.getAffiliateDataSingle();
        affiliateDataUrl.setAdvertiser("advertiser");
        affiliateDataUrl.setBanner("banner");
        affiliateDataUrl.setProfile("profile");
        affiliateDataUrl.setUrl("url");
        affiliateDataUrl.setCreferrer("creferrer");
        AffiliateData affiliateDataCookie = affiliateData.getAffiliateDataSingle();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.registerClientTypeCreferrer);
        NavigationUtils.navigateToAffiliateURL(ConfiguredPages.registerClientTypeCreferrer, affiliateDataUrl);
        AffiliateCookie affiliateCookie = new AffiliateCookie(affiliateDataCookie);
        affiliateCookie.add();
        registrationPage.registerUser(userData);
        affiliateDataCookie.addCreferrer(affiliateData.getCreferrerRegistrationPortletProperty());
        IMSUtils.validateAffiliate(userData.getUsername(), affiliateDataCookie);
    }
}