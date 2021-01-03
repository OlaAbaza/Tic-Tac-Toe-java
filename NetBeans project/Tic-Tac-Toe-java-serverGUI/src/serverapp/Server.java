/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverapp;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author tasne
 */
public class Server implements Runnable{

    ServerSocket serverSocket;
    static boolean termenator2;
    public static Thread th;
    private PlayersHandler playersHandler;
    

    public Server() {
        

       th= new Thread(this);
       th.start();
       
       

    }
    
    @Override
            public void run() {

                try {
                    serverSocket = new ServerSocket(5005);
                    while (true) {
     
                        Socket socket = serverSocket.accept();
                        
                        PlayersHandler playersHandler=new PlayersHandler(socket);
                        
                    }
                    
                }
                catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

}
