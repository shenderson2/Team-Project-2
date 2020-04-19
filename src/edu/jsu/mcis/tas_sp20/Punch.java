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
    private long originaltimestamp, adjustedtimestamp;
    private Badge badgeid;
    private GregorianCalendar gc = new GregorianCalendar ();
    private long adjustedtime = 0;
    private String adjusttype = ""; 
          
    public Punch(int terminalid, Badge badgeid, long originaltimestamp, int punchtypeid){
          this.punchtypeid = punchtypeid;
          this.terminalid = terminalid;
          this.originaltimestamp = originaltimestamp;
          
          this.badgeid = badgeid;
    }
    public Punch( Badge badgeid, int terminalid, int punchtypeid){
          this.punchtypeid = punchtypeid;
          this.terminalid = terminalid;
          this.badgeid = badgeid;
          this.originaltimestamp = System.currentTimeMillis();
    } 
    
    
    public void adjust(Shift s){
        GregorianCalendar originalpunch= new GregorianCalendar();
        originalpunch.setTimeInMillis(this.originaltimestamp);
        originalpunch.set(GregorianCalendar.SECOND,0);
        long ogTime = originalpunch.getTimeInMillis(); 
        int dayofWeek = originalpunch.get(Calendar.DAY_OF_WEEK);

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
        long shiftlstart = shiftlstarttimestamp.getTimeInMillis();

        GregorianCalendar shiftlstoptimestamp = new GregorianCalendar();   
        shiftlstoptimestamp.setTimeInMillis(originaltimestamp);
        shiftlstoptimestamp.set(Calendar.HOUR_OF_DAY, s.getlstopHour());
        shiftlstoptimestamp.set(Calendar.MINUTE, s.getlstopMinute());
        shiftlstoptimestamp.set(Calendar.SECOND, 0);
        long shiftlstop = shiftlstoptimestamp.getTimeInMillis();

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
        Intervalstoptimestamp.add(Calendar.MINUTE, (s.getInterval()));
        long Intervalstop = Intervalstoptimestamp.getTimeInMillis();

        GregorianCalendar Dockstarttimestamp = new GregorianCalendar();
        Dockstarttimestamp.setTimeInMillis(shiftstarttimestamp.getTimeInMillis());
        Dockstarttimestamp.add(Calendar.MINUTE, s.getDock());
        long Dockstart = Dockstarttimestamp.getTimeInMillis();

        GregorianCalendar Dockstoptimestamp = new GregorianCalendar();
        Dockstoptimestamp.setTimeInMillis(shiftstoptimestamp.getTimeInMillis());
        Dockstoptimestamp.add(Calendar.MINUTE, -(s.getDock()));
        long Dockstop = Dockstoptimestamp.getTimeInMillis();


        if (dayofWeek != Calendar.SATURDAY && dayofWeek != Calendar.SUNDAY){ 

            switch (this.getPunchtypeid()){
                
                case 0:
                    
                    if ((ogTime >= shiftstop) && (ogTime <= Intervalstop)){
                        adjustedtime = shiftstop;
                        adjusttype = "Shift Stop";
                    }
                    else if ((ogTime <= shiftlstop) && (ogTime >= shiftlstart)){
                        adjustedtime = shiftlstart;
                        adjusttype = "Lunch Start";
                    }


                    else if ((ogTime <= shiftstop) && (ogTime >= gracestop )){
                        adjustedtime = shiftstop;
                        adjusttype = "Shift Stop";
                    }

                    else if ((ogTime >= Dockstop) && (ogTime < gracestop )){
                        adjustedtime = Dockstop;
                        adjusttype = "Shift Dock";
                    }


                    else {
                        adjustedtime = -1;
                        adjusttype = "Interval Round";
                    }
                    
                break;
                case 1:
                    
                    if ((ogTime > gracestart) && (ogTime <= Dockstart)){
                        adjustedtime = Dockstart;
                        adjusttype = "Shift Dock";
                    }
                    else if ((ogTime >= shiftstart) &&  (ogTime <= gracestart)){
                         adjustedtime = shiftstart;
                        adjusttype = "Shift Start";
                    }
                    else if  ((ogTime <= shiftlstop)  && (ogTime >= shiftlstart)){

                        adjustedtime = shiftlstop;
                        adjusttype = "Lunch Stop";
                    }
                    else if ((ogTime <= shiftstart)){

                         adjustedtime = shiftstart;
                        adjusttype = "Shift Start";
                    }
                    else {

                        adjustedtime = -1;
                        adjusttype = "Interval Round";
                    }
                break;
              }  

            }

            else 
            {
                    
                adjustedtime = -1;
                adjusttype = "Interval Round";

            }
                  
            makeAdjustment(s.getInterval() , originalpunch);
                    
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
    public String printAdjustedTimestamp(){
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
            Date d = new Date(this.adjustedtimestamp);

            s += (df.format(d)).toUpperCase() + " (" + this.getAdjustmenttype()+ ")";

            return s;
    }
    
    private void setAdjustedTimestamp(long time) {
        this.adjustedtimestamp = time;
    }

    private void makeAdjustment(int interval, GregorianCalendar originalpunch) 
    {
 
        int min = originalpunch.get(Calendar.MINUTE);
        int rem = min % interval;
        
        if(adjusttype.equals("Interval Round"))
        {
            
            if (rem != 0 )
            {
                if(rem < interval /2) 
                {
                    originalpunch.add(Calendar.MINUTE, - rem);
                }
                else
                {
                    originalpunch.add(Calendar.MINUTE, interval - rem);
                }
                adjustedtime = originalpunch.getTimeInMillis();
            }
            else
            {
                this.adjusttype = "None";
                adjustedtime = originalpunch.getTimeInMillis();
            }
        }
        
        this.setAdjustedTimestamp(adjustedtime);
        this.setAdjustmenttype(adjusttype);
        
        
        
    }
}
