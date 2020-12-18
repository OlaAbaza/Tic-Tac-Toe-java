/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xo;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author ahmed
 */
public class OnlinePane extends Pane {

    protected final AnchorPane anchorPane;
    protected final ImageView imageView;
    protected final ImageView connectbtn;
    protected final TextField IP;
    protected final ImageView soundbtn;
    protected final ImageView backbtn;
    protected final ImageView soundoff_btn;

    Alert alert = new Alert(Alert.AlertType.WARNING);

    public OnlinePane() {

        anchorPane = new AnchorPane();
        imageView = new ImageView();
        connectbtn = new ImageView();
        IP = new TextField("127.0.0.1");
        soundbtn = new ImageView();
        backbtn = new ImageView();
        soundoff_btn = new ImageView();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(500.0);
        setPrefWidth(380.0);

        anchorPane.setPrefHeight(500.0);
        anchorPane.setPrefWidth(380.0);
        anchorPane.getStylesheets().add("/xo/style.css");

        imageView.setFitHeight(465.0);
        imageView.setFitWidth(330.0);
        imageView.setLayoutX(25.0);
        imageView.setLayoutY(148.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(getClass().getResource("/images/ServerIp.png").toExternalForm()));

        connectbtn.setFitHeight(62.0);
        connectbtn.setFitWidth(130.0);
        connectbtn.setLayoutX(125.0);
        connectbtn.setLayoutY(306.0);
        connectbtn.setPickOnBounds(true);
        connectbtn.setPreserveRatio(true);
        connectbtn.setCursor(Cursor.HAND);
        connectbtn.setImage(new Image(getClass().getResource("/images/Connect.png").toExternalForm()));

        IP.setLayoutX(103.0);
        IP.setLayoutY(261.0);
        IP.getStyleClass().add("text-input");

        soundbtn.setFitHeight(46.0);
        soundbtn.setFitWidth(54.0);
        soundbtn.setLayoutX(4.0);
        soundbtn.setLayoutY(3.0);
        soundbtn.setPickOnBounds(true);
        soundbtn.setPreserveRatio(true);
        soundbtn.setCursor(Cursor.HAND);
        soundbtn.setImage(new Image(getClass().getResource("/images/volume2x.png").toExternalForm()));

        soundoff_btn.setFitHeight(46.0);
        soundoff_btn.setFitWidth(54.0);
        soundoff_btn.setLayoutX(4.0);
        soundoff_btn.setLayoutY(3.0);
        soundoff_btn.setPickOnBounds(true);
        soundoff_btn.setPreserveRatio(true);
        soundoff_btn.setVisible(false);
         soundoff_btn.setCursor(Cursor.HAND);
        soundoff_btn.setImage(new Image(getClass().getResource("/images/volumemute2x.png").toExternalForm()));

        backbtn.setFitHeight(64.0);
        backbtn.setFitWidth(61.0);
        backbtn.setLayoutX(14.0);
        backbtn.setLayoutY(431.0);
        backbtn.setPickOnBounds(true);
        backbtn.setPreserveRatio(true);
           backbtn.setCursor(Cursor.HAND);
        backbtn.setImage(new Image(getClass().getResource("/images/back.png").toExternalForm()));

        anchorPane.getChildren().add(imageView);
        anchorPane.getChildren().add(connectbtn);
        anchorPane.getChildren().add(IP);
        anchorPane.getChildren().add(soundbtn);
        anchorPane.getChildren().add(backbtn);
        anchorPane.getChildren().add(soundoff_btn);
        getChildren().add(anchorPane);

        soundbtn.setOnMouseClicked((event) -> {
            soundbtn.setVisible(false);
            soundoff_btn.setVisible(true);
            LoginPane.audio.stop();

        });
        soundoff_btn.setOnMouseClicked((event) -> {
            soundoff_btn.setVisible(false);
            soundbtn.setVisible(true);
            LoginPane.audio.play();
        });

        backbtn.setOnMouseClicked((Action) -> {
            AppManager.viewPane(AppManager.startPane);
        });

        connectbtn.setOnMouseClicked((event) -> {

            connectbtn.setImage(new Image(getClass().getResource("/images/ConnectPressed.png").toExternalForm()));

            Task<Void> sleeper = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                    }
                    return null;
                }
            };
            sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    /*if (AppManager.connect(ipTxtFld.getText())){
                AppManager.viewPane(AppManager.loginPane);
            } else {
                alert.setTitle("Connection");
                alert.setHeaderText("Connetion Failure");
                alert.setContentText("The Server offline or Wrong IP!!");
                alert.showAndWait();
            }*/
                    AppManager.viewPane(AppManager.loginPane);

                    connectbtn.setImage(new Image(getClass().getResource("/images/Connect.png").toExternalForm()));
                }
            });
            new Thread(sleeper).start();

        });

    }

}
