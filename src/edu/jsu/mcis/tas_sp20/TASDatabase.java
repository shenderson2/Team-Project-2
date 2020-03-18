package edu.jsu.mcis.tas_sp20;
import java.sql.SQLException;
//import javax.xml.transform.Result;
import java.sql.*;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;


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
            PreparedStatement pst;
            ResultSet resultSet;

            query = "SELECT * FROM punch WHERE id=?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, ID);

            boolean execute = pst.execute();
            resultSet = pst.getResultSet();
            resultSet.first();

            int terminalID = resultSet.getInt(2);
            String badgeID = resultSet.getString(3);
            long origTimeStamp = resultSet.getTimestamp(4).getTime();
            int punchTypeID = resultSet.getInt(5);
            query = "SELECT * FROM badge WHERE id=?";

            pst = conn.prepareStatement(query);

            pst.setString(1, badgeID);

            pst.execute();

            resultSet = pst.getResultSet();

            resultSet.first();

            Badge badge = new Badge(resultSet.getString(1), resultSet.getString(2));
            
            
            punch = new Punch(terminalID, badge, origTimeStamp, punchTypeID);
            
            
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

    //public int insertPunch(Punch p)
    public int insertPunch(Punch p) {   
        
        String badgeID = p.getBadgeid();
  //      String badgeID = p.getId();
        int terminalID = p.getTerminalid();
        int punchTypeID = p.getPunchtypeid();
        int punchId = 1;
        
        Long originalTimeStamp = p.getOriginaltimestamp();
        GregorianCalendar ots = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MM/dd/yyyy HH:mm:ss");
        ots.setTimeInMillis(originalTimeStamp);
        
        Timestamp a = new Timestamp(ots.getTimeInMillis());
       
        
        ots.setTimeInMillis(p.getOriginaltimestamp());
        String originaltimestamp = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(ots.getTime());
        
        
        
       //a = new Timestamp(originalTimeStamp);
        
        PreparedStatement pst;
        String query;
        ResultSet resultSet;
        
        try {
     
            query = "INSERT INTO punch (terminalid, badgeid, originaltimestamp, punchtypeid) VALUES (?, ?, ?, ?)";
            //pst = conn.prepareStatement(query);
            pst = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, terminalID);
            pst.setString(2, badgeID);
            pst.setTimestamp(3, a);
            pst.setInt(4, punchTypeID);
            
            pst.execute();

            resultSet = pst.getGeneratedKeys();

            resultSet.first();

            if (resultSet.getInt(1) > 0) {

                return resultSet.getInt(1);

            } else {

                return -1;

            }
        
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        return punchId;
    }
    
    public ArrayList<Punch> getDailyPunchList(Badge badge, long ts) 
    {   
        Timestamp timestamp = new Timestamp(ts);
        ArrayList<Punch> dailyPunchList = new ArrayList<>();
        //return null;
        return null; 
    }
}
