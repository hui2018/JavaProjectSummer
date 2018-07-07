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

    /**
     * Constructor that takes in the phone number and time of the phone call
     * @param callerNumber  The phone number of the caller
     * @param calleeNumber  The phone number of the callee
     * @param startTime     The start date and time of the call
     * @param endTime       The end date and time of the call
     */
  PhoneCall(String callerNumber, String calleeNumber, String startTime, String endTime)
  {
    this.callerNumber = callerNumber;
    this.calleeNumber = calleeNumber;
    this.startTime = startTime;
    this.endTime = endTime;
  }

    /**
     *
     * @return it returns the caller's phone number
     */
  @Override
  public String getCaller() {
    return callerNumber;
  }

    /**
     *
     * @return it returns the callee's phone number
     */
  @Override
  public String getCallee() {
    return calleeNumber;
  }

    /**
     *
     * @return It returns the start date and time
     */
  @Override
  public String getStartTimeString() {
    return startTime;
  }

    /**
     *
     * @return It returns the end date and time 
     */
  @Override
  public String getEndTimeString() {
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
