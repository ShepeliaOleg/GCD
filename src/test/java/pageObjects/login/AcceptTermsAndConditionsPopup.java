package pageObjects.login;

import pageObjects.core.AbstractPortalPopup;

public class AcceptTermsAndConditionsPopup extends AbstractPortalPopup{
	public static final String TERMS_ROOT_XP=		"//*[contains(@class,'terms-popup')]";

	private static final String CASINO=			"playtech81001";
	private static final String SKIN=			"playtech81001";
	private static final String LANGUAGE=		"EN";
	private static final String CLIENTTYPE=		"portal";
	private static final String CLIENT=			"web";
	private static final String VERSION=		"3";

	public AcceptTermsAndConditionsPopup(){
		super(new String[]{TERMS_ROOT_XP});
	}

}
