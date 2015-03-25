package springConstructors;

import utils.core.AbstractTest;

import java.net.MalformedURLException;
import java.net.URL;

public class DeviceData {

    public Device[] devices;

    public Device getDeviceById(String id){
        for(Device device:devices) {
            if (device.getId().equals(id)) {
                return device;
            }
        }
        throw new RuntimeException("Device with id: '" + id + "' is not found.");
    }

    public String getSerialByName(String name) {
        return getDeviceById(name).getSerial();
    }

    public URL getRemoteByName(String name, String browser) {
        URL remote = null;
        String url = getDeviceById(name).getRemote();
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
