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
import utils.Recurrence;
import utils.UserCommandException;

/**
 * @@author A0088646M For handling the main logic
 * 
 * @author yeehuipoh
 *
 */
public class MainLogic {
	private static final String MESSAGE_INVALID_COMMAND = "Invalid command.";
	private Parser parser = null;
	private EventHandler eventHandler = null;
	private Event event = null;
	private List<Event> events = null;
	private List<Event> filteredEvents = null;

	/**
	 * @@author A0088646M Constructor to initialize the main components of Main
	 *          Logic
	 */
	public MainLogic() {
		parser = new Parser();
		eventHandler = new EventHandler();
		this.events = new ArrayList<>();
	}

	/**
	 * @@author A0088646M Inject a stub event handler
	 * 
	 * @param eventHandler
	 *            stub event handler to be injected
	 */
	public void injectEventHandler(EventHandler eventHandler) {
		this.eventHandler = eventHandler;
	}

	/**
	 * @@author A0088646M Inject a stub parser
	 * 
	 * @param parser
	 *            stub parser to be injected
	 */
	public void injectParser(Parser parser) {
		this.parser = parser;
	}

	/**
	 * @@author A0088646M Execute command
	 * 
	 * @param command
	 *            Command string input from user
	 * @return command action
	 * @throws UserCommandException
	 */
	public Command execute(String command) throws UserCommandException {

		ParsedCommand parsedCommand = null;
		try {
			parsedCommand = parser.parse(command);
		} catch (NumberFormatException exception) {
			UserCommandException userCommandException = new UserCommandException(MESSAGE_INVALID_COMMAND);
			userCommandException.setCommand(command);
			throw userCommandException;
		}

		assert (parsedCommand != null);
		if (parsedCommand.getCommand() != null) {
			try {
				performCommand(parsedCommand);
			} catch (UserCommandException exception) {
				exception.setCommand(command);
				throw exception;
			} catch (Exception e) {
				throw e;
			}
		}

		return parsedCommand.getCommand();
	}

	/**
	 * @@author A0088646M Perform the parsed command
	 * 
	 * @param parsedCommand
	 *            parsedCommand from the parser
	 */
	private void performCommand(ParsedCommand parsedCommand) throws UserCommandException {
		List<Event> eventList = new ArrayList<>();

		try {
			eventList = eventHandler.execute(parsedCommand);
			updateFilteredList(parsedCommand, eventList);
		} catch (UserCommandException e) {
			throw e;
		} catch (Exception e) {
			// Just in case it is not UserCommandException
			e.printStackTrace();
		}

		updateEvent(eventList);
	}

	private void updateFilteredList(ParsedCommand parsedCommand, List<Event> eventList) {
		if (parsedCommand.getCommand() == Command.FILTER) {
			filteredEvents = eventList;
		}
	}

	/**
	 * @@author A0088646M Update event of the last action
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
	 * @@author A0088646M Gets the event of the last action performed
	 * 
	 * @return event which the last action has performed on
	 */
	public Event getEvent() {
		return event;
	}

	/**
	 * @@author A0088646M Gets all available events
	 * 
	 * @return all events
	 */
	public List<Event> getAllEvents() {
		List<Event> savedEvents = eventHandler.getAllEvents();

		// Clear Events
		this.events.clear();

		// Perform recurrence check
		getRecurrence(savedEvents);

		// Sort
		sortByStartDateTime(this.events);

		return this.events;
	}

	private void getRecurrence(List<Event> savedEvents) {
		for (int i = 0; i < savedEvents.size(); i++) {
			Event event = savedEvents.get(i);
			this.events.add(event.getRecurredEvent());
		}
	}

	/* @@author A0088646M */
	private List<Event> getAllMonthEvents(int year, int month) {
		List<Event> savedEvents = eventHandler.getAllEvents();

		// Clear Events
		this.events.clear();

		// Perform recurrence check
		getRecurrence(year, month, savedEvents);

		return this.events;
	}

	private void getRecurrence(int year, int month, List<Event> savedEvents) {
		for (int i = 0; i < savedEvents.size(); i++) {
			Event event = savedEvents.get(i);
			this.events.addAll(event.getRecurredEvents(year, month));
		}
	}

	/**
	 * @@author A0088646M Gets a list of filtered events
	 * 
	 * @return list of filtered events
	 */
	public List<Event> getFilteredEvents() {
		return filteredEvents;
	}

	/**
	 * @@author A0088646M Gets all events within a specific month excluding
	 *          Floating tasks
	 * 
	 * @param year
	 *            year of event (e.g. 2015)
	 * @param month
	 *            month of event starting from 1 as January and 12 as December
	 * @return list of events in the month
	 */
	public List<Event> getMonthEvents(int year, int month) {
		return getMonthEvents(year, month, false);
	}

