package utils.core;

import springConstructors.*;
import springConstructors.mail.MailService;

public class DataContainer {

    private static AdminUserData    adminUserData;
    private static AffiliateData    affiliateData;
    private static Defaults         defaults;
    private static DeviceData       deviceData;
    private static DriverData       driverData;
    private static IMS              ims;
    private static MailService      mailService;
    private static UserData         userData;
    private static GameData         gameData;

    public static AffiliateData getAffiliateData() {
        return affiliateData;
    }

    public static void setAffiliateData(AffiliateData affiliateData) {
        DataContainer.affiliateData = affiliateData;
    }

    public static AdminUserData getAdminUserData() {
        return adminUserData;
    }

    public static void setAdminUserData(AdminUserData adminUserData) {
        DataContainer.adminUserData = adminUserData;
    }

    public static Defaults getDefaults() {
        return defaults;
    }

    public static void setDefaults(Defaults defaults) {
        DataContainer.defaults = defaults;
    }

    public static DeviceData getDeviceData() {
        return deviceData;
    }

    public static void setDeviceData(DeviceData deviceData) {
        DataContainer.deviceData = deviceData;
    }

    public static DriverData getDriverData() {
        return driverData;
    }

    public static void setDriverData(DriverData driverData) {
        DataContainer.driverData = driverData;
    }

    public static IMS getIms() {
        return ims;
    }

    public static void setIms(IMS ims) {
        DataContainer.ims = ims;
    }

    public static MailService getMailService() {
        return mailService;
    }

    public static void setMailService(MailService mailService) {
        DataContainer.mailService = mailService;
    }

    public static UserData getUserData() {
        return userData;
    }

    public static void setUserData(UserData userData) {
        DataContainer.userData = userData;
    }

    public static GameData getGameData() {
        return gameData;
    }

    public static void setGameData(GameData gameData) {
        DataContainer.gameData = gameData;
    }
}
