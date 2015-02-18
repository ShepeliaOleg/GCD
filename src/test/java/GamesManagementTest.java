

/**
 * Created by serhiist on 2/18/2015.
 */

import enums.ConfiguredPages;
import enums.GameType;
import enums.PlayerCondition;
import org.testng.annotations.Test;
import org.w3c.dom.NodeList;
import pageObjects.admin.AdminPageAdmin;
import pageObjects.admin.settings.AddGamePopup;
import pageObjects.admin.settings.EditGamePopup;
import pageObjects.admin.settings.GamesManagementPopup;
import springConstructors.GameData;
import utils.FileUtils;
import utils.NavigationUtils;
import utils.XMLUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;

import java.util.ArrayList;

public class GamesManagementTest extends AbstractTest {
    @Test(groups = {"admin"})
    public static void addNewGame() {
        GameData gameData = DataContainer.getGameData();
        addGame(gameData);
        assertTrue(verifyGameDataWithDataFromXml(gameData), "New game correctly added to XML");
        deleteGame(gameData.getGameName());
    }

    @Test(groups = {"admin"})
    public static void editGame() {
        GameData gameData = DataContainer.getGameData();
        addGame(gameData);
        String gameName = gameData.getGameName();
        GameData randomGameData = GameData.getRandomGameData();
        editGame(gameName, randomGameData);
        assertTrue(verifyGameDataWithDataFromXml(gameData), "Edited Game data is correctly saved to XML");
        deleteGame(gameName);
    }

    @Test(groups = {"admin"})
    public static void deleteGame() {
        GameData gameData = DataContainer.getGameData();
        String gameName = gameData.getGameName();
        addGame(gameData);
        deleteGame(gameName);
        assertTrue(findGameInNodeListByName(XMLUtils.getGamesNodeList(FileUtils.getGamesXMLDocument()), gameName) < 0, "Game is deleted from XML");
    }

    private static void addGame(GameData gameData) {
        AdminPageAdmin adminPageAdmin = (AdminPageAdmin) NavigationUtils.navigateToPage(PlayerCondition.admin, ConfiguredPages.admin);
        AddGamePopup addGamePopup = adminPageAdmin.addGame();
        addGamePopup.addNewGame(gameData);
        addGamePopup.clickSaveButton();
        System.out.println("Game with Game Code = " + gameData.getGameCode() + " saved in XML");
    }

    private static void editGame(String editedGameName, GameData gameData) {
        AdminPageAdmin adminPageAdmin = (AdminPageAdmin) NavigationUtils.navigateToPage(PlayerCondition.admin, ConfiguredPages.admin);
        EditGamePopup editGamePopup = adminPageAdmin.openGamesManagement().editGamePopup(editedGameName);
        editGamePopup.editGame(gameData);
        gameData.setGameCode(editGamePopup.getGameCodeOnEditPage());
        editGamePopup.clickSaveButton();
    }

    private static void deleteGame(String gameName) {
        AdminPageAdmin adminPageAdmin = (AdminPageAdmin) NavigationUtils.navigateToPage(PlayerCondition.admin, ConfiguredPages.admin);
        GamesManagementPopup gamesManagementPopup = adminPageAdmin.openGamesManagement();
        gamesManagementPopup.deleteGame(gameName);
    }

    /**
     * @return -1 if game with specified game code is not found in NodeList
     */
    public static Integer findGameInNodeListByGameCode(NodeList gameList, String gameCode) {
        for (int i = 0; i < gameList.getLength(); i++) {
            if (gameCode.equals(XMLUtils.getNodeValuesByName(gameList.item(i).getChildNodes(), "gameCode").get(0)))
                return i;
        }
        return -1;
    }

    /**
     * @return -1 if game with specified game code is not found in NodeList
     */
    public static Integer findGameInNodeListByName(NodeList gameList, String name) {
        for (int i = 0; i < gameList.getLength(); i++) {
            if (name.equals(XMLUtils.getNodeValuesByName(gameList.item(i).getChildNodes(), "name").get(0)))
                return i;
        }
        return -1;
    }