	/**
	 * @@author A0088646M Gets all events within a specific month
	 * 
	 * @param year
	 *            year of event (e.g. 2015)
	 * @param month
	 *            month of event starting from 1 as January and 12 as December
	 * @param floating
	 *            whether to include floating tasks
	 * @return list of events in the month
	 */
	public List<Event> getMonthEvents(int year, int month, boolean floating) {
		events = getAllMonthEvents(year, month);
		List<Event> monthEvents = new ArrayList<>();

		filterToMonth(year, month, monthEvents, floating);
		sortByStartDateTime(monthEvents);

		return monthEvents;
	}

	/**
	 * @@author A0088646M Gets all events within a specific day excluding
	 *          Floating tasks
	 * 
	 * @param year
	 *            year of event (e.g. 2015)
	 * @param month
	 *            month of event starting from 1 as January and 12 as December
	 * @param day
	 *            date of event starting from 1 as first day of month
	 * @return list of events in the day
	 */
	public List<Event> getDayEvents(int year, int month, int day) {
		return getDayEvents(year, month, day, false);
	}

	/**
	 * @@author A0088646M Gets all events within a specific day
	 * 
	 * @param year
	 *            year of event (e.g. 2015)
	 * @param month
	 *            month of event starting from 1 as January and 12 as December
	 * @param day
	 *            date of event starting from 1 as first day of month
	 * @param floating
	 *            whether to include floating tasks
	 * @return list of events in the day
	 */
	public List<Event> getDayEvents(int year, int month, int day, boolean floating) {
		events = getAllMonthEvents(year, month);
		List<Event> dayEvents = new ArrayList<>();

		filterToDay(year, month, day, dayEvents, floating);
		sortByStartDateTime(dayEvents);

		return dayEvents;
	}

	/* @@author A0088646M */
	private void sortByStartDateTime(List<Event> monthEvents) {
		Collections.sort(monthEvents, new Comparator<Event>() {

			@Override
			public int compare(Event e1, Event e2) {
				int compareResult = 0;
				long e1start = 0;
				long e2start = 0;

				// Both Non-floating
				if (e1.getStartDateTime() != null && e2.getStartDateTime() != null) {
					e1start = e1.getStartDateTime().getTimeInMillis();
					e2start = e2.getStartDateTime().getTimeInMillis();

					// Different start time
					if (e1start != e2start) {
						compareResult = e1start - e2start > 0 ? 1 : -1;
					}
					// Same start time, compare Title
					else {
						compareResult = e1.getTitle().compareToIgnoreCase(e2.getTitle());
					}
				}
				// Both Floating, compare Title
				else if (e1.getStartDateTime() == null && e2.getStartDateTime() == null) {
					compareResult = e1.getTitle().compareToIgnoreCase(e2.getTitle());
				}
				// E1 Floating
				else if (e1.getStartDateTime() == null) {
					compareResult = 1;
				}
				// E2 Floating
				else if (e2.getStartDateTime() == null) {
					compareResult = -1;
				}
				return compareResult;
			}

		});
	}

	/* @@author A0088646M */
	private void filterToDay(int year, int month, int day, List<Event> dayEvents, boolean floating) {
		// Filter into day
		for (int i = 0; i < events.size(); i++) {
			Event event = events.get(i);

			if (isInDay(event, year, month, day, floating)) {
				dayEvents.add(event);
			}
		}
	}

	/* @@author A0088646M */
	private void filterToMonth(int year, int month, List<Event> monthEvents, boolean floating) {
		// Filter into month
		for (int i = 0; i < events.size(); i++) {
			Event event = events.get(i);

			if (isInMonth(event, year, month, floating)) {
				monthEvents.add(event);
			}
		}
	}

	/* @@author A0088646M */
	private boolean isInDay(Event event, int year, int month, int day, boolean floating) {
		boolean isInThisDay = false;

		Calendar thisDay = Calendar.getInstance();
		Calendar nextDay = Calendar.getInstance();

		// Set to start of day
		setDayAnchor(year, month, day, thisDay, nextDay);

		// Check Floating Task
		if (event.getStartDateTime() == null && event.getEndDateTime() == null) {
			if (floating == true) {
				isInThisDay = true;
			}
		}
		// Check Start Date
		else if (isWithinDay(event.getStartDateTime(), thisDay, nextDay)) {
			isInThisDay = true;
		}
		// Check End Date
		else if (isWithinDay(event.getEndDateTime(), thisDay, nextDay)) {
			isInThisDay = true;
		}
		// Event spans through entire day
		else if (coversDay(event, thisDay, nextDay)) {
			isInThisDay = true;
		}

		return isInThisDay;
	}

