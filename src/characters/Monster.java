package characters;

import java.util.ArrayList;

/**
 * 
 * @author Brianna McBurney
 */
public class Monster extends Character implements Attack {

    private int level;
    private int health;
    
    private ArrayList<Monster> monsterArray;

    /**
     * Creates a Monster for the Player to fight.
     * @param level The level in which the Monster will attack the Player
     * @param name The name of the Monster
     * @param health How much health the Monster has
     * @param imagePath
     */
    public Monster(String name, int level, int health, String imagePath) {
        super(name, imagePath);
        this.level = level;
        this.health = health;
    }
    
    public Monster() {
        
    }

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
     * 
     * @param player
     * @param monster
     * @return The monster
     */
    @Override
    public String damage(Player player, Monster monster) {
        int playerAttack = player.getAttackStrength();
        int monsterHealth = monster.getHealth();

        int newHealth = monsterHealth - playerAttack;
        
        monster.setHealth(newHealth);

        String newHealthString = String.valueOf(newHealth);
        
        return newHealthString;
    }
    
        /**
     * Icons made by Smashicons (https://smashicons.com/)
     * From https://www.flaticon.com/
     * Licensed by Creative Commons 3.0 BY (http://creativecommons.org/licenses/by/3.0/)
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
        
        // level 1 = monster at 0
        // level 2 = monster at 1
        // level 3 = monster at 2
        Monster nextMonster = null;
        try {
            
            nextMonster = monsterArray.get(index);
            
        } catch (NullPointerException ex) {
            
            System.out.println(ex.getMessage());
            
        }
        
        return nextMonster;
    }
}