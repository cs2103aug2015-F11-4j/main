package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import utils.Event;
import utils.Priority;
import utils.Recurrence;

public class EventTest {

	@Test
	public void testGeneric() {
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

		assertEquals("id: testId, " + "title: testTitle, " + "startDateTime: Tue Oct 20 10:33:25 SGT 2015, "
				+ "endDateTime: Wed Oct 21 11:34:26 SGT 2015, " + "priority: MEDIUM, " + "location: test location, "
				+ "notes: test note, " + "reminder: [Tue Oct 20 10:33:25 SGT 2015, ], " + "groups: [], "
				+ "recurrence: null, " + "subtasks: [], ", event.toString());
	}

	@Test
	public void testWithGroups1Item() {
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

		event.addGroup("group 1");

		assertEquals("id: testId, " + "title: testTitle, " + "startDateTime: Tue Oct 20 10:33:25 SGT 2015, "
				+ "endDateTime: Wed Oct 21 11:34:26 SGT 2015, " + "priority: MEDIUM, " + "location: test location, "
				+ "notes: test note, " + "reminder: [Tue Oct 20 10:33:25 SGT 2015, ], " + "groups: [group 1], "
				+ "recurrence: null, " + "subtasks: [], ", event.toString());
	}

	@Test
	public void testWithGroups2Items() {
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

		event.addGroup("group 1");
		event.addGroup("group 2");

		assertEquals("id: testId, " + "title: testTitle, " + "startDateTime: Tue Oct 20 10:33:25 SGT 2015, "
				+ "endDateTime: Wed Oct 21 11:34:26 SGT 2015, " + "priority: MEDIUM, " + "location: test location, "
				+ "notes: test note, " + "reminder: [Tue Oct 20 10:33:25 SGT 2015, ], " + "groups: [group 1, group 2], "
				+ "recurrence: null, " + "subtasks: [], ", event.toString());
	}

	@Test
	public void testNoId() {
		Calendar calendarStart = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		calendarStart.set(2015, 9, 20, 10, 33, 25);
		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.set(2015, 9, 21, 11, 34, 26);

		Event event = new Event();
		event.setTitle("testTitle");
		event.setStartDateTime(calendarStart);
		event.setEndDateTime(calendarEnd);
		event.setPriority(Priority.MEDIUM);
		event.setLocation("test location");
		event.setNotes("test note");
		event.setReminder(calendarStart);

		assertEquals("id: null, " + "title: testTitle, " + "startDateTime: Tue Oct 20 10:33:25 SGT 2015, "
				+ "endDateTime: Wed Oct 21 11:34:26 SGT 2015, " + "priority: MEDIUM, " + "location: test location, "
				+ "notes: test note, " + "reminder: [Tue Oct 20 10:33:25 SGT 2015, ], " + "groups: [], "
				+ "recurrence: null, " + "subtasks: [], ", event.toString());
	}

	@Test
	public void testNoTitle() {
		Calendar calendarStart = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		calendarStart.set(2015, 9, 20, 10, 33, 25);
		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.set(2015, 9, 21, 11, 34, 26);

		Event event = new Event();
		event.setId("testId");
		event.setStartDateTime(calendarStart);
		event.setEndDateTime(calendarEnd);
		event.setPriority(Priority.MEDIUM);
		event.setLocation("test location");
		event.setNotes("test note");
		event.setReminder(calendarStart);

		assertEquals("id: testId, " + "title: null, " + "startDateTime: Tue Oct 20 10:33:25 SGT 2015, "
				+ "endDateTime: Wed Oct 21 11:34:26 SGT 2015, " + "priority: MEDIUM, " + "location: test location, "
				+ "notes: test note, " + "reminder: [Tue Oct 20 10:33:25 SGT 2015, ], " + "groups: [], "
				+ "recurrence: null, " + "subtasks: [], ",  event.toString());
	}

	@Test
	public void testNoStartDateTime() {
		Calendar calendarStart = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		calendarStart.set(2015, 9, 20, 10, 33, 25);
		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.set(2015, 9, 21, 11, 34, 26);

		Event event = new Event();
		event.setId("testId");
		event.setTitle("testTitle");
		event.setEndDateTime(calendarEnd);
		event.setPriority(Priority.MEDIUM);
		event.setLocation("test location");
		event.setNotes("test note");
		event.setReminder(calendarStart);

		assertEquals("id: testId, " + "title: testTitle, " + "startDateTime: null, "
				+ "endDateTime: Wed Oct 21 11:34:26 SGT 2015, " + "priority: MEDIUM, " + "location: test location, "
				+ "notes: test note, " + "reminder: [Tue Oct 20 10:33:25 SGT 2015, ], " + "groups: [], "
				+ "recurrence: null, " + "subtasks: [], ",  event.toString());
	}

	@Test
	public void testNoEndDateTime() {
		Calendar calendarStart = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		calendarStart.set(2015, 9, 20, 10, 33, 25);
		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.set(2015, 9, 21, 11, 34, 26);

		Event event = new Event();
		event.setId("testId");
		event.setTitle("testTitle");
		event.setStartDateTime(calendarStart);
		event.setPriority(Priority.MEDIUM);
		event.setLocation("test location");
		event.setNotes("test note");
		event.setReminder(calendarStart);

		assertEquals(
				"id: testId, " + "title: testTitle, " + "startDateTime: Tue Oct 20 10:33:25 SGT 2015, "
						+ "endDateTime: null, " + "priority: MEDIUM, " + "location: test location, "
						+ "notes: test note, " + "reminder: [Tue Oct 20 10:33:25 SGT 2015, ], " + "groups: [], "
						+ "recurrence: null, " + "subtasks: [], ", 
				event.toString());
	}

