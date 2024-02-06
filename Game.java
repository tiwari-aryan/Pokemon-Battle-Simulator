import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

class Game {

  private Scanner scanner = new Scanner(System.in);
  
  private String saveFile;
  private Player player;
  private boolean inGame;
  private boolean newUser;

  public Game(String saveFile){
    this.saveFile = saveFile;
    this.inGame = true;
    this.newUser = true;
    readUserData();
  }

  private void saveUserData(){

    // Pokeballs
    // Number
    // START POKEMON
    // Name
    // Pokemon Name
    // Type
    // Pokemon Types
    // Stats
    // Pokemon Stats
    // NEXT POKEMON
    // END POKEMON

    try {
      FileWriter fw = new FileWriter(this.saveFile);
      BufferedWriter bw = new BufferedWriter(fw);

      String battlePokemonIndicator;
      
      bw.write("Pokeballs\n");
      bw.write(this.player.getPokeballs() + "\n");
      bw.write("START POKEMON\n");
  
      for(Pokemon pokemon: this.player.getPokemonList()){
        battlePokemonIndicator = "";
        bw.write("Name\n");
        if(player.checkBattlePokemon(pokemon)){
          System.out.println("saved battle pokemon");
          battlePokemonIndicator = "*";
        }
        bw.write(pokemon.getName() + battlePokemonIndicator + "\n");
        bw.write("Level\n");
        bw.write(pokemon.getLevel() + "\n");
        bw.write("NEXT POKEMON\n");
      }
  
      bw.write("END POKEMON");
  
      bw.close();
    }

    catch(IOException e){
      e.printStackTrace();
    }

    
    
  }

  private void readUserData(){
    int numPokeballs = 20;
    ArrayList <Pokemon> pokemonList = new ArrayList <Pokemon>();
    Pokemon[] battlePokemon = new Pokemon[6];
    int battlePokemonIndex = 0;

    try {
      FileReader fr = new FileReader(this.saveFile);
      BufferedReader br = new BufferedReader(fr);
  
      String data;
  
      while((data = br.readLine()) != null){
        this.newUser = false;
        if(data.equals("Pokeballs")){
          numPokeballs = Integer.parseInt(br.readLine());
        }
        else if(data.equals("START POKEMON")){
          String pokemonData = "", pokemonName = "";
          boolean isBattlePokemon;
          int pokemonLevel = 1;
          Pokemon newPokemon;

          System.out.println("Loading Data...");
          
          while(!pokemonData.equals("END POKEMON")){
            pokemonData = br.readLine();
            Thread.sleep(100);
            if(pokemonData.equals("Name")){
              pokemonName = br.readLine();
            }
            else if(pokemonData.equals("Level")){
              pokemonLevel = Integer.parseInt(br.readLine());
            }
            else if(pokemonData.equals("NEXT POKEMON")){
              isBattlePokemon = false;
              if(pokemonName.charAt(pokemonName.length()-1) == '*'){
                pokemonName = pokemonName.substring(0, pokemonName.length()-1);
                isBattlePokemon = true;
              }
              newPokemon = new Pokemon(Pokedex.getPokemon(pokemonName), pokemonLevel);
              pokemonList.add(newPokemon);
              if(isBattlePokemon){
                battlePokemon[battlePokemonIndex] = newPokemon;
                battlePokemonIndex += 1;
              }
            }
          }
        }
      }
    }
    catch(IOException e){
      e.printStackTrace();
    }
    catch(InterruptedException e){
      e.printStackTrace();
    }

    System.out.println("Save file succesfully loaded!");
    
    this.player = new Player(numPokeballs, pokemonList, battlePokemon);
    
  }

