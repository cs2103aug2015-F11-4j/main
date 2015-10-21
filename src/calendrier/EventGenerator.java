package calendrier;

import utils.Event;

import utils.ParsedCommand;

public class EventGenerator {
	
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
		e.addGroup(pc.getGroup());
		return e;
	}
	
	/**
	 * Overloaded method used to create an event from a given string
	 * @param s
	 * @return Event e		The event created from the generator
	 */
	public Event createEvent(String s) {
		Event e = new Event();
		return e;
	}
}