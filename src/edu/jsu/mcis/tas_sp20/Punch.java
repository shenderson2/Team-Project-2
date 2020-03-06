/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.jsu.mcis.tas_sp20;
import java.text.SimpleDateFormat;
import java.util.*;

public class Punch {
  private String adjustmenttype;
  private int terminalid;
  private int punchtypeid;
  private static int id;
  private long originaltimestamp;
  Badge badge;
  private GregorianCalendar gc = new GregorianCalendar ();
  
          
  public Punch(int terminalid, Badge badge, long originaltimestamp, int punchtypeid){
        this.punchtypeid = punchtypeid;
        this.terminalid = terminalid;
        this.originaltimestamp = originaltimestamp;
        this.badge = badge;
  }
   public Punch(int id){
       Punch.id = id;
   }
     public Badge getbadge(){
     return badge;
 }
    public int getid(){
        return id;
    }
   public int getterminalid(){
      return terminalid;
  }
   public long getoriginaltimestamp(){
        return originaltimestamp; 
   }
   public int getpunchtypeid(){
      return punchtypeid;
  }
   public void setOriginalTimeStamp(long ts){
       gc.setTimeInMillis(originaltimestamp);
       this.originaltimestamp = ts;
   }
    
    public String printOriginalTimestamp(){
      StringBuilder sb = new StringBuilder("#");
      sb.append(badge);
      switch(punchtypeid){
          case 0:
            sb.append(" CLOCKED OUT:");
            break;
          case 1:
            sb.append(" CLOCKED IN:");
            break; 
          case 2:
            sb.append(" TIMED OUT:");
            break;
          default:
            sb.append(" ERROR");
      }
       sb.append(originaltimestamp);
       
       SimpleDateFormat sdf = new SimpleDateFormat("EEE MM/dd/yyyy HH:mm:ss");
       sb.append(sdf.format(gc.getTime()).toUpperCase());
       return sb.toString();
    }
}
