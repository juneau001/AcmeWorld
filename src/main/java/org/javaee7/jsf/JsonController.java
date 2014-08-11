

package org.javaee7.jsf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
import static javax.json.stream.JsonParser.Event.KEY_NAME;
import static javax.json.stream.JsonParser.Event.VALUE_FALSE;
import static javax.json.stream.JsonParser.Event.VALUE_NULL;
import static javax.json.stream.JsonParser.Event.VALUE_NUMBER;
import static javax.json.stream.JsonParser.Event.VALUE_STRING;
import static javax.json.stream.JsonParser.Event.VALUE_TRUE;
import org.javaee7.entity.ParkReservation;
import org.javaee7.session.ParkReservationFacade;

/**
 *
 * @author Juneau
 */
@Named(value = "jsonController")
@SessionScoped
public class JsonController implements Serializable {
    
    @EJB
    ParkReservationFacade parkReservationFacade;
    
    private StringBuilder jsonStr;

    /**
     * Creates a new instance of JsonController
     */
    public JsonController() {
    }
    
    public void buildReservations(){
        List<ParkReservation> reservations = parkReservationFacade.findAll();
        JsonObjectBuilder builder = Json.createObjectBuilder();
        JsonArrayBuilder reservationArray = Json.createArrayBuilder();
        setJsonStr(new StringBuilder());
        try(StringWriter sw = new StringWriter()){
            for(ParkReservation reservation:reservations){
                JsonObjectBuilder reservationBuilder = Json.createObjectBuilder();
                reservationBuilder.add("id", reservation.getId())
                        .add("firstName", reservation.getFirstName())
                        .add("lastName", reservation.getLastName())
                        .add("numAdults", reservation.getNumAdults())
                        .add("numChild", reservation.getNumChild())
                        .add("numDays", reservation.getNumDays())
                        .add("tripStart", reservation.getTripStartDate().toString());
                reservationArray.add(reservationBuilder);
                
            }
            builder.add("reservations", reservationArray);
            JsonObject result = builder.build();
            try(JsonWriter writer = Json.createWriter(sw)){
                writer.writeObject(result);
            }
            getJsonStr().append(sw.toString());
            System.out.println(getJsonStr());
            writeToDisk("reservations.json", result);
        } catch (IOException ex){
            System.out.println(ex);
        }
    }
    
    /**
     * Utility method to write JSON to disk
     * @param fileName
     * @param obj 
     */
    public void writeToDisk(String fileName, JsonObject obj){
        try {
        JsonWriter writer = Json.createWriter(new FileWriter(fileName));
        writer.writeObject(obj);
        writer.close();
        } catch (IOException ex){
            System.out.println(ex);
        }
    }
    
    public void parseJson(String fileName){
        try {
        InputStream is = new FileInputStream(fileName);
        JsonParser parser = Json.createParser(is);
        
        while(parser.hasNext()){
            Event event = parser.next();
            switch (event) {
            case KEY_NAME:
                parser.getString();
                
                break;
            case VALUE_STRING:
                // perform processing of String value
                break;
            case VALUE_NUMBER:
                // perform processing of number value
                break;
            case VALUE_FALSE:
                // perform processing of boolean
                break;
            case VALUE_TRUE:
                // perform processing of boolean
                break;
            case VALUE_NULL:
                // don't set anything
                break;
            default:
                // we are not looking for other events
            }
        }
        
        
        } catch (FileNotFoundException ex){
            System.out.println(ex);
        }
    }

    /**
     * @return the jsonStr
     */
    public StringBuilder getJsonStr() {
        return jsonStr;
    }

    /**
     * @param jsonStr the jsonStr to set
     */
    public void setJsonStr(StringBuilder jsonStr) {
        this.jsonStr = jsonStr;
    }
    
}
