package view;

import java.util.ArrayList;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Player;

/**
 *
 * @author Stage split to a new class by Brianna McBurney Created by Mostafa
 */
public class MenuStage extends Stage {

    //controls that used in all methods through this class
    private final TextField playerName;

    private ArrayList<Player> playerList;

    private Player playerOfChoice;

    private final Button joinBtn;
    private final Button instructionBtn;

    private final Label gameNameLbl;
    private final Label playerOfChoiceLbl;
    private final Label namePromptLbl;

    private final VBox startPageLayout;
    private final HBox joinNinstructionBtnLayout;

    /**
     * @author Mostafa
     *
     */
    public MenuStage() {
        //crating the original layout of the stage and add different layout to it
        startPageLayout = new VBox(10);
        startPageLayout.setStyle("-fx-background-color: black");
        startPageLayout.setPadding(new Insets(20));

        gameNameLbl = new Label("   MONSTER     COMBAT");
        gameNameLbl.setFont(Font.loadFont("file:resources/font/DragonForcE.ttf", 200));
        gameNameLbl.setTextFill(Color.web("#0076a9"));

        playerName = new TextField();
        playerName.setStyle("-fx-border-color: white;" + "-fx-background-color: lightBlue");
        playerName.setPromptText("Enter your name");

        playerOfChoiceLbl = new Label("Choose a player");
        playerOfChoiceLbl.setTextFill(Color.web("#0076a9"));
        playerOfChoiceLbl.setFont(Font.font("Verdana", 40));

        // (Brianna) create the player options to display 
        playerList = new ArrayList<>();
        playerList = Player.getPlayers();

        // grade HBox to hold player options and add the players Images to it
        HBox playerImagesLayout = new HBox();
        playerImagesLayout.setAlignment(Pos.CENTER);

        //making an arrayList of playes image and hold all three images of players.
        ImageView[] playerImages = new ImageView[3];
        //imageView for each image is being create in this loop
        for (int i = 0; i < 3; i++) {
            playerImages[i] = new ImageView(playerList.get(i).getImagePath());
            playerImages[i].setFitHeight(450);
            playerImages[i].setFitWidth(400);
            playerImagesLayout.getChildren().add(playerImages[i]);//adding each image to the layout
            Player choosenPlayer = playerList.get(i);
            playerImages[i].addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
                // set the selected player as the player of choice
                setPlayerOfChoise(choosenPlayer);
            });
        }

        //creating an instace of a button(joinBtn) and resize it
        joinBtn = new Button("Join");
        joinBtn.setPrefHeight(200);
        joinBtn.setPrefWidth(200);
        joinBtn.setOnAction(e -> onJoinClick()); //setting an action for the button

        // creating an istance of a button to allow user to view the instructions and resize the button
        instructionBtn = new Button("Instructions");
        instructionBtn.setPrefHeight(200);
        instructionBtn.setPrefWidth(200);
        instructionBtn.setOnAction(e -> AlertBox.readInstructions()); //setting an action for the button

        //creating an HBox to hold the join and instruction button and them them to the layout
        joinNinstructionBtnLayout = new HBox(630);
        joinNinstructionBtnLayout.getChildren().addAll(instructionBtn, joinBtn);

        HBox choosePlayerPrompt = new HBox(playerOfChoiceLbl);
        choosePlayerPrompt.setAlignment(Pos.CENTER);

        //creting an instance of label to promp user to enter name
        namePromptLbl = new Label("Please enter your name");
        namePromptLbl.setTextFill(Color.web("#0076a9"));
        namePromptLbl.setFont(Font.font("Verdana", 40));
        startPageLayout.getChildren().addAll(gameNameLbl, namePromptLbl, playerName, choosePlayerPrompt, playerImagesLayout, joinNinstructionBtnLayout);

        Scene startUpScene = new Scene(startPageLayout);
        this.setScene(startUpScene);

        this.setMaximized(true);//to meke the stage full screan 

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
    }

    /**
     * @author Mostafa this is an event for when user clicks join button
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
