package pageObjects.responsibleGaming;

import pageObjects.core.AbstractNotification;

public class TimePerSessionNotification extends AbstractNotification{

    public TimePerSessionNotification(String time){
        super(new String[] {ROOT_XP+"[contains(text(), 'Time per session has been activated on "+time+"')]"});
    }
}
