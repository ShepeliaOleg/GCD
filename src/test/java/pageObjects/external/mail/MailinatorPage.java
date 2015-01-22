package pageObjects.external.mail;

import utils.WebDriverUtils;
import utils.core.AbstractTest;
import utils.core.WebDriverFactory;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailinatorPage extends MailServicePage {

    private static final String MAILLIST_XP=		"//ul[@id='mailcontainer']";
    private static final String IFRAME_XP=		    "//*[@id='mailshowdivbody']//iframe";
    private static final String MAILLIST_ITEM_XP=	MAILLIST_XP + "//*[contains(text(), 'New Password')]";
    private static final String MATCHER=			"It is now: ";
    private static final String MAILLIST_INVITATION_ITEM_XP=	MAILLIST_XP + "//*[contains(text(), 'An invitation from')]";
    private static final String LETTER_CONTENT_XP=	"//div[@class='mailview']";

    @Override
    public boolean inboxIsEmpty(){
        return WebDriverUtils.getXpathCount(WebDriverFactory.getServerDriver(), MAILLIST_ITEM_XP) == 0;
    }

    private String getPasswordLetterText(){
        String result;
        if(WebDriverUtils.isVisible(WebDriverFactory.getServerDriver(), MAILLIST_XP)){
            WebDriverUtils.click(WebDriverFactory.getServerDriver(), MAILLIST_ITEM_XP);
        }
        WebDriverUtils.waitForElement(WebDriverFactory.getServerDriver(), IFRAME_XP);
        WebDriverUtils.switchToIframeByXpath(WebDriverFactory.getServerDriver(), IFRAME_XP);
        result = WebDriverUtils.getElementText(WebDriverFactory.getServerDriver(), LETTER_CONTENT_XP);
        WebDriverUtils.switchFromIframe(WebDriverFactory.getServerDriver());
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
        AbstractTest.validateTrue(WebDriverUtils.isVisible(WebDriverFactory.getServerDriver(), MAILLIST_ITEM_XP, timeout), "Email with password was received after '"+timeout+"' seconds");
    }

    @Override
    public void waitForInvitationEmail(long timeout) {
        AbstractTest.validateTrue(WebDriverUtils.isVisible(WebDriverFactory.getServerDriver(), MAILLIST_INVITATION_ITEM_XP, timeout), "Email with invitation was received after '"+timeout+"' seconds");
    }
}
