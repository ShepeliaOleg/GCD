package springConstructors;

import utils.core.AbstractTest;
import utils.core.WebDriverFactory;

import java.net.MalformedURLException;
import java.net.URL;

public class DeviceData {

    public Device[] devices;

    public Device[] getDevices() {
        return devices;
    }

    public void setDevices(Device[] devices) {
        this.devices = devices;
    }

    private Device getDeviceByName(String name){
        for(Device device:devices) {
            if (device.getName().equals(name)) {
                return device;
            }
        }
        AbstractTest.failTest("Device '" + name + "' is not found.");
        return null;
    }

    public String getSerialByName(String name) {
        return getDeviceByName(name).getSerial();
    }

    public URL getRemoteByName(String name) {
        URL remote = null;
        String url = getDeviceByName(name).getRemote();
        if(WebDriverFactory.browser.equals("native")){
            url = url.replace("9515", "4444/wd/hub/");
        }
        try {
            remote = new URL(url);
        } catch (MalformedURLException e) {
            AbstractTest.failTest("Remote URL for '" + name + "' is empty or incorrect.");
        }
        return remote;
    }
}
