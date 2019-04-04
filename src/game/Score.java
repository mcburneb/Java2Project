package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Brianna McBurney
 */
public class Score {
    private final int score;
    private final String name;

    public Score(int score, String name) {
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
     * get the high scores from the file
     * 
     * TO DO: display the scores in a GUI
     */
    public void getHighScores() {
        File selectedFile = new File("file:resources\\files\\highScores.json");
        
        ArrayList<JSONObject> scoreList = new ArrayList<>();
        if (selectedFile == null) return;
        try {
            Scanner input = new Scanner(selectedFile);
            StringBuilder builder = new StringBuilder();
            
            while (input.hasNextLine()) {          
                builder.append(input.nextLine());
            }
            
            JSONParser parser = new JSONParser();
            JSONObject root = (JSONObject) parser.parse(builder.toString());
            
            JSONArray highScores = (JSONArray) root.get("highScores"); 

            // put scores into an array: each name+score will be an object in the array
            for (int i = 0; i < highScores.size(); i++) {
                scoreList.add((JSONObject) highScores.get(i));
            }
            
        } catch (FileNotFoundException | ParseException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // sort high scores from highest to lowest
        Collections.sort(scoreList, new scoreComparator());            

        //display the top 10 scores
        System.out.println("\t NAME \t\t SCORE");
        int numScoresToShow;
        if (scoreList.size() < 10) {
            numScoresToShow = scoreList.size();
        } else {
            numScoresToShow = scoreList.size();
        }
        
        for (int i = 0; i < numScoresToShow; i++) {
            if (! scoreList.get(i).isEmpty()) {
                JSONObject entry = (JSONObject) scoreList.get(i);
                String score = (String) entry.get("score");
                String name = (String) entry.get("name");

                System.out.println((i+1) + ")\t" + name + "\t\t" + score);
            } else {
                break;
            }
        }
    }
    
    /**
     * Adding a new score to the High Score file
     * 
     * @param name The name the user provided at the start of the game
     * @param score The total score the user has accumulated
     */
    public void addNewScore(String name, String score){
        File selectedFile = new File("file:resources\\files\\highScores.json");
        
        JSONArray updatedScoreList = new JSONArray();
        if (selectedFile == null) return;
        try {
            // check if the file is empty or not
            if (selectedFile.length() > 0) {
                Scanner input = new Scanner(selectedFile);
                StringBuilder builder = new StringBuilder();

                while (input.hasNextLine()) {          
                    builder.append(input.nextLine());
                }

                JSONParser parser = new JSONParser();
                JSONObject root = (JSONObject) parser.parse(builder.toString());

                JSONArray oldScoreList = (JSONArray) root.get("highScores"); 

                // put scores into an array: each name+score will be an object in the array
                for (int i = 0; i < oldScoreList.size(); i++) {
                    updatedScoreList.add((JSONObject) oldScoreList.get(i));
                }
            }            
            
             // create new object to store the new score
            JSONObject newScore = new JSONObject();

            newScore.put("name", name);
            newScore.put("score", score);
            
            // add new score to the array of scores
            updatedScoreList.add((JSONObject) newScore);
            
            // add array to the highScores name-value pair
            JSONObject updatedHighScores = new JSONObject();
            updatedHighScores.put("highScores", updatedScoreList);            
            
            PrintWriter writer = null;
            try {
                // 2nd param, false, overrides the contents of the file
                FileWriter fileWriter = new FileWriter(selectedFile, false);

                writer = new PrintWriter(fileWriter);
                
                writer.println(updatedHighScores);
                
                // TO DO: add alert telling user their score has been added
                
            } catch (IOException ex) {
                Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
            
        } catch (FileNotFoundException | ParseException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @author Brianna McBurney
     * sort scores from highest to lowest
     */
    public class scoreComparator implements Comparator<JSONObject> {
        
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