package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

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
     * 
     * @return the StringBuilder that holds the instructions
     */
    public StringBuilder readInstructions() {
        // get the file holding the Instructions
        File selectedFile = new File("file:resources\\files\\instructions.txt");
        
        StringBuilder builder = new StringBuilder();
        
        if (selectedFile != null){
            try {
                Scanner in = new Scanner(selectedFile);

                while (in.hasNextLine()) {
                    String lineIn = in.nextLine();
                    builder.append(lineIn).append("\n");
                }

            } catch (FileNotFoundException ex) {
                Logger.getLogger(AlertBox.class.getName()).log(Level.SEVERE, null, ex);
            }
        }      
         
        // return the contents of the instructions folder
        return builder;
    }    
}
