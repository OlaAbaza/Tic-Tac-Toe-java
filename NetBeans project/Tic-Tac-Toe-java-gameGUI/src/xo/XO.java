package xo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javafx.scene.layout.Pane;
import javafx.stage.StageStyle;

public class XO extends Application {
        double x, y;
        ArrayList<String> sendData;
    @Override
    public void start(Stage stage) {

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
        root.getChildren().add(AppManager.gameLevels);

        AppManager.viewPane(AppManager.startPane);
        Scene scene = new Scene(root, 380, 500);

        stage.setTitle("Tic Tac Toe");
        stage.setScene(scene);
        stage.setResizable(false);
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image("/images/Icon.png"));
        stage.show();

        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                x = stage.getX() - event.getScreenX();
                y = stage.getY() - event.getScreenY();
            }
        });
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() + x);
                stage.setY(event.getScreenY() + y);
            }
        });

        stage.setOnCloseRequest((event) -> {
            if(AppManager.isOnline){
                
                sendData = new ArrayList<String>();
                sendData.add("end entirly");
                sendData.add((AppManager.gamePane.firstPlayerScore.getText() + "-" + AppManager.gamePane.secondPlayerScore.getText()));
                sendData.add(AppManager.playerName);
                sendData.add(AppManager.opponentName);
                try {
                    AppManager.objectOutput.writeObject(sendData);
                    AppManager.objectOutput.flush();
                } catch (IOException ex) {
                    Logger.getLogger(GamePane.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            StartPane.audio.stop();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
