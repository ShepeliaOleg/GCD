package pageObjects.external.ims;

import org.openqa.selenium.WebDriver;
import pageObjects.core.AbstractServerPage;
import utils.WebDriverUtils;
import utils.core.WebDriverFactory;

public class IMSPlayerBonusInfoPage extends AbstractServerPage {

    private static WebDriver serverDriver = WebDriverFactory.getServerDriver();
    //private static final String TABLE_MAIN =	"//div[contains (text(), 'Player balance info')]";
    private static final String TABLE_MAIN =	"/html/body/div";
    private static final String TABLE_OPTIN =	"//div[contains (text(), 'Player active bonus opt-ins')]";

    {
        serverDriver.switchTo().frame("main-content");
    }

    public IMSPlayerBonusInfoPage() {
        //super(new String[]{TABLE_MAIN, TABLE_OPTIN});
        super(new String[]{TABLE_MAIN});
        //super(new String[]{"html/body/div"});
    }

    public boolean isVisibleActiveBonus(String optInID){
        return WebDriverUtils.isElementVisible(serverDriver, "//table[@class='result']/tbody/tr/td[2]/a[@href='/bonus_info.php?code=" + optInID + "']", 1);
    }

    public boolean isVisibleNoActiveBonus(String optInID) {
        return WebDriverUtils.isElementVisible(serverDriver, "//table[@class='result']/tbody/tr[2]/td[contains (text(), 'No active bonus opt-ins')]");
    }

    public String getNameActiveBonus(String optInID){
        return WebDriverUtils.getElementText(serverDriver, "//table[@class='result']/tbody/tr/td[2]/a[@href='/bonus_info.php?code=" + optInID + "']");
    }
}
