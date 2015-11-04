package calendrier.gui;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import utils.Event;
import utils.IdMapper;
import utils.Priority;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class EventBoxController extends StackPane {

	@FXML
	private GridPane eventGridPane;
	@FXML
	private ImageView imgType;
	@FXML
	private Label lblEventID;
	@FXML
	private Label lblEventTitle;
	@FXML
	private Label lblEventDate;

	private static final String SINGLE_EVENT_LAYOUT_FXML = "/calendrier/resources/EventBox.fxml";

	private static final String DEFAULT_EVENT_TYPE_IMAGE = "/calendrier/resources/information.png";
	private static final String REPORT_EVENT_TYPE_IMAGE = "/calendrier/resources/report.png";
	private static final String DINNER_EVENT_TYPE_IMAGE = "/calendrier/resources/dinner.png.";
	private static final String READING_EVENT_TYPE_IMAGE = "/calendrier/resources/reading.png";
	private static final String MEETING_EVENT_TYPE_IMAGE = "/calendrier/resources/meeting.png";
	private static final String BIRTHDAY_EVENT_TYPE_IMAGE = "/calendrier/resources/birthday.png";

	private static final String VALUE_GROUP_DEADLINE = "deadline";
	private static final String VALUE_GROUP_MEETING = "meeting";
	private static final String VALUE_GROUP_MEAL = "meal";
	private static final String VALUE_GROUP_BIRTHDAY = "birthday";
	private static final String VALUE_GROUP_READING = "reading";

	private static final String VALUE_VERY_HIGH_PRIORITY = "very_high";
	private static final String VALUE_HIGH_PRIORITY = "high";
	private static final String VALUE_MEDIUM_PRIORITY = "medium";
	private static final String VALUE_LOW_PRIORITY = "low";
	private static final String VALUE_VERY_LOW_PRIORITY = "very_low";

	private static final String VALUE_SHOW_EMPTY_DATA = "-";
	private static DateFormat dateFormat = new SimpleDateFormat("EEE dd/MM/yy HH:mm");

	public EventBoxController(Event event, int position) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(SINGLE_EVENT_LAYOUT_FXML));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		initEventValue(event, position);
	}

	public void initEventValue(Event event, int position) {
		// Calendar cal = Calendar.getInstance();
		IdMapper idMapper = IdMapper.getInstance();
		idMapper.set(Integer.toString(position), checkExistValue(event.getId()));
		lblEventID.setText(Integer.toString(position));
		lblEventTitle.setText(checkExistValue(event.getTitle()));
		lblEventDate.setText(constructEventDate(event.getStartDateTime(), event.getEndDateTime()));

		if (event.isDone()) {
			changeEventDesign();
		} else {
			String strPriority = checkExistPriority(event.getPriority());
			// changeBoarderColour(event, cal, strPriority);
			changeBorderColor(strPriority);
		}

		Image img;
		if (checkExistValue(event.getGroup()).equalsIgnoreCase(VALUE_SHOW_EMPTY_DATA)) {
			img = new Image(DEFAULT_EVENT_TYPE_IMAGE);
		} else {
			String strGroup = event.getGroup();
			String strImage = getGrpImage(strGroup);
			img = new Image(strImage);
		}
		imgType.setImage(img);
	}

	// private void changeBoarderColour(Event event, Calendar cal, String
	// strPriority) {
	// if (!strPriority.equalsIgnoreCase(VALUE_SHOW_EMPTY_DATA)) {
	// if ((event.getStartDateTime()!=null &&
	// (cal.compareTo(event.getStartDateTime())<0))
	// || (event.getEndDateTime()!=null &&
	// (cal.compareTo(event.getEndDateTime())<0))) {
	// changeBorderColor(event.getPriority());
	// } else if (event.getStartDateTime()==null &&
	// event.getEndDateTime()==null) {
	// changeBorderColor(event.getPriority());
	// }
	// else{
	// eventGridPane.setStyle("-fx-border-color: lightgray;");
	// }
	// }
	// else{
	// if ((event.getStartDateTime()!=null &&
	// (cal.compareTo(event.getStartDateTime())>0))
	// ||(event.getEndDateTime()!=null &&
	// (cal.compareTo(event.getEndDateTime())>0))) {
	// eventGridPane.setStyle("-fx-border-color: lightgray;");
	// }
	// }
	// }

	public String checkExistValue(String parseInValue) {
		if(parseInValue!=null){
			return parseInValue;
		} else{
			return VALUE_SHOW_EMPTY_DATA;
		}
//		try {
//			return parseInValue;
//		} catch (NullPointerException e) {
//			return VALUE_SHOW_EMPTY_DATA;
//		}
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

	private static String checkExistPriority(Priority priority) {
		try {
			return priority.toString();
		} catch (NullPointerException e) {
			return VALUE_SHOW_EMPTY_DATA;
		}
	}

	// private void changeBorderColor(Priority priority) {
	// if (priority == Priority.VERY_HIGH) {
	// eventGridPane.setStyle("-fx-border-color: red;");
	// } else if (priority == Priority.HIGH) {
	// eventGridPane.setStyle("-fx-border-color: #FFA07A;");
	// } else if (priority == Priority.MEDIUM) {
	// eventGridPane.setStyle("-fx-border-color: #FFFF00;");
	// } else if (priority == Priority.LOW) {
	// eventGridPane.setStyle("-fx-border-color: #00FF7F;");
	// } else if (priority == Priority.VERY_LOW) {
	// eventGridPane.setStyle("-fx-border-color: #2E8B57;");
	// } else {
	// eventGridPane.setStyle("-fx-border-color: black;");
	// }
	// }

	private void changeBorderColor(String priority) {
		if (priority.equalsIgnoreCase(VALUE_VERY_HIGH_PRIORITY)) {
			eventGridPane.setStyle("-fx-border-color: red;");
		} else if (priority.equalsIgnoreCase(VALUE_HIGH_PRIORITY)) {
			eventGridPane.setStyle("-fx-border-color: #FFA07A;");
		} else if (priority.equalsIgnoreCase(VALUE_MEDIUM_PRIORITY)) {
			eventGridPane.setStyle("-fx-border-color: #FFFF00;");
		} else if (priority.equalsIgnoreCase(VALUE_LOW_PRIORITY)) {
			eventGridPane.setStyle("-fx-border-color: #00FF7F;");
		} else if (priority.equalsIgnoreCase(VALUE_VERY_LOW_PRIORITY)) {
			eventGridPane.setStyle("-fx-border-color: #2E8B57;");
		} else {
			eventGridPane.setStyle("-fx-border-color: black;");
		}
	}

	private void changeEventDesign() {
		eventGridPane.setStyle("-fx-border-color: lightgray");
		lblEventID.setStyle("-fx-text-decoration: line-through");
		lblEventTitle.setStyle("-fx-text-decoration: line-through");
		lblEventDate.setStyle("-fx-text-decoration: line-through");
	}
}
