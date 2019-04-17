package view;

import java.util.Random;
import javafx.animation.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Monster;
import model.Player;
import model.Score;

/**
 * Creates the stage to run the game in
 * 
 * @authors: Stage split to a new class by Brianna McBurney 
 * Created by Mostafa
 */
public class GameStage extends Stage {

    //controls that used in all methods through this class
    private final Button pauseBtn;
    private final Button resumeBtn;

    private TranslateTransition transition;
    private Timeline time;

    private static int levelCount;

    private final Label lblCurrentLevel;
    private final Label lblPlayerName;
    private final Label lblPlayerAttackStrength;
    private final Label lblTime;
    private final Label lblMonsterHealth;
    private final Label lblScore;

    private final Player playerOfChoice;
    private Monster currentMonster;
    private Monster monster;

    private final VBox gameLayoutLeft;
    private final VBox gameLayoutRight;

    private final ImageView playerImage;
    private final ImageView monsterImage;

    private final Integer startTime = 20;
    private Integer seconds = startTime;

    private final Timeline timeline;
    private final Random random = new Random();

    /**
     * Sets up the stage for the game
     * @author Mostafa
     *
     * @param playerOfChoice The player that the user selected
     */
    public GameStage(Player playerOfChoice) {
        this.playerOfChoice = playerOfChoice;

        // create label to show the current level of the game
        lblCurrentLevel = new Label("Level 1");

        // initialize levelCount to first level
        levelCount = 1;

        // create label to display the name the user entered before the game started
        lblPlayerName = new Label();
        lblPlayerName.setText("Name: " + playerOfChoice.getName());

        // create label to display the strength of the players attack
        lblPlayerAttackStrength = new Label();
        lblPlayerAttackStrength.setText("Attack Strength: " + playerOfChoice.getAttackStrength());

        // create the label to show the user how much time they have left
        lblTime = new Label("Ramaining time: 20");

        // create image to display the player the user chose
        playerImage = new ImageView(playerOfChoice.getImagePath());
        playerImage.setFitHeight(600);
        playerImage.setFitWidth(500);

        // create an instance of Monster class
        monster = new Monster();

        // call the method to create the array of monsters
        monster.createMonsters();

        // call the method to get the monster for the next level
        currentMonster = monster.getMonsters(levelCount);

        // create label to show how much health the monster has
        lblMonsterHealth = new Label("Monster Health: " + currentMonster.getHealth());

        // create label to display the user's score
        lblScore = new Label("Score: " + playerOfChoice.getScore());

        // create a ListView as well as a observable list and add the info to it
        ObservableList info = FXCollections.observableArrayList(lblCurrentLevel, lblPlayerName, lblPlayerAttackStrength, lblTime, lblMonsterHealth, lblScore);
        ListView playerInfo = new ListView<>(info);
        playerInfo.setPrefHeight(info.size() * 32);
        playerInfo.setMouseTransparent(true);
        playerInfo.setFocusTraversable(false);

        // create pause and resume buttons, and add setOnAction to call the appropriate methods
        pauseBtn = new Button("Pause");
        pauseBtn.setPrefWidth(150);
        pauseBtn.setPrefHeight(80);
        pauseBtn.setOnAction(e -> pause());
        resumeBtn = new Button("Resume");
        resumeBtn.setPrefWidth(150);
        resumeBtn.setPrefHeight(80);
        resumeBtn.setOnAction(e -> resume());

        // create a HBox to hold pause and resume button
        HBox pauseNresumeLayout = new HBox(20);
        pauseNresumeLayout.setAlignment(javafx.geometry.Pos.CENTER);
        pauseNresumeLayout.getChildren().addAll(pauseBtn, resumeBtn);

        // create root layout of the game structure
        gameLayoutLeft = new VBox();
        gameLayoutLeft.setPadding(new Insets(10));
        gameLayoutLeft.setStyle("-fx-background-color: lightBlue");
        
        // add the elements to the layout for the left side of the game
        gameLayoutLeft.getChildren().addAll(playerInfo, playerImage, pauseNresumeLayout);

        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        
        // create the monster image
        monsterImage = new ImageView(currentMonster.getImagePath());
        monsterImage.setFitHeight(100);
        monsterImage.setFitWidth(100);

        // event handling for when the user 'attacks' the monster
        monsterImage.setOnMousePressed(event -> onMonsterAction());

        // create pane to hold the monster's image
        AnchorPane root = new AnchorPane(monsterImage);

        // create the layout for the right side of the game
        gameLayoutRight = new VBox();
        gameLayoutRight.setPadding(new Insets(10));
        gameLayoutRight.getChildren().add(root);
        
        
        // create the layout to hold everything
        HBox gameLayoutOriginal = new HBox();
        gameLayoutOriginal.setPadding(new Insets(10));
        gameLayoutOriginal.setStyle("-fx-background-color: DarkBlue");
        gameLayoutOriginal.getChildren().addAll(gameLayoutLeft, gameLayoutRight);

        // create the scene
        Scene game = new Scene(gameLayoutOriginal);
        
        // set the scene
        this.setScene(game);

        //to make the stage full screan 
        this.setMaximized(true);
        
        // start the animation to move the monster
        keyFrameAction();

        // start the timer
        doTime();

        // show the stage
        this.show();

    }

