/* @@author A0126288X */
package calendrier.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import utils.Event;
import utils.IdMapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

public class ViewController extends FlowPane {

	private static final String VIEW_SCREEN_LAYOUT_FXML = "/calendrier/resources/View.fxml";
	private static final String VIEWMONTH_SCREEN_LAYOUT_FXML = "/calendrier/resources/ViewMonth.fxml";
	private static final String VIEWHOME_SCREEN_LAYOUT_FXML = "/calendrier/resources/Home.fxml";
	private static final int VALUE_ADD_TO_ARRAY = 15;
	
	//@@author A0126421U
	@FXML
	private Label lblmonth;
	@FXML
	private Label lblyear;
	@FXML
	private Label lbltime;
	@FXML
	private Label lblCurrent;
	@FXML
	private Label lblCurrent1;
	@FXML
	private Label lblNext;
	@FXML
	private Label lblPassed;
	@FXML
	private Label lblOngoing;
	@FXML
	private Label lblFloat;
	//@@author 

	public ViewController() {
	}
	
	/**
	 * @@author A0126421U
	 * Constructor to initialize the main components of viewHome
	 * 
	 * @param time - the remaining time for next event to happen
	 * @param events - the events that happen today
	 * @param nextEvents - the next event that going to happen
	 * @param floatTask - total number of floating task
	 * @param onGoingTask - total number of on going task 
	 * @param passedTask - total number of passed task
	 * 
	 */
	public ViewController(long time, List<Event> events, int floatTask, int onGoingTask, int passedTask) {
		long sec = 0, min = 0, hour = 0, day = 0;
		FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEWHOME_SCREEN_LAYOUT_FXML));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Calendar cal= Calendar.getInstance();
		for(int i=0;i<events.size();i++){
			if(events.get(i).getStartDateTime()!=null){
				if(events.get(i).getStartDateTime().before(cal)){
					lblCurrent.setText(events.get(i).getTitle());
				}
				else if(events.get(i).getStartDateTime().after(cal)){
					lblNext.setText(events.get(i).getTitle());
					break;
				}
			}
		}
