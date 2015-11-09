/* @@author A0126421U */
package calendrier.gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import utils.Event;
import utils.Priority;

public class HomeEventBoxController extends StackPane {

	@FXML
	private Label lblHomeEventTitle;
	@FXML
	private Label lblHomeEventID;
	@FXML
	private GridPane homeEventGridPane;

	private static final String SINGLE_DATED_EVENT_LAYOUT_FXML = "/calendrier/resources/HomeEventBox.fxml";

	/**
	 * @@author A0126421U
	 * 
	 *          Constructor to initialize the main components of homeEventBox
	 * 
	 * @param event
	 *            - event to be display
	 * 
	 */
	public HomeEventBoxController(Event event, int id) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(SINGLE_DATED_EVENT_LAYOUT_FXML));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		initEventValue(event, id);
	}

	/**
	 * @@author A0126421U
	 * 
	 *          Set the detail in the homeEventBox
	 * 
	 * @param event
	 *            - the event to be show
	 * 
	 */
	private void initEventValue(Event event, int id) {
		lblHomeEventID.setText(String.format("%d.", id));
		lblHomeEventTitle.setText(event.getTitle());
		if (event.isDone()) {
			changeTextDecoration(lblHomeEventTitle);
		}
		changeBorderColor(event.getPriority());
	}

	/**
	 * @@author A0126421U
	 * 
	 *          Strike through the event if is done
	 * 
	 * @param lblEvent
	 *            - the layout to be modified
	 * 
	 */
	private void changeTextDecoration(Label lblEvent) {
		lblEvent.getStyleClass().remove(lblEvent.styleProperty());
		lblEvent.getStyleClass().add("lblEventStrikeThrough");
	}

	/**
	 * @@author A0126421U
	 * 
	 *          Change the text color based on priority
	 * 
	 * @param priority
	 *            - the priority of the current event
	 * 
	 */
	private void changeBorderColor(Priority priority) {
		if (priority == Priority.VERY_HIGH) {
			homeEventGridPane.setStyle("-fx-border-color: red;");
		} else if (priority == Priority.HIGH) {
			homeEventGridPane.setStyle("-fx-border-color: #FFA07A;");
		} else if (priority == Priority.MEDIUM) {
			homeEventGridPane.setStyle("-fx-border-color: #FFFF00;");
		} else if (priority == Priority.LOW) {
			homeEventGridPane.setStyle("-fx-border-color: #00FF7F;");
		} else if (priority == Priority.VERY_LOW) {
			homeEventGridPane.setStyle("-fx-border-color: #2E8B57;");
		} else {
			homeEventGridPane.setStyle("-fx-border-color: black;");
		}
	}
}
