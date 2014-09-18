package pageObjects.registration;

import pageObjects.core.AbstractPopup;

public class AfterRegistrationPopup extends AbstractPopup{
    private static final String AFTER_REG_ROOT_MOBILE_XP =  ROOT_XP + "//*[contains(text(), 'After Registration Popup')]";
    private static final String AFTER_REG_ROOT_DESKTOP_XP = ROOT_XP + "//*[contains(@class, 'depositButton')]";

    public static final String AFTER_REG_ROOT_XP = AFTER_REG_ROOT_MOBILE_XP + " | " + AFTER_REG_ROOT_DESKTOP_XP;

	public AfterRegistrationPopup(){
		super(new String[]{AFTER_REG_ROOT_XP});
	}
}
