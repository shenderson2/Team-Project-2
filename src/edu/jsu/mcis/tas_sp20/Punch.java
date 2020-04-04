/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.jsu.mcis.tas_sp20;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Punch {
    
    private String adjustmenttype;
    private int terminalid;
    private int punchtypeid;
    private long originaltimestamp;
    private Badge badgeid;
    private GregorianCalendar gc = new GregorianCalendar ();
  
          
    public Punch(int terminalid, Badge badgeid, long originaltimestamp, int punchtypeid){
          this.punchtypeid = punchtypeid;
          this.terminalid = terminalid;
          this.originaltimestamp = originaltimestamp;
          

      //  this.originaltimestamp = System.currentTimeMillis();
          this.badgeid = badgeid;
    }
    public Punch( Badge badgeid, int terminalid, int punchtypeid){
          this.punchtypeid = punchtypeid;
          this.terminalid = terminalid;
          this.badgeid = badgeid;
          this.originaltimestamp = System.currentTimeMillis();
    } 
    
    public void adjust(Shift s){
            GregorianCalendar shiftstarttimestamp = new GregorianCalendar();   
            shiftstarttimestamp.setTimeInMillis(originaltimestamp);
            shiftstarttimestamp.set(Calendar.HOUR_OF_DAY, s.getstartHour());
            shiftstarttimestamp.set(Calendar.MINUTE, s.getstartMinute());
            shiftstarttimestamp.set(Calendar.SECOND, 0);
            long shiftstart = shiftstarttimestamp.getTimeInMillis();
            
            GregorianCalendar shiftstoptimestamp = new GregorianCalendar();   
            shiftstoptimestamp.setTimeInMillis(originaltimestamp);
            shiftstoptimestamp.set(Calendar.HOUR_OF_DAY, s.getstopHour());
            shiftstoptimestamp.set(Calendar.MINUTE, s.getstopMinute());
            shiftstoptimestamp.set(Calendar.SECOND, 0);
            long shiftstop = shiftstoptimestamp.getTimeInMillis();
            
            GregorianCalendar shiftlstarttimestamp = new GregorianCalendar();   
            shiftlstarttimestamp.setTimeInMillis(originaltimestamp);
            shiftlstarttimestamp.set(Calendar.HOUR_OF_DAY, s.getlstartHour());
            shiftlstarttimestamp.set(Calendar.MINUTE, s.getlstartMinute());
            shiftlstarttimestamp.set(Calendar.SECOND, 0);
            long shiftlstart = shiftstarttimestamp.getTimeInMillis();
            
            GregorianCalendar shiftlstoptimestamp = new GregorianCalendar();   
            shiftlstoptimestamp.setTimeInMillis(originaltimestamp);
            shiftlstoptimestamp.set(Calendar.HOUR_OF_DAY, s.getlstopHour());
            shiftlstoptimestamp.set(Calendar.MINUTE, s.getlstopMinute());
            shiftlstoptimestamp.set(Calendar.SECOND, 0);
            long shiftlstop = shiftstoptimestamp.getTimeInMillis();
            
            GregorianCalendar gracestarttimestamp = new GregorianCalendar();
            gracestarttimestamp.setTimeInMillis(shiftstarttimestamp.getTimeInMillis());
            gracestarttimestamp.add(Calendar.MINUTE, s.getGraceperiod());
            long gracestart = gracestarttimestamp.getTimeInMillis();
            
            GregorianCalendar gracestoptimestamp = new GregorianCalendar();
            gracestoptimestamp.setTimeInMillis(shiftstoptimestamp.getTimeInMillis());
            gracestoptimestamp.add(Calendar.MINUTE, -(s.getGraceperiod()));
            long gracestop = gracestoptimestamp.getTimeInMillis();
            
            GregorianCalendar Intervalstarttimestamp = new GregorianCalendar();
            Intervalstarttimestamp.setTimeInMillis(shiftstarttimestamp.getTimeInMillis());
            Intervalstarttimestamp.add(Calendar.MINUTE, s.getInterval());
            long Intervalstart = Intervalstarttimestamp.getTimeInMillis();
            
            GregorianCalendar Intervalstoptimestamp = new GregorianCalendar();
            Intervalstoptimestamp.setTimeInMillis(shiftstoptimestamp.getTimeInMillis());
            Intervalstoptimestamp.add(Calendar.MINUTE, -(s.getInterval()));
            long Intervalstop = Intervalstoptimestamp.getTimeInMillis();
            
            GregorianCalendar Dockstarttimestamp = new GregorianCalendar();
            Dockstarttimestamp.setTimeInMillis(shiftstarttimestamp.getTimeInMillis());
            Dockstarttimestamp.add(Calendar.MINUTE, s.getDock());
            long Dockstart = Dockstarttimestamp.getTimeInMillis();
            
            GregorianCalendar Dockstoptimestamp = new GregorianCalendar();
            Dockstoptimestamp.setTimeInMillis(shiftstoptimestamp.getTimeInMillis());
            Dockstoptimestamp.add(Calendar.MINUTE, -(s.getDock()));
            long Dockstop = Dockstoptimestamp.getTimeInMillis();
          
  
            /*
            "Shift Start" and "Shift Stop": The employee’s early "clock in" punch or late "clock out" punch (within the Interval before the start of the shift or after the end of the shift) are realigned with the starting or stopping time of the shift, respectively.
            
            
            "Lunch Start": The employee’s "clock out" punch (within the lunch break) is to be realigned with the start of lunch break.
            
            
            "Lunch Stop": The employee’s "clock in" punch (within the lunch break) is to be realigned with the stop of lunch break.
            
            
            "Grace Period": The employee’s late "clock in" punch or early "clock out" punch (within the Grace Period after the start of the shift or before the end of the shift) is to be realigned with the starting or stopping time of the shift, 
            respectively.
            
            
            "Dock": The employee’s late "clock in" punch (within the Dock interval after the start of the shift and outside the Grade Period) is to be adjusted forward in time from the start of the shift by this amount.  Similarly, the employee’s early "clock out" punch (within the Dock interval before the end of the shift and outside the Grade Period) is to be moved backward in time from the end of the shift by this amount.
            
            
            "Interval Round": If an employee's punch falls outside any of the rules outlined above—that is, if it is outside the Intervals before the start of the shift and after the end of the shift, if it does not fall within the Grace or Dock periods at the start and end of the shift, and if it does not occur within the designated lunch break—it should simply be rounded up or down to the nearest increment of the Interval.  In addition to rounding the minutes up or down, the seconds should be set to zero in the adjusted timestamp.
            
            
            "None": If the punch happens to have occurred at an even increment of the Interval (for example, if the Interval is set to 15 minutes and a first-shift employee clocks in at 9:30, disregarding the seconds), then no adjustment is necessary; all that needs to be done is to reset the seconds to zero.
            */
    }

    public String getAdjustmenttype() {
        return adjustmenttype;
    }

    public void setAdjustmenttype(String adjustmenttype) {
        this.adjustmenttype = adjustmenttype;
    }

    public int getTerminalid() {
        return terminalid;
    }

    public void setTerminalid(int terminalid) {
        this.terminalid = terminalid;
    }

    public int getPunchtypeid() {
        return punchtypeid;
    }

    public void setPunchtypeid(int punchtypeid) {
        this.punchtypeid = punchtypeid;
    }

    public long getOriginaltimestamp() {
        return originaltimestamp;
    }

    public void setOriginaltimestamp(long originaltimestamp) {
        
        gc.setTimeInMillis(originaltimestamp);
        this.originaltimestamp = originaltimestamp;
        
    }

    public Badge getBadge(){

        return this.badgeid;

    }
    public String getBadgeid(){

        return (this.getBadge()).getId();

    }

     public void setBadge(Badge badge){

        this.badgeid = badge;

    }

    public String printOriginalTimestamp(){
     String s = "#";
        String b = this.getBadgeid();
        s += b;
        
        switch (this.getPunchtypeid()){
            case 0:
                s += " CLOCKED OUT: ";
                break;
            case 1:
                s += " CLOCKED IN: ";
                break;
            case 2:
                s += " TIMED OUT: ";
        }
        
        DateFormat df = new SimpleDateFormat("EEE MM/dd/yyyy HH:mm:ss");
        Date d = new Date(this.originaltimestamp);
        
        s += (df.format(d)).toUpperCase();
        
        
        return s;
    }
    

