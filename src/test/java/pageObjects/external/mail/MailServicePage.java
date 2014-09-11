package pageObjects.external.mail;

import pageObjects.core.AbstractPage;

public abstract class MailServicePage extends AbstractPage{

    //constructor
    public MailServicePage(){
        super();
    }

    public abstract boolean inboxIsEmpty();

    public abstract String getPasswordFromLetter();

	public void waitForEmail() {
		waitForPasswordEmail(600);
	}

    public abstract void waitForPasswordEmail(long timeout);

}
