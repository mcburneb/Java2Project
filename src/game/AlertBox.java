

package game;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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

    public File openInstructions() {
        // create Strings for the path to the folder and file that will hold the instructions
        String MCfolder = System.getProperty("user.home") + "\\Desktop\\MonsterCombat";
        String MCfile = MCfolder+"\\instructions.txt";
        
        File folder = new File(MCfolder);
        
        File file = new File(MCfile);
        
        // check if there is already a folder to store the instructions
        if (! folder.isDirectory()){
            // if theres no folder create the folder
            new File(MCfolder).mkdirs();
            
            // add file to store the instructions in
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
            }
                      
        } else {
            // check if the folder contains the file to store the instructions in
            if (! file.exists()) {                
                // if it doesnt exist create a new file in the right folder
                try {
                    // create the file
                    file.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
        }
        
        File selectedFile = new File(MCfile);
        
        return selectedFile; 
    }
    public void getHighScores(File selectedFile) {
        
        if (selectedFile == null) return;
        try {
            Scanner input = new Scanner(selectedFile);
            StringBuilder builder = new StringBuilder();
            
            while (input.hasNextLine()) {          
                builder.append(input.nextLine());
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }        

    }
    
}
