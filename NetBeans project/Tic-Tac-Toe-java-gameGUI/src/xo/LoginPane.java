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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


/**
 *
 * @author ahmed
 */
public class LoginPane extends Pane {

    protected final AnchorPane anchorPane;
    protected final ImageView imageView;
    protected final TextField UserNameInput;
    protected final PasswordField UserPassword;
    protected final ImageView LogInBtn;
    protected final ImageView back_btn;
    protected final ImageView sound_btn;
    protected final ImageView imageView0;
    protected final ImageView rigister;
    protected final ImageView soundoff_btn;
    Alert alert = new Alert(Alert.AlertType.WARNING);
    
    ArrayList<String> info = new ArrayList<String>();

    public LoginPane() {

        anchorPane = new AnchorPane();
        imageView = new ImageView();
        UserNameInput = new TextField();
        UserPassword = new PasswordField();
        LogInBtn = new ImageView();
        back_btn = new ImageView();
        sound_btn = new ImageView();
        imageView0 = new ImageView();
        rigister = new ImageView();
        soundoff_btn = new ImageView();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(500.0);
        setPrefWidth(380.0);

        anchorPane.setLayoutX(-9.0);
        anchorPane.setLayoutY(-10.0);
        anchorPane.setMaxHeight(USE_PREF_SIZE);
        anchorPane.setMaxWidth(USE_PREF_SIZE);
        anchorPane.setMinHeight(USE_PREF_SIZE);
        anchorPane.setMinWidth(USE_PREF_SIZE);
        anchorPane.setPrefHeight(520.0);
        anchorPane.setPrefWidth(400.0);
        anchorPane.getStyleClass().add("bg_img");
        anchorPane.getStylesheets().add("/xo/style.css");

        AnchorPane.setLeftAnchor(imageView, 41.0);
        AnchorPane.setRightAnchor(imageView, 40.0);
        imageView.setFitHeight(293.0);
        imageView.setFitWidth(319.0);
        imageView.setLayoutX(41.0);
        imageView.setLayoutY(106.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(getClass().getResource("/images/Login.png").toExternalForm()));

        UserNameInput.setLayoutX(113.0);
        UserNameInput.setLayoutY(216.0);
        UserNameInput.setMaxHeight(USE_PREF_SIZE);
        UserNameInput.setMaxWidth(USE_PREF_SIZE);
        UserNameInput.setMinHeight(USE_PREF_SIZE);
        UserNameInput.setMinWidth(USE_PREF_SIZE);
        UserNameInput.setPrefHeight(30.0);
        UserNameInput.setPrefWidth(175.0);
        UserNameInput.setCursor(Cursor.TEXT);
        UserNameInput.getStyleClass().add("text-input");

        UserPassword.setLayoutX(113.0);
        UserPassword.setLayoutY(299.0);
        UserPassword.setMaxHeight(USE_PREF_SIZE);
        UserPassword.setMaxWidth(USE_PREF_SIZE);
        UserPassword.setMinHeight(USE_PREF_SIZE);
        UserPassword.setMinWidth(USE_PREF_SIZE);
        UserPassword.setPrefHeight(30.0);
        UserPassword.setPrefWidth(175.0);
        UserPassword.setCursor(Cursor.TEXT);
        UserPassword.getStyleClass().add("text-input");

        LogInBtn.setFitHeight(62.0);
        LogInBtn.setFitWidth(130.0);
        LogInBtn.setLayoutX(136.0);
        LogInBtn.setLayoutY(368.0);
        LogInBtn.setPickOnBounds(true);
        LogInBtn.setPreserveRatio(true);
        LogInBtn.setCursor(Cursor.HAND);
        LogInBtn.setImage(new Image(getClass().getResource("/images/Loginbtn.png").toExternalForm()));

        back_btn.setFitHeight(64.0);
        back_btn.setFitWidth(61.0);
        back_btn.setLayoutX(14.0);
        back_btn.setLayoutY(431.0);
        back_btn.setPickOnBounds(true);
        back_btn.setPreserveRatio(true);
        back_btn.setCursor(Cursor.HAND);
        back_btn.setImage(new Image(getClass().getResource("/images/back.png").toExternalForm()));

        sound_btn.setFitHeight(41.0);
        sound_btn.setFitWidth(47.0);
        sound_btn.setLayoutX(14.0);
        sound_btn.setLayoutY(14.0);
        sound_btn.setPickOnBounds(true);
        sound_btn.setPreserveRatio(true);
        sound_btn.setCursor(Cursor.HAND);
        sound_btn.setImage(new Image(getClass().getResource("/images/volume2x.png").toExternalForm()));

        soundoff_btn.setVisible(false);
        soundoff_btn.setFitHeight(41.0);
        soundoff_btn.setFitWidth(47.0);
        soundoff_btn.setLayoutX(14.0);
        soundoff_btn.setLayoutY(14.0);
        soundoff_btn.setPickOnBounds(true);
        soundoff_btn.setPreserveRatio(true);
        soundoff_btn.setCursor(Cursor.HAND);
        soundoff_btn.setImage(new Image(getClass().getResource("/images/volumemute2x.png").toExternalForm()));

        imageView0.setFitHeight(22.0);
        imageView0.setFitWidth(175.0);
        imageView0.setLayoutX(55.0);
        imageView0.setLayoutY(346.0);
        imageView0.setPickOnBounds(true);
        imageView0.setPreserveRatio(true);
        imageView0.setImage(new Image(getClass().getResource("/images/haveAccount.png").toExternalForm()));

        rigister.setFitHeight(39.0);
        rigister.setFitWidth(105.0);
        rigister.setLayoutX(243.0);
        rigister.setLayoutY(344.0);
        rigister.setCursor(Cursor.HAND);
        rigister.setPickOnBounds(true);
        rigister.setPreserveRatio(true);
        rigister.setImage(new Image(getClass().getResource("/images/RegisterNow.png").toExternalForm()));

        anchorPane.getChildren().add(imageView);
        anchorPane.getChildren().add(UserNameInput);
        anchorPane.getChildren().add(UserPassword);
        anchorPane.getChildren().add(LogInBtn);
        anchorPane.getChildren().add(back_btn);
        anchorPane.getChildren().add(sound_btn);
        anchorPane.getChildren().add(imageView0);
        anchorPane.getChildren().add(rigister);
        anchorPane.getChildren().add(soundoff_btn);
        getChildren().add(anchorPane);

        

        sound_btn.setOnMouseClicked((event) -> {
            sound_btn.setVisible(false);
            soundoff_btn.setVisible(true);
            AppManager.soundoff();
            StartPane.audio.stop();
        });
        soundoff_btn.setOnMouseClicked((event) -> {
            soundoff_btn.setVisible(false);
            sound_btn.setVisible(true);
            AppManager.soundON();
            StartPane.audio.play();
        });

        rigister.setOnMouseClicked((event) -> {
            AppManager.viewPane(AppManager.registerPane);
        });

        back_btn.setOnMouseClicked((event) -> {
            try {
                AppManager.objectInput.close();
                AppManager.objectOutput.close();
                AppManager.mySocket.close();
            } catch (IOException ex) {
                Logger.getLogger(LoginPane.class.getName()).log(Level.SEVERE, null, ex);
            }
            AppManager.viewPane(AppManager.onlinePane);
        });
        LogInBtn.setOnMouseClicked((event) -> {

            LogInBtn.setImage(new Image(getClass().getResource("/images/LoginbtnPressed.png").toExternalForm()));

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
                   
                    if (UserNameInput.getText().equals("") || UserPassword.getText().equals("")) {

                        alert.setTitle("Login");
                        alert.setContentText("must insert userName and Password");
                        alert.showAndWait();

                    } else if (UserPassword.getText().length() >= 10) {
                        alert.setTitle("Login");
                        alert.setContentText("Password must be less than 10 charcaters");
                        alert.showAndWait();
                    } else {

                        login();}
                    
                    LogInBtn.setImage(new Image(getClass().getResource("/images/Loginbtn.png").toExternalForm()));
                }
            });
            new Thread(sleeper).start();

        });

    }

    public void login() {
         ArrayList<String> msg=new  ArrayList<String>();
        info = new ArrayList<String>();
        info.add("true");
        info.add(UserNameInput.getText());
        info.add(UserPassword.getText());
        System.out.println("After"+info);
        try {
            AppManager.objectOutput.writeObject(info);
            AppManager.objectOutput.flush();
            System.out.println("Sent player1: "+info);

        } catch (IOException ex) {
            Logger.getLogger(LoginPane.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    
    public void LogenClear(){
        UserNameInput.clear();
        UserPassword.clear();
    }
    
}
