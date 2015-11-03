package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

import utils.Event;
import utils.Priority;
import utils.Recurrence;

public class EventTest {

	@Test
	public void testPriorityOrdinal() {
		assertTrue(Priority.VERY_HIGH.ordinal() > Priority.HIGH.ordinal());
		assertTrue(Priority.HIGH.ordinal() > Priority.MEDIUM.ordinal());
		assertTrue(Priority.MEDIUM.ordinal() > Priority.LOW.ordinal());
		assertTrue(Priority.LOW.ordinal() > Priority.VERY_LOW.ordinal());
	}

	@Test
	public void testRemoveGroup() {
		Event event = new Event();

		event.addGroup("group 1");

		event.removeGroup();
		assertEquals(null, event.getGroup());

	}
	
	@Test
	public void testAddReminder(){
		Event event = new Event();
		event.addReminder(Calendar.getInstance());
		assertEquals(1, event.getReminder().size());
	}
	
	@Test
	public void testAddNullReminder(){
		Event event = new Event();
		event.addReminder(null);
		assertEquals(0, event.getReminder().size());
	}
	
	@Test
	public void testSetReminder(){
		Event event = new Event();
		event.setReminder(Calendar.getInstance());
		assertEquals(1, event.getReminder().size());
	}
	
	@Test
	public void testSetNullReminder(){
		Event event = new Event();
		event.setReminder((Calendar) null);
		assertEquals(0, event.getReminder().size());
	}
	
	@Test
	public void testSetManyReminders(){
		List<Calendar> reminders = new ArrayList<>();
		Event event = new Event();

		reminders.add(Calendar.getInstance());
		reminders.add(Calendar.getInstance());
		assertEquals(2, reminders.size());
		
		event.setReminder(reminders);
		assertEquals(2, event.getReminder().size());
	}
	
	@Test
	public void testSetNullReminders(){
		Event event = new Event();
		event.setReminder((List<Calendar>) null);
		
		assertEquals(0, event.getReminder().size());
	}
	
	@Test
	public void testRemoveReminder(){
		List<Calendar> reminders = new ArrayList<>();
		Event event = new Event();
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c2.add(Calendar.SECOND, 10);

		reminders.add(c1);
		reminders.add(c2);
		assertEquals(2, reminders.size());
		
		event.setReminder(reminders);
		assertEquals(2, event.getReminder().size());
		
		event.removeReminder(c1);
		assertEquals(1, event.getReminder().size());
		assertFalse(event.getReminder().contains(c1));
	}
	
	@Test
	public void testRemoveReminderPosition(){
		List<Calendar> reminders = new ArrayList<>();
		Event event = new Event();
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c2.add(Calendar.SECOND, 10);

		reminders.add(c1);
		reminders.add(c2);
		assertEquals(2, reminders.size());
		
		event.setReminder(reminders);
		assertEquals(2, event.getReminder().size());
		
		event.removeReminder(0);
		assertEquals(1, event.getReminder().size());
		assertFalse(event.getReminder().contains(c1));
	}
	
	@Test
	public void testRemoveNullReminder(){
		List<Calendar> reminders = new ArrayList<>();
		Event event = new Event();
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c2.add(Calendar.SECOND, 10);

		reminders.add(c1);
		reminders.add(c2);
		assertEquals(2, reminders.size());
		
		event.setReminder(reminders);
		assertEquals(2, event.getReminder().size());
		
		event.removeReminder(null);
		assertEquals(2, event.getReminder().size());
		assertTrue(event.getReminder().contains(c1));
		assertTrue(event.getReminder().contains(c2));
	}
	
	@Test
	public void testRemoveAllReminder(){
		List<Calendar> reminders = new ArrayList<>();
		Event event = new Event();
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c2.add(Calendar.SECOND, 10);

		reminders.add(c1);
		reminders.add(c2);
		assertEquals(2, reminders.size());
		
		event.setReminder(reminders);
		assertEquals(2, event.getReminder().size());
		
		event.removeAllReminders();
		assertEquals(0, event.getReminder().size());
		assertFalse(event.getReminder().contains(c1));
		assertFalse(event.getReminder().contains(c2));
	}
	
