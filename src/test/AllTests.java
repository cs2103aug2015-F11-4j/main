package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CalendrierTest.class, EventHandlerTest.class, EventGeneratorTest.class, EventTest.class,
		ParserTest.class, StorageManagerTest.class,
		MainLogicFullTest.class, ReminderManagerTest.class })
public class AllTests {

}
