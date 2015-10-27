package calendrier;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.logging.Logger;

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
	Stack<ArrayList<Event>> history;
	ArrayList<Event> events = new ArrayList<>();
	EventGenerator generator;
	Event previousEvent;
	Event beforeUpdate;
	Logger log;

	public EventHandler() {
		manage = new StorageManager();
		generator = new EventGenerator();
		previousEvent = new Event();
		beforeUpdate = new Event();
		log = Logger.getLogger(EventHandler.class.getName());
		history = new Stack<>();
	}

	public void injectStorageManager(StorageManager manager) {
		this.manage = manager;
	}

	/**
	 * Method called by external classes Determines the type of command to
	 * execute based on parameter pc
	 * 
	 * @param pc
	 * @return eventsReturned - an arraylist of the events that were executed
	 * @throws Exception
	 */
	public ArrayList<Event> execute(ParsedCommand pc) throws Exception {
		ArrayList<Event> eventsReturned = new ArrayList<>();

		assert (pc != null);

		if (pc.getCommand() == Command.ADD) {
			Event newEvent = generator.createEvent(pc);
			assert (newEvent != null);
			add(newEvent);
			eventsReturned.add(newEvent);
		} else if (pc.getCommand() == Command.DELETE) {
			Event removedEvent = remove(pc);
			eventsReturned.add(removedEvent);
		} else if (pc.getCommand() == Command.UPDATE) {
			Event updatedEvent = update(pc);
//			history.push(pc);
			eventsReturned.add(updatedEvent);
		} else if (pc.getCommand() == Command.VIEW) {
			Event viewedEvent = view(pc);
			eventsReturned.add(viewedEvent);
		} else if (pc.getCommand() == Command.VIEW_ALL) {
			eventsReturned = events;
		} else if (pc.getCommand() == Command.UNDO) {
			Event undoneEvent = undo();
			eventsReturned.add(undoneEvent);
		} else if (pc.getCommand() == Command.UNDELETE) {
			Event undeletedEvent = undo();
//			history.push(pc);
			eventsReturned.add(undeletedEvent);
		}

		else if (pc.getCommand() == Command.SEARCH) {
			eventsReturned = search(pc);

		} else if (pc.getCommand() == Command.STORAGE_LOCATION) {
			setStorageAndLoadEvents(pc);

		} else {
			// EXIT, PREV, NEXT commands, do nothing!
		}
		return eventsReturned;
	}

	private void setStorageAndLoadEvents(ParsedCommand pc) {
		manage.setStorageLocation(pc.getStorageLocation());

		ArrayList<String> eventsFromStorage = (ArrayList<String>) manage.load();
		events = generator.createMultipleEvents(eventsFromStorage);
	}

	public ArrayList<Event> search(ParsedCommand pc) {
		ArrayList<Event> searchedEvents = new ArrayList<>();
		for (Event e : events) {
			if (e.getGroups().contains(pc.getGroup())) {
				searchedEvents.add(e);
			} else if (e.getPriority().equals(pc.getPriority())) {
				searchedEvents.add(e);
			}
		}
		return searchedEvents;
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
	 * 
	 * @returns the undone event
	 */
	public Event undo() {
		Event undone = new Event();
		if (history.isEmpty()) {
			events.clear();
		} else if (history.size() == 1) {
			// empty history after undo
			history.pop();
			events.clear();
		} else {
			// regular undo of history
			history.pop();
			events = history.peek();
		}
		return undone;
	}

	/**
	 * 
	 * @param event
	 * @return
	 */
	public Event add(Event event) throws Exception {
		previousEvent = event;

		if (checkTimeConflict(event)) {
			throw new Exception("ERROR - TIME CONFLICT");
		} else {
			events.add(event);
			manage.save(events);
			saveHistory();
		}
		return event;
	}

	private void saveHistory() {
		ArrayList<Event> tempEvents = new ArrayList<>();
		for (Event e : events) {
			tempEvents.add(e);
		}
		history.add(tempEvents);
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
			if (e.getId().equals(pc.getId())) {
				eventToBeRemoved = e;
				break;
			}
		}
		events.remove(eventToBeRemoved);
		saveHistory();
		manage.save(events);
		return eventToBeRemoved;
	}

	/**
	 * Updates an event identified by the ParsedCommand pc
	 * 
	 * @param pc
	 * @return eventToBeUpdated
	 */
	public Event update(ParsedCommand pc) {
		Event newEvent = generator.createEvent(pc);
		Event oldEvent = new Event();

		// find event to be updated
		for (Event e : events) {
			if (e.getId().equals(pc.getId())) {
				oldEvent = e;
				events.remove(e);
				break;
			}
		}
		
		// ensure updatedEvent contains all relevant info from oldEvent
		if (newEvent.getTitle() == null) {
			newEvent.setTitle(oldEvent.getTitle());
		}
		if (newEvent.getStartDateTime() == null) {
			newEvent.setStartDateTime(oldEvent.getStartDateTime());
		}
		if (newEvent.getEndDateTime() == null) {
			newEvent.setEndDateTime(oldEvent.getEndDateTime());
		}
		if (newEvent.getPriority() == null) {
			newEvent.setPriority(oldEvent.getPriority());
		}
		if (newEvent.getLocation() == null) {
			newEvent.setLocation(oldEvent.getLocation());
		}
		if (newEvent.getNotes() == null) {
			newEvent.setNotes(oldEvent.getNotes());
		}
		if (newEvent.getReminder() == null) {
			newEvent.setReminder(oldEvent.getReminder());
		}
		if (newEvent.getGroups().isEmpty()) {
			for (String s : newEvent.getGroups()) {
				newEvent.addGroup(s);
			}
		}
		beforeUpdate = oldEvent;
		events.add(newEvent);
		saveHistory();
		manage.save(events);
		return newEvent;
	}

	/**
	 * used to check if a time conflict exists between any event in storage and
	 * the new event
	 * 
	 * @param newEvent
	 * @return
	 */
	public boolean checkTimeConflict(Event newEvent) {
		boolean conflict = false;
		for (Event e : events) {
			if (e.getStartDateTime() != null && e.getEndDateTime() != null && newEvent.getStartDateTime() != null
					&& newEvent.getEndDateTime() != null) {
				if (newEvent.getStartDateTime().before(e.getStartDateTime())
						&& newEvent.getEndDateTime().after(e.getEndDateTime())) {
					conflict = true;
				} else if (newEvent.getStartDateTime().before(e.getStartDateTime())
						&& newEvent.getEndDateTime().after(e.getStartDateTime())) {
					conflict = true;
				} else if (newEvent.getStartDateTime().before(e.getEndDateTime())
						&& newEvent.getEndDateTime().after(e.getEndDateTime())) {
					conflict = true;
				} else if (newEvent.getStartDateTime().after(e.getStartDateTime())
						&& newEvent.getEndDateTime().before(e.getEndDateTime())) {
					conflict = true;
				}
			}
		}
		return conflict;
	}


	/**
	 * Returns the list of all events
	 * 
	 * @return events
	 */
	public List<Event> getAllEvents() {
		return events;
	}
}
