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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.scene.text.Font;

/**
 *
 * @author Ola Abaza
 */
public class GameLevels extends Pane {

    protected final AnchorPane anchorPane;
    protected final ImageView imageView;
    protected final Label label;
    protected final ImageView easy_btn;
    protected final ImageView back;
    protected final ImageView sound;
    protected final ImageView soundOff;
    protected final ImageView hard_btn;
    protected final ImageView med_btn;

    public GameLevels() {

        anchorPane = new AnchorPane();
        imageView = new ImageView();
        label = new Label();
        easy_btn = new ImageView();
        back = new ImageView();
        sound = new ImageView();
        soundOff = new ImageView();
        hard_btn = new ImageView();
        med_btn = new ImageView();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(500.0);
        setPrefWidth(380.0);

        anchorPane.setPrefHeight(500.0);
        anchorPane.setPrefWidth(380.0);

        imageView.setFitHeight(525.0);
        imageView.setFitWidth(420.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(getClass().getResource("/images/bg.png").toExternalForm()));

        label.setLayoutX(158.0);
        label.setLayoutY(58.0);
        label.setText("Levels");
        label.setTextFill(javafx.scene.paint.Color.WHITE);
        label.setFont(new Font("Luducudu", 32.0));

        easy_btn.setFitHeight(74.0);
        easy_btn.setFitWidth(197.0);
        easy_btn.setLayoutX(103.0);
        easy_btn.setLayoutY(141.0);
        easy_btn.setPickOnBounds(true);
        easy_btn.setPreserveRatio(true);
        easy_btn.setCursor(Cursor.HAND);
        easy_btn.setImage(new Image(getClass().getResource("/images/Easy.png").toExternalForm()));

        back.setFitHeight(61.0);
        back.setFitWidth(59.0);
        back.setLayoutX(14.0);
        back.setLayoutY(432.0);
        back.setPickOnBounds(true);
        back.setPreserveRatio(true);
        back.setCursor(Cursor.HAND);
        back.setImage(new Image(getClass().getResource("/images/back.png").toExternalForm()));

        sound.setFitHeight(44.0);
        sound.setFitWidth(49.0);
        sound.setLayoutX(14.0);
        sound.setLayoutY(14.0);
        sound.setPickOnBounds(true);
        sound.setPreserveRatio(true);
        sound.setCursor(Cursor.HAND);
        sound.setImage(new Image(getClass().getResource("/images/volume2x.png").toExternalForm()));

        soundOff.setFitHeight(44.0);
        soundOff.setFitWidth(49.0);
        soundOff.setLayoutX(14.0);
        soundOff.setLayoutY(14.0);
        soundOff.setPickOnBounds(true);
        soundOff.setPreserveRatio(true);
        soundOff.setVisible(false);
        soundOff.setCursor(Cursor.HAND);
        soundOff.setImage(new Image(getClass().getResource("/images/volumemute2x.png").toExternalForm()));

        hard_btn.setFitHeight(74.0);
        hard_btn.setFitWidth(197.0);
        hard_btn.setLayoutX(103.0);
        hard_btn.setLayoutY(351.0);
        hard_btn.setPickOnBounds(true);
        hard_btn.setPreserveRatio(true);
        hard_btn.setCursor(Cursor.HAND);
        hard_btn.setImage(new Image(getClass().getResource("/images/Hard.png").toExternalForm()));

        med_btn.setFitHeight(74.0);
        med_btn.setFitWidth(197.0);
        med_btn.setLayoutX(103.0);
        med_btn.setLayoutY(244.0);
        med_btn.setPickOnBounds(true);
        med_btn.setPreserveRatio(true);
        med_btn.setCursor(Cursor.HAND);
        med_btn.setImage(new Image(getClass().getResource("/images/Medium.png").toExternalForm()));

        anchorPane.getChildren().add(imageView);
        anchorPane.getChildren().add(label);
        anchorPane.getChildren().add(easy_btn);
        anchorPane.getChildren().add(back);
        anchorPane.getChildren().add(sound);
        anchorPane.getChildren().add(soundOff);
        anchorPane.getChildren().add(hard_btn);
        anchorPane.getChildren().add(med_btn);
        getChildren().add(anchorPane);

        // when click on sound btn
        sound.setOnMouseClicked((event) -> {
            sound.setVisible(false);
            soundOff.setVisible(true);
            StartPane.audio.stop();
            AppManager.soundoff();
        });
        soundOff.setOnMouseClicked((event) -> {
            soundOff.setVisible(false);
            sound.setVisible(true);
            StartPane.audio.play();
            AppManager.soundON();

        });

        back.setOnMouseClicked((event) -> {
            AppManager.viewPane(AppManager.singlePlayerPane);

        });
/////////////when click on easy level///////////
        easy_btn.setOnMouseClicked((event) -> {

            easy_btn.setImage(new Image(getClass().getResource("/images/EasyPressed.png").toExternalForm()));

            Task<Void> sleeper = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                    }
                    return null;
                }
            };
            sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    AppManager.easyLevel = true;
                    AppManager.viewPane(AppManager.gamePane);
                    easy_btn.setImage(new Image(getClass().getResource("/images/Easy.png").toExternalForm()));
                }
            });
            new Thread(sleeper).start();

        });
        /////////////when click on mid level///////////
        med_btn.setOnMouseClicked((event) -> {

            med_btn.setImage(new Image(getClass().getResource("/images/MediumPressed.png").toExternalForm()));

            Task<Void> sleeper = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                    }
                    return null;
                }
            };
            sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    AppManager.midLevel = true;
                    AppManager.viewPane(AppManager.gamePane);
                    med_btn.setImage(new Image(getClass().getResource("/images/Medium.png").toExternalForm()));
                }
            });
            new Thread(sleeper).start();

        });
        /////////////when click on hard level///////////
        hard_btn.setOnMouseClicked((event) -> {

            hard_btn.setImage(new Image(getClass().getResource("/images/HardPressed.png").toExternalForm()));

            Task<Void> sleeper = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                    }
                    return null;
                }
            };
            sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    AppManager.hardLevel = true;
                    AppManager.viewPane(AppManager.gamePane);
                    hard_btn.setImage(new Image(getClass().getResource("/images/Hard.png").toExternalForm()));
                }
            });
            new Thread(sleeper).start();

        });
        //////////////////////////////////////////////////////////
    }

}
