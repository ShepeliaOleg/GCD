package pageObjects.admin;

import pageObjects.core.AbstractPortalPage;
import utils.WebDriverUtils;

public class AdminPage extends AbstractPortalPage {

    private final static String ROOT_XP = "//*[@class='login-admin-portlet']";
    public final static String DOCKBAR_XP = "//*[@id='dockbar']";
    private final static String FIELD_USERNAME_XP = "//*[@id='_58_login']";
    private final static String FIELD_PASSWORD_XP = "//*[@id='_58_password']";
    private final static String BUTTON_LOGIN_XP = ROOT_XP+"//input[@class='btn']";
    private final static String BUTTON_LOGOUT_XP = "//*[@class='sign-out']";

    public AdminPage(){
        super(new String[]{ROOT_XP});
    }

    private void clickLogin(){
        WebDriverUtils.click(BUTTON_LOGIN_XP);
    }

    private void fillUsername(){
        WebDriverUtils.executeScript("document.getElementById('_58_login').value = 'sergii.chernyavskiy'");
//        WebDriverUtils.inputTextToField(FIELD_USERNAME_XP, getAdminUserData().getAdminUsername(), 100);
    }

    private void fillPassword(){
        WebDriverUtils.executeScript("document.getElementById('_58_password').value = '9875321Res'");
//        WebDriverUtils.inputTextToField(FIELD_PASSWORD_XP, getAdminUserData().getAdminUsername(), 100);
    }

    public AbstractPortalPage loginAdmin() {
        fillUsername();
        fillPassword();
        clickLogin();
        WebDriverUtils.waitFor();
        return waitForAdminLogin();
    }

    public AbstractPortalPage waitForAdminLogin(){
        WebDriverUtils.waitForElement(DOCKBAR_XP);
        return new AbstractPortalPage();
    }

    public static void clickLogout(){
        WebDriverUtils.click(BUTTON_LOGOUT_XP);
    }

}
