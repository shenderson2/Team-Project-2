
package edu.jsu.mcis.tas_sp20;

import java.sql.Time;
import java.time.LocalTime;


public class Shift {
       
        private int shiftId;
        private String description;
        private LocalTime start;
        private LocalTime stop;    
        private int interval;
        private int graceperiod;
        private int dock;
        private LocalTime lunchstart;
        private LocalTime lunchstop;
        private int lunchdeduct;
        private int startHour;
        private int startMinute;
        private int stopHour;
        private int stopMinute;
        private int lstartHour;
        private int lstartMinute;
        private int lstopHour;
        private int lstopMinute;

        public Shift(int shiftId, String description, int startHour, int startMinute, int stopHour, int stopMinute, 
                int Interval, int Graceperiod, int Dock, int lstartHour,  int lstartMinute, 
                int lstopHour, int lstopMinute, int Lunchdeduct){


            this.shiftId = shiftId;
            this.description = description;
            this.start = LocalTime.of(startHour, startMinute);
            this.stop = LocalTime.of(stopHour, stopMinute);
            this.interval = Interval; 
            this.graceperiod = Graceperiod;
            this.dock = Dock;
            this.lunchstart = LocalTime.of(lstartHour, lstartMinute);
            this.lunchstop = LocalTime.of(lstopHour, lstopMinute);           
            this.lunchdeduct = Lunchdeduct;

        }
        public int getstartHour() {
            return startHour;
        }
        public int getstartMinute() {
            return startMinute;
        }
        public int getstopHour() {
            return stopHour;
        }
        public int getstopMinute() {
            return stopMinute;
        }
        public int getlstartHour() {
            return lstartHour;
        }
        public int getlstartMinute() {
            return lstartMinute;
        }
        public int getlstopHour() {
            return lstopHour;
        }
        public int getlstopMinute() {
            return lstopMinute;
        }
        public int getShiftId() {
            return shiftId;
        }

        public void setShiftId(int shiftId) {
            this.shiftId = shiftId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public LocalTime getStart() {
            return start;
        }

        public void setStart(LocalTime start) {
            this.start = start;
        }

        public LocalTime getStop() {
            return stop;
        }

        public void setStop(LocalTime stop) {
            this.stop = stop;
        }

        public int getInterval() {
            return interval;
        }

        public void setInterval(int interval) {
            this.interval = interval;
        }

        public int getGraceperiod() {
            return graceperiod;
        }

        public void setGraceperiod(int graceperiod) {
            this.graceperiod = graceperiod;
        }

        public int getDock() {
            return dock;
        }

        public void setDock(int dock) {
            this.dock = dock;
        }

        public LocalTime getLunchstart() {
            return lunchstart;
        }

        public void setLunchstart(LocalTime lunchstart) {
            this.lunchstart = lunchstart;
        }

        public LocalTime getLunchstop() {
            return lunchstop;
        }

        public void setLunchstop(LocalTime lunchstop) {
            this.lunchstop = lunchstop;
        }

        public int getLunchdeduct() {
            return lunchdeduct;
        }

        public void setLunchdeduct(int lunchdeduct) {
            this.lunchdeduct = lunchdeduct;
        }
        
        public int getshiftduration() {
            
            int starttime = start.getHour() * 60 + start.getMinute();
            int stoptime = stop.getHour() * 60 + stop.getMinute();
            
            return (stoptime - starttime);
            
        }
        
        public int getlunchbreak() {
            
            int Lunchstart = lunchstart.getHour() * 60 + lunchstart.getMinute();
            int Lunchstop = lunchstop.getHour() * 60 + lunchstop.getMinute();
            
            return (Lunchstop - Lunchstart);
        }
        
        
	@Override

	public String toString(){

                //"Shift 1: 07:00 - 15:30 (510 minutes); Lunch: 12:00 - 12:30 (30 minutes)
                
                StringBuilder s = new StringBuilder("");
                s.append(getDescription()).append(": ").append(this.getStart().toString());
                s.append(" - ").append(this.getStop().toString());
                s.append(" (").append(getshiftduration()).append(" minutes); Lunch: ").append(this.getLunchstart().toString());
                s.append(" - ").append(getLunchstop().toString()).append(" (");
                s.append(getlunchbreak()).append(" minutes)");
                
                
		return s.toString();

	}  


}

