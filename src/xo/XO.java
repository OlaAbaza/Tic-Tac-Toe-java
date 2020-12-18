package xo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javafx.scene.layout.Pane;

public class XO extends Application {
    
    @Override
    public void start(Stage stage) {
        
        
        AppManager.setDefaultSettings(); //***DLT***
        
        Pane root = new Pane();
        
        root.getChildren().add(AppManager.startPane);
        root.getChildren().add(AppManager.singlePlayerPane);
        root.getChildren().add(AppManager.multiPlayerPane);
        root.getChildren().add(AppManager.onlinePane);
        root.getChildren().add(AppManager.recordsPane);
        root.getChildren().add(AppManager.gamePane);
        root.getChildren().add(AppManager.loginPane);
        root.getChildren().add(AppManager.registerPane);
        root.getChildren().add(AppManager.aboutPane);
        root.getChildren().add(AppManager.availablePane);
        root.getChildren().add(AppManager.scorePane);
        
        //new Connection();  // while choose online mode
        
        
        AppManager.viewPane(AppManager.startPane);
        Scene scene = new Scene(root, 380, 500);
        
       
        stage.setTitle("Tic Tac Toe");
        stage.setScene(scene);
        stage.setResizable(false);
        
        stage.getIcons().add(new Image("/images/Icon.png"));
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}