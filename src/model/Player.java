package model;

import java.util.ArrayList;

/**
 * 
 * @author Brianna McBurney
 */
public class Player extends Character {
    private int attackStrength;
    private int score;

    /**
     * Creates a Player for the user to control.
     * @param name The name of the Player
     * @param attackStrength How much damage the Player's attack can do
     * @param imagePath The file path to the image of the player
     * @param score The score the player accumulates
     */
    public Player(String name, int attackStrength, String imagePath, int score) {
        super(name, imagePath);
        this.attackStrength = attackStrength;
        this.score = score;
    }
    
    public Player(){
        
    };

    public void setAttackStrength(int attackStrength) {
        this.attackStrength = attackStrength;
    }

    public int getAttackStrength() {
        return attackStrength;
    }   

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    /**
     * @param player
     */
    public void levelUp(Player player) {
        player.setAttackStrength(attackStrength + 4 );
    }
    
    public ArrayList<Player> getPlayers() {
        ArrayList<Player> playerList = new ArrayList<>();
        
        Player p1 = new Player("Player 1", 5, "file:resources\\pictures\\player\\player1.png", 0);
        Player p2 = new Player("Player 2", 6, "file:resources\\pictures\\player\\player2.png", 0);
        Player p3 = new Player("Player 3", 7, "file:resources\\pictures\\player\\player3.png", 0);
        
        playerList.add(p1);
        playerList.add(p2);
        playerList.add(p3);
        
        return playerList;
    }

}