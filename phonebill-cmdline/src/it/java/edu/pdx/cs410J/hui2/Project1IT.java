package edu.pdx.cs410J.hui2;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the functionality in the {@link Project3} main class.
 */
public class Project1IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project3} with the given arguments.
     */
    private void invokeMain(String... args) {
        //return invokeMain( Project3.class, args );
    }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  public void testNoCommandLineArguments() {
    //MainMethodResult result = invokeMain();
    //assertThat(result.getExitCode(), equalTo(1));
    //assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
  }

}