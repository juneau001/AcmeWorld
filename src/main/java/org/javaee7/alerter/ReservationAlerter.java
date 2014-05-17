package org.javaee7.alerter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Example of Runnable that queries data
 * @author Juneau
 */
public class ReservationAlerter implements Runnable {

    @Override
    public void run() {
        System.out.println("Calling Managed Thread...");
      // If we wanted this thread to run continuously, place it into a loop...
      //  while (!Thread.interrupted()) {
            reviewReservations();
       //     try {
       //         Thread.sleep(300000);
       //     } catch (InterruptedException ex) {
       //         Logger.getLogger(ReservationAlerter.class.getName()).log(Level.SEVERE, null, ex);
       //     }
       // }
    }

    public Collection reviewReservations() {

        
        Connection conn = null;

        Collection reservations = null;
        try {

            String query = "select first_name, last_name, num_adults, num_child, num_days " +
                           "from park_reservation";
            DataSource ds = (DataSource) new InitialContext().lookup("jdbc/acmeworld");
            // Use the connection to query the database for reservations
            conn = ds.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            int resNumber = 1;
            while (rs.next()){
                String firstName = rs.getString("first_name");
                String lastName =  rs.getString("last_name");
                int numAdults = rs.getInt("num_adults");
                int numChild = rs.getInt("num_child");
                int numDays = rs.getInt("num_days");
                System.out.println("Reservation: " + resNumber);
                System.out.println(firstName + " " + lastName + " - Adults: " + numAdults +
                        " Children: " + numChild + " Days: " + numDays);
                resNumber++;    
            }
        } catch (SQLException|NamingException ex){
            System.out.println("Exception: " + ex);
        } finally {
            if (conn != null){
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ReservationAlerter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return reservations;
    }
}
