package edu.pdx.cs410J.hui2.client;

import com.google.common.annotations.VisibleForTesting;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DefaultDateTimeFormatInfo;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * A basic GWT class that makes sure that we can send an Phone Bill back from the server
 */
public class PhoneBillGwt implements EntryPoint {
  public void onModuleLoad() {

    //Buttons
    Button buttonAddCall = new Button("Add Phone Call");
    Button buttonPrettyPrintAllCalls = new Button("Display All Calls");
    Button buttonSearch = new Button("Search");
    Button buttonHelp = new Button("Help");

    //Text box
    final TextArea textBox = new TextArea();
    textBox.setReadOnly(true);
    textBox.setSize("800px","100px");

    //Vertical Panels
    VerticalPanel panelHeader = new VerticalPanel();
    panelHeader.add(new Label());
    panelHeader.add(textBox);

    //Adding a customer
    VerticalPanel panelAddNewCustomer = new VerticalPanel();
    panelAddNewCustomer.add(new Label("Customer Name:"));
    final TextBox textBoxCustomerName = new TextBox();
    panelAddNewCustomer.add(textBoxCustomerName);

    panelAddNewCustomer.add(new Label("Caller's Number:"));
    final TextBox textBoxCallerNumber = new TextBox();
    panelAddNewCustomer.add(textBoxCallerNumber);

    panelAddNewCustomer.add(new Label("Callee's Number:"));
    final TextBox textBoxCalleeNumber = new TextBox();
    panelAddNewCustomer.add(textBoxCalleeNumber);

    panelAddNewCustomer.add(new Label("Start Time:"));
    final TextBox textBoxStartTime = new TextBox();
    panelAddNewCustomer.add(textBoxStartTime);

    panelAddNewCustomer.add(new Label("End Time:"));
    final TextBox textBoxEndTime = new TextBox();
    panelAddNewCustomer.add(textBoxEndTime);

    //add all the buttons to the root panel
    RootPanel rootPanel = RootPanel.get();

    //rootPanel.add
    rootPanel.add(buttonAddCall,50,50);
    rootPanel.add(buttonPrettyPrintAllCalls,200,50);
    rootPanel.add(buttonSearch,350,50);
    rootPanel.add(buttonHelp,450,50);
    rootPanel.add(panelAddNewCustomer, 50, 250);

    //add Vertical Panels to the root panel
    rootPanel.add(panelHeader, 50, 100);

    /**
     * This is to do a window alert and print out the content
     */
    buttonHelp.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        Window.alert(readMe());
      }
    });

    /**
     * Add a phone call to new or existing phone bill
     */
    buttonAddCall.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        try {
          if(!checkNullValues(textBoxCustomerName.getValue(),textBoxCallerNumber.getValue(),textBoxCalleeNumber.getValue(),
                  textBoxStartTime.getValue(), textBoxEndTime.getValue()))
          {
            textBox.setValue("");
            return;
          }
          if(!compareDate(textBoxStartTime.getValue(), textBoxEndTime.getValue()))
          {
            textBox.setValue("");
            return;
          }
          final PhoneCall tempCall = new PhoneCall(textBoxCallerNumber.getValue(), textBoxCalleeNumber.getValue(), textBoxStartTime.getValue(), textBoxEndTime.getValue());
          PhoneBillServiceAsync async = GWT.create(PhoneBillService.class);
          async.addCustomer(textBoxCustomerName.getValue().trim(),tempCall , new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable throwable) {
              Window.alert(throwable.getMessage());
            }

            @Override
            public void onSuccess(Void aVoid) {
              textBox.setValue("Customer successfully added:\n" + textBoxCustomerName.getValue()+" has added a new phone call: "+tempCall.toString() );
            }
          });
        }
        catch(Exception ex){
          Window.alert("Please provide correct arguments"+ex.getMessage());
          return;
        }

      }
    });

    /**
     * print out all of the phone calls for that specific customer
     */
    buttonPrettyPrintAllCalls.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        PhoneBillServiceAsync async = GWT.create(PhoneBillService.class);
        async.printAll(new AsyncCallback<Map<String, PhoneBill>>() {

          @Override
          public void onFailure(Throwable throwable) {
            Window.alert(throwable.getMessage());
          }

          @Override
          public void onSuccess(Map<String, PhoneBill> stringPhoneBillMap) {
            String prettyCalls = "";
            //pretty print it
            if (stringPhoneBillMap == null || stringPhoneBillMap.isEmpty()) {
              Window.alert("No phonebills to show");
              return;
            }
            for (String customer : stringPhoneBillMap.keySet()) {
              Collection calls = stringPhoneBillMap.get(customer).getPhoneCalls();
              if(customer.equals(textBoxCustomerName.getValue()))
                prettyCalls+=prettyPrint((List<PhoneCall>) calls)+"\n";
            }
            if(prettyCalls == "") {
              Window.alert("Unable to find customer");
              textBox.setValue("");
            }
            else
              textBox.setValue("Display all calls\n\n"+"Customer Name: "+ textBoxCustomerName.getValue() +"\n\n" +
                      "#          caller                   callee                       Start Time                    " +
                      "       End Time                  Duration \n"
                    +prettyCalls);
          }
        });
      }
    });

    /**
     * Search for that time of a specific customer and print out
     */
    buttonSearch.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        if(!checkSearchNullValues(textBoxCustomerName.getValue(), textBoxStartTime.getValue(), textBoxEndTime.getValue()))
        {
          textBox.setValue("");
          return;
        }
        if(!compareDate(textBoxStartTime.getValue(), textBoxEndTime.getValue()))
        {
          textBox.setValue("");
          return;
        }
        PhoneBillServiceAsync async = GWT.create(PhoneBillService.class);
        async.search(textBoxCustomerName.getValue(), textBoxStartTime.getValue(), textBoxEndTime.getValue(),
                new AsyncCallback<List<PhoneCall>>() {
          @Override
          public void onFailure(Throwable throwable) {
            Window.alert("error "+throwable.getMessage());
          }

          @Override
          public void onSuccess(List<PhoneCall> phoneCalls) {
            if(phoneCalls==null||phoneCalls.isEmpty()){
              Window.alert("No phonecalls matching under "+textBoxCustomerName.getValue()+ " or that " +
                      "no phone calls found");
              return;
            }

            //pretty print the search calls
            textBox.setValue("Search print\n\n"+"Customer Name: "+ textBoxCustomerName.getValue() +"\n\n" +
                    "#          caller                   callee                       Start Time                    " +
                    "       End Time                  Duration \n"
                    +prettyPrint(phoneCalls));
          }
        });

      }
    });
  }

  /**
   * Print all or search of the phone call
   * @param calls The list of calls on the server
   * @return returns back the phone call if it matches
   */
  public String prettyPrint(List<PhoneCall> calls){
    int x=0;
    String prettyCalls="";

    for(PhoneCall call: calls){
      prettyCalls += ++x + "   " + call.getCaller() + "      " + call.getCallee() + "       " + call.getStartTimeString() + "      " +
              call.getEndTimeString() + "         " + call.duration() + "\n";
    }
    return prettyCalls;
  }

  /**
   * return the read me back to main
   * @return return the string
   */
  private String readMe() {
    return "Name: Hui Yu Sim \nProject: 5 A Rich Internet Application for a Phone Bill\n\n" +
            "The Purpose of this programming assignment is to create a web service that\n" +
            "has customer name and consists of multiple phone calls.\n" +
            "Please follow the following steps to insure program will run correctly.\n" +
            "\tcustomer           Person whose phone bill we’re modeling\n" +
            "\tcallerNumber       Phone number of caller\n" +
            "\tcalleeNumber       Phone number of person who was called\n" +
            "\tstartTime          Date and time call began (12hours)\n" +
            "\tendTime            Date and time call ended (12hours)\n" +
            "Buttons available: \n" +
            "\tAdd Phone Call    Adding one phone call\n" +
            "\tSearch            Phone calls should be searched for\n" +
            "\tPrint All         Prints a specific phone bill\n" +
            "\tREADME            Prints a README for this project and exits\n" +
            "Date and time should be in the format: mm/dd/yyyy hh:mm am/pm";
  }

  /**
   * It is to check all of the parameter entered in by the customer, it will check if they are empty then it will
   * check if they are correct
   * @param customerName  the customer name
   * @param caller  the caller phone number
   * @param callee the callee phone number
   * @param startTime the start time of the phone call including date/time(am/pm)
   * @param endTime the end time of the phone call including date/time(am/pm)
   * @return return if they are true or false, if they are false it will do a window alert and on the main
   * function it will exit out of the function.
   */
  private boolean checkNullValues(String customerName, String caller, String callee, String startTime, String endTime) {
    if(customerName == "")
    {
      Window.alert("Customer name is missing");
      return false;
    }
    else if(caller == "")
    {
      Window.alert("Caller phone number is missing");
      return false;
    }
    else if(callee == "")
    {
      Window.alert("Callee phone number is missing");
      return false;
    }
    else if(startTime == "")
    {
      Window.alert("Start time is missing");
      return false;
    }
    else if(endTime == "")
    {
      Window.alert("End time is missing");
      return false;
    }
    if(!customerName.matches("[a-z A-Z 0-9]+"))
    {
      Window.alert("Invalid customer name");
      return false;
    }
    if(!caller.matches("\\d{3}-\\d{3}-\\d{4}$"))
    {
      Window.alert("Invalid caller phone number, follow the format xxx-xxx-xxxx with digit");
      return false;
    }
    if(!callee.matches("\\d{3}-\\d{3}-\\d{4}$"))
    {
      Window.alert("Invalid callee phone number, follow the format xxx-xxx-xxxx with digit");
      return false;
    }
    String[] tempStart = startTime.split(" ");
    String[] tempEnd= endTime.split(" ");
    if(!tempStart[0].matches("((0?[1-9])|(1?[012]))/(0?[1-9]|[12][0-9]|3[01])/[0-9]{2}([0-9]{2})"))
    {
      Window.alert("Invalid start date, follow the format of mm/dd/yyyy with digit");
      return false;
    }
    else if(!tempStart[1].matches("([0]?[1-9]|1[0-2]):[0-5][0-9]"))
    {
      Window.alert("Invalid start time, follow the format of hh:mm with digit");
      return false;
    }
    if(!tempStart[2].matches("(am|pm|AM|PM)"))
    {
      Window.alert("Need to include am or pm for start time");
      return false;
    }
    if(!tempEnd[0].matches("((0?[1-9])|(1?[012]))/(0?[1-9]|[12][0-9]|3[01])/[0-9]{2}([0-9]{2})"))
    {
      Window.alert("Invalid end date, follow the format of mm/dd/yyyy with digit");
      return false;
    }
    else if(!tempEnd[1].matches("([0]?[1-9]|1[0-2]):[0-5][0-9]"))
    {
      Window.alert("Invalid end time, follow the format of hh:mm with digit");
      return false;
    }
    if(!tempEnd[2].matches("(am|pm|AM|PM)"))
    {
      Window.alert("Need to include am or pm for end time");
    }

    return true;
  }

  /**
   * This check is for search only. It will only require the user to enter the customer name, start time and end time
   * @param customerName  the customer name
   * @param startTime the start time of the phone call including date/time(am/pm)
   * @param endTime the end time of the phone call including date/time(am/pm)
   * @return it will return true or false back to main, if its true it will continue if not it will exit out of
   * the function and window alert the error
   */
  private boolean checkSearchNullValues(String customerName, String startTime, String endTime) {
    if(customerName == "")
    {
      Window.alert("Customer name is missing");
      return false;
    }
    else if(startTime == "")
    {
      Window.alert("Start time is missing");
      return false;
    }
    else if(endTime == "")
    {
      Window.alert("End time is missing");
      return false;
    }
    if(!customerName.matches("[a-z A-Z 0-9]+"))
    {
      Window.alert("Invalid customer name");
      return false;
    }
    String[] tempStart = startTime.split(" ");
    String[] tempEnd= endTime.split(" ");
    if(!tempStart[0].matches("((0?[1-9])|(1?[012]))/(0?[1-9]|[12][0-9]|3[01])/[0-9]{2}([0-9]{2})"))
    {
      Window.alert("Invalid start date, follow the format of mm/dd/yyyy with digit");
      return false;
    }
    else if(!tempStart[1].matches("([0]?[1-9]|1[0-2]):[0-5][0-9]"))
    {
      Window.alert("Invalid start time, follow the format of hh:mm with digit");
      return false;
    }
    if(!tempStart[2].matches("(am|pm|AM|PM)"))
    {
      Window.alert("Need to include am or pm for start time");
      return false;
    }
    if(!tempEnd[0].matches("((0?[1-9])|(1?[012]))/(0?[1-9]|[12][0-9]|3[01])/[0-9]{2}([0-9]{2})"))
    {
      Window.alert("Invalid end date, follow the format of mm/dd/yyyy with digit");
      return false;
    }
    else if(!tempEnd[1].matches("([0]?[1-9]|1[0-2]):[0-5][0-9]"))
    {
      Window.alert("Invalid end time, follow the format of hh:mm with digit");
      return false;
    }
    if(!tempEnd[2].matches("(am|pm|AM|PM)"))
    {
      Window.alert("Need to include am or pm for end time");
    }
    return true;
  }

  /**
   * In this function we are checking if the start date and end date make sense
   * @param startDate the start time of the phone call including date/time(am/pm)
   * @param endDate the end time of the phone call including date/time(am/pm)
   * @return it will return true or false to see if the start time is less than end time
   */
  public static boolean compareDate(String startDate, String endDate)
  {
    DefaultDateTimeFormatInfo formatInfo = new DefaultDateTimeFormatInfo();
    DateTimeFormat ShortDateFormat = new DateTimeFormat("MM/dd/yyy hh:mm a",formatInfo){};
    Date start = null;
    Date end = null;
    long duration;
    try {
      start = ShortDateFormat.parse(startDate);
      end = ShortDateFormat.parse(endDate);
      duration = end.getTime() - start.getTime();
      if(duration < 0) {
        Window.alert("Start time is after end time, please modified the date/time");
        return false;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return true;
  }

}
