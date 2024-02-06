import java.util.ArrayList;
class Player {
  
  public int pokeballs;
  public ArrayList <Pokemon> pokemonList = new ArrayList <Pokemon>();
  public Pokemon[] battlePokemon = new Pokemon[6];
  public boolean turn = false;
  public Pokemon activePokemon = null;
  public Pokemon targetPokemon = null;

  public Player(int pokeballs, ArrayList <Pokemon> pokemonList, Pokemon[] battlePokemon){
    this.pokeballs = pokeballs;
    this.pokemonList = pokemonList;
    for(int i = 0; i < 6; i++){
      this.battlePokemon[i] = battlePokemon[i];
    }
  }

  public boolean canMove(){
    return activePokemon.isUsable() || canSwap();
  }

  public boolean canSwap(){
    return numUsablePokemon() > 0;
  }

  public void addPokemon(String pokemonName){
    this.pokemonList.add(new Pokemon(Pokedex.getPokemon(pokemonName), 1));
  }
  
  public boolean pokemonSelected(){
    return activePokemon != null;
  }

  public int getPokeballs(){
    return pokeballs;
  }

  public ArrayList <Pokemon> getPokemonList(){
    return pokemonList;
  }

  public int performMove(Move move, Pokemon targetPokemon){
    return activePokemon.performMove(move, targetPokemon);
  }

  public int numUsablePokemon(){
    int count = 0;
    for(Pokemon pokemon: battlePokemon){
      if(pokemon != null && pokemon != activePokemon){
        if(pokemon.isUsable()){
          count += 1;
        }
      }
    }
    return count;
  }

  public Pokemon getPokemon(int index){
    return pokemonList.get(index);
  }
  
  public Pokemon getBattlePokemon(int index){
    return battlePokemon[index];
  }

  public boolean setBattlePokemon(int swapIndex, int pokemonIndex){
    for(int i = 0; i < 6; i++){
      if(battlePokemon[i] != null){
        if(battlePokemon[i] == pokemonList.get(pokemonIndex)){
          return false;
        }
      }
    }
    
    battlePokemon[swapIndex] = pokemonList.get(pokemonIndex);
    return true;
  }

  public boolean checkBattlePokemon(Pokemon p){
    for(Pokemon pokemon: battlePokemon){
      if(pokemon == p){
        return true;
      }
    }
    return false;
  }

  public Pokemon getActivePokemon(){
    return activePokemon;
  }

  public void setActivePokemon(int index){
    activePokemon = battlePokemon[index];
  }

  public void removeActivePokemon(){
    activePokemon = null;
  }

  public void printRoster(){
    for(int i = 0; i < 6; i++){
      if(battlePokemon[i] != null){
        System.out.println("[" + (i+1) + "] " + battlePokemon[i].getName());
      }
      else{
        System.out.println("[" + (i+1) + "] (empty slot)");
      }
    }
  }

  public void printPokemon(){
    int i = 1;
    for(Pokemon pokemon: pokemonList){
      System.out.println("[" + (i) + "] " + pokemon.getName());
      i++;
    }
  }
  
}