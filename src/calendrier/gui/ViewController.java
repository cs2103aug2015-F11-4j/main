package calendrier.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import utils.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;

public class ViewController extends FlowPane {

	private static final String VIEW_SCREEN_LAYOUT_FXML = "/calendrier/resources/View.fxml";
	private static final int VALUE_ADD_TO_ARRAY = 8;

	public ViewController(List<Event> events) {
		int i, end, month;

		FXMLLoader loader = new FXMLLoader(getClass().getResource(
				VIEW_SCREEN_LAYOUT_FXML));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.set(2015, 10, 18, 10, 55, 00);

		@SuppressWarnings("deprecation")
		int date = cal.getTime().getDate(), day = cal.getTime().getDay();

		date = date % 7 - 1;
		day = day - date;

		if (day < 0) {
			day = Math.abs(day) + 1;
		}
		if (day != 7) {
			date = 0;
			while (date < day) {
				getChildren().add(new EventMonthController(0, null));
				date++;
			}
		}
		month = cal.getTime().getMonth();
		if (month == 0 || month == 2 || month == 4 || month == 6 || month == 7
				|| month == 9 || month == 11) {
			end = 31;
		} else {
			end = 30;
		}
		for (i = 0; i < end; i++) {
			getChildren().add(
					new EventMonthController(i + 1, detectDate(events, i + 1)));
		}
	}

	public List<Event> detectDate(List<Event> events, int date) {
		int i;
		List<Event> results = new ArrayList<Event>();

		for (i = 0; i < events.size(); i++) {
			if (events.get(i).getStartDateTime().getTime().getDate() == date) {
				results.add(events.get(i));
			}
		}
		return results;
	}

	public ViewController(List<Event> events, int startIndex) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(
				VIEW_SCREEN_LAYOUT_FXML));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setHand(events, startIndex);
	}

	public void setHand(List<Event> events, int startIndex) {

		int endingIndex = startIndex + VALUE_ADD_TO_ARRAY;

		if ((endingIndex) > events.size()) {
			endingIndex = events.size();
		}

		int end = endingIndex - startIndex;

		// Calendar cal = Calendar.getInstance();
		// DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		// System.out.println(dateFormat.format(cal.getTime())); //2014/08/06
		// 16:00:22

		// for (int i = startIndex; i < endingIndex; i++) {
		// //if(cal.compareTo(events.get(i).getStartDateTime())<0){
		// addEvent(events.get(i));
		// //System.out.println(events.get(i).getId());
		// //}
		// }

		for (int i = 0; i < end; i++) {
			addEvent(events.get(startIndex + i), i);
		}
	}

	public void addEvent(Event event, int position) {
		getChildren().add(new EventBoxController(event, position));
	}
}
