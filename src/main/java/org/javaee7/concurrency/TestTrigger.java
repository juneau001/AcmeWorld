
package org.javaee7.concurrency;

import java.util.Calendar;
import java.util.Date;
import javax.enterprise.concurrent.LastExecution;
import javax.enterprise.concurrent.Trigger;

/**
 *
 * @author Juneau
 */
public class TestTrigger implements Trigger {

    Date initialDate;
    
    public TestTrigger(Date initial){
        this.initialDate = initial;
    }
    
    @Override
    public Date getNextRunTime(LastExecution lastExecutionInfo, Date taskScheduledTime) {
        // implementation
        return null;
    }

    @Override
    public boolean skipRun(LastExecution lastExecutionInfo, Date scheduledRunTime) {
        // test implementation
        return false;
    }
    
}
