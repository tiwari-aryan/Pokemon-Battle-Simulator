import java.util.ArrayList;

class Pokemon {

  private String name;
  private String[] types;
  private int hp;
  private int xp;
  private int level;
  private int attack;
  private int defense;
  private int specialAttack;
  private int specialDefense;
  private int speed;
  private ArrayList <Move> moveSet;
  private Move[] battleMoves;
  // make booleans for all the status effects
  // make a function called check status that applies effects if the booleans are true

  public Pokemon(String name, String[] types, int[] pokemonStatValues, ArrayList <Move> moveSet) {
    this.name = name;
    this.types = types;
    this.level = 1;
    this.xp = 0;
    this.hp = pokemonStatValues[0];
    this.attack = pokemonStatValues[1];
    this.defense = pokemonStatValues[2];
    this.specialAttack = pokemonStatValues[3];
    this.specialDefense = pokemonStatValues[4];
    this.speed = pokemonStatValues[5];
    this.moveSet = new ArrayList <Move>(moveSet);
    this.battleMoves = new Move[4];
  }

  public Pokemon(Pokemon copyPokemon, int level){
    System.out.println("hello");
    this.name = copyPokemon.getName();
    this.types = copyPokemon.getTypes();
    this.level = level;
    this.xp = 0;
    this.hp = copyPokemon.getHp();
    this.attack = copyPokemon.getAttack();
    this.defense = copyPokemon.getDefense();
    this.specialAttack = copyPokemon.getSpecialAttack();
    this.specialDefense = copyPokemon.getSpecialDefense();
    this.speed = copyPokemon.getSpeed();
    this.moveSet = new ArrayList <Move>(copyPokemon.getMoveSet());
    this.battleMoves = new Move[4];
    generateRandomMoves();
  }

  public void generateRandomMoves(){
    int moveIndex;
    for(int i = 0; i < 4; i++){
      moveIndex = (int) (Math.random() * moveSet.size());
      while(!swapMoves(moveIndex, i));
    }
  }

  public void updateStats(){
    Pokemon basePokemon = Pokedex.getPokemon(this.name);
    this.hp = basePokemon.getHp() + (2 * this.level);
    this.attack = basePokemon.getAttack() + (2 * this.level);
    this.defense = basePokemon.getDefense() + (2 * this.level);
    this.specialAttack = basePokemon.getSpecialAttack() + (2 * this.level);
    this.specialDefense = basePokemon.getSpecialDefense() + (2 * this.level);
    this.speed = basePokemon.getSpeed() + (2 * this.level);
  }

  public void updatePokemon(){
    if(this.xp == 100 * this.level){
      this.level += 1;
      this.xp = 0;
      this.hp += 5;
      this.attack += 5;
      this.defense += 5;
      this.specialAttack += 5;
      this.specialDefense += 5;
      this.speed += 5;
    }
  }

  public void printStats(){
    System.out.println("Name: " + this.name);
    System.out.println("Level: " + this.level);
    System.out.println("HP: " + this.hp);
    System.out.println("Attack: " + this.attack);
    System.out.println("Defense: " + this.defense);
    System.out.println("Special Attack: " + this.specialAttack);
    System.out.println("Special Defense: " + this.specialDefense);
    System.out.println("Speed: " + this.speed);
  }

  public void printMoves(){
    for(int i = 0; i < 4; i++){
      if(battleMoves[i] != null){
        System.out.println("[" + (i+1) + "]" + this.battleMoves[i].getName());
      }
      else{
        System.out.println("[" + (i+1) + "] (empty slot)");
      }
    }
  }

  public void printMoveSet(){
    for(int i = 0; i < moveSet.size(); i++){
      System.out.println("[" + (i+1) + "]" + this.moveSet.get(i).getName());
    }
  }

  public boolean swapMoves(int moveIndex, int swapIndex){
    for(Move move: battleMoves){
      if(move == moveSet.get(moveIndex)){
        return false;
      }
    }
    battleMoves[swapIndex] = moveSet.get(moveIndex);
    return true;
  }

  public int calculateDamage(Move move, Pokemon targetPokemon){
    
    double STAB = 1;
    for(String type: types){
      if(type.equals(move.getType())){
        STAB = 1.5;
      }
    }

    double multiplier = 1;

    for(String type: targetPokemon.getTypes()){
      multiplier *= Type.getEffectiveness(move.getType(), type);
    }
    
    int damage = (int) (((((2 * this.level / 5 + 2) * this.attack * move.getPower() / targetPokemon.getDefense()) / 50) + 2) * STAB * multiplier);

    return damage;
    
  }

  public Move getMove(int moveIndex){
    return battleMoves[moveIndex];
  }

  public int performMove(Move move, Pokemon targetPokemon){
    int accuracyChance = (int) (Math.random() * 100) + 1;
    int damage;
    if(accuracyChance < move.getAccuracy()){
      damage = calculateDamage(move, targetPokemon);
    }
    else{
      damage = 0;
    }

    targetPokemon.takeDamage(damage);
    return damage;
    
  }

  public void setLevel(int level){
    this.level = level;
  }

  public void takeDamage(int damage){
    this.hp -= damage;
  }

  public String[] getTypes(){
    return this.types;
  }

  public String getName() {
    return name;
  }

  public int getLevel() {
    return level;
  }

  public boolean isUsable() {
    return hp > 0;
  }

  public ArrayList <Move> getMoveSet(){
    return moveSet;
  }

  public int[] getStats() {
    int[] stats = {hp, attack, defense, specialAttack, specialDefense, speed};
    return stats;
  }

  public int getHp() {
    return hp;
  }

  public int getSpeed() {
    return speed;
  }

  public int getAttack() {
    return attack;
  }

  public int getDefense() {
    return defense;
  }

  public int getSpecialAttack() {
    return specialAttack;
  }

  public int getSpecialDefense() {
    return specialDefense;
  }

}