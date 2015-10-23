package calendrier.gui;

import java.util.ArrayList;
import java.util.Collections;
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

	private SortedEvents() {
		reinitiateArray();
	}

	public static ArrayList<Event> sortEvents(List<Event> parsedInEvents) {
		reinitiateArray();
		doSorting(parsedInEvents);
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
	}

	private static void doSorting(List<Event> parsedInEvents) {
		for (int i = 0; i < parsedInEvents.size(); i++) {
			Event currentEvent = parsedInEvents.get(i);
			Priority priority = currentEvent.getPriority();
			addToArray(currentEvent, priority);
		}
		addToMainArrEvents();
	}
	
	private static void addToMainArrEvents() {
		events.addAll(arrVeryHigh);
		events.addAll(arrHigh);
		events.addAll(arrMedium);
		events.addAll(arrLow);
		events.addAll(arrVeryLow);
		events.addAll(arrNoPriority);
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