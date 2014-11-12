package pageObjects.referAFriend;

import pageObjects.core.AbstractPortalPage;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

public class ReferAFriendPage extends AbstractPortalPage{
	private static final String REFER_A_FRIEND_ROOT_XP=	"//*[contains(@id,'portlet_referafriend')]";
	private static final String FIELD_NAME1_XP=			"//*[@id='recommendedFriends0.name']";
    private static final String FIELD_NAME2_XP=			"//*[@id='recommendedFriends1.name']";
    private static final String FIELD_NAME3_XP=			"//*[@id='recommendedFriends2.name']";
    private static final String FIELD_EMAIL1_XP=		"//*[@id='recommendedFriends0.email']";
    private static final String FIELD_EMAIL2_XP=		"//*[@id='recommendedFriends1.email']";
    private static final String FIELD_EMAIL3_XP=		"//*[@id='recommendedFriends2.email']";
    public  static final String BUTTON_CONFIRM_XP=		"//*[@id='referAFriend']";
	private static final String MESSAGE_SENT=			"Your recommendations have been sent";
	private static final String MESSAGE_SAME=			"One of your referred emails has already been added";
	private static final String MESSAGE_XP=				"//div[contains(@class,'recommend')]/*[contains(@class,'msg')]";


	public ReferAFriendPage(){
		super(new String[]{REFER_A_FRIEND_ROOT_XP, FIELD_NAME1_XP, FIELD_EMAIL1_XP, BUTTON_CONFIRM_XP});
	}

    private void fillRecipientInfo(String inputNameXpath, String name, String inputEmailXpath, String email) {
        WebDriverUtils.clearAndInputTextToField(inputNameXpath, name);
        WebDriverUtils.clearAndInputTextToField(inputEmailXpath, email);
    }

	public void fillFirstRecipientInfo(String recipientName, String recipientEmail){
		fillRecipientInfo(FIELD_NAME1_XP, recipientName, FIELD_EMAIL1_XP, recipientEmail);
	}

    public void fillSecondRecipientInfo(String recipientName, String recipientEmail){
        fillRecipientInfo(FIELD_NAME2_XP, recipientName, FIELD_EMAIL2_XP, recipientEmail);
    }

	public void send(){
		WebDriverUtils.click(BUTTON_CONFIRM_XP);
	}

//	private WebElement notificationMessageDisplayed(){
//		//WebElement messageNotification = (new WebDriverWait(getWebDriver(), 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(MESSAGE_XP)));
//		//return messageNotification;
//		WebElement messageNotification=getWebDriver().findElement(By.xpath(MESSAGE_XP));
//		return messageNotification;
//	}
//
//	private boolean determineMessageType(String messageText){
//		WebElement messageNotification=notificationMessageDisplayed();
//		return messageNotification.getText().contains(messageText);
//	}
//
//	public boolean notificationMessageIsSuccessful(){
//		return determineMessageType(MESSAGE_SENT);
//	}
//
//	public boolean notificationMessageIsUnsuccessful(){
//
//		return determineMessageType(MESSAGE_SAME);
//	}

    private boolean notificationMessageDisplayed(){
        return WebDriverUtils.isVisible(MESSAGE_XP);
    }

	public void validateNotificationMessageIsSuccessful(){
        AbstractTest.validateTrue(notificationMessageDisplayed(), "Notification message displayed");
        String message = WebDriverUtils.getElementText(MESSAGE_XP);
        AbstractTest.assertTrue(message.contains(MESSAGE_SENT), "Notification message contains '"+MESSAGE_SENT+"'");
	}

//    public void validateName(ValidationRule rule) {
//        ValidationUtils.validate(FIELD_NAME1_XP, rule);
//    }
//
//    public void validateEmail(ValidationRule rule) {
//		ValidationUtils.validate(FIELD_EMAIL1_XP, rule);
//    }
}
