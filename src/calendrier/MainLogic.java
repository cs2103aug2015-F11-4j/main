package calendrier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import stub.EventHandlerStub;
import stub.ParserStub;
import utils.Command;
import utils.Event;
import utils.ParsedCommand;

/**
 * For handling the main logic
 * 
 * @author yeehuipoh
 *
 */
public class MainLogic {
	private Parser parser = null;
	private EventHandler eventHandler = null;
	
	private Event event = null;
	private List<Event> events = null;

	public MainLogic() {
		super();
		parser = new Parser();
		eventHandler = new EventHandler();
	}
	
	public void injectEventHandler(EventHandler eventHandler){
		this.eventHandler = eventHandler;
	}
	
	public void injectParser(Parser parser){
		this.parser = parser;
	}

	/**
	 * Execute command
	 * 
	 * @param command
	 *            Command string input from user
	 * @return command action
	 */
	public Command execute(String command) {
		List<Event> eventList = new ArrayList<>();

		ParsedCommand parsedCommand = parser.parse(command);

		assert(parsedCommand != null);
		if (parsedCommand.getCommand() != null) {
			try {
				eventList = eventHandler.execute(parsedCommand);
				if(eventList.size() > 0){
					event = eventList.get(0);
				}
				else{
					event = null;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			// Throw invalid command exception
		}
		
		return parsedCommand.getCommand();
	}

	/**
	 * Notifies user about event starting soon
	 * 
	 * @param event
	 *            event that is starting soon
	 */
	public void notifyUser(Event event) {
	}
	
	public Event getEvent(){
		return event;
	}
	
	public List<Event> getAllEvents(){
		events = eventHandler.getAllEvents();
		return events;
	}
}
