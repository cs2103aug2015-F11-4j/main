package calendrier;

import java.util.ArrayList;
import java.util.List;

import utils.Event;

public class EventHandler {
	
	public EventHandler() {
		StorageManager manage = new StorageManager();
	}
	
	public Event add(String identifier, Event event){
		Event addedEvent = new Event();
		return null;
	}
	
	public Event remove(String identifier, Event event){
		return null;
	}
	
	public Event update(String identifier, Event event){
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
