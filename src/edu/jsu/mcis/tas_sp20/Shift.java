/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.jsu.mcis.tas_sp20;

import java.sql.Time;

/**
 *
 * @author caitl
 */
public class Shift {
       
        public int shiftId;

        public Time start;

        public Time stop;
        
        public int lunchduration;

        public Time lunchstart;

        public Time lunchstop;

  
    

        public Shift(int shiftId, Time Start, Time Stop,int Lunchduration, Time Lunchstart, Time Lunchstop){



            this.shiftId = shiftId;

            this.start = Start;

            this.stop = Stop;

            this.lunchduration =Lunchduration;
            
            this.lunchstart = Lunchstart;

            this.lunchstop = Lunchstop;

        }

        
}

