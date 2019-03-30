package characters;

/**
 * 
 * @author Brianna McBurney
 */
public class Player extends Character {
    private int attackStrength;

    /**
     * Creates a Player for the user to control.
     * @param name The name of the Player
     * @param attackStrength How much damage the Player's attack can do
     */
    public Player(String name, int attackStrength) {
        super(name);
        this.attackStrength = attackStrength;
    }

    public int getAttackStrength() {
        return attackStrength;
    }

    public void setAttackStrength(int attackStrength) {
        this.attackStrength = attackStrength;
    }
    
    /**
     * @author Brianna McBurney
     * 
     * @param player
     * @param monster
     * @return The monsters new health
     */
    @Override 
    public int damage(Player player, Monster monster){
        int playerAttack = player.getAttackStrength();
        int monsterHealth = monster.getHealth();
        
        int newHealth = monsterHealth - playerAttack;
        
        monster.setHealth(newHealth);
        
        return newHealth;
    }
    
    /**
     * @author Brianna McBurney
     * @param player
     * @return The outcome of the leveling up
     */
    public String levelUp(Player player) {
        
        player.setAttackStrength(attackStrength + 2);
        
        return "You have sucessfully leveled up your attack";
    }

}