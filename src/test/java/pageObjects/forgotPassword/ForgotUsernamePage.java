package pageObjects.forgotPassword;

import pageObjects.base.AbstractPage;

public class ForgotUsernamePage extends AbstractPage {
    public ForgotUsernamePopup forgotUsernamePopup;

    public ForgotUsernamePage() {
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage();

        forgotPasswordPage.forgotPasswordPopup.switchToForgotUsernamePopup();

        forgotUsernamePopup = new ForgotUsernamePopup("page");
    }

}
