package pageObjects.registration;

import pageObjects.core.AbstractPopup;

public class AfterRegistrationPopup extends AbstractPopup{

	public static final String AFTER_REG_ROOT_XP=ROOT_XP + "//*[contains(text(), 'After Registration Popup')]";

	public AfterRegistrationPopup(){
		super(new String[]{AFTER_REG_ROOT_XP});
	}
}
