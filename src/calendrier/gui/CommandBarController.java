package calendrier;

/*
* This class handles: 
* 1. commandbar where user input commands
* 2. label to show message 
* (e.g. successful add/update/delete event)
*/

import java.io.IOExeption;

import javafx.fxml.FXML;
import javafx.fml.FXMLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class CommandBarController extends BorderPane {

	@FXML
	private Label lblMessage;

	@FXML
	private TextField tfCommandBar;

	private static final String COMMAND_BAR_LAYOUT_FXML = "calendrier/resources/CommandBar.fxml";

	private UserInterface userInterface;

	public CommandBarController(UserInterface userInterface) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(COMMAND_BAR_LAYOUT_FXML));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOExeption e) {
			e.printStackTrace();
		}

		this.userInterface = userInterface;
	}

	@FXML
	public void onKeyPress(KeyEvent event) {
		userInterface.handleKeyPress(this, event.getCode(), tfCommandBar.getText());
	}

	public void clear() {
		tfCommandBar.clear();
	}

	public void setMessage(String messageText) {
		lblMessage.setText(feedbackText);
	}
}