package edu.pdx.cs410J.hui2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

public class PhoneBill extends AbstractPhoneBill{
    //Collection<PhoneCall> calls = new ArrayList<>();
    List<AbstractPhoneCall> phoneCalls;
    String customer;

    PhoneBill(String customer, PhoneCall phoneCall)
    {
        this.customer = customer;
        phoneCalls = new ArrayList<AbstractPhoneCall>();
        addPhoneCall(phoneCall);
    }

    public PhoneBill() {

    }

    @Override
    public String getCustomer() {
        //return null;
        return customer;
    }

    @Override
    public void addPhoneCall(AbstractPhoneCall abstractPhoneCall) {
        phoneCalls.add(abstractPhoneCall);
    }

    @Override
    public Collection getPhoneCalls() {
        //return null;
        return phoneCalls;
    }
}
