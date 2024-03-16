package fr.polytech.vgl.main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
 

public class test extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Cr�ation du bouton
        Button button = new Button("Mon Bouton");
        
        // Cr�ation d'une pile pour le placer dans la sc�ne
        StackPane root = new StackPane();
        root.getChildren().add(button);
        
        // Cr�ation de la sc�ne
        Scene scene = new Scene(root, 200, 100);
        
        // Chargement du fichier CSS
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        
        // Application de la sc�ne � la fen�tre principale
        primaryStage.setScene(scene);
        primaryStage.setTitle("Exemple de Bouton Stylis�");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


/*public class test extends Application implements EventHandler<ActionEvent> {
    Button button;
     
    public static void main(String args[]){          
         launch(args);     
    } 
         
    @Override    
    public void start(Stage primaryStage) throws Exception { 
        StackPane layout = new StackPane();
         
        Scene scene = new Scene(layout, 300, 300);
         
        button = new Button("Hello World");
        button.setOnAction(this);
         
        layout.getChildren().addAll(button);
         
        primaryStage.setTitle("CodersLegacy");
        primaryStage.setScene(scene);   
        primaryStage.show();
    }
     
    public void handle(ActionEvent event) {
        if(event.getSource() == button) {
            System.out.println("Hello World");
        }
        //if(event.getSource() == button2) {
            //System.out.println("A commented out code block");
        //}     
    }
}*/
