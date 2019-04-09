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
    public static void informAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.show();
    }
    
    /**
     * @author Brianna McBurney
     * @return 
     */
    public static boolean nextLevelAlert(){
        Alert levelAlert = new Alert(AlertType.INFORMATION,  "You may now proceed to the next level", ButtonType.NEXT);
        levelAlert.setTitle("DEAD MONSTER");
        levelAlert.setHeaderText("You killed the monster!");
        levelAlert.showAndWait();
        return true;
    }

    /**
     * @author Brianna McBurney
     * @param primaryStage
     * 
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
        
//        https://code.makery.ch/blog/javafx-dialogs-official/
        TextArea textArea = new TextArea(builder.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);
        
        StackPane pane = new StackPane();
        pane.getChildren().add(textArea);
        
        alert.getDialogPane().setExpandableContent(pane);
        alert.showAndWait();
    }    
}
