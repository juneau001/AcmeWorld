package org.javaee7.session;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.ejb.TimerService;

/**
 * EJB Timer to send notification to users
 *
 * @author juneau
 */
@Stateless
public class NotificationTimer {
    
    @Resource
    SessionContext sessionContext;

    @Schedule(minute = "*/1", hour = "*")
    public void automaticTimer() {
        System.out.println("*** Automatic Timeout Occurred - Send notification to users ***");
    }
    
    /**
     * Return all timer data
     * @return 
     */
    public Collection<Timer> obtainActiveTimers() {
        List<Timer> timerList = new ArrayList<>();
        TimerService timerService = sessionContext.getTimerService();
        Collection<Timer> timers = timerService.getAllTimers();
        
        return timers;
    }
}
