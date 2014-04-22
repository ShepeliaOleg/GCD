package pageObjects.forgotPassword;

import org.openqa.selenium.WebDriver;
import pageObjects.base.AbstractPopup;

/**
 * User: svetlanagl
 * Date: 3/20/13
 */

public class ForgotPasswordConfirmationPopup extends AbstractPopup{

	public static final String MODULE_LOCATOR_XP="//p[contains(text(),'We have sent a new password to your email')]";

	public ForgotPasswordConfirmationPopup(WebDriver webDriver){
		super(new String[]{MODULE_LOCATOR_XP});
	}
}
