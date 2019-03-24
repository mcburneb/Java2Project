package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Brianna McBurney
 */
public class score {
    private final int score;
    private final String name;

    public score(int score, String name) {
        this.score = score;
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }
    
    /**
     * @author Paul Bonenfant
     * @modifier Brianna McBurney
     * @param primaryStage 
     */
    public void readJSON(Stage primaryStage) {
    
        // prompt user to choose the file that they stored their high scores i
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(primaryStage);
        
        if (file == null) return;
        try {
            Scanner input = new Scanner(file);
            StringBuilder builder = new StringBuilder();
            
            while (input.hasNextLine()) {          
                builder.append(input.nextLine());
            }
            
            JSONParser parser = new JSONParser();
            JSONObject root = (JSONObject) parser.parse(builder.toString());
            
            JSONArray highScores = (JSONArray) root.get("highScores"); 

            // put scores into an array: each name+score will be an object in the array
            ArrayList<JSONObject> scoreList = new ArrayList<>();
            for (int i = 0; i < highScores.size(); i++) {
                scoreList.add((JSONObject) highScores.get(i));
            }

            // sort high scores from highest to lowest
            Collections.sort(scoreList, new JSONComparator());            
            
            //display the top 10 scores
            System.out.println("\t NAME \t\t SCORE");
            for (int i = 0; i < 10; i++) {
                
                JSONObject entry = (JSONObject) scoreList.get(i);
                String score = (String) entry.get("score");
                String name = (String) entry.get("name");
                
                System.out.println((i+1) + ")\t" + name + "\t\t" + score);
            
            }            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(score.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(score.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

    /**
     * sort scores from highest to lowest
     */
    public class JSONComparator implements Comparator<JSONObject> {
        
        @Override
        public int compare(JSONObject obj1, JSONObject obj2) {
            String s1 = (String) (obj1.get("score"));
            String s2 = (String) (obj2.get("score"));
            
            int score1 = Integer.parseInt(s1);
            int score2 = Integer.parseInt(s2);
            
            int returnValue = 0;             
            if (score1 < score2){
                returnValue = 1;
            } else if (score1 > score2){
                returnValue = -1;
            }
        
            return returnValue;
        }
    }
}
