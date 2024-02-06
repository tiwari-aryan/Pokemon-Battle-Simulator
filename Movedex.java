import java.io.*;
import java.util.HashMap;

class Movedex {

  private static HashMap <String, Move> moveList;

  public static void initMovedex(){

    moveList = new HashMap <String, Move>();

    try{
      FileReader fr = new FileReader("move_list.txt");
      BufferedReader br = new BufferedReader(fr);

      String data, moveName = "", moveType = "", attackType = "";
      int pp = 0, power = 0, accuracy = 0;
      Move newMove;
      
      while((moveName = br.readLine()) != null){
        moveType = br.readLine();
        attackType = br.readLine();
        pp = Integer.parseInt(br.readLine());
        power = Integer.parseInt(br.readLine());
        accuracy = Integer.parseInt(br.readLine());
        newMove = new Move(moveName, moveType, attackType, pp, power, accuracy, 0);
        moveList.put(moveName, newMove);
        br.readLine();
      }
      
    }
    catch(IOException e){
      e.printStackTrace();
    }
    
  }
  
  public static Move getMove(String name){
    return moveList.get(name);
  }

  public static boolean containsMove(String name){
    return moveList.containsKey(name);
  }
  
}