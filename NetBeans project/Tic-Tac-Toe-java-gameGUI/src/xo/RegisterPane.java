/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class RegisterPane extends Pane {

    protected final AnchorPane anchorPane;
    protected final ImageView imageView;
    protected final TextField firstName;
    protected final TextField secondName;
    protected final TextField userName;
    protected final PasswordField password;
    protected final ImageView registerbtn;
    protected final ImageView imageView0;
    protected final ImageView imageView1;
    protected final RadioButton radioButton;
    protected final RadioButton radioButton0;
    protected final ImageView soundbtn;
    protected final ImageView backbtn;
    protected final ImageView soundoffbtn;
    protected String gender;
    ToggleGroup tg;

    Alert alert = new Alert(Alert.AlertType.WARNING);
    DialogPane dialogPane = alert.getDialogPane();
    ArrayList<String> info = new ArrayList<String>();

    public RegisterPane() {
        anchorPane = new AnchorPane();
        imageView = new ImageView();
        firstName = new TextField();
        secondName = new TextField();
        userName = new TextField();
        password = new PasswordField();
        registerbtn = new ImageView();
        imageView0 = new ImageView();
        imageView1 = new ImageView();
        radioButton = new RadioButton();
        radioButton0 = new RadioButton();
        soundbtn = new ImageView();
        backbtn = new ImageView();
        soundoffbtn = new ImageView();
        tg = new ToggleGroup();
        dialogPane.getStylesheets().add( getClass().getResource("/xo/style.css").toExternalForm());

        radioButton.setToggleGroup(tg);
        radioButton0.setToggleGroup(tg);

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(500.0);
        setPrefWidth(380.0);

        anchorPane.setPrefHeight(500.0);
        anchorPane.setPrefWidth(380.0);
        anchorPane.getStylesheets().add("/xo/style.css");

        imageView.setFitHeight(452.0);
        imageView.setFitWidth(297.0);
        imageView.setLayoutX(48.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(getClass().getResource("/images/Register.png").toExternalForm()));

        firstName.setLayoutX(112.0);
        firstName.setLayoutY(96.0);
        firstName.setMaxHeight(USE_PREF_SIZE);
        firstName.setMaxWidth(USE_PREF_SIZE);
        firstName.setMinHeight(USE_PREF_SIZE);
        firstName.setMinWidth(USE_PREF_SIZE);
        firstName.getStylesheets().add("/xo/style.css");
        firstName.setCursor(Cursor.TEXT);

        secondName.setLayoutX(112.0);
        secondName.setLayoutY(170.0);
        secondName.getStylesheets().add("/xo/style.css");
        secondName.setCursor(Cursor.TEXT);

        userName.setLayoutX(112.0);
        userName.setLayoutY(240.0);
        userName.getStylesheets().add("/xo/style.css");
        userName.setCursor(Cursor.TEXT);

        password.getStylesheets().add("/xo/style.css");
        password.setLayoutX(112.0);
        password.setLayoutY(310.0);
        password.setCursor(Cursor.TEXT);

        registerbtn.setFitHeight(44.0);
        registerbtn.setFitWidth(128.0);
        registerbtn.setLayoutX(131.0);
        registerbtn.setLayoutY(435.0);
        registerbtn.setPickOnBounds(true);
        registerbtn.setPreserveRatio(true);
        registerbtn.setCursor(Cursor.HAND);
        registerbtn.setImage(new Image(getClass().getResource("/images/RegisterBtn.png").toExternalForm()));

        imageView0.setFitHeight(27.0);
        imageView0.setFitWidth(80.0);
        imageView0.setLayoutX(229.0);
        imageView0.setLayoutY(390.0);
        imageView0.setPickOnBounds(true);
        imageView0.setPreserveRatio(true);
        imageView0.setX(5.0);
        imageView0.setY(5.0);
        imageView0.setImage(new Image(getClass().getResource("/images/Female.png").toExternalForm()));

        imageView1.setFitHeight(27.0);
        imageView1.setFitWidth(80.0);
        imageView1.setLayoutX(97.0);
        imageView1.setLayoutY(390.0);
        imageView1.setPickOnBounds(true);
        imageView1.setPreserveRatio(true);
        imageView1.setX(5.0);
        imageView1.setY(5.0);
        imageView1.setImage(new Image(getClass().getResource("/images/Male.png").toExternalForm()));

        radioButton.setLayoutX(61.0);
        radioButton.setLayoutY(398.0);
        radioButton.setMaxHeight(USE_PREF_SIZE);
        radioButton.setMaxWidth(USE_PREF_SIZE);
        radioButton.setMinHeight(USE_PREF_SIZE);
        radioButton.setMinWidth(USE_PREF_SIZE);
        radioButton.setMnemonicParsing(false);
        radioButton.setPrefHeight(27.0);
        radioButton.setPrefWidth(23.0);

        radioButton0.setLayoutX(190.0);
        radioButton0.setLayoutY(397.0);
        radioButton0.setMnemonicParsing(false);
        radioButton0.setPrefHeight(27.0);
        radioButton0.setPrefWidth(23.0);

        soundbtn.setFitHeight(44.0);
        soundbtn.setFitWidth(53.0);
        soundbtn.setPickOnBounds(true);
        soundbtn.setPreserveRatio(true);
        soundbtn.setCursor(Cursor.HAND);
        soundbtn.setImage(new Image(getClass().getResource("/images/volume2x.png").toExternalForm()));

        soundoffbtn.setVisible(false);
        soundoffbtn.setFitHeight(44.0);
        soundoffbtn.setFitWidth(53.0);
        soundoffbtn.setPickOnBounds(true);
        soundoffbtn.setPreserveRatio(true);
        soundoffbtn.setPickOnBounds(true);
        soundoffbtn.setCursor(Cursor.HAND);
        soundoffbtn.setImage(new Image(getClass().getResource("/images/volumemute2x.png").toExternalForm()));

        backbtn.setFitHeight(44.0);
        backbtn.setFitWidth(47.0);
        backbtn.setLayoutX(-1.0);
        backbtn.setLayoutY(453.0);
        backbtn.setPickOnBounds(true);
        backbtn.setPreserveRatio(true);
        backbtn.setCursor(Cursor.HAND);
        backbtn.setImage(new Image(getClass().getResource("/images/back.png").toExternalForm()));

        anchorPane.getChildren().add(imageView);
        anchorPane.getChildren().add(firstName);
        anchorPane.getChildren().add(secondName);
        anchorPane.getChildren().add(userName);
        anchorPane.getChildren().add(password);
        anchorPane.getChildren().add(registerbtn);
        anchorPane.getChildren().add(imageView0);
        anchorPane.getChildren().add(imageView1);
        anchorPane.getChildren().add(radioButton);
        anchorPane.getChildren().add(backbtn);
        anchorPane.getChildren().add(soundoffbtn);
        anchorPane.getChildren().add(soundbtn);
        anchorPane.getChildren().add(radioButton0);
        getChildren().add(anchorPane);

      

        soundbtn.setOnMouseClicked((event) -> {
            soundbtn.setVisible(false);
            soundoffbtn.setVisible(true);
            AppManager.soundoff();
            StartPane.audio.stop();
        });
        soundoffbtn.setOnMouseClicked((event) -> {
            soundoffbtn.setVisible(false);
            soundbtn.setVisible(true);
            AppManager.soundON();
            StartPane.audio.play();
        });
        registerbtn.setOnMouseClicked((event) -> {

            registerbtn.setImage(new Image(getClass().getResource("/images/RegisterBtnPressd.png").toExternalForm()));

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
                  
                    register();
                    
                    
                    registerbtn.setImage(new Image(getClass().getResource("/images/RegisterBtn.png").toExternalForm()));

                }
            });
            new Thread(sleeper).start();

        });

        backbtn.setOnMouseClicked((Action) -> {
            AppManager.viewPane(AppManager.onlinePane);
        });
    }

    public void register() {
       ArrayList<String> msg=new  ArrayList<String>();
        info = new ArrayList<String>();
        System.out.println("B4"+info);
        String gender1="";
        
        if (tg.getSelectedToggle() != null) {
            if (tg.getSelectedToggle() == radioButton) {
                gender1 = "Male";
            } else if (tg.getSelectedToggle() == radioButton0) {
                gender1 = "Female";

            }
        }
       
            if (userName.getText().equals("") || password.getText().equals("")||firstName.getText().equals("") || secondName.getText().equals("")) {

                alert.setTitle("Regestartion");
                alert.setContentText("must insert in All fields except gender");
                alert.showAndWait();

            }else if(password.getText().length()>=10){
                alert.setTitle("Registration");
                alert.setContentText("Password must be less than 10 charcaters");
                alert.showAndWait();
            }else {
            info.add("false");
            info.add(userName.getText());
            info.add(password.getText());
            info.add(firstName.getText());
            info.add(secondName.getText());
            info.add(gender1);
            System.out.println("After" + info);
            try {
                AppManager.objectOutput.writeObject(info);
                AppManager.objectOutput.flush();
                System.out.println("Sent player1: " + info);
            } catch (IOException ex) {
                Logger.getLogger(RegisterPane.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
    }
    
    
    public void RegisterClear(){
        
        firstName.clear();
        secondName.clear();
        userName.clear();
        password.clear();
        
    }
    
}