	/* @@author A0088646M */
	private boolean isInMonth(Event event, int year, int month, boolean floating) {
		boolean isInThisMonth = false;
		Calendar thisMonth = Calendar.getInstance();
		Calendar nextMonth = Calendar.getInstance();

		// Set to start of month
		setMonthAnchor(year, month, thisMonth, nextMonth);

		// Check Floating Task
		if (event.getStartDateTime() == null && event.getEndDateTime() == null) {
			if (floating == true) {
				isInThisMonth = true;
			}
		}
		// Check Start Date
		else if (isWithinMonth(event.getStartDateTime(), thisMonth, nextMonth)) {
			isInThisMonth = true;
		}
		// Check End Date
		else if (isWithinMonth(event.getEndDateTime(), thisMonth, nextMonth)) {
			isInThisMonth = true;
		}
		// Event spans through entire month
		else if (coversMonth(event, thisMonth, nextMonth)) {
			isInThisMonth = true;
		}

		return isInThisMonth;
	}

	/* @@author A0088646M */
	private boolean coversDay(Event event, Calendar thisDay, Calendar nextDay) {
		boolean coveringDay = false;
		Calendar start = event.getStartDateTime();
		Calendar end = event.getEndDateTime();

		if (start != null && end != null) {
			boolean startBefore = start.before(thisDay);
			boolean endAfter = end.after(nextDay);
			coveringDay = startBefore && endAfter;
		}

		return coveringDay;
	}

	/* @@author A0088646M */
	private boolean coversMonth(Event event, Calendar thisMonth, Calendar nextMonth) {
		boolean coveringMonth = false;
		Calendar start = event.getStartDateTime();
		Calendar end = event.getEndDateTime();

		if (start != null && end != null) {
			boolean startBefore = start.before(thisMonth);
			boolean endAfter = end.after(nextMonth);

			coveringMonth = startBefore && endAfter;
		}
		return coveringMonth;
	}

	/* @@author A0088646M */
	private void setDayAnchor(int year, int month, int day, Calendar thisDay, Calendar nextDay) {
		// Reset
		thisDay.setTimeInMillis(0);
		nextDay.setTimeInMillis(0);

		// Set to start of day
		thisDay.set(year, (month + 11) % 12, day, 0, 0, 0);
		nextDay.set(year, (month + 11) % 12, day, 0, 0, 0);
		nextDay.add(Calendar.DATE, 1);

	}

	/* @@author A0088646M */
	private void setMonthAnchor(int year, int month, Calendar thisMonth, Calendar nextMonth) {
		// Reset
		thisMonth.setTimeInMillis(0);
		nextMonth.setTimeInMillis(0);

		// Set to start of month
		thisMonth.set(year, (month + 11) % 12, 1, 0, 0, 0);
		nextMonth.set(year, (month + 11) % 12, 1, 0, 0, 0);
		nextMonth.add(Calendar.MONTH, 1);

	}

	/* @@author A0088646M */
	private boolean isWithinDay(Calendar eventDateTime, Calendar thisDay, Calendar nextDay) {
		boolean isWithin = false;

		if (eventDateTime != null) {
			isWithin = eventDateTime.compareTo(thisDay) >= 0 && eventDateTime.before(nextDay);
		}

		return isWithin;
	}

	/* @@author A0088646M */
	private boolean isWithinMonth(Calendar eventDateTime, Calendar thisMonth, Calendar nextMonth) {
		boolean isWithin = false;
		if (eventDateTime != null) {
			isWithin = eventDateTime.compareTo(thisMonth) >= 0 && eventDateTime.before(nextMonth);
		}
		return isWithin;
	}

	/**
	 * @@author A0088646M Set OnRemindListener
	 * 
	 * @param listener
	 *            listener for reminder
	 */
	public void setOnRemindListener(OnRemindListener listener) {
		// Set in event handler
		if (listener != null) {
			eventHandler.setOnRemindListener(listener);
		}
	}

	/**
	 * @@author A0088646M Gets number of milliseconds until the next event
	 * 
	 * @return number of milliseconds until next event. If no next event, -1
	 *         will be returned.
	 */
	public long getTimeToNextEvent() {
		long time = -1;

		List<Event> events = getAllEvents();
		sortByStartDateTime(events);

		for (int i = 0; i < events.size(); i++) {
			Calendar startTime = events.get(i).getStartDateTime();
			Calendar now = Calendar.getInstance();

			if (startTime != null && startTime.after(now)) {
				time = getTimeDifference(startTime, now);
				break;
			}
		}

		return time;
	}

	private long getTimeDifference(Calendar startTime, Calendar now) {
		return startTime.getTimeInMillis() - now.getTimeInMillis();
	}

	/**
	 * @@author A0088646M Gets event based on its actual id
	 * @param actualId
	 *            the actual id of the event
	 * @return the event object
	 */
	public Event getEvent(String actualId) {
		Event event = null;

		event = eventHandler.getEvent(actualId);

		return event;
	}
}
