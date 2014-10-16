import enums.ConfiguredPages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.account.AddCardPage;
import pageObjects.cashier.deposit.DepositPage;
import pageObjects.cashier.withdraw.WithdrawPage;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.RandomUtils;
import utils.core.AbstractTest;

public class CashierAddCardTest extends AbstractTest{

    @Autowired
    @Qualifier("userData")
    private UserData defaultUserData;

    @Test(groups = {"regression", "mobile"})
    public void checkboxFillDataIsUncheckedByDefault(){
        PortalUtils.loginUser(defaultUserData.getRegisteredUserData());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        AddCardPage addCardPage = depositPage.clickAddCard();
        assertEquals(false, addCardPage.getCheckboxFillUserData(), "Checkbox state");
    }

    /*all fields are empty by default*/
    @Test(groups = {"regression", "mobile"})
    public void checkboxFieldsEmptyByDefault(){
        PortalUtils.loginUser(defaultUserData.getRegisteredUserData());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        AddCardPage addCardPage = depositPage.clickAddCard();
        addCardPage.assertUserDataIsEmpty();
    }


    /*check FillUserdataCheckbox - data is correct, uncheck - data is deleted, overwrite - overwritten*/
    @Test(groups = {"regression", "mobile"})
    public void checkboxFillData(){
        UserData userData = defaultUserData.getRegisteredUserData();
        PortalUtils.loginUser(userData);
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        AddCardPage addCardPage = depositPage.clickAddCard();
        addCardPage.setCheckboxFillUserData(true);
        addCardPage.assertUserData(userData);
        addCardPage.setCheckboxFillUserData(false);
        addCardPage.assertUserDataIsEmpty();
        addCardPage.fillData(userData);
        addCardPage.assertUserData(userData);
    }

    /*invalid card is not added, correct message*/
    @Test(groups = {"regression", "mobile"})
    public void addInvalidCard(){
        UserData userData = defaultUserData.getRegisteredUserData();
        PortalUtils.loginUser(userData);
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        AddCardPage addCardPage = depositPage.clickAddCard();
        addCardPage = addCardPage.addInvalidCard();
        addCardPage.assertInvalidMessage();
        depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.isCardPresent(AddCardPage.INVALID_CARD);
    }

    /*valid but unsupported card is not added, correct message*/
    @Test(groups = {"regression", "mobile"})
    public void addUnsupportedCard(){
        UserData userData = defaultUserData.getRegisteredUserData();
        PortalUtils.loginUser(userData);
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        AddCardPage addCardPage = depositPage.clickAddCard();
        addCardPage = addCardPage.addUnsupportedCard();
        addCardPage.assertUnsupportedMessage();
        depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.isCardPresent(AddCardPage.UNSUPPORTED_CARD);
    }

    /*already used by same user card is not added, correct message*/
    @Test(groups = {"regression", "mobile"})
    public void addAlreadyUsedCard(){
        String card = RandomUtils.getValidCardNumber();
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        AddCardPage addCardPage = depositPage.clickAddCard();
        addCardPage.addValidCard(card);
        depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        addCardPage = depositPage.clickAddCard();
        addCardPage.addCard(card);
        addCardPage.assertCardUsedMessage();
    }

    /*already used by other user is not added, correct message*/
    @Test(groups = {"regression", "mobile"})
    public void addAlreadyUsedByOtherPlayerCard(){
        String card = RandomUtils.getValidCardNumber();
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        AddCardPage addCardPage = depositPage.clickAddCard();
        addCardPage.addValidCard(card);
        PortalUtils.logout();
        userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        addCardPage = depositPage.clickAddCard();
        addCardPage.addCard(card);
        addCardPage.assertCardUsedMessage();
    }

    /*valid card is added, correct message*/
    @Test(groups = {"regression", "mobile"})
    public void addValidCardFromWithdraw(){
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        AddCardPage addCardPage = withdrawPage.clickAddCard();
        String card = RandomUtils.getValidCardNumber();
        addCardPage.addValidCard(card);
        withdrawPage = new WithdrawPage();
        withdrawPage.isCardPresent(card);
    }

    /*valid card is added, correct message*/
    @Test(groups = {"regression", "mobile"})
    public void addValidCardFromDeposit(){
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        AddCardPage addCardPage = depositPage.clickAddCard();
        String card = RandomUtils.getValidCardNumber();
        addCardPage.addValidCard(card);
        depositPage = new DepositPage();
        depositPage.isCardPresent(card);
    }


}
