

package org.javaee7.jsf;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import org.javaee7.entity.Park;
import org.javaee7.session.ParkFacade;

/**
 *
 * @author Juneau
 */
@Named(value = "parkController")
@SessionScoped
public class ParkController implements Serializable {

    @EJB
    ParkFacade ejbFacade;
    
    private Park current;
    /**
     * Creates a new instance of ParkController
     */
    public ParkController() {
    }

    /**
     * @return the current
     */
    public Park getCurrent() {
        if(current == null){
            current =new Park();
        }
        return current;
    }

    /**
     * @param current the current to set
     */
    public void setCurrent(Park current) {
        this.current = current;
    }
    
    /**
     * Returns all Park entities within a SelectItem list
     * @return 
     */
    public List<SelectItem> parkList(){
        List<Park> parkList = ejbFacade.findAll();
        List<SelectItem> parkItems = new ArrayList<>();
        for(Park park:parkList){
            parkItems.add(new SelectItem(park.getId(), park.getName()));
        }
        return parkItems;
    }
    
}