	@Test
	public void testAddSubTask(){
		Event event = new Event();
		event.setId("main");
		
		Event subtask = new Event();
		subtask.setId("subtask");
		
		event.addSubtask(subtask);
		
		assertEquals(1, event.getSubtasks().size());
		assertTrue(event.getSubtasks().contains(subtask.getId()));
	}

	@Test
	public void testAddSubTaskId(){
		Event event = new Event();
		event.setId("main");
		
		Event subtask = new Event();
		subtask.setId("subtask");
		
		event.addSubtask(subtask.getId());
		
		assertEquals(1, event.getSubtasks().size());
		assertTrue(event.getSubtasks().contains(subtask.getId()));
	}
	
	@Test
	public void testAddNullSubTask(){
		Event event = new Event();
		event.setId("main");
		
		event.addSubtask((Event) null);
		
		assertEquals(0, event.getSubtasks().size());
	}
	
	@Test
	public void testAddNullSubTaskIdEvent(){
		Event event = new Event();
		event.setId("main");
		
		Event subtask = new Event();
		
		event.addSubtask(subtask);
		
		assertEquals(0, event.getSubtasks().size());
	}
	
	@Test
	public void testAddNullSubTaskId(){
		Event event = new Event();
		event.setId("main");
		
		event.addSubtask((String) null);
		
		assertEquals(0, event.getSubtasks().size());
	}

	@Test
	public void testRemoveSubTask(){
		Event event = new Event();
		event.setId("main");
		
		Event subtask = new Event();
		subtask.setId("subtask");
		
		event.addSubtask(subtask.getId());
		
		assertEquals(1, event.getSubtasks().size());
		assertTrue(event.getSubtasks().contains(subtask.getId()));
		
		event.removeSubtask(subtask.getId());
		assertEquals(0, event.getSubtasks().size());
		assertFalse(event.getSubtasks().contains(subtask.getId()));
	}
	
	@Test
	public void testRemoveNullSubTask(){
		Event event = new Event();
		event.setId("main");
		
		Event subtask = new Event();
		subtask.setId("subtask");
		
		event.addSubtask(subtask.getId());
		
		assertEquals(1, event.getSubtasks().size());
		assertTrue(event.getSubtasks().contains(subtask.getId()));
		
		event.removeSubtask(null);
		assertEquals(1, event.getSubtasks().size());
		assertTrue(event.getSubtasks().contains(subtask.getId()));
	}
	
	@Test
	public void testRemoveAllSubtasks(){
		Event event = new Event();
		event.setId("main");
		
		Event subtask = new Event();
		subtask.setId("subtask");
		
		event.addSubtask(subtask.getId());
		
		assertEquals(1, event.getSubtasks().size());
		assertTrue(event.getSubtasks().contains(subtask.getId()));
		
		event.removeAllSubtasks();
		assertEquals(0, event.getSubtasks().size());
		assertFalse(event.getSubtasks().contains(subtask.getId()));
	}
	
	@Test
	public void testFromTimeStampEmptyYear() {
		String s = "id: test," + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: /10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: WEEKLY, " + "subtasks: [abcd, defg], ";
		Event event = new Event();
		event.fromString(s);

		assertEquals(event.getStartDateTime(), null);
	}

	@Test
	public void testFromTimeStampEmptyMonth() {
		String s = "id: test," + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015//20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: WEEKLY, " + "subtasks: [abcd, defg], ";
		Event event = new Event();
		event.fromString(s);

		assertEquals(event.getStartDateTime(), null);
	}

	@Test
	public void testFromTimeStampEmptyDate() {
		String s = "id: test," + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: WEEKLY, " + "subtasks: [abcd, defg], ";
		Event event = new Event();
		event.fromString(s);

		assertEquals(event.getStartDateTime(), null);
	}

	@Test
	public void testFromTimeStampEmptyHour() {
		String s = "id: test," + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: WEEKLY, " + "subtasks: [abcd, defg], ";
		Event event = new Event();
		event.fromString(s);

		assertEquals(event.getStartDateTime(), null);
	}

