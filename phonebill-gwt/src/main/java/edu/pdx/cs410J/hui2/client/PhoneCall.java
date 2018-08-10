package edu.pdx.cs410J.hui2.client;

import com.google.gwt.i18n.client.DefaultDateTimeFormatInfo;
import edu.pdx.cs410J.AbstractPhoneCall;
import com.google.gwt.user.client.Window;
import com.google.gwt.i18n.client.DateTimeFormat;
import java.util.Date;

public class PhoneCall extends AbstractPhoneCall implements  Comparable<PhoneCall>{
  String callerNumber;
  String calleeNumber;
  public Date startTime;
  public Date endTime;
  private boolean flag;

  /**
   * Constructor for the phonecall class. Holds all relavent data for a particular phonecall
   * @param callerNumber The phone number of the customer
   * @param calleeNumber The phone number that the customer is trying to reach
   * @param startTime The time at which the phonecall began
   * @param endTime The time at which the phonecall ended
   */
  public PhoneCall(String callerNumber, String calleeNumber, String startTime, String endTime){
    this.callerNumber = callerNumber;
    this.calleeNumber = calleeNumber;
    setDate(startTime,endTime);
  }

  /**
   * empty
   */
  public PhoneCall() {
    flag = false;
    try{
      calleeNumber = "";
      callerNumber = "";
      startTime = null;
      endTime = null;
    }
    catch(Exception ex)
    {
      Window.alert(ex.getMessage());
      flag = true;
      return;
    }
  }


  /**
   * set the date of start time and end time
   * @param start the start time in Date
   * @param end the end time in Date
   */
  public void setDate(String start, String end){
    flag = false;
    DefaultDateTimeFormatInfo formatInfo = new DefaultDateTimeFormatInfo();
    DateTimeFormat ShortDateFormat = new DateTimeFormat("MM/dd/yyy hh:mm a",formatInfo){};
    try {
      this.startTime=ShortDateFormat.parse(start);
      this.endTime = ShortDateFormat.parse(end);
    }
    catch(Exception ex){
      Window.alert("Error Parsing the time, please enter valid time, dont forget to include am/pm " + ex.getMessage());
      flag = true;
      return;
    }
  }

  /**
   * check exception
   * @return return true or false of the flag
   */
  public boolean exceptionWasThrown(){
    return flag;
  }

  /**
   *
   * @return Returns caller number
   */
  @Override
  public String getCaller() {
    return callerNumber;
  }

  /**
   *
   * @return returns the callee number
   */
  @Override
  public String getCallee() {
    return calleeNumber;
  }

  /**
   *
   * @return get the string of start time
   */
  @Override
  public String getStartTimeString() {
    DefaultDateTimeFormatInfo formatInfo = new DefaultDateTimeFormatInfo();
    DateTimeFormat ShortDateFormat = new DateTimeFormat("MM/dd/yyy hh:mm a",formatInfo){};
    if(startTime != null)
      return (ShortDateFormat.format(startTime));
    else
      return "";
  }

  /**
   *
   * @return get the string of end time
   */
  @Override
  public String getEndTimeString() {
    DefaultDateTimeFormatInfo formatInfo = new DefaultDateTimeFormatInfo();
    DateTimeFormat ShortDateFormat = new DateTimeFormat("MM/dd/yyy hh:mm a",formatInfo){};
    if(endTime != null)
      return (ShortDateFormat.format(endTime));
    else
      return "";
  }

  /**
   * calculate the duration of the phone call
   * @return return the duration of phone call
   */
  public String duration(){
    long duration;
    duration = endTime.getTime() -startTime.getTime();
    long minutes;
    minutes = duration / (60*1000)%60;
    long hour;
    hour = duration /(60*60*1000)%24;
    long days;
    days = duration /(1000*60*60*24);
    return ""+days+":"+hour+":"+minutes;
  }

  /**
   * compare the phone call
   * @param o objec tof phonecalls
   * @return it will return a value depends on which phone call is earlier or later
   */
  @Override
  public int compareTo(PhoneCall o)
  {
    try {
      if (this.startTime == null) {
        throw new NullPointerException("No start time to compare");
      }
      if (o.startTime == null) {
        throw new NullPointerException("No end time to compare");
      }
      long diff = this.startTime.getTime()-o.startTime.getTime();

      if (diff > 0) {
        return 1;
      }
      if (diff < 0) {
        return -1;
      }
      if (diff == 0) {
        //equal - differ by caller number
        //String numberDiff = this.callerNumber-o.callerNumber;
        String callerStringA = this.getCaller();
        String callerStringB = o.getCaller();
        callerStringA = callerStringA.replaceAll("\\D", "");
        callerStringB = callerStringB.replaceAll("\\D", "");
        long numberDiff = Long.parseLong(callerStringA)-Long.parseLong(callerStringB);

        if(numberDiff >0){
          return 1;
        }
        if(numberDiff<0){
          return -1;
        }
        if(numberDiff == 0){
          return 0;
        }
      }

    }
    catch(Exception ex){
      Window.alert(ex.getMessage());
      return 0;

    }

    return 0;
  }
}
