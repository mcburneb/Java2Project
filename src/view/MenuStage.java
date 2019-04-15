package view;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Player;

/**
 *
 * @author Brianna McBurney
 */
public class MenuStage extends Stage {

    private TextField playerName;
    
    private ArrayList<Player> playerList;
    
    private Player playerOfChoice;

    private Button joinBtn;
    private Button instructionBtn;

    private Label gameNameLbl;
    private Label playerOfChoiceLbl;
    
    private VBox startPageLayout;
    private HBox joinBtnLayout;

    /**
     * @author Mostafa
     */
    public MenuStage() {
        startPageLayout = new VBox(15);
        startPageLayout.setStyle("-fx-background-color: black");
        startPageLayout.setPadding(new Insets(20));

        gameNameLbl = new Label(" MONSTER  COMBAT");
        gameNameLbl.setFont(Font.loadFont("file:resources/font/DragonForcE.ttf", 150));
        gameNameLbl.setTextFill(Color.web("#0076a9"));
        playerName = new TextField();
        playerName.setStyle("-fx-border-color: white;" + "-fx-background-color: lightBlue");
        playerName.setPromptText("Enter your name");

        playerOfChoiceLbl = new Label("Choose a player");
        playerOfChoiceLbl.setTextFill(Color.web("#0076a9"));

        // create the player options to display 
        playerList = new ArrayList<>();
        playerList = Player.getPlayers();

        ImageView p1Image = new ImageView(playerList.get(0).getImagePath());
        p1Image.setFitHeight(450);
        p1Image.setFitWidth(400);
        Player p1 = playerList.get(0);
        p1Image.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            // set the selected player as the player of choice
            setPlayerOfChoise(p1);
        });

        ImageView p2Image = new ImageView(playerList.get(1).getImagePath());
        p2Image.setFitHeight(450);
        p2Image.setFitWidth(400);
        Player p2 = playerList.get(1);
        p2Image.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            // set the selected player as the player of choice
            setPlayerOfChoise(p2);
        });

        ImageView p3Image = new ImageView(playerList.get(2).getImagePath());
        p3Image.setFitHeight(450);
        p3Image.setFitWidth(400);
        Player p3 = playerList.get(2);
        p3Image.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            // set the selected player as the player of choice
            setPlayerOfChoise(p3);
        });

        // grade HBox to hold player options and add the players Images to it
        HBox playerImages = new HBox();
        playerImages.getChildren().addAll(p1Image, p2Image, p3Image);

        joinBtn = new Button("Join");
        joinBtn.setOnAction(e -> onJoinClick());
        joinBtn.setPrefWidth(400);

        // create button to allow user to view the instructions
        instructionBtn = new Button("Instructions");
        instructionBtn.setOnAction(e -> AlertBox.readInstructions(this));

        joinBtnLayout = new HBox(290);
        joinBtnLayout.getChildren().addAll(instructionBtn, joinBtn);

        startPageLayout.getChildren().addAll(gameNameLbl, playerName, playerOfChoiceLbl, playerImages, joinBtnLayout);

        Scene startUpScene = new Scene(startPageLayout, 1250, 850);
        this.setScene(startUpScene);

        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds(); //to make the stage center
        this.setX((primScreenBounds.getWidth() - this.getWidth()) / 2); //to make the stage center
        this.setY((primScreenBounds.getHeight() - this.getHeight()) / 2); // to make the stage center
        
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        this.setX(bounds.getMinX());
        this.setY(bounds.getMinY());
        this.setWidth(bounds.getWidth());
        this.setHeight(bounds.getHeight());
        
        this.show();
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
        playerOfChoice.setImagePath(playerOfChoice.getImagePath());
    }
    
    /**
     * @author Mostafa
     */
    public void onJoinClick() {
        // make sure the user has entered a name for their player
        if (playerName.getText().isEmpty()) {
            AlertBox.informAlert("Game Requirement", "You need to enter a name \nbefore joining the game");
        } else if (playerOfChoice == null) { // make sure the user selected a character
            AlertBox.informAlert("Game Requirement", "You need to select a character \nbefore joining the game");
        } else {
            playerOfChoice.setName(playerName.getText());
            
            GameStage gameStage = new GameStage(playerOfChoice);
            this.close();
        }
    }
}
