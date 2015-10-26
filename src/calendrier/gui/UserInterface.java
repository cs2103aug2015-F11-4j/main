package calendrier.gui;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import utils.Event;
import calendrier.MainLogic;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class UserInterface extends Application {

	private static final String ROOT_LAYOUT_FXML = "/calendrier/resources/RootLayout.fxml";
	private static final String WINDOW_TITLE = "Calendrier";

	private static final String MESSAGE_INVALID_COMMAND = "Invalid command.";
	private static final String MESSAGE_SUCCESSFUL_ADD = "Event is added successfully.";
	private static final String MESSAGE_SUCCESSFUL_UPDATE = "Event has been updated.";
	private static final String MESSAGE_SUCCESSFUL_DELETE = "Event has been deleted.";
	private static final String MESSAGE_SUCCESSFUL_UNDO = "Successful undo operation.";
	private static final String MESSAGE_SUCCESSFUL_UNDELETE = "Successful undelete operation";
	private static final String MESSAGE_FAIL_ADD = "Fail to add event ";
	private static final String MESSAGE_FAIL_UPDATE = "Fail to update event";
	private static final String MESSAGE_FAIL_DELETE = "Fail to delete event";
	private static final String MESSAGE_FAIL_UNDO = "Fail to undo event";
	private static final String MESSAGE_FAIL_UNDELETE = "Fail to undelete event";
	private static final String MESSAGE_FAIL_VIEW_DETAIL = "Invalid Event ID";
	private static final String MESSAGE_WELCOME = "Welcome!";
	private static final String MESSAGE_EMPTY = "";

	private static final int VALUE_START_SCREEN_MIN = 1;
	private static final int VALUE_START_SCREEN_MAX = 3;

	private static final int VALUE_TO_ADD_OR_MINUS = 1;
	private static final int VALUE_ADD_TO_ARRAY = 8;

	private static final int VALUE_START_SCREEN = 1;
	private static final int VALUE_VIEW_SCREEN = 2;

	private static final int VALUE_GET_ALL_EVENTS = 1;
	private static final int VALUE_GET_FILTERED_EVENTS = 2;

	private static final int VALUE_NO_EVENT = 0;

	private static final String PARAM_NAVIGATION_NEXT = "next";
	private static final String PARAM_NAVIGATION_PREVIOUS = "previous";

	private static final boolean PARAM_SET_STORAGE_TRUE = true;
	private static final boolean PARAM_SET_STORAGE_FALSE = false;

	private int startScreenPage = VALUE_START_SCREEN_MIN;
	private int currentScreenState = VALUE_START_SCREEN;
	private int currentEventState = VALUE_GET_ALL_EVENTS;

	private boolean setStorage = PARAM_SET_STORAGE_FALSE;

	private String setMessage = "";

	private int arrStartIndex = 0;
	private int eventSize = 0;
	private List<Event> events;

	private Stage primaryStage;
	private BorderPane rootLayout;

	private MainLogic mainLogic = null;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		initRootLayout();
		initPrimaryStage(primaryStage);

		initLogic();

		// Adding commandbar to RootLayout
		addCommandBar(this);
		addStartScreen(this);
	}

	private void initRootLayout() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(
				ROOT_LAYOUT_FXML));
		try {
			rootLayout = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle(WINDOW_TITLE);
		this.primaryStage.setScene(new Scene(rootLayout));
		this.primaryStage.show();
	}

	private void initLogic() {
		mainLogic = new MainLogic();

		eventSize = mainLogic.getAllEvents().size();
		events = mainLogic.getAllEvents();
	}

	private void addCommandBar(UserInterface userInterface) {
		rootLayout.setBottom(new CommandBarController(userInterface));
	}

	private void addStartScreen(UserInterface userInterface) {

		// trying out notification (for reminder)
		// Notifications.create() .title("Task Reminder") .text("Hello")
		// .showWarning();

		rootLayout.setCenter(new StartScreenController(userInterface,
				VALUE_START_SCREEN_MIN));
	}

	private void addEventView(UserInterface userInterface) {
		currentScreenState = VALUE_VIEW_SCREEN;
		rootLayout.setCenter(new EventDetailController(mainLogic.getEvent()));
	}

	private void addView(UserInterface userInterface) {
		currentScreenState = VALUE_VIEW_SCREEN;

		if (mainLogic.getAllEvents().size() == VALUE_NO_EVENT) {
			rootLayout.setCenter(new NoEventController(userInterface));
		} else {
			// rootLayout.setCenter(new
			// EventAllController(mainLogic.getAllEvents()));
			List<Event> listEvents = null;
			if (currentEventState == VALUE_GET_ALL_EVENTS) {
				listEvents = mainLogic.getAllEvents();
			} else if (currentEventState == VALUE_GET_FILTERED_EVENTS) {
				listEvents = mainLogic.getFilteredEvents();
			}

			rootLayout.setCenter(new ViewController(SortedEvents
					.sortEvents(listEvents), arrStartIndex));
		}
	}

	private void getHelp(UserInterface userInterface) {
		currentScreenState = VALUE_START_SCREEN;
		startScreenPage = VALUE_START_SCREEN_MIN + VALUE_TO_ADD_OR_MINUS;
		rootLayout.setCenter(new StartScreenController(userInterface,
				startScreenPage));
	}

	private void getNextPage(UserInterface userInterface) {
		if (currentScreenState == VALUE_START_SCREEN) {
			// to navigate around the start screen page
			if (isValidScreen(PARAM_NAVIGATION_NEXT)) {
				rootLayout.setCenter(new StartScreenController(userInterface,
						startScreenPage));
			}
		} else {
			if ((arrStartIndex + VALUE_ADD_TO_ARRAY) <= (mainLogic
					.getAllEvents().size() - VALUE_TO_ADD_OR_MINUS)) {
				arrStartIndex += VALUE_ADD_TO_ARRAY;
				addView(userInterface);
			}
		}
	}

	private void getPreviousPage(UserInterface userInterface) {
		if (currentScreenState == VALUE_START_SCREEN) {
			if (isValidScreen(PARAM_NAVIGATION_PREVIOUS)) {
				rootLayout.setCenter(new StartScreenController(userInterface,
						startScreenPage));
			}
		} else {
			if ((arrStartIndex - VALUE_ADD_TO_ARRAY) >= 0) {
				arrStartIndex -= VALUE_ADD_TO_ARRAY;
			} else if ((arrStartIndex - VALUE_ADD_TO_ARRAY) < 0) {
				arrStartIndex = 0;
			}
			addView(userInterface);
		}
	}

	private boolean isValidScreen(String operation) {
		if (operation.equals(PARAM_NAVIGATION_NEXT)) {
			if (startScreenPage + VALUE_TO_ADD_OR_MINUS <= VALUE_START_SCREEN_MAX) {
				startScreenPage += VALUE_TO_ADD_OR_MINUS;
				return true;
			}
		} else {
			if (startScreenPage - VALUE_TO_ADD_OR_MINUS >= VALUE_START_SCREEN_MIN) {
				startScreenPage -= VALUE_TO_ADD_OR_MINUS;
				return true;
			}
		}
		return false;
	}

	public void handleKeyPress(CommandBarController commandBarController,
			KeyCode key, String userInput) {
		if (key == KeyCode.ENTER) {
			handleEnterPress(commandBarController, userInput);
		}
	}

	private void handleEnterPress(CommandBarController commandBarController,
			String userInput) {
		switch (mainLogic.execute(userInput)) {
		case STORAGE_LOCATION:
			setMessage = MESSAGE_WELCOME;
			setStorage = PARAM_SET_STORAGE_TRUE;
			addView(this);
			break;
		case ADD:
			if (setStorage) {
				setMessage = checkAdding();
				currentEventState = VALUE_GET_ALL_EVENTS;
				addView(this);
				break;
			}
		case VIEW_ALL:
			if (setStorage) {
				setMessage = MESSAGE_EMPTY;
				currentEventState = VALUE_GET_ALL_EVENTS;
				addView(this);
				break;
			}
		case VIEW:
			if (setStorage) {
				setMessage = checkEventExist();
				currentEventState = VALUE_GET_ALL_EVENTS;
				break;
			}

		case FILTER:
			if (setStorage) {
				setMessage = MESSAGE_EMPTY;
				currentEventState = VALUE_GET_FILTERED_EVENTS;
				addView(this);
				break;
			}
		case SEARCH:
			// addView(this);
			// either 1 search view or use back view all view
			// addSearchView(this);
			break;
		case UPDATE:
			if (setStorage) {
				setMessage = checkUpdate();
				currentEventState = VALUE_GET_ALL_EVENTS;
				addView(this);
				break;
			}
		case DELETE:
			if (setStorage) {
				setMessage = checkDeleting();
				currentEventState = VALUE_GET_ALL_EVENTS;
				addView(this);
				break;
			}
		case UNDO:
			if (setStorage) {
				setMessage = checkUndo();
				currentEventState = VALUE_GET_ALL_EVENTS;
				addView(this);
				break;
			}
		case UNDELETE:
			if(setStorage) {
				setMessage = checkUndelete();
				currentEventState = VALUE_GET_ALL_EVENTS;
				addView(this);
				break;
			}
		case EXIT:
			System.exit(0);
		case PREVIOUS:
			setMessage = MESSAGE_EMPTY;
			getPreviousPage(this);
			break;
		case NEXT:
			setMessage = MESSAGE_EMPTY;
			getNextPage(this);
			break;
		case HELP:
				setMessage = MESSAGE_EMPTY;
				currentEventState = VALUE_GET_ALL_EVENTS;
				getHelp(this);
				break;
		default:
			commandBarController.setMessage(MESSAGE_INVALID_COMMAND);
		}
		commandBarController.setMessage(setMessage);
		commandBarController.clear();
	}
	
	private String checkUndelete() {
		int currentEventsSize = mainLogic.getAllEvents().size();
		if(currentEventsSize != eventSize) {
			eventSize = currentEventsSize;
			events = mainLogic.getAllEvents();
			return MESSAGE_SUCCESSFUL_UNDELETE;
		} else {
			return MESSAGE_FAIL_UNDELETE;
		}
	}

	private String checkUndo() {
		int currentEventsSize = mainLogic.getAllEvents().size();
		if (currentEventsSize != eventSize) {
			eventSize = currentEventsSize;
			events = mainLogic.getAllEvents();
			return MESSAGE_SUCCESSFUL_UNDO;
		} else {
			if (checkSameEvents(mainLogic.getAllEvents())) {
				return MESSAGE_FAIL_UNDO;
			} else {
				events = mainLogic.getAllEvents();
				eventSize = currentEventsSize;
				return MESSAGE_SUCCESSFUL_UNDO;
			}
		}
	}

	private String checkUpdate() {
		List<Event> currentEvents = mainLogic.getAllEvents();
		if (checkSameEvents(currentEvents)) {
			return MESSAGE_FAIL_UPDATE;
		} else {
			return MESSAGE_SUCCESSFUL_UPDATE;
		}
	}

	private boolean checkSameEvents(List<Event> currentEvents) {
		Set<Event> currentSet = new HashSet<Event>();
		currentSet.addAll(currentEvents);
		Set<Event> eventsSet = new HashSet<Event>();
		eventsSet.addAll(events);
		return currentSet.equals(eventsSet);
	}

	private String checkDeleting() {
		int currentEventsSize = mainLogic.getAllEvents().size();
		if ((eventSize - currentEventsSize) == VALUE_TO_ADD_OR_MINUS) {
			eventSize = currentEventsSize;
			events = mainLogic.getAllEvents();
			return MESSAGE_SUCCESSFUL_DELETE;
		} else {
			return MESSAGE_FAIL_DELETE;
		}
	}

	private String checkAdding() {
		int currentEventsSize = mainLogic.getAllEvents().size();
		if ((currentEventsSize - eventSize) == VALUE_TO_ADD_OR_MINUS) {
			eventSize = currentEventsSize;
			events = mainLogic.getAllEvents();
			return MESSAGE_SUCCESSFUL_ADD;
		} else {
			return MESSAGE_FAIL_ADD;
		}
	}

	private String checkEventExist() {
		if (mainLogic.getEvent().getId() == null) {
			addView(this);
			return MESSAGE_FAIL_VIEW_DETAIL;
		}
		addEventView(this);
		return MESSAGE_EMPTY;
	}
}