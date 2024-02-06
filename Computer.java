import java.util.ArrayList;
class Computer {

  private Pokemon[] battlePokemon;
  private Pokemon activePokemon = null;

  public Computer() {
    this.battlePokemon = new Pokemon[6];
  }

  public boolean canSwap(){
    for(Pokemon pokemon: battlePokemon){
      if(pokemon != null && pokemon != activePokemon){
        if(pokemon.isUsable()){
          return true;
        }
      }
    }
    return false;
  }

  public boolean pokemonSelected(){
    return activePokemon != null;
  }

  public boolean chance(int percent){
    return (int) (Math.random() * 100) < percent;
  }

  public boolean canMove(){
    return activePokemon.isUsable() || canSwap();
  }

  public int getUsablePokemon(){
    int count = 0;
    for(Pokemon pokemon: battlePokemon){
      if(pokemon != null){
        if(pokemon.isUsable()){
          count += 1;
        }
      }
    }
    return count;
  }

  public void removeActivePokemon(){
    activePokemon = null;
  }

  public Pokemon getActivePokemon(){
    return activePokemon;
  }

  public int performMove(Move move, Pokemon targetPokemon){
    return activePokemon.performMove(move, targetPokemon);
  }

  public Move generateMove(){
    if(canSwap()){
      if(chance(20)){
        int index = (int) (Math.random() * getUsablePokemon());
        swapPokemon(index);
        return Movedex.getMove("SWAP");
      }
    }
    return getRandomMove();
  }

  public Move getRandomMove(){
    int index = (int) (Math.random() * 4);
    return activePokemon.getMove(index);
  }

  public void swapPokemon(int index){
    activePokemon = battlePokemon[index];
  }

  public void generateRoster(int rosterSize){
    System.out.println("hello");
    for(int i = 0; i < rosterSize; i++){
      this.battlePokemon[i] = Pokedex.getWildPokemon();
    }
  }
  
}