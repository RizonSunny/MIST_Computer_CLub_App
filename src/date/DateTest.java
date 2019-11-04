

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
  
public class DateTest {
   public static void main(String[] args) throws ParseException {
    //  Date now = new Date();
   //   System.out.println("toString(): " + now);  // dow mon dd hh:mm:ss zzz yyyy
      
      // SimpleDateFormat can be used to control the date/time display format:
      //   E (day of week): 3E or fewer (in text xxx), >3E (in full text)
      //   M (month): M (in number), MM (in number with leading zero)
      //              3M: (in text xxx), >3M: (in full text full)
      //   h (hour): h, hh (with leading zero)
      //   m (minute)
      //   s (second)
      //   a (AM/PM)
      //   H (hour in 0 to 23)
      //   z (time zone)
      
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm"); /// need line
         Date matchDateTime = sdf.parse("2017-01-16T10:00:00"); /// need line
          Date extra = sdf.parse("2017-00-00T01:00:00");
          
      SimpleDateFormat dateFormatter = new SimpleDateFormat("E, y-M-d 'at' h:m:s a z");
     // System.out.println("Format 1:   " + dateFormatter.format(now));
      
     
     Calendar cal=Calendar.getInstance();// need
      Date current= cal.getTime(); //need
      
      
      // System.out.println(current.toString());
       
        Date end = new Date(matchDateTime.getTime()+60*60*1000);
        
       // DateUtils DateUtils=new DateUtils(); 
       
        
        System.out.println(matchDateTime.toString());
        System.out.println(end.toString());
      
      // System.out.println(matchDateTime.toString());
       



//      if(current.compareTo(matchDateTime)>0) /// jake argument hisebe dicci tar result ta dekhabe
//      {
//          System.out.println("argument ager date");
//      }
//      else 
//      {
//          System.out.println("argument new date");
//      }


//      dateFormatter = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
//      System.out.println("Format 2:   " + dateFormatter.format(now));
      
//      dateFormatter = new SimpleDateFormat("EEEE, MMMM d, yyyy");
//      System.out.println("Format 3:   " + dateFormatter.format(now));
   }

  }