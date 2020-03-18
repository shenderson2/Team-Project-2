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
    } 
    
    public void adjust(Shift s){
        
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
    
}
