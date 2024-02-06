import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
class Pokedex {

  private static HashMap <String, Pokemon> pokedexList;
  private static ArrayList <String> pokedexNames;
  private static int pokedexSize;

  public static void initPokedex(){

    try {
      FileReader fr = new FileReader("pokemon_list.txt");
      BufferedReader br = new BufferedReader(fr);

      pokedexList = new HashMap <String, Pokemon>();
      pokedexNames = new ArrayList <String>();
      
      String data, pokemonName = "", pokemonMove;
      String[] pokemonTypes = new String[2], pokemonStats = new String[6];
      int pokemonMoveLevel;
      int[] pokemonStatValues = new int[6];
      ArrayList <Move> pokemonMoves = new ArrayList <Move>();
      Pokemon newPokemon;

      while((data = br.readLine()) != null){
        if(data.equals("Name")){
          pokemonName = br.readLine();
          pokedexNames.add(pokemonName);
        }
        else if(data.equals("Types")){
          pokemonTypes = br.readLine().split(" ");
        }
        else if(data.equals("Stats")){
          pokemonStats = br.readLine().split(" ");
          for(int i = 0; i < 6; i++){
            pokemonStatValues[i] = Integer.parseInt(pokemonStats[i]);
          }
        }
        else if(data.equals("Start Moves")){
          String pokemonMoveData;
          while(!(pokemonMoveData = br.readLine()).equals("End Moves")){
            pokemonMoveLevel = Integer.parseInt(pokemonMoveData);
            pokemonMove = br.readLine();
            if(Movedex.containsMove(pokemonMove)){
              pokemonMoves.add(new Move(Movedex.getMove(pokemonMove), pokemonMoveLevel));
            }
          }
        }
        else if(data.equals("NEXT POKEMON")){
          newPokemon = new Pokemon(pokemonName, pokemonTypes, pokemonStatValues, pokemonMoves);
          pokedexList.put(pokemonName, newPokemon);
          pokemonMoves.clear();
        }
      }
      pokedexSize = 8;
      
    }
    catch(IOException e){
      e.printStackTrace();
    }
    
  }

  public static HashMap <String, Pokemon> getPokedex(){
    return pokedexList;
  }

  public static Pokemon getPokemon(String name){
    if(pokedexList.containsKey(name)){
      return pokedexList.get(name);
    }
    return null;
  }

  public static Pokemon getWildPokemon(){
    System.out.println("hello");
    int index = (int) (Math.random() * pokedexSize);
    int level = (int) (Math.random() * 100) + 1;
    System.out.println(pokedexList.get(pokedexNames.get(index)).getName());
    Pokemon wildPokemon = new Pokemon(pokedexList.get(pokedexNames.get(index)), level);
    System.out.println("hello");
    return wildPokemon;
    
  }
  
}