  private void printTutorial(){

    System.out.println("Hello Trainer! Welcome to Pokemon! [ENTER TO PROCEED]");

    scanner.nextLine();

    System.out.println("Pokemon is a game that involves collecting small characters (called Pokemon) and battling other Pokemon to get stronger! The ultimate goal is that you... GOTTA CATCH 'EM ALL!");

    scanner.nextLine();

    System.out.println("Let's start you off by picking a starter!");

    System.out.println("There are three starters.");

    System.out.println("Firstly, we have Bulbasaur. Bulbasaur is a Grass-Poison Type Pokemon. Its stats are shown below:");

    Pokedex.getPokemon("Bulbasaur").printStats();

    System.out.println("[ENTER TO PROCEED]");

    scanner.nextLine();

    System.out.println("Next, we have Charmander. Charmander is a Fire Type Pokemon. Its stats are shown below:");

    Pokedex.getPokemon("Charmander").printStats();

    System.out.println("[ENTER TO PROCEED]");

    scanner.nextLine();

    System.out.println("Last but most definitely not least, we have Squirtle. Squirtle is a Water Type Pokemon. Its stats are shown below:");

    Pokedex.getPokemon("Squirtle").printStats();

    System.out.println("[ENTER TO PROCEED]");

    scanner.nextLine();
    
    System.out.println("Okay, now that the starter Pokemon have been introduced, time to make a choice.");

    String starterPokemon = "";

    boolean validPokemon = false;

    while(!validPokemon){
      System.out.println("Which starter Pokemon would you like to select?");
      starterPokemon = scanner.nextLine();
      if(starterPokemon.equalsIgnoreCase("Bulbasaur")){
        this.player.addPokemon("Bulbasaur");
        validPokemon = true;
      }
      else if(starterPokemon.equalsIgnoreCase("Charmander")){
        this.player.addPokemon("Charmander");
        validPokemon = true;
      }
      else if(starterPokemon.equalsIgnoreCase("Squirtle")){
        this.player.addPokemon("Squirtle");
        validPokemon = true;
      }
      else{
        System.out.println("Please enter a valid starter Pokemon from the options above.");
      }
    }

    starterPokemon = starterPokemon.substring(0, 1).toUpperCase() + starterPokemon.substring(1).toLowerCase();

    System.out.println("Wow! " + starterPokemon + " is an amazing choice! I'm excited to see your journey as a Pokemon trainer as you attempt to CATCH 'EM ALL!");

    this.newUser = false;
    saveUserData();

    System.out.println("Press [ENTER] to start the game!");

    scanner.nextLine();
    
  }

  public boolean getGame(){
    return inGame;
  }

  public Player getPlayer(){
    return this.player;
  }

