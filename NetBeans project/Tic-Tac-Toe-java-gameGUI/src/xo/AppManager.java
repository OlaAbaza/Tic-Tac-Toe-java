package xo;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import static javafx.scene.media.AudioClip.INDEFINITE;
import javafx.scene.text.Font;
// static قمنا بإنشائه لتمرير القيم المشتركة بين حاويات اللعبة بسهولة لهذا قمنا بتعريف كل شيء فيه كـ AppManager الكلاس

public class AppManager extends Thread {

    // هنا قمنا بإنشاء كائن من كل كلاس يمثل حاوية قمنا بتجهيزه سابقاً
    static StartPane startPane = new StartPane();
    static SinglePlayerPane singlePlayerPane = new SinglePlayerPane();
    static MultiPlayerPane multiPlayerPane = new MultiPlayerPane();
    static AboutPane aboutPane = new AboutPane();
    static ScoresPane scorePane = new ScoresPane();
    static OnlinePane onlinePane = new OnlinePane();
    static RecordsPane recordsPane = new RecordsPane();
    static GamePane gamePane = new GamePane();
    static LoginPane loginPane = new LoginPane();
    static RegisterPane registerPane = new RegisterPane();
    static AvailablePlayers availablePane = new AvailablePlayers();
    static GameLevels gameLevels = new GameLevels();
    // RecordsPane سنخزن فيه إسم صورة خلفية اللعبة التي يستطيع المستخدم تغييرها من الحاوية preferredBoard المتغير
    static String preferredBoard; // ***DLT***

    // RecordsPane سنخزن فيه حجم خط كل زر, نص و مربع نص أضفناه في اللعبة و الذي يستطيع المستخدم تغييره من الحاوية preferredFont الكائن
    static Font preferredFont;  // ***DLT***

    // للإشارة إلى أنه سيتم اللعب ضد الكمبيوتر SinglePlayerPane الموضوع في الحاوية start عند النقر على الزر true سنخزن فيه القيمة challengeComputer المتغير
    static boolean challengeComputer;
    static boolean isRec;  // to know if record mode or playing mode
    static boolean rec;    // to record the steps or not (if user pressed on record button to record the game).

    static Socket mySocket;
    static ObjectOutputStream objectOutput;
    static ObjectInputStream objectInput;

    static ArrayList<String> arr;
    //static ArrayList<String> availablePlayers;
    static boolean terminatorMoves = true;
    static boolean terminatorRequest = true;
    static String playerName;
    static String opponentName;
    static  String Gender="";
    static boolean accept = false;
    
 
    static boolean easyLevel;
    static boolean midLevel;
    static boolean hardLevel;

    static boolean isMyTurn = true;
    static boolean amISender = false;
    static boolean isOnline = false;
     static boolean isNewGame = false;
    static ArrayList<Score> Scores;

    // pane الدالة التالية نستخدمها لإخفاء أي نافذة معروضة حالياً في النافذة و عرض الحاوية التي نمررها لها فقط مكان الباراميتر
    public static void viewPane(Pane pane) {
        startPane.setVisible(false);
        singlePlayerPane.setVisible(false);
        multiPlayerPane.setVisible(false);
        onlinePane.setVisible(false);
        recordsPane.setVisible(false);
        gamePane.setVisible(false);
        loginPane.setVisible(false);
        registerPane.setVisible(false);
        availablePane.setVisible(false);
        aboutPane.setVisible(false);
        scorePane.setVisible(false);
        gameLevels.setVisible(false);
        
        if(pane==gamePane){
            gamePane.sound.setVisible(true);
            gamePane.soundoff_btn.setVisible(false);
            startPane.audio.stop();
            startPane.audioGame.setCycleCount(INDEFINITE);
            startPane.audioGame.play();
        }
        pane.setVisible(true);
    }

