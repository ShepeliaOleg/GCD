package pageObjects.external.mail;

import utils.WebDriverUtils;
import utils.core.AbstractTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailinatorPage extends MailServicePage {

    private static final String MAILLIST_XP=		"//ul[@id='mailcontainer']";
    private static final String IFRAME_XP=		"//*[@id='mailshowdivbody']//iframe";
    private static final String MAILLIST_ITEM_XP=	MAILLIST_XP + "//*[contains(text(), 'New Password')]";
    private static final String MATCHER=			"Your account password is ";
    private static final String LETTER_CONTENT_XP=	"//div[@class='mailview']";

    @Override
    public boolean inboxIsEmpty(){
        return WebDriverUtils.getXpathCount(MAILLIST_ITEM_XP) == 0;
    }

    private String getPasswordLetterText(){
        String result;
        if(WebDriverUtils.isVisible(MAILLIST_XP)){
            WebDriverUtils.click(MAILLIST_ITEM_XP);
        }
        WebDriverUtils.waitForElement(IFRAME_XP);
        WebDriverUtils.switchToIframeByXpath(IFRAME_XP);
        result = WebDriverUtils.getElementText(LETTER_CONTENT_XP);
        WebDriverUtils.switchFromIframe();
        return result;
    }

    @Override
    public String getPasswordFromLetter(){
        String password=null;
        String encodedLetter= getPasswordLetterText();
        Pattern pattern=Pattern.compile(MATCHER + "\\S+");
        Matcher matcher=pattern.matcher(encodedLetter);
        if(matcher.find()){
            password=matcher.group().replace(MATCHER, "");
        }

        return password;
    }

    @Override
    public void waitForPasswordEmail(long timeout){
        AbstractTest.validateTrue(WebDriverUtils.isVisible(MAILLIST_ITEM_XP, timeout), "Email was received after '"+timeout+"' seconds");
    }
}