  public void runLoop() {

    if(this.newUser){
      printTutorial();
    }

    else{

      System.out.println("Please select one of the following options: ");
      System.out.println();
      System.out.println("[catch] - to catch a Pokemon!");
      System.out.println("[train] - to battle other Trainers and level up your Pokemon!");
      System.out.println("[roster] - to inspect and update your Pokemon roster!");
      System.out.println("[save] - to save your current progress!");
      System.out.println("[main menu] - to exit to the main menu!");
      System.out.println("[quit] - to exit the game!");
      System.out.println();
      System.out.println("What would you like to do Trainer?");
      
      String command = scanner.nextLine();
      
      // catch

      if(command.equalsIgnoreCase("catch")){

        Computer computer = new Computer();
        computer.generateRoster(1);

        Battle catchBattle = new Battle(player, computer, "catch");


        while(catchBattle.getBattle()){
          System.out.println("hello");
          catchBattle.battleTurn();
          System.out.println("");
        }

        if(catchBattle.getResult()){
          System.out.println("Congratulations! You have won the battle!");
        }
        
        
        
        // Pokemon wildPokemon = Pokedex.getWildPokemon();

        // System.out.println("A wild " + wildPokemon.getName() + " appeared!");

        // wildPokemon.printStats();
        
      }

      // train

      else if(command.equalsIgnoreCase("train")){
        Computer computer = new Computer();
        computer.generateRoster(6);

        System.out.println("hello");

        Battle trainerBattle = new Battle(player, computer, "train");

        while(trainerBattle.getBattle()){
          trainerBattle.battleTurn();
        }
        
      }

      else if(command.equalsIgnoreCase("roster")){

        String rosterCommand = "";

        System.out.println("Welcome to the roster management menu. Here, you can inspect your Pokemon and select them to be in your active roster that you use in battle!");
        System.out.println();
        
        while(!rosterCommand.equalsIgnoreCase("exit")){

          System.out.println("Your current roster and a list of all your Pokemon is shown below.");
          System.out.println();
          
          System.out.println("ACTIVE ROSTER");

          this.player.printRoster();
          
          System.out.println("ALL POKEMON");
  
          this.player.printPokemon();

          System.out.println();
          System.out.println();

          System.out.println("Please select one of the following options:");
          System.out.println("[inspect] - to inspect the statistics of a specific Pokemon");
          System.out.println("[swap] - to add or remove a Pokemon to and from your battle roster");
          System.out.println("[exit] - to exit the roster management menu");

          System.out.println("What would you like to do Trainer?");

          rosterCommand = scanner.nextLine();
          
          
          if(rosterCommand.equalsIgnoreCase("inspect")){
            System.out.println("Please select a Pokemon from the ALL POKEMON list above by entering the corresponding number.");
            int index = scanner.nextInt();
            Pokemon inspectPokemon = player.getPokemon(index-1);

            String inspectCommand = "";

            while(!inspectCommand.equalsIgnoreCase("exit")){

              scanner.nextLine();

              inspectPokemon.printStats();
              System.out.println();
              System.out.println("BATTLE MOVES");
              System.out.println();
              inspectPokemon.printMoves();
              System.out.println();
              System.out.println("MOVESET");
              System.out.println();
              inspectPokemon.printMoveSet();
              System.out.println();
              
              System.out.println("Please select one of the following commands:");
              System.out.println("[edit] - to add or remove a move to the Pokemon's battle moves");
              System.out.println("[exit] - to exit the Pokemon inspect menu");
              System.out.println();
              System.out.println("What would you like to do Trainer?");

              inspectCommand = scanner.nextLine();
              
              if(inspectCommand.equalsIgnoreCase("edit")){
                int swapIndex, moveIndex;
                System.out.println("Please select a move to add from the MOVESET list above:");
                moveIndex = scanner.nextInt();
                System.out.println("Please select a move to remove from the BATTLE MOVES list");
                swapIndex = scanner.nextInt();
                inspectPokemon.swapMoves(moveIndex-1, swapIndex-1);

                System.out.println("The change has been made! Thank you!");
              }
              else if(!inspectCommand.equalsIgnoreCase("exit")){
                System.out.println("Sorry Trainer! That is an invalid command!");
              }
            }
            
          }
          else if(rosterCommand.equalsIgnoreCase("swap")){
            System.out.println("Which Pokemon would you like to add to your battle roster?");
            int pokemonIndex = scanner.nextInt();
            System.out.println("Which battle roster slot would you like to add this Pokemon to?");
            int swapIndex = scanner.nextInt();

            if(player.setBattlePokemon(swapIndex-1, pokemonIndex-1)){
              System.out.println("Updated!");
            }
            else{
              System.out.println("The Pokemon you have selected is already on your active roster. Please enter a valid Pokemon to add to your roster.");
            }
            
          }
          else if(!rosterCommand.equalsIgnoreCase("exit")){
            System.out.println("Sorry Trainer! That is an invalid command!");
          }
        }
        
      }

      else if(command.equalsIgnoreCase("save")){
        saveUserData();
      }

      else if(command.equalsIgnoreCase("main menu")){
        saveUserData();
        System.out.println("Exiting to main menu...");
        inGame = false;
      }

      else if(command.equalsIgnoreCase("quit")){
        saveUserData();
        System.out.println("Thank you for playing!");
        inGame = false;
        System.exit(0);
      }

      else{
        System.out.println("Hmmmm... that is not a recognized command Trainer!");
      }
      
    }
    
  }
  
}