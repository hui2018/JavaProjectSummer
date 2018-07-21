package edu.pdx.cs410J.hui2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.*;

import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * class that reads from the file
 */
public class TextParser implements PhoneBillParser {

    String fileName = null;
    File filePath = null;
    private String commandCustomerName = null;
    String [] list;
    AbstractPhoneBill phoneBill;

    /**
     * We are reading the file name and return the object of phonebill. We are doing a lot of checking in here,
     * such as the checking name, checking file, checking date, and checking phone. We are also storing line by line
     * into the string of array and split it by a comma that is contain in the file. With that comma in the file its
     * easier to split them and store them in the array list.
     * @return It returns the object of phonebill after it finish reading the text file
     */
    @Override
    public AbstractPhoneBill parse() {
        phoneBill = null;
        try
        {
            FileReader fRead = new FileReader(fileName);
            BufferedReader bRead = new BufferedReader(fRead);
            String check = "";
            String splitby = ",";
            String customerName = "";

            boolean firstCheck = true;
            while((check = bRead.readLine()) != null) {

                list = check.split(splitby);

                String splitBySpace = " ";

                String startDate;
                String startTime;
                String startLabel;
                String endDate;
                String endTime;
                String endLabel;

                if(list.length < 4)
                {
                    System.err.println("Bad information in text file");
                    System.exit(1);
                }
                checkFileName(list[0]);
                checkName(list[0]);

                checkCallerPhone(list[1]);
                checkCalleePhone(list[2]);

                //Check start date and time
                String[] startList = list[3].split(splitBySpace);
                startDate = startList[0];
                startTime = startList[1];
                startLabel = startList[2];

                //check end date and time
                String[] endList = list[4].split(splitBySpace);
                endDate = endList[0];
                endTime = endList[1];
                endLabel = endList[2];

                //System.out.println(list[3]);
                formatter(list[3], list[4]);
                checkStartTime(startDate, startTime);
                checkEndTime(endDate, endTime);
                //formatter(list[3], list[4]);

                if (firstCheck) {
                    firstCheck = false;
                    phoneBill = new PhoneBill(list[0], new PhoneCall(list[1], list[2], list[3], list[4]));
                } else {
                    phoneBill.addPhoneCall(new PhoneCall(list[1], list[2], list[3], list[4]));
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
        catch (IOException e) {
            System.out.println("File path not found");
            e.printStackTrace();
        }
        return phoneBill;
    }

    /**
     * This function is to store the file name and customer name into this constructor so that we
     * can check if file exist and if the customer name matches from command line and from the file
     * @param fileName  string that stores the file name from the command line
     * @param customerName string that stores the customer's name from the command line
     */
    public void getFile(String fileName, String customerName)
    {
        this.fileName = fileName;
        filePath = new File(fileName);
        this.commandCustomerName = customerName;
    }

    /**
     * we want to check if the file path exist or not. if it exist we return true if not false
     * so we can decide if we need to create a file for it or not.
     * @return If file exist return true else return false
     */
    public boolean checkFile()
    {
        if(filePath.exists())
        {
            return true;
        }
        return false;
    }

    /**
     * We want to create a new file if the file path that the user entered doesn't exist. I am making it
     * so that the path is already correct and I just create a new file namd with what was input in the first place
     * @param fileName The file name/path that is entered in the command line
     */
    public void createFile(String fileName)
    {
        File file = new File(fileName);
        try {
            if(file.createNewFile())
            {
                System.out.println("File successfully created");
            }
        } catch (IOException e) {
            System.err.println("File can not be created due to incorrect path");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * check the file name and the name that is entered from the command line is the same,
     * if its not the same we want to return an error
     * @param fileCustomerName The file customer's name to check with the customer's name that is input from command line
     */
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
        if(!customerName.matches("[a-z A-Z 0-9]+"))
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
        else if(!startTime.matches("([0]?[1-9]|1[0-2]):[0-5][0-9]"))
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
        else if(!endTime.matches("([0]?[1-9]|1[0-2]):[0-5][0-9]"))
        {
            System.err.println("Invalid end time in text file");
            System.exit(1);
        }
    }
    public static void formatter(String startTime, String endTime){
        SimpleDateFormat startFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
        try {
            Date start = startFormat.parse(startTime);
            Date end = startFormat.parse(endTime);
            if(start.compareTo(end) > 0)
            {
                System.err.println("Start time is after end time in the text file, please modified the date/time");
                System.exit(1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
