package test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.TimeZone;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import calendrier.EventGenerator;
import utils.Event;
import utils.ParsedCommand;
import utils.Priority;

public class EventGeneratorTest {
	ParsedCommand pc;
	Event testEvent;

	// Information in parsedCommand/Event
	String ID = "TEST1";
	String title = "TEST1";
	
	Calendar startDateTime = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
//	startDateTime.set(2015, 9, 20, 10, 33, 25);
	
	Calendar endDateTime = Calendar.getInstance();
//	endDateTime.set(2015, 9, 21, 11, 34, 26);
	
	Priority priority = utils.Priority.LOW;
	String location = "Orchard Road";
	String notes = "Run at least 5 km";
	String group = "Exercise";
//	Calendar reminder;
	

	@Before
	public void setUp() {
		
		// create parsedCommand
		pc = new ParsedCommand();
		pc.setId(ID);
		pc.setTitle(title);
		pc.setStartDateTime(startDateTime);
		pc.setEndDateTime(endDateTime);
		pc.setPriority(priority);
		pc.setLocation(location);
		pc.setNotes(notes);
		pc.setGroup(group);
		
		// create event to test against
		testEvent = new Event();
		testEvent.setId(ID);
		testEvent.setTitle(title);
		testEvent.setStartDateTime(startDateTime);
		testEvent.setEndDateTime(endDateTime);
		testEvent.setPriority(priority);
		testEvent.setLocation(location);
		testEvent.setNotes(notes);
		testEvent.addGroup(group);
	}

	@Test
	public void testEventGenerated() {
		EventGenerator gen = new EventGenerator();
		Event generatedEvent = gen.createEvent(pc);

		assertEquals(generatedEvent.getId(), testEvent.getId());
		assertEquals(generatedEvent.getTitle(), testEvent.getTitle());
		assertEquals(generatedEvent.getStartDateTime(), testEvent.getStartDateTime());
		assertEquals(generatedEvent.getEndDateTime(), testEvent.getEndDateTime());
		assertEquals(generatedEvent.getPriority(), testEvent.getPriority());
		assertEquals(generatedEvent.getLocation(), testEvent.getLocation());
		assertEquals(generatedEvent.getNotes(), testEvent.getNotes());
		assertEquals(generatedEvent.getGroups(), testEvent.getGroups());
	}
}
