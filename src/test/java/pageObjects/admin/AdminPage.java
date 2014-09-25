package pageObjects.admin;

import pageObjects.core.AbstractPage;
import utils.WebDriverUtils;

public class AdminPage extends AbstractPage {

    private final static String ROOT_XP = "//*[@class='login-admin-portlet']";
    public final static String DOCKBAR_XP = "//*[@id='dockbar']";
    private final static String FIELD_USERNAME_XP = "//*[@id='_58_login']";
    private final static String FIELD_PASSWORD_XP = "//*[@id='_58_password']";
    private final static String BUTTON_LOGIN_XP = ROOT_XP+"//*[@class='btn']";
    private final static String BUTTON_LOGOUT_XP = "//*[@class='sign-out']";
    private final static String LOGIN = "test";
    private final static String PASSWORD = LOGIN;

    public AdminPage(){
        super(new String[]{ROOT_XP});
    }

    private void clickLogin(){
        WebDriverUtils.click(BUTTON_LOGIN_XP);
    }

    private void fillUsername(){
        WebDriverUtils.inputTextToField(FIELD_USERNAME_XP, LOGIN);
    }

    private void fillPassword(){
        WebDriverUtils.inputTextToField(FIELD_PASSWORD_XP, PASSWORD);
    }

    public AbstractPage loginAdmin() {
        fillUsername();
        fillPassword();
        clickLogin();
        return waitForAdminLogin();
    }

    public AbstractPage waitForAdminLogin(){
        WebDriverUtils.waitForElement(DOCKBAR_XP);
        return new AbstractPage();
    }

    public static void clickLogout(){
        WebDriverUtils.click(BUTTON_LOGOUT_XP);
    }

}
