package view;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.util.Callback;
import model.Player;
import model.Score;

/**
 *
 * @author Brianna McBurney
 */
public class GameOverStage extends Stage {

    /**
     * The stage that will be displayed when the game is over.
     *
     * @author Mostafa Allahmoradi
     *
     * @param playerOfChoice is the player that the user chose
     * @param gameOverMessage is the message that tell the user why the game is
     * over
     */
    public GameOverStage(Player playerOfChoice, String gameOverMessage) {

        this.initModality(Modality.APPLICATION_MODAL);
        this.setTitle("Game is over");

        // create the label to display the game's name
        Label lblGameName = new Label(" MONSTER  COMBAT");
        lblGameName.setFont(Font.loadFont("file:resources/font/DragonForcE.ttf", 150));
        lblGameName.setTextFill(Color.web("#0076a9"));

        // create the text to display the gameOverMessage
        Text message = new Text(gameOverMessage);
        message.setFont(Font.font("Verdava", 45));
        message.setFill(Color.RED);

        // create label to display the players name and the score they achieved
        Label playerResults = new Label();
        playerResults.setText("Name: " + playerOfChoice.getName() + " \tScore: " + playerOfChoice.getScore());
        playerResults.setFont(Font.font("Verdava", 50));

        Score score = new Score();
        // create the list that will display the previous scores
        ObservableList scoreList = score.getHighScores();
        TableView<Score> scoresTable = createScoreTable(scoreList);
        scoresTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        scoresTable.setMaxWidth(500);

        // create button to get the game to start over
        Button playAgainBtn = new Button("Play Again");
        playAgainBtn.setPrefWidth(500);
        playAgainBtn.setPrefHeight(80);
        playAgainBtn.setStyle("-fx-background-color: lightblue");
        playAgainBtn.setOnAction(e -> playAgain());

        // create the general layout to hold the elements of the stage
        VBox layout = new VBox(10);
        layout.getChildren().addAll(lblGameName, playerResults, message, scoresTable, playAgainBtn);
        layout.setAlignment(javafx.geometry.Pos.CENTER);
        Scene scene = new Scene(layout, 1150, 850);
        
        // set the scene, make it full screen, and display the stage
        this.setScene(scene);
        this.setMaximized(true);
        this.show();
    }

    /**
     * Creating the table that will display the previous scores
     * @author Brianna McBurney
     *
     * @param scoreList Observable list holding all the scores retrieved from
     * the file of scores
     */
    private TableView createScoreTable(ObservableList scoreList) {
        // set up the table to display the scores
        TableView<Score> scores = new TableView();

        // Create the column to display the rank of the score
        TableColumn rankColumn = new TableColumn("RANK");
        rankColumn.setMinWidth(20);

        // create new cell factory that gets its value from the row number
        rankColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Score, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Score, String> p) {
                return new ReadOnlyObjectWrapper(((scores.getItems().indexOf(p.getValue())) + 1) + "");
            }
        });
        rankColumn.setSortable(false);
        rankColumn.setResizable(false);

        // Create the column to display the name of the player that achieved that score
        TableColumn<Score, String> nameColumn = new TableColumn<>("NAME");
        nameColumn.setResizable(false);
        // pair the cellValue with the name from the Score object (has to match the name of the data field used when you create the Score)
        nameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        nameColumn.setSortable(false);
        nameColumn.setMinWidth(200);

        // Create the column to display the score that the player achieved
        TableColumn<Score, Integer> scoreColumn = new TableColumn<>("SCORE");
        scoreColumn.setResizable(false);
        // pair the cellValue with the score from the Score object (has to match the name of the data field used when you create the Score)
        scoreColumn.setCellValueFactory(new PropertyValueFactory("score"));
        scoreColumn.setSortable(false);

        // add the list of scores to the TableView
        scores.setItems(scoreList);

        // Add the columns to the TableView
        scores.getColumns().addAll(rankColumn, nameColumn, scoreColumn);

        // Return the TableView
        return scores;
    }

    /**
     * playAgainBtn action that fires when user click on play again button
     * @author Mostafa Allahmoradi
     */
    private void playAgain() {
        // don't assign to return value because it will never be used
        new MenuStage();
        this.close();
    }
}
