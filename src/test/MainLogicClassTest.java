package test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

import calendrier.MainLogic;
import utils.Command;
import utils.Event;
import utils.OnRemindListener;

public class MainLogicClassTest {

	
	@Test
	public void testCountDown(){
		MainLogic mainLogic = loadEmptyTestCases();
		Calendar in10Minutes = Calendar.getInstance();
		in10Minutes.add(Calendar.MINUTE, 10);
		
		String startdate = String.format("%04d/%02d/%02d", in10Minutes.get(Calendar.YEAR), in10Minutes.get(Calendar.MONTH) + 1, in10Minutes.get(Calendar.DATE));
		String starttime = String.format("%02d.%02d", in10Minutes.get(Calendar.HOUR_OF_DAY), in10Minutes.get(Calendar.MINUTE));
		String command = String.format("add test, startdate %s, starttime %s", startdate, starttime);
		
		Command cmd = mainLogic.execute(command);
		assertEquals(Command.ADD, cmd);
		
		long countdown = mainLogic.getTimeToNextEvent();
		assertTrue(countdown <= 600000);
	}
	
	@Test
	public void testCountDownNotAfter(){
		MainLogic mainLogic = loadEmptyTestCases();
		Calendar in10Minutes = Calendar.getInstance();
		in10Minutes.add(Calendar.MINUTE, -10);
		
		String startdate = String.format("%04d/%02d/%02d", in10Minutes.get(Calendar.YEAR), in10Minutes.get(Calendar.MONTH) + 1, in10Minutes.get(Calendar.DATE));
		String starttime = String.format("%02d.%02d", in10Minutes.get(Calendar.HOUR_OF_DAY), in10Minutes.get(Calendar.MINUTE));
		String command = String.format("add test, startdate %s, starttime %s", startdate, starttime);
		
		Command cmd = mainLogic.execute(command);
		assertEquals(Command.ADD, cmd);
		
		long countdown = mainLogic.getTimeToNextEvent();
		assertTrue(countdown == -1);
	}
	
	@Test
	public void testCountDownFloating(){
		MainLogic mainLogic = loadEmptyTestCases();
		String command = "add test";
		
		Command cmd = mainLogic.execute(command);
		assertEquals(Command.ADD, cmd);
		
		long countdown = mainLogic.getTimeToNextEvent();
		assertTrue(countdown == -1);
	}
	
	@Test
	public void testCovers(){
		MainLogic mainLogic = loadEmptyTestCases();
		String command = "add test, startdate 2015/9/10";
		Command cmd = mainLogic.execute(command);
		assertEquals(Command.ADD, cmd);
		
		List<Event> monthEvents = mainLogic.getMonthEvents(2015, 10);
		for(int i = 0; i < monthEvents.size(); i++){
			Event event = monthEvents.get(i);
			boolean startOK = false;
			boolean endOK = false;
			boolean cover = false;
			
			if(event.getStartDateTime() != null){
				startOK = event.getStartDateTime().get(Calendar.MONTH) == 9;
			}
			if(event.getEndDateTime() != null){
				endOK = event.getEndDateTime().get(Calendar.MONTH) == 9;
			}
			
			if(event.getStartDateTime() != null && event.getEndDateTime() != null){
				int startMonth = event.getStartDateTime().get(Calendar.MONTH);
				int endMonth = event.getEndDateTime().get(Calendar.MONTH);
				int startDate = event.getStartDateTime().get(Calendar.DATE);
				int endDate = event.getEndDateTime().get(Calendar.DATE);
				
				cover = (startMonth < 9) && (endMonth > 9);
			}
			
			assertTrue(startOK || endOK || cover);
		}
	}
	
	@Test
	public void testCoversStartBeforeEndBefore(){
		MainLogic mainLogic = loadEmptyTestCases();
		String command = "add test, startdate 2015/9/10, enddate 2015/9/11";
		Command cmd = mainLogic.execute(command);
		assertEquals(Command.ADD, cmd);
		
		List<Event> monthEvents = mainLogic.getMonthEvents(2015, 10);
		for(int i = 0; i < monthEvents.size(); i++){
			Event event = monthEvents.get(i);
			boolean startOK = false;
			boolean endOK = false;
			boolean cover = false;
			
			if(event.getStartDateTime() != null){
				startOK = event.getStartDateTime().get(Calendar.MONTH) == 9;
			}
			if(event.getEndDateTime() != null){
				endOK = event.getEndDateTime().get(Calendar.MONTH) == 9;
			}
			
			if(event.getStartDateTime() != null && event.getEndDateTime() != null){
				int startMonth = event.getStartDateTime().get(Calendar.MONTH);
				int endMonth = event.getEndDateTime().get(Calendar.MONTH);
				int startDate = event.getStartDateTime().get(Calendar.DATE);
				int endDate = event.getEndDateTime().get(Calendar.DATE);
				
				cover = (startMonth < 9) && (endMonth > 9);
			}
			
			assertTrue(startOK || endOK || cover);
		}
	}
	
