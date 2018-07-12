package edu.pdx.cs410J.hui2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TextDumper implements PhoneBillDumper {
    String fileName;
    String customerName;
    String callerNumber;
    String calleeNumber;
    String startTime;
    String endTime;
    AbstractPhoneCall newPhoneCall;
    AbstractPhoneBill totalPhoneBill;
    //File filePath = null;
    @Override
    public void dump(AbstractPhoneBill bill) throws IOException
    {
        AbstractPhoneBill phonebill = null;
        BufferedWriter bw = null;
        FileWriter fw = null;
        bw = new BufferedWriter(new FileWriter(fileName, true));
        bw.write(customerName + "," + callerNumber + "," + calleeNumber + "," + startTime + "," + endTime);
        bw.newLine();
        bw.flush();
        if(bw!=null)
            bw.close();
        if(bill == null) {
            phonebill = new PhoneBill(customerName, new PhoneCall(callerNumber, calleeNumber, startTime, endTime));
            bill = phonebill;
        }
        else {
            newPhoneCall = new PhoneCall(callerNumber, calleeNumber, startTime, endTime);
            bill.addPhoneCall(newPhoneCall);
        }
        totalPhoneBill = bill;

    }
    public void getFile(String fileName)
    {
        this.fileName = fileName;
        //filePath = new File(fileName);
    }
    TextDumper(String customer, String caller, String callee, String startTime, String endTime)
    {
        this.customerName = customer;
        this.callerNumber = caller;
        this.calleeNumber = callee;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public AbstractPhoneBill returnPhoneBill()
    {
        return totalPhoneBill;
    }
}
