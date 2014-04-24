package pageObjects.forgotPassword;

import pageObjects.base.AbstractPage;

public class ForgotPasswordPage extends AbstractPage {
    public ForgotPasswordPopup forgotPasswordPopup;

    public ForgotPasswordPage() {
        forgotPasswordPopup = new ForgotPasswordPopup("page");
    }

}
