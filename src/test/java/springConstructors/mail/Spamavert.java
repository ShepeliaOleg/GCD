package springConstructors.mail;

import pageObjects.external.mail.SpamavertPage;

import java.net.URL;

public class Spamavert extends MailService{

    public Spamavert(URL mailServiceUrl, String mailDomain){
		this.mailServiceUrl=mailServiceUrl;
		this.mailDomain=mailDomain;
    }

	@Override
	public SpamavertPage navigateToInbox(String emailOrUsername){
		return (SpamavertPage) super.navigateToInbox(emailOrUsername);
	}
}