    /**
     * @param expectedGameData
     * @return true if expectedGameData equals to actual game data from xml
     */
    public static boolean verifyGameDataWithDataFromXml(GameData expectedGameData) {
        boolean isDataEquals = true;
        String expectedGameCode = expectedGameData.getGameCode();
        /*get node list with games*/
        NodeList gameList = XMLUtils.getGamesNodeList(FileUtils.getGamesXMLDocument());
        /*get position of game for comparison in node list*/
        Integer gamePositionInXmlFile = findGameInNodeListByGameCode(gameList, expectedGameCode);
        /*checking if game is present in node list*/
        if (gamePositionInXmlFile < 0)
            AbstractTest.failTest("No game with gameCode = " + expectedGameCode + " in XML file " + FileUtils.GAMES_CONFIGURATION_XML_PATH);
        /*get field names which should compare for defined game type*/
        ArrayList<String> fieldsForComparison = GameData.gameFieldsSavedInXml(GameType.getGameTypeEnum(expectedGameData.getGameType()));
        for (String field : fieldsForComparison) {
            ArrayList<String> nodeValues = XMLUtils.getNodeValuesByName(gameList.item(gamePositionInXmlFile).getChildNodes(), field);
            for (int i = 0; i < nodeValues.size(); i++) {
                switch (field) {
                    case "gameType":
                        isDataEquals = !AbstractTest.assertEquals(expectedGameData.getGameType(), GameType.getAdminValueForDefinedXmlValue(nodeValues.get(i)), "Wrong Game Type saved in XML") && isDataEquals;
                        break;
                    case "gameCode":
                        isDataEquals = !AbstractTest.assertEquals(expectedGameData.getGameCode(), nodeValues.get(i), "Wrong Game Code saved in XML") && isDataEquals;
                        break;
                    case "mobileGameType":
                        isDataEquals = !AbstractTest.assertEquals(expectedGameData.getMobileGameType(), nodeValues.get(i), "Wrong Mobile Game Type saved in XML") && isDataEquals;
                        break;
                    case "mobileGameCode":
                        /*this field is not saved to XML*/
                        break;
                    case "name":
                        isDataEquals = !AbstractTest.assertEquals(expectedGameData.getGameName(), nodeValues.get(i), "Wrong Game name saved in XML") && isDataEquals;
                        break;
                    case "articleId":
                        isDataEquals = !AbstractTest.assertEquals(expectedGameData.getArticleId(), nodeValues.get(i), "Wrong Article ID saved in XML") && isDataEquals;
                        break;
                    case "description":
                        isDataEquals = !AbstractTest.assertEquals(expectedGameData.getGameDescription(), nodeValues.get(i), "Wrong Game Description saved in XML") && isDataEquals;
                        break;
                    case "imageURL":
                        isDataEquals = !AbstractTest.assertEquals(expectedGameData.getMainImage(), nodeValues.get(i), "Wrong Main Image saved in XML") && isDataEquals;
                        break;
                    case "mobileImageURL":
                        isDataEquals = !AbstractTest.assertEquals(expectedGameData.getMobileImage(), nodeValues.get(i), "Wrong Mobile Image saved in XML") && isDataEquals;
                        break;
                    case "jackpotCode":
                        isDataEquals = !AbstractTest.assertEquals(expectedGameData.getJackpotCode(), nodeValues.get(i), "Wrong Jackpot Code saved in XML") && isDataEquals;
                        break;
                    case "bingoCategory":
                        isDataEquals = !AbstractTest.assertEquals(expectedGameData.getBingoCategory(), nodeValues.get(i), "Wrong Bingo Category saved in XML") && isDataEquals;
                        break;
                    case "vfGameType":
                        isDataEquals = !AbstractTest.assertEquals(expectedGameData.getVfGameType(), nodeValues.get(i), "Wrong VF Game Type saved in XML") && isDataEquals;
                        break;
                    case "smallImage":
                        isDataEquals = !AbstractTest.assertEquals(expectedGameData.getSmallImage(), nodeValues.get(i), "Wrong Small Image saved in XML") && isDataEquals;
                        break;
                    case "mobileArticleId":
                        /*this field is not saved to XML*/
                        break;
                    case "tag":
                        switch (nodeValues.get(i)) {
                            case "new":
                                isDataEquals = !AbstractTest.assertEquals(expectedGameData.getNewHighlight(), "true", "Wrong New Highlight saved in XML") && isDataEquals;
                                break;
                            case "popular":
                                isDataEquals = !AbstractTest.assertEquals(expectedGameData.getPopular(), "true", "Wrong Popular saved in XML") && isDataEquals;
                                break;
                            case "active":
                                isDataEquals = !AbstractTest.assertEquals(expectedGameData.getActive(), "true", "Wrong Activity status saved in XML") && isDataEquals;
                                break;
                        }
                        break;
                    case "mode":
                        if (nodeValues.size() == 2) {
                            isDataEquals = !AbstractTest.assertEquals(expectedGameData.getFunSupport(), "true", "Wrong Fun Support saved in XML") && isDataEquals;
                            break;
                        } else {
                            isDataEquals = !AbstractTest.assertEquals(expectedGameData.getFunSupport(), "false", "Wrong Fun Support saved in XML") && isDataEquals;
                        }
                        break;
                }
            }
        }
        return isDataEquals;
    }
}

