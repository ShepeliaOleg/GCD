package pageObjects.registration;

import pageObjects.core.AbstractPopup;

public class AfterRegistrationPopup extends AbstractPopup{
    public static final String ROOT_XP =  AbstractPopup.ROOT_XP + "//*[contains(@href, '/deposit')]";

	public AfterRegistrationPopup(){
		super(new String[]{ROOT_XP});
	}
}
