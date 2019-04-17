package view;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Brianna McBurney
 */
public class Game extends Application{

    /**
     * Opens the game menu
     * 
     * @param primaryStage
     * @throws Exception 
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // don't assign to return value because it will never be used
        new MenuStage();
    }
    
    /**
     * Launches the menuStage
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }   

}
