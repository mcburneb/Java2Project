package characters;

public abstract class Character {

    private final String name;
    private int health;
    private int regeneration;
    private int attackStrength;
    private int blockStrength;

    /**
     * 
     * @param name The name of the Player
     * @param regeneration How much health the Character can regenerate at a time
     * @param attackStrength How much damage the Character's attack can do
     * @param blockStrength How strong the Character's block is
     */
    public Character(String name, int health, int regeneration, int attackStrength, int blockStrength) {
        this.name = name;
        this.health = health;
        this.regeneration = regeneration;
        this.attackStrength = attackStrength;
        this.blockStrength = blockStrength;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
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
    
    /**
     * 
     * @param character
     * @return The outcome of the attack
     */
    public String attack(Character character) {
        String attackMessage = "Attack happened";
        // determine if its the player or monster attacking
        if (character instanceof Monster ){
            // remove the amount of the mosnters attack from the player health
            // create message about the status of the attack
        } else if (character instanceof Player) {
            // remove the amount of the players attack from the monsters health
            // create message about the status of the attack
        }
        return attackMessage;
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