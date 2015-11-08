/* @@author A0126288X */
package calendrier.gui;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import com.sun.javafx.css.Style;

import utils.Event;
import utils.IdMapper;
import utils.Priority;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
//import javafx.scene.control.Label;

public class EventBoxController extends StackPane {

	@FXML
	private GridPane eventGridPane;
	@FXML
	private ImageView imgType;
	@FXML
	private Text lblEventID;
	@FXML
	private Text lblEventTitle;
	@FXML
	private Text lblEventDate;

	private static final String SINGLE_EVENT_LAYOUT_FXML = "/calendrier/resources/EventBox.fxml";

	private static final String DEFAULT_EVENT_TYPE_IMAGE = "/calendrier/resources/information.png";
	private static final String REPORT_EVENT_TYPE_IMAGE = "/calendrier/resources/report.png";
	private static final String DINNER_EVENT_TYPE_IMAGE = "/calendrier/resources/dinner.png";
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
	private static DateFormat displayDateFormat = new SimpleDateFormat("EEE dd/MM/yy HH:mm");

	private static final Calendar cal = Calendar.getInstance();
	@SuppressWarnings("deprecation")
	private static final int todayDate = cal.getTime().getDate(), todayMonth = cal.getTime().getMonth(),
			todayYear = cal.getTime().getYear() + 1900;
	private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private static final String VALUE_EMPTY_STRING = "";
	private static final String VALUE_SPLIT_REGEX = "/";

	private static final boolean VALUE_TRUE = true;
	private static final boolean VALUE_FALSE = false;

	private static final int PARAM_FOR_DATE = 0;
	private static final int PARAM_FOR_MONTH = 1;
	private static final int PARAM_FOR_YEAR = 2;

	public EventBoxController(Event event, int position) {
		setLoader();
		initEventValue(event, position);
	}

