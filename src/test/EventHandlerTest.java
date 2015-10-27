package test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import calendrier.EventHandler;
import stub.StorageManagerStub;
import utils.Command;
import utils.Event;
import utils.ParsedCommand;
import utils.Priority;

public class EventHandlerTest {
	ParsedCommand pc = new ParsedCommand();
	ParsedCommand deleteCommand = new ParsedCommand();
	ParsedCommand undoCommand = new ParsedCommand();
	ParsedCommand updateCommand = new ParsedCommand();
	ParsedCommand setStorage = new ParsedCommand();
	
	Event testEvent = new Event();
	

	// simulating inputs from a parsed command
	String ID = "TEST";
	String title = "testing the first time";
	String newNotes = "Ps - email boss";
	Priority priority = utils.Priority.LOW;
	String location = "Orchard Road";
	String notes = "Run at least 5 km";
	String group = "Exercise";

	static Calendar startDateTime;
	static Calendar endDateTime;
	static Calendar reminder;

	static Calendar start1;
	static Calendar end1;
	Event conflictEvent1 = new Event();

	static Calendar start2;
	static Calendar end2;
	Event conflictEvent2 = new Event();

	static Calendar start3;
	static Calendar end3;
	Event conflictEvent3 = new Event();

	static Calendar start4;
	static Calendar end4;
	Event conflictEvent4 = new Event();

	@BeforeClass
	public static void setUpBeforeClass() {
		startDateTime = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		startDateTime.set(2015, 9, 20, 10, 33, 25);

		endDateTime = Calendar.getInstance();
		endDateTime.set(2015, 11, 21, 11, 34, 26);

		reminder = Calendar.getInstance();
		reminder.set(2015, 10, 21, 11, 34, 26);

		// refers to a test case where new event starts before old one, bet ends
		// while the old one continues
		start1 = Calendar.getInstance();
		start1.set(2015, 8, 20, 10, 33, 25);
		end1 = Calendar.getInstance();
		end1.set(2015, 10, 20, 10, 33, 25);

		// new event starts before old one, ends after old one
		start2 = Calendar.getInstance();
		start2.set(2015, 8, 20, 10, 33, 25);
		end2 = Calendar.getInstance();
		end2.set(2015, 12, 20, 10, 33, 25);

		// new event starts in the duration of the old one, ends after old event
		// finishes
		start3 = Calendar.getInstance();
		start3.set(2015, 9, 22, 10, 33, 25);
		end3 = Calendar.getInstance();
		end3.set(2015, 12, 20, 10, 33, 25);

		// new event starts and ends in the duration of the old one
		start4 = Calendar.getInstance();
		start4.set(2015, 9, 22, 10, 33, 25);
		end4 = Calendar.getInstance();
		end4.set(2015, 12, 20, 10, 33, 25);
	}

	@Before
	public void setUp() {
		// create parsedCommand add
		pc.setCommand(Command.ADD);
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

		deleteCommand.setCommand(Command.DELETE);
		deleteCommand.setId(ID);

		undoCommand.setCommand(Command.UNDO);

		updateCommand.setCommand(Command.UPDATE);
		updateCommand.setId(ID);
		updateCommand.setNotes(newNotes);

		setStorage.setCommand(Command.STORAGE_LOCATION);
		setStorage.setStorageLocation("abc.txt");
	}
	
	/**
	 * TESTS FOR ADD
	 * 
	 */

	@Test
	public void testAddSingleEvent() throws Exception {

		// Create EventHandler()
		EventHandler handle = new EventHandler();
		handle.injectStorageManager(new StorageManagerStub());
		handle.add(testEvent);

		Event addedEvent = handle.getAllEvents().get(0);

		// Tests
		assertEquals(addedEvent.getId(), testEvent.getId());
		assertEquals(addedEvent.getTitle(), testEvent.getTitle());
		assertEquals(addedEvent.getStartDateTime(), testEvent.getStartDateTime());
		assertEquals(addedEvent.getEndDateTime(), testEvent.getEndDateTime());
		assertEquals(addedEvent.getPriority(), testEvent.getPriority());
		assertEquals(addedEvent.getLocation(), testEvent.getLocation());
		assertEquals(addedEvent.getNotes(), testEvent.getNotes());
		assertTrue(handle.getAllEvents().contains(testEvent));
	}
	
