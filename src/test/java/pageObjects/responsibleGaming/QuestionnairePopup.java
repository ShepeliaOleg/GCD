package pageObjects.responsibleGaming;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.core.AbstractPortalPopup;
import utils.WebDriverUtils;
import java.util.List;

public class QuestionnairePopup extends AbstractPortalPopup{
	public static final String BUTTON_SUBMIT_XP=	ROOT_XP + "//*[@class='submit']";
	private static final String QUESTION_CLASSNAME=	"row fn-valid-check";
	private static final String ANSWER_CLASSNAME=	"button-side";


	public QuestionnairePopup(){
		super(new String[]{BUTTON_SUBMIT_XP});
	}

//    public QuestionnairePopup(WebDriver webDriver, By id) {
//        super(webDriver, id);
//    }
//
//    public static QuestionnairePopup getInstance(WebDriver webDriver) {
//            if (isVisible(webDriver)) {
//                return new QuestionnairePopup(webDriver, By.id(MESSAGE_POPUP_ID));
//            } else {
//                return null;
//            }
//    }

	private void submitQuestioneer(){
		WebDriverUtils.click(BUTTON_SUBMIT_XP);
	}

	private void answerQuestioneer(){
		List<WebElement> buttonsAnswer= WebDriverUtils.getElementsByClassName(ANSWER_CLASSNAME);
		for(int i=0; i < buttonsAnswer.size(); i++){
			if((i % 2) == 0)
				buttonsAnswer.get(i).click();
		}
	}

	public QuestionnaireConfirmPopup submit(){
		answerQuestioneer();
		submitQuestioneer();
		return new QuestionnaireConfirmPopup();
	}
}
