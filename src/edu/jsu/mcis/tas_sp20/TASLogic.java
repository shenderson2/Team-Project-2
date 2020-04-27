package edu.jsu.mcis.tas_sp20;
import java.util.ArrayList;
import java.util.GregorianCalendar;



public class TASLogic {
    public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, Shift shift)
    {
        long timeinmils = 0;
        int timeinmin = 0;
        int lunchinmin = 0;
        int lunchduct = 0;
        GregorianCalendar in = new GregorianCalendar ();
        GregorianCalendar out = new GregorianCalendar ();
        boolean timeout = true;
        int lunch = shift.getlunchbreak() ; 
        boolean testlunch = false;
        if (dailypunchlist.size() >= 4)
        {
            testlunch = true;
        }
        for (int i = 0; i < dailypunchlist.size();i ++) 
        {
            Punch punch = dailypunchlist.get(i);
            if (punch.getPunchtypeid()== 0) {
                out.setTimeInMillis(punch.getAdjustedTimestamp());
                timeout = false;
            }
            else if (punch.getPunchtypeid()== 1) {
                in.setTimeInMillis(punch.getAdjustedTimestamp());
                timeout = true;
            }
            
            if(!timeout)
            {
                timeinmils += (out.getTimeInMillis() - in.getTimeInMillis())/ 60000;
            }
           
        }
        timeinmin = (int)timeinmils;
        if (timeout)
        {
            lunch = 0;
        }
        if(testlunch)
        {
            out.setTimeInMillis(dailypunchlist.get(1).getOriginaltimestamp());
            in.setTimeInMillis(dailypunchlist.get(2).getOriginaltimestamp());
            timeinmils = (out.getTimeInMillis() - in.getTimeInMillis())/ 60000;
            lunchinmin = (int)timeinmils;
            lunch= 0;
        }
        if(timeinmin <= (6 * 60)&& !testlunch)
        {
            lunch = 0;
        }
        
        return timeinmin - lunch;
    }
}