    /**
     * Using an instance of key frame and TranslateTransition to control the animation for the monster and time
     * @author Mostafa
     */
    private void keyFrameAction() {
        // set the starting point for the monster
        monsterImage.setLayoutX(100);
        monsterImage.setLayoutY(100);
        
        // create instance of Pos class to use random coordinates
        Pos endPos = new Pos();

        // create instance of TranslateTransition
        transition = new TranslateTransition();
        
        // set how long each movement would take
        transition.setDuration(Duration.seconds(2));
        
        // setting the coordinates for the end position of each movement
        transition.setToX(endPos.x);
        transition.setToY(endPos.y);
        
        // set monster to move forever
        transition.setCycleCount(Animation.INDEFINITE);
        transition.setAutoReverse(true);
        
        // apply the Node of monster image to the TranslateTransition
        transition.setNode(monsterImage);
        transition.play();

        // restart timeline with new values after each movement has finished
        timeline.stop();
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(2000),
                (e) -> keyFrameAction()));
        timeline.play();
    }

    /**
     * Pauses the game when the user clicks the 'pause' button
     * @author Mostafa
     */
    private void pause() {
        // stop the user from attacking the monster while the game is paused
        monsterImage.setOnMousePressed(null);
        
        // stop the animation
        transition.stop();
        timeline.stop();
        
        // stop the timer
        time.stop();
    }

    /**
     * Resumes the game
     * @author Mostafa
     */
    private void resume() {
        // allow the player to attack the monster again
        monsterImage.setOnMousePressed(event -> onMonsterAction());
        
        // resume the animation
        transition.play();
        timeline.play();
        
        // start the timer again
        time.play();
    }

    /**
     * Gets random coordinates for the monster to move to
     * @author Mostafa
     */
    private class Pos {

        int x = random.nextInt(1100);
        int y = random.nextInt(750);
    }

    /**
     * Controls the timer 
     * @author Mostafa
     *
     */
    private void doTime() {
        time = new Timeline();
        time.setCycleCount(Timeline.INDEFINITE);
        if (time != null) {
            time.stop();
        }
        
        // create KeyFrame, passing duration and EventHandler to the constructor
        KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                seconds--;
                lblTime.setText("Remaining time: " + seconds.toString());
                
                // check if the time has run out or the player has completed the last level
                if (seconds <= 0 || levelCount > 29) {
                    
                    // (Brianna)add the players score to the list in the file
                    Score sc = new Score();
                    sc.addNewScore(playerOfChoice.getName(), playerOfChoice.getScore());

                    // pause the timer
                    time.stop();

                    // call the method to open the GameOverStage
                    changeStage();
                }
            }

        });
        time.getKeyFrames().add(frame);
        time.playFromStart();
    }
    
    /**
     * Creating an instance of gameOverStage class to change stage when time is up or the player wins the game with the appropriate message
     * @authro Mostafa
     */
    private void changeStage() {
        if (levelCount > 29) { // player has completed all the levels
            
            // don't assign to return value because it will never be used
            new GameOverStage(playerOfChoice, "You are winner. You beated all of monsters");   
            
        } else { // player has run out of time
            
            // don't assign to return value because it will never be used
            new GameOverStage(playerOfChoice, "You ran out of time!");
        }
        this.close();
    }

    /**
     * Make appropriate changes to move to the next level
     * @author Brianna McBurney
     */
    public void changeLevel() {
        monster = new Monster();

        // go to the next level
        levelCount += 1;

        // update the label that displays the current level
        lblCurrentLevel.setText("Level " + String.valueOf(levelCount));

        // increase the players attackStrength |and update the label to display it
        Player player = new Player();
        playerOfChoice.setAttackStrength(player.levelUp(playerOfChoice));
        lblPlayerAttackStrength.setText("Attack Strength: " + playerOfChoice.getAttackStrength());

        // increase the players score
        int remainingSec = seconds;
        playerOfChoice.setScore(Score.increaseScore(playerOfChoice.getScore(), remainingSec));

        // update the players score on the ListView
        lblScore.setText("Score: " + String.valueOf(playerOfChoice.getScore()));

        // reset the time to 20 seconds
        seconds = 20;

        // make sure there is a monster for the next level
        if (monster.getMonsters(levelCount) != null) {
            
            // get the monsterImage for the next nevel
            currentMonster = monster.getMonsters(levelCount);

            // update ImageView with new monsterImage for next level
            Image newMonster = new Image(currentMonster.getImagePath());
            monsterImage.setImage(newMonster);

            // udpate monsterHealthLbl with new monsters health
            lblMonsterHealth.setText("Monster Health: " + currentMonster.getHealth());
        }
    }

    /**
     * If the player has clicked on the monster to 'attack' it, inflict damage on
     * the monster. Make sure the monster isn't dead then reflect those changes
     * in the GUI. If the monster is dead change the monster image to explosion 
     * and go to the next level
     * @author Brianna McBurney
     */
    public void onMonsterAction() {
        // cause 'damage' to the monsters health
        int monsterHealth = monster.damage(playerOfChoice, currentMonster);

        // check and see if the monster still has health
        if (monsterHealth > 0) {
            // if he still has health update the Label that displays the monster's health
            currentMonster.setHealth(monsterHealth);
            lblMonsterHealth.setText("Monster Health: " + monsterHealth);
        } else { // the monster has died so alert the user and let them go to the next level
            // stop the timer
            time.stop();
            
            // set the monster's image to an explosion
            Image deadMonster = new Image("file:resources\\pictures\\special_effects\\explosion.png");
            monsterImage.setImage(deadMonster);
            
            // call method to display the alert that the player has killed the monster and can go to the next level
            AlertBox.informAlert("DEAD MONSTER", "You killed the monster! You may now proceed to the next level");
                
            // chaneg levels
            changeLevel();
                
            // reusme the timer
            time.play();
        }
    }
}
