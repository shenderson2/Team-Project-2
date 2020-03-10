package edu.jsu.mcis.tas_sp20;
import java.sql.SQLException;
//import javax.xml.transform.Result;
import java.sql.*;
//import java.sql.Time; //Caitlin Added
import java.sql.Time;
import java.text.SimpleDateFormat;


public class TASDatabase {
    
    private Connection conn;

    public TASDatabase(){
        try {
          //  conn = DriverManager.getConnection(server, user, pass);
            String server = "jdbc:mysql://localhost/TAS"; 
            String user = "root";
            String pass = "CS488";
            Class.forName("com.mysql.jdbc.Driver").newInstance();

           conn = DriverManager.getConnection(server, user, pass);

            // remove after testing
            if (conn.isValid(0)) {
                System.out.println("Connected successfully!");
            } else {
                System.out.println("Error connecting!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TASDatabase(String server, String user, String pass) {
        try {
              
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            conn = DriverManager.getConnection(server, user, pass);

              if (conn.isValid(0)) {
                System.out.println("Connected successfully!");
            } else {
                System.out.println("Error connecting!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void close() {
        
        try {
            conn.close();
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Punch getPunch(int ID) {
        
        Punch punch = null;
        
        try {
            
            String query;
            PreparedStatement pstPunch, pstBadge;
            ResultSet resultSet;

            query = "SELECT * FROM punch WHERE id=?";
            pstPunch = conn.prepareStatement(query);
            pstPunch.setInt(1, ID);

            boolean execute = pstPunch.execute();
            resultSet = pstPunch.getResultSet();
            resultSet.first();

            int terminalID = resultSet.getInt("terminalid");
            String badgeID = resultSet.getString("badgeid");
            long origTimeStamp = resultSet.getTimestamp("originaltimestamp").getTime();
            int punchTypeID = resultSet.getInt("punchtypeid");
            
            punch = new Punch(terminalID, badgeID, origTimeStamp, punchTypeID);
            
          
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }

        return punch;
    }

    public Badge getBadge(String ID) {
        
        Badge badge = null;
        String b = null;
        String description = null;
        String query;
        PreparedStatement pst;
        ResultSet resultSet;

        try {
            
            query = "SELECT * FROM badge WHERE id=?";
            pst = conn.prepareStatement(query);
            pst.setString(1, ID);
            pst.execute();

            resultSet = pst.getResultSet();
            resultSet.first();
            b = resultSet.getString("id");
            description = resultSet.getString("description");
            

        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        badge = new Badge(b, description);

        return badge;
    }

    public Shift getShift(int ID) {
        
        Shift shift = null;
        String query;
        PreparedStatement pst;
        ResultSet resultSet;
        int shiftId = 0;

        String description = null;
        String start = null;
        String stop = null;
        int interval = 0;
        int graceperiod = 0;
        int dock = 0;
        String lunchstart = null;
        String  lunchstop = null;
        int lunchdeduct = 0;

        try {
            
            query = "SELECT * FROM shift WHERE id = ?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, ID);

            pst.execute();
            resultSet  = pst.getResultSet();
            resultSet.first();
            
            shiftId = resultSet.getInt("id");
            description = resultSet.getString("description");
            start = resultSet.getString("start");
            String[] starttime = start.split(":");
            int startHour = Integer.parseInt(starttime[0]);
            int startMinute = Integer.parseInt(starttime[1]);
            
            stop = resultSet.getString("stop");
            String[] stoptime = stop.split(":");
            int stopHour = Integer.parseInt(stoptime[0]);
            int stopMinute = Integer.parseInt(stoptime[1]);
            
            interval = resultSet.getInt("interval");
            graceperiod = resultSet.getInt("graceperiod");
            dock = resultSet.getInt("dock");
            lunchstart = resultSet.getString("lunchstart");
            String[] lstarttime = lunchstart.split(":");
            int lstartHour = Integer.parseInt(lstarttime[0]);
            int lstartMinute = Integer.parseInt(lstarttime[1]);
            
            lunchstop = resultSet.getString("lunchstop");
            String[] lstoptime = lunchstop.split(":");
            int lstopHour = Integer.parseInt(lstoptime[0]);
            int lstopMinute = Integer.parseInt(lstoptime[1]);
            lunchdeduct = resultSet.getInt("lunchdeduct");
            
            
            shift = new Shift(shiftId, description, startHour, startMinute,
            stopHour, stopMinute, interval, graceperiod, dock, lstartHour, lstartMinute, 
            lstopHour, lstopMinute, lunchdeduct);
           

        } 
        catch (SQLException e) {
             e.printStackTrace();
        }

        return shift;
    }

    public Shift getShift(Badge badge) {
        
        Shift shift = null;
        String query;
        PreparedStatement pst;
        ResultSet resultSet;

        try {
            
            query = "SELECT shiftid FROM employee WHERE badgeid = ?";
            pst = conn.prepareStatement(query);
            pst.setString(1, badge.getId());

            pst.execute();
            resultSet = pst.getResultSet();
            resultSet.first();
            int shiftID = resultSet.getInt("shiftid");
            
            shift = getShift(shiftID);
            
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return shift;
    }

    public void insertPunch(Punch p) {   
        
        String badgeID = p.getBadgeid();
        int terminalID = p.getTerminalid();
        int punchTypeID = p.getPunchtypeid();
        
        
        Long originalTimeStamp = p.getOriginaltimestamp();
        
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MM/dd/yyyy HH:mm:ss");
        String timestamp = sdf.format(originalTimeStamp).toUpperCase();
        
        PreparedStatement pst;
        ResultSet resultSet;
        String query;

        try {
       
            query = "INSERT INTO punch (terminalid, badgeid, originaltimestamp, punchtypeid) VALUES (?, ?, ?, ?)";
            pst = conn.prepareStatement(query);
            
            pst.setInt(1, terminalID);
            pst.setString(2, badgeID);
            pst.setString(3, timestamp);
            pst.setInt(4, punchTypeID);

            pst.execute();
          
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
