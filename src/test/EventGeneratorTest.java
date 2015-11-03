package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;

import calendrier.EventGenerator;
import utils.Event;
import utils.ParsedCommand;
import utils.Priority;

public class EventGeneratorTest {
	ParsedCommand pc;
	ParsedCommand commandNoID;
	Event testEvent;

	// Information in parsedCommand/Event
	String ID = "TEST1";
	String title = "TEST1";

	Calendar startDateTime = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
	// startDateTime.set(2015, 9, 20, 10, 33, 25);

	Calendar endDateTime = Calendar.getInstance();
	// endDateTime.set(2015, 9, 21, 11, 34, 26);

	Priority priority = utils.Priority.LOW;
	String location = "Orchard Road";
	String notes = "Run at least 5 km";
	String group = "Exercise";
	// Calendar reminder;

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

		// no ID command
		commandNoID = new ParsedCommand();
		commandNoID.setTitle(title);

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
		testEvent.addSubtask("SUBTASK1");
		testEvent.addSubtask("SUBTASK2");
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
		assertEquals("SUBTASK2", testEvent.getSubtasks().get(1));
	}

	@Test
	public void testGenerateEventNoGivenID() {
		EventGenerator gen = new EventGenerator();
		Event generatedEvent = gen.createEvent(commandNoID);
		assertNotNull(generatedEvent.getId());
	}

	@Test
	public void testSetID() {
		EventGenerator gen = new EventGenerator();
		gen.setCurrentID(2);
		assertEquals(gen.getCurrentIDAsString(), 2 + "");
	}

	@Test
	public void testGenerateEventFromString() {
		String s = "id: 3, mainId: null, title: test generate Event, startDateTime: null, endDateTime: null, priority: null, location: null, notes: null, reminder: [], groups: [null], recurrence: null, subtasks: [],";
		EventGenerator gen = new EventGenerator();
		Event generated = gen.createEvent(s);
		assertEquals("test generate Event", generated.getTitle());
	}

	@Test
	public void testGenerateMultipleEventsFromStrings() {
		EventGenerator gen = new EventGenerator();

		ArrayList<String> strings = new ArrayList<>();
		strings.add("id: 4, mainId: null, title: yet, startDateTime: null, endDateTime: null, priority: null, location: null, notes: null, reminder: [], groups: [null], recurrence: null, subtasks: [], ");
		strings.add("id: 5, mainId: null, title: yet antoher, startDateTime: null, endDateTime: null, priority: null, location: null, notes: null, reminder: [], groups: [null], recurrence: null, subtasks: [], ");
		strings.add("id: 1945e87b-95c5-48ee-9b9c-47604b1170d2, mainId: null, title: addTitle, startDateTime: 2015/10/23-10:55, endDateTime: 2015/10/23-10:56, priority: VERY_HIGH, location: addLocation, notes: addNotes, reminder: [2015/10/30-12:18], groups: [null, null], recurrence: null, subtasks: [], ");
		strings.add("id: f68f148c-4a32-42e8-998b-df2ef0328158, mainId: null, title: back in Canada, startDateTime: 2021/10/6-10:55, endDateTime: 2015/12/27-10:55, priority: null, location: null, notes: shovel driveway, reminder: [], groups: [null], recurrence: null, subtasks: [], ");

		ArrayList<Event> events = gen.createMultipleEvents(strings);
		assertEquals(events.get(0).getId(), "4");
		assertEquals(events.get(1).getId(), "5");
		assertEquals(events.get(2).getId(), "1945e87b-95c5-48ee-9b9c-47604b1170d2");
		assertEquals(events.get(3).getId(), "f68f148c-4a32-42e8-998b-df2ef0328158");
	}

	@Test
	public void testGenerateEventNoDate() {
		fail();
	}

	@Test
	public void testGenerateEventNoTime() {
		fail();
	}

	@Test
	public void testGenerateEventNoDateOrTime() {
		fail();
	}
}
