package edu.pdx.cs410J.hui2;

public class PrettyPrinter extends TextDumper {

    /**
     * a constructor to take in the variables
     *
     * @param customer  string contains customer name
     * @param caller    string contains the caller's phone number
     * @param callee    string contains the callee's phone number
     * @param startTime string contains the start date and time
     * @param endTime   string contains the end date and time
     */
    PrettyPrinter(String customer, String caller, String callee, String startTime, String endTime) {
        super(customer, caller, callee, startTime, endTime);
    }
}
