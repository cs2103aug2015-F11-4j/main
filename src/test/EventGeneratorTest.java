package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
		assertEquals(generatedEvent.getGroup(), testEvent.getGroup());
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
	public void testBreakfast(){
		EventGenerator gen = new EventGenerator();
		ParsedCommand parsedCommand = new ParsedCommand();
		parsedCommand.setTitle("have breakfast with Shan");
		
		Event generatedEvent = gen.createEvent(parsedCommand);
		
		assertEquals(Priority.MEDIUM, generatedEvent.getPriority());
		assertEquals("meal", generatedEvent.getGroup());
	}
	
	@Test
	public void testLunch(){
		EventGenerator gen = new EventGenerator();
		ParsedCommand parsedCommand = new ParsedCommand();
		parsedCommand.setTitle("have lunch with Shan");
		
		Event generatedEvent = gen.createEvent(parsedCommand);
		
		assertEquals(Priority.MEDIUM, generatedEvent.getPriority());
		assertEquals("meal", generatedEvent.getGroup());
	}
	
	@Test
	public void testDinner(){
		EventGenerator gen = new EventGenerator();
		ParsedCommand parsedCommand = new ParsedCommand();
		parsedCommand.setTitle("have dinner with Shan");
		
		Event generatedEvent = gen.createEvent(parsedCommand);
		
		assertEquals(Priority.MEDIUM, generatedEvent.getPriority());
		assertEquals("meal", generatedEvent.getGroup());
	}
	
	@Test
	public void testTea(){
		EventGenerator gen = new EventGenerator();
		ParsedCommand parsedCommand = new ParsedCommand();
		parsedCommand.setTitle("have tea with Shan");
		
		Event generatedEvent = gen.createEvent(parsedCommand);
		
		assertEquals(Priority.MEDIUM, generatedEvent.getPriority());
		assertEquals("meal", generatedEvent.getGroup());
	}
	
	@Test
	public void testBrunch(){
		EventGenerator gen = new EventGenerator();
		ParsedCommand parsedCommand = new ParsedCommand();
		parsedCommand.setTitle("have brunch with Shan");
		
		Event generatedEvent = gen.createEvent(parsedCommand);
		
		assertEquals(Priority.MEDIUM, generatedEvent.getPriority());
		assertEquals("meal", generatedEvent.getGroup());
	}
	
	@Test
	public void testSupper(){
		EventGenerator gen = new EventGenerator();
		ParsedCommand parsedCommand = new ParsedCommand();
		parsedCommand.setTitle("have supper with Shan");
		
		Event generatedEvent = gen.createEvent(parsedCommand);
		
		assertEquals(Priority.MEDIUM, generatedEvent.getPriority());
		assertEquals("meal", generatedEvent.getGroup());
	}
	
	@Test
	public void testMeal(){
		EventGenerator gen = new EventGenerator();
		ParsedCommand parsedCommand = new ParsedCommand();
		parsedCommand.setTitle("have a meal with Shan");
		
		Event generatedEvent = gen.createEvent(parsedCommand);
		
		assertEquals(Priority.MEDIUM, generatedEvent.getPriority());
		assertEquals("meal", generatedEvent.getGroup());
	}
	
	@Test
	public void testEat(){
		EventGenerator gen = new EventGenerator();
		ParsedCommand parsedCommand = new ParsedCommand();
		parsedCommand.setTitle("eat with Shan");
		
		Event generatedEvent = gen.createEvent(parsedCommand);
		
		assertEquals(Priority.MEDIUM, generatedEvent.getPriority());
		assertEquals("meal", generatedEvent.getGroup());
	}

	@Test
	public void testDeadline(){
		EventGenerator gen = new EventGenerator();
		ParsedCommand parsedCommand = new ParsedCommand();
		parsedCommand.setTitle("cs2103 project deadline");
		
		Event generatedEvent = gen.createEvent(parsedCommand);
		
		assertEquals(Priority.VERY_HIGH, generatedEvent.getPriority());
		assertEquals("deadline", generatedEvent.getGroup());
	}
	
	@Test
	public void testMeeting(){
		EventGenerator gen = new EventGenerator();
		ParsedCommand parsedCommand = new ParsedCommand();
		parsedCommand.setTitle("meeting with Shan");
		
		Event generatedEvent = gen.createEvent(parsedCommand);
		
		assertEquals(Priority.HIGH, generatedEvent.getPriority());
		assertEquals("meeting", generatedEvent.getGroup());
	}
	
	@Test
	public void testMeet(){
		EventGenerator gen = new EventGenerator();
		ParsedCommand parsedCommand = new ParsedCommand();
		parsedCommand.setTitle("meet with Shan");
		
		Event generatedEvent = gen.createEvent(parsedCommand);
		
		assertEquals(Priority.HIGH, generatedEvent.getPriority());
		assertEquals("meeting", generatedEvent.getGroup());
	}
	
	@Test
	public void testMeetup(){
		EventGenerator gen = new EventGenerator();
		ParsedCommand parsedCommand = new ParsedCommand();
		parsedCommand.setTitle("meetup with Shan");
		
		Event generatedEvent = gen.createEvent(parsedCommand);
		
		assertEquals(Priority.HIGH, generatedEvent.getPriority());
		assertEquals("meeting", generatedEvent.getGroup());
	}

	@Test
	public void testMeetUp(){
		EventGenerator gen = new EventGenerator();
		ParsedCommand parsedCommand = new ParsedCommand();
		parsedCommand.setTitle("meet up with Shan");
		
		Event generatedEvent = gen.createEvent(parsedCommand);
		
		assertEquals(Priority.HIGH, generatedEvent.getPriority());
		assertEquals("meeting", generatedEvent.getGroup());
	}
	
	@Test
	public void testBirthday(){
		EventGenerator gen = new EventGenerator();
		ParsedCommand parsedCommand = new ParsedCommand();
		parsedCommand.setTitle("Shan's birthday");
		
		Event generatedEvent = gen.createEvent(parsedCommand);
		
		assertEquals(Priority.LOW, generatedEvent.getPriority());
		assertEquals("birthday", generatedEvent.getGroup());
	}
	
	@Test
	public void testUserSpecifiedPriority(){
		EventGenerator gen = new EventGenerator();
		ParsedCommand parsedCommand = new ParsedCommand();
		parsedCommand.setTitle("Shan's birthday");
		parsedCommand.setPriority(Priority.VERY_HIGH);
		
		Event generatedEvent = gen.createEvent(parsedCommand);
		
		assertEquals(Priority.VERY_HIGH, generatedEvent.getPriority());
		assertEquals("birthday", generatedEvent.getGroup());
	}
	
	@Test
	public void testUserSpecifiedGroup(){
		EventGenerator gen = new EventGenerator();
		ParsedCommand parsedCommand = new ParsedCommand();
		parsedCommand.setTitle("Shan's birthday");
		parsedCommand.setGroup("friend_birthday");
		
		Event generatedEvent = gen.createEvent(parsedCommand);
		
		assertEquals(Priority.LOW, generatedEvent.getPriority());
		assertEquals("friend_birthday", generatedEvent.getGroup());
	}
	
	@Test
	public void testNoTitle(){
		EventGenerator gen = new EventGenerator();
		ParsedCommand parsedCommand = new ParsedCommand();
		
		Event generatedEvent = gen.createEvent(parsedCommand);
		
		assertEquals(null, generatedEvent.getPriority());
		assertEquals(null, generatedEvent.getGroup());
	}
}
