package utils;

import java.util.ArrayList;
import java.util.List;


import java.util.Calendar;

import utils.CalenderTime;
import utils.Event;

//class ErrorForAddding extends Exception {
//	public ErrorForAddding(String msg) {
//		super(msg);
//	}
//}

public class CalenderDate {
	
	private int date, numOfSlot=0;
	private ArrayList<CalenderTime> timeSlot;
	
	@SuppressWarnings("deprecation")
	public CalenderDate(Event event) {
		timeSlot = new ArrayList<CalenderTime>();
		defineTimeSlot();
		this.date=event.getStartDateTime().getTime().getDate();
		addTask(event);
	}
	
	public void addTask(Event event){
		
		int slot, range, i;
		
		if(checkTimeSlot(event)){
			numOfSlot++;
			slot=convertIndex(convertTime(event.getStartDateTime()));
			range=convertIndex(convertTime(event.getEndDateTime()));
			for(i=slot; i<range; i++){
				timeSlot.get(i).setTime(true, event);
			}
		}
		else{
			//throw new ErrorForAddding("Clash with another time slot");
		}
	}
	
	public CalenderDate(Event event, int numDate, int startTime, int endTime) {
		timeSlot = new ArrayList<CalenderTime>();
		defineTimeSlot();
		this.date=numDate;
		addTask(event, startTime,endTime);
	}
	
	public void addTask(Event event, int startTime, int endTime){
		
		int slot, range, i;
		
		if(checkTimeSlot(startTime, endTime)){
			numOfSlot++;
			slot=convertIndex(startTime);
			range=convertIndex(endTime);
			for(i=slot; i<range; i++){
				timeSlot.get(i).setTime(true, event);
			}
		}
		else{
			System.out.println("Warning time slot is being taken! Retry!");
		}
	}
	
	private boolean checkTimeSlot(int startTime, int endTime) {
		
		int start, end, i;

		start = convertIndex(startTime);
		end = convertIndex(endTime);
		for (i = start; i < end; i++) {
			if (timeSlot.get(i).getTimeSlot() == true)
				return false;
		}
		return true;
	}

	public void deleteTask(Event event){
		int slot, range, i;
		
		numOfSlot--;
		
		slot=convertIndex(convertTime(event.getStartDateTime()));
		range=convertIndex(convertTime(event.getEndDateTime()));

		for(i=slot; i<range; i++){
			timeSlot.get(i).clear();
		}
	}
	
	public List<Event> getTask(){
		int i;
		List<Event> events = new ArrayList<>();
		
		for(i=0;i<timeSlot.size();i++){
			if(timeSlot.get(i).getTimeSlot()==true&&!events.contains(timeSlot.get(i).getEvent())){
				events.add(timeSlot.get(i).getEvent());
			}
		}
		/*for(i=0;i<timeSlot.size();i++){
			if(timeSlot.get(i).getTimeSlot()==true){
				System.out.println(date+" " +timeSlot.get(i).getTask());
			}
			else{
				System.out.println(date+" " +timeSlot.get(i).getTimeSlot());
			}
		}
		System.out.println();*/
		return events;
	}
	
	public int getDate(){
		return date;
	}
	
	//return number of task in a day.
	public int getTimeSlot(){
		return numOfSlot;
	}
	
	public void clear() {
		numOfSlot=0;
		timeSlot.clear();
	}
	
	// check whether the timeslot is taken
	public Boolean checkTimeSlot(Event event) {
		int start, end, i;
		int timeStart = convertTime(event.getStartDateTime());
		int timeEnd = convertTime(event.getEndDateTime());

		start = convertIndex(timeStart);
		end = convertIndex(timeEnd);
		for (i = start; i < end; i++) {
			if (timeSlot.get(i).getTimeSlot() == true)
				return false;
		}
		return true;
	}

	private void defineTimeSlot() {
		int i;
		
		for(i=0; i<48; i++){
			timeSlot.add(new CalenderTime(false));
		}
	}
	
	@SuppressWarnings("deprecation")
	private int convertTime(Calendar input){
		int hour, minutes;
		
		hour=input.getTime().getHours();
		minutes = input.getTime().getMinutes();
		
		if(minutes<30){
			hour=hour*100;
		}
		else{
			hour=hour*100+30;
		}
		return hour;
	}
	
	//convert time to the index in arraylist
	private int convertIndex(int time){
		if(time%100==30){
			return (time+20)*2/100;
		}
		else{
			return time/100*2;
		}
	}
	
}