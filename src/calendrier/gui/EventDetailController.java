package calendrier.gui;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import utils.Event;
import utils.Priority;
import utils.Recurrence;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class EventDetailController extends StackPane {

	@FXML
	private GridPane gridPaneDetail;
	@FXML
	private ImageView imgType;
	@FXML
	private Label lblID;
	@FXML
	private Label lblTitle;
	@FXML
	private Label lblDate;
	@FXML
	private Label lblLocation;
	@FXML
	private Label lblReminder;
	@FXML
	private Label lblNotes;
	@FXML
	private Label lblPriority;
	@FXML
	private Label lblGroup;
	@FXML
	private Label lblRecurrence;

	private static final String EVENT_DETAIL_LAYOUT_FXML = "/calendrier/resources/ViewEventDetail.fxml";

	private static final String DEFAULT_EVENT_TYPE_IMAGE = "/calendrier/resources/information.png";
	private static final String REPORT_EVENT_TYPE_IMAGE = "/calendrier/resources/report.png";
	private static final String DINNER_EVENT_TYPE_IMAGE = "/calendrier/resources/dinner.png.";
	private static final String READING_EVENT_TYPE_IMAGE = "/calendrier/resources/reading.png";
	private static final String MEETING_EVENT_TYPE_IMAGE = "/calendrier/resources/meeting.png";
	private static final String BIRTHDAY_EVENT_TYPE_IMAGE = "/calendrier/rosources/birthday.png";
	
	private static final String VALUE_GROUP_DEADLINE = "deadline";
	private static final String VALUE_GROUP_MEETING = "meeting";
	private static final String VALUE_GROUP_MEAL = "meal";
	private static final String VALUE_GROUP_BIRTHDAY = "birthday";
	private static final String VALUE_GROUP_READING = "reading";
	
	private static final int VALUE_EMPTY_SIZE = 0;

	private static final String VALUE_SHOW_EMPTY_DATA = "-";
	private static final String VALUE_SHOW_NULL = "null, ";
	private static final String VALUE_ADD_COMMA = ", ";

	public EventDetailController(Event event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(EVENT_DETAIL_LAYOUT_FXML));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		initEventValue(event);
	}

	public void initEventValue(Event event) {
		// lblID.setText(checkExistValue(event.getId()));
		lblTitle.setText(checkExistValue(event.getTitle()));
		lblDate.setText(constructEventDate(event.getStartDateTime(), event.getEndDateTime()));
		lblLocation.setText(checkExistValue(event.getLocation()));
		lblReminder.setText(checkExistReminder(event.getReminder()));
		lblNotes.setText(checkExistValue(event.getNotes()));
		lblPriority.setText(checkExistPriority(event.getPriority()));
		lblRecurrence.setText(checkExistRecurrence(event.getRecurrence()));

		if (!lblPriority.getText().equalsIgnoreCase(VALUE_SHOW_EMPTY_DATA)) {
			changeBorderColor(event.getPriority());
		}

		Image img;
		if (checkExistValue(event.getGroup()).equalsIgnoreCase(VALUE_SHOW_EMPTY_DATA)) {
			lblGroup.setText(VALUE_SHOW_EMPTY_DATA);
			img = new Image(DEFAULT_EVENT_TYPE_IMAGE);
		} else {
			String strGroup = event.getGroup();
			lblGroup.setText(strGroup);
			String strImage = getGrpImage(strGroup);
			img = new Image(strImage);
		}
		imgType.setImage(img);
	}

	private static String checkExistReminder(List<Calendar> reminders) {
		String strReminder = "";
		if (reminders.size() != VALUE_EMPTY_SIZE) {
			for (int i = 0; i < reminders.size(); i++) {
				strReminder += reminders.get(i).getTime().toString() + VALUE_ADD_COMMA;
			}
		}

		if (strReminder.equalsIgnoreCase(VALUE_SHOW_NULL)) {
			return VALUE_SHOW_EMPTY_DATA;
		}

		return strReminder;
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

	private static String checkExistRecurrence(Recurrence recurrence) {
		try {
			return recurrence.toString();
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

	private void changeBorderColor(Priority priority) {
		if (priority == Priority.VERY_HIGH) {
			gridPaneDetail.setStyle("-fx-border-color: red;");
		} else if (priority == Priority.HIGH) {
			gridPaneDetail.setStyle("-fx-border-color: #FFA07A;");
		} else if (priority == Priority.MEDIUM) {
			gridPaneDetail.setStyle("-fx-border-color: #FFFF00;");
		} else if (priority == Priority.LOW) {
			gridPaneDetail.setStyle("-fx-border-color: #00FF7F;");
		} else if (priority == Priority.VERY_LOW) {
			gridPaneDetail.setStyle("-fx-border-color: #2E8B57;");
		} else {
			gridPaneDetail.setStyle("-fx-border-color: black;");
		}
	}

	private static String checkExistValue(String parseInValue) {
		try {
			return parseInValue;
		} catch (NullPointerException e) {
			return VALUE_SHOW_EMPTY_DATA;
		}
	}

	private static String checkExistDate(Calendar calendar) {
		try {
			return calendar.getTime().toString();
		} catch (NullPointerException e) {
			return VALUE_SHOW_EMPTY_DATA;
		}
	}

	private static String getGrpImage(String strGrp) {
		if (strGrp.equalsIgnoreCase(VALUE_GROUP_DEADLINE)) {
			return REPORT_EVENT_TYPE_IMAGE;
		} else if (strGrp.equalsIgnoreCase(VALUE_GROUP_MEAL)) {
			return DINNER_EVENT_TYPE_IMAGE;
		} else if (strGrp.equalsIgnoreCase(VALUE_GROUP_MEETING)) {
			return MEETING_EVENT_TYPE_IMAGE;
		} else if (strGrp.equalsIgnoreCase(VALUE_GROUP_READING)) {
			return READING_EVENT_TYPE_IMAGE;
		} else if (strGrp.equalsIgnoreCase(VALUE_GROUP_BIRTHDAY)) {
			return BIRTHDAY_EVENT_TYPE_IMAGE;
		} else {
			return DEFAULT_EVENT_TYPE_IMAGE;
		}
	}
}
