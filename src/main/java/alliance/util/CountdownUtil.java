package alliance.util;

import java.sql.Time;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/* Handle the count down in ExamCountdownTable */
public class CountdownUtil {

	public CountdownUtil() {}
	
	public  String getCountdownFunc(Time time){
		String a= getCountdown(timeConvert(time));
		return a;
		}
	private  Calendar timeConvert(Time time){ 
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(time);
		  return cal;
		}
	/**
	 * Calculate the difference between two dates
	 * @param date1		Time now
	 * @param date2		Target time
	 * @param timeUnit	TimeUnit to convert to
	 * @return
	 */
	private  long getDateDiff(Calendar date1, Calendar date2, TimeUnit timeUnit) {
	    long diffInMillies = date2.getTimeInMillis() - date1.getTimeInMillis();
	    if (diffInMillies<= 0)
	    	return 0;
	    else
	    	return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}
	
	/**
	 * Calculates how much time until target date
	 * @param target	Target date
	 * @return			The time left until target date in String format
	 */
	private  String getCountdown(Calendar target){
		long secs = getDateDiff(Calendar.getInstance(),target,TimeUnit.SECONDS) % 60;
		long mins = getDateDiff(Calendar.getInstance(),target,TimeUnit.SECONDS) /60 %60;
		long hrs = getDateDiff(Calendar.getInstance(),target,TimeUnit.SECONDS) /60 /60 %24;
		long days = getDateDiff(Calendar.getInstance(),target,TimeUnit.SECONDS) /60 /60 /24;
		if ((secs <= 0)&(mins==0)&(hrs==0)&(days==0))
			return "Take Exam Now";
		else if((mins==0)&(hrs==0)&(days==0))
			return String.format("%02d", secs);
		else if((hrs==0)&(days==0))
			return (String.format("%02d",mins)+" : "+String.format("%02d", secs));
		else if(days==0)
			return (String.format("%02d", hrs)+" : "+String.format("%02d",mins)+" : "+String.format("%02d", secs));
		else
			return (String.format("%02d", days)+" : "+String.format("%02d", hrs)+" : "+String.format("%02d", mins)+" : "+String.format("%02d", secs));
	}
}
