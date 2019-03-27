/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Mostafa
 */
public class Game extends Application {

    private TextField playerName;
    private Button join;
    private Scene game;
    private Label levelNum;
    private Label playerNameOnScreenDisplay;
    private String playerName1;
    

    @Override
    public void start(Stage startUpStage) {
        startUpStage.setTitle("Start Up");

        VBox startPageLayout = new VBox(17);
        startPageLayout.setStyle("-fx-background-color: black");
        startPageLayout.setPadding(new Insets(20));

        Label gameName = new Label("MONSTER\n          COMBAT");
        gameName.setFont(Font.loadFont("file:resources/font/DragonForcE.ttf", 60));
        gameName.setTextFill(Color.web("#0076a9"));

        playerName = new TextField();
        playerName.setStyle("-fx-border-color: blue;" + "-fx-background-color: Red");
        playerName.setPromptText("Enter your name");

        HBox modeNjoin = new HBox(20);

        ComboBox<String> playMode = new ComboBox<>();
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

        Scene scene = new Scene(startPageLayout, 380, 300);
        startUpStage.setScene(scene);
        
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds(); //to make the stage center
        startUpStage.setX((primScreenBounds.getWidth() - startUpStage.getWidth()) / 2); //to make the stage center
        startUpStage.setY((primScreenBounds.getHeight() - startUpStage.getHeight()) / 2); // to make the stage center
        startUpStage.show();

        
        
        //root layout of the game structure
        VBox gameLayoutRight = new VBox();
        gameLayoutRight.setPadding(new Insets(10));

        levelNum = new Label();
        playerNameOnScreenDisplay = new Label();
        
        

        //hero ImageView
        ImageView hero = new ImageView("file:resources/pictures/hero/hero.png");
        hero.setFitHeight(650);
        hero.setFitWidth(650);

        //hero information framework
        StackPane heroInfoLayout = new StackPane();
        Rectangle heroInfo = new Rectangle();
        heroInfo.setHeight(210);
        heroInfo.setWidth(690);
        gameLayoutRight.getChildren().addAll(levelNum, hero, playerNameOnScreenDisplay, heroInfo);

        VBox gameLayoutLeft = new VBox();
        gameLayoutLeft.setPadding(new Insets(10));

        Rectangle monsterInfo = new Rectangle();
        monsterInfo.setHeight(210);
        monsterInfo.setWidth(690);
        gameLayoutLeft.getChildren().add(monsterInfo);

        ImageView monster = new ImageView("file:resources/pictures/monsters/monster.png");
        monster.setFitHeight(650);
        monster.setFitWidth(650);
        gameLayoutLeft.getChildren().add(monster);

        HBox gameLayoutOriginal = new HBox();
        gameLayoutOriginal.setPadding(new Insets(10));
        gameLayoutOriginal.setStyle("-fx-background-color: DarkBlue");
        gameLayoutOriginal.getChildren().addAll(gameLayoutRight, gameLayoutLeft);
        
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
            AlertBox.display("Game Requairements", "You need to enter a name befor joining the game");
        } else {
            
            playerName1 = playerName.getText();
            startUpStage.setScene(game);
            playerNameOnScreenDisplay.setText(playerName1);
        }
    }

}
