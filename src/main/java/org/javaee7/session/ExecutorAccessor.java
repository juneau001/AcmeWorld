package org.javaee7.session;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.enterprise.concurrent.ManagedThreadFactory;

/**
 * Adheres to the guidelines that are contained within the API specification.
 *
 * A Singleton session bean that is used to create an ExecutorService, which can
 * be utilized for creating managed thread tasks
 *
 * @author Juneau
 */
@Singleton
public class ExecutorAccessor {

    private ExecutorAccessor executorAccessor;
    private ThreadPoolExecutor tpe;
    @Resource(name = "concurrent/__defaultManagedThreadFactory")
    ManagedThreadFactory threadFactory;

    @PostConstruct
    public void postConstruct() {
        tpe = new ThreadPoolExecutor(
                5, 10, 5, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10), threadFactory);
    }

    public ExecutorService getThreadPool() {
        return tpe;
    }
}