	private void setLoader() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(SINGLE_EVENT_LAYOUT_FXML));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initEventValue(Event event, int position) {
		IdMapper idMapper = IdMapper.getInstance();
		idMapper.set(Integer.toString(position), checkExistValue(event.getId()));
		lblEventID.setText(Integer.toString(position));
		lblEventTitle.setText(checkExistValue(event.getTitle()));
		lblEventDate.setText(constructEventDate(event.getStartDateTime(), event.getEndDateTime()));

		boolean isPast = checkDate(event.getStartDateTime(), event.getEndDateTime());
		checkDoneEvent(event);
		checkPastEvent(event, isPast);
		initImage(event);
	}

	private void initImage(Event event) {
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

	private void checkPastEvent(Event event, boolean isPast) {
		if (isPast) {
			changePastEventDesign();
		} else {
			String strPriority = checkExistPriority(event.getPriority());
			changeBorderColor(strPriority);
		}
	}

	private void checkDoneEvent(Event event) {
		if (event.isDone()) {
			changeTextDecoration();
		}
	}

	public String checkExistValue(String parseInValue) {
		if (parseInValue != null) {
			return parseInValue;
		} else {
			return VALUE_SHOW_EMPTY_DATA;
		}
	}

	private static boolean checkDate(Calendar startDateTime, Calendar endDateTime) {
		// Calendar today = Calendar.getInstance();
		// if (endDateTime != null) {
		// if (endDateTime.before(today)) {
		// return true;
		// }
		// }
		// return false;
		
		String startDate = checkExistDate(startDateTime);
		String endDate = checkExistDate(startDateTime);
		String[] arrStartDate = startDate.split(VALUE_SPLIT_REGEX);
		String[] arrEndDate = endDate.split(VALUE_SPLIT_REGEX);
		return determineDateType(startDate, endDate, arrStartDate, arrEndDate);
	}

	private static boolean determineDateType(String startDate, String endDate, String[] arrStartDate,
			String[] arrEndDate) {
		if (!startDate.equalsIgnoreCase(VALUE_EMPTY_STRING) && !endDate.equalsIgnoreCase(VALUE_EMPTY_STRING)) {

			if (startDate.equalsIgnoreCase(endDate)) {
				return checkBeforeToday(arrStartDate);
			} else {
				// check today is in outside dates
				return checkBetweenToday(arrStartDate, arrEndDate);
			}
		} else if (!startDate.equalsIgnoreCase(VALUE_EMPTY_STRING) && endDate.equalsIgnoreCase(VALUE_EMPTY_STRING)) {
			return checkBeforeToday(arrStartDate);
		} else if (startDate.equalsIgnoreCase(VALUE_EMPTY_STRING) && !endDate.equalsIgnoreCase(VALUE_EMPTY_STRING)) {
			return checkAfterToday(arrEndDate);
		} else {
			return false;
		}
	}

	private static boolean checkBetweenToday(String[] arrStartDate, String[] arrEndDate) {

		int startYear = convertToInteger(arrStartDate[PARAM_FOR_YEAR], VALUE_FALSE);
		int startMonth = convertToInteger(arrStartDate[PARAM_FOR_MONTH], VALUE_TRUE);
		int startDate = convertToInteger(arrStartDate[PARAM_FOR_DATE], VALUE_FALSE);

		int endYear = convertToInteger(arrEndDate[PARAM_FOR_YEAR], VALUE_FALSE);
		int endMonth = convertToInteger(arrEndDate[PARAM_FOR_MONTH], VALUE_TRUE);
		int endDate = convertToInteger(arrEndDate[PARAM_FOR_DATE], VALUE_FALSE);
		
		return isInBtwToday(startYear, startMonth, startDate, endYear, endMonth, endDate);

		// if ((convertToInteger(arrStartDate[PARAM_FOR_DATE], VALUE_FALSE) <
		// todayDate)
		// && (convertToInteger(arrStartDate[PARAM_FOR_MONTH], VALUE_TRUE) <=
		// todayMonth)
		// && (convertToInteger(arrStartDate[PARAM_FOR_YEAR], VALUE_FALSE) <=
		// todayYear)
		// && (convertToInteger(arrEndDate[PARAM_FOR_DATE], VALUE_FALSE) >
		// todayDate)
		// && (convertToInteger(arrEndDate[PARAM_FOR_MONTH], VALUE_TRUE) >=
		// todayMonth)
		// && (convertToInteger(arrEndDate[PARAM_FOR_YEAR], VALUE_FALSE) >=
		// todayYear)) {
		// return false;
		// }
		// return true;
	}

	private static boolean isInBtwToday(int startYear, int startMonth, int startDate, int endYear, int endMonth,
			int endDate) {
		if (startYear <= todayYear && todayYear <= endYear) {
			if (startMonth <= todayMonth && todayMonth <= endMonth) {
				return false;
			} else if (startMonth == todayMonth && todayMonth == endMonth) {
				if (startDate <= todayDate && todayDate <= endDate) {
					return false;
				}
			} else {
				return true;
			}
		} else {
			return true;
		}
		return false;
	}

	private static boolean checkAfterToday(String[] arrEndDate) {

		int currentYear = convertToInteger(arrEndDate[PARAM_FOR_YEAR], VALUE_FALSE);
		int currentMonth = convertToInteger(arrEndDate[PARAM_FOR_MONTH], VALUE_TRUE);
		int currentDate = convertToInteger(arrEndDate[PARAM_FOR_DATE], VALUE_FALSE);
		return isAfterToday(currentYear, currentMonth, currentDate);

		// if ((convertToInteger(arrEndDate[PARAM_FOR_DATE], VALUE_FALSE) >
		// todayDate)
		// && (convertToInteger(arrEndDate[PARAM_FOR_MONTH], VALUE_TRUE) >=
		// todayMonth)
		// && (convertToInteger(arrEndDate[PARAM_FOR_YEAR], VALUE_FALSE) >=
		// todayYear)) {
		// return false;
		// }
		// return true;
	}

	private static boolean isAfterToday(int currentYear, int currentMonth, int currentDate) {
		if (currentYear == todayYear) {
			if (currentMonth > todayMonth) {
				return false;
			} else if (currentMonth == todayMonth) {
				if (currentDate >= todayDate) {
					return false;
				}
				return true;
			} else {
				return true;
			}
		} else if (currentYear > todayYear) {
			return false;
		} else {
			return true;
		}
	}

	private static boolean checkBeforeToday(String[] arrStartDate) {

		int currentYear = convertToInteger(arrStartDate[PARAM_FOR_YEAR], VALUE_FALSE);
		int currentMonth = convertToInteger(arrStartDate[PARAM_FOR_MONTH], VALUE_TRUE);
		int currentDate = convertToInteger(arrStartDate[PARAM_FOR_DATE], VALUE_FALSE);
		return isBeforeToday(currentYear, currentMonth, currentDate);

		// if ((convertToInteger(arrStartDate[PARAM_FOR_DATE], VALUE_FALSE) <
		// todayDate)
		// && (convertToInteger(arrStartDate[PARAM_FOR_MONTH], VALUE_TRUE) <=
		// todayMonth)
		// && (convertToInteger(arrStartDate[PARAM_FOR_YEAR], VALUE_FALSE) <=
		// todayYear)) {
		// return true;
		// }
		// return false;
	}

	private static boolean isBeforeToday(int currentYear, int currentMonth, int currentDate) {
		if (currentYear == todayYear) {
			if (currentMonth < todayMonth) {
				return true;
			} else if (currentMonth == todayMonth) {
				if (currentDate < todayDate) {
					return true;
				}
				return false;
			} else {
				return false;
			}
		} else if (currentYear < todayYear) {
			return true;
		} else {
			return false;
		}
	}

	private static Integer convertToInteger(String value, boolean isMonth) {
		int currentMonth = Integer.parseInt(value);
		if (isMonth) {
			currentMonth = currentMonth - 1;
		}
		return currentMonth;
	}

	private static String checkExistDate(Calendar calendar) {
		try {
			return dateFormat.format(calendar.getTime());
		} catch (NullPointerException e) {
			return VALUE_EMPTY_STRING;
		}
	}

	private static String constructEventDate(Calendar startDateTime, Calendar endDateTime) {

		String startDate = checkDisplayExistDate(startDateTime);
		String endDate = checkDisplayExistDate(endDateTime);
		return checkDateValue(startDate, endDate);
	}

	private static String checkDateValue(String startDate, String endDate) {
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

	private static String checkDisplayExistDate(Calendar calendar) {
		try {
			return displayDateFormat.format(calendar.getTime());
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

	private void changePastEventDesign() {
		eventGridPane.setStyle("-fx-border-color: lightgray;");
	}

	private void changeTextDecoration() {
		lblEventTitle.setStyle("-fx-strikethrough: true;");
		lblEventDate.setStyle("-fx-strikethrough: true;");
	}
}
