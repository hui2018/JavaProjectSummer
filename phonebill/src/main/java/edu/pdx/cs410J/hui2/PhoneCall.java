package edu.pdx.cs410J.hui2;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PhoneCall extends AbstractPhoneCall {
  private String callerNumber;
  private String calleeNumber;
  private String startTime;
  private String endTime;

  PhoneCall(String callerNumber, String calleeNumber, String startTime, String endTime)
  {
    this.callerNumber = callerNumber;
    this.calleeNumber = calleeNumber;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  @Override
  public String getCaller() {

    //throw new UnsupportedOperationException("This method is not implemented yet");
    return callerNumber;
  }

  @Override
  public String getCallee() {
    //return "This method is not implemented yet";
    return calleeNumber;
  }

  @Override
  public String getStartTimeString() {
    //throw new UnsupportedOperationException("This method is not implemented yet");
    return startTime;
  }

  @Override
  public String getEndTimeString() {
    //throw new UnsupportedOperationException("This method is not implemented yet");
    return endTime;
  }
/*
  public static Date formatter(String time){
    SimpleDateFormat startFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    Date date = null;
    try {
      date = startFormat.parse(time);
      return date;
    } catch (ParseException e) {
      e.printStackTrace();
      System.exit(1);
    }
    return null;
  }
  */
}
