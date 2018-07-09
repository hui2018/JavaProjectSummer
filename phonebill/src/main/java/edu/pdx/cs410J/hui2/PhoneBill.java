package edu.pdx.cs410J.hui2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

/**
 * A phone bill class that contains the customer name and multiple phone calls
 */
public class PhoneBill extends AbstractPhoneBill{
    List<AbstractPhoneCall> phoneCalls;
    private String customer;

    /**
     * The constructor will set the customer name and
     * @param customer customer name
     * @param phoneCall a phone call that contains different variables
     */
    PhoneBill(String customer, PhoneCall phoneCall)
    {
        this.customer = customer;
        phoneCalls = new ArrayList<AbstractPhoneCall>();
        addPhoneCall(phoneCall);
    }
    PhoneBill()
    {
        }

    /**
     *
     * @return It returns the customer name
     */
    @Override
    public String getCustomer() {
        return customer;
    }

    /**
     *
     * @param addCall it takes a phone call and adds it to the list of phone call
     */
    @Override
    public void addPhoneCall(AbstractPhoneCall addCall) {
        phoneCalls.add(addCall);
    }

    /**
     *
     * @return returns a list of all the phone calls from one customer
     */
    @Override
    public Collection getPhoneCalls() {
        return phoneCalls;
    }
}
