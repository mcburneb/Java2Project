

package game;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Mostafa
 */
public class AlertBox {
    // i'm making it static and I dont have to make an object of it
    public static void display(String title, String content){
        Stage window = new Stage();//a new blank window so far
        window.initModality(Modality.APPLICATION_MODAL); //to block user interaction unless this one get take care off
        window.setTitle(title);
        window.setMinWidth(250);
        
        Label label = new Label();
        label.setText(content);
        Button closeButton = new Button("Undrestand");
        closeButton.setOnAction(e -> window.close());
        
        VBox layout = new VBox (10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(layout, 380, 130);
        window.setScene(scene);
        window.showAndWait();
        
        
    }

}
