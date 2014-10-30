package springConstructors;

import utils.core.AbstractTest;

import java.net.MalformedURLException;
import java.net.URL;

public class DeviceData {

    public Device[] devices;

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

    public URL getRemoteByName(String name, String browser) {
        URL remote = null;
        String url = getDeviceByName(name).getRemote();
        if(browser.equals("native")){
            url = url.replace("9515", "4444/wd/hub/");
        }
        try {
            remote = new URL(url);
        } catch (MalformedURLException e) {
            AbstractTest.failTest("Remote URL for '" + name + "' is empty or incorrect.");
        }
        return remote;
    }

    public Device[] getDevices() {
        return devices;
    }

    public void setDevices(Device[] devices) {
        this.devices = devices;
    }
}
