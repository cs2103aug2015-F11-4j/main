package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import calendrier.StorageManager;
import utils.Event;
import utils.Priority;

import org.junit.Test;

public class StorageManagerTest {
	
	@Test
	public void checkProcessFile(){
		StorageManager rm= new StorageManager();
		rm.setStorageLocation("storageFile.txt") ;
		assertEquals("id: testId, title: testTitle, startDateTime: Fri Oct 23 10:55:00 SGT 2015, endDateTime: Fri Oct 23 10:56:00 SGT 2015, priority: MEDIUM, location: test location, notes: test note, reminder: Mon Oct 19 10:33:00 SGT 2015, groups: [], \n"
				+ "id: abc, title: 123, startDateTime: null, endDateTime: null, priority: null, location: null, notes: test 123, reminder: null, groups: [], \n",
				rm.listToString());
	}
	
	@Test
	public void testSave() {
		
		StorageManager rm= new StorageManager();
		List<Event> data= new ArrayList<Event>();
		
		//rm.setStorageLocation("src/calendrier/storageFile.txt") ;
		Calendar calendarStart = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		calendarStart.set(2015, 9, 23, 10, 55, 00);
		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.set(2015, 9, 23, 10, 56, 00);
		Calendar calendarRemind = Calendar.getInstance();
		calendarRemind.set(2015, 9, 19, 10, 33, 00);
		
		Event event = new Event();
		event.setId("testId");
		event.setTitle("testTitle");
		event.setStartDateTime(calendarStart);
		event.setEndDateTime(calendarEnd);
		event.setPriority(Priority.MEDIUM);
		event.setLocation("test location");
		event.setNotes("test note");
		event.setReminder(calendarRemind);
		data.add(event);
		
		Event event1 = new Event();
		event1.setTitle("123");
		event1.setNotes("test 123");
		event1.setId("abc");
		event1.setStartDateTime(null);
		event1.setEndDateTime(null);
		event1.setPriority(null);
		event1.setLocation(null);
		event1.setReminder(null);
		data.add(event1);
		
		rm.save(data);
		assertEquals("id: testId, title: testTitle, startDateTime: Fri Oct 23 10:55:00 SGT 2015, endDateTime: Fri Oct 23 10:56:00 SGT 2015, priority: MEDIUM, location: test location, notes: test note, reminder: Mon Oct 19 10:33:00 SGT 2015, groups: [], \n"
				+ "id: abc, title: 123, startDateTime: null, endDateTime: null, priority: null, location: null, notes: test 123, reminder: null, groups: [], \n",
				rm.listToString());
		
	}
}