//		lblCurrent1.setStyle("-fx-border-width: 3.0px");
//		lblCurrent.setText(events);
//		lblNext.setText(nextEvents);
		lblPassed.setText(Integer.toString(passedTask));
		lblOngoing.setText(Integer.toString(onGoingTask));
		lblFloat.setText(Integer.toString(floatTask));
		if (time != -1){
			sec = (time / 1000) % 60;
			min = (time / 60000) % 60;
			hour = (time / 3600000) % 24;
			day = (time / 3600000) / 24;
			lbltime.setText(Long.toString(day) + "Day " + Long.toString(hour) + "Hour " + Long.toString(min) + "Min "
					+ Long.toString(sec) + "Sec ");
		} else

		{
			lbltime.setText("-");
		}
	}
	//@@author 

	/**
	 * @@author A0126421U
	 * Constructor to initialize the main components of viewMonth
	 * 
	 * @param events - List of events in the specific month
	 * @param date - the date to be display for user
	 * @param month - the month to display for user
	 * @param year - the year to display for user
	 * 
	 */
	public ViewController(List<Event> events, int date, int month, int year) {
		int i, end;
		List<String> idList = new ArrayList<String>();

		FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEWMONTH_SCREEN_LAYOUT_FXML));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Calendar cal = Calendar.getInstance();
		cal.set(year, month, 1);

		@SuppressWarnings("deprecation")
		int day = cal.getTime().getDay();

		if (day != 7) {
			date = 0;
			while (date < day) {
				getChildren().add(new EventMonthController(0, month, year, null, null));
				date++;
			}
		}

		end = detectLengthofMonth(month, year);

		lblmonth.setText(detectMonth(month));
		lblyear.setText(String.format("%d", year));
		idList = setIdMapper(events);
		for (i = 0; i < end; i++) {
			getChildren()
					.add(new EventMonthController(i + 1, month, year, detectDate(events, i + 1, month, year), idList));
		}
	}
	//@@author 
	
	/**
	 * @@author A0126421U
	 * Map short id to the real id
	 * 
	 * @param events - the events to be mapped
	 * 
	 * @return idList -  the list of id that map to the id
	 * 
	 */
	private List<String> setIdMapper(List<Event> events) {
		IdMapper idMapper = IdMapper.getInstance();
		List<String> idList = new ArrayList<String>();
		for (int i = 0; i < events.size(); i++) {
			// System.out.println(events.get(i).toString());
			idMapper.set(Integer.toString(i), events.get(i).getId());
			idList.add(events.get(i).getId());
		}
		return idList;
	}
	//@@author 
	
	/**
	 * @@author A0126421U
	 * to compute total number of day in month
	 * 
	 * @param month - the month to detect the total day
	 * @param year - the year to display
	 * 
	 * @return numOfDay - number of day in particular month
	 * 
	 */
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
	//@@author 
	
	/**
	 * @@author A0126421U
	 * convert month from integer to string
	 * 
	 * @param month - the month to be convert
	 * 
	 * @return String of month
	 * 
	 */
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
	//@@author 
	
	/**
	 * @@author A0126421U
	 * determine for leap year
	 * 
	 * @param year - the year to be determine for leap year
	 * 
	 * @return numOfDay - number of day in February for that year
	 * 
	 */
	private int detectLeapYear(int year) {
		if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
			return 29;
		}
		return 28;
	}
	//@@author 
	
	/**
	 * @@author A0126421U
	 * detect the number of events in particular date
	 * 
	 * @param year - the year to display for user
	 * @param month - the month to display for user
	 * @param date - the date to display for user
	 * @param events - the list of events for a month
	 * 
	 * @return eventList -total event in a date
	 * 
	 */
	@SuppressWarnings("deprecation")
	public List<Event> detectDate(List<Event> events, int date, int month, int year) {
		int i;
		List<Event> results = new ArrayList<Event>();

		for (i = 0; i < events.size(); i++) {
			if (events.get(i).getStartDateTime() != null && events.get(i).getEndDateTime() != null) {
				// Start and end in same year
				if (events.get(i).getEndDateTime().getTime().getYear() + 1900 == year
						&& events.get(i).getStartDateTime().getTime().getYear() + 1900 == year) {

					if ((events.get(i).getEndDateTime().getTime().getDate() >= date
							&& events.get(i).getEndDateTime().getTime().getMonth() == month)
							&& (events.get(i).getStartDateTime().getTime().getDate() <= date
									&& events.get(i).getStartDateTime().getTime().getMonth() == month)) {
						results.add(events.get(i));
					} else if (events.get(i).getStartDateTime().getTime().getMonth() == month
							&& events.get(i).getEndDateTime().getTime().getMonth() > month) {
						if (events.get(i).getStartDateTime().getTime().getDate() <= date) {
							results.add(events.get(i));
						}
					} else if (events.get(i).getStartDateTime().getTime().getMonth() < month
							&& events.get(i).getEndDateTime().getTime().getMonth() == month) {
						if (events.get(i).getEndDateTime().getTime().getDate() >= date) {
							results.add(events.get(i));
						}
					} else if (events.get(i).getStartDateTime().getTime().getMonth() < month
							&& events.get(i).getEndDateTime().getTime().getMonth() > month) {
						results.add(events.get(i));
					}
				}
				// Start year < end year, set for start year
				else if (events.get(i).getEndDateTime().getTime().getYear() + 1900 > year
						&& events.get(i).getStartDateTime().getTime().getYear() + 1900 == year) {
					if (events.get(i).getStartDateTime().getTime().getMonth() == month) {
						if (events.get(i).getStartDateTime().getTime().getDate() <= date) {
							results.add(events.get(i));
						}
					} else if (events.get(i).getStartDateTime().getTime().getMonth() < month) {
						results.add(events.get(i));
					}
				}
				// Start year < end year, set for end year
				else if (events.get(i).getEndDateTime().getTime().getYear() + 1900 == year
						&& events.get(i).getStartDateTime().getTime().getYear() + 1900 < year) {
					if (events.get(i).getEndDateTime().getTime().getMonth() == month) {
						if (events.get(i).getEndDateTime().getTime().getDate() >= date) {
							results.add(events.get(i));
						}
					} else if (events.get(i).getEndDateTime().getTime().getMonth() > month) {
						results.add(events.get(i));
					}
				}
			}
			// for task without enddate
			else {
				if (events.get(i).getStartDateTime().getTime().getDate() == date) {
					if (events.get(i).getStartDateTime().getTime().getMonth() == month) {
						results.add(events.get(i));
					}
				}
			}
		}
		return results;
	}
	//@@author
	
	/**
	 * @@author A0126421U
	 * check for month
	 * 
	 * @param month - the month to be check
	 * 
	 * @return month -the checked month
	 * 
	 */
	public int checkMonth(int month) {
		if (month > 11) {
			return 12;
		}
		return month;
	}
	//@@author
	

	//@@author A0126288X
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

		for (int i = 0; i < end; i++) {
			addEvent(events.get(startIndex + i), i);
		}
	}

	public void addEvent(Event event, int position) {
		getChildren().add(new EventBoxController(event, position));
	}
}
//@@author
