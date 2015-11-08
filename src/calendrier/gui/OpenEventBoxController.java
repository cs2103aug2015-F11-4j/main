/* @@author A0126288X */
package calendrier.gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import utils.Event;
import utils.IdMapper;
import utils.Priority;

public class OpenEventBoxController extends StackPane {

	@FXML
	private GridPane openEventGridPane;
	@FXML
	private Label lblOpenEventID;
	@FXML
	private Label lblOpenEventTitle;

	private static final String SINGLE_DATED_EVENT_LAYOUT_FXML = "/calendrier/resources/OpenEventBox.fxml";

	private static final String VALUE_VERY_HIGH_PRIORITY = "very_high";
	private static final String VALUE_HIGH_PRIORITY = "high";
	private static final String VALUE_MEDIUM_PRIORITY = "medium";
	private static final String VALUE_LOW_PRIORITY = "low";
	private static final String VALUE_VERY_LOW_PRIORITY = "very_low";
	private static final String VALUE_SHOW_EMPTY_DATA = "-";
	
	public OpenEventBoxController(Event event, int position) {
		setLoader();
		initEventValue(event, position);
	}

	private void setLoader() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(SINGLE_DATED_EVENT_LAYOUT_FXML));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void initEventValue(Event event, int position) {
		//Calendar cal = Calendar.getInstance();
		IdMapper idMapper = IdMapper.getInstance();
		idMapper.set(Integer.toString(position), checkExistValue(event.getId()));
		lblOpenEventID.setText(Integer.toString(position));
		lblOpenEventTitle.setText(checkExistValue(event.getTitle()));
		
		String strPriority = checkExistPriority(event.getPriority());
		changeBorderColor(strPriority);
	}
	
	private String checkExistValue(String parseInValue) {
		try {
			return parseInValue;
		} catch (NullPointerException e) {
			return VALUE_SHOW_EMPTY_DATA;
		}
	}
	
	private static String checkExistPriority(Priority priority) {
		try {
			return priority.toString();
		} catch (NullPointerException e) {
			return VALUE_SHOW_EMPTY_DATA;
		}
	}
	
	private void changeBorderColor(String priority) {
		if (priority.equalsIgnoreCase(VALUE_VERY_HIGH_PRIORITY)) {
			openEventGridPane.setStyle("-fx-border-color: red;");
		} else if (priority.equalsIgnoreCase(VALUE_HIGH_PRIORITY)) {
			openEventGridPane.setStyle("-fx-border-color: #FFA07A;");
		} else if (priority.equalsIgnoreCase(VALUE_MEDIUM_PRIORITY)) {
			openEventGridPane.setStyle("-fx-border-color: #FFFF00;");
		} else if (priority.equalsIgnoreCase(VALUE_LOW_PRIORITY)) {
			openEventGridPane.setStyle("-fx-border-color: #00FF7F;");
		} else if (priority.equalsIgnoreCase(VALUE_VERY_LOW_PRIORITY)) {
			openEventGridPane.setStyle("-fx-border-color: #2E8B57;");
		} else {
			openEventGridPane.setStyle("-fx-border-color: black;");
		}
	}
}