	@Test
	public void testFromTimeStampEmptyMinute() {
		String s = "id: test," + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: WEEKLY, " + "subtasks: [abcd, defg], ";
		Event event = new Event();
		event.fromString(s);

		assertEquals(event.getStartDateTime(), null);
	}

	@Test
	public void testFromTimeStampInvalidYear() {
		String s = "id: test," + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: a/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: WEEKLY, " + "subtasks: [abcd, defg], ";
		Event event = new Event();
		event.fromString(s);

		assertEquals(event.getStartDateTime(), null);
	}

	@Test
	public void testFromTimeStampInvalidMonth() {
		String s = "id: test," + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/a/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: WEEKLY, " + "subtasks: [abcd, defg], ";
		Event event = new Event();
		event.fromString(s);

		assertEquals(event.getStartDateTime(), null);
	}

	@Test
	public void testFromTimeStampInvalidDate() {
		String s = "id: test," + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/a-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: WEEKLY, " + "subtasks: [abcd, defg], ";
		Event event = new Event();
		event.fromString(s);

		assertEquals(event.getStartDateTime(), null);
	}

	@Test
	public void testFromTimeStampInvalidHour() {
		String s = "id: test," + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-a:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: WEEKLY, " + "subtasks: [abcd, defg], ";
		Event event = new Event();
		event.fromString(s);

		assertEquals(event.getStartDateTime(), null);
	}

	@Test
	public void testFromTimeStampInvalidMinute() {
		String s = "id: test," + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:a, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: WEEKLY, " + "subtasks: [abcd, defg], ";
		Event event = new Event();
		event.fromString(s);

		assertEquals(event.getStartDateTime(), null);
	}

	@Test
	public void testNoIdParse() {
		String s = "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: WEEKLY, " + "subtasks: [abcd, defg], ";
		Event event = new Event();
		event.fromString(s);

		assertEquals(event.getId(), null);
	}

	@Test
	public void testEmptyIdParse() {
		String s = "id: ," + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: WEEKLY, " + "subtasks: [abcd, defg], ";
		Event event = new Event();
		event.fromString(s);

		assertEquals(event.getId(), null);
	}

	@Test
	public void testNoTitleParse() {
		String s = "id: testId," + "mainId: testMainId, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: WEEKLY, " + "subtasks: [abcd, defg], ";
		Event event = new Event();
		event.fromString(s);

		assertEquals(event.getTitle(), null);
	}

	@Test
	public void testEmptyTitleParse() {
		String s = "id: testId," + "mainId: testMainId, " + "title: , " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: WEEKLY, " + "subtasks: [abcd, defg], ";
		Event event = new Event();
		event.fromString(s);

		assertEquals(event.getTitle(), null);
	}

	@Test
	public void testNoMainIdParse() {
		String s = "id: testId," + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: WEEKLY, " + "subtasks: [abcd, defg], ";
		Event event = new Event();
		event.fromString(s);

		assertEquals(event.getMainId(), null);
	}

	@Test
	public void testEmptyMainIdParse() {
		String s = "id: testId," + "mainId: , " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: WEEKLY, " + "subtasks: [abcd, defg], ";
		Event event = new Event();
		event.fromString(s);

		assertEquals(event.getMainId(), null);
	}

	@Test
	public void testNoStartDateTimeParse() {
		String s = "id: testId," + "mainId: testMainId, " + "title: testTitle, " + "endDateTime: 2015/10/20-10:33, "
				+ "priority: HIGH, " + "location: test location, " + "notes: test note, "
				+ "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, " + "recurrence: WEEKLY, "
				+ "subtasks: [abcd, defg], ";
		Event event = new Event();
		event.fromString(s);

		assertEquals(event.getStartDateTime(), null);
	}

	@Test
	public void testEmptyStartDateTimeParse() {
		String s = "id: testId," + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: , "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: WEEKLY, " + "subtasks: [abcd, defg], ";
		Event event = new Event();
		event.fromString(s);

		assertEquals(event.getStartDateTime(), null);
	}

