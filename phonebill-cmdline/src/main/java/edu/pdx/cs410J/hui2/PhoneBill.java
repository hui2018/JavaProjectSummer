package edu.pdx.cs410J.hui2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.*;

/**
 * A phone bill class that contains the customer name and multiple phone calls
 */
public class PhoneBill extends AbstractPhoneBill{
    //ArrayList<AbstractPhoneCall> phoneCalls;
    ArrayList<PhoneCall> phoneCalls;
    private String customer;
    ArrayList<PhoneCall> test = new ArrayList<>();

    /**
     * The constructor will set the customer name and
     * @param customer customer name
     * @param phoneCall a phone call that contains different variables
     */
    PhoneBill(String customer, PhoneCall phoneCall)
    {
        this.customer = customer;
        //phoneCalls = new ArrayList<AbstractPhoneCall>();
        phoneCalls = new ArrayList<PhoneCall>();
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
        phoneCalls.add((PhoneCall) addCall);

    }

    /**
     *
     * @return returns a list of all the phone calls from one customer
     */
    @Override
    public Collection getPhoneCalls() {
        //System.out.println(phoneCalls);
        //System.out.println("\n\n\n");
        Collections.sort(phoneCalls);
        //System.out.println(phoneCalls);
        return phoneCalls;
    }

}
