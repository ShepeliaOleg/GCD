package pageObjects.admin.settings;

import enums.GameType;
import springConstructors.GameData;

/**
 * Created by serhiist on 2/18/2015.
 */
public class EditGamePopup extends GameManagementPopup {
    public void fillFieldsForEditGame(GameData gameData){
        fillAllFieldsForGameType(GameType.getGameTypeEnum(gameData.getGameType()), gameData, "edit");
    }

    public void editGame(GameData gameData) {
        fillFieldsForEditGame(gameData);
    }
}