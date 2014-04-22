package pageObjects.external.mail;

import pageObjects.base.AbstractPage;

public abstract class MailServicePage extends AbstractPage{

    //constructor
    public MailServicePage(){
        super();
    }

    public abstract boolean inboxIsEmpty();

    public abstract String getPasswordFromLetter();

	public void waitForEmail() {
		waitForEmail(600);
	}

    public abstract void waitForEmail(long timeout);

}
