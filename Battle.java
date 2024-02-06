import java.util.InputMismatchException;
import java.util.Scanner;
class Battle {

  private static Scanner scanner = new Scanner(System.in);
  private Player player;
  private Computer computer;
  private String battleType;
  private boolean inBattle;
  private boolean playerTurn;
  private boolean playerWin;
  private Move playerMove;
  private Move computerMove;
  private int xp;
  
  public Battle(Player player, Computer computer, String battleType){
    this.player = player;
    this.computer = computer;
    this.battleType = battleType;
    this.inBattle = true;
    this.xp = calculateXp();
  }

  public boolean getBattle(){
    return inBattle;
  }

  private int calculateXp(){
    return 100;
  }

  public void checkTurn(){
    if(player.getActivePokemon().getSpeed() > computer.getActivePokemon().getSpeed()){
      playerTurn = true;
    }
    else if(player.getActivePokemon().getSpeed() == computer.getActivePokemon().getSpeed()){
      playerTurn = Math.random() < 0.5;
    }
    else{
      playerTurn = false;
    }
  }

  public Move selectMove(){
    Move selectedMove = null;
    int index;
    while(selectedMove == null){
      try{
        System.out.println("Please select a move from the list below:");
        player.getActivePokemon().printMoves();
        System.out.println("[5] - to swap your current Pokemon");
        index = scanner.nextInt();
        if(index <= 0 || index > 5){
          System.out.println("The input you have entered is invalid. Please select a valid move from the list above.");
        }
        else if(index == 5){
          if(player.canSwap()){
            selectedMove = Movedex.getMove("SWAP");
          }
          else{
            System.out.println("There are currently no Pokemon to switch with. Please select a valid move.");
          }
        }
        else{
          selectedMove = player.getActivePokemon().getMove(index-1);
        }
      }
      catch(InputMismatchException e){
        System.out.println("Please enter a valid integer value to select your move.");
      }
    }
    return selectedMove;
  }

  public void selectPokemon(){
    int index;
    while(!player.pokemonSelected()){
      try{
        System.out.println("Please select a pokemon from your roster:");
        player.printRoster();
        index = scanner.nextInt();
        if(index <= 0 || index > player.numUsablePokemon()){
          System.out.println("The input you have entered is invalid. Please select a valid Pokemon from the list above.");
        }
        else{
          player.setActivePokemon(index-1);
        }
      }
      catch(NumberFormatException e){
        System.out.println("Please enter a valid integer value to select your Pokemon.");
      }
    }
  }

  public boolean getResult(){
    return playerWin;
  }

  public void battleTurn(){

    playerMove = null;
    computerMove = null;

    if(!player.getActivePokemon().isUsable()){
      player.removeActivePokemon();
    }

    if(!computer.getActivePokemon().isUsable()){
      computer.removeActivePokemon();
    }
    
    if(!player.pokemonSelected()){
      if(player.canSwap()){
        selectPokemon();
        playerMove = Movedex.getMove("SWAP");
      }
      else{
        inBattle = false;
        playerWin = false;
      }
    }

    else{
      playerMove = selectMove();
    }
    
    if(!computer.pokemonSelected()){
      if(computer.canSwap()){
        computer.swapPokemon(0);
        computerMove = Movedex.getMove("SWAP");
      }
      else{
        inBattle = false;
        playerWin = true;
      }
    }
    else{
      computerMove = computer.generateMove();
    }

    if(playerMove != null && computerMove != null){

      player.getActivePokemon().printStats();
      computer.getActivePokemon().printStats();
      
      checkTurn();
      
      if(playerMove.getName().equals("SWAP")){
        System.out.println("Player: " + player.getActivePokemon().getName() + ", I choose you!");
      }
      if(computerMove.getName().equals("SWAP")){
        System.out.println("Computer: " + computer.getActivePokemon().getName() + ", I choose you!");
      }

      if(playerTurn){
        if(!playerMove.getName().equals("SWAP")){
          System.out.println(player.getActivePokemon().getName() + " used " + playerMove.getName());
          int moveDamage = player.performMove(playerMove, computer.getActivePokemon());
          if(moveDamage != 0){
            System.out.println("The move hit! It did " + moveDamage + " damage!");
          }
          else{
            System.out.println("The move missed!");
          }
        }
        if(!computerMove.getName().equals("SWAP")){
          if(computer.getActivePokemon().isUsable()){
            System.out.println(computer.getActivePokemon().getName() + " used " + computerMove.getName());
            int moveDamage = computer.performMove(computerMove, player.getActivePokemon());
            if(moveDamage != 0){
              System.out.println("The move hit! It did " + moveDamage + " damage!");
            }
            else{
              System.out.println("The move missed!");
            }
          }
          else{
            System.out.println(computer.getActivePokemon().getName() + " fainted!");
            computer.removeActivePokemon();
          }
        }
      }
      else{
        if(!computerMove.getName().equals("SWAP")){
          System.out.println(computer.getActivePokemon().getName() + " used " + computerMove.getName());
          int moveDamage = computer.performMove(computerMove, player.getActivePokemon());
          if(moveDamage != 0){
            System.out.println("The move hit! It did " + moveDamage + " damage!");
          }
          else{
            System.out.println("The move missed!");
          }
        }
        if(!playerMove.getName().equals("SWAP")){
          if(player.getActivePokemon().isUsable()){
            System.out.println(player.getActivePokemon().getName() + " used " + playerMove.getName());
            int moveDamage = player.performMove(playerMove, computer.getActivePokemon());
            if(moveDamage != 0){
              System.out.println("The move hit! It did " + moveDamage + " damage!");
            }
            else{
              System.out.println("The move missed!");
            }
          }
          else{
            System.out.println(player.getActivePokemon().getName() + " fainted!");
            player.removeActivePokemon();
          }
        }
      }

      
      
    }

    
    
  }

  public void endScreen(){
    // if(battleType.equals("catch")){
    //   if(playerWin){
    //     System.out.println("Congratulations! You caught a " + );
    //   }
    //   else{
    //     System.out.println("Sorry Trainer, the Pokemon you ")
    //   }
    // }
  }

  
  
}