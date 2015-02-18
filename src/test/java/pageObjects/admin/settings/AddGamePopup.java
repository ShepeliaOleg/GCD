package pageObjects.admin.settings;

import enums.GameType;
import springConstructors.GameData;

/**
 * Created by serhiist on 2/18/2015.
 */
public class AddGamePopup extends GameManagementPopup {
    public void fillFieldsForNewGame(GameData gameData){
        fillAllFieldsForGameType(GameType.getGameTypeEnum(gameData.getGameType()), gameData, "new");
    }

    public void addNewGame(GameData gameData){
        fillFieldsForNewGame(gameData);
    }
}
