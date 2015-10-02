package test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.TimeZone;

import calendrier.StorageManager;
import utils.Event;
import utils.Priority;

import org.junit.Test;

public class StorageManagerTest {

	@Test
	public void checkProcessFile(){
		StorageManager rm= new StorageManager();
		rm.setStorageLocation("src/calendrier/storageFile.txt") ;
		assertEquals("id: abc, title: def, startDateTime: Tue Oct 20 10:33:25 SGT 2015, endDateTime: Wed Oct 21 11:34:26 SGT 2015, priority: MEDIUM, \nid: abc, title: def, startDateTime: Tue Oct 20 18:33:25 SGT 2015, endDateTime: Wed Oct 21 19:34:26 SGT 2015, priority: MEDIUM, \n", rm.listToString());
	}
	
	@Test
	public void testSave() {
		StorageManager rm= new StorageManager();
		rm.setStorageLocation("src/calendrier/storageFile.txt") ;
		rm.clear();
		Calendar calendarStart = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		calendarStart.set(2015, 9, 20, 10, 33, 25);
		Calendar calendarEnd = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		calendarEnd.set(2015, 9, 21, 11, 34, 26);
		
		Event event = new Event();
		event.setId("abc");
		event.setTitle("def");
		event.setStartDateTime(calendarStart);
		event.setEndDateTime(calendarEnd);
		event.setPriority(Priority.MEDIUM);
		//assertEquals("id: abc, title: def, startDateTime: Tue Oct 20 10:33:25 SGT 2015, endDateTime: Wed Oct 21 11:34:26 SGT 2015, priority: MEDIUM, ", event.toString());
		rm.add(event);
		assertEquals("id: abc, title: def, startDateTime: Tue Oct 20 10:33:25 SGT 2015, endDateTime: Wed Oct 21 11:34:26 SGT 2015, priority: MEDIUM, \n", rm.listToString());
		
		Calendar calendarStart1 = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		calendarStart1.set(2015, 9, 20, 18, 33, 25);
		Calendar calendarEnd1 = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		calendarEnd1.set(2015, 9, 21, 19, 34, 26);
		
		Event event1 = new Event();
		event1.setId("abc");
		event1.setTitle("def");
		event1.setStartDateTime(calendarStart1);
		event1.setEndDateTime(calendarEnd1);
		event1.setPriority(Priority.MEDIUM);
		//assertEquals("id: abc, title: def, startDateTime: Tue Oct 20 10:33:25 SGT 2015, endDateTime: Wed Oct 21 11:34:26 SGT 2015, priority: MEDIUM, ", event.toString());
		rm.add(event1);
		assertEquals("id: abc, title: def, startDateTime: Tue Oct 20 10:33:25 SGT 2015, endDateTime: Wed Oct 21 11:34:26 SGT 2015, priority: MEDIUM, \nid: abc, title: def, startDateTime: Tue Oct 20 18:33:25 SGT 2015, endDateTime: Wed Oct 21 19:34:26 SGT 2015, priority: MEDIUM, \n", rm.listToString());
		rm.save();
	}

	@Test
	public void testAdd() {
		StorageManager rm= new StorageManager();
		Calendar calendarStart = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		calendarStart.set(2015, 9, 20, 10, 33, 25);
		Calendar calendarEnd = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		calendarEnd.set(2015, 9, 21, 11, 34, 26);
		
		Event event = new Event();
		event.setId("abc");
		event.setTitle("def");
		event.setStartDateTime(calendarStart);
		event.setEndDateTime(calendarEnd);
		event.setPriority(Priority.MEDIUM);
		//assertEquals("id: abc, title: def, startDateTime: Tue Oct 20 10:33:25 SGT 2015, endDateTime: Wed Oct 21 11:34:26 SGT 2015, priority: MEDIUM, ", event.toString());
		rm.add(event);
		assertEquals("id: abc, title: def, startDateTime: Tue Oct 20 10:33:25 SGT 2015, endDateTime: Wed Oct 21 11:34:26 SGT 2015, priority: MEDIUM, \n", rm.listToString());
		
		Calendar calendarStart1 = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		calendarStart1.set(2015, 9, 20, 18, 33, 25);
		Calendar calendarEnd1 = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		calendarEnd1.set(2015, 9, 21, 19, 34, 26);
		
		Event event1 = new Event();
		event1.setId("abc");
		event1.setTitle("def");
		event1.setStartDateTime(calendarStart1);
		event1.setEndDateTime(calendarEnd1);
		event1.setPriority(Priority.MEDIUM);
		//assertEquals("id: abc, title: def, startDateTime: Tue Oct 20 10:33:25 SGT 2015, endDateTime: Wed Oct 21 11:34:26 SGT 2015, priority: MEDIUM, ", event.toString());
		rm.add(event1);
		assertEquals("id: abc, title: def, startDateTime: Tue Oct 20 10:33:25 SGT 2015, endDateTime: Wed Oct 21 11:34:26 SGT 2015, priority: MEDIUM, \nid: abc, title: def, startDateTime: Tue Oct 20 18:33:25 SGT 2015, endDateTime: Wed Oct 21 19:34:26 SGT 2015, priority: MEDIUM, \n", rm.listToString());
	}
	
