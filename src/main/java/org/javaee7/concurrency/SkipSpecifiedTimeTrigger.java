package org.javaee7.concurrency;

import java.util.Calendar;
import java.util.Date;
import javax.enterprise.concurrent.LastExecution;
import javax.enterprise.concurrent.Trigger;

/**
 *
 * @author Juneau
 */
public class SkipSpecifiedTimeTrigger implements Trigger {

    Date scheduleDate;
    int skipTime;

    public SkipSpecifiedTimeTrigger(Date initial, int skipTime) {
        this.scheduleDate = initial;
        this.skipTime = skipTime;
    }

    @Override
    public Date getNextRunTime(LastExecution lastExecutionInfo, Date taskScheduledTime) {
      
        if (scheduleDate.before(taskScheduledTime)) {
            return null;
        }
        Date nextRunTime = scheduleDate;
        Calendar cal = Calendar.getInstance();
        cal.setTime(scheduleDate);
        cal.add(Calendar.SECOND, skipTime);
        scheduleDate = cal.getTime();
        return nextRunTime;
    }

    @Override
    public boolean skipRun(LastExecution lastExecutionInfo, Date scheduledRunTime) {
        // Not Implemented Here...we could set the trigger to skip runs that
        // met the specified criteria.  Perhaps skip all runs where the schedule
        // date occurs at the top of the hour, etc.
        return false;

    }

}
