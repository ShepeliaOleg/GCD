package pageObjects.registration;

import pageObjects.core.AbstractPortalPopup;

public class ReadTermsAndConditionsPopup extends AbstractPortalPopup{

//    public static final String ROOT_XP = AbstractPortalPopup.ROOT_XP + "//strong[contains(text(),'TERMS AND CONDITIONS')]";
    public static final String ROOT_XP = AbstractPortalPopup.ROOT_XP + "//*[@class='popup-modal__title'][contains(text(), 'Terms & Conditions')]";

	public ReadTermsAndConditionsPopup(){
		super(new String[]{ROOT_XP});
	}

}