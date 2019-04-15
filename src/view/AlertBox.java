package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Mostafa
 */
public class AlertBox {

    public static void informAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.show();
    }

    /**
     * @author Brianna McBurney
     * 
     * Alert to be shown if the player has killed the monster
     * Let the user know that they can now go to the next level
     * @return If the user clicks 'next' or closes the alert return true to go to the next level
     */
    public static boolean nextLevelAlert() {
        Alert levelAlert = new Alert(AlertType.INFORMATION, "You may now proceed to the next level", ButtonType.NEXT);
        levelAlert.setTitle("DEAD MONSTER");
        levelAlert.setHeaderText("You killed the monster!");
        Optional<ButtonType> result = levelAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.NEXT) {
            return true;
        }
        return true;
    }

    /**
     * @author Brianna McBurney
     * 
     * Retrieve the instructions from the file and display them in an alert
     * @param primaryStage
     */
    public static void readInstructions(Stage primaryStage) {
        
        File selectedFile = new File("src/fileResources/files/instructions.txt");
        
        StringBuilder builder = new StringBuilder();
        
        try {
            Scanner in = new Scanner(selectedFile);

            while (in.hasNextLine()) {
                String lineIn = in.nextLine();
                builder.append(lineIn).append("\n");
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(AlertBox.class.getName()).log(Level.SEVERE, null, ex);
        }      
         
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
        alert.setTitle("Instructions");
        alert.setHeaderText("Monster Combat Instructions");
        alert.setContentText("Click 'show details' to view instructions");
        
        TextArea textArea = new TextArea(builder.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);
        
        StackPane pane = new StackPane();
        pane.getChildren().add(textArea);
        
        alert.getDialogPane().setExpandableContent(pane);
        alert.showAndWait();
    }    
}
