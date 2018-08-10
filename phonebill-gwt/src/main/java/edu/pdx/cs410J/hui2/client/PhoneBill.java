package edu.pdx.cs410J.hui2.client;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.lang.Override;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A phone bill class that contains the customer name and multiple phone calls
 */
public class PhoneBill extends AbstractPhoneBill
{
  String customer;
  ArrayList<PhoneCall> phoneCalls;
  PhoneCall searchCallOnly;
  PhoneCall singlePhoneCall;

  /**
   * Constructor is essentially a setter function. creates a new list which will hold all additional phonecalls.
   * @param customer Name of the customer
   * @param phoneCall An instance of the phone call which took place.
   */
  public PhoneBill(String customer, PhoneCall phoneCall)
  {
    this.customer = customer;
    phoneCalls = new ArrayList<PhoneCall>();
    addPhoneCall(phoneCall);
    searchCallOnly=null;
    singlePhoneCall = null;
  }

  public PhoneBill() {
    customer = "";
    phoneCalls = new ArrayList<PhoneCall>();
    searchCallOnly = null;
    singlePhoneCall = null;
  }

  public PhoneBill(String customer)
  {
    this.customer = customer;
    phoneCalls = new ArrayList<PhoneCall>();
    searchCallOnly = null;
    singlePhoneCall = null;
  }

  /**
   *
   * @return Returns customer name - Getter function
   */
  @Override
  public String getCustomer() {
    return customer;
  }


  /**
   *
   * @param abstractPhoneCall Takes an instance of the phone call and adds it to the list
   */
  @Override
  public void addPhoneCall(AbstractPhoneCall abstractPhoneCall) {
    boolean addPhoneCall = true;
    for(AbstractPhoneCall call:phoneCalls){
      if(call.toString().equals(abstractPhoneCall.toString())) {
        addPhoneCall = false;
      }
    }
    if(addPhoneCall) {
      phoneCalls.add((PhoneCall)abstractPhoneCall);
    }

    Collections.sort(phoneCalls);
  }

  /**
   *
   * @return Returns a list of all phonecalls made - Getter function
   */
  @Override
  public Collection getPhoneCalls() {
    return phoneCalls;
  }

  public boolean exceptionWasThrown(){
    for(PhoneCall call: phoneCalls){
      if(call.exceptionWasThrown())
        return true;
    }
    return false;
  }
}
