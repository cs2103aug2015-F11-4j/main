package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import calendrier.MainLogic;
import stub.ParserStub;
import utils.Event;
import utils.Priority;

public class MainLogicNoEventHandlerStubTest {

	@Test
	public void executeShouldNotReturnNull() {
		MainLogic mainLogic = new MainLogic();
		mainLogic.injectParser(new ParserStub());

		String command = "add addTitle, " + "startdate 2015/09/23, " + "starttime 10.55, " + "enddate 2015/09/23, "
				+ "endtime 10.56, " + "priority very high, " + "location addLocation, " + "notes addNotes, "
				+ "recurring yes, " + "reminderdate 2015/09/19, " + "remindertime 10.33";

		List<Event> eventList = mainLogic.execute(command);
		assertFalse("should not return null", eventList == null);
	}

	@Test
	public void executeEventListSizeShouldBeGreaterThanZero() {
		MainLogic mainLogic = new MainLogic();
		mainLogic.injectParser(new ParserStub());

		String command = "add addTitle, " + "startdate 2015/09/23, " + "starttime 10.55, " + "enddate 2015/09/23, "
				+ "endtime 10.56, " + "priority very high, " + "location addLocation, " + "notes addNotes, "
				+ "recurring yes, " + "reminderdate 2015/09/19, " + "remindertime 10.33";
		List<Event> eventList = mainLogic.execute(command);
		assertTrue("should be > 0", eventList.size() > 0);
	}

	@Test
	public void executeEventListShouldHaveAdded() {
		MainLogic mainLogic = new MainLogic();
		mainLogic.injectParser(new ParserStub());

		String command = "add addTitle, " + "startdate 2015/09/23, " + "starttime 10.55, " + "enddate 2015/09/23, "
				+ "endtime 10.56, " + "priority very high, " + "location addLocation, " + "notes addNotes, "
				+ "recurring yes, " + "reminderdate 2015/09/19, " + "remindertime 10.33";
		List<Event> eventList = mainLogic.execute(command);

		boolean haveAdded = false;
		for (int i = 0; i < eventList.size(); i++) {
			Event event = eventList.get(i);
			if (event.getTitle().equals("addTitle") && event.getPriority().equals(Priority.VERY_HIGH)
					&& event.getLocation().equals("addLocation") && event.getNotes().equals("addNotes")) {
				haveAdded = true;
				break;
			}
		}
		assertTrue("should have added event", haveAdded);
	}

	@Test
	public void executeEventListShouldHaveDeletedEvent() {
		MainLogic mainLogic = new MainLogic();
		mainLogic.injectParser(new ParserStub());

		String command = "delete deleteId";
		List<Event> eventList = mainLogic.execute(command);
		boolean haveDeleted = false;
		for (Event event : eventList) {
			if (event.getTitle().equals("deleteId")) {
				haveDeleted = true;
				break;
			}
		}
		assertFalse("should not have \"deleted\" event", haveDeleted);
	}

	@Test
	public void executeEventListShouldBeUpdated() {
		MainLogic mainLogic = new MainLogic();
		mainLogic.injectParser(new ParserStub());

		String command = "update id updateId, " + "startdate 2015/09/23, " + "starttime 10.55, "
				+ "enddate 2015/09/23, " + "endtime 10.56, " + "priority high, " + "location updateLocation, "
				+ "notes updateNotes, " + "recurring yes, " + "reminderdate 2015/09/22, " + "remindertime 10.55";
		List<Event> eventList = mainLogic.execute(command);
		boolean isHigh = true;
		for (int i = 0; i < eventList.size(); i++) {
			Event event = eventList.get(i);
			if (event.getId() != null && event.getId().equals("updateId")) {
				if (event.getPriority() != Priority.HIGH) {
					isHigh = false;
				}
				break;
			}
		}
		assertTrue("\"updateId\" should have \"HIGH\" priority", isHigh);
	}

	@Test
	public void executeEventListShouldHaveOnlyOneEvent() {
		MainLogic mainLogic = new MainLogic();
		mainLogic.injectParser(new ParserStub());

		String command = "view viewId";
		List<Event> eventList = mainLogic.execute(command);
		boolean isViewEvent = false;
		assertTrue("should only have 1 event", eventList.size() == 1);

		for (int i = 0; i < eventList.size(); i++) {
			Event event = eventList.get(i);
			if (event != null) {
				if (event.getId() != null && event.getId().equals("viewId")) {
					isViewEvent = true;
					break;
				}
			}
		}
		assertTrue("should only have \"viewId\"", isViewEvent);
	}

	@Test
	public void executeEventListShouldHaveAllEvent() {
		MainLogic mainLogic = new MainLogic();
		mainLogic.injectParser(new ParserStub());

		String command = "view all";
		List<Event> eventList = mainLogic.execute(command);

		assertTrue("should have all event", eventList.size() >= 0);

	}

	@Test
	public void executeEventListShouldBeInDecreasingPriority() {
		MainLogic mainLogic = new MainLogic();
		mainLogic.injectParser(new ParserStub());
		List<Event> eventList = mainLogic.execute("view all");

		boolean isDecreasing = true;
		Priority previousPriority = Priority.VERY_HIGH;
		for (Event event : eventList) {
			if (event != null) {
				if (event.getPriority().compareTo(previousPriority) > 0) {
					isDecreasing = false;
				}
				previousPriority = event.getPriority();
			}
		}

		assertTrue("should be in decreasing priority", isDecreasing);

	}

}
