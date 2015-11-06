/* @@author A0126288X */
package calendrier.gui;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import utils.Event;

public class ViewDayController extends GridPane {

	private static final String VIEWDAY_SCREEN_LAYOUT_FXML = "/calendrier/resources/ViewDay.fxml";
	private static final String VALUE_EMPTY_SPACE = " ";
	private static final String VALUE_TODAY = "( Today )";
	private static final String VALUE_FOR_DAY = "( %1$s )";
	private static final String VALUE_FOR_DISPLAY_NUMBER_EVENT = "( %1$s - %2$s ) / %3$s";
	private static final String VALUE_FOR_NO_EVENT = "( %1$s ) / %2$s";
	private static final int VALUE_ADD_TO_ARRAY = 5;
	private static final int VALUE_START_FLOATING_POSITION = 5;
	private static final int VALUE_ZERO = 0;

	@FXML
	private Label lblPageDate;
	@FXML
	private FlowPane flowPaneDayEvents;
	@FXML
	private Label lblDayOfWeek;
	@FXML
	private Label lblToday;
	@FXML
	private FlowPane flowPaneOpenEvents;
	@FXML
	private Label lblNoOfDatedEvent;
	@FXML
	private Label lblNoOfOpenEvent;

	public ViewDayController(List<Event> datedEvents, List<Event> floatingEvents, int viewDate, int viewMonth,
			int viewYear, int day, boolean isToday, boolean isPast, int startArrIndex, int floatingArrStartIndex) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEWDAY_SCREEN_LAYOUT_FXML));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String currentDate = viewDate + VALUE_EMPTY_SPACE + detectMonth(viewMonth) + VALUE_EMPTY_SPACE + viewYear;
		lblPageDate.setText(currentDate);

		lblDayOfWeek.setText(String.format(VALUE_FOR_DAY, getDay(day)));

		if (isToday) {
			lblToday.setText(VALUE_TODAY);
		}

		setHand(datedEvents, isPast, startArrIndex);
		setFloating(floatingEvents, VALUE_START_FLOATING_POSITION, floatingArrStartIndex);
	}

	private String getDay(int day) {
		switch (day) {
		case 0:
			return "Sunday";
		case 1:
			return "Monday";
		case 2:
			return "Tuesday";
		case 3:
			return "Wednesday";
		case 4:
			return "Thursday";
		case 5:
			return "Friday";
		case 6:
			return "Saturday";
		default:
			return null;
		}
	}

	private void setFloating(List<Event> floatingEvents, int startIndex, int arrStartIndex) {
		if (floatingEvents.size() != VALUE_ZERO) {
			int endingIndex = arrStartIndex + VALUE_ADD_TO_ARRAY;
			if ((endingIndex) > floatingEvents.size()) {
				endingIndex = floatingEvents.size();
			}
			int end = endingIndex - arrStartIndex;

			if (endingIndex == (startIndex + 1)) {
				lblNoOfOpenEvent.setText(String.format(VALUE_FOR_NO_EVENT, startIndex + 1, floatingEvents.size()));
			} else {
				lblNoOfOpenEvent.setText(String.format(VALUE_FOR_DISPLAY_NUMBER_EVENT, arrStartIndex + 1, endingIndex,
						floatingEvents.size()));
			}

			for (int i = 0; i < end; i++) {
				addFloatingEvent(floatingEvents.get(i+arrStartIndex), startIndex);
				startIndex++;
			}
		} else if (floatingEvents.size() == VALUE_ZERO) {
			lblNoOfOpenEvent.setText(String.format(VALUE_FOR_NO_EVENT, startIndex, floatingEvents.size()));
		}
		
	}

	private void addFloatingEvent(Event event, int position) {
		flowPaneOpenEvents.getChildren().add(new OpenEventBoxController(event, position));
	}

	private void setHand(List<Event> events, boolean isPast, int startIndex) {

		if (events.size() != VALUE_ZERO) {

			int endingIndex = startIndex + VALUE_ADD_TO_ARRAY;

			if ((endingIndex) > events.size()) {
				endingIndex = events.size();
			}

			int end = endingIndex - startIndex;

			if (endingIndex == (startIndex + 1)) {
				lblNoOfDatedEvent.setText(String.format(VALUE_FOR_NO_EVENT, startIndex + 1, events.size()));
			} else {
				lblNoOfDatedEvent.setText(
						String.format(VALUE_FOR_DISPLAY_NUMBER_EVENT, startIndex + 1, endingIndex, events.size()));
			}
			for (int i = 0; i < end; i++) {
				addEvent(events.get(i), i, isPast);
			}
		} else if (events.size() == VALUE_ZERO) {
			lblNoOfDatedEvent.setText(String.format(VALUE_FOR_NO_EVENT, startIndex, events.size()));
		}
	}

	private void addEvent(Event event, int position, boolean isPast) {
		flowPaneDayEvents.getChildren().add(new DatedEventBoxController(event, position, isPast));
	}

	private String detectMonth(int month) {
		switch (month) {
		case 0:
			return "JANUARY";
		case 1:
			return "FEBUARY";
		case 2:
			return "MARCH";
		case 3:
			return "APRIL";
		case 4:
			return "MAY";
		case 5:
			return "JUNE";
		case 6:
			return "JULY";
		case 7:
			return "AUGUST";
		case 8:
			return "SEPTEMBER";
		case 9:
			return "OCTOBER";
		case 10:
			return "NOVEMBER";
		case 11:
			return "DECEMBER";
		}
		return null;
	}
}
