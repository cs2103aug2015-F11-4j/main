package calendrier.gui;

import java.io.IOException;
import java.util.List;

import utils.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;

public class ViewController extends FlowPane {

	private static final String VIEW_SCREEN_LAYOUT_FXML = "/calendrier/resources/View.fxml";
	private static final int VALUE_ADD_TO_ARRAY = 8;

	public ViewController(List<Event> events, int startIndex) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(
				VIEW_SCREEN_LAYOUT_FXML));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setHand(events, startIndex);
	}

	public void setHand(List<Event> events, int startIndex) {
		int endingIndex = startIndex + VALUE_ADD_TO_ARRAY;
		if((endingIndex) > events.size()) {
			endingIndex = events.size();
		}
		
		for (int i = startIndex; i < endingIndex; i++) {
			addEvent(events.get(i));
			System.out.println(events.get(i).getId());
		}
	}

	public void addEvent(Event event) {
		getChildren().add(new EventBoxController(event));
	}
}