	@Test
	public void testAddMultipleEvents() throws Exception {
//		fail();
	}
	
	@Test
	public void testAddMultipleEventsSimultaneously() throws Exception {
//		fail();
	}

	
	/**
	 * boundary test case (conflict where new event starts before old event, and
	 * ends during the duration of the old event)
	 * 
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testAddConflictingEvents1() throws Exception {
		conflictEvent1.setStartDateTime(start1);
		conflictEvent1.setEndDateTime(end1);

		EventHandler handle = new EventHandler();
		handle.add(testEvent);
		handle.add(conflictEvent1);
	}

	/**
	 * boundary test case (conflict where new event starts before old event, and
	 * ends after the end of the old event)
	 * 
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testAddConflictingEvents2() throws Exception {
		conflictEvent2.setStartDateTime(start2);
		conflictEvent2.setEndDateTime(end2);

		EventHandler handle = new EventHandler();
		handle.add(testEvent);
		handle.add(conflictEvent2);
	}

	/**
	 * boundary test case (conflict where new event starts in the duration of
	 * the old event, and ends after the old event finishes)
	 * 
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testAddConflictingEvents3() throws Exception {
		conflictEvent3.setStartDateTime(start3);
		conflictEvent3.setEndDateTime(end3);

		EventHandler handle = new EventHandler();
		handle.add(testEvent);
		handle.add(conflictEvent3);
	}

	/**
	 * boundary test case (conflict where new event starts and ends in the
	 * duration of the old event)
	 * 
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testAddConflictingEvents4() throws Exception {
		conflictEvent4.setStartDateTime(start4);
		conflictEvent4.setEndDateTime(end4);

		EventHandler handle = new EventHandler();
		handle.add(testEvent);
		handle.add(conflictEvent4);
	}

	
	/**
	 * TESTS FOR REMOVE
	 * 
	 */
	@Test
	public void testRemoveSingleEvent() throws Exception {
		EventHandler handle = new EventHandler();
		handle.injectStorageManager(new StorageManagerStub());

		handle.add(testEvent);

		handle.remove(pc);
		assertFalse(handle.getAllEvents().contains(testEvent));
		assertTrue(handle.getAllEvents().isEmpty());
	}
	
	@Test
	public void testRemoveMultipleEvents() throws Exception {
//		fail();
	}
	
	@Test
	public void testRemoveMultipleEventsSimultaneously() throws Exception {
//		fail();
	}

	
	/**
	 * TESTS FOR VIEW
	 */
	
	@Test
	public void testViewEvent() throws Exception {
		EventHandler handle = new EventHandler();
		handle.injectStorageManager(new StorageManagerStub());

		handle.add(testEvent);
		Event viewedEvent = handle.view(pc);
		assertEquals(viewedEvent.getId(), testEvent.getId());
		assertEquals(viewedEvent.getTitle(), testEvent.getTitle());
		assertEquals(viewedEvent.getStartDateTime(), testEvent.getStartDateTime());
		assertEquals(viewedEvent.getEndDateTime(), testEvent.getEndDateTime());
		assertEquals(viewedEvent.getPriority(), testEvent.getPriority());
		assertEquals(viewedEvent.getLocation(), testEvent.getLocation());
		assertEquals(viewedEvent.getNotes(), testEvent.getNotes());
	}
	
	@Test
	public void testViewEmptyEvents() throws Exception {
//		fail();
	}

	@Test
	public void testSearchEvent() throws Exception {
		EventHandler handle = new EventHandler();
		handle.injectStorageManager(new StorageManagerStub());

		handle.add(testEvent);
		Event searchedEvent = handle.search(pc).get(0);
		assertEquals(searchedEvent.getId(), testEvent.getId());
		assertEquals(searchedEvent.getNotes(), testEvent.getNotes());
	}

	@Test
	public void testUpdateEvent() throws Exception {
		EventHandler handle = new EventHandler();
		handle.injectStorageManager(new StorageManagerStub());
		handle.add(testEvent);

		Event updatedEvent = handle.update(updateCommand);

		assertEquals(updatedEvent.getId(), ID);
		assertEquals(updatedEvent.getTitle(), title);
		assertEquals(updatedEvent.getStartDateTime(), startDateTime);
		assertEquals(updatedEvent.getEndDateTime(), endDateTime);
		assertEquals(updatedEvent.getPriority(), priority);
		assertEquals(updatedEvent.getLocation(), location);
		assertEquals(updatedEvent.getNotes(), newNotes);
	}

