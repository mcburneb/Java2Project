package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
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
     */
    public static void nextLevelAlert(){
        Alert levelAlert = new Alert(AlertType.INFORMATION,  "You may now proceed to the next level", ButtonType.NEXT);
        levelAlert.setTitle("DEAD MONSTER");
        levelAlert.setHeaderText("You killed the monster!");
        Optional<ButtonType> result = levelAlert.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.NEXT) {
            Game game = new Game();
            game.changeLevel();
        }
    }

    /**
     * @author Brianna McBurney
     * @param primaryStage
     * 
     */
    public static void readInstructions(Stage primaryStage) {
        FileChooser chooser = new FileChooser();
        
        File file = new File("C:\\Users\\banan\\Documents\\NetBeansProjects\\Java 2 Project\\MonsterCombat\\resources\\files");
        
        chooser.setInitialDirectory(file);
        
        File selectedFile = chooser.showOpenDialog(primaryStage);
        
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
         
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Instructions");
        alert.setHeaderText("Monster Combat Instructions");
        alert.setContentText(builder.toString());
        alert.showAndWait();
    }    
}
