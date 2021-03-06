package pageObjects.liveCasino;

import pageObjects.core.AbstractPortalPopup;
import utils.WebDriverUtils;

public class DealerImagePopup extends AbstractPortalPopup {

	private static final String IMAGE_XP=			ROOT_XP + "//img";
	private static final String FIELD_DEALER_NAME=	ROOT_XP + "//h2[@class='title']";

	public DealerImagePopup(){
		super(new String[]{IMAGE_XP});
	}

	public String getDealerName(){
		return WebDriverUtils.getElementText(FIELD_DEALER_NAME);
	}

	public boolean imageNotNull(){
		boolean imageIsLoaded=WebDriverUtils.getAttribute(IMAGE_XP, "src").contains(".jpg");
		boolean imageSizeIsNotNull=WebDriverUtils.getElementWidth(IMAGE_XP) > 0 && WebDriverUtils.getElementHeight(IMAGE_XP) > 0;
		return (imageIsLoaded && imageSizeIsNotNull) ? true : false;
	}
}