	/**
	 * TESTS FOR UNDO
	 */
	
	
	@Test
	public void testUndoAddEvent() throws Exception {
		EventHandler handle = new EventHandler();
		handle.injectStorageManager(new StorageManagerStub());
		
		handle.add(testEvent);
		handle.undo();

		assertTrue(handle.getAllEvents().isEmpty());
		assertTrue(handle.getAllEvents().size() == 0);
	}

	@Test
	public void testUndoDeleteEvent() throws Exception {
		EventHandler handle = new EventHandler();
		handle.injectStorageManager(new StorageManagerStub());
		
		handle.add(testEvent);
		handle.remove(pc);
		handle.undo();

		assertFalse(handle.getAllEvents().isEmpty());
		assertEquals(handle.getAllEvents().get(0).getId(), testEvent.getId());
		assertEquals(handle.getAllEvents().get(0).getTitle(), testEvent.getTitle());
		assertEquals(handle.getAllEvents().get(0).getStartDateTime(), testEvent.getStartDateTime());
		assertEquals(handle.getAllEvents().get(0).getEndDateTime(), testEvent.getEndDateTime());
		assertEquals(handle.getAllEvents().get(0).getPriority(), testEvent.getPriority());
		assertEquals(handle.getAllEvents().get(0).getLocation(), testEvent.getLocation());
		assertEquals(handle.getAllEvents().get(0).getNotes(), testEvent.getNotes());
	}

	@Test
	public void testUndoUpdate() throws Exception {
		EventHandler handle = new EventHandler();
		handle.injectStorageManager(new StorageManagerStub());
		
		
		handle.add(testEvent);
		handle.update(updateCommand);
		handle.undo();
		
		assertEquals(pc.getNotes(), handle.getAllEvents().get(0).getNotes());
		assertTrue(handle.getAllEvents().size() == 1);
	}
	
	
	/**
	 * TESTS FOR SORT
	 */
	@Test
	public void testSortEvents() throws Exception {
		EventHandler handle = new EventHandler();
		
		// create a bunch of events with different priorities and dates
		Event earlyVeryLowPriorityEvent = new Event();
		Event lateVeryLowPriorityEvent = new Event();
		
		earlyVeryLowPriorityEvent.setPriority(utils.Priority.VERY_LOW);
		lateVeryLowPriorityEvent.setPriority(utils.Priority.VERY_LOW);
		
		Event earlyLowPriorityEvent = new Event();
		Event lateLowPriorityEvent = new Event();
		earlyLowPriorityEvent.setPriority(utils.Priority.LOW);
		lateLowPriorityEvent.setPriority(utils.Priority.LOW);

		Event earlyMediumPriorityEvent = new Event();
		Event lateMediumPriorityEvent = new Event();
		earlyMediumPriorityEvent.setPriority(utils.Priority.MEDIUM);
		lateMediumPriorityEvent.setPriority(utils.Priority.MEDIUM);
		
		Event earlyHighPriorityEvent = new Event();
		Event lateHighPriorityEvent = new Event();
		earlyHighPriorityEvent.setPriority(utils.Priority.HIGH);
		lateHighPriorityEvent.setPriority(utils.Priority.HIGH);
		
		Event earlyVeryHighPriorityEvent = new Event();
		Event lateVeryHighPriorityEvent = new Event();
		earlyVeryHighPriorityEvent.setPriority(utils.Priority.VERY_HIGH);
		lateVeryHighPriorityEvent.setPriority(utils.Priority.VERY_HIGH);

		handle.add(earlyVeryLowPriorityEvent);
		handle.add(lateVeryLowPriorityEvent);
		
		handle.add(earlyLowPriorityEvent);
		handle.add(lateLowPriorityEvent);
		
		handle.add(earlyMediumPriorityEvent);
		handle.add(lateMediumPriorityEvent);
		
		handle.add(earlyHighPriorityEvent);
		handle.add(lateHighPriorityEvent);
		
		handle.add(earlyVeryHighPriorityEvent);
		handle.add(lateVeryHighPriorityEvent);
		
		// create
		handle.sortEvents();
		
		// check that first one is a high priority
		assertEquals(utils.Priority.VERY_HIGH, handle.getAllEvents().get(0).getPriority());
	}
	
}
