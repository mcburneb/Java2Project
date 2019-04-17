package model;

import java.util.ArrayList;

/**
 * 
 * @author Brianna McBurney
 */
public class Player extends Character {
    private int attackStrength;
    private int score;

    public Player(String name, int attackStrength, String imagePath, int score) {
        super(name, imagePath);
        this.attackStrength = attackStrength;
        this.score = score;
    }
    
    public Player(){    }

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
     * Level up the players attack strength
     * @author Brianna McBurney
     * @param player
     * @return updated attackStrength
     */
    public int levelUp(Player player) {
        int playerAttackStrength = (player.getAttackStrength()+2);
        
        return playerAttackStrength;
    }
    /**
     * Create the players that the user can pick from
     * @author Brianna McBurney
     * @return arrayList of player
     */
    public static ArrayList<Player> getPlayers() {
        ArrayList<Player> playerList = new ArrayList<>();
        
        Player KrazetsPlayer = new Player("Krazets", 5, "file:resources\\pictures\\player\\player1.png", 0);
        Player FegnoidPlayer = new Player("Fegnoid", 6, "file:resources\\pictures\\player\\player2.png", 0);
        Player MildraPlayer = new Player("Mildra", 7, "file:resources\\pictures\\player\\player3.png", 0);
        
        playerList.add(KrazetsPlayer);
        playerList.add(FegnoidPlayer);
        playerList.add(MildraPlayer);
        
        return playerList;
    }

}