	@Test
	public void testNoEndDateTimeParse() {
		String s = "id: testId," + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "priority: HIGH, " + "location: test location, " + "notes: test note, "
				+ "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, " + "recurrence: WEEKLY, "
				+ "subtasks: [abcd, defg], ";
		Event event = new Event();
		event.fromString(s);

		assertEquals(event.getEndDateTime(), null);
	}

	@Test
	public void testEmptyEndDateTimeParse() {
		String s = "id: testId," + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: , " + "priority: HIGH, " + "location: test location, " + "notes: test note, "
				+ "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, " + "recurrence: WEEKLY, "
				+ "subtasks: [abcd, defg], ";
		Event event = new Event();
		event.fromString(s);

		assertEquals(event.getEndDateTime(), null);
	}

	@Test
	public void testNoPriorityParse() {
		String s = "id: testId," + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "location: test location, " + "notes: test note, "
				+ "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, " + "recurrence: WEEKLY, "
				+ "subtasks: [abcd, defg], ";
		Event event = new Event();
		event.fromString(s);

		assertEquals(event.getPriority(), null);
	}

	@Test
	public void testEmptyPriorityParse() {
		String s = "id: testId," + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: , " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: WEEKLY, " + "subtasks: [abcd, defg], ";
		Event event = new Event();
		event.fromString(s);

		assertEquals(event.getPriority(), null);
	}

	@Test
	public void testNoLocationParse() {
		String s = "id: testId," + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "notes: test note, "
				+ "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, " + "recurrence: WEEKLY, "
				+ "subtasks: [abcd, defg], ";
		Event event = new Event();
		event.fromString(s);

		assertEquals(event.getLocation(), null);
	}

	@Test
	public void testEmptyLocationParse() {
		String s = "id: testId," + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: , " + "notes: test note, "
				+ "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, " + "recurrence: WEEKLY, "
				+ "subtasks: [abcd, defg], ";
		Event event = new Event();
		event.fromString(s);

		assertEquals(event.getLocation(), null);
	}

	@Test
	public void testNoNotesParse() {
		String s = "id: testId," + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, " + "recurrence: WEEKLY, "
				+ "subtasks: [abcd, defg], ";
		Event event = new Event();
		event.fromString(s);

		assertEquals(event.getNotes(), null);
	}

	@Test
	public void testEmptyNotesParse() {
		String s = "id: testId," + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, " + "notes: , "
				+ "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, " + "recurrence: WEEKLY, "
				+ "subtasks: [abcd, defg], ";
		Event event = new Event();
		event.fromString(s);

		assertEquals(event.getNotes(), null);
	}

	@Test
	public void testNoReminderParse() {
		String s = "id: testId," + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "groups: abc, " + "recurrence: WEEKLY, " + "subtasks: [abcd, defg], ";
		Event event = new Event();
		event.fromString(s);

		assertEquals(event.getReminder().size(), 0);
	}

	@Test
	public void testEmptyReminderParse() {
		String s = "id: testId," + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: , " + "groups: abc, " + "recurrence: WEEKLY, "
				+ "subtasks: [abcd, defg], ";
		Event event = new Event();
		event.fromString(s);

		assertEquals(event.getReminder().size(), 0);
	}

	@Test
	public void testNoGroupsParse() {
		String s = "id: testId," + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "recurrence: WEEKLY, "
				+ "subtasks: [abcd, defg], ";
		Event event = new Event();
		event.fromString(s);

		assertEquals(null, event.getGroup());
	}

	@Test
	public void testEmptyGroupParse() {
		String s = "id: testId," + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: , "
				+ "recurrence: WEEKLY, " + "subtasks: [abcd, defg], ";
		Event event = new Event();
		event.fromString(s);

		assertEquals(null, event.getGroup());
	}

	@Test
	public void testNoRecurrenceParse() {
		String s = "id: testId," + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "subtasks: [abcd, defg], ";
		Event event = new Event();
		event.fromString(s);

		assertEquals(event.getRecurrence(), null);
	}

	@Test
	public void testEmptyRecurrenceParse() {
		String s = "id: testId," + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: , " + "subtasks: [abcd, defg], ";
		Event event = new Event();
		event.fromString(s);

		assertEquals(event.getRecurrence(), null);
	}

