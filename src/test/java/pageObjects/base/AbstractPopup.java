package pageObjects.base;

import org.apache.commons.lang3.ArrayUtils;
import utils.WebDriverUtils;

public abstract class AbstractPopup extends AbstractPageObject{

	public final static String ROOT_XP=			"//*[contains(@class, 'reveal-modal')and not(@class='reveal-modal-bg')]";
	public final static String BUTTON_CLOSE_XP=	ROOT_XP + "//a[@class='popupClose']";
	public final static String BUTTON_ACCEPT_XP=ROOT_XP + "//*[@data-action='accept']";

	public AbstractPopup(){
		this(null);
	}

	public AbstractPopup(String[] clickableBys){
		this(clickableBys, new String[]{});
	}

	public AbstractPopup(String[] clickableBys, String[] invisibleBys){
        this(clickableBys, invisibleBys, ROOT_XP);
	}

    public AbstractPopup(String[] clickableBys, String[] invisibleBys, String rootXp) {
        super(ArrayUtils.addAll(clickableBys, new String[]{rootXp}), invisibleBys);
        WebDriverUtils.waitFor(1000);
    }

    public void clickClose(){
		WebDriverUtils.click(BUTTON_CLOSE_XP);
	}

	public void clickAccept(){
		WebDriverUtils.click(BUTTON_ACCEPT_XP);
	}
}


