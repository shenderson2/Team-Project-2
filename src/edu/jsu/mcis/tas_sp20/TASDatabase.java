package edu.jsu.mcis.tas_sp20;
import java.sql.SQLException;
//import javax.xml.transform.Result;
import java.sql.*;
//import java.sql.Time; //Caitlin Added
import java.sql.Time;

public class TASDatabase {
    private Connection conn;

    public static void main(String[] args) {
        TASDatabase db = new TASDatabase();
      //  Punch punch = db.getPunch(3433);
        
        
    }

    public TASDatabase(){
        try {
         //   conn = DriverManager.getConnection(server, user, pass);
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
              
                System.out.println("Connected successfully!");
          
           
            server = "jdbc:mysql://localhost/TAS";
            user = "root";
            pass = "CS488";
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
        } catch (SQLException e) {
        }
    }

    public Punch getPunch(int ID) throws SQLException {
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

            query = "SELECT * FROM badge WHERE id=?";
            pstBadge = conn.prepareStatement(query);
            pstBadge.setString(1, badgeID);
            pstBadge.execute();
            resultSet = pstBadge.getResultSet();
            resultSet.first();
            
            Badge badge = new Badge(resultSet.getString("id"));
            
            punch = new Punch(terminalID, badge, origTimeStamp, punchTypeID);
            
          
        } catch (SQLException e) {
        }

        return punch;
    }

    public Badge getBadge(String ID) {
        Badge badge = null;
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
            badge = new Badge(ID);//, resultSet.getString("description")

        } catch (SQLException e) {
        }

        return badge;
    }

    public Shift getShift(int ID) {
        Shift shift = null;
        String query;
        PreparedStatement pst;
        ResultSet resultSet;
        int shiftId = 0;

        String description = null;
        Time start = null;
        Time stop = null;
        int interval = 0;
        int gracePeriod = 0;
        int dock = 0;
        Time lunchstart = null;
        Time  lunchstop = null;
        int lunchdeduct = 0;

   

        try {
            query = "SELECT * FROM shift WHERE id=?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, ID);

            pst.execute();
            resultSet  = pst.getResultSet();
      
           

        } catch (SQLException e) {
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
            pst.setString(1, badge.getid());

            pst.execute();
            resultSet = pst.getResultSet();
            resultSet.first();
            java.sql.Time temp;

            int shiftID = resultSet.getInt("shiftid");
            query = "SELECT * FROM shift WHERE id=?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, shiftID);
            pst.execute();

            resultSet = pst.getResultSet();
            resultSet.first();

            String description = resultSet.getString("description");
            temp = resultSet.getTime("start");
            Time start = temp;
            temp = resultSet.getTime("stop");
            Time stop = temp;
            int interval = resultSet.getInt("interval");
            int gracePeriod = resultSet.getInt("graceperiod");
            int dock = resultSet.getInt("dock");
            temp = resultSet.getTime("lunchstart");
            Time lunchStart = temp;
            temp = resultSet.getTime("lunchstop");
            Time lunchStop = temp;
            int lunchDeduct = resultSet.getInt("lunchdeduct");
            shift = new Shift(shiftID, description, start, stop, interval, gracePeriod, dock, lunchStart, lunchStop, lunchDeduct);
        } catch (SQLException e) {
        }

        return shift;
    }

    public int insertPunch(Punch p) {   
        String badgeID = p.getbadge().getid();
        int terminalID = p.getterminalid();
        int punchTypeID = p.getpunchtypeid();
       // Long originalTimeStamp = p.setOriginalTimeStamp();// Long needed to set time stamp

        try {
            PreparedStatement pst;
            ResultSet resultSet;
            String query;


            if (conn.isValid(0)){
                query = "INSERT INTO punch (terminalid, badgeid, originaltimestamp, punchtypeid) VALUES (?, ?, ?, ?)";
                pst = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                pst.setInt(1, terminalID);
                pst.setString(2, badgeID);
                long originaltimestamp = 0;
                Timestamp timestamp = new Timestamp(originaltimestamp);
                pst.setTimestamp(3, timestamp);
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
        } catch (SQLException e) {
        }

        return -1;

    }
}
