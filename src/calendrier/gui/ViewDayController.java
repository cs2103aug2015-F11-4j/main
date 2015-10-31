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

	@FXML
	private Label lblPageDate;
	@FXML
	private FlowPane flowPaneDayEvents;

	public ViewDayController(List<Event> events, int viewDate, int viewMonth, int viewYear) {
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
		setHand(events);
	}

	private void setHand(List<Event> events) {
		
		for(int i = 0; i < events.size(); i++) {
			addEvent(events.get(i), i);
		}
	}
	
	private void addEvent(Event event, int position) {
		flowPaneDayEvents.getChildren().add(new DatedEventBoxController(event, position));
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
