package springConstructors;

public class AdminUserData {

    private String adminUsername;
    private String defaultAdminUsername;
    private String adminPassword;
    private String defaultAdminPassword;

    public String getAdminUsername() {
        if(adminUsername!=null){
            return adminUsername;
        }else {
            return getDefaultAdminUsername();
        }
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public String getDefaultAdminUsername() {
        return defaultAdminUsername;
    }

    public void setDefaultAdminUsername(String defaultAdminUsername) {
        this.defaultAdminUsername = defaultAdminUsername;
    }

    public String getAdminPassword() {
        if(adminPassword!=null){
            return adminPassword;
        }else {
            return getDefaultAdminPassword();
        }
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getDefaultAdminPassword() {
        return defaultAdminPassword;
    }

    public void setDefaultAdminPassword(String defaultAdminPassword) {
        this.defaultAdminPassword = defaultAdminPassword;
    }


}
