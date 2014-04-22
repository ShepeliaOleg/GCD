package pageObjects.popups;

import pageObjects.base.AbstractPopup;

public class AfterRegistrationPopup extends AbstractPopup{

	public static final String BUTTON_DEPOSIT_XP=ROOT_XP + "//a[@class='btn']";

	public AfterRegistrationPopup(){
		super(new String[]{BUTTON_DEPOSIT_XP});
	}
}
