package calendrier;

import java.util.ArrayList;
import java.util.List;

import utils.Event;
import utils.ParsedCommand;

/**
 * This class is used to create event objects using data passed in from
 * MainLogic. These event objects are then sent to StorageManager.
 * 
 * @author Shan
 *
 */
public class EventHandler {

	StorageManager manage;
	ArrayList<Event> events = new ArrayList<>();
	EventGenerator gen;

	public EventHandler() {
		manage = new StorageManager();
		gen = new EventGenerator();
	}


	public ArrayList<Event> execute(ParsedCommand pc) throws Exception {
		Event newEvent;
		// if new add/update - generate Event
		newEvent = gen.createEvent(pc);
		return events;
	}
	
	public Event add(String identifier, Event eventDetails) {
		Event newEvent = new Event();


		// else if view

		// else if remove/delete

		// else

		// add new event to structure of events
		events.add(newEvent);

		return newEvent;
	}

	public Event add(Event event) {
		manage.add(event);
		events.add(event);
		return event;
	}

	public Event remove(Event event) {
		manage.remove(event);
		events.remove(event);
		return event;
	}

	
	/**
	 * NEED TO REFACTOR IN ORDER TO UTILIZE SOMETHING OTHER THAN IDENTIFIER
	 * @param identifier
	 * @param eventDetails
	 * @return
	 */
	public Event update(String identifier, Event eventDetails) {
		Event eventToBeUpdated = new Event();

		// find event to be updated
		for (Event e : events) {
			if (e.getId().equals(identifier)) {
				eventToBeUpdated = e;
				events.remove(e);
				break;
			}
		}
		manage.update(eventToBeUpdated, eventDetails);
		events.add(eventDetails);
		return eventDetails;
	}

	/**
	 * NEED TO BE REFACTORED
	 * @param identifier
	 * @return
	 */
	public Event view(String identifier) {
		Event eventToBeViewed = new Event();
		for (Event e : events) {
			if (e.getId().equals(identifier)) {
				eventToBeViewed = e;
			}
		}
		return eventToBeViewed;
	}

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
