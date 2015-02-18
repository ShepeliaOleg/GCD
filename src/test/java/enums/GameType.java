package enums;

/**
 * Created by serhiist on 2/18/2015.
 */

import utils.core.AbstractTest;

public enum GameType {
    Casino ("pt", "CASINO"),
    VFSlotGame("vf-slot", "VF-SLOT"),
    VFRoomGame ("vf-room", "VF-ROOM");

    private String xmlValue;
    private String adminValue;

    GameType(String xmlValue, String adminValue) {
        this.xmlValue = xmlValue;
        this.adminValue = adminValue;
    }


    public String getXmlValue() {
        return xmlValue;
    }

    public String getAdminValue() {
        return adminValue;
    }

    public static String getAdminValueForDefinedXmlValue(String xmlValue){
        String adminValue = null;
        for (GameType gameType : GameType.values()){
            if (gameType.getXmlValue().equals(xmlValue)){
                adminValue = gameType.getAdminValue();
                break;
            } else
                AbstractTest.addError("Wrong Game type saved to XML: actual value = " + xmlValue);
        }
        return adminValue;
    }

    public static GameType getGameTypeEnum (String gameTypeStringValue){
        return Enum.valueOf(GameType.class, gameTypeStringValue.substring(0, 1) + gameTypeStringValue.substring(1).toLowerCase());
    }
}

