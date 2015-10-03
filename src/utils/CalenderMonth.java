package utils;

import java.util.ArrayList;
import java.util.List;

import utils.Event;

import utils.CalenderDate;

public class CalenderMonth {

	private int month; 
	private ArrayList<CalenderDate> date;
	
	public CalenderMonth() {
		date=new ArrayList <CalenderDate> ();
	}
	
	@SuppressWarnings("deprecation")
	public CalenderMonth(Event event) {
		date=new ArrayList <CalenderDate> ();
		this.month=event.getStartDateTime().getTime().getMonth();
		if(isSameDate(event)){
			addDate(event);
		}
		else{
			addDifferentDate(event);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void addDate(Event event) {
		if(!isDateAvaliable(event)){
			date.add(new CalenderDate(event));
		}
		else{
			int Index= returnIndex(event.getStartDateTime().getTime().getDate());
			date.get(Index).addTask(event);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void addDifferentDate(Event event) {
		int startDate, endDate,i;
		startDate = event.getStartDateTime().getTime().getDate();
		endDate=event.getEndDateTime().getTime().getDate();
		for(i=startDate;i<endDate+1;i++){
			if(!isDateAvaliable(i)){
				date.add(new CalenderDate(event));
			}
			else{
				int Index= returnIndex(i);
				date.get(Index).addTask(event);
			}
		}
	}

	/*@SuppressWarnings("deprecation")
	public List<Event> splitDifferentEvent (Event event) {
		int startDate, endDate, different,i, year, month, date, endHour, endMin;
		List<Event> events= new ArrayList<Event>();
		Event newEvent =  new Event();
		Calendar calendarStart = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		Calendar calendarEnd = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		startDate = event.getStartDateTime().getTime().getDate();
		endDate=event.getEndDateTime().getTime().getDate();
		endHour=event.getEndDateTime().getTime().getHours();
		endMin=event.getEndDateTime().getTime().getMinutes();
		different= endDate - startDate+1;
		year=event.getStartDateTime().getTime().getYear()+1900;
		month=event.getStartDateTime().getTime().getMonth();
		date=event.getStartDateTime().getTime().getDate();
		newEvent=event;
		for(i=0;i<different;i++){
			if(i==0){
				calendarEnd.set(year, month, date, 23, 59);
				newEvent.setEndDateTime(calendarEnd);
				events.add(newEvent);
				date++;
			}
			else if (i == different - 1) {
				calendarStart.set(year, month, date, 00, 00);
				calendarEnd.set(year, month, endDate, endHour, endMin);
				newEvent.setEndDateTime(calendarEnd);
				newEvent.setStartDateTime(calendarStart);
				events.add(newEvent);
			}
			else{
				calendarEnd.set(year, month, date, 23, 59);
				calendarStart.set(year, month, date, 00, 00);
				newEvent.setEndDateTime(calendarEnd);
				newEvent.setStartDateTime(calendarStart);
				events.add(newEvent);
				date++;
			}
		}
		System.out.println(events.get(0).toString());
		return events;
	}*/
	
	//get all the information for a specific date.
	public CalenderDate getDate(int numDate){
		int index= returnIndex(numDate);
		return date.get(index);
	}
	
	public int getMonth(){
		return month;
	}
	
	public void clear() {
		date.clear();
	}
	
	//return number of day that contain task
	public int getSize(){
		return date.size();
	}
	
	public List<Event> getTask(){
		int i;
		List<Event> events = new ArrayList<>();
		for(i=0;i<date.size();i++){
			events.addAll(date.get(i).getTask());
		}
		return events;
	}
	
	@SuppressWarnings("deprecation")
	public Boolean isSameDate (Event event) {
		
		int startDate, endDate;
		
		startDate = event.getStartDateTime().getTime().getDate();
		endDate=event.getEndDateTime().getTime().getDate();
		
		if(startDate==endDate){
			return true;
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public Boolean isDateAvaliable (Event event) {
		
		int i ;

		for (i = 0; i <date.size(); i++) {
			if (date.get(i).getDate()==event.getStartDateTime().getTime().getDate()) {
				return true;
			}
		}
		return false;
	}
	
	public Boolean isDateAvaliable (int checkDate) {
		
		int i ;

		for (i = 0; i <date.size(); i++) {
			if (date.get(i).getDate()==checkDate) {
				return true;
			}
		}
		return false;
	}
	
	//find the index of the date in the arraylist.
	public int returnIndex (int dateInfo){
		int i, index=0;
		
		for(i=0;i<date.size();i++){
			if(date.get(i).getDate()== dateInfo){
				index=i;
			}
		}
		return index;
	}
}