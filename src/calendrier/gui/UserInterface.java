package calendrier.gui;

import utils.Event;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

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
	private static final String MESSAGE_WELCOME = "Welcome!";
	
	private static final int VALUE_START_SCREEN_MIN = 1;
	private static final int VALUE_START_SCREEN_MAX = 3;
	private static final int VALUE_TO_ADD_OR_MINUS = 1;
	
	private static final int VALUE_START_SCREEN = 1;
	private static final int VALUE_VIEW_SCREEN = 2;
	
	private static final String PARAM_NAVIGATION_NEXT = "next";
	private static final String PARAM_NAVIGATION_PREVIOUS = "previous";
	
	private int startScreenPage = VALUE_START_SCREEN_MIN;
	private int currentScreenState = VALUE_START_SCREEN;
	
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
	}
	
	private void addCommandBar(UserInterface userInterface) {
		rootLayout.setBottom(new CommandBarController(userInterface));
	}
	
	private void addStartScreen(UserInterface userInterface) {
		rootLayout.setCenter(new StartScreenController(userInterface, VALUE_START_SCREEN_MIN));
	}
	
	private void addEventView(UserInterface userInterface) {
		currentScreenState = VALUE_VIEW_SCREEN;
		// rootLayout.setCenter(new ViewEventController(mainLogic.getEvent()));
	}
	
	private void addView(UserInterface userInterface) {
		currentScreenState = VALUE_VIEW_SCREEN;
		rootLayout.setCenter(new ViewController(mainLogic.getAllEvents()));
	}
	
	private void getHelp(UserInterface userInterface) {
		currentScreenState = VALUE_START_SCREEN;
		startScreenPage = VALUE_START_SCREEN_MIN + VALUE_TO_ADD_OR_MINUS;
	}
	
	private void getNextPage(UserInterface userInterface) {
		if(currentScreenState == VALUE_START_SCREEN) {
			// to navigate around the start screen page
			if(isValidScreen(PARAM_NAVIGATION_NEXT)) {
				rootLayout.setCenter(new StartScreenController(userInterface, startScreenPage));
			}
		} else {
			// for view events next page...
		}
	}
	
	private void getPreviousPage(UserInterface userInterface) {
		if(currentScreenState == VALUE_START_SCREEN) {
			if(isValidScreen(PARAM_NAVIGATION_PREVIOUS)) {
				// rootLayout.setCenter(new StartScreenController(userInterface, startScreenPage));
			}
		} else {
			// for view events next page... 
		}
	}
	
	private boolean isValidScreen(String operation) {
		if(operation.equals(PARAM_NAVIGATION_NEXT)) {
			if(startScreenPage + VALUE_TO_ADD_OR_MINUS <= VALUE_START_SCREEN_MAX) {
				startScreenPage += VALUE_TO_ADD_OR_MINUS;
				return true;
			}
		} else {
			if(startScreenPage - VALUE_TO_ADD_OR_MINUS >= VALUE_START_SCREEN_MIN) {
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
	}
	
	private void handleEnterPress(CommandBarController commandBarController, String userInput) {
//			System.out.println(userInput);
//			commandBarController.setMessage(userInput);
		switch (mainLogic.execute(userInput)) {
			case STORAGE_LOCATION :
				commandBarController.setMessage(MESSAGE_WELCOME);
				addView(this);
				break;
			case ADD :
				commandBarController.setMessage(MESSAGE_SUCCESSFUL_ADD);
				addView(this);
				break;
			case VIEW_ALL :
				addView(this);
				break;
			case VIEW :
				addEventView(this);
				break;
			case FILTER :
				//addFilterView(this);
				break;
			case UPDATE :
				commandBarController.setMessage(MESSAGE_SUCCESSFUL_UPDATE);
				addView(this);
				break;
			case DELETE :
				commandBarController.setMessage(MESSAGE_SUCCESSFUL_DELETE);
				addView(this);
				break;
			case UNDO :
				commandBarController.setMessage(MESSAGE_SUCCESSFUL_UNDO);
				addView(this);
				break;
			case EXIT :
			case PREVIOUS :
				getPreviousPage(this);
				break;
			case NEXT :
				getNextPage(this);
				break;
			case HELP :
				getHelp(this);
				break;
			default :
				commandBarController.setMessage(MESSAGE_INVALID_COMMAND);
		}
	}
}
