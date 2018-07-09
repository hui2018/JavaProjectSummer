package edu.pdx.cs410J.hui2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.*;

import java.io.FileReader;

public class TextParser implements PhoneBillParser {
    String fileName = null;
    File filePath = null;
    private String commandCustomerName = null;
    String [] list;
    @Override
    public AbstractPhoneBill parse() {
        AbstractPhoneBill phoneBill = null;
        try
        {
            FileReader fRead = new FileReader(fileName);
            BufferedReader bRead = new BufferedReader(fRead);
            String check = "";
            String splitby = ",";
            String customerName = "";

            boolean firstCheck = true;
            while((check = bRead.readLine()) != null)
            {
                list = check.split(splitby);
                checkFileName(list[0]);
                checkFileInfo(list);

                if(firstCheck)
                {
                    firstCheck = false;
                    phoneBill = new PhoneBill(list[0], new PhoneCall(list[1],list[2],list[3],list[4]));
                }
                else
                {
                    phoneBill.addPhoneCall(new PhoneCall(list[1], list[2], list[3], list[4]));
                }

            }

        }
        catch (FileNotFoundException e) {
            System.out.println("error");
            e.printStackTrace();
        }
        catch (IOException e) {
            System.out.println("error2");
            e.printStackTrace();
        }
        return phoneBill;
    }
    public void getFile(String fileName, String customerName)
    {
        this.fileName = fileName;
        filePath = new File(fileName);
        this.commandCustomerName = customerName;
    }
    public boolean checkFile()
    {
        if(filePath.exists())
        {
            return true;
        }
        return false;
    }
    public void checkFileName(String fileCustomerName)
    {
        if(!fileCustomerName.equals(commandCustomerName))
        {
            System.err.println("The customer name from file does not match with the input name");
            System.exit(1);
        }
    }

    /**
     * Check that the customer name can only contain letters and no numbers or symbols.
     * The name will also allow double quote and a space so that it can have a full name
     * when entering the customer name.
     * @param customerName  Name of the customer stored in a string
     */
    private static void checkName(String customerName)
    {
        if(!customerName.matches("[a-z A-Z]+"))
        {
            System.err.println("Invalid customer name in the text file");
            System.exit(1);
        }
    }

    /**
     * We want to check if the phone number of caller is in the correct format
     * if the format is incorrect then we just want to tell the user that
     * the callee phone number is invalid.
     * @param phoneNumber caller's phone number that is stored in a string and needs to be
     *                    in a format of nnn-nnn-nnnn
     */
    private static void checkCallerPhone(String phoneNumber)
    {
        if(!phoneNumber.matches("\\d{3}-\\d{3}-\\d{4}$"))
        {
            System.err.println("Invalid caller phone number in text file");
            System.exit(1);
        }
    }

    /**
     * We want to check if the phone number of callee is in the correct format
     * if the format is incorrect then we just want to tell the user that
     * the callee phone number is invalid.
     * @param phoneNumber callee's phone number that is stored in a string and needs to be
     *                    in a format of nnn-nnn-nnnn
     */
    private static void checkCalleePhone(String phoneNumber)
    {
        if(!phoneNumber.matches("\\d{3}-\\d{3}-\\d{4}$"))
        {
            System.err.println("Invalid callee phone number in text file");
            System.exit(1);
        }
    }

    /**
     * In this function we are checking if the start date and start time is in the format that we want.
     * If the format is not what we want then we want to tell the user what the problem is
     * then exit out of the program.
     * @param startDate start date that is stored in a string in the format of nn/nn/nnnn
     * @param startTime start time that is stored in a string in the format of nn:nn
     * Where the "n" are integers.
     */
    private static void checkStartTime(String startDate, String startTime)
    {
        if(!startDate.matches("((0?[1-9])|(1?[012]))/(0?[1-9]|[12][0-9]|3[01])/[0-9]{2}([0-9]{2})"))
        {
            System.err.println("Invalid start date in text file");
            System.exit(1);
        }
        else if(!startTime.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]"))
        {
            System.err.println("Invalid start time in text file");
            System.exit(1);
        }
    }

    /**
     * In this function we are checking if the end date and end time is in the format that we want.
     * If the format is not what we want then we want to tell the user what the problem is
     * then exit out of the program.
     * @param endDate end date that is stored in a string in the format of nn/nn/nnnn
     * @param endTime end time that is stored in a string in the format of nn:nn
     * Where the "n" are integers.
     */
    private static void checkEndTime(String endDate, String endTime)
    {
        if(!endDate.matches("((0?[1-9])|(1?[012]))/(0?[1-9]|[12][0-9]|3[01])/(19|20)([0-9]{2})"))
        {
            System.err.println("Invalid end date in text file");
            System.exit(1);
        }
        else if(!endTime.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]"))
        {
            System.err.println("Invalid end time in text file");
            System.exit(1);
        }
    }
    public static void checkFileInfo(String[] list)
    {
        String splitBySpace = " ";

        String startDate;
        String startTime;
        String endDate;
        String endTime;

        checkName(list[0]);
        checkCallerPhone(list[1]);
        checkCalleePhone(list[2]);

        //Check start date and time
        String [] startList = list[3].split(splitBySpace);
        startDate = startList[0];
        startTime = startList[1];
        //check end date and time
        String [] endList = list[4].split(splitBySpace);
        endDate = endList[0];
        endTime = endList[1];
        checkStartTime(startDate, startTime);
        checkEndTime(endDate, endTime);
    }
}
