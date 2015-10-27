package calendrier;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import utils.Command;
import utils.Event;
import utils.OnRemindListener;
import utils.ParsedCommand;

/**
 * For handling the main logic
 * 
 * @author yeehuipoh
 *
 */
public class MainLogic {
	private Parser parser = null;
	private EventHandler eventHandler = null;
	private Event event = null;
	private List<Event> events = null;
	private List<Event> filteredEvents = null;

	/**
	 * Constructor to initialize the main components of Main Logic
	 */
	public MainLogic() {
		parser = new Parser();
		eventHandler = new EventHandler();
	}

	/**
	 * Inject a stub event handler
	 * 
	 * @param eventHandler
	 *            stub event handler to be injected
	 */
	public void injectEventHandler(EventHandler eventHandler) {
		this.eventHandler = eventHandler;
	}

	/**
	 * Inject a stub parser
	 * 
	 * @param parser
	 *            stub parser to be injected
	 */
	public void injectParser(Parser parser) {
		this.parser = parser;
	}

	/**
	 * Execute command
	 * 
	 * @param command
	 *            Command string input from user
	 * @return command action
	 */
	public Command execute(String command) {

		ParsedCommand parsedCommand = parser.parse(command);

		assert (parsedCommand != null);
		if (parsedCommand.getCommand() != null) {
			performCommand(parsedCommand);
		}

		return parsedCommand.getCommand();
	}

	/**
	 * Perform the parsed command
	 * 
	 * @param parsedCommand
	 *            parsedCommand from the parser
	 */
	private void performCommand(ParsedCommand parsedCommand) {
		List<Event> eventList = new ArrayList<>();

		try {
			eventList = eventHandler.execute(parsedCommand);
			if (parsedCommand.getCommand() == Command.FILTER) {
				filteredEvents = eventList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		updateEvent(eventList);
	}

	/**
	 * Update event of the last action
	 * 
	 * @param eventList
	 *            events returned from event handler after execution
	 */
	private void updateEvent(List<Event> eventList) {
		if (eventList.size() > 0) {
			event = eventList.get(0);
		} else {
			event = null;
		}
	}

	/**
	 * Notifies user about event starting soon (Not Implemented Yet)
	 * 
	 * @param event
	 *            event that is starting soon
	 */
	public void notifyUser(Event event) {
	}

	/**
	 * Gets the event of the last action performed
	 * 
	 * @return event which the last action has performed on
	 */
	public Event getEvent() {
		return event;
	}

	/**
	 * Gets all available events
	 * 
	 * @return all events
	 */
	public List<Event> getAllEvents() {
		events = eventHandler.getAllEvents();
		return events;
	}

	public List<Event> getFilteredEvents() {
		return filteredEvents;
	}

	/**
	 * Gets all events within a specific month
	 * 
	 * @param year
	 *            year of event (e.g. 2015)
	 * @param month
	 *            month of event starting from 1 as January and 12 as December
	 * @return list of events in the month
	 */
	public List<Event> getMonthEvents(int year, int month) {
		events = eventHandler.getAllEvents();
		List<Event> monthEvents = new ArrayList<>();

		filterToMonth(year, month, monthEvents);
		sortByStartDateTime(monthEvents);

		return events;
	}

	private void sortByStartDateTime(List<Event> monthEvents) {
		Collections.sort(monthEvents, new Comparator<Event>() {

			@Override
			public int compare(Event e1, Event e2) {
				int compareResult = 0;
				long e1start = e1.getStartDateTime().getTimeInMillis();
				long e2start = e2.getStartDateTime().getTimeInMillis();

				if (e1start != e2start) {
					compareResult = e1start - e2start > 0 ? 1 : -1;

				}
				return compareResult;
			}

		});
	}

	private void filterToMonth(int year, int month, List<Event> monthEvents) {
		// Filter into month
		for (int i = 0; i < events.size(); i++) {
			Event event = events.get(i);

			if (isInMonth(event, year, month)) {
				monthEvents.add(event);
			}
		}
	}

	private boolean isInMonth(Event event, int year, int month) {
		boolean isInThisMonth = false;
		Calendar thisMonth = Calendar.getInstance();
		Calendar nextMonth = Calendar.getInstance();

		// Reset
		thisMonth.setTimeInMillis(0);
		nextMonth.setTimeInMillis(0);

		// Set to start of month
		thisMonth.set(year, month - 1, 0);
		nextMonth.set(year, month - 1, 0);

		// Check Start Date
		if (isWithinMonth(event.getStartDateTime(), thisMonth, nextMonth)) {
			isInThisMonth = true;
		}
		// Check End Date
		else if (isWithinMonth(event.getEndDateTime(), thisMonth, nextMonth)) {
			isInThisMonth = true;
		}

		return isInThisMonth;
	}

	private boolean isWithinMonth(Calendar eventDateTime, Calendar thisMonth, Calendar nextMonth) {
		return eventDateTime.after(thisMonth) && eventDateTime.before(nextMonth);
	}

	/**
	 * Set OnRemindListener
	 * 
	 * @param listener
	 *            listener for reminder
	 */
	public void setOnRemindListener(OnRemindListener listener) {
		// Set in event handler
	}
}
