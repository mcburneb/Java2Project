/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import characters.Attack;
import characters.Monster;
import characters.Player;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 *
 * @author Mostafa
 */
public class Game extends Application implements Attack {
    
    private Stage startUpStage;

    private TextField playerName;

    private Button join;

    private Scene game;

    private Label levelNum;
    private Label playerNameOnScreenDisplay;
    private Label gameName;

    private String playerName1;

    private VBox startPageLayout;

    private Stage startupStage;

    private HBox modeNjoin;
    private VBox gameLayoutLeft;
    private VBox gameLayoutRight;

    private ComboBox<String> playMode;

    private Scene scene;

    private ImageView hero;
    private ImageView monster ;

    private ListView<Label> heroInfo;

    private final Integer startTime = 20;
    private Integer seconds = startTime;
    private Label timeLabel;
    
    private Timeline timeline;
    private final Random random = new Random();

    @Override
    public void start(Stage startUpStage) {
        startUpStage.setTitle("Start Up");

        startPageLayout = new VBox(17);
        startPageLayout.setStyle("-fx-background-color: black");
        startPageLayout.setPadding(new Insets(20));

        gameName = new Label("MONSTER\n          COMBAT");
        gameName.setFont(Font.loadFont("file:resources/font/DragonForcE.ttf", 60));
        gameName.setTextFill(Color.web("#0076a9"));

        playerName = new TextField();
        playerName.setStyle("-fx-border-color: blue;" + "-fx-background-color: Red");
        playerName.setPromptText("Enter your name");

        modeNjoin = new HBox(20);

        playMode = new ComboBox<>();
        playMode.setPrefWidth(160);
        String[] playChoices = {"single player", "two player"};
        ObservableList playChoice = FXCollections.observableArrayList(playChoices);
        playMode.setItems(playChoice);
        playMode.getSelectionModel().select(0);

        join = new Button("Join");
        join.setOnAction(e -> onJoinClick(startUpStage));
        join.setPrefWidth(160);

        modeNjoin.getChildren().addAll(playMode, join);

        startPageLayout.getChildren().addAll(gameName, playerName, modeNjoin);

        scene = new Scene(startPageLayout, 380, 300);
        startUpStage.setScene(scene);

        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds(); //to make the stage center
        startUpStage.setX((primScreenBounds.getWidth() - startUpStage.getWidth()) / 2); //to make the stage center
        startUpStage.setY((primScreenBounds.getHeight() - startUpStage.getHeight()) / 2); // to make the stage center
        startUpStage.show();

        // root layout of the game structure
        gameLayoutLeft = new VBox();
        gameLayoutLeft.setPadding(new Insets(10));

        levelNum = new Label();
        playerNameOnScreenDisplay = new Label();

        //hero ImageView
        hero = new ImageView("file:resources/pictures/hero/hero.png");
        hero.setFitHeight(650);
        hero.setFitWidth(650);

        heroInfo = new ListView<>();
        heroInfo.getItems().add(playerNameOnScreenDisplay);

        timeLabel = new Label();

        doTime();

        heroInfo.getItems().add(timeLabel);

        //hero information framework
        StackPane heroInfoLayout = new StackPane();
        gameLayoutLeft.getChildren().addAll(levelNum, hero, playerNameOnScreenDisplay, heroInfo);

        gameLayoutRight = new VBox();
        gameLayoutRight.setPadding(new Insets(10));

        monster = new ImageView("file:resources/pictures/monsters/monster.png");
        
        
        
        monster.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onMonsterClick());
        monster.setFitHeight(100);
        monster.setFitWidth(100);
        

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
        if (playerName.getText().isEmpty()) {
            AlertBox.display("Game Requairements", "You need to enter a name \nbefor joining the game");
        } else {

            playerName1 = playerName.getText();
            startUpStage.setScene(game);
            playerNameOnScreenDisplay.setText(playerName1);

        }
        
        //init timeline
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        AnchorPane root = new AnchorPane(monster);
        gameLayoutRight.getChildren().add( root);
        keyFrameAction();
        
    }
    private void keyFrameAction() {
        // generate next random start and end positions for star
        Pos startPos = getRandomPos();
        Pos endPos = getRandomPos();

        // initial values (resetting)
        monster.setLayoutX(startPos.x);
        monster.setLayoutY(startPos.y);
        monster.setScaleX(1);
        monster.setScaleY(1);
        monster.setOpacity(1);

        // restart timeline with new values
        timeline.stop();
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000),
                (e) -> keyFrameAction()));
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

    private void onMonsterClick() {
        System.out.println("attack");    
    }

    private class Pos {

        int x;
        int y;
    }

    @Override 
    public int damage(Player player, Monster monster){
        int playerAttack = player.getAttackStrength();
        int monsterHealth = monster.getHealth();
        
        int newHealth = monsterHealth - playerAttack;
        
        monster.setHealth(newHealth);
        
        return newHealth;
    }

    private void doTime() {
        Timeline time = new Timeline();
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
                }
            }

        });
        time.getKeyFrames().add(frame);
        time.playFromStart();
    }

}
