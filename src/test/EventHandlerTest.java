package test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;

import calendrier.EventHandler;
import utils.Event;
import utils.Priority;

public class EventHandlerTest {

	// protected void setUp() {
	//
	// }

	@Test
	public void testAddEvent() {

		// simulating calendar inputs from a command?
		Calendar startDate = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		startDate.set(2015, 9, 20, 10, 33, 25);
		Calendar endDate = Calendar.getInstance();
		endDate.set(2015, 9, 21, 11, 34, 26);

		// simulating inputs from a parsed command
		String title = "testAdd";
		String location = "Holland Village";
		String eventId = "ADD1";
		String description = "buy groceries";
		String recurring = "yes";

		Event newEvent = new Event();

		// adding attributes to event
		newEvent.setId(eventId);
		newEvent.setTitle(title);
		newEvent.setStartDateTime(startDate);
		newEvent.setEndDateTime(endDate);
		newEvent.setPriority(utils.Priority.HIGH);
		newEvent.setAddLocation(location);
		newEvent.setAddRecurring(recurring);
		newEvent.setAddTaskDescription(description);

		// Create EventHandler()
		EventHandler handle = new EventHandler();
		newEvent = handle.add(eventId, newEvent);

		// Tests
		assertEquals(eventId, newEvent.getId());
		assertEquals(title, newEvent.getTitle());
		assertEquals(startDate, newEvent.getStartDateTime());
		assertEquals(endDate, newEvent.getEndDateTime());
		assertEquals(utils.Priority.HIGH, newEvent.getPriority());
		assertEquals(location, newEvent.getAddLocation());
		assertEquals(recurring, newEvent.getAddRecurring());
		assertEquals(description, newEvent.getAddTaskDescription());
		assertTrue(handle.getAllEvents().contains(newEvent));
	}

	// @Test
	// public void testUndoEvent() {
	//
	// }

	@Test
	public void testRemoveEvent() {

		// simulating calendar inputs from a command?
		Calendar startDate = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		startDate.set(2015, 9, 20, 10, 33, 25);
		Calendar endDate = Calendar.getInstance();
		endDate.set(2015, 9, 21, 11, 34, 26);

		// simulating inputs from a parsed command
		String title = "testAdd";
		String location = "Holland Village";
		String eventId = "ADD1";
		String description = "buy groceries";
		String recurring = "yes";

		Event newEvent = new Event();

		// adding attributes to event
		newEvent.setId(eventId);
		newEvent.setTitle(title);
		newEvent.setStartDateTime(startDate);
		newEvent.setEndDateTime(endDate);
		newEvent.setPriority(utils.Priority.HIGH);
		newEvent.setAddLocation(location);
		newEvent.setAddRecurring(recurring);
		newEvent.setAddTaskDescription(description);

		// Create EventHandler()
		EventHandler handle = new EventHandler();
		newEvent = handle.add(eventId, newEvent);

		handle.remove(eventId, newEvent);

		// Test
		assertFalse(handle.getAllEvents().contains(newEvent));
	}

	@Test
	public void testUpdateEvent() {

		// simulating calendar inputs from a command?
		Calendar startDate = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		startDate.set(2015, 9, 20, 10, 33, 25);
		Calendar endDate = Calendar.getInstance();
		endDate.set(2015, 9, 21, 11, 34, 26);

		// simulating inputs from a parsed command
		String title = "testAdd";
		String location = "Holland Village";
		String eventId = "GROC1";
		String description = "buy groceries";
		String recurring = "no";

		Event oldEvent = new Event();

		// adding attributes to oldEvent
		oldEvent.setId(eventId);
		oldEvent.setTitle(title);
		oldEvent.setStartDateTime(startDate);
		oldEvent.setEndDateTime(endDate);
		oldEvent.setPriority(utils.Priority.HIGH);
		oldEvent.setAddLocation(location);
		oldEvent.setAddRecurring(recurring);
		oldEvent.setAddTaskDescription(description);

		// Create EventHandler()
		EventHandler handle = new EventHandler();
		oldEvent = handle.add(eventId, oldEvent);

		// simulating calendar inputs from a command?
		Calendar newstartDate = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		startDate.set(2015, 9, 20, 10, 33, 25);
		Calendar newendDate = Calendar.getInstance();
		endDate.set(2015, 9, 21, 11, 34, 26);

		// simulating inputs from a parsed command
		String newtitle = "testUPDATE";
		String newlocation = "Orchard Road";
		String newdescription = "purchase groceries";
		String newrecurring = "yes";

		Event newEvent = new Event();
		newEvent.setId(eventId);
		newEvent.setTitle(newtitle);
		newEvent.setStartDateTime(startDate);
		newEvent.setEndDateTime(endDate);
		newEvent.setPriority(utils.Priority.HIGH);
		newEvent.setAddLocation(newlocation);
		newEvent.setAddRecurring(newrecurring);
		newEvent.setAddTaskDescription(newdescription);

		handle.update(eventId, newEvent);

		// tests
		assertFalse(handle.getAllEvents().contains(oldEvent));
		assertTrue(handle.getAllEvents().contains(newEvent));
	}

	@Test
	public void testView() {
		// simulating calendar inputs from a command?
		Calendar startDate = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		startDate.set(2015, 9, 20, 10, 33, 25);
		Calendar endDate = Calendar.getInstance();
		endDate.set(2015, 9, 21, 11, 34, 26);

		// simulating inputs from a parsed command
		String title = "testAdd";
		String location = "Holland Village";
		String eventId = "ADD1";
		String description = "buy groceries";
		String recurring = "yes";

		Event newEvent = new Event();

		// adding attributes to event
		newEvent.setId(eventId);
		newEvent.setTitle(title);
		newEvent.setStartDateTime(startDate);
		newEvent.setEndDateTime(endDate);
		newEvent.setPriority(utils.Priority.HIGH);
		newEvent.setAddLocation(location);
		newEvent.setAddRecurring(recurring);
		newEvent.setAddTaskDescription(description);

		// Create EventHandler()
		EventHandler handle = new EventHandler();
		newEvent = handle.add(eventId, newEvent);
		Event viewedEvent = handle.view(eventId);
		
		assertEquals(viewedEvent, newEvent);
	}

}
