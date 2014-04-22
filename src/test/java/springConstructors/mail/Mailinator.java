package springConstructors.mail;

import pageObjects.external.mail.MailinatorPage;

import java.net.URL;

public class Mailinator extends MailService{

    public Mailinator(URL mailServiceUrl, String mailDomain){
            this.mailServiceUrl=mailServiceUrl;
            this.mailDomain=mailDomain;
    }

	@Override
	public MailinatorPage navigateToInbox(String emailOrUsername){
		return (MailinatorPage) super.navigateToInbox(emailOrUsername);
	}
}
