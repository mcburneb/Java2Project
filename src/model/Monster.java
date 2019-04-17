package model;

import controller.Attack;
import java.util.ArrayList;

/**
 * 
 * @author Brianna McBurney
 */
public class Monster extends Character implements Attack {

    private int level;
    private int health;
    
    private static ArrayList<Monster> monsterArray;

    public Monster(String name, int level, int health, String imagePath) {
        super(name, imagePath);
        this.level = level;
        this.health = health;
    }
    
    public Monster() {  }

    public int getLevel() {
        return level;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Lower the monster's health
     * Overrides the damage method from the Attack interface
     * 
     * @param player
     * @param monster
     * @return The monster's new health
     */
    @Override
    public int damage(Player player, Monster monster) {
        // get the players attackStrength and the monsters current health
        int playerAttack = player.getAttackStrength();
        int monsterHealth = monster.getHealth();

        // deal the damage to the monsters health
        int newHealth = monsterHealth - playerAttack;
        monster.setHealth(newHealth);    
        
        return newHealth;
    }
    
    /**
     * Create the ArrayList that will hold all of the Monsters and fill it with Monsters 
     */
    public void createMonsters() {
        monsterArray = new ArrayList<>();
                
        for (int i=1; i <= 30; i++) {
            int monsterHealth = i*10;
            String path = "file:resources\\pictures\\monsters\\png\\monster-" + i + ".png";

            Monster monster = new Monster("Monster "+i, i, monsterHealth, path);

            monsterArray.add(monster);
        }
    }
    
    /**
     * Get the appropriate monster for the current level of the game
     * 
     * @param currentLevel The level that the game is at
     * @return The monster that corresponds with the next level
     */
    public Monster getMonsters(int currentLevel) {  
        
        // the index of the monster for the next level
        int index = currentLevel-1;
        
        Monster nextMonster = null;
        try {
            nextMonster = monsterArray.get(index);            
        } catch (NullPointerException ex) {            
            System.out.println(ex.getMessage());            
        }
        
        return nextMonster;
    }
}