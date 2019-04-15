package view;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Brianna McBurney
 */
public class Game extends Application{

    /**
     * 
     * @param primaryStage
     * @throws Exception 
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        MenuStage menuStage = new MenuStage();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }   

}
