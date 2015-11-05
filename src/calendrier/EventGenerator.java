package calendrier;

import java.util.ArrayList;
import java.util.Random;

import utils.Event;
import utils.IdMapper;
import utils.ParsedCommand;
import utils.Priority;

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
	 * @return Event e The event created from the generator
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
		e.setRecurrence(pc.getRecurFreq());
		e.setMainId(IdMapper.getInstance().getActualId(pc.getMainId()));
		e.setDone(pc.isDone());

		// Automated assignment
		assignGroup(e);
		assignPriority(e);

		return e;
	}

	private void assignPriority(Event e) {
		if(e.getTitle() == null){
			return;
		}
		
		String title = e.getTitle().toLowerCase();

		if (e.getPriority() == null) {
			// Keyword check for Priority
			if (title.contains("deadline")) {
				e.setPriority(Priority.VERY_HIGH);
			} else if (title.contains("meeting")) {
				e.setPriority(Priority.HIGH);
			} else if (title.contains("meetup")) {
				e.setPriority(Priority.HIGH);
			} else if (title.contains("meet")) {
				e.setPriority(Priority.HIGH);
			} else if (title.contains("breakfast")) {
				e.setPriority(Priority.MEDIUM);
			} else if (title.contains("lunch")) {
				e.setPriority(Priority.MEDIUM);
			} else if (title.contains("dinner")) {
				e.setPriority(Priority.MEDIUM);
			} else if (title.contains("tea")) {
				e.setPriority(Priority.MEDIUM);
			} else if (title.contains("supper")) {
				e.setPriority(Priority.MEDIUM);
			} else if (title.contains("brunch")) {
				e.setPriority(Priority.MEDIUM);
			} else if (title.contains("eat")) {
				e.setPriority(Priority.MEDIUM);
			} else if (title.contains("meal")) {
				e.setPriority(Priority.MEDIUM);
			} else if (title.contains("birthday")) {
				e.setPriority(Priority.LOW);
			}
		}
	}

	private void assignGroup(Event e) {
		if(e.getTitle() == null){
			return;
		}
		
		String title = e.getTitle().toLowerCase();

		if (e.getGroup() == null) {
			// Keyword check for Group
			if (title.contains("deadline")) {
				e.addGroup("deadline");
			} else if (title.contains("meeting")) {
				e.addGroup("meeting");
			} else if (title.contains("meetup")) {
				e.addGroup("meeting");
			} else if (title.contains("meet")) {
				e.addGroup("meeting");
			} else if (title.contains("breakfast")) {
				e.addGroup("meal");
			} else if (title.contains("lunch")) {
				e.addGroup("meal");
			} else if (title.contains("dinner")) {
				e.addGroup("meal");
			} else if (title.contains("tea")) {
				e.addGroup("meal");
			} else if (title.contains("brunch")) {
				e.addGroup("meal");
			} else if (title.contains("supper")) {
				e.addGroup("meal");
			} else if (title.contains("eat")) {
				e.addGroup("meal");
			} else if (title.contains("meal")) {
				e.addGroup("meal");
			} else if (title.contains("birthday")) {
				e.addGroup("birthday");
			}
		}
	}

	/**
	 * Overloaded method used to create an event from a given string
	 * 
	 * @param s
	 * @return Event e The event created from the generator
	 */
	public Event createEvent(String s) {
		Event e = new Event();
		e.fromString(s);
		return e;
	}

	/**
	 * Method to create multiple event instances from a given arraylist of
	 * strings
	 * 
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