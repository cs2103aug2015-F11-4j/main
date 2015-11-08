/* @@author A0126288X */
package calendrier.gui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import utils.Event;

public class EventController extends StackPane {

	private static final String EVENT_BOX_LAYOUT_FXML = "/calendrier/resources/EventBox.fxml";

	public EventController(Event event) {
		setLoader();
	}

	private void setLoader() {
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
