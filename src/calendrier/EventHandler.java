package calendrier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.logging.Logger;

import utils.Command;
import utils.Event;
import utils.IdMapper;
import utils.OnRemindListener;
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
	ArrayList<Event> initialEvents = new ArrayList<>();
	EventGenerator generator;
	ReminderManager reminders;
	Event previousEvent;
	Event beforeUpdate;
	Logger log;
	

	public EventHandler() {
		manage = new StorageManager();
		generator = new EventGenerator();
		reminders = new ReminderManager();
		previousEvent = new Event();
		beforeUpdate = new Event();
		log = Logger.getLogger(EventHandler.class.getName());
		history = new Stack<>();
	}

	public void injectStorageManager(StorageManager manager) {
		this.manage = manager;
	}
	
	public void setOnRemindListener(OnRemindListener listen) {
		reminders.setOnRemindListener(listen);
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
			eventsReturned.add(updatedEvent);
		} else if (pc.getCommand() == Command.VIEW) {
			Event viewedEvent = view(pc);
			eventsReturned.add(viewedEvent);
		} else if (pc.getCommand() == Command.VIEW_ALL) {
			eventsReturned = events;
		} else if (pc.getCommand() == Command.UNDO) {
			undo();
			eventsReturned.addAll(events);
		} else if (pc.getCommand() == Command.UNDELETE) {
			undo();
			eventsReturned.addAll(events);
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
		initialEvents = generator.createMultipleEvents(eventsFromStorage);
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
			if (e.getId().equals(IdMapper.getInstance().getActualId(pc.getId()))) {
				eventToBeViewed = e;
			}
		}
		return eventToBeViewed;
	}

	/**
	 * 
	 * @returns the undone event
	 */
	public void undo() throws Exception {
		if (history.isEmpty()) {
			// do nothing!
			throw new Exception("ERROR - CANNOT UNDO");

		} else if (history.size() == 1) {
			// empty history after undo
			history.pop();
			events.clear();
			// populate with initial events on load
			for (Event e : initialEvents) {
				events.add(e);
			}

		} else {
			// regular undo of history
			history.pop();
			events = history.peek();
		}
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
			reminders.addReminder(event);
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
			if (e.getId().equals(IdMapper.getInstance().getActualId(pc.getId()))) {
				eventToBeRemoved = e;
				break;
			}
		}
		events.remove(eventToBeRemoved);
		reminders.removeReminder(eventToBeRemoved);
		saveHistory();
		manage.save(events);
		return eventToBeRemoved;
	}

	/**
	 * Updates an event identified by the ParsedCommand pc
	 * 
	 * @param pc
	 * @return eventToBeUpdated
	 * @throws Exception
	 */
	public Event update(ParsedCommand pc) throws Exception {
		String Id  = IdMapper.getInstance().getActualId(pc.getId());
		Event newEvent = generator.createEvent(pc);
		newEvent.setId(Id);
		Event oldEvent = null;

		// find event to be updated
		for (Event e : events) {
			if (e.getId().equals(IdMapper.getInstance().getActualId(pc.getId()))) {
				oldEvent = e;
				events.remove(e);
				break;
			}
		}
		if (oldEvent == null) {
			throw new Exception("ERROR - That event does not exist!");

		} else {
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
			reminders.updateReminder(newEvent);
			saveHistory();
			manage.save(events);
			return newEvent;
		}
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
	
	public List<Event> sortEvents() {
		Collections.sort(events);
		return events;
	}
}
