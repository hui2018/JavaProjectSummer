package edu.pdx.cs410J.hui2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.io.File;

/**
 * This class is to print the customer's phone bill to the command line and to the text file
 */
public class PrettyPrinter implements PhoneBillDumper {
    /**
     * @param filePath this is to store the string that is pass in from the command line
     * @param fileLocation this is to store file Location by passing in the file path string
     */
    String filePath;
    File fileLocation;

    /**
     *
     * @param filePath Stores the string of file path
     */
    PrettyPrinter(String filePath)
    {
        this.filePath = filePath;
        this.fileLocation = new File(filePath);
    }

    /**
     * This function is to get the duration between start time and end time
     * @param startTime The start time of the phone call
     * @param endTime the end time of the phone call
     */
    public void getDuration(Date startTime, Date endTime)
    {
        long duration;
        //duration = startTime.getTime() - endTime.getTime();
        duration = endTime.getTime() -startTime.getTime();
        long minutes;
        minutes = duration / (60*1000)%60;
        long hour;
        hour = duration /(60*60*1000)%24;


        if(hour == 0)
            System.out.println(minutes);
        else
            System.out.println(hour + ":" + minutes);


    }

    /**
     * this function is to dump the phone bill onto the command line, but also to run the function to write to
     * text file
     * @param bill  all of the phone bill that are stored
     * @throws IOException  If we can't write to file
     */

    @Override
    public void dump(AbstractPhoneBill bill) throws IOException {

        ArrayList<PhoneCall> phoneCallsTest = (ArrayList<PhoneCall>) bill.getPhoneCalls();
        Date startTime;
        Date endTime;
        System.out.println("Customer name: " + bill.getCustomer() +"\n");
        System.out.println("Caller's Phone number   " + "Callee's phone number         " + "Start Time                  " +
        "End Time                " + "Duration (hh:mm)");
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
        String endDate;
        String startDate;
        for(int i = 0; i< phoneCallsTest.size(); ++i) {
            startDate = dateFormat.format(phoneCallsTest.get(i).getStartTime());
            endDate = dateFormat.format(phoneCallsTest.get(i).getEndTime());

            startTime = phoneCallsTest.get(i).getStartTime();
            endTime = phoneCallsTest.get(i).getEndTime();
            System.out.print("     "+phoneCallsTest.get(i).getCaller() + "            " + phoneCallsTest.get(i).getCallee()
                    + "         " + startDate + "         " + endDate + "             ");
            getDuration(startTime, endTime);
        }
        dumpToFile(bill);
    }

    /**
     * This function is to dump the phone bill information onto the text file. It will check if file exist or not
     * so it can decide if it will create text file. Then we just print pretty to the text file
     * @param bill      all of the phone bill that are stored
     * @throws IOException  Check if we can write to file
     */
    public void dumpToFile(AbstractPhoneBill bill) throws IOException
    {
        String endDate;
        String startDate;
        Date startTime;
        Date endTime;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
        BufferedWriter bw = null;
        //FileWriter fw = null;
        ArrayList<PhoneCall> phoneCallsTest = (ArrayList<PhoneCall>) bill.getPhoneCalls();
        if(!fileLocation.exists())
            fileLocation.createNewFile();
        bw = new BufferedWriter(new FileWriter(filePath, true));
        bw.write("Customer name: " + bill.getCustomer());
        bw.newLine();
        bw.write("Caller's Phone number   " + "Callee's phone number         " + "Start Time                  " +
                "End Time                " + "Duration (hh:mm)");
        bw.newLine();
        for(int i = 0; i<phoneCallsTest.size(); ++i)
        {
            startDate = dateFormat.format(phoneCallsTest.get(i).getStartTime());
            endDate = dateFormat.format(phoneCallsTest.get(i).getEndTime());

            startTime = phoneCallsTest.get(i).getStartTime();
            endTime = phoneCallsTest.get(i).getEndTime();
            bw.write("     "+phoneCallsTest.get(i).getCaller() + "            " + phoneCallsTest.get(i).getCallee()
                    + "         " + startDate + "         " + endDate + "             ");
            bw.newLine();
        }

        bw.flush();
        bw.close();
    }
}
