import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

class Main {

  private static Scanner scanner = new Scanner(System.in);

  private static Game game;

  private static ArrayList <String> saveFiles = new ArrayList <String>();

  public static void addSaveFile(String fileName){

    saveFiles.add(fileName);
    
    try {
      FileWriter fw = new FileWriter("save_files.txt", true);
      fw.write("\n");
      fw.write(fileName);
      fw.close();
    }

    catch(IOException e){
      e.printStackTrace();
    }
    
  }

  public static boolean createFile(String fileName){
    
    try {

      File saveFile = new File(fileName + ".txt");

      if(saveFile.createNewFile()){
        return true;
      }
      else{
        return false;
      }
      
    }

    catch(IOException e){
      e.printStackTrace();
      return false;
    }
    
  }

  public static void createSaveFile(){

    String fileName = "";
    
    boolean fileCreated = false, runLoop = true;
    
    while(!fileCreated && runLoop){

      System.out.println("What would you like to name your save file? (Please enter exit if you wish to exit to the main menu)");
      
      fileName = scanner.nextLine();

      if(fileName.equalsIgnoreCase("exit")){
        System.out.println("Exiting to main menu...");
        runLoop = false;
      }

      else if(createFile(fileName)){
        System.out.println("Thank you! Your save file has been succesfully created");
        addSaveFile(fileName + ".txt");
        fileCreated = true;
      }
      else{
        System.out.println("Sorry, there was an error creating the file. Please ensure that the file you are trying to create is unique, and does not share a name with any other files.");
      }
    }
    
  }

  public static String selectSaveFile(){

    boolean validResponse = false;

    String response;

    String fileName = "";

    while(!validResponse){

      try{

        System.out.println("Please enter the number of the save file you would like to play. (Please enter exit if you wish to exit to the main menu)");

        printSaveFiles();

        response = scanner.nextLine();

        if(response.equals("exit")){
          validResponse = true;
        }

        else if(Integer.parseInt(response) <= 0 || Integer.parseInt(response) > saveFiles.size()){
          System.out.println("Please enter a valid number within the range shown above.");
        }

        else{
          fileName = saveFiles.get(Integer.parseInt(response) - 1);
          validResponse = true;
        }
        
      }

      catch(NumberFormatException e){
        System.out.println("Please enter a valid number from the list above.");
      }
      
    }

    return fileName;
    
  }

  public static void printSaveFiles(){
    for(int i = 0; i < saveFiles.size(); i++){
      System.out.println("[" + (i+1) + "] - " + saveFiles.get(i));
    }
  }
  
  public static void welcomeScreen(){

    generateSaveFiles();

    String fileName;

    boolean validFile = false;
    
    if(saveFiles.size() == 0){

      while(saveFiles.size() == 0){
        System.out.println("Welcome! I see that you are a new player...");
        System.out.println("Let's start by creating a save file.");
        System.out.println();
  
        createSaveFile();
      }
        
    }

    else{
      System.out.println("Welcome back!");
    }

    System.out.println("Would you like to create a new save file or play an existing save file?");

    boolean runLoop = true;

    String command, playFile = "";
    
    while(runLoop){

      System.out.println("Please select one of the following options:");
      System.out.println("[create] - to create a new file");
      System.out.println("[play] - to play an existing file");

      command = scanner.nextLine();
      
      if(command.equalsIgnoreCase("create")){
        createSaveFile();
      }
      else if(command.equalsIgnoreCase("play")){
        playFile = selectSaveFile();
        runLoop = playFile.equals("");
      }
      else{
        System.out.println("That is not a valid response.");
        runLoop = true;
      }
      
    }

    startGame(playFile);
    
  }

  public static void generateSaveFiles() {

    try {

      FileReader fr = new FileReader("save_files.txt");

      BufferedReader br = new BufferedReader(fr);
      
      String file;

      while((file = br.readLine()) != null){
        saveFiles.add(file);
      }

      br.close();
      
    }

    catch(IOException e){
      e.printStackTrace();
    }
    
    
  }

  public static void startGame(String saveFile){

    game = new Game(saveFile);
    
  }
  
  public static void main(String[] args) {

    
    
    System.out.println("Welcome to Pokemon! ");

    Movedex.initMovedex();
    Pokedex.initPokedex();
    Type.initTypes();
    
    welcomeScreen();

    while(game.getGame()){
      game.runLoop();
    }
    
  }
}