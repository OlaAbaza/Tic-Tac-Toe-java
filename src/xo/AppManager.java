package xo;

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
import javafx.scene.layout.Pane;
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
    static boolean accept = false;

    static boolean isMyTurn = true;
    static boolean amISender = false;
    static boolean isOnline = false;

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
            //Logger.getLogger(AppManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(AppManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /*public static void refresh() {    
    }*/
    public static void recieving() {
        new Thread() {
            ArrayList<String> data = new ArrayList<String>();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
            Alert alert4 = new Alert(Alert.AlertType.INFORMATION);
            Alert alert5 = new Alert(Alert.AlertType.INFORMATION);
            ArrayList<String> sendData;

            @Override
            public void run() {
                System.out.println("11111");
                while (terminatorRequest) {
                    try {
                        data = (ArrayList<String>) objectInput.readObject();
                        System.out.println("RUN  " + data);
                        if (data.get(0).equals("yes") || data.get(0).equals("no")) { //sending and reciving the position in the game    
                            if (data.get(0).equals("yes")) {
                                amISender = true;
                                playerName = data.get(1);
                                opponentName = data.get(2);
                                Platform.runLater(() -> {
                                    gamePane.firstPlayerName.setText(playerName);
                                    gamePane.secondPlayerName.setText(opponentName);
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
                                });
                            }
                        } else if (data.get(0).equals("Do you accept this challenge")) {
                            System.out.println(data.get(1) + ", do u accept this challenge from : " + data.get(2));
                            playerName = data.get(1);
                            opponentName = data.get(2);
                            Platform.runLater(() -> {
                                alert.setTitle("Confirmation Dialog");
                                alert.setHeaderText("do u accept this challenge from : " + data.get(2));
                                alert.setContentText("Are you ok with this?");
                                Optional<ButtonType> result = alert.showAndWait();
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
                                        gamePane.startNewGame();
                                    });
                                    viewPane(gamePane);
                                    //gamePane.recieveingMoves();
                                    //stop();
                                } else {
                                    sentData.add("no");
                                    sentData.add(opponentName);
                                    sentData.add(playerName);
                                    try {
                                        objectOutput.writeObject(sentData);
                                        objectOutput.flush();
                                    } catch (IOException ex) {
                                        Logger.getLogger(AppManager.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                }
                            });
                        } else if (data.get(0).equals("PLAYERS")) {   //if user click on refresh
                            arr = new ArrayList<String>();
                            arr = data;
                            //for loop to not display the player name
                            System.out.println(arr);

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
                                AppManager.playerName = loginPane.UserNameInput.getText();
                                AppManager.viewPane(AppManager.availablePane);
                            } else {
                                Platform.runLater(() -> {
                                    alert5.setTitle("Login");
                                    alert5.setHeaderText("Login Failure");
                                    alert5.setContentText(data.get(1));
                                    alert5.showAndWait();
                                });
                            }

                        }else if (data.get(0).equals("register")) {
                            if (data.get(1).equals("you are registerd")) {
                                AppManager.playerName = loginPane.UserNameInput.getText();
                                AppManager.viewPane(AppManager.availablePane);
                            } else {
                                Platform.runLater(() -> {
                                alert5.setTitle("Register");
                                alert5.setHeaderText("Register Failure");
                                alert5.setContentText(data.get(1));
                                alert5.showAndWait();
                                });
                            }

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
                                alert3.setTitle("Infirmation Dialog");
                                alert3.setContentText("Sorry, ur opponent ended the game");
                                alert3.showAndWait();
                                viewPane(AppManager.availablePane);
                                gamePane.startNewGame();
                            });

                            
                        }

                        //********
                    } catch (SocketException s) {
                        try {
                            objectInput.close();
                            objectOutput.close();
                            mySocket.close();
                            System.out.println("finishhhhhh");
                            
                            Platform.runLater(() -> {
                                alert4.setTitle("Infirmation Dialog");
                                alert4.setContentText("YOU CLOSED THE CONNECTION \nOR \n server closed :( ");
                                alert4.showAndWait();
                            });
                            viewPane(AppManager.onlinePane);
                            stop();
                        } catch (IOException ex) {
                            Logger.getLogger(AppManager.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (IOException ex) {
                        System.out.println("يا رب تتحل1");
                        Logger.getLogger(AppManager.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        System.out.println("يا رب تتحل");
                        Logger.getLogger(AppManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        }.start();
    }


    //*****DLT*****
    // recordsPane الدالة التالية نستخدمها لوضع الخيارات الإفتراضية التي يمكن تغييرها في الحاوية
    public static void setDefaultSettings() {
        // fontSizesComboBox و ثاني خيار في الكائن boardsComboBox هنا قلنا أنه سيتم إختيار أول خيار في الكائن
        recordsPane.comboBox.getSelectionModel().selectFirst();
   
    }


}
