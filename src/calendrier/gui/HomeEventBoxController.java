/* @@author A0126288X */
package calendrier.gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import utils.Event;

public class HomeEventBoxController extends StackPane {

	@FXML
	private Label lblHomeEventTitle;

	private static final String SINGLE_DATED_EVENT_LAYOUT_FXML = "/calendrier/resources/HomeEventBox.fxml";

	public HomeEventBoxController(Event event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(SINGLE_DATED_EVENT_LAYOUT_FXML));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		initEventValue(event);
	}

	private void initEventValue(Event event) {
		lblHomeEventTitle.setText(event.getTitle());
	}

//	private void changeBorderColor(String priority) {
//		if (priority.equalsIgnoreCase(VALUE_VERY_HIGH_PRIORITY)) {
//			datedEventGridPane.setStyle("-fx-border-color: red;");
//		} else if (priority.equalsIgnoreCase(VALUE_HIGH_PRIORITY)) {
//			datedEventGridPane.setStyle("-fx-border-color: #FFA07A;");
//		} else if (priority.equalsIgnoreCase(VALUE_MEDIUM_PRIORITY)) {
//			datedEventGridPane.setStyle("-fx-border-color: #FFFF00;");
//		} else if (priority.equalsIgnoreCase(VALUE_LOW_PRIORITY)) {
//			datedEventGridPane.setStyle("-fx-border-color: #00FF7F;");
//		} else if (priority.equalsIgnoreCase(VALUE_VERY_LOW_PRIORITY)) {
//			datedEventGridPane.setStyle("-fx-border-color: #2E8B57;");
//		} else {
//			datedEventGridPane.setStyle("-fx-border-color: black;");
//		}
//	}
}
