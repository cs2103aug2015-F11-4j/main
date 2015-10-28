package calendrier.gui;

import java.io.IOException;
import java.util.ArrayList;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import utils.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

public class ViewController extends FlowPane {

	private static final String VIEW_SCREEN_LAYOUT_FXML = "/calendrier/resources/View.fxml";
	private static final String VIEWMONTH_SCREEN_LAYOUT_FXML = "/calendrier/resources/ViewMonth.fxml";
	private static final int VALUE_ADD_TO_ARRAY = 8;
	@FXML
	private Label lblmonth;
	@FXML
	private Label lblyear;

	public ViewController(List<Event> events, int date, int month, int year) {
		int i, end;
	
		FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEWMONTH_SCREEN_LAYOUT_FXML));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, date);

		@SuppressWarnings("deprecation")
		int day = cal.getTime().getDay();

		date = date % 7 - 1;
		day = day - date;

		if (day < 0) {
			day = Math.abs(day) + 1;
		}
		if (day != 7) {
			date = 0;
			while (date < day) {
				getChildren().add(new EventMonthController(0, month, year, null));
				date++;
			}
		}

		end = detectLengthofMonth(month, year);

		lblmonth.setText(detectMonth(month));
		lblyear.setText(String.format("%d", year));
		
		for (i = 0; i < end; i++) {
			getChildren().add(new EventMonthController(i + 1, month, year, detectDate(events, i + 1)));
		}
	}

	private int detectLengthofMonth(int month, int year) {
		int end;
		if (month == 0 || month == 2 || month == 4 || month == 6 || month == 7 || month == 9 || month == 11) {
			end = 31;
		} else if (month == 1) {
			end = detectLeapYear(year);
		} else {
			end = 30;
		}
		return end;
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

	private int detectLeapYear(int year) {
		if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
			return 29;
		}
		return 28;
	}

	@SuppressWarnings("deprecation")
	public List<Event> detectDate(List<Event> events, int date) {
		int i, flag=0;
		List<Event> results = new ArrayList<Event>();

		for (i = 0; i < events.size(); i++) {
			if (events.get(i).getStartDateTime() != null && events.get(i).getEndDateTime() != null) {
				if (events.get(i).getEndDateTime().getTime().getDate() >= date
								&& events.get(i).getStartDateTime().getTime().getDate() <= date) {
					results.add(events.get(i));
					flag++;
				}
			}
			if(events.get(i).getStartDateTime().getTime().getDate() == date && flag==0){
				results.add(events.get(i));
			}
			flag=0;
		}
		return results;
	}

	public ViewController(List<Event> events, int startIndex) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEW_SCREEN_LAYOUT_FXML));
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
