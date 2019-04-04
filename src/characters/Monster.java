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
    public int damage(Player player, Monster monster){
        int playerAttack = player.getAttackStrength();
        int monsterHealth = monster.getHealth();
        
        int newHealth = monsterHealth - playerAttack;
        
        monster.setHealth(newHealth);
        
        return newHealth;
    }
    
    /**
     * Get the appropriate monster for the current level of the game
     * 
     * @param currentLevel The level that the game is at
     * @return The monster that corresponds with the level the game is at
     */
    public Monster getMonsters(int currentLevel) {        
        File selectedFile = openFile();
        
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
    /**
     * Create all the Monsters and save them to a file.
     *  
     * Icons made by Smashicons (https://smashicons.com/)
     * From https://www.flaticon.com/
     * Licensed by Creative Commons 3.0 BY (http://creativecommons.org/licenses/by/3.0/)
     */
    public void createMonsters() {
        File selectedFile = openFile();
        
        // initialize ArrayList to hold monsters
        monsterArray = new ArrayList<>();
        
        // intialize ArrayList to hold monster images
        monsterImageArray = new ArrayList<>();
        
        // create the monsters
        for (int i=1; i <= 30; i++) {
            // set the amount of health the monster will have
            int monsterHealth = i*5;
            
            // create the path to the monster's image
            String path = "file:resources/pictures/monsters/monster" + i + ".png";
            
            // create the new Monster
            Monster monster = new Monster("Monster1", i, monsterHealth, path);
            
            // add the monster to the array
            monsterArray.add(monster);
            
            // create ImageView to hold the picture for the monster
            Image monsterView = new Image(path);
            
//            System.out.println(monsterView);
            
            try {
                // add the monster's ImageView to th arrray
                monsterImageArray.add(monsterView);
            } catch (NullPointerException ex) {
                System.out.println(ex.toString());
            }
        }
        
        // Create the ArrayList to hold the monsters to add to the file
        ArrayList<JSONObject> monstersToWrite = new ArrayList<>();

        // Try-with-resources
        try (PrintWriter writer = new PrintWriter(selectedFile)) {

            // iterate through all monsters in the Array
            for(int i=0; i < monsterArray.size(); i++) {

                // create an object with the Monster
                JSONObject monstersToAdd = new JSONObject();
                monstersToAdd.put("name", monsterArray.get(i).getName());
                monstersToAdd.put("health", monsterArray.get(i).getHealth());
                monstersToAdd.put("level", monsterArray.get(i).getLevel());
                monstersToAdd.put("image path", monsterArray.get(i).getImagePath());

                // add Course to array
                monstersToWrite.add(monstersToAdd);
            }

            // Create JSONObject to hold the array of monsters
            JSONObject root = new JSONObject();

            // Add the courses to the root object
            root.put("monsters", monstersToWrite);

            // write the Object holding the array of monsters to the file
            writer.print(root);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Character.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public File openFile() {
        // create Strings for the path to the folder and file that will hold the Monsters
        String MCfolder = System.getProperty("user.home") + "\\Desktop\\MonsterCombat";
        String MCfile = MCfolder+"\\monsterArray.json";
        
        File folder = new File(MCfolder);
        
        File file = new File(MCfile);
        
        // check if there is already a folder to store the high scores
        if (! folder.isDirectory()){
            // if theres no folder create the folder
            new File(MCfolder).mkdirs();
            
            // add file to store the monsters in
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
            }
                      
        } else {
            // check if the folder contains the file to store the monster array in
            if (! file.exists()) {                
                // if it doesnt exist create a new file in the right folder
                try {
                    // create the file
                    file.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
        }
        
        File selectedFile = new File(MCfile);
        
        return selectedFile; 
    }
}