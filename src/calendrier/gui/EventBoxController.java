package calendrier.gui;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;

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

	private static final int VALUE_EMPTY_SIZE = 0;
	private static final int VALUE_GET_INDEX = 0;

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
		Calendar cal = Calendar.getInstance();
		IdMapper idMapper = IdMapper.getInstance();
		idMapper.set(Integer.toString(position), checkExistValue(event.getId()));
		lblEventID.setText(Integer.toString(position));
//		lblEventID.setText(checkExistValue(event.getId()));
		lblEventTitle.setText(checkExistValue(event.getTitle()));
		lblEventDate.setText(constructEventDate(event.getStartDateTime(), event.getEndDateTime()));

		String strPriority = checkExistPriority(event.getPriority());
		if (!strPriority.equalsIgnoreCase(VALUE_SHOW_EMPTY_DATA)) {
			if ((cal.compareTo(event.getStartDateTime())<0)
					|| (cal.compareTo(event.getEndDateTime())<0)) {
				changeBorderColor(event.getPriority());
			}
			else{
				eventGridPane.setStyle("-fx-border-color: gray;");
			}
		}

		Image img;
		if (checkExistValue(event.getGroups().toString()).equalsIgnoreCase(VALUE_SHOW_EMPTY_DATA)) {
			img = new Image(DEFAULT_EVENT_TYPE_IMAGE);
		} else {
			String strGroup = convertGroupToString(event.getGroups());
			String strImage = getGrpImage(strGroup);
			img = new Image(strImage);

		}
		imgType.setImage(img);

	}

	public String checkExistValue(String parseInValue) {
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
		} else
			if (!startDate.equalsIgnoreCase(VALUE_SHOW_EMPTY_DATA) && endDate.equalsIgnoreCase(VALUE_SHOW_EMPTY_DATA)) {
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

	private static String convertGroupToString(Collection<String> groups) {
		String strGrp = "";
		if (groups.size() != VALUE_EMPTY_SIZE) {
			for (String str : groups) {
				strGrp += str + ", ";
			}
		}

		if (strGrp.equalsIgnoreCase("null, ")) {
			return VALUE_SHOW_EMPTY_DATA;
		}

		return strGrp;
	}

	private static String getGrpImage(String strGrp) {
		String[] groups = strGrp.split(",");
		String mainGrp = groups[VALUE_GET_INDEX];

		if (mainGrp.equalsIgnoreCase("report")) {
			return REPORT_EVENT_TYPE_IMAGE;
		} else if (mainGrp.equalsIgnoreCase("dinner")) {
			return DINNER_EVENT_TYPE_IMAGE;
		} else if (mainGrp.equalsIgnoreCase("meeting")) {
			return MEETING_EVENT_TYPE_IMAGE;
		} else if (mainGrp.equalsIgnoreCase("reading")) {
			return READING_EVENT_TYPE_IMAGE;
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

	private void changeBorderColor(Priority priority) {
		if (priority == Priority.VERY_HIGH) {
			eventGridPane.setStyle("-fx-border-color: red;");
		} else if (priority == Priority.HIGH) {
			eventGridPane.setStyle("-fx-border-color: #FFA07A;");
		} else if (priority == Priority.MEDIUM) {
			eventGridPane.setStyle("-fx-border-color: #FFFF00;");
		} else if (priority == Priority.LOW) {
			eventGridPane.setStyle("-fx-border-color: #00FF7F;");
		} else if (priority == Priority.VERY_LOW) {
			eventGridPane.setStyle("-fx-border-color: #2E8B57;");
		} else {
			eventGridPane.setStyle("-fx-border-color: black;");
		}
	}
}
