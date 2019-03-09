package characters;

public abstract class Character implements Attack{

    private final String name;
    private int regeneration;
    private int attackStrength;
    private int blockStrength;
    private int health;

    /**
     * 
     * @param name The name of the Player
     * @param regeneration How much health the Character can regenerate at a time
     * @param attackStrength How much damage the Character's attack can do
     * @param blockStrength How strong the Character's block is
     * @param health
     */
    public Character(String name, int regeneration, int attackStrength, int blockStrength, int health) {
        this.name = name;
        this.regeneration = regeneration;
        this.attackStrength = attackStrength;
        this.blockStrength = blockStrength;
        this.health = health;
    }

    public String getName() {
        return name;
    }

    public int getRegeneration() {
        return regeneration;
    }

    public void setRegeneration(int regeneration) {
        this.regeneration = regeneration;
    }

    public int getAttackStrength() {
        return attackStrength;
    }

    public void setAttackStrength(int attackStrength) {
        this.attackStrength = attackStrength;
    }

    public int getBlockStrength() {
        return blockStrength;
    }

    public void setBlockStrength(int blockStrength) {
        this.blockStrength = blockStrength;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    
    @Override 
    public int damage(int monsterHealth){
        monsterHealth--;
        return monsterHealth;
    }
    
    /**
     * 
     * @param character
     * @return The outcome of the regeneration
     */
    public String regenerate(Character character) {
        String regenMessage = "You have healed";
        
        // determine if its the player or monster regenerating
        if (character instanceof Monster ){
            // add the regenerationAmount to the monsters health
            // create message about the statuc of the regeneration
        } else if (character instanceof Player) {
            // add the regenerationAmount to the players health
            // create message about the statuc of the regeneration
        }
        
        return regenMessage;
    }
    
    /**
     * 
     * @param character
     * @return The outcome of the block
     */
    public String block(Character character) {
        String blockMessage = "You blocked _% of the attack";
        
        // determine if its the player or monster blocking
        if (character instanceof Monster ){
            // determin how much of the players attack the monster can block
        } else if (character instanceof Player) {
            // determin how much of the monsters attack the player can block
        }
        
        return blockMessage;
    }

    /**
     * 
     * @param player
     * @param stat The statistic that the player wishes to level up
     * @return The outcome of the leveling up
     */
    public String levelUp(Player player, String stat) {
        // TODO - implement Character.levelUpRegeneration
        return "You have sucessfully leveled up your " + stat;
    }
}