	@Test 
	public void testValidGetMonthStartBeforeEndIn(){
		MainLogic mainLogic = loadEmptyTestCases();
		String command = "add test, startdate 2015/9/20, starttime 12.00, enddate 2015/10/10, endtime 13.25";
		Command cmd = mainLogic.execute(command);
		assertEquals(Command.ADD, cmd);
		
		List<Event> monthEvents = mainLogic.getMonthEvents(2015, 10);
		for(int i = 0; i < monthEvents.size(); i++){
			Event event = monthEvents.get(i);
			boolean startOK = false;
			boolean endOK = false;
			boolean cover = false;
			
			if(event.getStartDateTime() != null){
				startOK = event.getStartDateTime().get(Calendar.MONTH) == 9;
			}
			if(event.getEndDateTime() != null){
				endOK = event.getEndDateTime().get(Calendar.MONTH) == 9;
			}
			
			if(event.getStartDateTime() != null && event.getEndDateTime() != null){
				int startMonth = event.getStartDateTime().get(Calendar.MONTH);
				int endMonth = event.getEndDateTime().get(Calendar.MONTH);
				int startDate = event.getStartDateTime().get(Calendar.DATE);
				int endDate = event.getEndDateTime().get(Calendar.DATE);
				
				cover = (startMonth < 9) && (endMonth > 9);
			}
			
			assertTrue(startOK || endOK || cover);
		}
	}
	
	@Test 
	public void testValidGetMonth(){
		MainLogic mainLogic = loadTestCases();
		List<Event> monthEvents = mainLogic.getMonthEvents(2015, 10);
		for(int i = 0; i < monthEvents.size(); i++){
			Event event = monthEvents.get(i);
			boolean startOK = false;
			boolean endOK = false;
			boolean cover = false;
			
			if(event.getStartDateTime() != null){
				startOK = event.getStartDateTime().get(Calendar.MONTH) == 9;
			}
			if(event.getEndDateTime() != null){
				endOK = event.getEndDateTime().get(Calendar.MONTH) == 9;
			}
			
			if(event.getStartDateTime() != null && event.getEndDateTime() != null){
				int startMonth = event.getStartDateTime().get(Calendar.MONTH);
				int endMonth = event.getEndDateTime().get(Calendar.MONTH);
				int startDate = event.getStartDateTime().get(Calendar.DATE);
				int endDate = event.getEndDateTime().get(Calendar.DATE);
				
				cover = (startMonth < 9) && (endMonth > 9);
			}
			
			assertTrue(startOK || endOK || cover);
		}
	}
	
	@Test 
	public void testValidGetMonthWithFloating(){
		MainLogic mainLogic = loadTestCases();
		List<Event> monthEvents = mainLogic.getMonthEvents(2015, 10, true);
		for(int i = 0; i < monthEvents.size(); i++){
			Event event = monthEvents.get(i);
			boolean startOK = false;
			boolean endOK = false;
			boolean floating = event.getStartDateTime() == null && event.getEndDateTime() == null;
			boolean cover = false;
			
			if(event.getStartDateTime() != null){
				startOK = event.getStartDateTime().get(Calendar.MONTH) == 9;
			}
			if(event.getEndDateTime() != null){
				endOK = event.getEndDateTime().get(Calendar.MONTH) == 9;
			}
			
			if(event.getStartDateTime() != null && event.getEndDateTime() != null){
				int startMonth = event.getStartDateTime().get(Calendar.MONTH);
				int endMonth = event.getEndDateTime().get(Calendar.MONTH);
				int startDate = event.getStartDateTime().get(Calendar.DATE);
				int endDate = event.getEndDateTime().get(Calendar.DATE);
				
				cover = (startMonth < 9) && (endMonth > 9);
			}
			
			assertTrue(startOK || endOK || floating || cover);
		}
	}
	
	@Test 
	public void testValidGetDay(){
		MainLogic mainLogic = loadTestCases();
		List<Event> monthEvents = mainLogic.getDayEvents(2015, 10, 30);
		for(int i = 0; i < monthEvents.size(); i++){
			Event event = monthEvents.get(i);
			boolean startOK = event.getStartDateTime().get(Calendar.DATE) == 30;
			boolean endOK = event.getEndDateTime().get(Calendar.DATE) == 30;
			boolean cover = false;
			
			int startMonth = event.getStartDateTime().get(Calendar.MONTH);
			int endMonth = event.getEndDateTime().get(Calendar.MONTH);
			int startDate = event.getStartDateTime().get(Calendar.DATE);
			int endDate = event.getEndDateTime().get(Calendar.DATE);
			
			cover = (startMonth < 9 || (startMonth == 9 && startDate < 30)) 
					&& (endMonth > 9 || (endMonth == 9 && endDate > 30));
			
			assertTrue(startOK || endOK || cover);
		}
	}
	
