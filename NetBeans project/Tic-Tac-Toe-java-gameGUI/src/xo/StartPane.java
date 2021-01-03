package xo;

import java.io.File;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import static javafx.scene.media.AudioClip.INDEFINITE;


public class StartPane extends Pane {

    protected final AnchorPane anchorPane;
    protected final VBox vBox;
    protected final ImageView logo_img;
    protected final ImageView singleplayer_btn;
    protected final ImageView multiplayer_btn;
    protected final ImageView onlinemode_btn;
    protected final ImageView rec_btn;
    protected final ImageView sound_btn;
    protected final ImageView soundoff_btn;
    protected final ImageView exit_btn;
    protected final ImageView about_btn;
    File f = new File(GamePane.pathh);
    public static AudioClip audio;
    public static AudioClip audioGame;

    public StartPane() {
        anchorPane = new AnchorPane();
        vBox = new VBox();
        logo_img = new ImageView();
        singleplayer_btn = new ImageView();
        multiplayer_btn = new ImageView();
        onlinemode_btn = new ImageView();
        rec_btn = new ImageView();
        sound_btn = new ImageView();
        soundoff_btn = new ImageView();
        exit_btn = new ImageView();
        about_btn = new ImageView();
        
        audioGame = new AudioClip(getClass().getResource("/sounds/we-will-win-.mp3").toExternalForm());
        audioGame.setVolume(0.3f);

        audio = new AudioClip(getClass().getResource("/sounds/happy-place.mp3").toExternalForm());
        audio.setVolume(0.2f);
        audio.setCycleCount(INDEFINITE);
        audio.play();
        
        

        anchorPane.setMaxHeight(USE_PREF_SIZE);
        anchorPane.setMaxWidth(USE_PREF_SIZE);
        anchorPane.setMinHeight(USE_PREF_SIZE);
        anchorPane.setMinWidth(USE_PREF_SIZE);
        anchorPane.setPrefHeight(520.0);
        anchorPane.setPrefWidth(400.0);
        anchorPane.setLayoutX(0);
        anchorPane.setLayoutY(0);
        anchorPane.getStylesheets().add("/xo/style.css");
        anchorPane.getStyleClass().add("bg_img");

        vBox.setAlignment(javafx.geometry.Pos.CENTER);
        vBox.setLayoutX(5.0);
        vBox.setLayoutY(63.0);
        vBox.setMaxHeight(USE_PREF_SIZE);
        vBox.setMaxWidth(USE_PREF_SIZE);
        vBox.setMinHeight(USE_PREF_SIZE);
        vBox.setMinWidth(USE_PREF_SIZE);
        vBox.setPrefHeight(380.0);
        vBox.setPrefWidth(370.0);
        vBox.setSpacing(20.0);

        logo_img.setFitHeight(85.0);
        logo_img.setFitWidth(316.0);
        logo_img.setLayoutX(103.0);
        logo_img.setLayoutY(10.0);
        logo_img.setPickOnBounds(true);
        logo_img.setPreserveRatio(true);
        logo_img.setImage(new Image(getClass().getResource("/images/Logo2x.png").toExternalForm()));

        singleplayer_btn.setFitHeight(150.0);
        singleplayer_btn.setFitWidth(200.0);
        singleplayer_btn.setPickOnBounds(true);
        singleplayer_btn.setPreserveRatio(true);
        singleplayer_btn.setCursor(Cursor.HAND);
        singleplayer_btn.setImage(new Image(getClass().getResource("/images/btn1+.png").toExternalForm()));

        multiplayer_btn.setFitHeight(66.0);
        multiplayer_btn.setFitWidth(258.0);
        multiplayer_btn.setPickOnBounds(true);
        multiplayer_btn.setPreserveRatio(true);
        multiplayer_btn.setCursor(Cursor.HAND);
        multiplayer_btn.setImage(new Image(getClass().getResource("/images/btn2+.png").toExternalForm()));

        onlinemode_btn.setFitHeight(150.0);
        onlinemode_btn.setFitWidth(200.0);
        onlinemode_btn.setPickOnBounds(true);
        onlinemode_btn.setCursor(Cursor.HAND);
        onlinemode_btn.setPreserveRatio(true);
        onlinemode_btn.setImage(new Image(getClass().getResource("/images/btn3+.png").toExternalForm()));

        rec_btn.setFitHeight(61.0);
        rec_btn.setFitWidth(171.0);
        rec_btn.setLayoutX(103.0);
        rec_btn.setLayoutY(230.0);
        rec_btn.setCursor(Cursor.HAND);
        rec_btn.setPickOnBounds(true);
        rec_btn.setPreserveRatio(true);
        rec_btn.getStyleClass().add("btn_img");
        rec_btn.setImage(new Image(getClass().getResource("/images/rec2x.png").toExternalForm()));

        sound_btn.setFitHeight(39.0);
        sound_btn.setFitWidth(39.0);
        sound_btn.setLayoutX(12.0);
        sound_btn.setLayoutY(14.0);
        sound_btn.setPickOnBounds(true);
        sound_btn.setPreserveRatio(true);
        sound_btn.setCursor(Cursor.HAND);
        sound_btn.setImage(new Image(getClass().getResource("/images/volume2x.png").toExternalForm()));

        soundoff_btn.setFitHeight(39.0);
        soundoff_btn.setFitWidth(39.0);
        soundoff_btn.setLayoutX(12.0);
        soundoff_btn.setLayoutY(14.0);
        soundoff_btn.setPickOnBounds(true);
        soundoff_btn.setPreserveRatio(true);
        soundoff_btn.setVisible(false);
        soundoff_btn.setCursor(Cursor.HAND);
        soundoff_btn.setImage(new Image(getClass().getResource("/images/volumemute2x.png").toExternalForm()));

        exit_btn.setFitHeight(54.0);
        exit_btn.setFitWidth(39.0);
        exit_btn.setLayoutX(14.0);
        exit_btn.setLayoutY(430.0);
        exit_btn.setPickOnBounds(true);
        exit_btn.setCursor(Cursor.HAND);
        exit_btn.setPreserveRatio(true);
        exit_btn.setImage(new Image(getClass().getResource("/images/exit2x.png").toExternalForm()));

        about_btn.setFitHeight(46.0);
        about_btn.setCursor(Cursor.HAND);
        about_btn.setFitWidth(45.0);
        about_btn.setLayoutX(321.0);
        about_btn.setLayoutY(435.0);
        about_btn.setPickOnBounds(true);
        about_btn.setPreserveRatio(true);
        about_btn.setImage(new Image(getClass().getResource("/images/About2x.png").toExternalForm()));

        vBox.getChildren().add(logo_img);
        vBox.getChildren().add(singleplayer_btn);
        vBox.getChildren().add(multiplayer_btn);
        vBox.getChildren().add(onlinemode_btn);
        vBox.getChildren().add(rec_btn);
        anchorPane.getChildren().add(vBox);
        anchorPane.getChildren().add(sound_btn);
        anchorPane.getChildren().add(soundoff_btn);
        anchorPane.getChildren().add(exit_btn);
        anchorPane.getChildren().add(about_btn);
        getChildren().add(anchorPane);

        // when click on single player btn
        singleplayer_btn.setOnMouseClicked((event) -> {

            singleplayer_btn.setImage(new Image(getClass().getResource("/images/btnclick1.png").toExternalForm()));

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
                    AppManager.viewPane(AppManager.singlePlayerPane);
                    singleplayer_btn.setImage(new Image(getClass().getResource("/images/btn1+.png").toExternalForm()));
                }
            });
            new Thread(sleeper).start();

        });

        multiplayer_btn.setOnMouseClicked((event) -> {
            multiplayer_btn.setImage(new Image(getClass().getResource("/images/btnclick2.png").toExternalForm()));
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
                    AppManager.viewPane(AppManager.multiPlayerPane);
                    multiplayer_btn.setImage(new Image(getClass().getResource("/images/btn2+.png").toExternalForm()));
                }
            });
            new Thread(sleeper).start();
        });
        // when click on online player btn
        onlinemode_btn.setOnMouseClicked((event) -> {
            onlinemode_btn.setImage(new Image(getClass().getResource("/images/btnclick3.png").toExternalForm()));
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
                    AppManager.viewPane(AppManager.onlinePane);
                    onlinemode_btn.setImage(new Image(getClass().getResource("/images/btn3+.png").toExternalForm()));
                }
            });
            new Thread(sleeper).start();

        });
        // when click on recorde btn 
        rec_btn.setOnMouseClicked((event) -> {
            AppManager.viewPane(AppManager.recordsPane);
            AppManager.recordsPane.comboBox.getItems().clear();// need a {placeholder}
            if (f.length() > 0) {													//ADD THIS CONDITION BEFORE 
                AppManager.recordsPane.comboBox.getItems().addAll(f.list());
            }
            AppManager.viewPane(AppManager.recordsPane);
        });
        // when click on sound btn
        sound_btn.setOnMouseClicked((event) -> {
            sound_btn.setVisible(false);
            soundoff_btn.setVisible(true);
            AppManager.soundoff();
             audio.stop();
        });
        soundoff_btn.setOnMouseClicked((event) -> {
            soundoff_btn.setVisible(false);
            sound_btn.setVisible(true);
            AppManager.soundON();
             audio.play();

        });
        exit_btn.setOnMouseClicked((event) -> {
            System.exit(0);
        });
        about_btn.setOnMouseClicked((event) -> {
            AppManager.viewPane(AppManager.aboutPane);

        });

    }
}
