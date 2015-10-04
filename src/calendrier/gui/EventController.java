package calendrier.gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import utils.Event;

public class EventController extends StackPane{

	private static final String EVENT_BOX_LAYOUT_FXML = "/calendrier/resources/EventBox.fxml";
	
	@FXML
	private Text eventID;
	private Text eventTitle;
	private Text eventDate;
	private Text eventTime;
	
	
	public EventController(Event event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(EVENT_BOX_LAYOUT_FXML));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		eventID.setText(event.getId());
		eventTitle.setText(event.getTitle());
		eventDate.setText(event.getStartDateTime().toString());
		eventTime.setText(event.getStartDateTime().getTime().toString());
		
		
	}
}
