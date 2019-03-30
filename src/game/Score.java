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
import javafx.stage.Stage;
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
     * @author Brianna McBurney
     * 
     * @param selectedFile
     */
    public void getHighScores(File selectedFile) {
        
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
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
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
     * @author Brianna McBurney
     * 
     * @param primaryStage     * 
     * @return  
     */
    public File openFile(Stage primaryStage) {

        String MCfolder = System.getProperty("user.home") + "\\Desktop\\MonsterCombat";
        String MCfile = MCfolder+"\\highScores.json";
        
        File folder = new File(MCfolder);
        
        File file = new File(MCfile);
        
        // check if there is already a folder to store the high scores
        if (! folder.isDirectory()){
            // if theres no folder create the folder
            new File(MCfolder).mkdirs();
            
            // add file to store the high scores in
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
            }
                      
        } else {
            // check if the folder contains the file to store the high scores in
            if (! file.exists()) {
                
                // if it doesnt exist create a new file in the right folder
                try {
                    file.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
        }
        
        File selectedFile = new File(MCfile);
        
        return selectedFile; 
    }
    
    public void addNewScore(File selectedFile, String name, String score){
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
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
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