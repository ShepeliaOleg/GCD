package pageObjects.forgotPassword;

import pageObjects.core.AbstractPage;

public class ForgotPasswordPage extends AbstractPage {
    public ForgotPasswordPopup forgotPasswordPopup;

    public ForgotPasswordPage() {
        forgotPasswordPopup = new ForgotPasswordPopup("page");
    }

}
