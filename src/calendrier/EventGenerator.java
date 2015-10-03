package calendrier;

import utils.Event;

import utils.ParsedCommand;

public class EventGenerator {
	
	public EventGenerator() {
		
	}
	
	/**
	 * Creates an event from a given ParsedCommand object
	 * 
	 * @param pc
	 * @return Event e		The event created from the generator
	 */
	public Event createEvent(ParsedCommand pc) {
		Event e = new Event();
		
		e.setId((pc.getId()));
		e.setTitle(pc.getTitle());
		e.setStartDateTime(pc.getStartDateTime());
		e.setEndDateTime(pc.getEndDateTime());
		e.setPriority(pc.getPriority());
		e.setLocation(pc.getLocation());
		e.setNotes(pc.getNotes());
		e.setReminder(pc.getReminder());
		
		// figure out group implementation
//		e.setGroups(pc.getGroup());
		
		return e;
	}
}