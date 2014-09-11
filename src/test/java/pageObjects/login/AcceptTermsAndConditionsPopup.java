package pageObjects.login;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageObjects.core.AbstractPopup;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 8/16/13
 */
public class AcceptTermsAndConditionsPopup extends AbstractPopup{
	public static final String BUTTON_ACCEPT_XP=ROOT_XP + "//button[@class='accept']";
	public static final String IFRAME_XP=		"//div[@class='message-area']/iframe";

	private static final String CASINO=			"playtech81001";
	private static final String SKIN=			"playtech81001";
	private static final String LANGUAGE=		"EN";
	private static final String CLIENTTYPE=		"portal";
	private static final String CLIENT=			"web";
	private static final String VERSION=		"3";

	public AcceptTermsAndConditionsPopup(){
		super(new String[]{BUTTON_ACCEPT_XP});
	}

	@FindBy(xpath=BUTTON_ACCEPT_XP)
	private WebElement buttonAccept;

	public void clickClose(){
		WebDriverUtils.click(BUTTON_ACCEPT_XP);
	}

	public boolean checkTermsAndConditionsUrlParameters(){
		String url=WebDriverUtils.getAttribute(IFRAME_XP, "src");
		return url.contains("casino=" + CASINO) && url.contains("skin=" + SKIN) && url.contains("language=" + LANGUAGE) && url.contains("clienttype=" + CLIENTTYPE) && url.contains("client=" + CLIENT) && url.contains("version=" + VERSION);

	}
}
