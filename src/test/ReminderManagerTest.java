package test;

import static org.junit.Assert.fail;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;

import calendrier.ReminderManager;
import utils.Event;
import utils.OnRemindListener;

public class ReminderManagerTest {
	
	@Test
	public void testAdd() {
		ReminderManager reminderManager = new ReminderManager();
		Event event = generateEvent(0);

		reminderManager.setOnRemindListener(new OnRemindListener() {
			
			@Override
			public void onRemind(Event e) {
				Calendar now = Calendar.getInstance();
				
				assertTrue("", e.getId().equals(event.getId()));
				
			}
		});
		
		reminderManager.addReminder(event);
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
	
	private Event generateEvent(int offset){
		Event event = new Event();
		
		Calendar now = Calendar.getInstance();
		
		event.setId("Test @ " + Double.toString(Math.random()));
		event.setStartDateTime(now);
		event.setReminder(now);
		return event;
	}

}
