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
 * @author Stage split to a new class by Brianna McBurney
 * Created by Mostafa Allahmoradi
 */
public class MenuStage extends Stage {

    //controls that used in all methods through this class
    private final TextField playerName;

    private ArrayList<Player> playerList;

    private Player playerOfChoice;

    private final Button joinBtn;
    private final Button instructionBtn;

    private final Label lblGameName;
    private final Label lblChosePlayerPrompt;
    private final Label lblNamePrompt;

    private final VBox startPageLayout;
    private final HBox joinAndInstructionBtnLayout;

    /**
     * @author Mostafa Allahmoradi
     *
     */
    public MenuStage() {
        //create the original layout of the stage and add different layout to it
        startPageLayout = new VBox(10);
        startPageLayout.setStyle("-fx-background-color: black");
        startPageLayout.setPadding(new Insets(20));

        // create the label to show the name of the game
        lblGameName = new Label("   MONSTER     COMBAT");
        lblGameName.setFont(Font.loadFont("file:resources/font/DragonForcE.ttf", 200));
        lblGameName.setTextFill(Color.web("#0076a9"));

        // create the next field to get the user's input for their name
        playerName = new TextField();
        playerName.setStyle("-fx-border-color: white;" + "-fx-background-color: lightBlue");
        playerName.setPromptText("Enter your name");

        // create label to display the prompt for the user to choose a player
        lblChosePlayerPrompt = new Label("Choose a player");
        lblChosePlayerPrompt.setTextFill(Color.web("#0076a9"));
        lblChosePlayerPrompt.setFont(Font.font("Verdana", 40));

        // (Brianna) create the player options to display 
        playerList = new ArrayList<>();
        playerList = Player.getPlayers();

        // grade HBox to hold player options and add the player's Images to it
        HBox playerImagesLayout = new HBox();
        playerImagesLayout.setAlignment(Pos.CENTER);

        //create arrayList to store the player's images
        ImageView[] playerImages = new ImageView[3];
        
        //create an imageView for each players image
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

        //creating an instance of a button (joinBtn) and resize it
        joinBtn = new Button("Join");
        joinBtn.setPrefHeight(200);
        joinBtn.setPrefWidth(200);
        joinBtn.setOnAction(e -> onJoinClick()); //setting an action for the button

        // creating an instance of a button to allow user to view the instructions and resize the button
        instructionBtn = new Button("Instructions");
        instructionBtn.setPrefHeight(200);
        instructionBtn.setPrefWidth(200);
        instructionBtn.setOnAction(e -> AlertBox.readInstructions()); //setting an action for the button

        //creating an HBox to hold the join and instruction button and them to the layout
        joinAndInstructionBtnLayout = new HBox(630);
        joinAndInstructionBtnLayout.getChildren().addAll(instructionBtn, joinBtn);

        HBox choosePlayerPrompt = new HBox(lblChosePlayerPrompt);
        choosePlayerPrompt.setAlignment(Pos.CENTER);

        // creating an instance of label to prompt user to enter name
        lblNamePrompt = new Label("Please enter your name");
        lblNamePrompt.setTextFill(Color.web("#0076a9"));
        lblNamePrompt.setFont(Font.font("Verdana", 40));
        startPageLayout.getChildren().addAll(lblGameName, lblNamePrompt, playerName, choosePlayerPrompt, playerImagesLayout, joinAndInstructionBtnLayout);

        Scene startUpScene = new Scene(startPageLayout);
        this.setScene(startUpScene);

        this.setMaximized(true); //make the stage full screen 

        this.show();
    }

    /**
     * @author Brianna McBurney
     *
     * When the player clicks on a player in the main menu, make that
     * player the one that they will play the game as
     * @param chosenPlayer The player the user has clicked on
     */
    private void setPlayerOfChoise(Player chosenPlayer) {
        // set the chosen player as the plater of choice
        playerOfChoice = chosenPlayer;
        lblChosePlayerPrompt.setText(chosenPlayer.getName());
    }

    /**
     * Method called when the user clicks the 'join' button
     * @author Mostafa Allahmoradi
     */
    public void onJoinClick() {
        // make sure the user has entered a name for their player
        if (playerName.getText().isEmpty()) {
            AlertBox.informAlert("Game Requirement", "You need to enter a name \nbefore joining the game");
        } else if (playerOfChoice == null) { // make sure the user selected a player
            AlertBox.informAlert("Game Requirement", "You need to select a player \nbefore joining the game");
        } else {
            playerOfChoice.setName(playerName.getText());
            // don't assign to return value because it will never be used
            new GameStage(playerOfChoice);
            this.close();
        }
    }
}
