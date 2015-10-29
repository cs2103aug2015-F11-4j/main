package calendrier;

import java.util.ArrayList;
import java.util.Random;

import utils.Event;

import utils.ParsedCommand;

public class EventGenerator {
	final int RANDOM_LIMIT = 5000;
	Random rand;
	int currentId;
	
	public EventGenerator() {
		currentId = 0;
		rand = new Random();
	}
	
	public void setCurrentID(int id) {
		currentId = id;
	}
	
	public String getCurrentIDAsString() {
		return currentId + "";
	}
	
	/**
	 * Creates an event from a given ParsedCommand object
	 * 
	 * @param pc
	 * @return Event e		The event created from the generator
	 */
	public Event createEvent(ParsedCommand pc) {
		Event e = new Event();
		
		if (pc.getId() == null) {
			currentId = rand.nextInt(RANDOM_LIMIT);
			e.setId(getCurrentIDAsString());
		} else {
			e.setId((pc.getId()));
		}
		e.setTitle(pc.getTitle());
		e.setStartDateTime(pc.getStartDateTime());
		e.setEndDateTime(pc.getEndDateTime());
		e.setPriority(pc.getPriority());
		e.setLocation(pc.getLocation());
		e.setNotes(pc.getNotes());
		e.setReminder(pc.getReminder());
		e.addGroup(pc.getGroup());
		e.setMainId(pc.getMainId());

		return e;
	}
	
	/**
	 * Overloaded method used to create an event from a given string
	 * @param s
	 * @return Event e		The event created from the generator
	 */
	public Event createEvent(String s) {
		Event e = new Event();
		e.fromString(s);
		return e;
	}
	
	/**
	 * Method to create multiple event instances from a given arraylist of strings
	 * @param strings
	 * @return
	 */
	public ArrayList<Event> createMultipleEvents(ArrayList<String> strings) {
		ArrayList<Event> events = new ArrayList<>();
		for (String s : strings) {
			Event e = createEvent(s);
			events.add(e);
		}
		return events;
	}
}