package characters;

import game.Score;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * 
 * @author Brianna McBurney
 */
public class Monster extends Character {

    private int level;
    private int health;
    private String imagePath;
    
    private ArrayList<Monster> monsterArray;
    private ArrayList<Image> monsterImageArray;

    /**
     * Creates a Monster for the Player to fight.
     * @param level The level in which the Monster will attack the Player
     * @param name The name of the Monster
     * @param health How much health the Monster has
     * @param imagePath
     */
    public Monster(String name, int level, int health, String imagePath) {
        super(name);
        this.level = level;
        this.health = health;
        this.imagePath = imagePath;
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

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public int damage(Player player, Monster monster) {
        int playerAttack = player.getAttackStrength();
        int monsterHealth = monster.getHealth();

        int newHealth = monsterHealth - playerAttack;

        monster.setHealth(newHealth);
        
        String stringHealth = String.valueOf(newHealth);

        return newHealth;
    }
    
    /**
     * Get the appropriate monster for the current level of the game
     * 
     * @param currentLevel The level that the game is at
     * @return The monster that corresponds with the level the game is at
     *
     * Icons made by Smashicons (https://smashicons.com/)
     * From https://www.flaticon.com/
     * Licensed by Creative Commons 3.0 BY (http://creativecommons.org/licenses/by/3.0/)
     */
    public Monster getMonsters(int currentLevel) {        
        File selectedFile = new File("file:resources\\files\\monsterArray.json");
        
        // initialize a Monster object to hold the monster for the current level
        Monster monster = null;
        
        // Create the ArrayList to hold the Monsters read from the file
        ArrayList<JSONObject> monstersFromFile = new ArrayList<>();

        try {
            // Create a scanner to open on the file the user selected
            Scanner input = new Scanner(selectedFile);

            // Create a StringBuilder to add the contents of the file to
            StringBuilder builder = new StringBuilder();

            // loop through and read each line of the file
            while (input.hasNextLine()) {
                builder.append(input.nextLine());
            }

            JSONParser parser = new JSONParser();
            JSONObject root = (JSONObject) parser.parse(builder.toString());

            // Create JSONArray to hold the Courses from the file
            JSONArray monstsers = (JSONArray) root.get("courses");

            // Add each of the Courses to the ArrayList 
            for (int i = 0; i < monstsers.size(); i++) {
                monstersFromFile.add((JSONObject) monstsers.get(i));
            }

            // get the Monster for the level the game is at
            JSONObject entry = (JSONObject) monstersFromFile.get(currentLevel);

            // get values of the Monster's name, level, health, and image file path
            String monsterName = (String) entry.get("name");
            int monsterLevel = (int) entry.get("level");
            int monsterHealth = (int) entry.get("health");
            String monsterImagePath = (String) entry.get("image path");

            // create a Course with the name and grade
            monster = new Monster(monsterName, monsterLevel, monsterHealth, monsterImagePath);

        } catch (FileNotFoundException | ParseException ex) {
            Logger.getLogger(Monster.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return monster;
    }
}