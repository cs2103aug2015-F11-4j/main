package calendrier.gui;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import utils.Event;
import utils.IdMapper;
import utils.Priority;

public class DatedEventBoxController extends StackPane {

	@FXML
	private GridPane datedEventGridPane;
	@FXML
	private Label lblDatedEventID;
	@FXML
	private Label lblDatedEventTitle;
	@FXML
	private Label lblDatedEventDate;

	private static final String SINGLE_DATED_EVENT_LAYOUT_FXML = "/calendrier/resources/DatedEventBox.fxml";
	
	private static final String VALUE_VERY_HIGH_PRIORITY = "very_high";
	private static final String VALUE_HIGH_PRIORITY = "high";
	private static final String VALUE_MEDIUM_PRIORITY = "medium";
	private static final String VALUE_LOW_PRIORITY = "low";
	private static final String VALUE_VERY_LOW_PRIORITY = "very_low";

	private static final String VALUE_SHOW_EMPTY_DATA = "-";
	private static DateFormat dateFormat = new SimpleDateFormat("EEE dd/MM/yy HH:mm");

	public DatedEventBoxController(Event event, int position) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(SINGLE_DATED_EVENT_LAYOUT_FXML));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		initEventValue(event, position);
	}

	private void initEventValue(Event event, int position) {
		//Calendar cal = Calendar.getInstance();
		IdMapper idMapper = IdMapper.getInstance();
		idMapper.set(Integer.toString(position), checkExistValue(event.getId()));
		lblDatedEventID.setText(Integer.toString(position));
		lblDatedEventTitle.setText(checkExistValue(event.getTitle()));
		lblDatedEventDate.setText(constructEventDate(event.getStartDateTime(), event.getEndDateTime()));

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

	private static String constructEventDate(Calendar startDateTime, Calendar endDateTime) {

		String startDate = checkExistDate(startDateTime);
		String endDate = checkExistDate(endDateTime);

		if (!startDate.equalsIgnoreCase(VALUE_SHOW_EMPTY_DATA) && !endDate.equalsIgnoreCase(VALUE_SHOW_EMPTY_DATA)) {
			if (startDate.equalsIgnoreCase(endDate)) {
				return startDate;
			}
			return startDate + " - " + endDate;
		} else if (!startDate.equalsIgnoreCase(VALUE_SHOW_EMPTY_DATA)
				&& endDate.equalsIgnoreCase(VALUE_SHOW_EMPTY_DATA)) {
			return startDate;
		} else if (startDate.equalsIgnoreCase(VALUE_SHOW_EMPTY_DATA)
				&& !endDate.equalsIgnoreCase(VALUE_SHOW_EMPTY_DATA)) {
			return endDate;
		} else {
			return VALUE_SHOW_EMPTY_DATA;
		}
	}

	private static String checkExistDate(Calendar calendar) {
		try {
			return dateFormat.format(calendar.getTime());
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
			datedEventGridPane.setStyle("-fx-border-color: red;");
		} else if (priority.equalsIgnoreCase(VALUE_HIGH_PRIORITY)) {
			datedEventGridPane.setStyle("-fx-border-color: #FFA07A;");
		} else if (priority.equalsIgnoreCase(VALUE_MEDIUM_PRIORITY)) {
			datedEventGridPane.setStyle("-fx-border-color: #FFFF00;");
		} else if (priority.equalsIgnoreCase(VALUE_LOW_PRIORITY)) {
			datedEventGridPane.setStyle("-fx-border-color: #00FF7F;");
		} else if (priority.equalsIgnoreCase(VALUE_VERY_LOW_PRIORITY)) {
			datedEventGridPane.setStyle("-fx-border-color: #2E8B57;");
		} else {
			datedEventGridPane.setStyle("-fx-border-color: black;");
		}
	}
}
