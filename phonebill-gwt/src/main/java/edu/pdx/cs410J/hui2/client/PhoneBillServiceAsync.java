package edu.pdx.cs410J.hui2.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.List;
import java.util.Map;

/**
 * The client-side interface to the phone bill service. It will have three different functions, to add a new
 * or existing customer, serach for phone bill for specific customer and print all of the phone call for that customer
 */
public interface PhoneBillServiceAsync {

  public void addCustomer(String customerName, PhoneCall newCall, AsyncCallback<Void>async);
  public void search(String customerName, String startTime, String endTime, AsyncCallback<List<PhoneCall>>async);
  public void printAll(AsyncCallback<Map<String,PhoneBill>>async);
}
