/* @@author A0126288X */
package calendrier.gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class StartScreenController extends StackPane {

	private static final String START_SCREEN_1_LAYOUT_FXML = "/calendrier/resources/StartScreen.fxml";
	private static final String START_SCREEN_2_LAYOUT_FXML = "/calendrier/resources/StartScreen2.fxml";
	private static final String START_SCREEN_3_LAYOUT_FXML = "/calendrier/resources/StartScreen3.fxml";
	private static final String START_SCREEN_4_LAYOUT_FXML = "/calendrier/resources/StartScreen4.fxml";
	private static final String START_SCREEN_5_LAYOUT_FXML = "/calendrier/resources/StartScreen5.fxml";
	
	private UserInterface userInterface;
	
	public StartScreenController(UserInterface userInterface, int screenNum) {
		FXMLLoader loader = getLoader(screenNum);
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.userInterface = userInterface;
	}
	
	private FXMLLoader getLoader(int screenNum) {
	
		switch(screenNum) {
			case 1:
				return new FXMLLoader(getClass().getResource(START_SCREEN_1_LAYOUT_FXML));
			case 2:
				return new FXMLLoader(getClass().getResource(START_SCREEN_2_LAYOUT_FXML));
			case 3:
				return new FXMLLoader(getClass().getResource(START_SCREEN_3_LAYOUT_FXML));
			case 4:
				return new FXMLLoader(getClass().getResource(START_SCREEN_4_LAYOUT_FXML));
			case 5:
				return new FXMLLoader(getClass().getResource(START_SCREEN_5_LAYOUT_FXML));
			default:
				return new FXMLLoader(getClass().getResource(START_SCREEN_1_LAYOUT_FXML));
		}
	}
	

}
