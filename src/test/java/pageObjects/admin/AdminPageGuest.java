package pageObjects.admin;

import pageObjects.core.AbstractPortalPage;
import utils.WebDriverUtils;
import utils.core.DataContainer;

public class AdminPageGuest extends AbstractPortalPage {

    private final static String ROOT_XP = "//*[@class='login-admin-portlet']";
    private final static String FIELD_USERNAME_XP = "//*[@id='_58_login']";
    private final static String FIELD_PASSWORD_XP = "//*[@id='_58_password']";
    private final static String BUTTON_LOGIN_XP = ROOT_XP+"//input[@class='btn']";


    public AdminPageGuest(){
        super(new String[]{ROOT_XP});
    }

    private void clickLogin(){
        WebDriverUtils.click(BUTTON_LOGIN_XP);
    }

    private void fillUsername(){
        WebDriverUtils.executeScript("document.getElementById('_58_login').value = '" + DataContainer.getAdminUserData().getAdminUsername() + "'");
//        WebDriverUtils.inputTextToField(FIELD_USERNAME_XP, getAdminUserData().getAdminUsername(), 100);
    }

    private void fillPassword(){
        WebDriverUtils.executeScript("document.getElementById('_58_password').value = '" + DataContainer.getAdminUserData().getAdminPassword() + "'");
//        WebDriverUtils.inputTextToField(FIELD_PASSWORD_XP, getAdminUserData().getAdminUsername(), 100);
    }

    public AdminPageAdmin loginAdmin() {
        while(WebDriverUtils.isVisible(FIELD_USERNAME_XP, 0)) {
            fillUsername();
            fillPassword();
            clickLogin();
            WebDriverUtils.waitFor();
        }
        return waitForAdminLogin();
    }

    public AdminPageAdmin waitForAdminLogin(){
        WebDriverUtils.waitForElement(AdminPageAdmin.DOCKBAR_XP);
        return new AdminPageAdmin();
    }

}
