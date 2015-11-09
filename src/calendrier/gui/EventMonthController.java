package calendrier.gui;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import utils.Event;
import utils.Priority;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 * @@author A0126421U 
 * For generate the children object for viewMonth
 * 
 * @author hiumengxiong
 *
 */
public class EventMonthController extends StackPane {

	@FXML
	private GridPane eventGridPaneMonth;
	@FXML
	private Label lblDate;
	@FXML
	private Label lblEventID1;
	@FXML
	private Label lblEventID2;
	@FXML
	private Label lblEvent1;
	@FXML
	private Label lblEvent2;
	@FXML
	private Label lblEvent3;

	private static final String SINGLE_EVENT_LAYOUT_FXML = "/calendrier/resources/EventMonth.fxml";

	private static final String VALUE_SHOW_EMPTY_DATA = " ";

	/**
	 * @@author A0126421U 
	 * Constructor to initialize the main components of
	 *          EventMonthController
	 * 
	 * @param date
	 *            - Date to be display
	 * @param month
	 *            - Month to be display
	 * @param year
	 *            - Year to be display
	 * @param events
	 *            - List of events for the this date
	 * @param idList
	 *            - The id to be display to user user, which will link for
	 *            idMapper
	 * 
	 */
	public EventMonthController(int date, int month, int year, List<Event> events, List<String> idList) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(SINGLE_EVENT_LAYOUT_FXML));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		initEventValue(date, month, year, events, idList);
	}

	/**
	 * @@author A0126421U 
	 * Fill in details for EventMonth
	 * 
	 * @param date
	 *            - Date to be display
	 * @param month
	 *            - Month to be display
	 * @param year
	 *            - Year to be display
	 * @param events
	 *            - List of events for the this date
	 * @param idList
	 *            - The id to be display to user user, which will link for
	 *            idMapper
	 * 
	 */
	public void initEventValue(int date, int month, int year, List<Event> events, List<String> idList) {

		Calendar cal = Calendar.getInstance();

		setContainerForDate(date, events, idList);
		setForToday(date, month, year, cal);
		setForPassedEvents(date, month, year, cal);
	}

	/**
	 * @@author A0126421U 
	 * Set Detail for each date
	 * 
	 * @param date
	 *            - Date to be display
	 * @param events
	 *            - List of events for the this date
	 * @param idList
	 *            - The id to be display to user user, which will link for
	 *            idMapper
	 * 
	 */
	private void setContainerForDate(int date, List<Event> events, List<String> idList) {

		lblDate.setText(checkDate(date));

		if (events != null && events.size() > 0) {
			setData(events, idList);
		} else {
			setEmptyData();
		}
	}

	/**
	 * @@author A0126421U 
	 * Fill in details for each date
	 * 
	 * @param events
	 *            - List of events for the this date
	 * @param idList
	 *            - The id to be display to user user, which will link for
	 *            idMapper
	 * 
	 */
	private void setData(List<Event> events, List<String> idList) {
		if (events.get(0).getTitle() != null) {
			setTaskInDate(events.get(0), Integer.toString(computeFakeId(idList, events.get(0).getId())), lblEvent1,
					lblEventID1);
			if (events.size() > 1) {
				setTaskInDate(events.get(1), Integer.toString(computeFakeId(idList, events.get(1).getId())), lblEvent2,
						lblEventID2);
				if (events.size() > 2) {
					lblEvent3.setText(String.format(" + %d more...", events.size() - 2));
					changeTextColor(Priority.VERY_HIGH, lblEvent3);
				}
			}
		} else {
			setEmptyData();
		}
	}
	
	/**
	 * @@author A0126421U 
	 * Set parameter for date
	 * 
	 * 
	 */
	private void setTaskInDate(Event events, String Id, Label lblEvent, Label lblEventId) {
		lblEvent.setText(events.getTitle());
		lblEventId.setText(Id);
		if (events.isDone()) {
			changeTextDecoration(lblEvent);
		}
		changeTextColor(events.getPriority(), lblEvent);
	}

	/**
	 * @@author A0126421U 
	 * Set empty data
	 * 
	 * 
	 */
	private void setEmptyData() {
		lblEvent1.setText(VALUE_SHOW_EMPTY_DATA);
		lblEvent2.setText(VALUE_SHOW_EMPTY_DATA);
		lblEvent3.setText(VALUE_SHOW_EMPTY_DATA);
	}

	/**
	 * @@author A0126421U 
	 * Set Color for Today
	 * 
	 * 
	 */
	@SuppressWarnings("deprecation")
	private void setForToday(int date, int month, int year, Calendar cal) {
		if (cal.getTime().getDate() == date && cal.getTime().getMonth() == month
				&& (cal.getTime().getYear() + 1900) == year) {
			eventGridPaneMonth.setStyle("-fx-border-color: red;-fx-border-width: 2.0px;");
		}
	}

	/**
	 * @@author A0126421U 
	 * Fill in details for EventMonth
	 * 
	 * @param date
	 *            - Date to be display
	 * @param month
	 *            - Month to be display
	 * @param year
	 *            - Year to be display
	 * @param cal
	 *            - calendar of today
	 * 
	 */
	@SuppressWarnings("deprecation")
	private void setForPassedEvents(int date, int month, int year, Calendar cal) {
		if (cal.getTime().getDate() > date && cal.getTime().getMonth() >= month
				&& (cal.getTime().getYear() + 1900) >= year) {
			lblEvent1.setStyle("-fx-text-fill: darkgray;");
			lblEvent2.setStyle("-fx-text-fill: darkgray;");
			lblEvent3.setStyle("-fx-text-fill: darkgray;");
			lblEventID1.setStyle("-fx-text-fill: darkgray;");
			lblEventID2.setStyle("-fx-text-fill: darkgray;");
		} else if (cal.getTime().getMonth() > month && (cal.getTime().getYear() + 1900) >= year) {
			lblEvent1.setStyle("-fx-text-fill: darkgray;");
			lblEvent2.setStyle("-fx-text-fill: darkgray;");
			lblEvent3.setStyle("-fx-text-fill: darkgray;");
			lblEventID1.setStyle("-fx-text-fill: darkgray;");
			lblEventID2.setStyle("-fx-text-fill: darkgray;");
		}
	}

	/**
	 * @@author A0126421U 
	 * Compute the fake id that display to the user.
	 * 
	 * @param idList
	 *            - The id to be display to user user, which will link for
	 *            idMapper
	 * @param id
	 *            - the actual id of current event
	 * 
	 * @return fakedId - the short id to be display to the user.
	 * 
	 */
	private int computeFakeId(List<String> idList, String id) {
		for (int i = 0; i < idList.size(); i++) {
			if (idList.get(i).equals(id)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * @@author A0126421U 
	 * check the existence of date
	 * 
	 * @param date
	 *            - Date to be check
	 * 
	 * @return String of Date
	 * 
	 */
	private String checkDate(int date) {
		if (date != 0) {
			return String.format("%d", date);
		}
		return VALUE_SHOW_EMPTY_DATA;
	}

	/**
	 * @@author A0126421U 
	 * Strike through the event if is done
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
	 * Change the text style for undone task based on
	 *          priority
	 * 
	 * @param priority
	 *            - the priority of the current event
	 * @param lblEvent
	 *            - the layout to be modified
	 * 
	 */
	private void changeTextColor(Priority priority, Label lblEvent) {
		if (priority == Priority.VERY_HIGH) {
			lblEvent.setStyle("-fx-text-fill: red;");
		} else if (priority == Priority.HIGH) {
			lblEvent.setStyle("-fx-text-fill: #FFA07A;");
		} else if (priority == Priority.MEDIUM) {
			lblEvent.setStyle("-fx-text-fill: gold;");
		} else if (priority == Priority.LOW) {
			lblEvent.setStyle("-fx-text-fill: #00FF7F;");
		} else if (priority == Priority.VERY_LOW) {
			lblEvent.setStyle("-fx-text-fill: #2E8B57;");
		} else {
			lblEvent.setStyle("-fx-text-fill: black;");
		}
	}
}
