package springConstructors;

import enums.Licensee;
import enums.Node;
import utils.core.AbstractTest;

public class DriverData{

    public String   adminNode;
    public String   publicNode;
    public String   cdnNode;
    public Node     node;
    public String   browser;
    public String   os;
    public Licensee licensee;
    public String   deviceId;

    public String getAdminNode() {
        return adminNode;
    }

    public void setAdminNode(String adminNode) {
        this.adminNode = adminNode;
    }

    public String getPublicNode() {
        return publicNode;
    }

    public void setPublicNode(String publicNode) {
        this.publicNode = publicNode;
    }

    public String getCdnNode() {
        return cdnNode;
    }

    public void setCdnNode(String cdnNode) {
        this.cdnNode = cdnNode;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public String getBrowser(){
        return browser;
	}

    public void setBrowser(String browser){
        this.browser=browser;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public Licensee getLicensee() {
        return licensee;
    }

    public void setLicensee(Licensee licensee) {
        this.licensee = licensee;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getCurrentUrl() {
        switch (getNode()) {
            case adminNode:  return getAdminNode();
            case publicNode: return getPublicNode();
            case cdnNode:    return getCdnNode();
            default:
                AbstractTest.failTest("Node is not defined.");
                return null;
        }
    }
}
