package calendrier;

import java.util.ArrayList;
import java.util.List;

import utils.Command;
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
		ArrayList<Event> eventsReturned = new ArrayList<>();

		if (pc.getCommand() == Command.ADD) {
			Event newEvent = gen.createEvent(pc);
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
		}

		return eventsReturned;
	}

	public Event add(Event event) {
		manage.add(event);
		events.add(event);
		return event;
	}

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
	 * 
	 * @param identifier
	 * @param eventDetails
	 * @return
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
	 * 
	 * @param identifier
	 * @return
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
