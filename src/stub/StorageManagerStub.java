package stub;

import java.util.HashSet;
import calendrier.StorageManager;
import utils.Event;

public class StorageManagerStub extends StorageManager {

	HashSet<Event> store;
	String storageLocation;

	public StorageManagerStub() {
		store = new HashSet<>();
	}

	public void add(Event e) {
		store.add(e);
	}

	public void remove(Event e) {

		// find event ID of event to be removed
		Event eventToRemove = findEvent(e);

		// remove Event from storage
		store.remove(eventToRemove);
		assert(!store.contains(eventToRemove));
	}

	public void update(Event oldEvent, Event newEvent) {
		// remove old event
		store.remove(oldEvent);

		// add new event
		store.add(newEvent);
	}

	public void setStorageLocation(String s) {
		storageLocation = s;
	}

	public void undo() {

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
