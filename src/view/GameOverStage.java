package view;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Player;
import model.Score;

/**
 *
 * @author Brianna McBurney
 */
public class GameOverStage extends Stage {

    /**
     * @author Mostafa
     * @param playerOfChoice
     * @param gameOverMessage
     */
    public GameOverStage(Player playerOfChoice, String gameOverMessage) {
        
        this.initModality(Modality.APPLICATION_MODAL);
        this.setTitle("Game is over");
        
        Label gameNameLbl = new Label(" MONSTER  COMBAT");
        gameNameLbl.setFont(Font.loadFont("file:resources/font/DragonForcE.ttf", 150));
        gameNameLbl.setTextFill(Color.web("#0076a9"));

        Text message = new Text(gameOverMessage);
        message.setFont(Font.font("Verdava", 45));
        message.setFill(Color.ROSYBROWN);
        
        Label playerResults = new Label();
        playerResults.setText("Name: " + playerOfChoice.getName() + " \tScore: " + playerOfChoice.getScore());
        playerResults.setFont(Font.font("Verdava", 50));

        Score s = new Score();
        ObservableList scoreList = s.getHighScores();
        TableView<Score> scores = createScoreTable(scoreList);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(gameNameLbl, playerResults, message, scores);
        layout.setAlignment(javafx.geometry.Pos.CENTER);
        Scene scene = new Scene(layout, 1150, 850);
        this.setScene(scene);
        
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
     * @param scoreList Observable list holding all the scores retrieved from
     * the file of scores
     */
    private TableView createScoreTable(ObservableList scoreList) {
        // set up the table to display the scores
        TableView<Score> scores = new TableView();

        // Create the column to diplay the rank of the score
        TableColumn rankColumn = new TableColumn("RANK");
        rankColumn.setMinWidth(20);

//        https://stackoverflow.com/questions/16384879/auto-numbered-table-rows-javafx
        // create new observableValue that gets it's value from the row number
        rankColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Score, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Score, String> p) {
                return new ReadOnlyObjectWrapper(((scores.getItems().indexOf(p.getValue())) + 1) + "");
            }
        });
        rankColumn.setSortable(false);

        // Create the column to display the name of the player that achived that score
        TableColumn<Score, String> nameColumn = new TableColumn<>("NAME");
        // pair the cellValue with the name from the Score object (has to match the name of the data field used when you create the Score)
        nameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        nameColumn.setMinWidth(200);
        nameColumn.setSortable(false);

        // Create the column to display the score that the player achieved
        TableColumn<Score, Integer> scoreColumn = new TableColumn<>("SCORE");
        // pair the cellValue with the score from the Score object (has to match the name of the data field used when you create the Score)
        scoreColumn.setCellValueFactory(new PropertyValueFactory("score"));
        scoreColumn.setMinWidth(100);
        scoreColumn.setSortable(false);

        // add the list of scores to the TableView
        scores.setItems(scoreList);

        // Add the columns to the TableView
        scores.getColumns().addAll(rankColumn, nameColumn, scoreColumn);

        // Return the TableView
        return scores;
    }
}
