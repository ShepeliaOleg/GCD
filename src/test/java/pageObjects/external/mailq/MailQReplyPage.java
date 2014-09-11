package pageObjects.external.mailq;

import pageObjects.core.AbstractPage;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 8/2/13
 */

public class MailQReplyPage extends AbstractPage{

	private static final String ROOT_XP=			"//*[@id='sendMesagePanel']";
	private static final String CHANGE_SUBJECT_XP=	"//*[@id='edit_subject']";
	private static final String FIELD_MESSAGE_XP=	"//*[@id='reply_message']";
	private static final String CHECKBOX_EMAIL_XP=	"//*[@id='reply_sendAs-1']";
	private static final String CHECKBOX_MESSAGE_XP="//*[@id='reply_sendAs-2']";
	private static final String BUTTON_SEND_XP=		"//*[@id='reply_replySubmit']";
	private static final String DROPDOWN_SUBJECT_XP="//*[@id='reply_topic']";

	public MailQReplyPage(){
		super(new String[]{ROOT_XP, CHANGE_SUBJECT_XP});
	}


	private void setFieldMessage(String text){
		WebDriverUtils.clearAndInputTextToField(FIELD_MESSAGE_XP, text);
	}

	public void setEmailCheckbox(boolean state){
		WebDriverUtils.setCheckBoxState(CHECKBOX_EMAIL_XP, state);
	}

	public void setMessageCheckbox(boolean state){
		WebDriverUtils.setCheckBoxState(CHECKBOX_MESSAGE_XP, state);
	}

	public void setSubjectDropdown(){
		WebDriverUtils.setDropdownOptionByText(DROPDOWN_SUBJECT_XP, "Test subject");
	}

	public MailQHomePage clickSendButton(){
		WebDriverUtils.click(BUTTON_SEND_XP);
		return new MailQHomePage();
	}

	public MailQHomePage sendMessage(String text, boolean sendEmail, boolean sendMessage){
		setSubjectDropdown();
		setFieldMessage(text);
		setEmailCheckbox(sendEmail);
		setMessageCheckbox(sendMessage);
		return clickSendButton();
	}
}
