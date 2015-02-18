package springConstructors;

import enums.GameType;
import utils.RandomUtils;

import java.util.ArrayList;

/**
 * Created by serhiist on 2/18/2015.
 */
public class GameData {
    String gameType;
    String gameCode;
    String mobileGameCode;
    String mobileGameType;
    String gameName;
    String articleId;
    String mobileArticleId;
    String gameDescription;
    String mainImage;
    String mobileImage;
    String jackpotCode;
    String funSupport;
    String newHighlight;
    String popular;
    String vfGameType;
    String smallImage;
    String bingoCategory;
    String active;

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getBingoCategory() {
        return bingoCategory;
    }

    public void setBingoCategory(String bingoCategory) {
        this.bingoCategory = bingoCategory;
    }

    public String getVfGameType() {
        return vfGameType;
    }

    public void setVfGameType(String vfGameType) {
        this.vfGameType = vfGameType;
    }

    public String getMobileGameCode() {
        return mobileGameCode;
    }

    public void setMobileGameCode(String mobileGameCode) {
        this.mobileGameCode = mobileGameCode;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = RandomUtils.generateString(RandomUtils.lettersAndNumbers, 5);
    }

    public String getMobileGameType() {
        return mobileGameType;
    }

    public void setMobileGameType(String mobileGameType) {
        this.mobileGameType = mobileGameType;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getMobileArticleId() {
        return mobileArticleId;
    }

    public void setMobileArticleId(String mobileArticleId) {
        this.mobileArticleId = mobileArticleId;
    }

    public String getGameDescription() {
        return gameDescription;
    }

    public void setGameDescription(String gameDescription) {
        this.gameDescription = gameDescription;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getMobileImage() {
        return mobileImage;
    }

    public void setMobileImage(String mobileImage) {
        this.mobileImage = mobileImage;
    }

    public String getJackpotCode() {
        return jackpotCode;
    }

    public void setJackpotCode(String jackpotCode) {
        this.jackpotCode = jackpotCode;
    }

    public String getFunSupport() {
        return funSupport;
    }

    public void setFunSupport(String funSupport) {
        this.funSupport = funSupport;
    }

    public String getNewHighlight() {
        return newHighlight;
    }

    public void setNewHighlight(String newHighlight) {
        this.newHighlight = newHighlight;
    }

    public String getPopular() {
        return popular;
    }

    public void setPopular(String popular) {
        this.popular = popular;
    }

    public GameData() {
        this.gameType = "";
        this.gameCode = "";
//        this.mobileGameCode = "";
        this.mobileGameType = "";
        this.gameName = "";
        this.articleId = "";
        this.mobileArticleId = "";
        this.gameDescription = "";
        this.mainImage = "";
        this.mobileImage = "";
        this.jackpotCode = "";
        this.funSupport = "";
        this.newHighlight = "";
        this.popular = "";
        this.vfGameType = "";
        this.smallImage = "";
        this.bingoCategory = "";
        this.active = "";
    }

    public static ArrayList<String> gameFieldsSavedInXml(GameType gameType) {
        ArrayList<String> gameTypeFields = new ArrayList<String>() {{
            add("gameType");
            add("mobileGameType");
            add("name");
            add("articleId");
            add("mobileArticleId");
            add("description");
            add("imageURL");
            add("mobileImageURL");
            add("tag");
            add("mode");
        }};

        switch (gameType) {
            case Casino:
                gameTypeFields.add("gameCode");
//                gameTypeFields.add("mobileGameCode");
                gameTypeFields.add("jackpotCode");
                break;
            case VFSlotGame:
                gameTypeFields.add("gameCode");
//                gameTypeFields.add("mobileGameCode");
                gameTypeFields.add("vfGameType");
                break;
            case VFRoomGame:
                gameTypeFields.add("smallImage");
                gameTypeFields.add("bingoCategory");
                break;
        }
        return gameTypeFields;
    }

    public static GameData getRandomGameData () {
        GameData gameData = new GameData();
        gameData.setGameType("CASINO");
        gameData.setGameCode(RandomUtils.generateString(RandomUtils.lettersAndNumbers, 10));
        gameData.setMobileGameType("BNG");
//        gameData.setMobileGameCode();
        gameData.setGameName(RandomUtils.generateString(RandomUtils.lettersAndNumbers, 10));
        gameData.setArticleId(RandomUtils.generateString(RandomUtils.lettersAndNumbers, 10));
        gameData.setMobileArticleId(RandomUtils.generateString(RandomUtils.lettersAndNumbers, 10));
        gameData.setGameDescription(RandomUtils.generateString(RandomUtils.lettersAndNumbers, 10));
        gameData.setMainImage(RandomUtils.generateString(RandomUtils.lettersAndNumbers, 10));
        gameData.setMobileImage(RandomUtils.generateString(RandomUtils.lettersAndNumbers, 10));
        gameData.setJackpotCode(RandomUtils.generateString(RandomUtils.lettersAndNumbers, 10));
        gameData.setVfGameType(RandomUtils.generateString(RandomUtils.lettersAndNumbers, 10));
        gameData.setSmallImage(RandomUtils.generateString(RandomUtils.lettersAndNumbers, 10));
        gameData.setBingoCategory(RandomUtils.generateString(RandomUtils.lettersAndNumbers, 10));
        gameData.setFunSupport(String.valueOf(RandomUtils.generateRandomBoolean()));
        gameData.setNewHighlight(String.valueOf(RandomUtils.generateRandomBoolean()));
        gameData.setPopular(String.valueOf(RandomUtils.generateRandomBoolean()));
        gameData.setActive(String.valueOf(RandomUtils.generateRandomBoolean()));
        return gameData;
    }
}

