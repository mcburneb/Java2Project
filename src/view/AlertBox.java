package view;

import java.io.*;
import java.util.*;
import java.util.logging.*;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * @author Mostafa Allahmoradi
 */
public class AlertBox {

    /**
     * Creating an information-type alert box 
     * @author Mostafa Allahmoradi
     * 
     * @param title to passed into this method to make it multi-functional
     * @param message to passed into this method to make it multi-functional
     */
    public static void informAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * @author Brianna McBurney
     * creating an information-type alert box to be shown when user click on instruction button
     * Retrieve the instructions from the file and display them in an alert
     */
    public static void readInstructions() {
        // get the file that holds the instruction
        File selectedFile = new File("src/fileResources/files/instructions.txt");

        // create string builder to hold the instructions
        StringBuilder builder = new StringBuilder();

        try {
            // read the file contents with the scanner
            Scanner in = new Scanner(selectedFile);

            // while there is still more content
            while (in.hasNextLine()) {
                String lineIn = in.nextLine();
                
                // add the next line to the string builder
                builder.append(lineIn).append("\n");
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(AlertBox.class.getName()).log(Level.SEVERE, null, ex);
        }

        // create the alert to display the instructions
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
        alert.setTitle("Instructions");
        alert.setHeaderText("Monster Combat Instructions");
        alert.setContentText("Click 'show details' to view instructions");

        // create the text area to hold the instruction
        TextArea textArea = new TextArea(builder.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);

        // add text area to a pane
        StackPane pane = new StackPane();
        pane.getChildren().add(textArea);

        // add the instructions pane to the alert
        alert.getDialogPane().setExpandableContent(pane);
        alert.showAndWait();
    }
}
