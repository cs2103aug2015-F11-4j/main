package test;

import static org.junit.Assert.fail;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

import calendrier.ReminderManager;
import utils.Event;
import utils.OnRemindListener;

public class ReminderManagerTest {
	
	
	@Test
	public void testAdd() {
		ReminderManager reminderManager = new ReminderManager();
		Event laterEvent = generateEvent(120000);
		Event event = generateEvent(0);

		reminderManager.setOnRemindListener(new OnRemindListener() {
			
			@Override
			public void onRemind(Event e) {
				Calendar now = Calendar.getInstance();
				
				assertTrue("", e.getId().equals(event.getId()) || e.getId().equals(laterEvent.getId()));
				
			}
		});
		
		reminderManager.addReminder(laterEvent);
		reminderManager.addReminder(event);
		reminderManager.checkEvents();
		
	}
	
	@Test
	public void testAddWithoutListener() {
		ReminderManager reminderManager = new ReminderManager();
		Event event = generateEvent(0);
		
		reminderManager.addReminder(event);
		reminderManager.checkEvents();
	}
	
	@Test
	public void testAddWithoutReminder(){
		ReminderManager reminderManager = new ReminderManager();
		Event event = generateEvent(0);
		event.removeAllReminders();
		event.addReminder(null);
		
		reminderManager.addReminder(event);
		reminderManager.checkEvents();
	}
	
	@Test
	public void testSetNullListener() {
		Event event = generateEvent(0);
		Event event2 = generateEvent(123);
		ReminderManager reminderManager = new ReminderManager();
		reminderManager.setOnRemindListener(null);

		reminderManager.addReminder(event);
		reminderManager.addReminder(event2);
		reminderManager.removeReminder(event2);
		reminderManager.checkEvents();
	}
	
	@Test
	public void testRemove(){
		ReminderManager reminderManager = new ReminderManager();
		Event event = generateEvent(0);
		Event event2 = generateEvent(123);
		
		
		reminderManager.setOnRemindListener(new OnRemindListener() {
			
			@Override
			public void onRemind(Event e) {
				assertFalse("", e.getId().equals(event2.getId()));
				
			}
		});
		

		reminderManager.addReminder(event);
		reminderManager.addReminder(event2);
		reminderManager.removeReminder(event2);
		reminderManager.checkEvents();
	}
	
	@Test
	public void testRemoveNotExist(){
		ReminderManager reminderManager = new ReminderManager();
		Event event = generateEvent(0);
		Event event2 = generateEvent(123);
		Event event3 = generateEvent(456);
		
		
		reminderManager.setOnRemindListener(new OnRemindListener() {
			
			@Override
			public void onRemind(Event e) {
				assertFalse("", e.getId().equals(event3.getId()));
				
			}
		});
		

		reminderManager.addReminder(event);
		reminderManager.addReminder(event2);
		reminderManager.removeReminder(event3);
		reminderManager.checkEvents();
	}
	

	@Test
	public void testUpdate(){
		ReminderManager reminderManager = new ReminderManager();
		Event event = generateEvent(0);
		event.setTitle("abc");
		Event event2 = generateEvent(0);
		event2.setTitle("def");
		event2.setId(event.getId());

		
		reminderManager.setOnRemindListener(new OnRemindListener() {
			
			@Override
			public void onRemind(Event e) {
				assertTrue("", e.getTitle().equals(event2.getTitle()));
			}
		});

		reminderManager.addReminder(event);
		reminderManager.updateReminder(event2);
		reminderManager.checkEvents();
	}
	

	@Test
	/* Boundary Test: More than a minute */
	public void testCompareTimeMoreThan(){
		ReminderManager reminderManager = new ReminderManager();
		
		Calendar time1 = Calendar.getInstance();
		Calendar time2 = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		
		time1.setTimeInMillis(now.getTimeInMillis());
		time2.setTimeInMillis(now.getTimeInMillis() - 61000);
		
		assertFalse(reminderManager.compareTime(time1, time2));
	}
	
