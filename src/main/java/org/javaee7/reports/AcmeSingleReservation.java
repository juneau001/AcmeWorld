
package org.javaee7.reports;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.javaee7.entity.ParkReservation;

/**
 * Retrieves all information for a specified reservation
 * @author Juneau
 */
public class AcmeSingleReservation implements Callable<ParkReservation>{
    
    private BigDecimal reservationId;
    
    
    public AcmeSingleReservation(BigDecimal reservationId){
        this.reservationId = reservationId;
    }
    
    @Override
    public ParkReservation call() throws Exception {
        Connection conn = null;

        ParkReservation reservation = null;
        try {
            // Obtain connection and retrieve reservations
            DataSource ds = (DataSource) new InitialContext().lookup("jdbc/acmeworld");
            // Use the connection to query the database for reservations
            conn = ds.getConnection();
           
            String qry = "select id, first_name, last_name, num_adults, " +
                    "num_child, num_days, trip_start_date, enter_date " +
                    "from Park_Reservation " +
                    "where id = ?";
            PreparedStatement pstmt = conn.prepareStatement(qry);
            pstmt.setBigDecimal(1, reservationId);
            ResultSet rset = pstmt.executeQuery();
            while(rset.next()){
                reservation = new ParkReservation();
                reservation.setId(rset.getBigDecimal("id"));
                reservation.setFirstName(rset.getString("first_name"));
                reservation.setLastName(rset.getString("last_name"));
                reservation.setNumAdults(rset.getInt("num_adults"));
                reservation.setNumChild(rset.getInt("num_child"));
                reservation.setTripStartDate(rset.getDate("trip_start_date"));
                reservation.setEnterDate(rset.getDate("enter_date"));
            }
        } catch (SQLException ex){
            System.out.println("Exception: " + ex);
        } finally {
            if (conn != null){
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AcmeSingleReservation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return reservation;
    }
    
}
