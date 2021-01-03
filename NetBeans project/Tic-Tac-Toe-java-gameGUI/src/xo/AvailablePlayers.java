/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xo;

import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 *
 * @author ahmed
 */
public class AvailablePlayers extends Pane implements Runnable{

    protected final AnchorPane anchorPane;
    protected final ImageView imageView;
    public static ListView<String> player_list;

    protected final ImageView rec;
    protected final ImageView sound;
    protected final ImageView logout;
    protected final HBox hBox;
    protected final ImageView avatar;
    protected final VBox vBox;
    public static Label player_name;
    public static Label player_username;
    protected final ImageView score_btn;
     protected final Label note;
    protected final ImageView soundoff_btn;
    protected final ImageView recON_btn;
    public static Thread th1;

    ArrayList<String> availablePlayers;
    ArrayList<String> allScores;
    ArrayList<String> log = new ArrayList<String>();

    public AvailablePlayers() {

        anchorPane = new AnchorPane();
        imageView = new ImageView();
        player_list = new ListView<String>();
    
        rec = new ImageView();
        sound = new ImageView();
        logout = new ImageView();
        hBox = new HBox();
        avatar = new ImageView();
        vBox = new VBox();
        player_name = new Label();
        player_username = new Label();
        score_btn = new ImageView();
        note = new Label();
        soundoff_btn = new ImageView();
        recON_btn = new ImageView();
        th1=new Thread(this);

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(500.0);
        setPrefWidth(380.0);

        anchorPane.setPrefHeight(200.0);
        anchorPane.setPrefWidth(200.0);
        anchorPane.getStylesheets().add("/xo/style.css");

        imageView.setFitHeight(520.0);
        imageView.setFitWidth(425.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(getClass().getResource("/images/bg.png").toExternalForm()));

        player_list.setLayoutX(58.0);
        player_list.setLayoutY(127.0);
        player_list.setPrefHeight(279.0);
        player_list.setPrefWidth(265.0);

        note.setLayoutX(76.0);
        note.setLayoutY(412.0);
        note.setText("Note:press on player name to send request.");
        note.setTextFill(javafx.scene.paint.Color.WHITE);
        note.setFont(new Font("Luducudu", 13.0));


        rec.setFitHeight(68.0);
        rec.setFitWidth(67.0);
        rec.setLayoutX(299.0);
        rec.setLayoutY(432.0);
        rec.setPickOnBounds(true);
        rec.setPreserveRatio(true);
        rec.setImage(new Image(getClass().getResource("/images/record2.png").toExternalForm()));
        rec.setCursor(Cursor.HAND);

        recON_btn.setFitHeight(68.0);
        recON_btn.setFitWidth(67.0);
        recON_btn.setLayoutX(299.0);
        recON_btn.setLayoutY(432.0);
        recON_btn.setPickOnBounds(true);
        recON_btn.setPreserveRatio(true);
        recON_btn.setVisible(false);
        recON_btn.setCursor(Cursor.HAND);
        recON_btn.setImage(new Image(getClass().getResource("/images/record5.png").toExternalForm()));

        sound.setFitHeight(35.0);
        sound.setFitWidth(37.0);
        sound.setLayoutX(12.0);
        sound.setLayoutY(9.0);
        sound.setPickOnBounds(true);
        sound.setPreserveRatio(true);
        sound.setCursor(Cursor.HAND);
        sound.setImage(new Image(getClass().getResource("/images/volume2x.png").toExternalForm()));

        soundoff_btn.setFitHeight(35.0);
        soundoff_btn.setFitWidth(37.0);
        soundoff_btn.setLayoutX(12.0);
        soundoff_btn.setLayoutY(9.0);
        soundoff_btn.setCursor(Cursor.HAND);
        soundoff_btn.setPickOnBounds(true);
        soundoff_btn.setPreserveRatio(true);
        soundoff_btn.setVisible(false);
        soundoff_btn.setImage(new Image(getClass().getResource("/images/volumemute2x.png").toExternalForm()));

        logout.setFitHeight(35.0);
        logout.setFitWidth(42.0);
        logout.setLayoutX(14.0);
        logout.setLayoutY(439.0);
        logout.setPickOnBounds(true);
        logout.setPreserveRatio(true);
        logout.setImage(new Image(getClass().getResource("/images/Logout.png").toExternalForm()));
        logout.setCursor(Cursor.HAND);

        hBox.setLayoutX(62.0);
        hBox.setLayoutY(27.0);
        hBox.setPrefHeight(100.0);
        hBox.setPrefWidth(265.0);
        hBox.setSpacing(8.0);

        avatar.setFitHeight(100.0);
        avatar.setFitWidth(83.0);
        avatar.setPickOnBounds(true);
        avatar.setPreserveRatio(true);
        avatar.setImage(new Image(getClass().getResource("/images/PLayer.png").toExternalForm()));
        
        

        vBox.setPrefHeight(100.0);
        vBox.setPrefWidth(164.0);
        vBox.setSpacing(2.0);

        player_name.setPrefHeight(35.0);
        player_name.setPrefWidth(137.0);
        player_name.setText("Ola Abaza");
        player_name.setTextFill(javafx.scene.paint.Color.WHITE);
        player_name.setFont(new Font("Luducudu", 18));

        player_username.setText("ola2100");
        player_username.setTextFill(javafx.scene.paint.Color.WHITE);
        player_username.setFont(new Font("Luducudu", 13.0));

        score_btn.setFitHeight(58.0);
        score_btn.setFitWidth(50.0);
        score_btn.setLayoutX(275.0);
        score_btn.setLayoutY(27.0);
        score_btn.setPickOnBounds(true);
        score_btn.setPreserveRatio(true);
        score_btn.setImage(new Image(getClass().getResource("/images/Score.png").toExternalForm()));
        score_btn.setCursor(Cursor.HAND);

       

        anchorPane.getChildren().add(imageView);
        anchorPane.getChildren().add(player_list);

        anchorPane.getChildren().add(rec);
        anchorPane.getChildren().add(recON_btn);
        anchorPane.getChildren().add(sound);
        anchorPane.getChildren().add(soundoff_btn);
        anchorPane.getChildren().add(logout);
        hBox.getChildren().add(avatar);
        vBox.getChildren().add(player_name);
        vBox.getChildren().add(player_username);
        hBox.getChildren().add(vBox);
        hBox.getChildren().add(score_btn);
        anchorPane.getChildren().add(note);
        anchorPane.getChildren().add(hBox);

        getChildren().add(anchorPane);
        
        
        player_list.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("clicked on " + player_list.getSelectionModel().getSelectedItem());

                if (player_list.getSelectionModel().getSelectedItem() != null) {
                    sendRequest(player_list.getSelectionModel().getSelectedItem());
                    AppManager.availablePane.setDisable(true);
                    recON_btn.setVisible(false);
                    rec.setVisible(true);
                }
            }
        });

        logout.setOnMouseClicked((event) -> {
            Platform.runLater(() -> {
            AppManager.availablePane.th1.stop();
                System.out.println("thread stop");
            });
            log = new ArrayList<String>();
            log.add("logOut");
            try {
                AppManager.objectOutput.writeObject(log);
                AppManager.objectOutput.flush();
               
            } catch (IOException ex) {
                Logger.getLogger(AppManager.class.getName()).log(Level.SEVERE, null, ex);
            }   
            AppManager.isOnline=false;
            AppManager.viewPane(AppManager.loginPane);

        });
        
        score_btn.setOnMouseClicked((event) -> {
            allScores = new ArrayList<String>();
            allScores.add("AllScores");
            try {
                AppManager.objectOutput.writeObject(allScores);
                AppManager.objectOutput.flush();
            } catch (IOException ex) {
                Logger.getLogger(AppManager.class.getName()).log(Level.SEVERE, null, ex);
            }

            AppManager.viewPane(AppManager.scorePane);
        });

        sound.setOnMouseClicked((event) -> {
            sound.setVisible(false);
            soundoff_btn.setVisible(true);
            AppManager.soundoff();
            StartPane.audio.stop();
        });
        soundoff_btn.setOnMouseClicked((event) -> {
            soundoff_btn.setVisible(false);
            sound.setVisible(true);
            AppManager.soundON();
            StartPane.audio.play();

        });

        rec.setOnMouseClicked((event) -> {
            AppManager.rec = true;
            rec.setVisible(false);
            recON_btn.setVisible(true);

        });
        recON_btn.setOnMouseClicked((event) -> {
            AppManager.rec = false;
            recON_btn.setVisible(false);
            rec.setVisible(true);
        });
        
         // when click on single player btn
        /*start_btn.setOnMouseClicked((event) -> {

            start_btn.setImage(new Image(getClass().getResource("/images/StartPressed.png").toExternalForm()));

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
                    
                    //sendRequest();
                    
                    //th1.stop();
                    start_btn.setImage(new Image(getClass().getResource("/images/Start.png").toExternalForm()));
                }
            });
            new Thread(sleeper).start();

        });*/
        
       
        
        
    }
   

   public void sendRequest(String selecetedPlayer) {
        
       AppManager.isMyTurn = true;
       AppManager.amISender = true;
       
       System.out.println(selecetedPlayer);
       
           //selecetedPlayer =""+(String)player_list.getSelectionModel().getSelectedItem();
        ArrayList<String> data = new ArrayList<String>();
        data.add(selecetedPlayer);
        try {
            AppManager.objectOutput.writeObject(data);
            AppManager.objectOutput.flush();
        } catch (IOException ex) {
            Logger.getLogger(AvailablePlayers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
   
   
   public void getAvailableThread(){
       new Thread() {
 ArrayList<String> availablePlayers;
           @Override
           public void run() {

               while (true) {
                  
                   availablePlayers = new ArrayList<String>();
                   availablePlayers.add("refresh");
                   try {
                       AppManager.objectOutput.writeObject(availablePlayers);
                       AppManager.objectOutput.flush();
                        sleep(5000);
                        
                   }catch(SocketException ex){
                       stop();
                   }
                   catch (IOException ex) {
                       Logger.getLogger(AvailablePlayers.class.getName()).log(Level.SEVERE, null, ex);
                   } catch (InterruptedException ex) {
                       Logger.getLogger(AvailablePlayers.class.getName()).log(Level.SEVERE, null, ex);
                   }

               }

           }

       }.start();

       
   }

    @Override
    public void run() {
        
         while (true) {
                  
                   availablePlayers = new ArrayList<String>();
                   availablePlayers.add("refresh");
                   try {
                       AppManager.objectOutput.writeObject(availablePlayers);
                       AppManager.objectOutput.flush();
                        th1.sleep(3000);
                        
                   }catch(SocketException ex){
                       th1.stop();
                   }
                   catch (IOException ex) {
                       Logger.getLogger(AvailablePlayers.class.getName()).log(Level.SEVERE, null, ex);
                   } catch (InterruptedException ex) {
                       Logger.getLogger(AvailablePlayers.class.getName()).log(Level.SEVERE, null, ex);
                   }

               }
        
    }


}


    
   

