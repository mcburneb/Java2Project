package model;

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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Brianna McBurney
 */
public class Score {

    private int score;
    private String name;

    public Score(int score, String name) {
        this.score = score;
        this.name = name;
    }

    public Score() {

    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    /**
     * Get the high scores from the file
     *
     * @return The list of scores
     */
    public ObservableList getHighScores() {
        // get the file that holds the scores
        File selectedFile = new File("src/fileResources/files/highScores.json");

        // create ArrayList to hold the scores from the file
        ArrayList<JSONObject> scoreList = new ArrayList<>();
        try {
            Scanner input = new Scanner(selectedFile);
            StringBuilder builder = new StringBuilder();

            // get all the scores from the file and put them in a string builder
            while (input.hasNextLine()) {
                builder.append(input.nextLine());
            }

            // parse the string builder to a JSONobject
            JSONParser parser = new JSONParser();
            JSONObject root = (JSONObject) parser.parse(builder.toString());

            // create the JSONarray to hold the scores
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

        // store all the scores in an list
        ObservableList<Score> scores = FXCollections.observableArrayList();
        for (int i = 0; i < scoreList.size(); i++) {
            if (!scoreList.get(i).isEmpty()) {
                JSONObject fileEntry = (JSONObject) scoreList.get(i);
                String scoreFromFile = (String) (fileEntry.get("score"));
                int scoreInt = Integer.valueOf(scoreFromFile);
                String nameFromFile = (String) fileEntry.get("name");

                // add the score to the list
                scores.add(new Score(scoreInt, nameFromFile));
            } else {
                break;
            }
        }

        return scores;
    }

    /**
     * Adding a new score to the High Score file
     *
     * @param name The name the user provided at the start of the game
     * @param score The total score the user has accumulated
     */
    public void addNewScore(String name, int score) {
        // get the file that stores the scores
        File selectedFile = new File("src/fileResources/files/highScores.json");

        // create JSONarray to hold the scores
        JSONArray updatedScoreList = new JSONArray();
        try {
            // check if the file is empty or not
            if (selectedFile.length() > 0) {
                Scanner input = new Scanner(selectedFile);
                StringBuilder builder = new StringBuilder();

                while (input.hasNextLine()) {
                    builder.append(input.nextLine());
                }

                // convert the string builder to a JSONobject
                JSONParser parser = new JSONParser();
                JSONObject root = (JSONObject) parser.parse(builder.toString());

                // create JSONarray to hold the scores
                JSONArray oldScoreList = (JSONArray) root.get("highScores");

                // put scores into an array: each name and score will be an object in the array
                for (int i = 0; i < oldScoreList.size(); i++) {
                    updatedScoreList.add((JSONObject) oldScoreList.get(i));
                }
            }

            // create new object to store the new score
            JSONObject newScore = new JSONObject();

            // add the new score to the JSONobject
            newScore.put("name", name);
            newScore.put("score", String.valueOf(score));

            // add new score to the array of scores
            updatedScoreList.add((JSONObject) newScore);

            // add array to the highScores name-value pair
            JSONObject updatedHighScores = new JSONObject();
            updatedHighScores.put("highScores", updatedScoreList);

            PrintWriter writer = null;
            try {
                // create fileWriter that will override the previous contents of the file
                FileWriter fileWriter = new FileWriter(selectedFile, false);
                
                writer = new PrintWriter(fileWriter);

                // add the updated list of scores to the file
                writer.println(updatedHighScores);

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
     * Increases the score by 100 + the remaining time, after every level the
     * monster is defeated
     *
     * @param currentScore Current score of the player
     * @param timeLeft The time remaining on the counter when the monster dies
     * @return The increased score
     */
    public static int increaseScore(int currentScore, int timeLeft) {

        int newScore = currentScore + 100 + timeLeft;

        return newScore;
    }

    /**
     * Sort the scores from highest to lowest
     */
    public class scoreComparator implements Comparator<JSONObject> {

        @Override
        public int compare(JSONObject obj1, JSONObject obj2) {
            String s1 = (String) (obj1.get("score"));
            String s2 = (String) (obj2.get("score"));

            int score1 = Integer.parseInt(s1);
            int score2 = Integer.parseInt(s2);

            int returnValue = 0;
            if (score1 < score2) {
                returnValue = 1;
            } else if (score1 > score2) {
                returnValue = -1;
            }

            return returnValue;
        }
    }

}