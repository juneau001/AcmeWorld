/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javaee7.rss;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.Callable;
import javax.enterprise.concurrent.ManagedTask;
import javax.enterprise.concurrent.ManagedTaskListener;
import static javax.servlet.SessionTrackingMode.URL;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author Juneau
 */
public class ConcurrentFeedReader implements Callable {

    private String url;
    
    public ConcurrentFeedReader(String url){
        this.url = url;
    }
    
    @Override
    public Object call() throws Exception {
        FeedReader reader = new FeedReader(url);
        Feed feed = reader.readFeed();
        return feed;
    }

  
   
}
