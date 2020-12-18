package xo;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class RecordsPane extends Pane {

    protected final AnchorPane anchorPane;
    protected final ImageView imageView;
    protected final Label label;
    protected final ImageView play_btn;
    protected final ImageView back;
    protected final ImageView sound;
    protected final ImageView soundOff;
    protected final ComboBox comboBox;
    protected final ImageView playPressed;

    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    String[] indxsss;
    static Queue<String> recQ = new LinkedList<>(); //for recieving indexes
    String selectedFile = "";

    public RecordsPane() {

        anchorPane = new AnchorPane();
        imageView = new ImageView();
        label = new Label();
        play_btn = new ImageView();
        back = new ImageView();
        sound = new ImageView();
        soundOff = new ImageView();
        comboBox = new ComboBox();
        playPressed = new ImageView();

        anchorPane.setPrefHeight(500.0);
        anchorPane.setPrefWidth(380.0);
        anchorPane.getStylesheets().add("/xo/style.css");

        imageView.setFitHeight(525.0);
        imageView.setFitWidth(420.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(getClass().getResource("/images/bg.png").toExternalForm()));

        label.setLayoutX(80.0);
        label.setLayoutY(104.0);
        label.setText("View All Records");
        label.setTextFill(javafx.scene.paint.Color.WHITE);
        label.setFont(new Font("Luducudu", 32.0));

        play_btn.setFitHeight(74.0);
        play_btn.setFitWidth(197.0);
        play_btn.setLayoutX(93.0);
        play_btn.setLayoutY(296.0);
        play_btn.setPickOnBounds(true);
        play_btn.setPreserveRatio(true);
        play_btn.setCursor(Cursor.HAND);
        play_btn.setImage(new Image(getClass().getResource("/images/Start.png").toExternalForm()));

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
        soundOff.setCursor(Cursor.HAND);
        soundOff.setPickOnBounds(true);
        soundOff.setPreserveRatio(true);
        soundOff.setVisible(false);
        soundOff.setImage(new Image(getClass().getResource("/images/volumemute2x.png").toExternalForm()));

        comboBox.setLayoutX(80.0);
        comboBox.setLayoutY(200.0);
        comboBox.setPrefHeight(37.0);
        comboBox.setPrefWidth(219.0);
        comboBox.setPromptText("SELECT");

        playPressed.setFitHeight(127.0);
        playPressed.setFitWidth(101.0);
        playPressed.setLayoutX(150.0);
        playPressed.setLayoutY(278.0);
        playPressed.setPickOnBounds(true);
        playPressed.setPreserveRatio(true);
        playPressed.setVisible(false);
        playPressed.setImage(new Image(getClass().getResource("/images/PlayButtenPressed.png").toExternalForm()));

        anchorPane.getChildren().add(imageView);
        anchorPane.getChildren().add(label);
        anchorPane.getChildren().add(play_btn);
        anchorPane.getChildren().add(back);
        anchorPane.getChildren().add(sound);
        anchorPane.getChildren().add(soundOff);
        anchorPane.getChildren().add(comboBox);
        anchorPane.getChildren().add(playPressed);
        getChildren().add(anchorPane);

        // when click on sound btn
        sound.setOnMouseClicked((event) -> {
            sound.setVisible(false);
            soundOff.setVisible(true);
            LoginPane.audio.stop();
        });
        soundOff.setOnMouseClicked((event) -> {
            soundOff.setVisible(false);
            sound.setVisible(true);
            LoginPane.audio.play();

        });

        back.setOnMouseClicked((event) -> {
            AppManager.isRec = false;               //if user back to stratPane, return it to false cuase it is a static var.
            AppManager.viewPane(AppManager.startPane);

        });
        comboBox.setOnAction((event) -> {  // ده بدل ال  addListener 
            selectedFile = (String) comboBox.getSelectionModel().getSelectedItem();
            System.out.println("selected File name -=->" + comboBox.getSelectionModel().getSelectedItem());
        });

        play_btn.setOnMouseClicked((event) -> {
            play_btn.setImage(new Image(getClass().getResource("/images/StartPressed.png").toExternalForm()));

            Task<Void> sleeper = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                    }
                    return null;
                }
            };
            sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    if (selectedFile.equals("")) {  //handle if file does NOT exist
                        String str = "";
                        alert.setTitle("Empty Folder");
                        alert.setHeaderText("There are no files!!");
                        alert.showAndWait();
                    } else {
                        playRec();
                        AppManager.gamePane.firstPlayerName.setText(indxsss[0]);
                        AppManager.gamePane.secondPlayerName.setText(indxsss[1]);
                        AppManager.gamePane.firstPlayerScore.setText(indxsss[2]);
                        AppManager.gamePane.secondPlayerScore.setText(indxsss[3]);
                        AppManager.gamePane.newGame.setVisible(false);

                        AppManager.isRec = true;               //open Board in Rec mode 

                        AppManager.viewPane(AppManager.gamePane);
                        AppManager.gamePane.play_rec();
                    }
                    play_btn.setImage(new Image(getClass().getResource("/images/Start.png").toExternalForm()));
                }
            });
            new Thread(sleeper).start();

        });
    }

    public void playRec() {
        FileInputStream fis;
        String pth = GamePane.pathh + File.separator + selectedFile;
        String res;
        try {
            fis = new FileInputStream(pth);
            DataInputStream dis;
            dis = new DataInputStream(fis);
            res = dis.readLine();
            indxsss = res.split("#");
            recIndxes(indxsss);
            dis.close();
            fis.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GamePane.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GamePane.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void recIndxes(String[] s) {
        recQ = new LinkedList<>();
        for (int i = 4; i < s.length; i++) {
            recQ.add(s[i]);
        }
    }
}
