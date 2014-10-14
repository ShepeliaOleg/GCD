package pageObjects.cashier.withdraw;

import pageObjects.core.AbstractPopup;

public class WithdrawSuccessfulPopup extends AbstractPopup{

    public static final String ROOT_XP =            "//*[contains(@class, 'popup_type_info ')] | //*[@class='info__content']";

    public WithdrawSuccessfulPopup(){
        super(new String[] {ROOT_XP});
    }
}
