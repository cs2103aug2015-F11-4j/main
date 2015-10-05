package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import calendrier.MainLogic;
import stub.EventHandlerStub;
import utils.Command;
import utils.Event;
import utils.Priority;

public class MainLogicNoParserStubTest {

	@Test
	public void executeShouldNotReturnNull() {
		MainLogic mainLogic = new MainLogic();
		mainLogic.injectEventHandler(new EventHandlerStub());

		String command = "add addTitle, " + "startdate 2015/09/23, " + "starttime 10.55, " + "enddate 2015/09/23, "
				+ "endtime 10.56, " + "priority very high, " + "location addLocation, " + "notes addNotes, "
				+ "recurring yes, " + "reminderdate 2015/09/19, " + "remindertime 10.33";

		Command cmd = mainLogic.execute(command);
		assertFalse("should not return null", cmd == null);
	}

	@Test
	public void executeAdd() {
		MainLogic mainLogic = new MainLogic();
		mainLogic.injectEventHandler(new EventHandlerStub());

		addDummyEvents(mainLogic);
	}

	@Test
	public void executeDelete() {
		MainLogic mainLogic = new MainLogic();
		mainLogic.injectEventHandler(new EventHandlerStub());

		String id = addDummyEvents(mainLogic);
		
		assertTrue("should not be null", mainLogic.getAllEvents() != null);
		boolean found = false;
		for(int i = 0; i < mainLogic.getAllEvents().size(); i++){
			if(mainLogic.getAllEvents().get(i).getId().equals(id)){
				found = true;
			}
		}
		assertTrue("can find", found);
		
		String command = "delete " + id;
		Command cmd = mainLogic.execute(command);
		assertTrue("is delete command", cmd == Command.DELETE);
		
		List<Event> currentList = mainLogic.getAllEvents();
		assertTrue("should not be null", currentList != null);
		
		for(int i = 0; i < currentList.size(); i++){
			assertFalse("should not have deleted id", currentList.get(i).getId().equals(id));
		}
		
		assertTrue("should not be null", mainLogic.getEvent() != null);
		assertTrue("should be id", mainLogic.getEvent().getId().equals(id));
	}

	@Test
	public void executeUpdate() {
		MainLogic mainLogic = new MainLogic();
		mainLogic.injectEventHandler(new EventHandlerStub());

		String id = addDummyEvents(mainLogic);

		List<Event> currentList = mainLogic.getAllEvents();
		for(int i = 0; i < currentList.size(); i++){
			if(currentList.get(i).getId().equals(id)){
				assertTrue("before: is very high", currentList.get(i).getPriority() == Priority.VERY_HIGH);
			}
		}
		
		String command = "update id " + id + ", priority low";
		Command cmd = mainLogic.execute(command);
		assertTrue("is update command", cmd == Command.UPDATE);
		
		currentList = mainLogic.getAllEvents();
		assertTrue("should be at least 1", currentList.size() > 0);
		boolean foundId = false;
		for(int i = 0; i < currentList.size(); i++){
			if(currentList.get(i).getId().equals(id)){
				foundId = true;
				assertTrue("should not be null", currentList.get(i).getPriority() != null);
				assertTrue("after: should be low, but get " + currentList.get(i).getPriority().name(), currentList.get(i).getPriority() == Priority.LOW);
			}
		}
		assertTrue("can find id", foundId);
		
		assertTrue("should not be null", mainLogic.getEvent() != null);
		assertTrue("should be low", mainLogic.getEvent().getPriority()== Priority.LOW);
	}

	@Test
	public void executeView() {
		MainLogic mainLogic = new MainLogic();
		mainLogic.injectEventHandler(new EventHandlerStub());

		String id = addDummyEvents(mainLogic);
		
		String command = "view " + id;
		Command cmd = mainLogic.execute(command);
		assertTrue("is view command", cmd == Command.VIEW);
		
		assertTrue("should not be null", mainLogic.getEvent() != null);
		assertTrue("should not be null", mainLogic.getAllEvents() != null);
		assertTrue("should have only 1 event", mainLogic.getAllEvents().size() == 1);
		
		List<Event> currentEvents = mainLogic.getAllEvents();
		assertTrue("is id", currentEvents.get(0).getId().equals(id));
		assertTrue("is id", mainLogic.getEvent().getId().equals(id));
	}

	@Test
	public void executeViewAll() {
		MainLogic mainLogic = new MainLogic();
		mainLogic.injectEventHandler(new EventHandlerStub());

		addDummyEvents(mainLogic);
		
		assertTrue("should not be null", mainLogic.getAllEvents() != null);
		assertTrue("should not be null", mainLogic.getEvent() != null);
		assertTrue("should be at least 1", mainLogic.getAllEvents().size() > 0);
		
		String command = "view all";
		Command cmd = mainLogic.execute(command);
		assertTrue("is view all command", cmd == Command.VIEW_ALL);
		
		assertTrue("should not be null", mainLogic.getEvent() != null);
		assertTrue("should not be null", mainLogic.getAllEvents() != null);
		assertTrue("should have at least 1 event", mainLogic.getAllEvents().size() > 0);
	}

	@Test
	public void getAllEvents() {
		MainLogic mainLogic = new MainLogic();
		mainLogic.injectEventHandler(new EventHandlerStub());

		addDummyEvents(mainLogic);
		
		assertTrue("Should not be null", mainLogic.getAllEvents() != null);
		assertTrue("Should have at least 1 event", mainLogic.getAllEvents().size() > 0);
	}
	
	public String addDummyEvents(MainLogic mainLogic){
		String command = "add addTitle, " + "startdate 2015/09/23, " + "starttime 10.55, " + "enddate 2015/09/23, "
				+ "endtime 10.56, " + "priority very high, " + "location addLocation, " + "notes addNotes, "
				+ "recurring yes, " + "reminderdate 2015/09/19, " + "remindertime 10.33";
		
		Command cmd = mainLogic.execute(command);
		assertTrue("is add command", cmd == Command.ADD);
		
		Event ev = mainLogic.getEvent();
		assertTrue("should have an event", ev != null);
		assertTrue("should have an id", ev.getId() != null);
		String id = ev.getId();
		assertTrue("should have title", ev.getTitle() != null);
		String title = ev.getTitle();
		
		List<Event> eventList = mainLogic.getAllEvents();
		boolean haveAdded = false;
		for (int i = 0; i < eventList.size(); i++) {
			Event event = eventList.get(i);
			if (event.getId().equals(id) && event.getTitle().equals(title)) {
				haveAdded = true;
				break;
			}
		}
		assertTrue("list should have added event", haveAdded);
		
		return id;
	}

}
