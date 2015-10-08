package stub;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

import calendrier.StorageManager;
import utils.Event;

public class StorageManagerStub extends StorageManager {

	Stack<HashSet<Event>> history;
	HashSet<Event> store;
	String storageLocation;

	public StorageManagerStub() {
		store = new HashSet<>();
		history = new Stack<>();
	}

	public void add(Event e) {
		store.add(e);
		HashSet<Event> tempStore = new HashSet<>();

		for (Event event : store) {
			tempStore.add(event);
		}
		history.push(tempStore);
	}

	public void remove(Event e) {

		// find event ID of event to be removed
		Event eventToRemove = findEvent(e);

		// remove Event from storage
		store.remove(eventToRemove);
		assert (!store.contains(eventToRemove));

		HashSet<Event> tempStore = new HashSet<>();

		for (Event event : store) {
			tempStore.add(event);
		}
		history.push(tempStore);
	}

	public void update(Event oldEvent, Event newEvent) {
		// remove old event
		store.remove(oldEvent);

		// add new event
		store.add(newEvent);
		HashSet<Event> tempStore = new HashSet<>();

		for (Event e : store) {
			tempStore.add(e);
		}
		history.push(tempStore);
	}

	public void setStorageLocation(String s) {
		storageLocation = s;
	}

	public void undo() {
		history.pop();
		if (!history.empty()) {
			store = history.peek();
		} else {
			store.clear();
		}
	}

	public List<Event> load() {
		ArrayList<Event> events = new ArrayList<>();
		for (Event e : store) {
			events.add(e);
		}
		return events;
	}

	/**
	 * returns the event with the same ID as the event passed in
	 * 
	 * @param e
	 * @return
	 */
	private Event findEvent(Event e) {
		Event foundEvent = new Event();
		for (Event temp : store) {
			if (e.getId().equals(temp.getId())) {
				foundEvent = temp;
				break;
			}
		}
		return foundEvent;
	}

}
