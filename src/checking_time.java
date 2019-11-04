
import java.util.Timer;
import java.util.TimerTask;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author RAZ
 */
public class checking_time {
    
    
    int secpas=0;
    Timer timer=new Timer();
    TimerTask timertask=new TimerTask(){
        
        public void run()
        {
            secpas++;
            if(secpas%300==0)
            {
                System.out.println("5 mnt hoice");
            }
            
        }
    };
    
    public void start()
    {
        timer.scheduleAtFixedRate(timertask,1000,1000);    
                
    }
    
}
