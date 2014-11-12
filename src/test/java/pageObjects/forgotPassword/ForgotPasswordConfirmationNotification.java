package pageObjects.forgotPassword;

import pageObjects.core.AbstractNotification;

public class ForgotPasswordConfirmationNotification extends AbstractNotification{

    private static final String ROOT_XP = AbstractNotification.ROOT_XP+"[contains(text(), 'Email with temporary password has been sent')]";

    public ForgotPasswordConfirmationNotification() {
        super(new String[]{ROOT_XP});
    }
}
