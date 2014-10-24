package pageObjects.pageInPopup;

import enums.ConfiguredPages;
import pageObjects.core.AbstractPopup;
import pageObjects.registration.AdultContentPopup;
import utils.WebDriverUtils;

public class PageInPopupPopup extends AbstractPopup {

    private static final String LABEL_XP =  ROOT_XP + "//p[contains(text(), 'Popup')]";
    private static final String BUTTON_XP=  ROOT_XP + "//a[contains(@class, 'btn')]";

	public PageInPopupPopup(){
		super(new String[]{LABEL_XP, BUTTON_XP});
	}

    public AbstractPopup clickButton() {
        WebDriverUtils.click(BUTTON_XP);
        if (WebDriverUtils.getCurrentUrl().endsWith(ConfiguredPages.page_in_popup_b.toString())) {
            return new AdultContentPopup();
        } else {
            return new PageInPopupPopup();
        }

    }
}
