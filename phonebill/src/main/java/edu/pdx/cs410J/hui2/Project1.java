package edu.pdx.cs410J.hui2;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import edu.pdx.cs410J.AbstractPhoneBill;
import java.util.Stack;

import java.util.Collection;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {

  private static boolean printOpt = false;
  public static void main(String[] args) {
    checkReadMe(args);  //check readMe first

    ArrayList listOfArgs = new ArrayList<String>(Arrays.asList(args));
    checkPrint(args);   //check if -print exist
    listOfArgs = removeOption(listOfArgs); // remove all options from the argument
    checkArgs(listOfArgs); // check the number of arguments

    String customerName = (String) listOfArgs.get(0);
    String callerNumber = (String) listOfArgs.get(1);
    String calleeNumber = (String) listOfArgs.get(2);
    String startTime = (String) listOfArgs.get(3) + " " + (String) listOfArgs.get(4);
    String endTime = (String) listOfArgs.get(5) + " " + (String) listOfArgs.get(6);


    PhoneBill bill = new PhoneBill(customerName, new PhoneCall(callerNumber, calleeNumber, startTime, endTime));

    if(printOpt)   //this will be at the very end
      printInfo(bill);

    //Collection<PhoneCall> phoneCalls = bill.getPhoneCalls();


    System.exit(1);
  }

  /**
   * Here we are removing the option arguments because they can appear anywhere on the command line.
   * So it will be easier for us to get the right arguments
   * @param arrayList
   * @return
   */
  public static ArrayList removeOption(ArrayList arrayList)
  {
    if(arrayList.contains("-print"))
    {
      printOpt = true;
      arrayList.remove(arrayList.indexOf("-print"));
    }
    return arrayList;
  }

  /**
   * we are checking all of the arguments conditions that are possible
   * The first condition is to check if the argument size is 0
   * The second condition is to check if the argument size is greater than 7 the reason is because
   * start time and end time consists of two arguments each so that the total argument is 7
   * The last condition is to make sure that there is a total of 7 arguments.
   * @param numArgs
   */
  public static void checkArgs(ArrayList numArgs)
  {
    if(numArgs.size() == 0) {
      System.err.println("Missing command line arguments");
      System.exit(1);
    }
    else if(numArgs.size() > 7) {
      System.err.println("Too many arguments");
      System.exit(1);
    }
    else if(numArgs.size() != 7)
    {
      System.err.println("Not enough arguments");
      System.exit(1);
    }

  }

  /**
   * we want to check if the -print is the first argument in command line.
   * Since we are checking if -README is in the argument it will just exit without problem.
   * So, we can just check the first argument in the command line and force user to type it in the first
   * command line so that it doesn't messed up the argument.
   * @param numArgs
   */
  public static void checkPrint(String[] numArgs)
  {
    for(int i = 0; i<numArgs.length; ++i)
    {
      if (numArgs[i].equals("-print"))
      {
        printOpt = true;
      }
    }
  }
  public static void printInfo(PhoneBill customer)
  {
      System.out.println("Custom: " + customer.getCustomer() + "\n" + customer.getPhoneCalls());
  }

  /**
   * These functions are to check if any of the arguments on the command line contains -README
   * and if it does, then we want to just run the readMe function and exit out of the program
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

  static public void readMe()
  {
    System.out.println("usage: java edu.pdx.cs410J.hui2.Project1 [options] <args>\n" +
            "args are (in this order):\n" +
              "\tcustomer           Person whose phone bill we’re modeling\n" +
              "\tcallerNumber       Phone number of caller\n" +
              "\tcalleeNumber       Phone number of person who was called\n" +
              "\tstartTime          Date and time call began (24-hour time)\n" +
              "\tendTime            Date and time call ended (24-hour time)\n" +
            "options are (options may appear in any order):\n" +
              "\t-print             Prints a description of the new phone call\n" +
              "\t-README            Prints a README for this project and exits\n" +
            "Date and time should be in the format: mm/dd/yyyy hh:mm");
  }
}