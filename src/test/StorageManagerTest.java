package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import calendrier.StorageManager;
import utils.Event;
import utils.Priority;
import utils.UserCommandException;
import org.junit.Test;

/**
 * @@author A0126421U
 * @author hiumengxiong
 * 
 */
public class StorageManagerTest {
	
	/* @@author A0126421U */
	@Test
	public void loadFromFile() throws UserCommandException{
		StorageManager rm= new StorageManager();
		rm.setStorageLocation("storageFile.txt") ;
		assertEquals("id: testId, " + "mainId: null, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/21-11:34, " + "priority: MEDIUM, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/20-10:33], " + "groups: null, "
				+ "recurrence: null, " + "subtasks: [], done: false, \nid: test123, " + "mainId: null, " + "title: 123Title, " + "startDateTime: null, "
				+ "endDateTime: null, " + "priority: HIGH, " + "location: test123123, "
				+ "notes: test note123213, " + "reminder: [], " + "groups: null, "
				+ "recurrence: null, " + "subtasks: [], done: false, \n", rm.listToString());
	}
	
	/* @@author A0126421U */
	@Test
	public void testSave() throws UserCommandException {
		
		StorageManager rm= new StorageManager();
		List<Event> data= new ArrayList<Event>();
		
		rm.setStorageLocation("storageFile.txt") ;

		Calendar calendarStart = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		calendarStart.set(2015, 9, 20, 10, 33, 25);
		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.set(2015, 9, 21, 11, 34, 26);

		Event event = new Event();
		event.setId("testId");
		event.setTitle("testTitle");
		event.setStartDateTime(calendarStart);
		event.setEndDateTime(calendarEnd);
		event.setPriority(Priority.MEDIUM);
		event.setLocation("test location");
		event.setNotes("test note");
		event.setReminder(calendarStart);
		data.add(event);
		
		Event event1 = new Event();
		event1.setId("test123");
		event1.setTitle("123Title");
		event1.setPriority(Priority.HIGH);
		event1.setLocation("test123123");
		event1.setNotes("test note123213");
		data.add(event1);
		
		rm.save(data);
		assertEquals("id: testId, " + "mainId: null, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/21-11:34, " + "priority: MEDIUM, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/20-10:33], " + "groups: null, "
				+ "recurrence: null, " + "subtasks: [], done: false, \nid: test123, " + "mainId: null, " + "title: 123Title, " + "startDateTime: null, "
				+ "endDateTime: null, " + "priority: HIGH, " + "location: test123123, "
				+ "notes: test note123213, " + "reminder: [], " + "groups: null, "
				+ "recurrence: null, " + "subtasks: [], done: false, \n", rm.listToString());
		
	}
}
