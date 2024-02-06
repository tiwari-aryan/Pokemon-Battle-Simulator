class Move {

  private String name;
  private String type;
  private String attackType;
  private int powerPoints;
  private int power;
  private int accuracy;
  private int learnLevel;

  public Move(String name, String type, String attackType, int powerPoints, int power, int accuracy, int learnLevel){
    this.name = name;
    this.type = type;
    this.attackType = attackType;
    this.powerPoints = powerPoints;
    this.power = power;
    this.accuracy = accuracy;
    this.learnLevel = learnLevel;
  }

  public Move(Move copyMove, int learnLevel){
    this.name = copyMove.getName();
    this.type = copyMove.getType();
    this.attackType = copyMove.getAttackType();
    this.powerPoints = copyMove.getPowerPoints();
    this.power = getPower();
    this.accuracy = getAccuracy();
    this.learnLevel = learnLevel;
  }

  public void printMove(){
    System.out.println("Name: " + name);
    System.out.println("Type: " + type);
    System.out.println("Attack Type: " + attackType);
    System.out.println("Power Points: " + powerPoints);
    System.out.println("Power: " + power);
    System.out.println("Accuracy: " + accuracy);
  }

  public String getName(){
    return name;
  }

  public int getLevel(){
    return learnLevel;
  }

  public int getPower(){
    return power;
  }

  public String getType(){
    return type;
  }

  public String getAttackType(){
    return attackType;
  }

  public int getPowerPoints(){
    return powerPoints;
  }

  public int getAccuracy(){
    return accuracy;
  }
  
}