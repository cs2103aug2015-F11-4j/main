package calendrier;

import java.util.ArrayList;
import java.util.List;

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

	public EventHandler() {
		manage = new StorageManager();
		generator = new EventGenerator();
	}

	/**
	 * Method called by external classes
	 * Determines the type of command to execute based on parameter pc
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
			// yet to be implemented

		} else if (pc.getCommand() == Command.UNDO) {
			// will we need a blank message/event for this?
			undo();
		} else {
			// throw an exception indicating a command was blank
		}

		return eventsReturned;
	}

	private Event undo() {
		manage.undo();
		return null;
	}

	/**
	 * 
	 * @param event
	 * @return
	 */
	public Event add(Event event) {
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
		Event eventToBeUpdated = new Event();

		// find event to be updated
		for (Event e : events) {
			if (e.getId().equals(pc.getId())) {
				eventToBeUpdated = e;
				events.remove(e);
				break;
			}
		}
		// manage.update(eventToBeUpdated, eventDetails);
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
