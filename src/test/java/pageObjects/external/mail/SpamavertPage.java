package pageObjects.external.mail;

import utils.TypeUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpamavertPage extends MailServicePage {

	private static final String MAILLIST_XP="//ul[@class='maillist']";
	private static final String MAILLIST_ITEM_XP=MAILLIST_XP.concat("/li");
	private static final String MATCHER="Password: ";
	private static final String LETTER_CONTENT_XP="//*[@id='mail']";
	private static final String WAITING_DIALOG_XP="//*[@id='dialogWaiting']";
	private static final String WAITING_DIALOG_BUTTON_ID="btnCancelWaiting";

    @Override
	public boolean inboxIsEmpty(){
		return WebDriverUtils.isVisible(WAITING_DIALOG_XP);
	}

	public int getInboxCount(){
		if(inboxIsEmpty()){
			return 0;
		}else
			return WebDriverUtils.getXpathCount(MAILLIST_ITEM_XP);
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
		String decodedLetter= TypeUtils.decodeBase64(encodedLetter);
		Pattern pattern=Pattern.compile(MATCHER.concat("\\S+"));
		Matcher matcher=pattern.matcher(decodedLetter);
		if(matcher.find()){
			password=matcher.group().replace(MATCHER, "");
		}

		return password;
	}

    @Override
	public void waitForPasswordEmail(long timeout){
        AbstractTest.validateTrue(WebDriverUtils.isVisible(WAITING_DIALOG_XP, timeout), "Email was received after '" + timeout + "' seconds");
	}
}