	@Test 
	public void testValidGetDayStartBeforeEndIn(){
		MainLogic mainLogic = loadEmptyTestCases();
		String command = "add test, startdate 2015/10/10, starttime 12.30, enddate 2015/10/30, endtime 12.02";
		Command cmd = mainLogic.execute(command);
		assertEquals(Command.ADD, cmd);
		
		List<Event> monthEvents = mainLogic.getDayEvents(2015, 10, 30);
		for(int i = 0; i < monthEvents.size(); i++){
			Event event = monthEvents.get(i);
			boolean startOK = event.getStartDateTime().get(Calendar.DATE) == 30;
			boolean endOK = event.getEndDateTime().get(Calendar.DATE) == 30;
			boolean cover = false;
			
			int startMonth = event.getStartDateTime().get(Calendar.MONTH);
			int endMonth = event.getEndDateTime().get(Calendar.MONTH);
			int startDate = event.getStartDateTime().get(Calendar.DATE);
			int endDate = event.getEndDateTime().get(Calendar.DATE);
			
			cover = (startMonth < 9 || (startMonth == 9 && startDate < 30)) 
					&& (endMonth > 9 || (endMonth == 9 && endDate > 30));
			
			assertTrue(startOK || endOK || cover);
		}
	}
	
	@Test 
	public void testValidGetDayWithFloating(){
		MainLogic mainLogic = loadTestCases();
		List<Event> monthEvents = mainLogic.getDayEvents(2015, 10, 30, true);
		for(int i = 0; i < monthEvents.size(); i++){
			Event event = monthEvents.get(i);
			boolean startOK = false;
			boolean endOK = false;
			boolean floating = event.getStartDateTime() == null && event.getEndDateTime() == null;
			boolean cover = false;
			
			if(event.getStartDateTime() != null){
				startOK = event.getStartDateTime().get(Calendar.DATE) == 30;
			}
			if(event.getEndDateTime() != null){
				endOK = event.getEndDateTime().get(Calendar.DATE) == 30;
			}
			
			if(event.getStartDateTime() != null && event.getEndDateTime() != null){
				int startMonth = event.getStartDateTime().get(Calendar.MONTH);
				int endMonth = event.getEndDateTime().get(Calendar.MONTH);
				int startDate = event.getStartDateTime().get(Calendar.DATE);
				int endDate = event.getEndDateTime().get(Calendar.DATE);
				
				cover = (startMonth < 9 || (startMonth == 9 && startDate < 30)) 
						&& (endMonth > 9 || (endMonth == 9 && endDate > 30));
			}
			assertTrue(startOK || endOK || floating || cover);
		}
	}
	
	@Test
	public void testSetOnRemindListener(){
		MainLogic mainLogic = loadEmptyTestCases();
		mainLogic.setOnRemindListener(new OnRemindListener() {
			
			@Override
			public void onRemind(Event event) {
				// Do Nothing
			}
		});
	}
	
	@Test 
	public void testSetNullOnRemindListener(){
		MainLogic mainLogic = loadEmptyTestCases();
		mainLogic.setOnRemindListener(null);
		assertTrue(true);
	}
	
	@Test
	public void testInvalidCommand(){
		MainLogic mainLogic = loadTestCases();
		String command = "blabla blablabla";
		
		Command cmd = mainLogic.execute(command);
		assertTrue("should be null", cmd == null);
	}
	
	@Test
	public void testFilterCommand(){
		MainLogic mainLogic = loadTestCases();
		String command = "filter name, priority very high";
		Command cmd = mainLogic.execute(command);
		assertTrue("should be filter", cmd == Command.FILTER);
		assertTrue(mainLogic.getFilteredEvents() != null);
		
	}
	
	@Test
	public void testAdd(){
		MainLogic mainLogic = loadEmptyTestCases();
		assertTrue(mainLogic.getAllEvents().size() == 0);
		
		String command = "add test case";
		Command cmd = mainLogic.execute(command);
		assertEquals(Command.ADD, cmd);
		
		assertTrue(mainLogic.getAllEvents().size() == 1);
		assertEquals(mainLogic.getEvent().getTitle(), mainLogic.getAllEvents().get(0).getTitle());
	}
	

	private MainLogic loadTestCases(){
		MainLogic mainLogic = new MainLogic();
		String command = "save in ml-test.txt";
		Command cmd = mainLogic.execute(command);
		assertEquals(Command.STORAGE_LOCATION, cmd);
		
		return mainLogic;
	}
	
	private MainLogic storageTestCases(){
		MainLogic mainLogic = new MainLogic();
		String command = "save in storage.txt";
		Command cmd = mainLogic.execute(command);
		assertEquals(Command.STORAGE_LOCATION, cmd);
		
		return mainLogic;
	}
	
	private MainLogic loadEmptyTestCases(){
		clearFile("empty.txt");
		MainLogic mainLogic = new MainLogic();
		String command = "save in empty.txt";
		Command cmd = mainLogic.execute(command);
		assertEquals(Command.STORAGE_LOCATION, cmd);
		
		return mainLogic;
	}
	
	private void clearFile(String filename){
		File file = new File(filename);
		file.delete();
	}

}
