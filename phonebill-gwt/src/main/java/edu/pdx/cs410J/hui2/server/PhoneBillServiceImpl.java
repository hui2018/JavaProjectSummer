package edu.pdx.cs410J.hui2.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.hui2.client.PhoneBill;
import edu.pdx.cs410J.hui2.client.PhoneCall;
import edu.pdx.cs410J.hui2.client.PhoneBillService;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DefaultDateTimeFormatInfo;
import java.util.HashMap;
import java.util.List;
import java.util.*;
import java.util.Map;

/**
 * The server-side implementation of the Phone Bill service
 */
public class PhoneBillServiceImpl extends RemoteServiceServlet implements PhoneBillService
{
  private final Map<String, PhoneBill> data = new HashMap<String, PhoneBill>();
  PhoneBill bill;
  Collection<PhoneCall> phoneCalls = null;

  /**
   * This function is to add the customer to their existing or add it to a new phone call
   * @param customer the customer name
   * @param call the parameters of all the phone calls
   */
  @Override
  public void addCustomer(String customer, PhoneCall call) {
    if(data.get(customer)!=null){
      bill=data.get(customer);
      bill.addPhoneCall(call);
      data.put(customer, bill);
    }
    else{
      bill= new PhoneBill(customer,call);
      data.put(customer, bill);
    }
  }

  /**
   * It will just return all of the phone bill
   * @return the phonebill
   */
  @Override
  public Map<String, PhoneBill> printAll() {
    return data;
  }

  /**
   * This function is to search for a specific time of all the phone calls for that customer
   * @param customer the customer name
   * @param startTime the start time
   * @param endTime the end time
   * @return it will return the list of phone call
   */
  @Override
  public List<PhoneCall> search(String customer, String startTime, String endTime) {
    DefaultDateTimeFormatInfo formatInfo = new DefaultDateTimeFormatInfo();
    DateTimeFormat ShortDateFormat = new DateTimeFormat("MM/dd/yyy hh:mm a",formatInfo){};
    Long begin;
    Long end;
    Date startDate;
    Date endDate;
    try {
      startDate = ShortDateFormat.parse(startTime.trim());
      begin = startDate.getTime();
      endDate = ShortDateFormat.parse(endTime.trim());
      end = endDate.getTime();
    }
    catch(Exception ex){
      System.out.println("Error Parsing "+ ex);
      return null;
    }

    List<PhoneCall> data = new ArrayList<PhoneCall>();
    if(this.data.isEmpty())
      return null;

    bill = this.data.get(customer);
    if(bill == null)
      return null;
    Collection<PhoneCall>calls = this.data.get(customer).getPhoneCalls();

    for(PhoneCall call: calls){
      if(begin>(Long)call.startTime.getTime())
      {}
      else if((Long)call.startTime.getTime() > end)
      {}
      else
        data.add(call);
    }
    return data;
  }
}
