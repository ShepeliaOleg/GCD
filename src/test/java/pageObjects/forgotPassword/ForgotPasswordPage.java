package pageObjects.forgotPassword;

import pageObjects.core.AbstractPortalPage;

public class ForgotPasswordPage extends AbstractPortalPage {
    public ForgotPasswordPopup forgotPasswordPopup;

    public ForgotPasswordPage() {
        forgotPasswordPopup = new ForgotPasswordPopup("page");
    }

}
