package pageObjects.external.ims;

import pageObjects.core.AbstractServerPage;
import utils.WebDriverUtils;
import utils.core.WebDriverFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by serhiist on 4/14/2015.
 */
public class IMSBonusTemplateInfoPage extends AbstractServerPage {
    private static final String ROOT_XP = "//*[@id='bonus-info-form']";
    private static final String FIELD_MARKETING_NAME_XP = "//*[@id='marketingname']";
    private static final String FIELD_EXPIRATION_DATE_XP = "//*[@id='enddate']";

    {
        WebDriverFactory.getServerDriver().switchTo().frame("main-content");
    }

    public IMSBonusTemplateInfoPage(){
        super(new String[]{ROOT_XP});
    }

    public String getMarketingName(){
        return WebDriverUtils.getElementText(FIELD_MARKETING_NAME_XP);
    }

    public String getExpirationDate() {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(WebDriverUtils.getElementText(FIELD_EXPIRATION_DATE_XP));
        } catch (ParseException e) {
            System.out.println("Unable to parse date");e.printStackTrace();
        }
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }
}
