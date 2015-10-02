package calendrier;

import java.util.ArrayList;
import java.util.List;

import utils.Event;

public class EventHandler {
	
	StorageManager manage;
	ArrayList<Event> events = new ArrayList<>();
	
	public EventHandler() {
		 manage = new StorageManager();
	}
	
	public Event add(String identifier, Event eventDetails){
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
		
		return newEvent;
	}
	
	public Event remove(String identifier, Event eventDetails){
		return null;
	}
	
	public Event update(String identifier, Event eventDetails){
		return null;
	}
	
	public Event view(String identifier){
		return null;
	}
	
	public List<Event> getAllEvents(){
		return null;
	}
	
	public List<Event> getOutstandingEvents(){
		List<Event> events = new ArrayList<>();
		return events;
	}
	
	public List<Event> getFilteredEvents(Event event){
		return null;
	}
}
