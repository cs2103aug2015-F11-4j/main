package test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import calendrier.EventHandler;
import utils.Event;
import utils.ParsedCommand;
import utils.Priority;

public class EventHandlerTest {
	
	ParsedCommand pc;
	Event testEvent;

	// simulating inputs from a parsed command
	String ID = "TEST";
	String title = "testing the first time";
	
	Priority priority = utils.Priority.LOW;
	String location = "Orchard Road";
	String notes = "Run at least 5 km";
	
	static Calendar startDateTime;
	static Calendar endDateTime;
	static Calendar reminder;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		startDateTime = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		startDateTime.set(2015, 9, 20, 10, 33, 25);
		
		endDateTime = Calendar.getInstance();
		endDateTime.set(2015, 11, 21, 11, 34, 26);
		
		reminder = Calendar.getInstance();
		reminder.set(2015,  10, 21, 11, 34, 26);
	}

	@Before
	public void setUp() {
		// create parsedCommand
		pc.setId(ID);
		
		// create event
		testEvent = new Event();
		testEvent.setId(ID);
		testEvent.setTitle(title);
		testEvent.setStartDateTime(startDateTime);
		testEvent.setEndDateTime(endDateTime);
		testEvent.setPriority(priority);
		testEvent.setLocation(location);
		testEvent.setNotes(notes);
		testEvent.setReminder(reminder);
	}

	@Test
	public void testAddEvent() {

		// Create EventHandler()
		EventHandler handle = new EventHandler();
		handle.add(testEvent);

		// Tests
//		assertEquals(eventId, newEvent.getId());
//		assertEquals(title, newEvent.getTitle());
//		assertEquals(startDate, newEvent.getStartDateTime());
//		assertEquals(endDate, newEvent.getEndDateTime());
//		assertEquals(utils.Priority.HIGH, newEvent.getPriority());
//		assertEquals(location, newEvent.getAddLocation());
//		assertEquals(recurring, newEvent.getAddRecurring());
//		assertEquals(description, newEvent.getAddTaskDescription());
		assertTrue(handle.getAllEvents().contains(testEvent));
	}

	// @Test
	// public void testUndoEvent() {
	//
	// }

	@Test
	public void testRemoveEvent() {

		// Create EventHandler()
		EventHandler handle = new EventHandler();
		handle.add(testEvent);

		handle.remove(pc);

		// Test
		assertFalse(handle.getAllEvents().contains(testEvent));
	}

	@Test
	public void testUpdateEvent() {
		
	}

	@Test
	public void testView() {
		// Create EventHandler()
		EventHandler handle = new EventHandler();
		handle.add(testEvent);
		Event viewedEvent = handle.view(pc);
		assertEquals(viewedEvent, testEvent);
	}
	
//	@Test
//	public void testExecute() {
//
//		ParsedCommand parsedExample = new ParsedCommand();
//
//		EventHandler handle = new EventHandler();
//		try {
//			handle.execute(parsedExample);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		fail();
//	}

}