	@Test
	public void testNoSubtaskParse() {
		String s = "id: testId," + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: WEEKLY, ";
		Event event = new Event();
		event.fromString(s);

		assertEquals(event.getSubtasks().size(), 0);
	}

	@Test
	public void testEmptySubtasksParse() {
		String s = "id: testId," + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: WEEKLY, " + "subtasks: , ";
		Event event = new Event();
		event.fromString(s);

		assertEquals(event.getSubtasks().size(), 0);
	}

	@Test
	public void testCompareToNull() {
		Event testEvent = new Event();
		testEvent.setPriority(Priority.MEDIUM);

		Event event = new Event();
		event.setPriority(null);

		int result = event.compareTo(testEvent);
		assertEquals(1, result);
	}

	@Test
	public void testCompareToNullReversed() {
		Event testEvent = new Event();
		testEvent.setPriority(null);

		Event event = new Event();
		event.setPriority(Priority.MEDIUM);

		int result = event.compareTo(testEvent);
		assertEquals(-1, result);
	}

	@Test
	public void testCompareNullToNull() {
		Event testEvent = new Event();
		testEvent.setPriority(null);

		Event event = new Event();
		event.setPriority(null);

		int result = event.compareTo(testEvent);
		assertEquals(0, result);
	}

	@Test
	public void testCompareMediumToHigh() {
		Event testEvent = new Event();
		testEvent.setPriority(Priority.MEDIUM);

		Event event = new Event();
		event.setPriority(Priority.HIGH);

		int result = event.compareTo(testEvent);
		assertEquals(-1, result);
	}

	@Test
	public void testCompareMediumToLow() {
		Event testEvent = new Event();
		testEvent.setPriority(Priority.MEDIUM);

		Event event = new Event();
		event.setPriority(Priority.LOW);

		int result = event.compareTo(testEvent);
		assertEquals(1, result);
	}

	@Test
	public void testCompareMediumToMedium() {
		Event testEvent = new Event();
		testEvent.setPriority(Priority.MEDIUM);

		Event event = new Event();
		event.setPriority(Priority.MEDIUM);

		int result = event.compareTo(testEvent);
		assertEquals(0, result);
	}

	@Test
	public void testToStringWithGroups1Item() {
		String s = "id: testId, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: [abc], "
				+ "recurrence: WEEKLY, " + "subtasks: [abcd, defg], done: false, ";

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
		e.addGroup("[abc]");
		e.setRecurrence(Recurrence.WEEKLY);
		e.addReminder(reminder);
		e.addReminder(reminder);
		e.addSubtask("abcd");
		e.addSubtask("defg");

		assertEquals(e.toString(), s);

		assertTrue(true);
	}
	
	@Test
	public void testToStringNoDone() {
		String s = "id: null, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: WEEKLY, " + "subtasks: [abcd, defg], done: false, ";

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
		e.setRecurrence(Recurrence.WEEKLY);
		e.addReminder(reminder);
		e.addReminder(reminder);
		e.addSubtask("abcd");
		e.addSubtask("defg");

		assertEquals(e.toString(), s);

		assertTrue(true);
	}
	
	@Test
	public void testToStringDone() {
		String s = "id: null, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: WEEKLY, " + "subtasks: [abcd, defg], done: true, ";

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
		e.setRecurrence(Recurrence.WEEKLY);
		e.addReminder(reminder);
		e.addReminder(reminder);
		e.addSubtask("abcd");
		e.addSubtask("defg");
		e.setDone(true);

		assertEquals(e.toString(), s);

		assertTrue(true);
	}
	
	@Test
	public void testToStringNotDone() {
		String s = "id: null, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: WEEKLY, " + "subtasks: [abcd, defg], done: false, ";

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
		e.setRecurrence(Recurrence.WEEKLY);
		e.addReminder(reminder);
		e.addReminder(reminder);
		e.addSubtask("abcd");
		e.addSubtask("defg");
		e.setDone(false);

		assertEquals(e.toString(), s);

		assertTrue(true);
	}

