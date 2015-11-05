package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import calendrier.EventHandler;
import stub.StorageManagerStub;
import utils.Command;
import utils.Event;
import utils.IdMapper;
import utils.ParsedCommand;
import utils.Priority;

public class EventHandlerTest {
	ParsedCommand pc = new ParsedCommand();
	ParsedCommand deleteCommand = new ParsedCommand();
	ParsedCommand deleteSubtaskCommand = new ParsedCommand();

	ParsedCommand undoCommand = new ParsedCommand();
	ParsedCommand updateCommand = new ParsedCommand();
	ParsedCommand setStorage = new ParsedCommand();
	
	ParsedCommand searchByGroupCommand =  new ParsedCommand();
	ParsedCommand searchByPriorityCommand = new ParsedCommand();
	ParsedCommand searchByStartDateCommand = new ParsedCommand();
	ParsedCommand searchByEndDateCommand = new ParsedCommand();
	
	Event testEvent = new Event();
	Event testSubtask = new Event();

	// simulating inputs from a parsed command
	String ID = "TEST";
	String title = "testing the first time";
	String newNotes = "Ps - email boss";
	Priority priority = utils.Priority.LOW;
	String location = "Orchard Road";
	String notes = "Run at least 5 km";
	String group = "Exercise";
	
	String subtaskID = "Subtask1";
	
	String updatedGroup = "Exercise Jogging";
	String updatedTitle = "updated1";
	static ArrayList<String> updatadSubtasks = new ArrayList<>();

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
		reminder.set(2015, 10, 21, 11, 34, 26);
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
		testEvent.setRecurrence(utils.Recurrence.DAILY);
//		testEvent.addSubtask("Subtask1");
		
		testSubtask.setId(subtaskID);
		testSubtask.setTitle("Subtask1");
		testSubtask.setMainId(ID);

		deleteCommand.setCommand(Command.DELETE);
		deleteCommand.setId(ID);
		
		deleteSubtaskCommand.setCommand(Command.DELETE);
		deleteSubtaskCommand.setId(subtaskID);

		undoCommand.setCommand(Command.UNDO);

		updateCommand.setCommand(Command.UPDATE);
		updateCommand.setId(ID);
		updateCommand.setTitle(updatedTitle);
		updateCommand.setNotes(newNotes);
		updateCommand.setDone(true);
		updateCommand.setGroup(updatedGroup);

		setStorage.setCommand(Command.STORAGE_LOCATION);
		setStorage.setStorageLocation("abc.txt");
		
		searchByGroupCommand.setCommand(Command.FILTER);
		searchByGroupCommand.setGroup(group);
		
		searchByPriorityCommand.setCommand(Command.FILTER);
		searchByPriorityCommand.setPriority(utils.Priority.LOW);
		
		searchByStartDateCommand.setCommand(Command.FILTER);
		searchByStartDateCommand.setStartDateTime(startDateTime);
		
		searchByEndDateCommand.setCommand(Command.FILTER);
		searchByEndDateCommand.setEndDateTime(endDateTime);
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
		assertEquals(addedEvent.getRecurrence(),  testEvent.getRecurrence());
	}
	
	@Test
	public void testAddedSubtask() throws Exception {
		EventHandler handle = new EventHandler();
		handle.injectStorageManager(new StorageManagerStub());
		handle.add(testEvent);
		handle.add(testSubtask);
		assertEquals(handle.getAllEvents().get(0).getSubtasks().get(0), testSubtask.getId());
	}
	
	
	/**
	 * TESTS FOR REMOVE
	 */
	
	@Test
	public void testRemoveSingleEvent() throws Exception {
		EventHandler handle = new EventHandler();
		handle.injectStorageManager(new StorageManagerStub());

		IdMapper.getInstance().set(pc.getId(), pc.getId());
		handle.add(testEvent);

		handle.remove(pc);
		assertFalse(handle.getAllEvents().contains(testEvent));
		assertTrue(handle.getAllEvents().isEmpty());
	}
	
//	@Test
//	public void testIdRemovedFromMaintaskWhenSubtaskDeleted() throws Exception{
//		EventHandler handle = new EventHandler();
//		handle.injectStorageManager(new StorageManagerStub());
//		
//		handle.add(testEvent);
//		handle.add(testSubtask);
//		handle.remove(deleteSubtaskCommand);
//		
//		assertEquals(0, handle.getAllEvents().get(0).getSubtasks().size());
//	}

	
	/**
	 * TESTS FOR VIEW
	 */
	
	@Test
	public void testViewEvent() throws Exception {
		EventHandler handle = new EventHandler();
		handle.injectStorageManager(new StorageManagerStub());

		handle.add(testEvent);

		IdMapper.getInstance().set(pc.getId(), pc.getId());
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
		EventHandler handle = new EventHandler();
		handle.injectStorageManager(new StorageManagerStub());
		
		Event viewedEvent = handle.view(pc);
		assertNull(viewedEvent);
	}
	
	
	/**
	 * TESTS FOR SEARCH
	 */
	
	@Test
	public void testSearchEventByGroup() throws Exception {
		EventHandler handle = new EventHandler();
		handle.injectStorageManager(new StorageManagerStub());
		
		handle.add(testEvent);
		Event searchedEvent = handle.search(searchByGroupCommand).get(0);
		assertEquals(title , searchedEvent.getTitle());
	}
	
	@Test
	public void testSearchEventByPriority() throws Exception {
		EventHandler handle = new EventHandler();
		handle.injectStorageManager(new StorageManagerStub());
		
		handle.add(testEvent);
		Event searchedEvent = handle.search(searchByPriorityCommand).get(0);
		assertEquals(utils.Priority.LOW , searchedEvent.getPriority());
	}
	
	@Test
	public void testSearchEventsByStartDate() throws Exception {
		EventHandler handle = new EventHandler();
		handle.injectStorageManager(new StorageManagerStub());
		
		handle.add(testEvent);
		Event searchedEvent = handle.search(searchByStartDateCommand).get(0);
		assertEquals(startDateTime, searchedEvent.getStartDateTime());
	}
	
	
	@Test
	public void testSearchEventsByEndDate() throws Exception {
		EventHandler handle = new EventHandler();
		handle.injectStorageManager(new StorageManagerStub());
		
		handle.add(testEvent);
		Event searchedEvent = handle.search(searchByEndDateCommand).get(0);
		assertEquals(endDateTime, searchedEvent.getEndDateTime());
	}
	
	
	/**
	 * TESTS FOR UPDATE
	 */
	
	@Test
	public void testUpdateEvent() throws Exception {
		EventHandler handle = new EventHandler();
		handle.injectStorageManager(new StorageManagerStub());
		handle.add(testEvent);

		Event updatedEvent = handle.update(updateCommand);

		assertEquals(ID, updatedEvent.getId());
		assertEquals(updatedTitle, updatedEvent.getTitle());
		assertEquals(startDateTime, updatedEvent.getStartDateTime());
		assertEquals(endDateTime, updatedEvent.getEndDateTime());
		assertEquals(priority, updatedEvent.getPriority());
		assertEquals(location, updatedEvent.getLocation());
		assertEquals(newNotes, updatedEvent.getNotes());
		assertEquals(true, updatedEvent.isDone());
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
		
		handle.sortEvents();
		assertEquals(utils.Priority.VERY_HIGH, handle.getAllEvents().get(0).getPriority());
	}
	
}
