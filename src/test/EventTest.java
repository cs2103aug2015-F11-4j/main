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
	public void testToStringWithGroups1Item() {
		String s = "id: testId, "
				+ "mainId: testMainId, "
				+ "title: testTitle, "
				+ "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, "
				+ "priority: HIGH, "
				+ "location: test location, "
				+ "notes: test note, "
				+ "reminder: [2015/10/21-11:34, 2015/10/21-11:34], "
				+ "groups: [abc], "
				+ "recurrence: WEEKLY, "
				+ "subtasks: [abcd, defg], ";
		
		Calendar startDateTime = Calendar.getInstance();
		startDateTime.setTimeInMillis(Long.valueOf("1445308380000"));
		Calendar endDateTime = Calendar.getInstance();
		endDateTime.setTimeInMillis(Long.valueOf("1445308380000"));
		Calendar reminder = Calendar.getInstance();
		reminder.setTimeInMillis(Long.valueOf("1445398440000"));
		
		Event e = new Event();
		e.setTitle("testTitle");
		e.setId("testId");
		e.setMainId("testMainId");
		e.setStartDateTime(startDateTime);
		e.setEndDateTime(endDateTime);
		e.setPriority(Priority.HIGH);
		e.setLocation("test location");
		e.setNotes("test note");
		e.addGroup("abc");
		e.setRecurrence(Recurrence.WEEKLY);
		e.addReminder(reminder);
		e.addReminder(reminder);
		e.addSubtask("abcd");
		e.addSubtask("defg");
		
		assertEquals(e.toString(), s);
		
		assertTrue(true);
	}

	@Test
	public void testToStringWithGroups2Items() {
		String s = "id: testId, "
				+ "mainId: testMainId, "
				+ "title: testTitle, "
				+ "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, "
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
		endDateTime.setTimeInMillis(Long.valueOf("1445308380000"));
		Calendar reminder = Calendar.getInstance();
		reminder.setTimeInMillis(Long.valueOf("1445398440000"));
		
		Event e = new Event();
		e.setTitle("testTitle");
		e.setMainId("testMainId");
		e.setId("testId");
		e.setStartDateTime(startDateTime);
		e.setEndDateTime(endDateTime);
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
	public void testToStringNoId() {
		String s = "id: null, "
				+ "mainId: testMainId, "
				+ "title: testTitle, "
				+ "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, "
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
		endDateTime.setTimeInMillis(Long.valueOf("1445308380000"));
		Calendar reminder = Calendar.getInstance();
		reminder.setTimeInMillis(Long.valueOf("1445398440000"));
		
		Event e = new Event();
		e.setTitle("testTitle");
		e.setId(null);
		e.setMainId("testMainId");
		e.setStartDateTime(startDateTime);
		e.setEndDateTime(endDateTime);
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
	public void testToStringNoMainId() {
		String s = "id: testId, "
				+ "mainId: null, "
				+ "title: testTitle, "
				+ "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, "
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
		endDateTime.setTimeInMillis(Long.valueOf("1445308380000"));
		Calendar reminder = Calendar.getInstance();
		reminder.setTimeInMillis(Long.valueOf("1445398440000"));
		
		Event e = new Event();
		e.setTitle("testTitle");
		e.setId("testId");
		e.setMainId(null);
		e.setStartDateTime(startDateTime);
		e.setEndDateTime(endDateTime);
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
	public void testToStringNoTitle() {
		String s = "id: testId, "
				+ "mainId: testMainId, "
				+ "title: null, "
				+ "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, "
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
		endDateTime.setTimeInMillis(Long.valueOf("1445308380000"));
		Calendar reminder = Calendar.getInstance();
		reminder.setTimeInMillis(Long.valueOf("1445398440000"));
		
		Event e = new Event();
		e.setTitle(null);
		e.setId("testId");
		e.setMainId("testMainId");
		e.setStartDateTime(startDateTime);
		e.setEndDateTime(endDateTime);
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
	public void testToStringNoStartDateTime() {
		String s = "id: testId, "
				+ "mainId: testMainId, "
				+ "title: testTitle, "
				+ "startDateTime: null, "
				+ "endDateTime: 2015/10/20-10:33, "
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
		endDateTime.setTimeInMillis(Long.valueOf("1445308380000"));
		Calendar reminder = Calendar.getInstance();
		reminder.setTimeInMillis(Long.valueOf("1445398440000"));
		
		Event e = new Event();
		e.setTitle("testTitle");
		e.setId("testId");
		e.setMainId("testMainId");
		e.setStartDateTime(null);
		e.setEndDateTime(endDateTime);
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
	public void testToStringNoEndDateTime() {
		String s = "id: testId, "
				+ "mainId: testMainId, "
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
		endDateTime.setTimeInMillis(Long.valueOf("1445308380000"));
		Calendar reminder = Calendar.getInstance();
		reminder.setTimeInMillis(Long.valueOf("1445398440000"));
		
		Event e = new Event();
		e.setTitle("testTitle");
		e.setId("testId");
		e.setMainId("testMainId");
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
	public void testToStringNoPriority() {
		String s = "id: testId, "
				+ "mainId: testMainId, "
				+ "title: testTitle, "
				+ "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, "
				+ "priority: null, "
				+ "location: test location, "
				+ "notes: test note, "
				+ "reminder: [2015/10/21-11:34, 2015/10/21-11:34], "
				+ "groups: [abc, def], "
				+ "recurrence: WEEKLY, "
				+ "subtasks: [abcd, defg], ";
		
		Calendar startDateTime = Calendar.getInstance();
		startDateTime.setTimeInMillis(Long.valueOf("1445308380000"));
		Calendar endDateTime = Calendar.getInstance();
		endDateTime.setTimeInMillis(Long.valueOf("1445308380000"));
		Calendar reminder = Calendar.getInstance();
		reminder.setTimeInMillis(Long.valueOf("1445398440000"));
		
		Event e = new Event();
		e.setTitle("testTitle");
		e.setId("testId");
		e.setMainId("testMainId");
		e.setStartDateTime(startDateTime);
		e.setEndDateTime(endDateTime);
		e.setPriority(null);
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
	public void testToStringNoLocation() {
		String s = "id: testId, "
				+ "mainId: testMainId, "
				+ "title: testTitle, "
				+ "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, "
				+ "priority: HIGH, "
				+ "location: null, "
				+ "notes: test note, "
				+ "reminder: [2015/10/21-11:34, 2015/10/21-11:34], "
				+ "groups: [abc, def], "
				+ "recurrence: WEEKLY, "
				+ "subtasks: [abcd, defg], ";
		
		Calendar startDateTime = Calendar.getInstance();
		startDateTime.setTimeInMillis(Long.valueOf("1445308380000"));
		Calendar endDateTime = Calendar.getInstance();
		endDateTime.setTimeInMillis(Long.valueOf("1445308380000"));
		Calendar reminder = Calendar.getInstance();
		reminder.setTimeInMillis(Long.valueOf("1445398440000"));
		
		Event e = new Event();
		e.setTitle("testTitle");
		e.setId("testId");
		e.setMainId("testMainId");
		e.setStartDateTime(startDateTime);
		e.setEndDateTime(endDateTime);
		e.setPriority(Priority.HIGH);
		e.setLocation(null);
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
	public void testToStringNoNotes() {
		String s = "id: testId, "
				+ "mainId: testMainId, "
				+ "title: testTitle, "
				+ "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, "
				+ "priority: HIGH, "
				+ "location: test location, "
				+ "notes: null, "
				+ "reminder: [2015/10/21-11:34, 2015/10/21-11:34], "
				+ "groups: [abc, def], "
				+ "recurrence: WEEKLY, "
				+ "subtasks: [abcd, defg], ";
		
		Calendar startDateTime = Calendar.getInstance();
		startDateTime.setTimeInMillis(Long.valueOf("1445308380000"));
		Calendar endDateTime = Calendar.getInstance();
		endDateTime.setTimeInMillis(Long.valueOf("1445308380000"));
		Calendar reminder = Calendar.getInstance();
		reminder.setTimeInMillis(Long.valueOf("1445398440000"));
		
		Event e = new Event();
		e.setTitle("testTitle");
		e.setId("testId");
		e.setMainId("testMainId");
		e.setStartDateTime(startDateTime);
		e.setEndDateTime(endDateTime);
		e.setPriority(Priority.HIGH);
		e.setLocation("test location");
		e.setNotes(null);
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
	public void testToStringNoReminder() {
		String s = "id: testId, "
				+ "mainId: testMainId, "
				+ "title: testTitle, "
				+ "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, "
				+ "priority: HIGH, "
				+ "location: test location, "
				+ "notes: test note, "
				+ "reminder: [], "
				+ "groups: [abc, def], "
				+ "recurrence: WEEKLY, "
				+ "subtasks: [abcd, defg], ";
		
		Calendar startDateTime = Calendar.getInstance();
		startDateTime.setTimeInMillis(Long.valueOf("1445308380000"));
		Calendar endDateTime = Calendar.getInstance();
		endDateTime.setTimeInMillis(Long.valueOf("1445308380000"));
		Calendar reminder = Calendar.getInstance();
		reminder.setTimeInMillis(Long.valueOf("1445398440000"));
		
		Event e = new Event();
		e.setTitle("testTitle");
		e.setId("testId");
		e.setMainId("testMainId");
		e.setStartDateTime(startDateTime);
		e.setEndDateTime(endDateTime);
		e.setPriority(Priority.HIGH);
		e.setLocation("test location");
		e.setNotes("test note");
		e.addGroup("abc");
		e.addGroup("def");
		e.setRecurrence(Recurrence.WEEKLY);
		e.addSubtask("abcd");
		e.addSubtask("defg");
		
		assertEquals(e.toString(), s);
		
		assertTrue(true);
	}
	
	@Test
	public void testToString1Reminder(){
		String s = "id: testId, "
				+ "mainId: testMainId, "
				+ "title: testTitle, "
				+ "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, "
				+ "priority: HIGH, "
				+ "location: test location, "
				+ "notes: test note, "
				+ "reminder: [2015/10/21-11:34], "
				+ "groups: [abc, def], "
				+ "recurrence: WEEKLY, "
				+ "subtasks: [abcd, defg], ";
		
		Calendar startDateTime = Calendar.getInstance();
		startDateTime.setTimeInMillis(Long.valueOf("1445308380000"));
		Calendar endDateTime = Calendar.getInstance();
		endDateTime.setTimeInMillis(Long.valueOf("1445308380000"));
		Calendar reminder = Calendar.getInstance();
		reminder.setTimeInMillis(Long.valueOf("1445398440000"));
		
		Event e = new Event();
		e.setTitle("testTitle");
		e.setId("testId");
		e.setMainId("testMainId");
		e.setStartDateTime(startDateTime);
		e.setEndDateTime(endDateTime);
		e.setPriority(Priority.HIGH);
		e.setLocation("test location");
		e.setNotes("test note");
		e.addGroup("abc");
		e.addGroup("def");
		e.setRecurrence(Recurrence.WEEKLY);
		e.addReminder(reminder);
		e.addSubtask("abcd");
		e.addSubtask("defg");
		
		assertEquals(e.toString(), s);
		
		assertTrue(true);
	}
	
	@Test
	public void testToString2Reminder(){
		String s = "id: testId, "
				+ "mainId: testMainId, "
				+ "title: testTitle, "
				+ "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, "
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
		endDateTime.setTimeInMillis(Long.valueOf("1445308380000"));
		Calendar reminder = Calendar.getInstance();
		reminder.setTimeInMillis(Long.valueOf("1445398440000"));
		
		Event e = new Event();
		e.setTitle("testTitle");
		e.setId("testId");
		e.setMainId("testMainId");
		e.setStartDateTime(startDateTime);
		e.setEndDateTime(endDateTime);
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
	public void testToStringNoSubtask(){
		String s = "id: testId, "
				+ "mainId: testMainId, "
				+ "title: testTitle, "
				+ "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, "
				+ "priority: HIGH, "
				+ "location: test location, "
				+ "notes: test note, "
				+ "reminder: [2015/10/21-11:34, 2015/10/21-11:34], "
				+ "groups: [abc, def], "
				+ "recurrence: WEEKLY, "
				+ "subtasks: [], ";
		
		Calendar startDateTime = Calendar.getInstance();
		startDateTime.setTimeInMillis(Long.valueOf("1445308380000"));
		Calendar endDateTime = Calendar.getInstance();
		endDateTime.setTimeInMillis(Long.valueOf("1445308380000"));
		Calendar reminder = Calendar.getInstance();
		reminder.setTimeInMillis(Long.valueOf("1445398440000"));
		
		Event e = new Event();
		e.setTitle("testTitle");
		e.setId("testId");
		e.setMainId("testMainId");
		e.setStartDateTime(startDateTime);
		e.setEndDateTime(endDateTime);
		e.setPriority(Priority.HIGH);
		e.setLocation("test location");
		e.setNotes("test note");
		e.addGroup("abc");
		e.addGroup("def");
		e.setRecurrence(Recurrence.WEEKLY);
		e.addReminder(reminder);
		e.addReminder(reminder);
		
		assertEquals(e.toString(), s);
		
		assertTrue(true);
	}
	
	@Test
	public void testToString1Subtask(){
		String s = "id: testId, "
				+ "mainId: testMainId, "
				+ "title: testTitle, "
				+ "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, "
				+ "priority: HIGH, "
				+ "location: test location, "
				+ "notes: test note, "
				+ "reminder: [2015/10/21-11:34, 2015/10/21-11:34], "
				+ "groups: [abc, def], "
				+ "recurrence: WEEKLY, "
				+ "subtasks: [abcd], ";
		
		Calendar startDateTime = Calendar.getInstance();
		startDateTime.setTimeInMillis(Long.valueOf("1445308380000"));
		Calendar endDateTime = Calendar.getInstance();
		endDateTime.setTimeInMillis(Long.valueOf("1445308380000"));
		Calendar reminder = Calendar.getInstance();
		reminder.setTimeInMillis(Long.valueOf("1445398440000"));
		
		Event e = new Event();
		e.setTitle("testTitle");
		e.setId("testId");
		e.setMainId("testMainId");
		e.setStartDateTime(startDateTime);
		e.setEndDateTime(endDateTime);
		e.setPriority(Priority.HIGH);
		e.setLocation("test location");
		e.setNotes("test note");
		e.addGroup("abc");
		e.addGroup("def");
		e.setRecurrence(Recurrence.WEEKLY);
		e.addReminder(reminder);
		e.addReminder(reminder);
		e.addSubtask("abcd");
		
		assertEquals(e.toString(), s);
		
		assertTrue(true);
	}
	
	@Test
	public void testToString2Subtask(){
		String s = "id: testId, "
				+ "mainId: testMainId, "
				+ "title: testTitle, "
				+ "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, "
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
		endDateTime.setTimeInMillis(Long.valueOf("1445308380000"));
		Calendar reminder = Calendar.getInstance();
		reminder.setTimeInMillis(Long.valueOf("1445398440000"));
		
		Event e = new Event();
		e.setTitle("testTitle");
		e.setId("testId");
		e.setMainId("testMainId");
		e.setStartDateTime(startDateTime);
		e.setEndDateTime(endDateTime);
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
	public void testToStringNoRecurrence(){
		String s = "id: testId, "
				+ "mainId: testMainId, "
				+ "title: testTitle, "
				+ "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, "
				+ "priority: HIGH, "
				+ "location: test location, "
				+ "notes: test note, "
				+ "reminder: [2015/10/21-11:34, 2015/10/21-11:34], "
				+ "groups: [abc, def], "
				+ "recurrence: null, "
				+ "subtasks: [abcd, defg], ";
		
		Calendar startDateTime = Calendar.getInstance();
		startDateTime.setTimeInMillis(Long.valueOf("1445308380000"));
		Calendar endDateTime = Calendar.getInstance();
		endDateTime.setTimeInMillis(Long.valueOf("1445308380000"));
		Calendar reminder = Calendar.getInstance();
		reminder.setTimeInMillis(Long.valueOf("1445398440000"));
		
		Event e = new Event();
		e.setTitle("testTitle");
		e.setId("testId");
		e.setMainId("testMainId");
		e.setStartDateTime(startDateTime);
		e.setEndDateTime(endDateTime);
		e.setPriority(Priority.HIGH);
		e.setLocation("test location");
		e.setNotes("test note");
		e.addGroup("abc");
		e.addGroup("def");
		e.setRecurrence(null);
		e.addReminder(reminder);
		e.addReminder(reminder);
		e.addSubtask("abcd");
		e.addSubtask("defg");
		
		assertEquals(e.toString(), s);
		
		assertTrue(true);
	}
	
	@Test
	public void testFromStringWithNoGroupsItem(){
		String c = "id: testId, "
				+ "mainId: testMainId, "
				+ "title: testTitle, "
				+ "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/21-11:34, "
				+ "priority: MEDIUM, "
				+ "location: test location, "
				+ "notes: test note, "
				+ "reminder: [2015/10/21-11:34, 2015/10/21-11:34], "
				+ "groups: [], "
				+ "recurrence: null, "
				+ "subtasks: [abcd, defg], ";
		
		Event e = new Event();
		e.fromString(c);
		
		assertEquals("testId", e.getId());
		assertEquals("testMainId", e.getMainId());
		assertEquals("testTitle", e.getTitle());
		assertEquals("2015/10/20-10:33", e.toTimestamp(e.getStartDateTime()));
		assertEquals("2015/10/21-11:34", e.toTimestamp(e.getEndDateTime()));
		assertEquals("MEDIUM", e.getPriority().name());
		assertEquals("test location", e.getLocation());
		assertEquals("test note", e.getNotes());
		assertEquals(null, e.getRecurrence());
		assertEquals("[2015/10/21-11:34, 2015/10/21-11:34]", Arrays.toString(e.getReminderList().toArray()));
		assertEquals("[]", Arrays.toString(e.getGroups().toArray()));
		assertEquals("[abcd, defg]", Arrays.toString(e.getSubtasks().toArray()));

		assertTrue(true);
	}
	
	@Test
	public void testFromStringWithGroups1Item() {
		String c = "id: testId, "
				+ "mainId: testMainId, "
				+ "title: testTitle, "
				+ "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/21-11:34, "
				+ "priority: MEDIUM, "
				+ "location: test location, "
				+ "notes: test note, "
				+ "reminder: [2015/10/21-11:34, 2015/10/21-11:34], "
				+ "groups: [abc], "
				+ "recurrence: null, "
				+ "subtasks: [abcd, defg], ";
		
		Event e = new Event();
		e.fromString(c);
		
		assertEquals("testId", e.getId());
		assertEquals("testMainId", e.getMainId());
		assertEquals("testTitle", e.getTitle());
		assertEquals("2015/10/20-10:33", e.toTimestamp(e.getStartDateTime()));
		assertEquals("2015/10/21-11:34", e.toTimestamp(e.getEndDateTime()));
		assertEquals("MEDIUM", e.getPriority().name());
		assertEquals("test location", e.getLocation());
		assertEquals("test note", e.getNotes());
		assertEquals(null, e.getRecurrence());
		assertEquals("[2015/10/21-11:34, 2015/10/21-11:34]", Arrays.toString(e.getReminderList().toArray()));
		assertEquals("[abc]", Arrays.toString(e.getGroups().toArray()));
		assertEquals("[abcd, defg]", Arrays.toString(e.getSubtasks().toArray()));

		assertTrue(true);
	}

	@Test
	public void testFromStringWithGroups2Items() {
		String c = "id: testId, "
				+ "mainId: testMainId, "
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
		assertEquals("testMainId", e.getMainId());
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

	@Test
	public void testFromStringNoId() {
		String c = "id: null, "
				+ "mainId: testMainId, "
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
		
		assertEquals(null, e.getId());
		assertEquals("testMainId", e.getMainId());
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
	
	@Test
	public void testFromStringNoMainId() {
		String c = "id: testId, "
				+ "mainId: null, "
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
		assertEquals(null, e.getMainId());
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

	@Test
	public void testFromStringNoTitle() {
		String c = "id: testId, "
				+ "mainId: testMainId, "
				+ "title: null, "
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
		assertEquals("testMainId", e.getMainId());
		assertEquals(null, e.getTitle());
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

	@Test
	public void testFromStringNoStartDateTime() {
		String c = "id: testId, "
				+ "mainId: testMainId, "
				+ "title: testTitle, "
				+ "startDateTime: null, "
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
		assertEquals("testMainId", e.getMainId());
		assertEquals("testTitle", e.getTitle());
		assertEquals(null, e.toTimestamp(e.getStartDateTime()));
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

	@Test
	public void testFromStringNoEndDateTime() {
		String c = "id: testId, "
				+ "mainId: testMainId, "
				+ "title: testTitle, "
				+ "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: null, "
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
		assertEquals("testMainId", e.getMainId());
		assertEquals("testTitle", e.getTitle());
		assertEquals("2015/10/20-10:33", e.toTimestamp(e.getStartDateTime()));
		assertEquals(null, e.toTimestamp(e.getEndDateTime()));
		assertEquals("MEDIUM", e.getPriority().name());
		assertEquals("test location", e.getLocation());
		assertEquals("test note", e.getNotes());
		assertEquals(null, e.getRecurrence());
		assertEquals("[2015/10/21-11:34, 2015/10/21-11:34]", Arrays.toString(e.getReminderList().toArray()));
		assertEquals("[abc, def]", Arrays.toString(e.getGroups().toArray()));
		assertEquals("[abcd, defg]", Arrays.toString(e.getSubtasks().toArray()));

		assertTrue(true);
	}

	@Test
	public void testFromStringNoPriority() {
		String c = "id: testId, "
				+ "mainId: testMainId, "
				+ "title: testTitle, "
				+ "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/21-11:34, "
				+ "priority: null, "
				+ "location: test location, "
				+ "notes: test note, "
				+ "reminder: [2015/10/21-11:34, 2015/10/21-11:34], "
				+ "groups: [abc, def], "
				+ "recurrence: null, "
				+ "subtasks: [abcd, defg], ";
		
		Event e = new Event();
		e.fromString(c);
		
		assertEquals("testId", e.getId());
		assertEquals("testMainId", e.getMainId());
		assertEquals("testTitle", e.getTitle());
		assertEquals("2015/10/20-10:33", e.toTimestamp(e.getStartDateTime()));
		assertEquals("2015/10/21-11:34", e.toTimestamp(e.getEndDateTime()));
		assertEquals(null, e.getPriority());
		assertEquals("test location", e.getLocation());
		assertEquals("test note", e.getNotes());
		assertEquals(null, e.getRecurrence());
		assertEquals("[2015/10/21-11:34, 2015/10/21-11:34]", Arrays.toString(e.getReminderList().toArray()));
		assertEquals("[abc, def]", Arrays.toString(e.getGroups().toArray()));
		assertEquals("[abcd, defg]", Arrays.toString(e.getSubtasks().toArray()));

		assertTrue(true);
	}

	@Test
	public void testFromStringNoLocation() {
		String c = "id: testId, "
				+ "mainId: testMainId, "
				+ "title: testTitle, "
				+ "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/21-11:34, "
				+ "priority: MEDIUM, "
				+ "location: null, "
				+ "notes: test note, "
				+ "reminder: [2015/10/21-11:34, 2015/10/21-11:34], "
				+ "groups: [abc, def], "
				+ "recurrence: null, "
				+ "subtasks: [abcd, defg], ";
		
		Event e = new Event();
		e.fromString(c);
		
		assertEquals("testId", e.getId());
		assertEquals("testMainId", e.getMainId());
		assertEquals("testTitle", e.getTitle());
		assertEquals("2015/10/20-10:33", e.toTimestamp(e.getStartDateTime()));
		assertEquals("2015/10/21-11:34", e.toTimestamp(e.getEndDateTime()));
		assertEquals("MEDIUM", e.getPriority().name());
		assertEquals(null, e.getLocation());
		assertEquals("test note", e.getNotes());
		assertEquals(null, e.getRecurrence());
		assertEquals("[2015/10/21-11:34, 2015/10/21-11:34]", Arrays.toString(e.getReminderList().toArray()));
		assertEquals("[abc, def]", Arrays.toString(e.getGroups().toArray()));
		assertEquals("[abcd, defg]", Arrays.toString(e.getSubtasks().toArray()));

		assertTrue(true);
	}

	@Test
	public void testFromStringNoNotes() {
		String c = "id: testId, "
				+ "mainId: testMainId, "
				+ "title: testTitle, "
				+ "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/21-11:34, "
				+ "priority: MEDIUM, "
				+ "location: test location, "
				+ "notes: null, "
				+ "reminder: [2015/10/21-11:34, 2015/10/21-11:34], "
				+ "groups: [abc, def], "
				+ "recurrence: null, "
				+ "subtasks: [abcd, defg], ";
		
		Event e = new Event();
		e.fromString(c);
		
		assertEquals("testId", e.getId());
		assertEquals("testMainId", e.getMainId());
		assertEquals("testTitle", e.getTitle());
		assertEquals("2015/10/20-10:33", e.toTimestamp(e.getStartDateTime()));
		assertEquals("2015/10/21-11:34", e.toTimestamp(e.getEndDateTime()));
		assertEquals("MEDIUM", e.getPriority().name());
		assertEquals("test location", e.getLocation());
		assertEquals(null, e.getNotes());
		assertEquals(null, e.getRecurrence());
		assertEquals("[2015/10/21-11:34, 2015/10/21-11:34]", Arrays.toString(e.getReminderList().toArray()));
		assertEquals("[abc, def]", Arrays.toString(e.getGroups().toArray()));
		assertEquals("[abcd, defg]", Arrays.toString(e.getSubtasks().toArray()));

		assertTrue(true);
	}

	@Test
	public void testFromStringNoReminder() {
		String c = "id: testId, "
				+ "mainId: testMainId, "
				+ "title: testTitle, "
				+ "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/21-11:34, "
				+ "priority: MEDIUM, "
				+ "location: test location, "
				+ "notes: test note, "
				+ "reminder: [], "
				+ "groups: [abc, def], "
				+ "recurrence: null, "
				+ "subtasks: [abcd, defg], ";
		
		Event e = new Event();
		e.fromString(c);
		
		assertEquals("testId", e.getId());
		assertEquals("testMainId", e.getMainId());
		assertEquals("testTitle", e.getTitle());
		assertEquals("2015/10/20-10:33", e.toTimestamp(e.getStartDateTime()));
		assertEquals("2015/10/21-11:34", e.toTimestamp(e.getEndDateTime()));
		assertEquals("MEDIUM", e.getPriority().name());
		assertEquals("test location", e.getLocation());
		assertEquals("test note", e.getNotes());
		assertEquals(null, e.getRecurrence());
		assertEquals("[]", Arrays.toString(e.getReminderList().toArray()));
		assertEquals("[abc, def]", Arrays.toString(e.getGroups().toArray()));
		assertEquals("[abcd, defg]", Arrays.toString(e.getSubtasks().toArray()));

		assertTrue(true);
	}
	
	@Test
	public void testFromString1Reminder(){
		String c = "id: testId, "
				+ "mainId: testMainId, "
				+ "title: testTitle, "
				+ "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/21-11:34, "
				+ "priority: MEDIUM, "
				+ "location: test location, "
				+ "notes: test note, "
				+ "reminder: [2015/10/21-11:34], "
				+ "groups: [abc, def], "
				+ "recurrence: null, "
				+ "subtasks: [abcd, defg], ";
		
		Event e = new Event();
		e.fromString(c);
		
		assertEquals("testId", e.getId());
		assertEquals("testMainId", e.getMainId());
		assertEquals("testTitle", e.getTitle());
		assertEquals("2015/10/20-10:33", e.toTimestamp(e.getStartDateTime()));
		assertEquals("2015/10/21-11:34", e.toTimestamp(e.getEndDateTime()));
		assertEquals("MEDIUM", e.getPriority().name());
		assertEquals("test location", e.getLocation());
		assertEquals("test note", e.getNotes());
		assertEquals(null, e.getRecurrence());
		assertEquals("[2015/10/21-11:34]", Arrays.toString(e.getReminderList().toArray()));
		assertEquals("[abc, def]", Arrays.toString(e.getGroups().toArray()));
		assertEquals("[abcd, defg]", Arrays.toString(e.getSubtasks().toArray()));

		assertTrue(true);
	}
	
	@Test
	public void testFromString2Reminder(){
		String c = "id: testId, "
				+ "mainId: testMainId, "
				+ "title: testTitle, "
				+ "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/21-11:34, "
				+ "priority: MEDIUM, "
				+ "location: test location, "
				+ "notes: test note, "
				+ "reminder: [2015/10/21-11:34, 2015/10/21-11:34], "
				+ "groups: [abc, def], "
				+ "recurrence: MONTHLY, "
				+ "subtasks: [abcd, defg], ";
		
		Event e = new Event();
		e.fromString(c);
		
		assertEquals("testId", e.getId());
		assertEquals("testMainId", e.getMainId());
		assertEquals("testTitle", e.getTitle());
		assertEquals("2015/10/20-10:33", e.toTimestamp(e.getStartDateTime()));
		assertEquals("2015/10/21-11:34", e.toTimestamp(e.getEndDateTime()));
		assertEquals("MEDIUM", e.getPriority().name());
		assertEquals("test location", e.getLocation());
		assertEquals("test note", e.getNotes());
		assertEquals(Recurrence.MONTHLY, e.getRecurrence());
		assertEquals("[2015/10/21-11:34, 2015/10/21-11:34]", Arrays.toString(e.getReminderList().toArray()));
		assertEquals("[abc, def]", Arrays.toString(e.getGroups().toArray()));
		assertEquals("[abcd, defg]", Arrays.toString(e.getSubtasks().toArray()));

		assertTrue(true);
	}
	
	@Test
	public void testFromStringNoSubtask(){
		String c = "id: testId, "
				+ "mainId: testMainId, "
				+ "title: testTitle, "
				+ "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/21-11:34, "
				+ "priority: MEDIUM, "
				+ "location: test location, "
				+ "notes: test note, "
				+ "reminder: [2015/10/21-11:34, 2015/10/21-11:34], "
				+ "groups: [abc, def], "
				+ "recurrence: null, "
				+ "subtasks: [], ";
		
		Event e = new Event();
		e.fromString(c);
		
		assertEquals("testId", e.getId());
		assertEquals("testMainId", e.getMainId());
		assertEquals("testTitle", e.getTitle());
		assertEquals("2015/10/20-10:33", e.toTimestamp(e.getStartDateTime()));
		assertEquals("2015/10/21-11:34", e.toTimestamp(e.getEndDateTime()));
		assertEquals("MEDIUM", e.getPriority().name());
		assertEquals("test location", e.getLocation());
		assertEquals("test note", e.getNotes());
		assertEquals(null, e.getRecurrence());
		assertEquals("[2015/10/21-11:34, 2015/10/21-11:34]", Arrays.toString(e.getReminderList().toArray()));
		assertEquals("[abc, def]", Arrays.toString(e.getGroups().toArray()));
		assertEquals("[]", Arrays.toString(e.getSubtasks().toArray()));

		assertTrue(true);
	}
	
	@Test
	public void testFromString1Subtask(){
		String c = "id: testId, "
				+ "mainId: testMainId, "
				+ "title: testTitle, "
				+ "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/21-11:34, "
				+ "priority: MEDIUM, "
				+ "location: test location, "
				+ "notes: test note, "
				+ "reminder: [2015/10/21-11:34, 2015/10/21-11:34], "
				+ "groups: [abc, def], "
				+ "recurrence: null, "
				+ "subtasks: [abcd], ";
		
		Event e = new Event();
		e.fromString(c);
		
		assertEquals("testId", e.getId());
		assertEquals("testMainId", e.getMainId());
		assertEquals("testTitle", e.getTitle());
		assertEquals("2015/10/20-10:33", e.toTimestamp(e.getStartDateTime()));
		assertEquals("2015/10/21-11:34", e.toTimestamp(e.getEndDateTime()));
		assertEquals("MEDIUM", e.getPriority().name());
		assertEquals("test location", e.getLocation());
		assertEquals("test note", e.getNotes());
		assertEquals(null, e.getRecurrence());
		assertEquals("[2015/10/21-11:34, 2015/10/21-11:34]", Arrays.toString(e.getReminderList().toArray()));
		assertEquals("[abc, def]", Arrays.toString(e.getGroups().toArray()));
		assertEquals("[abcd]", Arrays.toString(e.getSubtasks().toArray()));

		assertTrue(true);
	}
	
	@Test
	public void testFromString2Subtask(){
		String c = "id: testId, "
				+ "mainId: testMainId, "
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
		assertEquals("testMainId", e.getMainId());
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
	
	@Test
	public void testFromStringNoRecurrence(){
		String c = "id: testId, "
				+ "mainId: testMainId, "
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
		assertEquals("testMainId", e.getMainId());
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

	@Test
	public void testToString(){
		String s = "id: testId, "
				+ "mainId: testMainId, "
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
		e.setMainId("testMainId");
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
				+ "mainId: testMainId, "
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
		assertEquals("testMainId", e.getMainId());
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
