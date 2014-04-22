package springConstructors.mail;

import pageObjects.external.mail.MailServicePage;
import pageObjects.external.mail.MailinatorPage;
import pageObjects.external.mail.SpamavertPage;
import utils.RandomUtils;
import utils.core.WebDriverObject;

import java.net.URL;

public abstract class MailService extends WebDriverObject{

    protected URL mailServiceUrl;
    protected String mailDomain;

    public MailServicePage navigateToInbox(String emailOrUsername){
		if (emailOrUsername.contains("@")) {
            int index = emailOrUsername.indexOf("@");
            emailOrUsername = emailOrUsername.substring(0, index);
        }

        webDriver.navigate().to(mailServiceUrl.toString().concat(emailOrUsername));
		if (mailServiceUrl.toString().contains("spamavert")) {
            return new SpamavertPage();
        } else
        if (mailServiceUrl.toString().contains("mailinator")) {
            return new MailinatorPage();
        } else {
            throw new RuntimeException("Unknown mail service: " + mailServiceUrl);
        }
	}

//    public String generateEmail(){
//        return "";
//    }

    public String generateEmail(String username){
        return RandomUtils.generateEmail(username.replaceAll(" ", "").replaceAll("'", "").replaceAll(",", ""), mailDomain);
    }

}
