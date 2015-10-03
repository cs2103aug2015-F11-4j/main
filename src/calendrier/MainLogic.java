package calendrier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import stub.EventHandlerStub;
import stub.ParserStub;
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

	public MainLogic() {
		super();
		parser = new ParserStub();
		eventHandler = new EventHandlerStub();
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
	 * @return List of events (to be shown to user)
	 */
	public List<Event> execute(String command) {
		List<Event> eventList = new ArrayList<>();

		ParsedCommand parsedCommand = parser.parse(command);

		assert(parsedCommand != null);
		if (parsedCommand.getCommand() != null) {
			try {
				eventList = eventHandler.execute(parsedCommand);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			// Throw invalid command exception
		}

		Collections.sort(eventList, new Comparator<Event>(){

			@Override
			public int compare(Event o1, Event o2) {
				// TODO Auto-generated method stub
				return o2.getPriority().compareTo(o1.getPriority());
			}
			
		});
		
		return eventList;
	}

	/**
	 * Notifies user about event starting soon
	 * 
	 * @param event
	 *            event that is starting soon
	 */
	public void notifyUser(Event event) {
	}
}
