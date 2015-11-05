//@@author A0145143N

package calendrier;

import java.util.ArrayList;
import java.util.Calendar;
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

	public EventHandler() {
		manage = new StorageManager();
		generator = new EventGenerator();
		reminders = new ReminderManager();
		previousEvent = new Event();
		beforeUpdate = new Event();
		history = new Stack<>();
	}

	public void injectStorageManager(StorageManager manager) {
		this.manage = manager;
	}

	public void setOnRemindListener(OnRemindListener listen) {
		reminders.setOnRemindListener(listen);
	}
	
	/**
	 * returns an event that has a specific id
	 * @param id
	 * @return event that has the id specified
	 */
	public Event getEventFromID(String id) {
		Event returnedEvent = null;
		for (Event e : events) {
			if (e.getId().equals(id)) {
				returnedEvent = e;
				break;
			}
		}
		return returnedEvent;
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
//			String Id = IdMapper.getInstance().getActualId(pc.getId());
//			newEvent.setId(Id);
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
		} else if (pc.getCommand() == Command.SEARCH || pc.getCommand() == Command.FILTER) {
			eventsReturned = search(pc);

		} else if (pc.getCommand() == Command.STORAGE_LOCATION) {
			setStorageAndLoadEvents(pc);

		} else {
			// EXIT, PREV, NEXT commands, do nothing!
		}
		return eventsReturned;
	}

	private void setStorageAndLoadEvents(ParsedCommand pc) {
		assert(manage != null);
		manage.setStorageLocation(pc.getStorageLocation());

		ArrayList<String> eventsFromStorage = (ArrayList<String>) manage.load();
		events = generator.createMultipleEvents(eventsFromStorage);
		initialEvents = generator.createMultipleEvents(eventsFromStorage);
	}

	/**
	 * Search the list of events for the set of events that match the given
	 * parsed command Can search events based on the following: - startDateTime
	 * - endDateTime - group - priority
	 * 
	 * @param pc
	 * @return searchedEvents: the events that satisfy the search
	 */
	public ArrayList<Event> search(ParsedCommand pc) {
		ArrayList<Event> searchedEvents = new ArrayList<>();
		for (Event e : events) {
			if (e.getGroup().equals(pc.getGroup())) {
				searchedEvents.add(e);
			} else if (pc.getPriority() != null && e.getPriority().equals(pc.getPriority())) {
				searchedEvents.add(e);
			} else if (pc.getGroup() != null && e.getGroup().contains(pc.getGroup())) {
				searchedEvents.add(e);
			} else if (pc.getStartDateTime() != null && e.getStartDateTime().equals(pc.getStartDateTime())) {
				searchedEvents.add(e);
			} else if (pc.getEndDateTime() != null && e.getEndDateTime().equals(pc.getEndDateTime())) {
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
		Event eventToBeViewed = null;
		for (Event e : events) {
			if (e.getId().equals(IdMapper.getInstance().getActualId(pc.getId()))) {
				eventToBeViewed = e;
			}
		}
		return eventToBeViewed;
	}


	public void undo() throws Exception {
		if (history.isEmpty()) {
			throw new Exception("ERROR - CANNOT UNDO");
		} else if (history.size() == 1) {
			history.pop();
			events.clear();
			for (Event e : initialEvents) {
				events.add(e);
			}
		} else {
			history.pop();
			events = history.peek();
		}
		manage.save(events);
	}

	/**
	 * adds an event object to the current set of events
	 * 
	 * @param event
	 * @return
	 */
	public Event add(Event event) throws Exception {
		previousEvent = event;

		events.add(event);
		reminders.addReminder(event);
		manage.save(events);

		// check if newly added event is a subtask
		if (event.getMainId() != null) {
			for (Event e : events) {
				if (e.getId().equals(event.getMainId())) {
					e.addSubtask(event);
					manage.save(events);
					break;
				}
			}
		}
		saveHistory();
		return event;
	}

	/**
	 * Removes an event identified by the ParsedCommand pc
	 * 
	 * @param pc
	 * @return eventToBeRemoved
	 */
	public Event remove(ParsedCommand pc) {
		Event eventToBeRemoved = findEventToRemove(pc);

		removeFromSubtasksAndMaintask(eventToBeRemoved);
		reminders.removeReminder(eventToBeRemoved);
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
	 * @throws Exception
	 */
	public Event update(ParsedCommand pc) throws Exception {
		String Id = IdMapper.getInstance().getActualId(pc.getId());
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
			copyEventInfo(newEvent, oldEvent);
			beforeUpdate = oldEvent;
			reminders.updateReminder(newEvent);
			events.add(newEvent);
			saveHistory();
			manage.save(events);
			return newEvent;
		}
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

	/*
	 * ============== Private Methods ==============
	 */
	
	/**
	 * 
	 * @param pc
	 * @return
	 */
	private Event findEventToRemove(ParsedCommand pc) {
		Event eventToBeRemoved = new Event();
		for (Event e : events) {
			if (e.getId().equals(IdMapper.getInstance().getActualId(pc.getId()))) {
				eventToBeRemoved = e;
				break;
			}
		}
		return eventToBeRemoved;
	}

	/**
	 * removes the event from any list of subtasks it may belong to
	 * also removes any subtasks that this even may have
	 * 
	 * @param eventToBeRemoved
	 */
	private void removeFromSubtasksAndMaintask(Event eventToBeRemoved) {
		// remove this event from maintask's list of subtask
		for (Event e : events) {
			if (e.getId() == eventToBeRemoved.getMainId()) {
				e.removeSubtask(eventToBeRemoved.getId());
			}
		}
		
		// remove subtasks of this event
		for (String s : eventToBeRemoved.getSubtasks()) {
			for (Event e : events) {
				if (e.getId().equals(s)) {
					reminders.removeReminder(e);
					events.remove(e);
					break;
				}
			}
		}
	}
	
	/**
	 * Used to copy information from the old event over to the new event
	 * 
	 * @param newEvent
	 * @param oldEvent
	 */
	private void copyEventInfo(Event newEvent, Event oldEvent) {
		if (newEvent.getTitle() == null) {
			newEvent.setTitle(oldEvent.getTitle());
		}
		if (newEvent.getMainId() == null) {
			newEvent.setMainId(oldEvent.getMainId());
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
		if (newEvent.getReminder() != oldEvent.getReminder()) {
			for (Calendar c : oldEvent.getReminder()) {
				newEvent.addReminder(c);
			}
		}
		if (newEvent.getGroup() == null) {
			newEvent.addGroup(oldEvent.getGroup());
		}
		if (newEvent.getSubtasks() != oldEvent.getSubtasks()) {
			for (String s : oldEvent.getSubtasks()) {
				newEvent.addSubtask(s);
			}
		}
	}

	private void saveHistory() {
		ArrayList<Event> tempEvents = new ArrayList<>();
		for (Event e : events) {
			tempEvents.add(e);
		}
		history.add(tempEvents);
	}

	// /**
	// * used to check if a time conflict exists between any event in storage
	// and
	// * the new event
	// *
	// * @param newEvent
	// * @return
	// */
	// private boolean checkTimeConflict(Event newEvent) {
	// boolean conflict = false;
	// for (Event e : events) {
	// if (e.getStartDateTime() != null && e.getEndDateTime() != null &&
	// newEvent.getStartDateTime() != null
	// && newEvent.getEndDateTime() != null) {
	// if (newEvent.getStartDateTime().before(e.getStartDateTime())
	// && newEvent.getEndDateTime().after(e.getEndDateTime())) {
	// conflict = true;
	// } else if (newEvent.getStartDateTime().before(e.getStartDateTime())
	// && newEvent.getEndDateTime().after(e.getStartDateTime())) {
	// conflict = true;
	// } else if (newEvent.getStartDateTime().before(e.getEndDateTime())
	// && newEvent.getEndDateTime().after(e.getEndDateTime())) {
	// conflict = true;
	// } else if (newEvent.getStartDateTime().after(e.getStartDateTime())
	// && newEvent.getEndDateTime().before(e.getEndDateTime())) {
	// conflict = true;
	// }
	// }
	// }
	// return conflict;
	// }
}
