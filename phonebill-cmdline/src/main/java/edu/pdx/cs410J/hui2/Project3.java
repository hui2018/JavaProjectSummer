package edu.pdx.cs410J.hui2;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.Stack;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Locale;

import java.util.Collection;

/**
 * Created by Hui Yu Sim
 * Project 1
 * The main class for the CS410J Phone Bill Project
 */
public class Project3 {
  private static boolean printOpt = false;  // We are to check if we need to print out the information at the end
  private static boolean textParseDump = false; //checking if textfile is in commandline
  private static boolean prettyPrint = false;  //check if we are printing the phonebill
  public static String fileName; //Store the file name
  public static String fileNamePretty; //Store the file name to print pretty to
  public static AbstractPhoneBill oldPhoneBill;
  public static AbstractPhoneCall oldPhoneCall;
  public static void main(String[] args) {
    checkReadMe(args);  //check readMe first

    ArrayList listOfArgs = new ArrayList<String>(Arrays.asList(args));
    listOfArgs = removeOption(listOfArgs); // remove all options from the argument
    checkArgs(listOfArgs); // check the number of arguments
    String customerName = (String) listOfArgs.get(0);
    String callerNumber = (String) listOfArgs.get(1);
    String calleeNumber = (String) listOfArgs.get(2);
    String startTime = (String) listOfArgs.get(3) + " " + (String) listOfArgs.get(4) + " " + (String) listOfArgs.get(5);
    String startTimeLabel = (String) listOfArgs.get(5);
    String endTime = (String) listOfArgs.get(6) + " " + (String) listOfArgs.get(7)+ " " + (String) listOfArgs.get(8);
    String endTimeLabel = (String) listOfArgs.get(8);

    checkName(customerName);
    checkCallerPhone(callerNumber);
    checkCalleePhone(calleeNumber);
    checkStartTime((String) listOfArgs.get(3), (String) listOfArgs.get(4));
    checkEndTime((String) listOfArgs.get(6), (String) listOfArgs.get(7));
    checkTimeLabel(startTimeLabel, endTimeLabel);

    formatter(startTime, endTime);

    if(textParseDump)
    {
      TextParser parser = new TextParser();
      parser.getFile(fileName, (String) listOfArgs.get(0));
      //check if file exist of not
      if(parser.checkFile())
      {
        oldPhoneBill = parser.parse();
      }
      //create empty PhoneBill new file with that command line
      else
      {
        System.out.println("File does not exist, creating new file");
        parser.createFile(fileName);
      }
      TextDumper dumper = new TextDumper(customerName, callerNumber, calleeNumber, startTime, endTime);
      dumper.getFile(fileName);
      try {
          dumper.dump(oldPhoneBill);
          oldPhoneBill = dumper.returnPhoneBill();
      } catch (IOException e) {
            e.printStackTrace();
        }
    }

    PhoneBill bill = new PhoneBill(customerName, new PhoneCall(callerNumber, calleeNumber, startTime, endTime));
    if(prettyPrint) {
      PrettyPrinter pretty = new PrettyPrinter(fileNamePretty);
      try {
        pretty.dump(oldPhoneBill);

      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    if(printOpt)   //this will be at the very end
      printInfo(bill);
    System.exit(1);
  }

  /**
   * THis is to check that the time label can only be am or pm
   * @param startLabel  Start time's am or pm
   * @param endLabel  end time's am or pm
   */
    public static void checkTimeLabel(String startLabel, String endLabel)
    {
      if(!startLabel.contains("am"))
      {
        if(!startLabel.contains("pm"))
          System.out.println("1 Failed");
      }
      else if(!startLabel.contains("pm"))
      {
        if(!startLabel.contains("am"))
          System.err.println("2 failed");
      }
      else if (!endLabel.contains("am"))
      {
        if(!endLabel.contains("pm"))
          System.err.println("3 end failed");
      }
      else if(!endLabel.contains("pm"))
      {
        if(!endLabel.contains("am"))
          System.err.println("4 end failed");
      }
    }

    /**
     * this function is to format the date so that we can use it to compare the values between start and end time
     * @param startTime String that contains the start date and time
     * @param endTime String that contains the end date and time
     */
    public static void formatter(String startTime, String endTime){
        SimpleDateFormat startFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
        try {
            Date start = startFormat.parse(startTime);
            Date end = startFormat.parse(endTime);
            if(start.compareTo(end) > 0)
            {
                  System.err.println("Start time is after end time, please modified the date/time");
                  System.exit(1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
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
      System.err.println("Invalid customer name");
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
        System.err.println("Invalid caller phone number");
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
        System.err.println("Invalid callee phone number");
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
        System.err.println("Invalid start date");
        System.exit(1);
      }
      else if(!startTime.matches("([0]?[1-9]|1[0-2]):[0-5][0-9]"))
      {
        System.err.println("Invalid start time");
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
        System.err.println("Invalid end date");
        System.exit(1);
      }
      else if(!endTime.matches("([0]?[1-9]|1[0-2]):[0-5][0-9]"))
      {
        System.err.println("Invalid end time");
        System.exit(1);
      }
    }

  /**
   * Here we are removing the option arguments because they can appear anywhere on the command line,
   * so it will be easier for us to get the right arguments. We want to then turn the print option on
   * with a boolean so that it will just run the print function if -print is found on the command line
   * @param arrayList All of the array of arguments from the command line
   * @return It will return the array list without the -print command
   */
  public static ArrayList removeOption(ArrayList arrayList)
  {
    if(arrayList.contains("-print"))
    {
      arrayList.remove(arrayList.indexOf("-print"));
      printOpt = true;
    }
    if(arrayList.contains("-textFile"))
    {
      int counter = 0;
      counter = arrayList.indexOf("-textFile");
      arrayList.remove(arrayList.indexOf("-textFile"));
      fileName = (String) arrayList.get(counter);
      //System.out.println(fileName);
      arrayList.remove(arrayList.get(counter));
      textParseDump = true;
    }
    if(arrayList.contains("-pretty"))
    {
      int counter = 0;
      counter = arrayList.indexOf("-pretty");
      arrayList.remove(arrayList.indexOf("-pretty"));
      fileNamePretty = (String) arrayList.get(counter);
      arrayList.remove(arrayList.get(counter));
      //System.out.println(fileNamePretty);
      prettyPrint = true;
    }
    return arrayList;
  }

  /**
   * we are checking all of the arguments conditions that are possible
   * The first condition is to check if the argument size is 0
   * The second condition is to check if the argument size is greater than 7 the reason is because
   * start time and end time consists of two arguments each so that the total argument is 7
   * The last condition is to make sure that there is a total of 7 arguments.
   * @param numArgs array list without the print option
   */
  public static void checkArgs(ArrayList numArgs)
  {
    if(numArgs.size() == 0) {
      System.err.println("Missing command line arguments");
      System.exit(1);
    }
    else if(numArgs.size() > 9) {
      System.err.println("Too many arguments");
      System.exit(1);
    }
    else if(numArgs.size() != 9)
    {
      System.err.println("Not enough arguments");
      System.exit(1);
    }

  }
  /**
   * We will print out the information if the -print option is set in the command line
   * @param customer object that contains the customer information
   */
  public static void printInfo(PhoneBill customer)
  {
      System.out.println("Custom: " + customer.getCustomer() + "\n" + customer.getPhoneCalls());
  }

  /**
   * This function is to check if any of the arguments on the command line contains -README
   * and if it does, then we want to just run the readMe function and exit out of the program
   * @param args list of arguments from the command line
   */
  public static void checkReadMe(String[]args)
  {
    for(int i = 0; i<args.length; ++i)
    {
      if (args[i].equals("-README"))
      {
        readMe();
        System.exit(1);
      }
    }
  }

  /**
   * This function will print out the project information and what is required of the user in order to get th
   * program to run successfully.
   */
  static public void readMe()
  {
    System.out.println("Name: Hui Yu Sim \nProject: 1 Designing a Phone Bill Application\n\n" +
            "The Purpose of this programming assignment is to create a phone bill that\n" +
            "has customer name and consists of multiple phone calls.\n" +
            "The program will take in arguments from the command line and check if the arguments are correct.\n" +
            "Please follow the following steps to insure program will run correctly.\n");
    System.out.println("usage: java edu.pdx.cs410J.<login-id>.Project2 [options] <args>\n" +
            "args are (in this order):\n" +
              "\tcustomer           Person whose phone bill we’re modeling\n" +
              "\tcallerNumber       Phone number of caller\n" +
              "\tcalleeNumber       Phone number of person who was called\n" +
              "\tstartTime          Date and time call began (24-hour time)\n" +
              "\tendTime            Date and time call ended (24-hour time)\n" +
            "options are (options may appear in any order):\n" +
              "\t-pretty file       Pretty print the phone bill to a text file" +
                                    " or standard out (file -)\n" +
              "\t-textFile file     Where to read/write the phone bill\n" +
              "\t-print             Prints a description of the new phone call\n" +
              "\t-README            Prints a README for this project and exits\n" +
            "Date and time should be in the format: mm/dd/yyyy hh:mm");
  }
}