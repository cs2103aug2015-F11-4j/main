/* @@author A0126288X */
package calendrier.gui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class NoEventController extends BorderPane{

	private static final String NO_EVENT_LAYOUT_FXML = "/calendrier/resources/NoEvent.fxml";
	
	private UserInterface userInterface;
	
	public NoEventController(UserInterface userInterface) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(NO_EVENT_LAYOUT_FXML));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.userInterface = userInterface;
	}
}
