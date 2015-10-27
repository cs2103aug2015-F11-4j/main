package calendrier.gui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import utils.Event;

public class EventController extends StackPane {

	private static final String EVENT_BOX_LAYOUT_FXML = "/calendrier/resources/EventBox.fxml";

	public EventController(Event event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(
				EVENT_BOX_LAYOUT_FXML));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
