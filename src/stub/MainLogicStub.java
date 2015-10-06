package stub;

import java.util.ArrayList;
import java.util.List;

import calendrier.MainLogic;
import utils.Command;
import utils.Event;
import utils.Priority;

public class MainLogicStub extends MainLogic {
	List<Event> events = new ArrayList<>();
	Event event = null;

	@Override
	public Command execute(String command) {
		Command cmd = null;
		if (command.equals("add abc")) {
			cmd = Command.ADD;
			event = new Event();
			event.setId("abcId");
			event.setTitle("abc");

			events.add(event);
		} else if (command.equals("update id abcId, priority low")) {
			cmd = Command.UPDATE;
			for (Event e : events) {
				if (e.getId().equals("abcId")) {
					e.setPriority(Priority.LOW);
					event = e;
					break;
				}
			}
		} else if (command.equals("delete abcId")) {
			cmd = Command.DELETE;
			for (Event e : events) {
				if (e.getId().equals("abcId")) {
					events.remove(e);
					event = e;
					break;
				}
			}
		} else if (command.equals("view abcId")) {
			cmd = Command.VIEW;
			for (Event e : events) {
				if (e.getId().equals("abcId")) {
					event = e;
					break;
				}
			}
		} else if (command.equals("undelete")) {
			cmd = Command.UNDELETE;
			events = new ArrayList<>();

			event = new Event();
			event.setId("abcId");
			event.setTitle("abc");

			events.add(event);
		} else if (command.equals("undo")) {
			cmd = Command.UNDO;

			if (events.size() > 0) {
				for (Event e : events) {
					if (e.getId().equals("abcId")) {
						if (e.getPriority() == Priority.LOW) {
							e.setPriority(null);
						} else {
							events.remove(e);
						}
						break;
					}
				}
			} else {
				events = new ArrayList<>();

				event = new Event();
				event.setId("abcId");
				event.setTitle("abc");

				events.add(event);
			}
		} else if (command.equals("save in abcFile.txt")) {
			cmd = Command.STORAGE_LOCATION;
		} else if (command.equals("previous")) {
			cmd = Command.PREVIOUS;
		} else if (command.equals("next")) {
			cmd = Command.NEXT;
		} else if (command.equals("view all")) {
			cmd = Command.VIEW_ALL;
		} else if (command.equals("exit")) {
			cmd = Command.EXIT;
		} else if (command.equals("help")) {
			cmd = Command.HELP;
		}
		return cmd;
	}

	@Override
	public Event getEvent() {
		return event;
	}

	@Override
	public List<Event> getAllEvents() {
		return events;
	}

}
