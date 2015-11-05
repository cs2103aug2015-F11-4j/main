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
	private static final int VALUE_TO_ADD = 1;
	private static final int VALUE_TO_MULTIPLY = 2;
	private static final int VALUE_EMPTY_LIST = 0;
	private static final int eachEventHeight = 70;

	private int totalDatedEventsHeight = VALUE_EMPTY_LIST;
	private int totalFloatingEventsHeight = VALUE_EMPTY_LIST;

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

	public ViewDayController(List<Event> datedEvents, List<Event> withFloatingEvents, int viewDate, int viewMonth,
			int viewYear, int day, boolean isToday, boolean isPast) {
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
		
		if(isToday) {
			lblToday.setText(VALUE_TODAY);
		}

		if (datedEvents.size() != VALUE_EMPTY_LIST) {
			totalDatedEventsHeight = eachEventHeight * datedEvents.size();
			if (totalDatedEventsHeight < eachEventHeight * VALUE_TO_MULTIPLY) {
				totalDatedEventsHeight = eachEventHeight * VALUE_TO_MULTIPLY;
			}
			flowPaneDayEvents.setPrefHeight(totalDatedEventsHeight);
			flowPaneDayEvents.setMaxHeight(totalDatedEventsHeight);
			setHand(datedEvents, isPast);
		} else {
			flowPaneDayEvents.setPrefHeight(eachEventHeight * VALUE_TO_MULTIPLY);
			flowPaneDayEvents.setMaxHeight(eachEventHeight * VALUE_TO_MULTIPLY);
		}

		List<Event> floatingEvents = getFloatingEvents(datedEvents, withFloatingEvents);
		if (floatingEvents.size() != VALUE_EMPTY_LIST) {
			totalFloatingEventsHeight = eachEventHeight * ((datedEvents.size() + VALUE_TO_ADD) / 2);
			flowPaneOpenEvents.setPrefHeight(totalFloatingEventsHeight);
			flowPaneOpenEvents.setMaxHeight(totalFloatingEventsHeight);
			setFloating(floatingEvents, datedEvents.size());
		}
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

	private void setFloating(List<Event> floatingEvents, int startIndex) {
		for (int i = 0; i < floatingEvents.size(); i++) {
			addFloatingEvent(floatingEvents.get(i), startIndex);
			startIndex++;
		}
	}

	private void addFloatingEvent(Event event, int position) {
		flowPaneOpenEvents.getChildren().add(new OpenEventBoxController(event, position));
	}

	private void setHand(List<Event> events, boolean isPast) {
		for (int i = 0; i < events.size(); i++) {
			addEvent(events.get(i), i, isPast);
		}
	}

	private void addEvent(Event event, int position, boolean isPast) {
		flowPaneDayEvents.getChildren().add(new DatedEventBoxController(event, position, isPast));
	}

	private List<Event> getFloatingEvents(List<Event> datedEvents, List<Event> floatingEvents) {
		for (Event e : datedEvents) {
			if (floatingEvents.contains(e)) {
				floatingEvents.remove(e);
			}
		}
		return floatingEvents;
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
