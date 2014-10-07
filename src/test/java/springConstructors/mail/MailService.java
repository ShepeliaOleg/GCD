package springConstructors.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import pageObjects.external.mail.MailServicePage;
import pageObjects.external.mail.MailinatorPage;
import pageObjects.external.mail.SpamavertPage;
import springConstructors.ValidationRule;
import utils.RandomUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;
import utils.core.WebDriverObject;

import java.net.URL;

public abstract class MailService extends WebDriverObject{

    @Autowired
    @Qualifier("emailValidationRule")
    private ValidationRule emailValidationRule;

    protected URL mailServiceUrl;
    protected String mailDomain;

    private String getUsername(String email) {
        String username = email;
        if (email.contains("@")) {
            int index = email.indexOf("@");
            username = email.substring(0, index);
        }
        return username;
    }

    public MailServicePage navigateToInbox(String email){
        String username = getUsername(email);

        WebDriverUtils.navigateToURL(mailServiceUrl.toString().concat(username));
        if (mailServiceUrl.toString().contains("spamavert")) {
            return new SpamavertPage();
        } else
        if (mailServiceUrl.toString().contains("mailinator")) {
            return new MailinatorPage();
        } else {
            AbstractTest.failTest("Unknown mail service: " + mailServiceUrl);
        }
	}

    public String generateEmail(){
        String email = emailValidationRule.generateValidString();
        String username = getUsername(email);
        return RandomUtils.generateEmail(username, mailDomain);
    }

}
