package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
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
	ParsedCommand pc = new ParsedCommand();
	Event testEvent = new Event();

	// simulating inputs from a parsed command
	String ID = "TEST";
	String title = "testing the first time";
	
	Priority priority = utils.Priority.LOW;
	String location = "Orchard Road";
	String notes = "Run at least 5 km";
	String group = "Exercise";

	
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
		pc.setTitle(title);
		pc.setStartDateTime(startDateTime);
		pc.setEndDateTime(endDateTime);
		pc.setPriority(priority);
		pc.setLocation(location);
		pc.setNotes(notes);
		pc.setGroup(group);

		
		// create event
		testEvent.setId(ID);
		testEvent.setTitle(title);
		testEvent.setStartDateTime(startDateTime);
		testEvent.setEndDateTime(endDateTime);
		testEvent.setPriority(priority);
		testEvent.setLocation(location);
		testEvent.setNotes(notes);
		testEvent.setReminder(reminder);
		testEvent.addGroup(group);

	}

	@Test
	public void testAddEvent() {

		// Create EventHandler()
		EventHandler handle = new EventHandler();
		handle.add(testEvent);

		// Tests
		assertEquals(testEvent.getId(), testEvent.getId());
//		assertEquals(testEvent.getTitle(), testEvent.getTitle());
//		assertEquals(testEvent.getStartDateTime(), testEvent.getStartDateTime());
//		assertEquals(testEvent.getEndDateTime(), testEvent.getEndDateTime());
//		assertEquals(testEvent.getPriority(), testEvent.getPriority());
//		assertEquals(testEvent.getLocation(), testEvent.getLocation());
//		assertEquals(testEvent.getNotes(), testEvent.getNotes());
		assertTrue(handle.getAllEvents().contains(testEvent));
	}

	// @Test
	// public void testUndoEvent() {
	//
	// }

	@Test
	public void testRemoveEvent() {

		EventHandler handle = new EventHandler();
		handle.add(testEvent);

		handle.remove(pc);
		assertFalse(handle.getAllEvents().contains(testEvent));
	}

	@Test
	public void testUpdateEvent() {
		
	}

	@Test
	public void testView() {
		EventHandler handle = new EventHandler();
		handle.add(testEvent);
		Event viewedEvent = handle.view(pc);
		assertEquals(viewedEvent, testEvent);
	}
	

	@Test
	public void testExecute() {

		ArrayList<Event> list = new ArrayList<>();

		EventHandler handle = new EventHandler();
		try {
			list = handle.execute(pc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		for (Event e : list) {
//			System.out.println(e);
//		}
	}



}