     public static boolean connect(String ip) {
        try {
            mySocket = new Socket(ip, 5005);
            objectOutput = new ObjectOutputStream(mySocket.getOutputStream());
            objectInput = new ObjectInputStream(mySocket.getInputStream());
            return true;
        } catch (ConnectException ex) {
            System.out.println("serverOFF");
            return false;
        } catch (IOException ex) {
            Logger.getLogger(AppManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    
    public static void recieving() {
        new Thread() {
            ArrayList<String> data = new ArrayList<String>();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            DialogPane dialogPane = alert.getDialogPane();
            Alert alert3 = new Alert(Alert.AlertType.CONFIRMATION);
            DialogPane dialogPane3 = alert3.getDialogPane();
            
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            DialogPane dialogPane2 = alert2.getDialogPane();
            
            ArrayList<String> sendData;

            @Override
            public void run() {
                dialogPane.getStylesheets().add( getClass().getResource("/xo/style.css").toExternalForm());
                dialogPane2.getStylesheets().add( getClass().getResource("/xo/style.css").toExternalForm());
                 dialogPane3.getStylesheets().add( getClass().getResource("/xo/style.css").toExternalForm());
                System.out.println("11111");
                while (terminatorRequest) {
                    try {
                        data = (ArrayList<String>) objectInput.readObject();
                        System.out.println("RUN  " + data);
                        if (data.get(0).equals("yes") || data.get(0).equals("no")) { //sending and reciving the position in the game    
                            if (data.get(0).equals("yes")) {
                                AppManager.availablePane.th1.stop();
                                amISender = true;
                                playerName = data.get(1);
                                opponentName = data.get(2);
                                Platform.runLater(() -> {
                                    gamePane.firstPlayerName.setText(playerName);
                                    gamePane.secondPlayerName.setText(opponentName);
                                    if (AppManager.Gender.equals("Female")) {
                                        gamePane.playeriImg.setImage(new Image(getClass().getResource("/images/AvatarF.png").toExternalForm()));
                                    } else if (AppManager.Gender.equals("Male")) {
                                        gamePane.playeriImg.setImage(new Image(getClass().getResource("/images/PLayer.png").toExternalForm()));
                                    } else {
                                        gamePane.playeriImg.setImage(new Image(getClass().getResource("/images/OIP.jpg").toExternalForm()));
                                    }
                                });
                                isOnline = true;
                                Platform.runLater(() -> {
                                    gamePane.startNewGame();
                                });
                                viewPane(gamePane);
                                //gamePane.recieveingMoves();
                                //stop();

                            } else {
                                Platform.runLater(() -> {
                                    alert2.setTitle("Infirmation Dialog");
                                    alert2.setContentText("Sorry, rejected");
                                    alert2.showAndWait();
                                    //gamePane.removeHandler();
                                    StartPane.audioGame.stop();
                                    StartPane.audio.play();
                                    AppManager.soundON();
                                    AppManager.availablePane.setDisable(false);
                                });
                            }
                        } else if (data.get(0).equals("Do you accept this challenge")) {
                            System.out.println(data.get(1) + ", do u accept this challenge from : " + data.get(2));
                            playerName = data.get(1);
                            opponentName = data.get(2);
                            Platform.runLater(() -> {
                                alert.setTitle("Confirmation Dialog");
                                alert.setHeaderText("do u accept this challenge from : " + data.get(2));
                                alert.setContentText("Are you ok with this?\nNote:your opponent will play the first move with X");
                                Optional<ButtonType> result = alert.showAndWait();
                                    AppManager.availablePane.setDisable(true);
                                ArrayList<String> sentData = new ArrayList<String>();
                                if (result.get() == ButtonType.OK) {
                                    isMyTurn = false;
                                    amISender = false;
                                    gamePane.firstPlayerName.setText(playerName);
                                    gamePane.secondPlayerName.setText(opponentName);
                                    sentData.add("yes");
                                    sentData.add(opponentName);
                                    sentData.add(playerName);
                                    try {
                                        objectOutput.writeObject(sentData);
                                        objectOutput.flush();
                                    } catch (IOException ex) {
                                        Logger.getLogger(AppManager.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    accept = true;
                                    isOnline = true;
                                    Platform.runLater(() -> {
                                        availablePane.th1.stop();
                                        gamePane.startNewGame();
                                    });
                                    viewPane(gamePane);
                                    //gamePane.recieveingMoves();
                                    //stop();
                                } else {
                                    StartPane.audioGame.stop();
                                    StartPane.audio.play();
                                    AppManager.soundON();
                                    sentData.add("no");
                                    sentData.add(opponentName);
                                    sentData.add(playerName);
                                    
                                    try {
                                        objectOutput.writeObject(sentData);
                                        objectOutput.flush();
                                    } catch (IOException ex) {
                                        Logger.getLogger(AppManager.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    AppManager.availablePane.setDisable(false);
                                }
                            });
                        } else if (data.get(0).equals("Do you want to play again?")) {
                            ArrayList<String> sendData = new ArrayList<String>();
                                    System.out.println(data.get(1) + ", Do you want to play again? : " + data.get(2));
                                    playerName = data.get(1);
                                    opponentName = data.get(2);
                                    Platform.runLater(() -> {
                                        alert3.setTitle("Confirmation Dialog");
                                        alert3.setHeaderText("Do you want to play again with : " + data.get(2));
                                        alert3.setContentText("Are you ok with this?\nNote:your opponent will play the first move with X");
                                        Optional<ButtonType> result2 = alert3.showAndWait();
                                        System.out.println("متبعطش ليه؟");
                                        if (result2.get() == ButtonType.OK) {
                                            isMyTurn = false;
                                            amISender = false;
                                            gamePane.firstPlayerName.setText(playerName);
                                            gamePane.secondPlayerName.setText(opponentName);
                                            sendData.add("yes");
                                            sendData.add(opponentName);
                                            sendData.add(playerName);
                                            try {
                                                objectOutput.writeObject(sendData);
                                                objectOutput.flush();
                                            } catch (IOException ex) {
                                                Logger.getLogger(AppManager.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                            isOnline = true;
                                            Platform.runLater(() -> {
                                                gamePane.startNewGame();
                                            });
                                            //gamePane.recieveingMoves();
                                            //stop();
                                        } else {
                                            StartPane.audioGame.stop();
                                            StartPane.audio.play();
                                            AppManager.soundON();
                                            sendData.add("end entirly");
                                            sendData.add((gamePane.firstPlayerScore.getText() + "-" + gamePane.secondPlayerScore.getText()));
                                            sendData.add(AppManager.playerName);
                                            sendData.add(AppManager.opponentName);
                                            AppManager.availablePane.setDisable(false);
                                            AppManager.viewPane(AppManager.availablePane);

                                            try {
                                                objectOutput.writeObject(sendData);
                                                objectOutput.flush();
                                            } catch (IOException ex) {
                                                Logger.getLogger(AppManager.class.getName()).log(Level.SEVERE, null, ex);
                                            }

                                        }
                                    });
                                   
                                } 
                        else if (data.get(0).equals("PLAYERS")) {   //if user click on refresh
                            arr = new ArrayList<String>();
                            data.remove(0);
                            for(String s: data){
                             if(!s.equals(playerName)){
                                 arr.add(s);
                             }   
                            }
                            //for loop to not display the player name
                            System.out.println(arr);
                             Platform.runLater(() -> {
                            AvailablePlayers.player_list.getItems().clear();
                            AvailablePlayers.player_list.getItems().addAll(arr);
                            });
                            
                        } //*****************
                        else if (data.get(0).matches("^\\d{1},\\d{1}$")) {         //ending the game and recording the results  
                            System.out.println("MOVE   " + data.get(0));
                            Platform.runLater(() -> {
                                gamePane.placeMove(data.get(0));
                            });
                            AppManager.isMyTurn = true;
                            System.out.println("else if of the agrrement");
                        } else if (data.get(0).equals("login")) {
                            if (data.get(1).equals("you loged in")) {
                                
                                 Platform.runLater(() -> {
                                //AppManager.availablePane.getAvailableThread();
                                AvailablePlayers.th1=new Thread(availablePane);
                                AvailablePlayers.th1.start();
                                AppManager.playerName = loginPane.UserNameInput.getText();
                                     System.out.println("userName"+playerName);
                                AppManager.availablePane.player_username.setText(loginPane.UserNameInput.getText());
                                AppManager.availablePane.player_name.setText(data.get(2)+" "+data.get(3));
                                Gender=data.get(4);
                                     if (AppManager.Gender.equals("Female")) {
                                         availablePane.avatar.setImage(new Image(getClass().getResource("/images/AvatarF.png").toExternalForm()));
                                     } else if (AppManager.Gender.equals("Male")) {
                                         availablePane.avatar.setImage(new Image(getClass().getResource("/images/PLayer.png").toExternalForm()));
                                     } else {
                                         availablePane.avatar.setImage(new Image(getClass().getResource("/images/OIP.jpg").toExternalForm()));
                                     }
                                AppManager.viewPane(AppManager.availablePane);
                                AppManager.loginPane.LogenClear();
                                });
                            } else {
                                Platform.runLater(() -> {
                                    alert2.setTitle("Login");
                                    alert2.setHeaderText("Login Failure");
                                    alert2.setContentText(data.get(1));
                                    alert2.showAndWait();
                                });
                            }

                        }else if (data.get(0).equals("register")) {
                            if (data.get(1).equals("you are registerd")) {
                                 Platform.runLater(() -> {
                                //AppManager.availablePane.th1.start();
                                //AppManager.playerName = registerPane.userName.getText();
                                //System.out.println("userName"+playerName);
                                //AppManager.availablePane.player_username.setText(playerName);
                                //AppManager.availablePane.player_name.setText(data.get(2)+" "+data.get(3));
                                //AppManager.viewPane(AppManager.availablePane);
                                    sendData = new ArrayList<String>();
                                    sendData.add("logOut");
                                    try {
                                        AppManager.objectOutput.writeObject(sendData);
                                        AppManager.objectOutput.flush();

                                    } catch (IOException ex) {
                                        Logger.getLogger(AppManager.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    AppManager.isOnline = false;
                                    AppManager.viewPane(AppManager.loginPane);
                                    AppManager.registerPane.RegisterClear();
                                });
                            } else {
                                Platform.runLater(() -> {
                                alert2.setTitle("Register");
                                alert2.setHeaderText("Register Failure");
                                alert2.setContentText(data.get(1));
                                alert2.showAndWait();
                                });
                            }

                        }else if (data.get(0).equals("AllScores")) {
                            Scores = new ArrayList<Score>();
                             Platform.runLater(() -> {
                            String[] recordScore;
                            Score scoresInfo;
                            for (int i = 1; i < data.size(); i++) {
                                recordScore = (data.get(i)).split("-");
                                 scoresInfo = new Score(recordScore[1],recordScore[2],recordScore[3]);
                                Scores.add(scoresInfo);
                            }
                            AppManager.scorePane.scoreTable.getItems().clear();
                            AppManager.scorePane.setScores();
                            System.out.println("Scores"+Scores);
                             });
                        }
                        else if (data.get(0).equals("end entirly")) {
                          
                            Platform.runLater(() -> {
                                try {
                                    sendData = new ArrayList<String>();
                                    sendData.add("Scores");
                                    sendData.add((gamePane.firstPlayerScore.getText() + "-" + gamePane.secondPlayerScore.getText()));
                                    sendData.add(AppManager.playerName);
                                    sendData.add(AppManager.opponentName);
                                    System.out.println(sendData);
                                    AppManager.objectOutput.writeObject(sendData);
                                    AppManager.objectOutput.flush();
                                    System.out.println(sendData);
                                    
                                } catch (IOException ex) {
                                    Logger.getLogger(AppManager.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                alert2.setTitle("Infirmation Dialog");
                                alert2.setContentText("Sorry, ur opponent ended the game");
                                alert2.showAndWait();
                                gamePane.firstPlayerScore.setText("0");
                                gamePane.secondPlayerScore.setText("0");
                                AppManager.availablePane.setDisable(false);
                                viewPane(AppManager.availablePane);
                                gamePane.startNewGame();
                                //Platform.runLater(() -> {
                                AvailablePlayers.th1=new Thread(availablePane);
                                AvailablePlayers.th1.start();
                               // }); 
                            });

                            
                        }

                        //********
                    }catch(EOFException ex){
                        
                         Platform.runLater(() -> {
                                alert2.setTitle("Infirmation Dialog");
                                alert2.setContentText("YOU LOGED OUT");
                                alert2.showAndWait();
                            });
                            viewPane(AppManager.onlinePane);
                            stop();
                        
                    }
                    catch (SocketException s) {
                        try {
                            objectInput.close();
                            objectOutput.close();
                            mySocket.close();
                            System.out.println("finishhhhhh");
                            
                            Platform.runLater(() -> {
                                alert2.setTitle("Infirmation Dialog");
                                alert2.setContentText("YOU CLOSED THE CONNECTION \nOR \n server closed :( ");
                                alert2.showAndWait();
                            });
                            viewPane(AppManager.onlinePane);
                            stop();
                        } catch (IOException ex) {
                            Logger.getLogger(AppManager.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (IOException ex) {
                        
                        Logger.getLogger(AppManager.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        
                        Logger.getLogger(AppManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        }.start();
    }


   
    public static void soundoff() {
        AppManager.gamePane.sound.setVisible(false);
        AppManager.startPane.sound_btn.setVisible(false);
        AppManager.singlePlayerPane.sound_btn.setVisible(false);
        AppManager.multiPlayerPane.sound_btn.setVisible(false);
        AppManager.scorePane.sound.setVisible(false);
        AppManager.onlinePane.soundbtn.setVisible(false);
        AppManager.recordsPane.sound.setVisible(false);
        AppManager.loginPane.sound_btn.setVisible(false);
        AppManager.registerPane.soundbtn.setVisible(false);
        AppManager.availablePane.sound.setVisible(false);
        AppManager.gameLevels.sound.setVisible(false);

        AppManager.gameLevels.soundOff.setVisible(true);
        AppManager.gamePane.soundoff_btn.setVisible(true);
        AppManager.startPane.soundoff_btn.setVisible(true);
        AppManager.singlePlayerPane.soundoff_btn.setVisible(true);
        AppManager.multiPlayerPane.soundoff_btn.setVisible(true);
        AppManager.scorePane.soundoff_btn.setVisible(true);
        AppManager.onlinePane.soundoff_btn.setVisible(true);
        AppManager.recordsPane.soundOff.setVisible(true);
        AppManager.loginPane.soundoff_btn.setVisible(true);
        AppManager.registerPane.soundoffbtn.setVisible(true);
        AppManager.availablePane.soundoff_btn.setVisible(true);

    }

    public static void soundON() {
        AppManager.gamePane.sound.setVisible(true);
        AppManager.startPane.sound_btn.setVisible(true);
        AppManager.singlePlayerPane.sound_btn.setVisible(true);
        AppManager.multiPlayerPane.sound_btn.setVisible(true);
        AppManager.scorePane.sound.setVisible(true);
        AppManager.onlinePane.soundbtn.setVisible(true);
        AppManager.recordsPane.sound.setVisible(true);
        AppManager.loginPane.sound_btn.setVisible(true);
        AppManager.registerPane.soundbtn.setVisible(true);
        AppManager.availablePane.sound.setVisible(true);
        AppManager.gameLevels.sound.setVisible(true);

        AppManager.gameLevels.soundOff.setVisible(false);
        AppManager.gamePane.soundoff_btn.setVisible(false);
        AppManager.startPane.soundoff_btn.setVisible(false);
        AppManager.singlePlayerPane.soundoff_btn.setVisible(false);
        AppManager.multiPlayerPane.soundoff_btn.setVisible(false);
        AppManager.scorePane.soundoff_btn.setVisible(false);
        AppManager.onlinePane.soundoff_btn.setVisible(false);
        AppManager.recordsPane.soundOff.setVisible(false);
        AppManager.loginPane.soundoff_btn.setVisible(false);
        AppManager.registerPane.soundoffbtn.setVisible(false);
        AppManager.availablePane.soundoff_btn.setVisible(false);

    }


}
