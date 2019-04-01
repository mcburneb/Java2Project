package characters;

import java.util.ArrayList;

/**
 * 
 * @author Brianna McBurney
 */
public class Player extends Character {
    private int attackStrength;
    private String imagePath;

    /**
     * Creates a Player for the user to control.
     * @param name The name of the Player
     * @param attackStrength How much damage the Player's attack can do
     * @param imagePath The file path to the image of the player
     */
    public Player(String name, int attackStrength, String imagePath) {
        super(name);
        this.attackStrength = attackStrength;
        this.imagePath = imagePath;
    }
    
    public Player(){
        System.out.println("here");
    };

    public void setAttackStrength(int attackStrength) {
        this.attackStrength = attackStrength;
    }

    public int getAttackStrength() {
        return attackStrength;
    }

    public String getImagePath() {
        return imagePath;
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
    
    public ArrayList<Player> getPlayers() {
        ArrayList<Player> playerList = new ArrayList<>();
        
        Player p1 = new Player("Player 1", 5, "file:resources/pictures/player/player1.png");
        Player p2 = new Player("Player 2", 6, "file:resources/pictures/player/player2.png");
        Player p3 = new Player("Player 3", 7, "file:resources/pictures/player/player2.png");
        
        playerList.add(p1);
        playerList.add(p2);
        playerList.add(p3);
        
        return playerList;
    }

}