	@Test
	public void testToStringNoId() {
		String s = "id: null, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: WEEKLY, " + "subtasks: [abcd, defg], done: false, ";

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
		String s = "id: testId, " + "mainId: null, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: WEEKLY, " + "subtasks: [abcd, defg], done: false, ";

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
		String s = "id: testId, " + "mainId: testMainId, " + "title: null, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: WEEKLY, " + "subtasks: [abcd, defg], done: false, ";

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
		String s = "id: testId, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: null, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: WEEKLY, " + "subtasks: [abcd, defg], done: false, ";

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
		String s = "id: testId, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: null, " + "priority: HIGH, " + "location: test location, " + "notes: test note, "
				+ "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, " + "recurrence: WEEKLY, "
				+ "subtasks: [abcd, defg], done: false, ";

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
		String s = "id: testId, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: null, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: WEEKLY, " + "subtasks: [abcd, defg], done: false, ";

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
		String s = "id: testId, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: null, " + "notes: test note, "
				+ "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, " + "recurrence: WEEKLY, "
				+ "subtasks: [abcd, defg], done: false, ";

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
		String s = "id: testId, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, " + "notes: null, "
				+ "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, " + "recurrence: WEEKLY, "
				+ "subtasks: [abcd, defg], done: false, ";

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
		String s = "id: testId, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [], " + "groups: abc, " + "recurrence: WEEKLY, "
				+ "subtasks: [abcd, defg], done: false, ";

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
		e.addSubtask("abcd");
		e.addSubtask("defg");

		assertEquals(e.toString(), s);

		assertTrue(true);
	}

	@Test
	public void testToString1Reminder() {
		String s = "id: testId, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: WEEKLY, " + "subtasks: [abcd, defg], done: false, ";

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
		e.addSubtask("abcd");
		e.addSubtask("defg");

		assertEquals(e.toString(), s);

		assertTrue(true);
	}

