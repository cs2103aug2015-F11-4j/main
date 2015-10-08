package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
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

		deleteCommand.setCommand(Command.DELETE);
		deleteCommand.setId(ID);

		undoCommand.setCommand(Command.UNDO);

		updateCommand.setCommand(Command.UPDATE);
		updateCommand.setId(ID);
		updateCommand.setNotes(newNotes);

		setStorage.setCommand(Command.STORAGE_LOCATION);
		setStorage.setStorageLocation("abc.txt");

	}

	@Test
	public void testAddEvent() {

		// Create EventHandler()
		EventHandler handle = new EventHandler();
		handle.injectStorageManager(new StorageManagerStub());
		handle.add(testEvent);

		// Tests
		assertEquals(handle.getAllEvents().get(0).getId(), testEvent.getId());
		assertEquals(handle.getAllEvents().get(0).getTitle(), testEvent.getTitle());
		assertEquals(handle.getAllEvents().get(0).getStartDateTime(), testEvent.getStartDateTime());
		assertEquals(handle.getAllEvents().get(0).getEndDateTime(), testEvent.getEndDateTime());
		assertEquals(handle.getAllEvents().get(0).getPriority(), testEvent.getPriority());
		assertEquals(handle.getAllEvents().get(0).getLocation(), testEvent.getLocation());
		assertEquals(handle.getAllEvents().get(0).getNotes(), testEvent.getNotes());
		assertTrue(handle.getAllEvents().contains(testEvent));
	}

	@Test
	public void testUndoAddEvent() {
		EventHandler handle = new EventHandler();
		handle.injectStorageManager(new StorageManagerStub());

		try {
			handle.execute(pc);
			handle.execute(undoCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(handle.getAllEvents().isEmpty());
		assertTrue(handle.getAllEvents().size() == 0);
	}

	@Test
	public void testUndoDeleteEvent() {
		EventHandler handle = new EventHandler();
		handle.injectStorageManager(new StorageManagerStub());

		try {
			handle.execute(pc);
			handle.execute(deleteCommand);
			handle.execute(undoCommand);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
	public void testRemoveEvent() {

		EventHandler handle = new EventHandler();
		handle.injectStorageManager(new StorageManagerStub());

		handle.add(testEvent);

		handle.remove(pc);
		assertFalse(handle.getAllEvents().contains(testEvent));
	}

	@Test
	public void testUpdateEvent() {
		EventHandler handle = new EventHandler();
		handle.injectStorageManager(new StorageManagerStub());

		try {
			handle.execute(setStorage);
			handle.execute(pc);
			handle.execute(updateCommand);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(handle.getAllEvents().get(handle.getAllEvents().size() - 1).getId(), ID);
		assertEquals(handle.getAllEvents().get(handle.getAllEvents().size() - 1).getTitle(), title);
		assertEquals(handle.getAllEvents().get(handle.getAllEvents().size() - 1).getStartDateTime(), startDateTime);
		assertEquals(handle.getAllEvents().get(handle.getAllEvents().size() - 1).getEndDateTime(), endDateTime);
		assertEquals(handle.getAllEvents().get(handle.getAllEvents().size() - 1).getPriority(), priority);
		assertEquals(handle.getAllEvents().get(handle.getAllEvents().size() - 1).getLocation(), location);
		assertEquals(handle.getAllEvents().get(handle.getAllEvents().size() - 1).getNotes(), newNotes);
	}

	@Test
	public void testView() {
		EventHandler handle = new EventHandler();
		handle.injectStorageManager(new StorageManagerStub());

		handle.add(testEvent);
		Event viewedEvent = handle.view(pc);
		assertEquals(viewedEvent, testEvent);
	}

	@Test
	public void testExecute() {
		EventHandler handle = new EventHandler();
		handle.injectStorageManager(new StorageManagerStub());

		try {
			handle.execute(pc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(pc.getId(), handle.getAllEvents().get(0).getId());
	}

}
