package pageObjects.registration;

import pageObjects.core.AbstractPortalPopup;

public class AfterRegistrationPopup extends AbstractPortalPopup{
    public static final String ROOT_XP =  AbstractPortalPopup.ROOT_XP + "//*[contains(@href, '/deposit')]";

	public AfterRegistrationPopup(){
		super(new String[]{ROOT_XP});
	}
}
