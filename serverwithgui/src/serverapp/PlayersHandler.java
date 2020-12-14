/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverapp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import org.apache.derby.jdbc.ClientDriver;


/**
 *
 * @author tasne
 */
public class PlayersHandler implements Runnable {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private ObjectOutputStream objectOutput;
    private ObjectInputStream objectInput;
    private int status;
    private ArrayList<String> info;
    Socket clientSocket;
    ArrayList<String> msg;
    //Socket availableSocket;
    //private ObjectOutputStream objectOutput2;
    //private ObjectInputStream objectInput2;
    String name;
    static boolean termenator;
    public static Thread th;

    static Set<PlayersHandler> playersSocket = new HashSet<PlayersHandler>();   //Try Hash map

    public PlayersHandler(Socket clientSocket) {

        //msg = "";
        this.clientSocket = clientSocket;
        //this.availableSocket = socket;
        info = new ArrayList<>();
        name = "";
        msg = new ArrayList<String>();

        //estublising the connection to the databse
        try {
            DriverManager.registerDriver(new ClientDriver());
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/PlayersDB", "root", "root");
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String queryString = new String("select * from profile");
            resultSet = statement.executeQuery(queryString);
        } catch (SQLException ex) {
            Logger.getLogger(PlayersHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            objectInput = new ObjectInputStream(clientSocket.getInputStream());
            objectOutput = new ObjectOutputStream(clientSocket.getOutputStream());
            playersSocket.add(this);
            th= new Thread(this);
            th.start();

        } catch (IOException ex) {
            Logger.getLogger(PlayersHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        /*try {
            objectOutput2 = new ObjectOutputStream(availableSocket.getOutputStream());
            objectInput2 = new ObjectInputStream(availableSocket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(PlayersHandler.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }

    //reading objects from the players 
    @Override
    public void run() {

        while (true) {

         try {
                try {
                    info = (ArrayList<String>) objectInput.readObject();

                    //determine where the info had come from.. whither registration or logen
                    if (info.get(0).equals("false") || info.get(0).equals("true")) {
                        System.out.println(info.get(0) + "  " + info.get(1) + "   " + info.get(2));
                        if (info.get(0).equals("false")) {
                            registration();
                        } else {
                            login();
                            System.out.println("after login");
                        }
                        name = info.get(1);
                        sendMessageToPlayer(msg);
                        //sending the available players
                    } else if (info.get(0).equals("refresh")) {

                        ArrayList<String> availablePlayers = new ArrayList<String>();
                        availablePlayers.add("PLAYERS");
                        String queryString = new String("select * from profile");   //resSet.first()
                        resultSet = statement.executeQuery(queryString);            //resSet.prev()
                        System.out.println("DBUPDATED");
                        while (resultSet.next()) {

                            if (resultSet.getInt(3) == 1) {
                                availablePlayers.add(resultSet.getString(1));
                            }
                        }
                        System.out.println("aP " + availablePlayers);
                        objectOutput.writeObject(availablePlayers);
                        objectOutput.flush();
                        //answering the challenge with yes or no    
                    } else if (info.get(0).equals("yes") || info.get(0).equals("no")) {

                        sendMessageToOpponentPlayer(info.get(1), info.get(0), name);
                        //sending and reciving the position in the game    
                    } else if (info.get(0).matches("^\\d{1},\\d{1}$")) {
                        System.out.println("else if of the agrrement");
                        sendMessageToOpponentPlayer(info.get(1), info.get(0), name);
                        //ending the game and recording the results  
                    } else if (info.get(0).equals("end entirly")) {
                        System.out.println("end entirly "+info);
                        insertScores(info);
                        sendMessageToOpponentPlayer(info.get(2), info.get(0), name);
                        PreparedStatement pst;
                        pst = connection.prepareStatement("UPDATE profile SET status=? WHERE username=? or username=?");
                        status = 1;
                        pst.setString(3, info.get(2));
                        pst.setString(2, name);
                        pst.setInt(1, status);
                        pst.executeUpdate();
                        //playing again with the same apponent
                    } else if (info.get(0).equals("again")) {
                        System.out.println(info.get(0));
                        sendMessageToOpponentPlayer(info.get(1), info.get(0), name);
                        //Switching to another User
                    } else if (info.get(0).equals("switchUser")) {
                        System.out.println(info.get(0));
                        PreparedStatement pst;
                        pst = connection.prepareStatement("UPDATE profile SET status=? WHERE username=?");
                        status = 0;
                        pst.setString(2, name);
                        pst.setInt(1, status);
                        pst.executeUpdate();
                        //receiving the opponent name from the player
                    } else {
                        String opponentName = info.get(0);//tas
                        String playerName = name;//ola
                        System.out.println(opponentName + "    " + playerName);
                        String challenge = "Do you accept this challenge";
                        sendMessageToOpponentPlayer(opponentName, challenge, name);
                    }

                    //closing the connection of the player who loged out and updating te database
                } catch (SocketException s) {
                    try {
                        PlayersHandler.this.objectInput.close();
                        PlayersHandler.this.objectOutput.close();
                        PreparedStatement pst;
                        pst = connection.prepareStatement("UPDATE profile SET status=? WHERE username=?");
                        status = 0;
                        pst.setString(2, name);
                        pst.setInt(1, status);
                        pst.executeUpdate();
                        playersSocket.remove(PlayersHandler.this);
                    } catch (SQLException ex) {
                        Logger.getLogger(PlayersHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(PlayersHandler.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (IOException ex) {
                Logger.getLogger(PlayersHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(PlayersHandler.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void insertScores(ArrayList<String> scores) {
        PreparedStatement pst;
        try {
            pst = connection.prepareStatement("INSERT INTO scores (player, opponent, score) VALUES ( ?,?,?)");
            pst.setString(1, scores.get(2));
            pst.setString(2, scores.get(3));
            pst.setString(3, scores.get(1));
            pst.executeUpdate();
            String queryString = new String("select * from profile");
            resultSet = statement.executeQuery(queryString);
        } catch (SQLException ex) {
            Logger.getLogger(PlayersHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void registration() {
        PreparedStatement pst;
        //msg = "";
        msg = new ArrayList<String>();

        boolean userNameUsed = false;
        try {

            while (resultSet.next()) {

                if (info.get(1).equals(resultSet.getString(1))) {
                    userNameUsed = true;
                    msg.add("that userName is used");
                    break;
                }
            }

            if (!userNameUsed) {
                pst = connection.prepareStatement("INSERT INTO profile VALUES ( ?,?,?)");
                status = 1;
                pst.setString(1, info.get(1));
                pst.setString(2, info.get(2));
                pst.setInt(3, status);
                pst.executeUpdate();
                String queryString = new String("select * from profile");
                resultSet = statement.executeQuery(queryString);
                msg.add("you are registerd");
            }

        } catch (SQLException ex) {
            Logger.getLogger(PlayersHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void login() {
        PreparedStatement pst;
        System.out.println("enter login");
        msg = new ArrayList<String>();
        //ArrayList<String> msg=new  ArrayList<String>();
        try {
            boolean isloged = false;
            while (resultSet.next()) {
                if (info.get(1).equals(resultSet.getString(1)) && info.get(2).equals(resultSet.getString(2))) {
                    pst = connection.prepareStatement("UPDATE profile SET status=? WHERE username=?");
                    status = 1;
                    pst.setString(2, info.get(1));
                    pst.setInt(1, status);
                    pst.executeUpdate();
                    msg.add("you loged in");
                    //playersSocket.add(this);
                    isloged = true;
                    break;
                }
            }
            if (!isloged) {
                msg.add("your userName or password is wrong");
            }

            System.out.println("after While");
            String queryString = new String("select * from profile");
            resultSet = statement.executeQuery(queryString);
            System.out.println("after Query");

        } catch (SQLException ex) {
            Logger.getLogger(PlayersHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendMessageToPlayer(ArrayList<String> msg) {

        System.out.println("in the method");

        try {
            for (PlayersHandler playersHandler : playersSocket) {
                System.out.println("player");
                if (name.equals(playersHandler.name)) {
                    playersHandler.objectOutput.writeObject(msg);
                    break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(PlayersHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void sendMessageToOpponentPlayer(String opponentName, String str, String playerName) {

        System.out.println("in the method");
        System.out.println(opponentName);
        ArrayList<String> data = new ArrayList<String>();
        data.add(str);
        data.add(opponentName);    // Y?
        data.add(playerName);

        try {
            for (PlayersHandler playersHandler : playersSocket) {
                System.out.println(playersHandler.name);
                if (opponentName.equals(playersHandler.name)) {
                    System.out.println("java");
                    playersHandler.objectOutput.writeObject(data);
                    playersHandler.objectOutput.flush();
                    break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(PlayersHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public static void terminate(){
        th.stop();
    }

}
