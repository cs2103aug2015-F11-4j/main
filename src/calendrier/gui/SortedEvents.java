/* @@author A0126288X */
package calendrier.gui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import utils.Event;
import utils.Priority;

public class SortedEvents {

	private static ArrayList<Event> events;
	private static ArrayList<Event> arrVeryHigh;
	private static ArrayList<Event> arrHigh;
	private static ArrayList<Event> arrMedium;
	private static ArrayList<Event> arrLow;
	private static ArrayList<Event> arrVeryLow;
	private static ArrayList<Event> arrNoPriority;
	private static ArrayList<Event> arrNotDone;
	private static ArrayList<Event> arrDone;
	private static ArrayList<Event> arrPastEvent;

	private static final Calendar cal = Calendar.getInstance();
	private static final int todayDate = cal.getTime().getDate();
	private static final int todayMonth = cal.getTime().getMonth();
	private static final int todayYear = cal.getTime().getYear();
	private static final DateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
	private static final String VALUE_EMPTY_STRING = "";
	private static final String VALUE_SPLIT_REGEX = " ";

	private static final int PARAM_FOR_DATE = 0;
	private static final int PARAM_FOR_MONTH = 1;
	private static final int PARAM_FOR_YEAR = 2;

	private SortedEvents() {
		reinitiateArray();
	}

	public static ArrayList<Event> sortEvents(List<Event> parsedInEvents) {
		reinitiateArray();
		doStatusSorting(parsedInEvents);
		doSorting();
		return events;
	}

	private static void reinitiateArray() {
		events = new ArrayList<Event>();
		arrVeryHigh = new ArrayList<Event>();
		arrHigh = new ArrayList<Event>();
		arrMedium = new ArrayList<Event>();
		arrLow = new ArrayList<Event>();
		arrVeryLow = new ArrayList<Event>();
		arrNoPriority = new ArrayList<Event>();
		arrNotDone = new ArrayList<Event>();
		arrDone = new ArrayList<Event>();
		arrPastEvent = new ArrayList<Event>();
	}

	private static void doStatusSorting(List<Event> parsedInEvents) {
		for (int i = 0; i < parsedInEvents.size(); i++) {
			Event currentEvent = parsedInEvents.get(i);
			boolean isDone = currentEvent.isDone();
			addToDoneArray(currentEvent, isDone);
		}
	}

	private static void doSorting() {
		for (int i = 0; i < arrNotDone.size(); i++) {
			Event currentEvent = arrNotDone.get(i);
			boolean isPast = checkDate(currentEvent.getStartDateTime(), currentEvent.getEndDateTime());
			addToPastArray(currentEvent, isPast);
		}
		addToMainArrEvents();
	}

	private static void addToPastArray(Event currentEvent, boolean isPast) {
		if (isPast) {
			arrPastEvent.add(currentEvent);
		} else {
			Priority priority = currentEvent.getPriority();
			addToArray(currentEvent, priority);
		}
	}

	private static boolean checkDate(Calendar startDateTime, Calendar endDateTime) {
		String startDate = checkExistDate(startDateTime);
		String endDate = checkExistDate(endDateTime);
		String[] arrStartDate = startDate.split(VALUE_SPLIT_REGEX);
		String[] arrEndDate = endDate.split(VALUE_SPLIT_REGEX);

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
		if ((convertToInteger(arrStartDate[PARAM_FOR_DATE]) < todayDate)
				&& (convertToInteger(arrStartDate[PARAM_FOR_MONTH]) <= todayMonth)
				&& (convertToInteger(arrStartDate[PARAM_FOR_YEAR]) <= todayYear)
				&& (convertToInteger(arrEndDate[PARAM_FOR_DATE]) > todayDate)
				&& (convertToInteger(arrEndDate[PARAM_FOR_MONTH]) >= todayMonth)
				&& (convertToInteger(arrEndDate[PARAM_FOR_YEAR]) >= todayYear)) {
			return false;
		}
		return true;
	}

	private static boolean checkAfterToday(String[] arrEndDate) {
		if ((convertToInteger(arrEndDate[PARAM_FOR_DATE]) > todayDate)
				&& (convertToInteger(arrEndDate[PARAM_FOR_MONTH]) >= todayMonth)
				&& (convertToInteger(arrEndDate[PARAM_FOR_YEAR]) >= todayYear)) {
			return false;
		}
		return true;
	}

	private static boolean checkBeforeToday(String[] arrStartDate) {
		if ((convertToInteger(arrStartDate[PARAM_FOR_DATE]) < todayDate)
				&& (convertToInteger(arrStartDate[PARAM_FOR_MONTH]) <= todayMonth)
				&& (convertToInteger(arrStartDate[PARAM_FOR_YEAR]) <= todayYear)) {
			return true;
		}
		return false;
	}

	private static Integer convertToInteger(String value) {
		return Integer.parseInt(value);
	}

	private static String checkExistDate(Calendar calendar) {
		try {
			return dateFormat.format(calendar.getTime());
		} catch (NullPointerException e) {
			return VALUE_EMPTY_STRING;
		}
	}

	private static void addToDoneArray(Event event, boolean isDone) {
		if (isDone) {
			arrDone.add(event);
			System.out.println("Done");
		} else {
			arrNotDone.add(event);
			System.out.println("Not Done");
		}
	}

	private static void addToMainArrEvents() {
		events.addAll(arrPastEvent);
		events.addAll(arrVeryHigh);
		events.addAll(arrHigh);
		events.addAll(arrMedium);
		events.addAll(arrLow);
		events.addAll(arrVeryLow);
		events.addAll(arrNoPriority);
		events.addAll(arrDone);
	}

	private static void addToArray(Event currentEvent, Priority priority) {
		if (priority == Priority.VERY_HIGH) {
			arrVeryHigh.add(currentEvent);
		} else if (priority == Priority.HIGH) {
			arrHigh.add(currentEvent);
		} else if (priority == Priority.MEDIUM) {
			arrMedium.add(currentEvent);
		} else if (priority == Priority.LOW) {
			arrLow.add(currentEvent);
		} else if (priority == Priority.VERY_LOW) {
			arrVeryLow.add(currentEvent);
		} else {
			arrNoPriority.add(currentEvent);
		}
	}
	
	
}
