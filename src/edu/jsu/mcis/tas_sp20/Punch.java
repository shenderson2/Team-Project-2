/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.jsu.mcis.tas_sp20;


public class Punch {
  private String adjustmenttype;
  private int terminalid;
  private int punchtypeid;
  private static int id;
  private String originaltimestamp;
  Badge badge;
          
  public Punch(Badge badge, int terminalid, int punchtypeid){
        this.punchtypeid = punchtypeid;
        this.terminalid = terminalid;
  }
   public Badge getbadge(){
     return badge;
 }
   public Punch(int id){
       Punch.id = id;
   }
    public int getid(){
        return id;
    }
   public int getterminalid(){
      return terminalid;
  }
   public String get_originaltimestamp(){
      return originaltimestamp; 
   }
   public void set_orginaltimestamp(String timestamp){
        this.originaltimestamp = timestamp;
    }
   public int getpunchtypeid(){
      return punchtypeid;
  }

}
