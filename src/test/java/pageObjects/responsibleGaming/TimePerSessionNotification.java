package pageObjects.responsibleGaming;

import pageObjects.core.AbstractNotification;

public class TimePerSessionNotification extends AbstractNotification{

    public TimePerSessionNotification(String time){
        super("Time per session has been activated on "+time+"')]");
    }
}
