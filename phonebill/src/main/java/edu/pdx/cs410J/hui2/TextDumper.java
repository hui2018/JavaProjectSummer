package edu.pdx.cs410J.hui2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The class that will implement the fucntions of writing the command line arguments to the text file
 */
public class TextDumper implements PhoneBillDumper {
    String fileName;
    String customerName;
    String callerNumber;
    String calleeNumber;
    String startTime;
    String endTime;
    AbstractPhoneCall newPhoneCall;
    AbstractPhoneBill totalPhoneBill;

    /**
     * This function is to write the information form the command line into the text file. It will also
     * check if the object is null, if it is then we want to create a new object of phonebill and store
     * the information.
     * @param bill the phonebill that pass in from the command line
     * @throws IOException it will throw an exception if the file location doesn't exit to create the file name
     */
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

    /**
     * gets the file name
     * @param fileName store the file name
     */
    public void getFile(String fileName)
    {
        this.fileName = fileName;
    }

    /**
     * a constructor to take in the variables
     * @param customer string contains customer name
     * @param caller string contains the caller's phone number
     * @param callee string contains the callee's phone number
     * @param startTime string contains the start date and time
     * @param endTime string contains the end date and time
     */
    TextDumper(String customer, String caller, String callee, String startTime, String endTime)
    {
        this.customerName = customer;
        this.callerNumber = caller;
        this.calleeNumber = callee;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     *
     * @return it returns the phonebill
     */
    public AbstractPhoneBill returnPhoneBill()
    {
        return totalPhoneBill;
    }
}
