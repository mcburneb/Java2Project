package game;

import characters.*;
import java.util.ArrayList;

/**
 *
 * @author Brianna McBurney
 * Student Number: 991517337
 * Date: February 27, 2019
 */
public class Game {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        /*
        - get user input for name and weapon choice (choices stored in an enum data field)
        - set players stats to same thing all across the board
        - put player up against level 1 monster
        - give player the 3 action options: attack, block, heal
        - player must defeat monster before they die
        - if monster dies the players health will level up and the player can level up one of the three actions
        - player gains points for defeating monsters plus a certain bonus if they did it in a certain amount of time
        - the players heal is replenished after each match and the player gets to pick if they level up regeneration, attack strength, or block strength
        - the game ends when the player dies 3 times
        */

        // have buttons to show the characters current stats and the Score Board (store in a file)
    }

    /**
    * Show the current statistics of the player
    * @param player Get current Players information
    */
    public void showStats(Player player) {
        // TODO - implement Game.showStats
    }

    /**
     * Show the high scores from the computer
     */
    public void showScoreBoard() {
        // TODO - implement Game.showScoreBoard
    }

    /**
     * Generate the Monsters for each level
     */
    public void renderMonsters() {        
        ArrayList<Monster> monsterList = new ArrayList<>();
        // TODO - implement Game.createMonster
    }
}
