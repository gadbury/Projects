import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Frontend interface for a song database.
 */
public class Frontend implements FrontendInterface {

  private Scanner input;                    //Scanner to read the user's input
  private BackendInterface backend;         //Backend to store and work the database
  private String currentCommand;            //The current user input being processed

  /**
   * Constructor for the frontend
   * @param in - Scanner to read from input
   * @param backend - backend database
   */
  public Frontend(Scanner in, BackendInterface backend){
    this.input = in;
    this.backend = backend;
  }

  @Override
  /**
   * This method is the primary method of the class, prompting the user for an input,
   * reading that input and routing them to each of the subsequent methods
   * based on what their input is.
   */
  public void runCommandLoop() {
    displayMainMenu();
    currentCommand = input.nextLine().toUpperCase();
    while(currentCommand.compareTo("Q") != 0){
      if(currentCommand.compareTo("L") == 0){
        loadFile();
      } else if (currentCommand.compareTo("G") == 0) {
        getSongs();
      } else if (currentCommand.compareTo("F") == 0) {
        setFilter();
      } else if (currentCommand.compareTo("D") == 0) {
        displayTopFive();
      }
      if(!currentCommand.isEmpty()) {
        displayMainMenu();
      }
      input.nextLine();
      currentCommand = input.next().toUpperCase();
    }
  }

  @Override
  /**
   * This method prints to the user each of the options they have for interacting
   * with the database.
   */
  public void displayMainMenu() {
    System.out.println("Please enter one of the following:");
    System.out.println("L - Load File");
    System.out.println("G - Get Songs");
    System.out.println("F - Set Filter");
    System.out.println("D - Display top five songs");
    System.out.println("Q - quit");
    System.out.println();
  }

  @Override
  /**
   * This method prompts the user to input a file name and calls the backend to load the
   * songs in that file to the database.
   */
  public void loadFile() {
    boolean wrongNameInput;
    do {
      try {
        System.out.println("Please enter file name to load:");
        String filename = input.next();
        backend.readData(filename);
        System.out.println("File successfully read!");
        wrongNameInput = false;
      } catch (IOException e) {
        System.out.println(
            "File name invalid, please double check the file you are trying to insert");
        wrongNameInput = true;
      }
    } while (wrongNameInput);
    System.out.println();
  }

  @Override
  /**
   * This method prompts the user for a min and max year to sort the song list by and then
   * calls the backend with this minimum and maximum to return a list of songs in sorted order.
   */
  public void getSongs() {
    Integer min = null;
    Integer max = null;
    System.out.println("Please enter a minimum year to filter the list by, or a non-integer to " +
        "clear the filter");
    if (input.hasNextInt()) {
      min = input.nextInt();
    }
    else {
      System.out.println("Minimum cleared");
    }

    input.nextLine();

    System.out.println("Please enter a maximum year to filter the list by, or a non-integer to " +
        "clear the filter");
    if(input.hasNextInt()) {
      max = input.nextInt();
    }
    else {
      System.out.println("Maximum cleared");
    }

    List<String> list = backend.getRange(min, max);
    for(String curr : list){
      System.out.println(curr);
    }
    System.out.println();
  }

  @Override
  /**
   * This method prompts the user for a value to set the filter of the database, or to enter a
   * non-integer value to clear the filter and then calls backend with this filter to return a
   * list of songs that clear the filter.
   */
  public void setFilter() {
    System.out.println("Please enter a loudness number to filter song lists by, or enter" +
        " anything else to clear the filters");
    Integer level = null;
    if(input.hasNextInt()){
      level = input.nextInt();
    }
    else {
      input.next();
      System.out.println("Filter cleared");
    }
    List<String> list = backend.setFilter(level);
    System.out.println("Current song list with filter:");
    for(String curr : list){
      System.out.println(curr);
    }
    System.out.println();
  }

  @Override
  /**
   * This method simply calls the backend for the top five songs that fit the filter and range
   * set in previous methods and tells the user if the list comes back empty.
   */
  public void displayTopFive() {
    System.out.println("Retrieving top fie songs...");
    List<String> list = backend.fiveMost();
    if(list == null || list.isEmpty()){
      System.out.println("List is empty, consider changing filter and range");
    }
    else {
      for(String curr : list){
        System.out.println(curr);
      }
    }
    System.out.println();
  }
}
