package edu.pdx.cs410J.hui2.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.Map;
import java.util.List;
/**
 * A GWT remote service that returns a dummy Phone Bill. It will have three different functions, to add a new
 * or existing customer, serach for phone bill for specific customer and print all of the phone call for that customer
 */
@RemoteServiceRelativePath("addCustomer")
public interface PhoneBillService extends RemoteService {
  public void addCustomer(String customerName, PhoneCall call);
  public Map<String,PhoneBill> printAll();
  public List<PhoneCall> search(String customerName, String startTime, String endTime);

}