	@Test
	public void testToString2Reminder() {
		String s = "id: testId, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: WEEKLY, " + "subtasks: [abcd, defg], done: false, ";

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
	public void testToStringNoSubtask() {
		String s = "id: testId, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: WEEKLY, " + "subtasks: [], done: false, ";

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

		assertEquals(e.toString(), s);

		assertTrue(true);
	}

	@Test
	public void testToString1Subtask() {
		String s = "id: testId, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: WEEKLY, " + "subtasks: [abcd], done: false, ";

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

		assertEquals(e.toString(), s);

		assertTrue(true);
	}

	@Test
	public void testToString2Subtask() {
		String s = "id: testId, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: WEEKLY, " + "subtasks: [abcd, defg], done: false, ";

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
	public void testToStringNoRecurrence() {
		String s = "id: testId, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/20-10:33, " + "priority: HIGH, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: null, " + "subtasks: [abcd, defg], done: false, ";

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
		e.setRecurrence(null);
		e.addReminder(reminder);
		e.addReminder(reminder);
		e.addSubtask("abcd");
		e.addSubtask("defg");

		assertEquals(e.toString(), s);

		assertTrue(true);
	}

	@Test
	public void testFromStringWithNoGroupsItem() {
		String c = "id: testId, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/21-11:34, " + "priority: MEDIUM, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: , "
				+ "recurrence: null, " + "subtasks: [abcd, defg], ";

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
		assertEquals(null, e.getGroup());
		assertEquals("[abcd, defg]", Arrays.toString(e.getSubtasks().toArray()));

		assertTrue(true);
	}

	@Test
	public void testFromStringWithGroups1Item() {
		String c = "id: testId, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/21-11:34, " + "priority: MEDIUM, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: [abc], "
				+ "recurrence: null, " + "subtasks: [abcd, defg], ";

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
		assertEquals("[abc]", e.getGroup());
		assertEquals("[abcd, defg]", Arrays.toString(e.getSubtasks().toArray()));

		assertTrue(true);
	}

	@Test
	public void testFromStringNoId() {
		String c = "id: null, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/21-11:34, " + "priority: MEDIUM, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: null, " + "subtasks: [abcd, defg], ";

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
		assertEquals("abc", e.getGroup());
		assertEquals("[abcd, defg]", Arrays.toString(e.getSubtasks().toArray()));

		assertTrue(true);
	}
	
	@Test
	public void testFromStringNoDone() {
		String c = "id: null, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/21-11:34, " + "priority: MEDIUM, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: null, " + "subtasks: [abcd, defg], ";

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
		assertEquals("abc", e.getGroup());
		assertEquals("[abcd, defg]", Arrays.toString(e.getSubtasks().toArray()));
		assertEquals(false, e.isDone());

		assertTrue(true);
	}
	
	@Test
	public void testFromStringNotDone() {
		String c = "id: null, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/21-11:34, " + "priority: MEDIUM, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: null, " + "subtasks: [abcd, defg], done: false, ";

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
		assertEquals("abc", e.getGroup());
		assertEquals("[abcd, defg]", Arrays.toString(e.getSubtasks().toArray()));
		assertEquals(false, e.isDone());

		assertTrue(true);
	}
	
	@Test
	public void testFromStringIsDone() {
		String c = "id: null, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/21-11:34, " + "priority: MEDIUM, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: null, " + "subtasks: [abcd, defg], done: true, ";

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
		assertEquals("abc", e.getGroup());
		assertEquals("[abcd, defg]", Arrays.toString(e.getSubtasks().toArray()));
		assertEquals(true, e.isDone());

		assertTrue(true);
	}
	
	@Test
	public void testFromStringEmptyDone() {
		String c = "id: null, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/21-11:34, " + "priority: MEDIUM, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: null, " + "subtasks: [abcd, defg], done: ,";

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
		assertEquals("abc", e.getGroup());
		assertEquals("[abcd, defg]", Arrays.toString(e.getSubtasks().toArray()));
		assertEquals(false, e.isDone());

		assertTrue(true);
	}

	@Test
	public void testFromStringNoMainId() {
		String c = "id: testId, " + "mainId: null, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/21-11:34, " + "priority: MEDIUM, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: null, " + "subtasks: [abcd, defg], ";

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
		assertEquals("abc", e.getGroup());
		assertEquals("[abcd, defg]", Arrays.toString(e.getSubtasks().toArray()));

		assertTrue(true);
	}

	@Test
	public void testFromStringNoTitle() {
		String c = "id: testId, " + "mainId: testMainId, " + "title: null, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/21-11:34, " + "priority: MEDIUM, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: null, " + "subtasks: [abcd, defg], ";

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
		assertEquals("abc", e.getGroup());
		assertEquals("[abcd, defg]", Arrays.toString(e.getSubtasks().toArray()));

		assertTrue(true);
	}

	@Test
	public void testFromStringNoStartDateTime() {
		String c = "id: testId, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: null, "
				+ "endDateTime: 2015/10/21-11:34, " + "priority: MEDIUM, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: null, " + "subtasks: [abcd, defg], ";

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
		assertEquals("abc", e.getGroup());
		assertEquals("[abcd, defg]", Arrays.toString(e.getSubtasks().toArray()));

		assertTrue(true);
	}

	@Test
	public void testFromStringNoEndDateTime() {
		String c = "id: testId, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: null, " + "priority: MEDIUM, " + "location: test location, " + "notes: test note, "
				+ "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, " + "recurrence: null, "
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
		assertEquals("abc", e.getGroup());
		assertEquals("[abcd, defg]", Arrays.toString(e.getSubtasks().toArray()));

		assertTrue(true);
	}

	@Test
	public void testFromStringNoPriority() {
		String c = "id: testId, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/21-11:34, " + "priority: null, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: null, " + "subtasks: [abcd, defg], ";

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
		assertEquals("abc", e.getGroup());
		assertEquals("[abcd, defg]", Arrays.toString(e.getSubtasks().toArray()));

		assertTrue(true);
	}

	@Test
	public void testFromStringNoLocation() {
		String c = "id: testId, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/21-11:34, " + "priority: MEDIUM, " + "location: null, " + "notes: test note, "
				+ "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, " + "recurrence: null, "
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
		assertEquals("abc", e.getGroup());
		assertEquals("[abcd, defg]", Arrays.toString(e.getSubtasks().toArray()));

		assertTrue(true);
	}

	@Test
	public void testFromStringNoNotes() {
		String c = "id: testId, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/21-11:34, " + "priority: MEDIUM, " + "location: test location, "
				+ "notes: null, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: null, " + "subtasks: [abcd, defg], ";

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
		assertEquals("abc", e.getGroup());
		assertEquals("[abcd, defg]", Arrays.toString(e.getSubtasks().toArray()));

		assertTrue(true);
	}

	@Test
	public void testFromStringNoReminder() {
		String c = "id: testId, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/21-11:34, " + "priority: MEDIUM, " + "location: test location, "
				+ "notes: test note, " + "reminder: [], " + "groups: abc, " + "recurrence: null, "
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
		assertEquals("abc", e.getGroup());
		assertEquals("[abcd, defg]", Arrays.toString(e.getSubtasks().toArray()));

		assertTrue(true);
	}

	@Test
	public void testFromString1Reminder() {
		String c = "id: testId, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/21-11:34, " + "priority: MEDIUM, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: null, " + "subtasks: [abcd, defg], ";

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
		assertEquals("abc", e.getGroup());
		assertEquals("[abcd, defg]", Arrays.toString(e.getSubtasks().toArray()));

		assertTrue(true);
	}

	@Test
	public void testFromString2Reminder() {
		String c = "id: testId, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/21-11:34, " + "priority: MEDIUM, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: MONTHLY, " + "subtasks: [abcd, defg], ";

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
		assertEquals("abc", e.getGroup());
		assertEquals("[abcd, defg]", Arrays.toString(e.getSubtasks().toArray()));

		assertTrue(true);
	}

	@Test
	public void testFromStringNoSubtask() {
		String c = "id: testId, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/21-11:34, " + "priority: MEDIUM, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: null, " + "subtasks: [], ";

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
		assertEquals("abc", e.getGroup());
		assertEquals("[]", Arrays.toString(e.getSubtasks().toArray()));

		assertTrue(true);
	}

	@Test
	public void testFromString1Subtask() {
		String c = "id: testId, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/21-11:34, " + "priority: MEDIUM, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: null, " + "subtasks: [abcd], ";

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
		assertEquals("abc", e.getGroup());
		assertEquals("[abcd]", Arrays.toString(e.getSubtasks().toArray()));

		assertTrue(true);
	}

	@Test
	public void testFromString2Subtask() {
		String c = "id: testId, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/21-11:34, " + "priority: MEDIUM, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: null, " + "subtasks: [abcd, defg], ";

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
		assertEquals("abc", e.getGroup());
		assertEquals("[abcd, defg]", Arrays.toString(e.getSubtasks().toArray()));

		assertTrue(true);
	}

	@Test
	public void testFromStringNoRecurrence() {
		String c = "id: testId, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/21-11:34, " + "priority: MEDIUM, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: null, " + "subtasks: [abcd, defg], ";

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
		assertEquals("abc", e.getGroup());
		assertEquals("[abcd, defg]", Arrays.toString(e.getSubtasks().toArray()));

		assertTrue(true);
	}

	@Test
	public void testToString() {
		String s = "id: testId, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: null, " + "priority: HIGH, " + "location: test location, " + "notes: test note, "
				+ "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, " + "recurrence: WEEKLY, "
				+ "subtasks: [abcd, defg], done: false, ";

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
		e.setRecurrence(Recurrence.WEEKLY);
		e.addReminder(reminder);
		e.addReminder(reminder);
		e.addSubtask("abcd");
		e.addSubtask("defg");

		assertEquals(e.toString(), s);

		assertTrue(true);
	}

	@Test
	public void testFromString() {
		String c = "id: testId, " + "mainId: testMainId, " + "title: testTitle, " + "startDateTime: 2015/10/20-10:33, "
				+ "endDateTime: 2015/10/21-11:34, " + "priority: MEDIUM, " + "location: test location, "
				+ "notes: test note, " + "reminder: [2015/10/21-11:34, 2015/10/21-11:34], " + "groups: abc, "
				+ "recurrence: null, " + "subtasks: [abcd, defg], ";

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
		assertEquals("abc", e.getGroup());
		assertEquals("[abcd, defg]", Arrays.toString(e.getSubtasks().toArray()));

		assertTrue(true);
	}
}
