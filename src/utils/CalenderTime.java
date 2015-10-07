package utils;
import utils.Event;
import java.util.ArrayList;
import java.util.List;

public class CalenderTime{
	private Boolean avalibility;
	private List<Event> events;
	
	public CalenderTime(){}
	public CalenderTime(Boolean input){
		events=new ArrayList<Event>();
		this.avalibility=input;
	}
	public void setTime(Boolean input, Event event){
		setTimeSlot(input);
		setTask(event);
	}
	public void setTimeSlot(Boolean input){
		this.avalibility = input;
	}
	public void setTask(Event event){
		events.add(event);
	}
	public List<Event> getEvent(){
		return events;
	}
	public Boolean getTimeSlot(){
		return avalibility;
	}
	public void clear(){
		avalibility=false;
		events.clear();
	}
	public void delete(Event event) {
		events.remove(event);
	}
}