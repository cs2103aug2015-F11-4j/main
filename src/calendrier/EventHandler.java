package calendrier;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import utils.Command;
import utils.Event;
import utils.ParsedCommand;

/**
 * This class is used to manage event objects using data passed in from
 * MainLogic. These event objects are then sent to StorageManager.
 * 
 * @author Shan
 *
 */
public class EventHandler {

	StorageManager manage;
	ArrayList<Event> events = new ArrayList<>();
	EventGenerator generator;
	Stack<ParsedCommand> commandHistory;
	Event previousEvent;

	public EventHandler() {
		manage = new StorageManager();
		generator = new EventGenerator();
		commandHistory = new Stack<>();
		previousEvent = new Event();
	}

	/**
	 * Method called by external classes Determines the type of command to
	 * execute based on parameter pc
	 * 
	 * @param pc
	 * @return eventsReturned - an arraylist of the events that were executed
	 * @throws Exception
	 */
	public ArrayList<Event> execute(ParsedCommand pc) throws Exception {
		ArrayList<Event> eventsReturned = new ArrayList<>();
		commandHistory.push(pc);

		if (pc.getCommand() == Command.ADD) {
			Event newEvent = generator.createEvent(pc);
			add(newEvent);
			eventsReturned.add(newEvent);
		} else if (pc.getCommand() == Command.DELETE) {
			Event removedEvent = remove(pc);
			eventsReturned.add(removedEvent);
		} else if (pc.getCommand() == Command.UPDATE) {
			Event updatedEvent = update(pc);
			eventsReturned.add(updatedEvent);
		} else if (pc.getCommand() == Command.VIEW) {
			Event viewedEvent = view(pc);
			eventsReturned.add(viewedEvent);
		} else if (pc.getCommand() == Command.VIEW_ALL) {
			eventsReturned = events;
		} else if (pc.getCommand() == Command.UNDO) {
			// will we need a blank message/event for this?
			undo();
		} else if (pc.getCommand() == Command.FILTER) {
			// filter??
			eventsReturned = filter(pc);

		} else if (pc.getCommand() == Command.STORAGE_LOCATION) {
			setStorage(pc);

		} else {
			// throw an exception indicating a command was blank
		}

		return eventsReturned;
	}

	private void setStorage(ParsedCommand pc) {
		manage.setStorageLocation(pc.getStorageLocation());
	}

	private ArrayList<Event> filter(ParsedCommand pc) {
		ArrayList<Event> filteredEvents = new ArrayList<>();

		// for every event in the current set of events, if any of them contain
		// stuff in the pc, select them
		for (Event e : events) {
			if (e.getGroups().contains(pc.getGroup())) {
				filteredEvents.add(e);
			} else if (e.getPriority().equals(pc.getPriority())) {
				filteredEvents.add(e);
			}
		}

		return filteredEvents;
	}

	private Event undo() {
		ParsedCommand lastCommand = commandHistory.pop();
		if (lastCommand.getCommand() == Command.ADD) {
			events.remove(events.size());
		} else if (lastCommand.getCommand() == Command.DELETE) {
			events.add(previousEvent);
		} else {

		}

		manage.undo();
		return null;
	}

	/**
	 * 
	 * @param event
	 * @return
	 */
	public Event add(Event event) {
		previousEvent = event;
		manage.add(event);
		events.add(event);

		return event;
	}

	/**
	 * Removes an event identified by the ParsedCommand pc
	 * 
	 * @param pc
	 * @return eventToBeRemoved
	 */
	public Event remove(ParsedCommand pc) {
		Event eventToBeRemoved = new Event();
		for (Event e : events) {
			if (e.getId() == pc.getId()) {
				eventToBeRemoved = e;
				break;
			}
		}
		manage.remove(eventToBeRemoved);
		events.remove(eventToBeRemoved);
		return eventToBeRemoved;
	}

	/**
	 * Updates an event identified by the ParsedCommand pc
	 * 
	 * @param pc
	 * @return eventToBeUpdated
	 */
	public Event update(ParsedCommand pc) {
		Event eventToBeUpdated = generator.createEvent(pc);
		Event oldEvent = new Event();

		// find event to be updated
		for (Event e : events) {
			if (e.getId().equals(pc.getId())) {
				oldEvent = e;
				events.remove(e);
				break;
			}
		}
		manage.update(oldEvent, eventToBeUpdated);

		// ensure updatedEvent contains all relevant info from oldEvent
		if (eventToBeUpdated.getTitle() == null) {
			eventToBeUpdated.setTitle(oldEvent.getTitle());
		} else if (eventToBeUpdated.getStartDateTime() == null) {
			eventToBeUpdated.setStartDateTime(oldEvent.getStartDateTime());
		}  else if (eventToBeUpdated.getEndDateTime() == null) {
			eventToBeUpdated.setEndDateTime(oldEvent.getEndDateTime());
		}  else if (eventToBeUpdated.getPriority() == null) {
			eventToBeUpdated.setPriority(oldEvent.getPriority());
		}  else if (eventToBeUpdated.getLocation() == null) {
			eventToBeUpdated.setLocation(oldEvent.getLocation());
		}  else if (eventToBeUpdated.getNotes() == null) {
			eventToBeUpdated.setNotes(oldEvent.getNotes());
		}  else if (eventToBeUpdated.getReminder() == null) {
			eventToBeUpdated.setReminder(oldEvent.getReminder());
		}  else if (eventToBeUpdated.getGroups() == null) {
			for (String s : eventToBeUpdated.getGroups()) {
				eventToBeUpdated.addGroup(s);
			}
		}
		
		events.add(eventToBeUpdated);
		return eventToBeUpdated;
	}

	/**
	 * Views an event identified by the ParsedCommand pc
	 * 
	 * @param pc
	 * @return eventToBeViewed
	 */
	public Event view(ParsedCommand pc) {
		Event eventToBeViewed = new Event();
		for (Event e : events) {
			if (e.getId().equals(pc.getId())) {
				eventToBeViewed = e;
			}
		}
		return eventToBeViewed;
	}

	/**
	 * Returns the list of all events
	 * 
	 * @return events
	 */
	public List<Event> getAllEvents() {
		return events;
	}

	/**
	 * NOT YET IMPLEMENTED
	 * 
	 * @return
	 */
	public List<Event> getOutstandingEvents() {
		List<Event> events = new ArrayList<>();
		return events;
	}

	/**
	 * NOT YET IMPLEMENTED
	 * 
	 * @return
	 */
	public List<Event> getFilteredEvents(Event event) {
		return null;
	}
}
