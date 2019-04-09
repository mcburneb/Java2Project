package view;

import model.Score;
import model.Monster;
import model.Player;
import java.util.ArrayList;
import java.util.Random;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 *
 * @author Mostafa
 */
public class Game extends Application {

    private TextField playerName;

    private Button join;
    private Button instructions;
    private Button pauseBtn;
    private Button resumeBtn;

    private TranslateTransition transition;
    private Timeline time;

    private Scene game;

    private static int levelCount;

    private Label levelLbl;
    private Label gameName;
    private Label playerNameLbl;
    private Label playerOfChoiceLbl;
    private Label playerAttackStrengthLbl;
    private Label timeLabel;
    private Label monsterHealthLbl;
    private Label scoreLbl;

    private Player playerOfChoice;
    private Monster currentMonster;

    private VBox startPageLayout;
    private HBox modeNjoin;
    private VBox gameLayoutLeft;
    private VBox gameLayoutRight;

    private Scene scene;

    private ImageView playerImage;
    private ImageView monsterImage;
    private ImageView p1Image;
    private ImageView p2Image;
    private ImageView p3Image;

    private ListView<Label> playerInfo;

    private Integer startTime = 20;
    private Integer seconds = startTime;

    private Timeline timeline;
    private final Random random = new Random();

    @Override
    public void start(Stage startUpStage) {
        startUpStage.setTitle("Start Up");

        startPageLayout = new VBox(17);
        startPageLayout.setStyle("-fx-background-color: black");
        startPageLayout.setPadding(new Insets(20));

        gameName = new Label("MONSTER COMBAT");
        gameName.setFont(Font.loadFont("file:resources/font/DragonForcE.ttf", 150));
        gameName.setTextFill(Color.web("#0076a9"));

        playerName = new TextField();
        playerName.setStyle("-fx-border-color: blue;" + "-fx-background-color: lightblue");
        playerName.setPromptText("Enter your name");

        playerOfChoiceLbl = new Label("Choose a player");

        createPlayerChoice();

        // grade HBox to hold player options and add the players Images to it
        HBox playerImages = new HBox();
        playerImages.getChildren().addAll(p1Image, p2Image, p3Image);

        join = new Button("Join");
        join.setOnAction(e -> onJoinClick(startUpStage));
        join.setPrefWidth(160);

        // create button to allow user to view the instructions
        instructions = new Button("Instructions");
        instructions.setOnAction(e -> AlertBox.readInstructions(startUpStage));

        modeNjoin = new HBox(20);
        modeNjoin.getChildren().add(join);
        modeNjoin.getChildren().add(instructions);

        startPageLayout.getChildren().addAll(gameName, playerName, playerOfChoiceLbl, playerImages, modeNjoin);

        scene = new Scene(startPageLayout, 1450, 950);
        startUpStage.setScene(scene);

        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds(); //to make the stage center
        startUpStage.setX((primScreenBounds.getWidth() - startUpStage.getWidth()) / 2); //to make the stage center
        startUpStage.setY((primScreenBounds.getHeight() - startUpStage.getHeight()) / 2); // to make the stage center
        startUpStage.show();

        // root layout of the game structure
        gameLayoutLeft = new VBox();
        gameLayoutLeft.setPadding(new Insets(10));

        // label to show the current level of the game
        levelLbl = new Label("Level 1");

        // set level to 1
        levelCount = 1;

        // label to display the name the user entered before the game started
        playerNameLbl = new Label();

        // label to display the strength of the players attack
        playerAttackStrengthLbl = new Label();

        // create the label to show the user how much time they have
        timeLabel = new Label("ramaining time: 20");

        // create image for the player the user chooses
        playerImage = new ImageView("file:resources\\pictures\\player\\player1.png");
        playerImage.setFitHeight(500);
        playerImage.setFitWidth(400);

        Monster m = new Monster();

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
        pauseBtn.setOnAction(e -> pause());
        resumeBtn = new Button("resume");
        resumeBtn.setOnAction(e -> resume());

        //player information framework
        gameLayoutLeft.getChildren().addAll(playerInfo, playerImage, pauseBtn, resumeBtn);

        gameLayoutRight = new VBox();
        gameLayoutRight.setPadding(new Insets(10));

        // create the monster image
        monsterImage = new ImageView(currentMonster.getImagePath());
        monsterImage.setFitHeight(100);
        monsterImage.setFitWidth(100);

        // event handling for when the user 'attacks' the monster
        monsterImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            String stringMonsterHealth = m.damage(playerOfChoice, currentMonster);
            int newMonsterHealth = Integer.parseInt(stringMonsterHealth);

            if (newMonsterHealth > 0) {
                currentMonster.setHealth(newMonsterHealth);
                monsterHealthLbl.setText("Monster Health: " + stringMonsterHealth);
            } else {
                // TO DO: stop the counter

                // move the game to the next level
                boolean changeLvl = AlertBox.nextLevelAlert();
                if (changeLvl) {
                    //startTime = 20;
                    changeLevel();
                }
            }
        });

        HBox gameLayoutOriginal = new HBox();
        gameLayoutOriginal.setPadding(new Insets(10));
        gameLayoutOriginal.setStyle("-fx-background-color: DarkBlue");
        gameLayoutOriginal.getChildren().addAll(gameLayoutLeft, gameLayoutRight);

        game = new Scene(gameLayoutOriginal, 1450, 950);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void onJoinClick(Stage startUpStage) {
        // make sure the user has entered a name for their player
        if (playerName.getText().isEmpty()) {
            AlertBox.informAlert("Game Requirement", "You need to enter a name \nbefore joining the game");
        } else if (playerOfChoice == null) { // make sure the user selected a character
            AlertBox.informAlert("Game Requirement", "You need to select a character \nbefore joining the game");
        } else {
            // get input from the user for their players name
            String playerNameValue = playerName.getText();

            // change Players name to the input
            playerOfChoice.setName(playerNameValue);

            // show the players name in the label that is added to the ListView
            playerNameLbl.setText("Name: " + playerNameValue);
            playerAttackStrengthLbl.setText("Attack Strength: " + playerOfChoice.getAttackStrength());

            startUpStage.setScene(game);

            doTime();
        }

        // init timeline
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        AnchorPane root = new AnchorPane(monsterImage);
        gameLayoutRight.getChildren().add(root);
        keyFrameAction();

    }

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
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(2000), (e) -> keyFrameAction()));
        timeline.play();
    }

    private Pos getRandomPos() {
        int x = random.nextInt(500);
        int y = random.nextInt(400);
        Pos p = new Pos();
        p.x = x + 100;
        p.y = y + 100;
        return p;
    }

    private void pause() {
        timeline.stop();
        time.stop();
    }

    private void resume() {
        timeline.play();
        time.play();
    }

    private void createPlayerChoice() {
        // create the player options to display 
        ArrayList<Player> playerList = null;
        Player p = new Player();
        playerList = p.getPlayers();

        p1Image = new ImageView(playerList.get(0).getImagePath());
        p1Image.setFitHeight(400);
        p1Image.setFitWidth(400);
        Player p1 = playerList.get(0);
        p1Image.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            // set the selected player as the player of choice
            setPlayerOfChoise(p1);
        });

        p2Image = new ImageView(playerList.get(1).getImagePath());
        p2Image.setFitHeight(400);
        p2Image.setFitWidth(400);
        Player p2 = playerList.get(1);
        p2Image.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            // set the selected player as the player of choice
            setPlayerOfChoise(p2);
        });

        p3Image = new ImageView(playerList.get(2).getImagePath());
        p3Image.setFitHeight(400);
        p3Image.setFitWidth(400);
        Player p3 = playerList.get(2);
        p3Image.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            // set the selected player as the player of choice
            setPlayerOfChoise(p3);
        });
    }

    private class Pos {

        int x;
        int y;
    }

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
                timeLabel.setText("remaining time: " + seconds.toString());
                if (seconds <= 0) {
                    time.stop();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("time is up");
                    alert.show();
                    timeline.stop();

                    // TO DO: show end of game GUI, allows user to add their score to the list & see the list of top 10
                }
            }

        });
        time.getKeyFrames().add(frame);
        time.playFromStart();
    }

    /**
     * @author Brianna McBurney
     *
     * @param chosenPlayer The player the user has clicked on
     */
    private void setPlayerOfChoise(Player chosenPlayer) {
        // set the selected player as the plater of choice
        playerOfChoice = chosenPlayer;
        playerOfChoiceLbl.setText(chosenPlayer.getName());

        Image image = new Image(playerOfChoice.getImagePath());

        playerImage.setImage(image);
    }

    /**
     * @author Brianna McBurney
     */
    public void changeLevel() {
        Monster m = new Monster();

        // go to the next level
        levelCount += 1;

        // get the monsterImage for the next nevel
        currentMonster = m.getMonsters(levelCount);

        // update ImageView with new monsterImage for next level
        Image newMonster = new Image(currentMonster.getImagePath());
        monsterImage.setImage(newMonster);;

        // udpate monsterHealthLbl with new monsters health
        monsterHealthLbl.setText("Monster Health: " + currentMonster.getHealth());

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
    }
}
