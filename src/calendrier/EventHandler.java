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
	Stack<ParsedCommand> history;
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

		assert(pc != null);

		if (pc.getCommand() == Command.ADD) {
			Event newEvent = generator.createEvent(pc);
			assert (newEvent != null);
			add(newEvent);
			history.push(pc);
			eventsReturned.add(newEvent);
		} else if (pc.getCommand() == Command.DELETE) {
			Event removedEvent = remove(pc);
			history.push(pc);
			eventsReturned.add(removedEvent);
		} else if (pc.getCommand() == Command.UPDATE) {
			Event updatedEvent = update(pc);
			history.push(pc);
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
			history.push(pc);
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

		ArrayList<String> eventsFromStorage = (ArrayList<String>)manage.load();
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
			// nothing to do!
			
		} else {
			Stack<ParsedCommand> newHistory = new Stack<>();
			for (ParsedCommand c : history) {
				newHistory.push(c);
			}
			newHistory.pop();
			events.clear();
			// run through every command so far and redo
			for (ParsedCommand c : newHistory) {
				try {
					execute(c);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return undone;
	}

	/**
	 * 
	 * @param event
	 * @return
	 */
	public Event add(Event event) {
		previousEvent = event;
		
		// check no conflicts are occuring
//		for (Event e : events) {
//			e.getStartDateTime()
//		}
		
		// if no conflicts, add
		events.add(event);
		// save to manager
		manage.save(events);
		
		// else throw an exception
		return event;
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
		// check that date does not conflict some other date

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
		manage.save(events);
		return newEvent;
	}


	/**
	 * Returns the list of all events
	 * 
	 * @return events
	 */
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