	@Test
	public void testDelete(){
		StorageManager rm= new StorageManager();
		Calendar calendarStart = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		calendarStart.set(2015, 9, 20, 10, 33, 25);
		Calendar calendarEnd = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		calendarEnd.set(2015, 9, 20, 11, 34, 26);
		
		Event event = new Event();
		event.setId("abc");
		event.setTitle("def");
		event.setStartDateTime(calendarStart);
		event.setEndDateTime(calendarEnd);
		event.setPriority(Priority.MEDIUM);
		rm.add(event);
		assertEquals("id: abc, title: def, startDateTime: Tue Oct 20 10:33:25 SGT 2015, endDateTime: Tue Oct 20 11:34:26 SGT 2015, priority: MEDIUM, \n", rm.listToString());
		
		Calendar calendarStart1 = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		calendarStart1.set(2015, 9, 20, 18, 33, 25);
		Calendar calendarEnd1 = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		calendarEnd1.set(2015, 9, 20, 19, 34, 26);
		
		Event event1 = new Event();
		event1.setId("abc");
		event1.setTitle("def");
		event1.setStartDateTime(calendarStart1);
		event1.setEndDateTime(calendarEnd1);
		event1.setPriority(Priority.MEDIUM);
		
		rm.add(event1);
		rm.remove(event1);
		assertEquals("id: abc, title: def, startDateTime: Tue Oct 20 10:33:25 SGT 2015, endDateTime: Tue Oct 20 11:34:26 SGT 2015, priority: MEDIUM, \n", rm.listToString());
		rm.remove(event);
		assertEquals("", rm.listToString());
	}
	
	@Test
	public void testUpdate(){
		StorageManager rm= new StorageManager();
		Calendar calendarStart = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		calendarStart.set(2015, 9, 20, 10, 33, 25);
		Calendar calendarEnd = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		calendarEnd.set(2015, 9, 20, 11, 34, 26);
		
		Event event = new Event();
		event.setId("abc");
		event.setTitle("def");
		event.setStartDateTime(calendarStart);
		event.setEndDateTime(calendarEnd);
		event.setPriority(Priority.MEDIUM);
		rm.add(event);
		assertEquals("id: abc, title: def, startDateTime: Tue Oct 20 10:33:25 SGT 2015, endDateTime: Tue Oct 20 11:34:26 SGT 2015, priority: MEDIUM, \n", rm.listToString());
		
		Calendar calendarStart1 = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		calendarStart1.set(2015, 9, 20, 18, 33, 25);
		Calendar calendarEnd1 = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		calendarEnd1.set(2015, 9, 20, 19, 34, 26);
		
		Event event1 = new Event();
		event1.setId("abc");
		event1.setTitle("def");
		event1.setStartDateTime(calendarStart1);
		event1.setEndDateTime(calendarEnd1);
		event1.setPriority(Priority.MEDIUM);
		
		rm.update(event, event1);
		assertEquals("id: abc, title: def, startDateTime: Tue Oct 20 18:33:25 SGT 2015, endDateTime: Tue Oct 20 19:34:26 SGT 2015, priority: MEDIUM, \n", rm.listToString());
	}
	
	@Test
	public void testUndo() {
		StorageManager rm= new StorageManager();
		Calendar calendarStart = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		calendarStart.set(2015, 9, 20, 10, 33, 25);
		Calendar calendarEnd = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		calendarEnd.set(2015, 9, 21, 11, 34, 26);
		
		Event event = new Event();
		event.setId("abc");
		event.setTitle("def");
		event.setStartDateTime(calendarStart);
		event.setEndDateTime(calendarEnd);
		event.setPriority(Priority.MEDIUM);
		//assertEquals("id: abc, title: def, startDateTime: Tue Oct 20 10:33:25 SGT 2015, endDateTime: Wed Oct 21 11:34:26 SGT 2015, priority: MEDIUM, ", event.toString());
		rm.add(event);
		assertEquals("id: abc, title: def, startDateTime: Tue Oct 20 10:33:25 SGT 2015, endDateTime: Wed Oct 21 11:34:26 SGT 2015, priority: MEDIUM, \n", rm.listToString());
		
		Calendar calendarStart1 = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		calendarStart1.set(2015, 9, 20, 18, 33, 25);
		Calendar calendarEnd1 = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		calendarEnd1.set(2015, 9, 21, 19, 34, 26);
		
		Event event1 = new Event();
		event1.setId("abc");
		event1.setTitle("def");
		event1.setStartDateTime(calendarStart1);
		event1.setEndDateTime(calendarEnd1);
		event1.setPriority(Priority.MEDIUM);
		//assertEquals("id: abc, title: def, startDateTime: Tue Oct 20 10:33:25 SGT 2015, endDateTime: Wed Oct 21 11:34:26 SGT 2015, priority: MEDIUM, ", event.toString());
		rm.add(event1);
		assertEquals("id: abc, title: def, startDateTime: Tue Oct 20 10:33:25 SGT 2015, endDateTime: Wed Oct 21 11:34:26 SGT 2015, priority: MEDIUM, \nid: abc, title: def, startDateTime: Tue Oct 20 18:33:25 SGT 2015, endDateTime: Wed Oct 21 19:34:26 SGT 2015, priority: MEDIUM, \n", rm.listToString());
		rm.undo();
		assertEquals("id: abc, title: def, startDateTime: Tue Oct 20 10:33:25 SGT 2015, endDateTime: Wed Oct 21 11:34:26 SGT 2015, priority: MEDIUM, \n", rm.listToString());
	}
}
