package pageObjects.external.mail;

import utils.RandomUtils;
import utils.WebDriverUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailinatorPage extends MailServicePage {

    private static final String MAILLIST_XP=		"//ul[@id='mailcontainer']";
    private static final String MAILLIST_ITEM_XP=	MAILLIST_XP.concat("/li");
    private static final String MATCHER=			"Password: ";
    private static final String LETTER_CONTENT_XP=	"//div[@class='mailview']";

    @Override
    public boolean inboxIsEmpty(){
        return WebDriverUtils.getXpathCount(MAILLIST_ITEM_XP) == 0;
    }

    private String getLetterText(){
        return getLetterText(1);
    }

    private String getLetterText(Integer mailIndex){
        if(WebDriverUtils.isVisible(MAILLIST_XP)){
            WebDriverUtils.click(MAILLIST_ITEM_XP.concat("[").concat(mailIndex.toString()).concat("]"));
        }

        return WebDriverUtils.getElementText(LETTER_CONTENT_XP);
    }

    @Override
    public String getPasswordFromLetter(){
        String password=null;
        String encodedLetter=getLetterText();
        String decodedLetter= RandomUtils.decodeBase64(encodedLetter);
        Pattern pattern=Pattern.compile(MATCHER.concat("\\S+"));
        Matcher matcher=pattern.matcher(decodedLetter);
        if(matcher.find()){
            password=matcher.group().replace(MATCHER, "");
        }

        return password;
    }

    @Override
    public void waitForEmail(long timeout){
        try{
            WebDriverUtils.waitForElement(MAILLIST_ITEM_XP, timeout);
        }catch(RuntimeException e){
            WebDriverUtils.runtimeExceptionWithLogs("Email was not received after waiting for " + timeout + " seconds");
        }

    }
}
