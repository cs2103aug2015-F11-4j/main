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
	Event beforeUpdate;

	public EventHandler() {
		manage = new StorageManager();
		generator = new EventGenerator();
		commandHistory = new Stack<>();
		previousEvent = new Event();
		beforeUpdate = new Event();
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
			Event undoneEvent = undo();
			eventsReturned.add(undoneEvent);
		} else if (pc.getCommand() == Command.UNDELETE) {
			Event undeletedEvent = undo();
			eventsReturned.add(undeletedEvent);
		}

		else if (pc.getCommand() == Command.FILTER) {
			eventsReturned = filter(pc);

		} else if (pc.getCommand() == Command.STORAGE_LOCATION) {
			setStorage(pc);

		} else {
			// throw an exception indicating a command was blank
		}

		commandHistory.push(pc);
		return eventsReturned;
	}

	private void setStorage(ParsedCommand pc) {
		manage.setStorageLocation(pc.getStorageLocation());
	}

	private ArrayList<Event> filter(ParsedCommand pc) {
		ArrayList<Event> filteredEvents = new ArrayList<>();

		for (Event e : events) {
			if (e.getGroups().contains(pc.getGroup())) {
				filteredEvents.add(e);
			} else if (e.getPriority().equals(pc.getPriority())) {
				filteredEvents.add(e);
			}
		}

		return filteredEvents;
	}

	public Event undo() {
		Event undone = new Event();
		ParsedCommand lastCommand = commandHistory.pop();
		if (lastCommand.getCommand() == Command.ADD) {
			undone = events.get(events.size() - 1);
			events.remove(events.size() - 1);
		} else if (lastCommand.getCommand() == Command.DELETE) {
			events.add(previousEvent);
			undone = previousEvent;
		} else if (lastCommand.getCommand() == Command.UPDATE) {
			events.remove(events.size() - 1);
			events.add(beforeUpdate);
			undone = beforeUpdate;
		} else {

		}

		manage.undo();
		return undone;
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
			if (e.getId().equals(pc.getId())) {
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
		Event newEvent = generator.createEvent(pc);
		Event oldEvent = new Event();

		// find event to be updated
		for (Event e : events) {
			if (e.getId().equals(pc.getId())) {
				oldEvent = e;
				events.remove(e);
				break;
			}
		}
		 manage.update(oldEvent, newEvent);
		// PROBLEM WITH STORAGE MANAGER

		// ensure updatedEvent contains all relevant info from oldEvent
		if (newEvent.getTitle() == null) {
			newEvent.setTitle(oldEvent.getTitle());
		}
		if (newEvent.getStartDateTime() == null) {
			newEvent.setStartDateTime(oldEvent.getStartDateTime());
		}
		if (newEvent.getEndDateTime() == null) {
			newEvent.setEndDateTime(oldEvent.getEndDateTime());
		}
		if (newEvent.getPriority() == null) {
			newEvent.setPriority(oldEvent.getPriority());
		}
		if (newEvent.getLocation() == null) {
			newEvent.setLocation(oldEvent.getLocation());
		}
		if (newEvent.getNotes() == null) {
			newEvent.setNotes(oldEvent.getNotes());
		}
		if (newEvent.getReminder() == null) {
			newEvent.setReminder(oldEvent.getReminder());
		}
		if (newEvent.getGroups().isEmpty()) {
			for (String s : newEvent.getGroups()) {
				newEvent.addGroup(s);
			}
		}

		beforeUpdate = oldEvent;
		events.add(newEvent);
		return newEvent;
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
