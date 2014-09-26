package pageObjects.registration;

import pageObjects.core.AbstractPopup;

/*
 * User: ivanva
 * Date: 4/19/13
 */

public class ReadTermsAndConditionsPopup extends AbstractPopup{

	public static final String ROOT_XP = AbstractPopup.ROOT_XP + "//strong[contains(text(),'TERMS AND CONDITIONS')]";

	public ReadTermsAndConditionsPopup(){
		super(new String[]{ROOT_XP});
	}

}