package view;

import java.util.Random;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Monster;
import model.Player;
import model.Score;

/**
 *
 * @author Brianna McBurney
 */
public class GameStage extends Stage {

    private Button pauseBtn;
    private Button resumeBtn;

    private TranslateTransition transition;
    private Timeline time;

    private static int levelCount;

    private Label levelLbl;
    private Label playerNameLbl;
    private Label playerOfChoiceLbl;
    private Label playerAttackStrengthLbl;
    private Label timeLabel;
    private Label monsterHealthLbl;
    private Label scoreLbl;

    private Player playerOfChoice;
    private Monster currentMonster;
    private Monster m;
    
    private VBox gameLayoutLeft;
    private VBox gameLayoutRight;

    private ImageView playerImage;
    private ImageView monsterImage;

    private ListView<Label> playerInfo;

    private Integer startTime = 20;
    private Integer seconds = startTime;

    private Timeline timeline;
    private final Random random = new Random();

    /**
     * @author Mostafa
     * 
     * @param playerOfChoice
     */
    public GameStage(Player playerOfChoice){
        this.playerOfChoice = playerOfChoice;
        
         // root layout of the game structure
        gameLayoutLeft = new VBox();
        gameLayoutLeft.setPadding(new Insets(10));

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
        playerImage = new ImageView("file:resources\\pictures\\player\\player1.png");
        playerImage.setFitHeight(500);
        playerImage.setFitWidth(400);

        m = new Monster();

        // create the monsters
        m.createMonsters();

        // get the next monster
        currentMonster = m.getMonsters(levelCount);

        // show how much health the first monster has
        monsterHealthLbl = new Label("Monster Health: " + currentMonster.getHealth());

        scoreLbl = new Label("Score: 0");

        // create a ListView and add the info to it
        playerInfo = new ListView<>();
        playerInfo.getItems().add(levelLbl);
        playerInfo.getItems().add(playerNameLbl);
        playerInfo.getItems().add(playerAttackStrengthLbl);
        playerInfo.getItems().add(timeLabel);
        playerInfo.getItems().add(monsterHealthLbl);
        playerInfo.getItems().add(scoreLbl);

        pauseBtn = new Button("Pause");
        pauseBtn.setPrefWidth(100);
        pauseBtn.setOnAction(e -> pause());
        resumeBtn = new Button("resume");
        resumeBtn.setPrefWidth(100);
        resumeBtn.setOnAction(e -> resume());

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

        Scene game = new Scene(gameLayoutOriginal, 1450, 950);
        this.setScene(game);
        
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        this.setX(bounds.getMinX());
        this.setY(bounds.getMinY());
        this.setWidth(bounds.getWidth());
        this.setHeight(bounds.getHeight());
        
        this.show();
        
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        AnchorPane root = new AnchorPane(monsterImage);
        gameLayoutRight.getChildren().add(root);
        keyFrameAction();

        doTime();
    }
    
    /**
     * @author Mostafa
     */
    private void keyFrameAction() {
        monsterImage.setLayoutX(100);
        monsterImage.setLayoutY(100);
        Pos endPos = getRandomPos();

        transition = new TranslateTransition();

        transition.setDuration(Duration.seconds(2));
        transition.setToX(endPos.x);
        transition.setToY(endPos.y);
        transition.setCycleCount(Animation.INDEFINITE);
        transition.setAutoReverse(true);
        transition.setNode(monsterImage);
        transition.play();

        monsterImage.setScaleX(1);
        monsterImage.setScaleY(1);
        monsterImage.setOpacity(1);

        // restart timeline with new values
        timeline.stop();
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(2000),
                (e) -> keyFrameAction()));
        timeline.play();
    }

    /**
     * @author Mostafa
     * @return
     */
    private Pos getRandomPos() {
        int x = random.nextInt(500);
        int y = random.nextInt(400);
        Pos p = new Pos();
        p.x = x + 100;
        p.y = y + 100;
        return p;
    }

    /**
     * @author Mostafa
     */
    private void pause() {
        monsterImage.setOnMousePressed(null);
        transition.stop();
        timeline.stop();
        time.stop();
    }

    /**
     * @author Mostafa
     */
    private void resume() {
        monsterImage.setOnMousePressed(event -> onMonsterAction());
        transition.play();
        timeline.play();
        time.play();
    }

    /**
     * @author Mostafa
     */
    private class Pos {

        int x;
        int y;
    }

    /**
     * @author Mostafa
     */
    private void doTime() {
        time = new Timeline();
        time.setCycleCount(Timeline.INDEFINITE);
        if (time != null) {
            time.stop();
        }
        KeyFrame frame;
        frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                seconds--;
                timeLabel.setText("Remaining time: " + seconds.toString());
                if (seconds <= 0) {
                    // add the players score to the list in the file
                    Score sc = new Score();
                    sc.addNewScore(playerOfChoice.getName(), playerOfChoice.getScore());
                    
                    time.stop();
                    timeline.stop();
                    
                    GameOverStage stage = new GameOverStage(playerOfChoice, "You ran out of time!");
                }
            }

        });
        time.getKeyFrames().add(frame);
        time.playFromStart();
    }

    /**
     * @author Brianna McBurney
     *
     * When the player clicks on a character in the main menu, make that
     * character the one that they will play the game as
     * @param chosenPlayer The player the user has clicked on
     */
    private void setPlayerOfChoise(Player chosenPlayer) {
        // set the chosen player as the plater of choice
        playerOfChoice = chosenPlayer;
        playerOfChoiceLbl.setText(chosenPlayer.getName());

        // add the chosen players Image to the ImageView
        Image image = new Image(playerOfChoice.getImagePath());
        playerImage.setImage(image);
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
        p.levelUp(playerOfChoice);

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
