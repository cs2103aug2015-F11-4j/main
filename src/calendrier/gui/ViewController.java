package calendrier.gui;

import java.io.IOException;
import java.util.List;

import utils.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class ViewController extends BorderPane {

	private static final String VIEW_SCREEN_LAYOUT_FXML = "/calendrier/resources/View.fxml";
	
	//private UserInterface userInterface;
	
	public ViewController(List<Event> events) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEW_SCREEN_LAYOUT_FXML));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
//	public void setHand(List<Events> events) {
//		
//	}
}
