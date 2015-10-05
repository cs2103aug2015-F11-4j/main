package test;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.TimeZone;

import org.junit.Test;

import utils.Event;
import utils.Priority;

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
				+ "notes: test note, " + "reminder: Tue Oct 20 10:33:25 SGT 2015, " + "groups: [], ", event.toString());
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
				+ "notes: test note, " + "reminder: Tue Oct 20 10:33:25 SGT 2015, " + "groups: [group 1], ",
				event.toString());
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
				+ "notes: test note, " + "reminder: Tue Oct 20 10:33:25 SGT 2015, " + "groups: [group 1, group 2], ",
				event.toString());
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
				+ "notes: test note, " + "reminder: Tue Oct 20 10:33:25 SGT 2015, " + "groups: [], ", event.toString());
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
				+ "notes: test note, " + "reminder: Tue Oct 20 10:33:25 SGT 2015, " + "groups: [], ", event.toString());
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
				+ "notes: test note, " + "reminder: Tue Oct 20 10:33:25 SGT 2015, " + "groups: [], ", event.toString());
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
						+ "notes: test note, " + "reminder: Tue Oct 20 10:33:25 SGT 2015, " + "groups: [], ",
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
				+ "notes: test note, " + "reminder: Tue Oct 20 10:33:25 SGT 2015, " + "groups: [], ", event.toString());
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
						+ "notes: test note, " + "reminder: Tue Oct 20 10:33:25 SGT 2015, " + "groups: [], ",
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
				+ "notes: null, " + "reminder: Tue Oct 20 10:33:25 SGT 2015, " + "groups: [], ", event.toString());
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
						+ "location: test location, " + "notes: test note, " + "reminder: null, " + "groups: [], ",
				event.toString());
	}

}
