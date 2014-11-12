package pageObjects.responsibleGaming;

import pageObjects.core.AbstractNotification;

public class SelfExcludeSuccessfulNotification extends AbstractNotification {

	public static final String ROOT_XP = AbstractNotification.ROOT_XP + "[contains(text(), 'Self exclusion was successful')]";

	public SelfExcludeSuccessfulNotification(){
		super(new String[]{ROOT_XP});
	}

}
