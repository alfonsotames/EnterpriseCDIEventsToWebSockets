package timers;

import events.TestEvent;
import events.TestEventQualifier;
import java.util.Date;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 *
 * @author Alfonso Tamés
 */
@Singleton
public class TestTimer {

    @Inject
    @TestEventQualifier
    Event<TestEvent> testEvent;

    @Schedule(second = "*/5", minute = "*", hour = "*", persistent = false)
    public void sendCDIEvent() {
        System.out.println("Sending CDI event...");
        Date d = new Date();
        TestEvent te = new TestEvent("Hello from timer! "+d.toString());
        testEvent.fire(te);
        
        
    }
}