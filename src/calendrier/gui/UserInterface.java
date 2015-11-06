package calendrier.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import utils.Event;
import utils.Notification;
import utils.OnRemindListener;
import calendrier.MainLogic;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class UserInterface extends Application implements OnRemindListener {

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
	private static final String MESSAGE_FAIL_VIEW_HOME_NEXT = "Fail to get next day event";
	private static final String MESSAGE_FAIL_VIEW_HOME_PREVIOUS = "Fail to get previous day event";
	private static final String MESSAGE_FAIL_VIEW_MONTH_NEXT = "Fail to get next month event";
	private static final String MESSAGE_FAIL_VIEW_MONTH_PREVIOUS = "Fail to get previous month event";
	private static final String MESSAGE_WELCOME = "Welcome!";
	private static final String MESSAGE_EMPTY = "";
	private static final String MESSAGE_REMINDER = "Reminder";

	private static final int VALUE_START_SCREEN_MIN = 1;
	private static final int VALUE_START_SCREEN_MAX = 5;

	private static final int VALUE_TO_ADD_OR_MINUS = 1;
	private static final int VALUE_ADD_TO_ARRAY = 15;
	private static final int VALUE_ADD_TO_DAY_ARRAY = 5;

	private static final int VALUE_START_SCREEN = 1;
	private static final int VALUE_VIEW_SCREEN = 2;
	private static final int VALUE_VIEW_MONTH_SCREEN = 3;
	private static final int VALUE_VIEW_HOME_SCREEN = 4;
	private static final int VALUE_VIEW_DETAIL_SCREEN = 5;
	private static final int VALUE_VIEW_DAY_SCREEN = 6;

	private static final int VALUE_GET_ALL_EVENTS = 1;
	private static final int VALUE_GET_FILTERED_EVENTS = 2;

	private static final int VALUE_RESET = 0;
	private static final int VALUE_NO_EVENT = 0;

	private static final String PARAM_NAVIGATION_NEXT = "next";
	private static final String PARAM_NAVIGATION_PREVIOUS = "previous";

	private static final boolean PARAM_GET_FLOATING_TASK_TRUE = true;
	private static final boolean PARAM_SET_STORAGE_TRUE = true;
	private static final boolean PARAM_SET_STORAGE_FALSE = false;
	private static final boolean PARAM_SET_AT_DETAIL_VIEW_FALSE = false;
	private static final boolean PARAM_SET_AT_DETAIL_VIEW_TRUE = true;

	private int startScreenPage = VALUE_START_SCREEN_MIN;
	private int currentScreenState = VALUE_START_SCREEN;
	private int currentEventState = VALUE_GET_ALL_EVENTS;

	private boolean setStorage = PARAM_SET_STORAGE_FALSE;
	private boolean atDetailView = PARAM_SET_AT_DETAIL_VIEW_FALSE;

	private String setMessage = "";

	private int dayArrStartIndex = 0;
	private int floatingArrStartIndex = 0;
	private int arrStartIndex = 0;
	private int eventSize = 0;
	private List<Event> events;
	final Timer timer = new Timer();
	private Stage primaryStage;
	private BorderPane rootLayout;

	private MainLogic mainLogic = null;
	private Notification.Notifier notifier;
	private Calendar cal = Calendar.getInstance();
	@SuppressWarnings("deprecation")
	private int date = cal.getTime().getDate(), month = cal.getTime().getMonth(), year = cal.getTime().getYear() + 1900;
	private int currentMonth, currentYear;
	private int viewDate, viewMonth, viewYear;

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
		FXMLLoader loader = new FXMLLoader(getClass().getResource(ROOT_LAYOUT_FXML));
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
		mainLogic.setOnRemindListener(this);

		eventSize = mainLogic.getAllEvents().size();
		events = mainLogic.getAllEvents();
		currentMonth = month;
		currentYear = year;
		resetViewDateInfo();
	}

	private void addCommandBar(UserInterface userInterface) {
		rootLayout.setBottom(new CommandBarController(userInterface));
	}

	private void addStartScreen(UserInterface userInterface) {
		rootLayout.setCenter(new StartScreenController(userInterface, VALUE_START_SCREEN_MIN));
	}

	private void addEventView(UserInterface userInterface) {
		// currentScreenState = VALUE_VIEW_SCREEN;
		List<Event> subEvents = new ArrayList<Event>();
		if (mainLogic.getEvent().getSubtasks().size() != 0) {
			for (int i = 0; i < mainLogic.getEvent().getSubtasks().size(); i++) {
				subEvents.add(mainLogic.getEvent(mainLogic.getEvent().getSubtasks().get(i)));
			}
		}
		currentScreenState = VALUE_VIEW_DETAIL_SCREEN;
		rootLayout.setCenter(new EventDetailController(mainLogic.getEvent(), subEvents));
	}

	private void addView(UserInterface userInterface) {
		currentScreenState = VALUE_VIEW_SCREEN;

		if (mainLogic.getAllEvents().size() == VALUE_NO_EVENT) {
			rootLayout.setCenter(new NoEventController(userInterface));
		} else {
			List<Event> listEvents = null;
			if (currentEventState == VALUE_GET_ALL_EVENTS) {
				listEvents = mainLogic.getAllEvents();
			} else if (currentEventState == VALUE_GET_FILTERED_EVENTS) {
				listEvents = mainLogic.getFilteredEvents();
			}

			rootLayout.setCenter(new ViewController(SortedEvents.sortEvents(listEvents), arrStartIndex));
		}
	}

	/**
	 * @@author A0126421U
	 * generate home view
	 * 
	 * @param userInterface - the current userInterface
	 * @param timeToNextEvent - time left for next event
	 * 
	 * 
	 */
	@SuppressWarnings("deprecation")
	private void viewHome(UserInterface userInterface, long timeToNextEvent) {
		Calendar cal = Calendar.getInstance();
		List<Event> currentTask = new ArrayList<Event>();
		List<Event> nextTask = new ArrayList<Event>();
		String name1, name2;
		currentTask = mainLogic.getDayEvents(cal.getTime().getYear() + 1900, cal.getTime().getMonth() + 1,
				cal.getTime().getDay() + 1);
		cal.add(Calendar.DATE, 1);
		nextTask = mainLogic.getDayEvents(cal.getTime().getYear() + 1900, cal.getTime().getMonth() + 1,
				cal.getTime().getDay() + 1);
		while (nextTask.size() == 0) {
			cal.add(Calendar.DATE, 1);
			nextTask = mainLogic.getDayEvents(cal.getTime().getYear() + 1900, cal.getTime().getMonth() + 1,
					cal.getTime().getDay() + 1);
		}
		currentScreenState = VALUE_VIEW_DAY_SCREEN;
		if (currentTask.size() != 0) {
			name1 = currentTask.get(0).getTitle();
		} else {
			name1 = null;
		}
		name2 = nextTask.get(0).getTitle();
		rootLayout.setCenter(new ViewController(timeToNextEvent, name1, name2,
				getNumOfFloatEvents(mainLogic.getAllEvents()), getNumOfOnGoingEvents(mainLogic.getAllEvents()),
				getNumOfPassedEvents(mainLogic.getAllEvents())));

		startCountDown(name1, name2);
	}
	
	/**
	 * @@author A0126421U
	 * Start count down for the next event 
	 * 
	 * @param name1 - the title for current event
	 * @param name2 - the title for next event
	 * 
	 * 
	 */
	private void startCountDown(String name1, String name2) {
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					public void run() {
						rootLayout.setCenter(new ViewController(mainLogic.getTimeToNextEvent(), name1, name2,
								getNumOfFloatEvents(mainLogic.getAllEvents()),
								getNumOfOnGoingEvents(mainLogic.getAllEvents()),
								getNumOfPassedEvents(mainLogic.getAllEvents())));
					}
				});
			}
		}, 1, 1000);
	}
	
	/**
	 * @@author A0126421U
	 * generate month view
	 * 
	 *  @param userInterface - the current userInterface
	 *  @param month - the month to be display
	 *  @param year - year to be display
	 * 
	 */
	private void viewMonth(UserInterface userInterface, int month, int year) {
		currentScreenState = VALUE_VIEW_MONTH_SCREEN;
		rootLayout.setCenter(new ViewController(mainLogic.getMonthEvents(currentYear, currentMonth + 1), date,
				currentMonth, currentYear));
	}

	private void viewDay(UserInterface userInterface, int date, int month, int year, int day, boolean isToday) {
		currentScreenState = VALUE_VIEW_HOME_SCREEN;
		List<Event> floatingEvents = getFloatingEvents(mainLogic.getDayEvents(year, month + 1, date),
				mainLogic.getDayEvents(year, month + 1, date, PARAM_GET_FLOATING_TASK_TRUE));
		rootLayout.setCenter(new ViewDayController(mainLogic.getDayEvents(year, month + 1, date), floatingEvents, date,
				month, year, day, isToday, checkPassedDay(), dayArrStartIndex, floatingArrStartIndex));
	}

	private List<Event> getFloatingEvents(List<Event> datedEvents, List<Event> floatingEvents) {
		for (Event e : datedEvents) {
			if (floatingEvents.contains(e)) {
				floatingEvents.remove(e);
			}
		}
		return floatingEvents;
	}

	private void getHelp(UserInterface userInterface) {
		currentScreenState = VALUE_START_SCREEN;
		startScreenPage = VALUE_START_SCREEN_MIN + VALUE_TO_ADD_OR_MINUS;
		rootLayout.setCenter(new StartScreenController(userInterface, startScreenPage));
	}

	private String getNextDay(UserInterface userInterface) {
		if (currentScreenState == VALUE_VIEW_HOME_SCREEN) {
			dayArrStartIndex = VALUE_RESET;
			floatingArrStartIndex = VALUE_RESET;

			Calendar newCal = Calendar.getInstance();
			newCal.set(Calendar.YEAR, viewYear);
			newCal.set(Calendar.MONTH, viewMonth);
			int lastDayOfMonth = newCal.getActualMaximum(Calendar.DAY_OF_MONTH);
			viewDate += VALUE_TO_ADD_OR_MINUS;
			if (viewDate > lastDayOfMonth) {
				viewDate = VALUE_TO_ADD_OR_MINUS;
				viewMonth += VALUE_TO_ADD_OR_MINUS;
				if (viewMonth > 11) {
					viewMonth = 0;
					viewYear += VALUE_TO_ADD_OR_MINUS;
				}
			}
			viewDay(this, viewDate, viewMonth, viewYear, getDay(viewDate, viewMonth, viewYear),
					boolIsToday(viewDate, viewMonth, viewYear));
			return MESSAGE_EMPTY;
		} else {
			return MESSAGE_FAIL_VIEW_HOME_NEXT;
		}
	}

	private String getNextMonth(UserInterface userInterface) {
		if (currentScreenState == VALUE_VIEW_MONTH_SCREEN) {
			currentMonth += 1;
			if (currentMonth > 11) {
				currentMonth = 0;
				currentYear++;
			}
			viewMonth(this, currentMonth, currentYear);
			return MESSAGE_EMPTY;
		} else {
			return MESSAGE_FAIL_VIEW_MONTH_NEXT;
		}
	}

	private void getNextPage(UserInterface userInterface) {
		if (currentScreenState == VALUE_VIEW_HOME_SCREEN) {

			List<Event> floatingEvents = getFloatingEvents(mainLogic.getDayEvents(viewYear, viewMonth + 1, viewDate),
					mainLogic.getDayEvents(viewYear, viewMonth + 1, viewDate, PARAM_GET_FLOATING_TASK_TRUE));
			if ((dayArrStartIndex
					+ VALUE_ADD_TO_DAY_ARRAY) <= (mainLogic.getDayEvents(viewYear, viewMonth + 1, viewDate).size()
							- VALUE_TO_ADD_OR_MINUS)) {
				dayArrStartIndex += VALUE_ADD_TO_DAY_ARRAY;
			}

			if ((floatingArrStartIndex + VALUE_ADD_TO_DAY_ARRAY) <= (floatingEvents.size() - VALUE_TO_ADD_OR_MINUS)) {
				floatingArrStartIndex += VALUE_ADD_TO_DAY_ARRAY;
			}

			viewDay(this, viewDate, viewMonth, viewYear, getDay(viewDate, viewMonth, viewYear),
					boolIsToday(viewDate, viewMonth, viewYear));

		} else if (currentScreenState == VALUE_START_SCREEN) {
			// to navigate around the start screen page
			if (isValidScreen(PARAM_NAVIGATION_NEXT)) {
				rootLayout.setCenter(new StartScreenController(userInterface, startScreenPage));
			}
		} else if (currentScreenState != VALUE_VIEW_MONTH_SCREEN && currentScreenState != VALUE_VIEW_DAY_SCREEN){
			if ((arrStartIndex + VALUE_ADD_TO_ARRAY) <= (mainLogic.getAllEvents().size() - VALUE_TO_ADD_OR_MINUS)) {
				arrStartIndex += VALUE_ADD_TO_ARRAY;
				addView(userInterface);
			}
		}
	}

	private String getPreviousDay(UserInterface userInterface) {
		if (currentScreenState == VALUE_VIEW_HOME_SCREEN) {
			dayArrStartIndex = VALUE_RESET;
			floatingArrStartIndex = VALUE_RESET;
			viewDate -= VALUE_TO_ADD_OR_MINUS;
			if (viewDate < 1) {
				Calendar newCal = Calendar.getInstance();
				viewMonth -= VALUE_TO_ADD_OR_MINUS;
				if (viewMonth < 0) {
					viewMonth = 11;
					viewYear -= VALUE_TO_ADD_OR_MINUS;
				}
				newCal.set(Calendar.YEAR, viewYear);
				newCal.set(Calendar.MONTH, viewMonth);
				viewDate = newCal.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
			viewDay(this, viewDate, viewMonth, viewYear, getDay(viewDate, viewMonth, viewYear),
					boolIsToday(viewDate, viewMonth, viewYear));
			return MESSAGE_EMPTY;
		} else {
			return MESSAGE_FAIL_VIEW_HOME_PREVIOUS;
		}
	}

	private String getPreviousMonth(UserInterface userInterface) {
		if (currentScreenState == VALUE_VIEW_MONTH_SCREEN) {
			currentMonth -= 1;
			if (currentMonth < 0) {
				currentMonth = 11;
				currentYear--;
			}
			viewMonth(this, currentMonth, currentYear);
			return MESSAGE_EMPTY;
		} else {
			return MESSAGE_FAIL_VIEW_MONTH_PREVIOUS;
		}
	}

	private void getPreviousPage(UserInterface userInterface) {
		if (currentScreenState == VALUE_VIEW_HOME_SCREEN) {

			if ((dayArrStartIndex - VALUE_ADD_TO_DAY_ARRAY) >= 0) {
				dayArrStartIndex -= VALUE_ADD_TO_DAY_ARRAY;
			} else if ((dayArrStartIndex - VALUE_ADD_TO_DAY_ARRAY) < 0) {
				dayArrStartIndex = 0;
			}

			if ((floatingArrStartIndex - VALUE_ADD_TO_DAY_ARRAY) >= 0) {
				floatingArrStartIndex -= VALUE_ADD_TO_DAY_ARRAY;
			} else if ((floatingArrStartIndex - VALUE_ADD_TO_DAY_ARRAY) < 0) {
				floatingArrStartIndex = 0;
			}

			viewDay(this, viewDate, viewMonth, viewYear, getDay(viewDate, viewMonth, viewYear),
					boolIsToday(viewDate, viewMonth, viewYear));

		} else if (currentScreenState == VALUE_START_SCREEN) {
			if (isValidScreen(PARAM_NAVIGATION_PREVIOUS)) {
				rootLayout.setCenter(new StartScreenController(userInterface, startScreenPage));
			}
		} else if (currentScreenState != VALUE_VIEW_MONTH_SCREEN && currentScreenState != VALUE_VIEW_DAY_SCREEN){
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

	public void handleKeyPress(CommandBarController commandBarController, KeyCode key, String userInput) {
		if (key == KeyCode.ENTER) {
			handleEnterPress(commandBarController, userInput);
		}

		if (key == KeyCode.LEFT && userInput.length() == 0) {
			getPreviousPage(this);
		}

		if (key == KeyCode.RIGHT && userInput.length() == 0) {
			getNextPage(this);
		}

		if (key == KeyCode.UP && userInput.length() == 0) {
			if (currentScreenState == VALUE_VIEW_HOME_SCREEN) {
				setMessage = getPreviousDay(this);
				commandBarController.setMessage(setMessage);
				commandBarController.clear();
			} else if (currentScreenState == VALUE_VIEW_MONTH_SCREEN) {
				setMessage = getPreviousMonth(this);
				commandBarController.setMessage(setMessage);
				commandBarController.clear();
			}
		}
		if (key == KeyCode.DOWN && userInput.length() == 0) {
			if (currentScreenState == VALUE_VIEW_HOME_SCREEN) {
				setMessage = getNextDay(this);
				commandBarController.setMessage(setMessage);
				commandBarController.clear();
			} else if (currentScreenState == VALUE_VIEW_MONTH_SCREEN) {
				setMessage = getNextMonth(this);
				commandBarController.setMessage(setMessage);
				commandBarController.clear();
			}
		}
	}

	private void handleEnterPress(CommandBarController commandBarController, String userInput) {
		switch (mainLogic.execute(userInput)) {
		case STORAGE_LOCATION:
			setMessage = MESSAGE_WELCOME;
			setStorage = PARAM_SET_STORAGE_TRUE;
			atDetailView = PARAM_SET_AT_DETAIL_VIEW_FALSE;
			eventSize = mainLogic.getAllEvents().size();
			if (eventSize == 0) {
				addView(this);
			} else {
				viewHome(this, mainLogic.getTimeToNextEvent());
				// viewDay(this, date, month, year, getDay(date, month, year),
				// boolIsToday(date, month, year));
			}
			break;
		case VIEW_HOME:
			if (setStorage) {
				timer.cancel();
				setMessage = MESSAGE_EMPTY;
				currentEventState = VALUE_GET_ALL_EVENTS;
				atDetailView = PARAM_SET_AT_DETAIL_VIEW_FALSE;
				viewHome(this, mainLogic.getTimeToNextEvent());
				break;
			}
		case ADD:
			if (setStorage) {
				timer.cancel();
				setMessage = checkAdding();
				currentEventState = VALUE_GET_ALL_EVENTS;
				atDetailView = PARAM_SET_AT_DETAIL_VIEW_FALSE;
				if (currentScreenState == VALUE_VIEW_SCREEN) {
					addView(this);
					if (eventSize > VALUE_ADD_TO_ARRAY) {
						getNextPage(this);
					}
				} else if (currentScreenState == VALUE_VIEW_HOME_SCREEN) {
					resetViewDateInfo();
					viewDay(this, date, month, year, getDay(date, month, year), boolIsToday(date, month, year));
				} else {
					viewMonth(this, currentMonth, currentYear);
				}
				break;
			}
		case VIEW_ALL:
			if (setStorage) {
				timer.cancel();
				setMessage = MESSAGE_EMPTY;
				currentEventState = VALUE_GET_ALL_EVENTS;
				atDetailView = PARAM_SET_AT_DETAIL_VIEW_FALSE;
				addView(this);
				break;
			}
		case VIEW:
			if (setStorage) {
				timer.cancel();
				setMessage = checkEventExist();
				atDetailView = PARAM_SET_AT_DETAIL_VIEW_TRUE;
				currentEventState = VALUE_GET_ALL_EVENTS;
				break;
			}

		case FILTER:
			if (setStorage) {
				timer.cancel();
				setMessage = MESSAGE_EMPTY;
				atDetailView = PARAM_SET_AT_DETAIL_VIEW_FALSE;
				currentEventState = VALUE_GET_FILTERED_EVENTS;
				addView(this);
				break;
			}
		case UPDATE:
			if (setStorage) {
				timer.cancel();
				setMessage = checkUpdate();
				currentEventState = VALUE_GET_ALL_EVENTS;

				if (atDetailView != PARAM_SET_AT_DETAIL_VIEW_TRUE) {

					if (currentScreenState == VALUE_VIEW_SCREEN) {
						addView(this);
					} else if (currentScreenState == VALUE_VIEW_DETAIL_SCREEN) {
						addEventView(this);
					} else if (currentScreenState == VALUE_VIEW_HOME_SCREEN) {
						resetViewDateInfo();
						viewDay(this, date, month, year, getDay(date, month, year), boolIsToday(date, month, year));
					} else {
						viewMonth(this, currentMonth, currentYear);
					}
				} else {
					checkEventExist();
					atDetailView = PARAM_SET_AT_DETAIL_VIEW_TRUE;
					currentEventState = VALUE_GET_ALL_EVENTS;
				}
				break;
			}
		case DELETE:
			if (setStorage) {
				timer.cancel();
				setMessage = checkDeleting();
				atDetailView = PARAM_SET_AT_DETAIL_VIEW_FALSE;
				currentEventState = VALUE_GET_ALL_EVENTS;
				if (currentScreenState == VALUE_VIEW_SCREEN) {
					addView(this);
					if (eventSize <= VALUE_ADD_TO_ARRAY) {
						getPreviousPage(this);
					}
				} else if (currentScreenState == VALUE_VIEW_HOME_SCREEN) {
					resetViewDateInfo();
					viewDay(this, date, month, year, getDay(date, month, year), boolIsToday(date, month, year));
				} else {
					viewMonth(this, currentMonth, currentYear);
				}
				break;
			}
		case UNDO:
			if (setStorage) {
				timer.cancel();
				setMessage = checkUndo();
				atDetailView = PARAM_SET_AT_DETAIL_VIEW_FALSE;
				currentEventState = VALUE_GET_ALL_EVENTS;
				if (currentScreenState == VALUE_VIEW_SCREEN) {
					addView(this);
				} else if (currentScreenState == VALUE_VIEW_HOME_SCREEN) {
					resetViewDateInfo();
					viewDay(this, date, month, year, getDay(date, month, year), boolIsToday(date, month, year));
				} else {
					viewMonth(this, currentMonth, currentYear);
				}
				break;
			}
		case UNDELETE:
			if (setStorage) {
				timer.cancel();
				setMessage = checkUndelete();
				atDetailView = PARAM_SET_AT_DETAIL_VIEW_FALSE;
				currentEventState = VALUE_GET_ALL_EVENTS;
				if (currentScreenState == VALUE_VIEW_SCREEN) {
					addView(this);
				} else if (currentScreenState == VALUE_VIEW_HOME_SCREEN) {
					resetViewDateInfo();
					viewDay(this, date, month, year, getDay(date, month, year), boolIsToday(date, month, year));
				} else {
					viewMonth(this, currentMonth, currentYear);
				}
				break;
			}
		case EXIT:
			System.exit(0);
		case PREVIOUS:
			timer.cancel();
			setMessage = MESSAGE_EMPTY;
			getPreviousPage(this);
			break;
		case NEXT:
			timer.cancel();
			setMessage = MESSAGE_EMPTY;
			getNextPage(this);
			break;
		case HELP:
			timer.cancel();
			setMessage = MESSAGE_EMPTY;
			currentEventState = VALUE_GET_ALL_EVENTS;
			getHelp(this);
			break;
		case VIEW_MONTH:
			if (setStorage) {
				timer.cancel();
				setMessage = MESSAGE_EMPTY;
				atDetailView = PARAM_SET_AT_DETAIL_VIEW_FALSE;
				currentEventState = VALUE_GET_ALL_EVENTS;
				viewMonth(this, month, year);
				break;
			}
		case VIEW_DAY:
			if (setStorage) {
				timer.cancel();
				setMessage = MESSAGE_EMPTY;
				currentEventState = VALUE_GET_ALL_EVENTS;
				atDetailView = PARAM_SET_AT_DETAIL_VIEW_FALSE;
				resetViewDateInfo();
				viewDay(this, date, month, year, getDay(date, month, year), boolIsToday(date, month, year));
				break;
			}
		case NEXT_DAY:
			if (setStorage) {
				timer.cancel();
				currentEventState = VALUE_GET_ALL_EVENTS;
				setMessage = getNextDay(this);
			}
		case PREVIOUS_DAY:
			if (setStorage) {
				timer.cancel();
				currentEventState = VALUE_GET_ALL_EVENTS;
				setMessage = getPreviousDay(this);
			}
		case NEXT_MONTH:
			if (setStorage) {
				timer.cancel();
				currentEventState = VALUE_GET_ALL_EVENTS;
				setMessage = getNextMonth(this);
			}
		case PREVIOUS_MONTH:
			if (setStorage) {
				timer.cancel();
				currentEventState = VALUE_GET_ALL_EVENTS;
				setMessage = getPreviousMonth(this);
			}
		default:
			timer.cancel();
			setMessage = MESSAGE_INVALID_COMMAND;
		}
		commandBarController.setMessage(setMessage);
		commandBarController.clear();

	}

	private boolean checkPassedDay() {
		if (viewDate < date && viewMonth <= month && viewYear <= year) {
			return true;
		}
		return false;
	}

	private void resetViewDateInfo() {
		dayArrStartIndex = VALUE_RESET;
		floatingArrStartIndex = VALUE_RESET;
		viewDate = date;
		viewMonth = month;
		viewYear = year;
	}

	@SuppressWarnings("deprecation")
	private int getDay(int intDate, int intMonth, int intYear) {
		Calendar thisCal = Calendar.getInstance();
		thisCal.set(intYear, intMonth, intDate);
		return thisCal.getTime().getDay();
	}

	/**
	 * @@author A0126421U
	 * get number of events that are passed
	 * 
	 * @param events - whole list of event
	 * 
	 * @return numOfPassedEvent - total number of events that are passed
	 * 
	 */
	private int getNumOfPassedEvents(List<Event> events) {
		int num = 0;
		Calendar thisCal = Calendar.getInstance();
		for (int i = 0; i < events.size(); i++) {
			if (events.get(i).getEndDateTime() != null) {
				if (events.get(i).getEndDateTime().compareTo(thisCal) < 0) {
					num++;
				}
			}
		}
		return num;
	}

	/**
	 * @@author A0126421U
	 * get number of events that are still active
	 * 
	 * @param events - whole list of event
	 * 
	 * @return numOfActiveEvent - total number of events that are still active
	 * 
	 */
	private int getNumOfOnGoingEvents(List<Event> events) {
		int num = 0;
		Calendar thisCal = Calendar.getInstance();
		for (int i = 0; i < events.size(); i++) {
			if (events.get(i).getStartDateTime() != null && events.get(i).getEndDateTime() != null) {
				if (events.get(i).getEndDateTime().compareTo(thisCal) > 0) {
					num++;
				}
			} else if (events.get(i).getStartDateTime() != null) {
				num++;
			}
		}
		return num;
	}

	/**
	 * @@author A0126421U
	 * get number of events that does not have deadline
	 * 
	 * @param events - whole list of event
	 * 
	 * @return numOfPassedEvent - total number of events that does not have deadline
	 * 
	 */
	private int getNumOfFloatEvents(List<Event> events) {
		int num = 0;
		for (int i = 0; i < events.size(); i++) {
			if (events.get(i).getStartDateTime() == null && events.get(i).getEndDateTime() == null) {
				num++;
			}
		}
		return num;
	}

	private boolean boolIsToday(int intDate, int intMonth, int intYear) {
		if (intDate == date && intMonth == month && intYear == year) {
			return true;
		}
		return false;
	}

	private String checkUndelete() {
		int currentEventsSize = mainLogic.getAllEvents().size();
		if (currentEventsSize != eventSize) {
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
			if (currentScreenState == VALUE_VIEW_SCREEN) {
				addView(this);
			} else if (currentScreenState == VALUE_VIEW_HOME_SCREEN) {
				viewDay(this, date, month, year, getDay(date, month, year), boolIsToday(date, month, year));
			} else if (currentScreenState == VALUE_VIEW_MONTH_SCREEN) {
				viewMonth(this, currentMonth, currentYear);
			}
			return MESSAGE_FAIL_VIEW_DETAIL;
		}
		addEventView(this);
		return MESSAGE_EMPTY;
	}

	@Override
	public void onRemind(Event event) {
		new JFXPanel();
		notify(event);
	}

	private void notify(Event event) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Notification noti = new Notification(MESSAGE_REMINDER, event.getTitle(), Notification.INFO_ICON);
				notifier = Notification.Notifier.INSTANCE;
				notifier.notify(noti);
			}
		});
	}
}