	@Test
	public void testNoPriority() {
		Calendar calendarStart = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		calendarStart.set(2015, 9, 20, 10, 33, 25);
		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.set(2015, 9, 21, 11, 34, 26);

		Event event = new Event();
		event.setId("testId");
		event.setTitle("testTitle");
		event.setStartDateTime(calendarStart);
		event.setEndDateTime(calendarEnd);
		event.setLocation("test location");
		event.setNotes("test note");
		event.setReminder(calendarStart);

		assertEquals("id: testId, " + "title: testTitle, " + "startDateTime: Tue Oct 20 10:33:25 SGT 2015, "
				+ "endDateTime: Wed Oct 21 11:34:26 SGT 2015, " + "priority: null, " + "location: test location, "
				+ "notes: test note, " + "reminder: [Tue Oct 20 10:33:25 SGT 2015, ], " + "groups: [], "
				+ "recurrence: null, " + "subtasks: [], ",  event.toString());
	}

	@Test
	public void testNoLocation() {
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
		event.setNotes("test note");
		event.setReminder(calendarStart);

		assertEquals(
				"id: testId, " + "title: testTitle, " + "startDateTime: Tue Oct 20 10:33:25 SGT 2015, "
						+ "endDateTime: Wed Oct 21 11:34:26 SGT 2015, " + "priority: MEDIUM, " + "location: null, "
						+ "notes: test note, " + "reminder: [Tue Oct 20 10:33:25 SGT 2015, ], " + "groups: [], "
						+ "recurrence: null, " + "subtasks: [], ", 
				event.toString());
	}

	@Test
	public void testNoNotes() {
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
		event.setReminder(calendarStart);

		assertEquals("id: testId, " + "title: testTitle, " + "startDateTime: Tue Oct 20 10:33:25 SGT 2015, "
				+ "endDateTime: Wed Oct 21 11:34:26 SGT 2015, " + "priority: MEDIUM, " + "location: test location, "
				+ "notes: null, " + "reminder: [Tue Oct 20 10:33:25 SGT 2015, ], " + "groups: [], "
				+ "recurrence: null, " + "subtasks: [], ",  event.toString());
	}

	@Test
	public void testNoReminder() {
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

		assertEquals(
				"id: testId, " + "title: testTitle, " + "startDateTime: Tue Oct 20 10:33:25 SGT 2015, "
						+ "endDateTime: Wed Oct 21 11:34:26 SGT 2015, " + "priority: MEDIUM, "
						+ "location: test location, " + "notes: test note, " + "reminder: [], " + "groups: [], "
						+ "recurrence: null, " + "subtasks: [], ", 
				event.toString());
	}

	@Test
	public void testToString(){
		String s = "id: testId, "
				+ "title: testTitle, "
				+ "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: null, "
				+ "priority: HIGH, "
				+ "location: test location, "
				+ "notes: test note, "
				+ "reminder: [2015/10/21-11:34, 2015/10/21-11:34], "
				+ "groups: [abc, def], "
				+ "recurrence: WEEKLY, "
				+ "subtasks: [abcd, defg], ";
		
		Calendar startDateTime = Calendar.getInstance();
		startDateTime.setTimeInMillis(Long.valueOf("1445308380000"));
		Calendar endDateTime = Calendar.getInstance();
		endDateTime.setTimeInMillis(Long.valueOf("1445398440000"));
		Calendar reminder = Calendar.getInstance();
		reminder.setTimeInMillis(Long.valueOf("1445398440000"));
		
		Event e = new Event();
		e.setTitle("testTitle");
		e.setId("testId");
		e.setStartDateTime(startDateTime);
		e.setEndDateTime(null);
		e.setPriority(Priority.HIGH);
		e.setLocation("test location");
		e.setNotes("test note");
		e.addGroup("abc");
		e.addGroup("def");
		e.setRecurrence(Recurrence.WEEKLY);
		e.addReminder(reminder);
		e.addReminder(reminder);
		e.addSubtask("abcd");
		e.addSubtask("defg");
		
		assertEquals(e.toString(), s);
		
		assertTrue(true);
	}
	
	@Test
	public void testFromString(){
		String c = "id: testId, "
				+ "title: testTitle, "
				+ "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/21-11:34, "
				+ "priority: MEDIUM, "
				+ "location: test location, "
				+ "notes: test note, "
				+ "reminder: [2015/10/21-11:34, 2015/10/21-11:34], "
				+ "groups: [abc, def], "
				+ "recurrence: null, "
				+ "subtasks: [abcd, defg], ";
		
		Event e = new Event();
		e.fromString(c);
		
		assertEquals("testId", e.getId());
		assertEquals("testTitle", e.getTitle());
		assertEquals("2015/10/20-10:33", e.toTimestamp(e.getStartDateTime()));
		assertEquals("2015/10/21-11:34", e.toTimestamp(e.getEndDateTime()));
		assertEquals("MEDIUM", e.getPriority().name());
		assertEquals("test location", e.getLocation());
		assertEquals("test note", e.getNotes());
		assertEquals(null, e.getRecurrence());
		assertEquals("[2015/10/21-11:34, 2015/10/21-11:34]", Arrays.toString(e.getReminderList().toArray()));
		assertEquals("[abc, def]", Arrays.toString(e.getGroups().toArray()));
		assertEquals("[abcd, defg]", Arrays.toString(e.getSubtasks().toArray()));

		assertTrue(true);
	}
}
