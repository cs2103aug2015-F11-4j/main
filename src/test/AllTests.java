package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/* @@author A0088646M */
@RunWith(Suite.class)
@SuiteClasses({ CalendrierTest.class, EventHandlerTest.class, EventGeneratorTest.class, EventTest.class,
		ParserTest.class, StorageManagerTest.class, IdMapperTest.class, 
		MainLogicFullTest.class, MainLogicClassTest.class, ReminderManagerTest.class })
public class AllTests {

}

