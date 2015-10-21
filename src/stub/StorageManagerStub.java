package stub;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

import calendrier.StorageManager;
import utils.Event;

public class StorageManagerStub extends StorageManager {

	HashSet<String> store;
	String storageLocation;

	public StorageManagerStub() {
		store = new HashSet<>();
	}
	
	public void setStorageLocation(String s) {
		storageLocation = s;
	}
	
	public List<String> load() {
		ArrayList<String> storedEvents = new ArrayList<>();
		for (String s : store) {
			storedEvents.add(s);
		}
		return storedEvents;
	}
	
	public void save(List<Event> data) {
		for (Event e : data) {
			store.add(e.toString());
		}
	}

//	public void add(Event e) {
//		store.add(e.toString());
//		HashSet<String> tempStore = new HashSet<>();
//
//		for (String s : store) {
//			tempStore.add(s);
//		}
//		history.push(tempStore);
//	}
//
//	public void remove(Event e) {
//
//		// find event ID of event to be removed
//		Event eventToRemove = findEvent(e);
//
//		// remove Event from storage
//		store.remove(eventToRemove);
//		assert (!store.contains(eventToRemove));
//
//		HashSet<Event> tempStore = new HashSet<>();
//
//		for (Event event : store) {
//			tempStore.add(event);
//		}
//		history.push(tempStore);
//	}
//
//	public void update(Event oldEvent, Event newEvent) {
//		// remove old event
//		store.remove(oldEvent);
//
//		// add new event
//		store.add(newEvent);
//		HashSet<Event> tempStore = new HashSet<>();
//
//		for (Event e : store) {
//			tempStore.add(e);
//		}
//		history.push(tempStore);
//	}

	

//	public void undo() {
//		history.pop();
//		if (!history.empty()) {
//			store = history.peek();
//		} else {
//			store.clear();
//		}
//	}

	
//
//	/**
//	 * returns the event with the same ID as the event passed in
//	 * 
//	 * @param e
//	 * @return
//	 */
//	private Event findEvent(Event e) {
//		Event foundEvent = new Event();
//		for (Event temp : store) {
//			if (e.getId().equals(temp.getId())) {
//				foundEvent = temp;
//				break;
//			}
//		}
//		return foundEvent;
//	}

}