	@Test
	/* Boundary Test: More than a minute */
	public void testCompareTimeReversedMoreThan(){
		ReminderManager reminderManager = new ReminderManager();
		
		Calendar time1 = Calendar.getInstance();
		Calendar time2 = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		
		time1.setTimeInMillis(now.getTimeInMillis());
		time2.setTimeInMillis(now.getTimeInMillis() + 61000);
		
		assertFalse(reminderManager.compareTime(time1, time2));
	}
	
	@Test
	/* Boundary Test: Just More than a minute */
	public void testCompareTimeJustMoreThan(){
		ReminderManager reminderManager = new ReminderManager();
		
		Calendar time1 = Calendar.getInstance();
		Calendar time2 = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		
		time1.setTimeInMillis(now.getTimeInMillis());
		time2.setTimeInMillis(now.getTimeInMillis() - 60001);
		
		assertFalse(reminderManager.compareTime(time1, time2));
	}
	
	@Test
	/* Boundary Test: Just More than a minute */
	public void testCompareTimeReversedJustMoreThan(){
		ReminderManager reminderManager = new ReminderManager();
		
		Calendar time1 = Calendar.getInstance();
		Calendar time2 = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		
		time1.setTimeInMillis(now.getTimeInMillis());
		time2.setTimeInMillis(now.getTimeInMillis() + 60001);
		
		assertFalse(reminderManager.compareTime(time1, time2));
	}
	
	@Test
	/* Boundary Test: Just a little more than a minute */
	public void testCompareTimeExactly(){
		ReminderManager reminderManager = new ReminderManager();
		
		Calendar time1 = Calendar.getInstance();
		Calendar time2 = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		
		time1.setTimeInMillis(now.getTimeInMillis());
		time2.setTimeInMillis(now.getTimeInMillis() - 60000);
		
		assertFalse(reminderManager.compareTime(time1, time2));
	}
	
	@Test
	/* Boundary Test: Just a little more than a minute */
	public void testCompareTimeReversedExactly(){
		ReminderManager reminderManager = new ReminderManager();
		
		Calendar time1 = Calendar.getInstance();
		Calendar time2 = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		
		time1.setTimeInMillis(now.getTimeInMillis());
		time2.setTimeInMillis(now.getTimeInMillis() + 60000);
		
		assertFalse(reminderManager.compareTime(time1, time2));
	}
	
	@Test
	/* Boundary Test: Just a little less than a minute */
	public void testCompareTimeJustLessThan(){
		ReminderManager reminderManager = new ReminderManager();
		
		Calendar time1 = Calendar.getInstance();
		Calendar time2 = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		
		time1.setTimeInMillis(now.getTimeInMillis());
		time2.setTimeInMillis(now.getTimeInMillis() - 59999);
		
		assertTrue(reminderManager.compareTime(time1, time2));
	}
	
	@Test
	/* Boundary Test: Reversed Just a little less than a minute */
	public void testCompareTimeReversedJustLessThan(){
		ReminderManager reminderManager = new ReminderManager();
		
		Calendar time1 = Calendar.getInstance();
		Calendar time2 = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		
		time1.setTimeInMillis(now.getTimeInMillis());
		time2.setTimeInMillis(now.getTimeInMillis() + 59999);
		
		assertTrue(reminderManager.compareTime(time1, time2));
	}
	
	@Test
	/* Boundary Test: Within minute */
	public void testCompareTimeWithin(){
		ReminderManager reminderManager = new ReminderManager();
		
		Calendar time1 = Calendar.getInstance();
		Calendar time2 = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		
		time1.setTimeInMillis(now.getTimeInMillis());
		time2.setTimeInMillis(now.getTimeInMillis() + 30000);
		
		assertTrue(reminderManager.compareTime(time1, time2));
	}
	
	private Event generateEvent(int offset){
		Event event = new Event();
		
		Calendar now = Calendar.getInstance();
		Calendar reminder = Calendar.getInstance();
		reminder.add(Calendar.MILLISECOND, offset);
		
		event.setId("Test @ " + Double.toString(Math.random()));
		event.setStartDateTime(now);
		event.setReminder(reminder);
		return event;
	}

}
