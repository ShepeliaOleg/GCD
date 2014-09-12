package springConstructors;

import utils.core.WebDriverFactory;

import java.net.MalformedURLException;
import java.net.URL;

public class DeviceData {

    public static Device[] devices;

    public static Device[] getDevices() {
        return devices;
    }

    public static void setDevices(Device[] devices) {
        DeviceData.devices = devices;
    }

    private static Device getDeviceByName(String name){
        for(Device device:devices) {
            if (device.getName().equals(name)) {
                return device;
            }
        }
        throw new RuntimeException("Device \"" + name + "\" is not found.");
    }

    public static String getSerialByName(String name) {
        return getDeviceByName(name).getSerial();
    }

    public static URL getRemoteByName(String name) {
        URL remote = null;
        String url = getDeviceByName(name).getRemote();
        if(WebDriverFactory.browser.equals("native")){
            url = url.replace("9515", "4444/wd/hub/");
        }
        try {
            remote = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Remote URL for \'" + name + "\' device is empty or incorrect.");
        }
        return remote;
    }
}
