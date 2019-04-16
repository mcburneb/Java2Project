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

    private final Label levelLbl;
    private final Label playerNameLbl;
    private final Label playerAttackStrengthLbl;
    private final Label timeLabel;
    private final Label monsterHealthLbl;
    private final Label scoreLbl;

    private final Player playerOfChoice;
    private Monster currentMonster;
    private Monster m;

    private final VBox gameLayoutLeft;
    private final VBox gameLayoutRight;

    private final ImageView playerImage;
    private final ImageView monsterImage;

    private final Integer startTime = 20;
    private Integer seconds = startTime;

    private final Timeline timeline;
    private final Random random = new Random();

    /**
     * @author Mostafa
     *
     * @param playerOfChoice
     */
    public GameStage(Player playerOfChoice) {
        this.playerOfChoice = playerOfChoice;

        
        // root layout of the game structure
        gameLayoutLeft = new VBox();
        gameLayoutLeft.setPadding(new Insets(10));
        gameLayoutLeft.setStyle("-fx-background-color: lightBlue");

        // label to show the current level of the game
        levelLbl = new Label("Level 1");

        // set level to 1
        levelCount = 1;

        // label to display the name the user entered before the game started
        playerNameLbl = new Label();
        playerNameLbl.setText("Name: " + playerOfChoice.getName());

        // label to display the strength of the players attack
        playerAttackStrengthLbl = new Label();
        playerAttackStrengthLbl.setText("Attack Strength: " + playerOfChoice.getAttackStrength());

        // create the label to show the user how much time they have
        timeLabel = new Label("Ramaining time: 20");

        // create image for the player the user chooses
        playerImage = new ImageView(playerOfChoice.getImagePath());
        playerImage.setFitHeight(600);
        playerImage.setFitWidth(500);

        m = new Monster();

        // create the monsters
        m.createMonsters();

        // get the next monster
        currentMonster = m.getMonsters(levelCount);

        // show how much health the first monster has
        monsterHealthLbl = new Label("Monster Health: " + currentMonster.getHealth());

        scoreLbl = new Label("Score: 0");

        // create a ListView as well as a observable list and add the info to it
        ObservableList info = FXCollections.observableArrayList(levelLbl, playerNameLbl, playerAttackStrengthLbl, timeLabel, monsterHealthLbl, scoreLbl);
        ListView playerInfo = new ListView<>(info);
        playerInfo.setPrefHeight(info.size() * 32);
        playerInfo.setMouseTransparent(true);
        playerInfo.setFocusTraversable(false);

        // instanciate pause and resume buttons and their action 
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

        //player information framework
        gameLayoutLeft.getChildren().addAll(playerInfo, playerImage, pauseNresumeLayout);

        gameLayoutRight = new VBox();
        gameLayoutRight.setPadding(new Insets(10));

        // create the monster image
        monsterImage = new ImageView(currentMonster.getImagePath());
        monsterImage.setFitHeight(100);
        monsterImage.setFitWidth(100);

        // event handling for when the user 'attacks' the monster
        monsterImage.setOnMousePressed(event -> onMonsterAction());

        HBox gameLayoutOriginal = new HBox();
        gameLayoutOriginal.setPadding(new Insets(10));
        gameLayoutOriginal.setStyle("-fx-background-color: DarkBlue");
        gameLayoutOriginal.getChildren().addAll(gameLayoutLeft, gameLayoutRight);

        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        AnchorPane root = new AnchorPane(monsterImage);
        gameLayoutRight.getChildren().add(root);
        keyFrameAction();

        doTime();

        Scene game = new Scene(gameLayoutOriginal);
        this.setScene(game);

        //to make the stage full screan 
        this.setMaximized(true);

        this.show();

    }

    /**
     * @author Mostafa
     * using an instance of key frame and TranslateTransition to control the animation 
     */
    private void keyFrameAction() {
        monsterImage.setLayoutX(100);
        monsterImage.setLayoutY(100);
        Pos endPos = new Pos();

        transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(2));
        transition.setToX(endPos.x);
        transition.setToY(endPos.y);
        transition.setCycleCount(Animation.INDEFINITE);
        transition.setAutoReverse(true);
        transition.setNode(monsterImage);
        transition.play();

        // restart timeline with new values
        timeline.stop();
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(2000),
                (e) -> keyFrameAction()));
        timeline.play();
    }

    /**
     * @author Mostafa sets of actions for pauseBnm
     */
    private void pause() {
        monsterImage.setOnMousePressed(null);
        transition.stop();
        timeline.stop();
        time.stop();
    }

    /**
     * @author Mostafa sets of actions for resumeBtn
     */
    private void resume() {
        monsterImage.setOnMousePressed(event -> onMonsterAction());
        transition.play();
        timeline.play();
        time.play();
    }

    /**
     * @author Mostafa a short class to make random position for monster
     * movement
     */
    private class Pos {

        int x = random.nextInt(1100);
        int y = random.nextInt(750);
    }

    /**
     * @author Mostafa
     *
     *
     */
    private boolean doTime() {
        boolean returnValue = true;

        time = new Timeline();
        time.setCycleCount(Timeline.INDEFINITE);
        if (time != null) {
            time.stop();
        }
        KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                seconds--;
                timeLabel.setText("Remaining time: " + seconds.toString());
                if (seconds <= 0 || levelCount > 29) {
                    // (Brianna)add the players score to the list in the file
                    Score sc = new Score();
                    sc.addNewScore(playerOfChoice.getName(), playerOfChoice.getScore());

                    time.stop();

                    changeStage();
                }
            }

        });
        time.getKeyFrames().add(frame);
        time.playFromStart();

        return returnValue;
    }
    
    /**
     * an instance of gameOverStage class to change stage when time is up
     */
    private void changeStage() {
        if (levelCount > 29) {
            GameOverStage stage = new GameOverStage(playerOfChoice, "You are winner. You beated all of monsters");            
        } else {
            GameOverStage stage = new GameOverStage(playerOfChoice, "You ran out of time!");
        }
        this.close();
    }

    /**
     * @author Brianna McBurney
     *
     * Changing Levels: - Increase the level count & update the Label that
     * displays it - Level up the player - Increase the score and display the
     * new score - reset timer - Get the monster for the next level, display
     * it's image, and it's initial health - Unless there are no monster left to
     * fight, then display the 'Game Over' stage
     */
    public void changeLevel() {
        m = new Monster();

        // go to the next level
        levelCount += 1;

        // update the lable that displays the current level
        levelLbl.setText("Level " + String.valueOf(levelCount));

        // increase the players attackStrength
        Player p = new Player();
        playerOfChoice.setAttackStrength(p.levelUp(playerOfChoice));
        playerAttackStrengthLbl.setText("Attack Strength: " + playerOfChoice.getAttackStrength());

        // increase the players score
        int remainingSec = seconds;
        playerOfChoice.setScore(Score.increaseScore(playerOfChoice.getScore(), remainingSec));

        // update the score on the ListView
        scoreLbl.setText("Score: " + String.valueOf(playerOfChoice.getScore()));

        // reset the time to 20 seconds
        seconds = 20;

        // make sure there is a monster for the next level
        if (m.getMonsters(levelCount) != null) {
            // get the monsterImage for the next nevel
            currentMonster = m.getMonsters(levelCount);

            // update ImageView with new monsterImage for next level
            Image newMonster = new Image(currentMonster.getImagePath());
            monsterImage.setImage(newMonster);

            // udpate monsterHealthLbl with new monsters health
            monsterHealthLbl.setText("Monster Health: " + currentMonster.getHealth());
        } else {
            // the player has defeated all the monsters do display the 'Game Over' stage
            GameOverStage stage = new GameOverStage(playerOfChoice, "You defeated all the monsters!");
        }
    }

    /**
     * @author Brianna McBurney
     *
     * If the player has clicked on the monster to 'attack' it inflict damage on
     * the monster Make sure the monster isn't dead then reflect those changes
     * in the GUI Else if the monster is dead start going to the next level
     */
    public void onMonsterAction() {
        // cause 'damage' to the monsters health
        String stringMonsterHealth = m.damage(playerOfChoice, currentMonster);
        int newMonsterHealth = Integer.parseInt(stringMonsterHealth);

        // check and see if the monster still has health
        if (newMonsterHealth > 0) {
            // if he still has health update the Label on the stage with the new health
            currentMonster.setHealth(newMonsterHealth);
            monsterHealthLbl.setText("Monster Health: " + stringMonsterHealth);
        } else {
            // the monster has died so alert the user and let them go to the next level
            time.stop();
            boolean changeLvl = AlertBox.nextLevelAlert();
            if (changeLvl) {
                changeLevel();
                time.play();
            }
        }
    }
}
