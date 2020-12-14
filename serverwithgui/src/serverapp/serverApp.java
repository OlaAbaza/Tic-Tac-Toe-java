/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverapp;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Ola Abaza
 */
public class serverApp extends Application {

    double x, y;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        Parent root1 = new pieChartBase(stage, primaryStage);
        Scene scene1 = new Scene(root1);
        stage.setScene(scene1);

        Parent root = new server_app_Base(stage, primaryStage);
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Server App");
        primaryStage.show();
        stage.setX(primaryStage.getX());
        stage.setY(primaryStage.getY());

        
        //make the second stage moveable
        scene1.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                x = stage.getX() - event.getScreenX();
                y = stage.getY() - event.getScreenY();
            }
        });
        scene1.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() + x);
                stage.setY(event.getScreenY() + y);
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
