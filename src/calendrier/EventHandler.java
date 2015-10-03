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

	public EventHandler() {
		manage = new StorageManager();
	}

	/**
	 * Need to implement this
	 */
	public List<Event> execute(ParsedCommand parsedCommand){
		return null;
	}
	
	public Event add(String identifier, Event eventDetails) {
		Event newEvent = new Event();

		// The following seem redundant, as the event is created in the parser
		newEvent.setId(identifier);
		newEvent.setTitle(eventDetails.getTitle());
		newEvent.setStartDateTime(eventDetails.getStartDateTime());
		newEvent.setEndDateTime(eventDetails.getEndDateTime());
		newEvent.setPriority(eventDetails.getPriority());
		newEvent.setAddLocation(eventDetails.getAddLocation());
		newEvent.setAddRecurring(eventDetails.getAddRecurring());
		newEvent.setAddTaskDescription(eventDetails.getAddTaskDescription());

		manage.add(newEvent);
		events.add(newEvent);
		return newEvent;
	}

	public Event remove(String identifier, Event eventDetails) {
		manage.remove(eventDetails);
		events.remove(eventDetails);
		return null;
	}

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

	public Event view(String identifier) {
		Event eventToBeViewed =  new Event();
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
