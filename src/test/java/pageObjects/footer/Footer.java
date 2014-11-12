package pageObjects.footer;

import org.apache.commons.lang3.ArrayUtils;
import pageObjects.core.AbstractPageObject;
import utils.WebDriverUtils;
import utils.core.WebDriverFactory;

public class Footer extends AbstractPageObject{
	protected static final String ROOT_XP=	"//*[contains(@class,'fn-footer-wrapper')]";
	private static final String CONTENT_XP =	ROOT_XP + "/p";

    public Footer(){
		super(WebDriverFactory.getPortalDriver(), new String[]{ROOT_XP});
	}

	public Footer(String[] clickableBys){
		super(WebDriverFactory.getPortalDriver(), ArrayUtils.addAll(clickableBys, new String[]{ROOT_XP}));
	}


    public String getFooterText() {
        return WebDriverUtils.getElementText(CONTENT_XP);
    }
}
