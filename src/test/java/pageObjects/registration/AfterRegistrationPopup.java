package pageObjects.registration;

import pageObjects.core.AbstractPopup;

public class AfterRegistrationPopup extends AbstractPopup{

	public static final String BUTTON_DEPOSIT_XP=ROOT_XP + "//a[contains(@class, 'btn')][contains(@class, 'depositButton')]";

	public AfterRegistrationPopup(){
		super(new String[]{BUTTON_DEPOSIT_XP});
	}
}
