package xo;

//import com.sun.xml.internal.ws.api.ha.StickyFeature;
import information.Info;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.Pane;

public class Connection {                       // *******OLD FILE*******
    
    Info info;
    Socket s;
    //DataInputStream dis;
    //PrintStream ps;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    String msg;
    Connection(){ 
        
        connectToServer();
        System.out.println("afterConnect");
        
    
    }
    
    public void sendData(){
       new Thread() {
            public void run() {
                try {
                    ois = new ObjectInputStream(s.getInputStream());
                    msg = (String) ois.readObject();
                    System.out.println(msg);
                } catch (IOException ex) {
                    Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
        
    }
    
    public void connectToServer(){
        new Thread(){
            public void run(){
                while(true){
                    try {
                        s = new Socket("127.0.0.1", 7007);
                        /*dis = new DataInputStream(s.getInputStream());
                        ps = new PrintStream(s.getOutputStream());*/
                        oos = new ObjectOutputStream(s.getOutputStream());
                        
                        //sleep(2500);
                        //connectionChecker();
                        //System.out.println("afterCheck");
                        
                        info = new Info();
                        info.name="ahm";
                        info.password="123456777";
                        info.flage=true;
                        oos.writeObject(info);
                        oos.flush();
                        /*if(info.flage==false){
                            oos.writeObject(info);
                            oos.flush();
                            System.out.println("Sent student1");
                        }
                        else{
                            oos.writeObject(info);
                            oos.flush();
                            System.out.println("Sent student2");       
                        }*/
                            break;
                        } catch (IOException ex) {
                            System.out.println("Connetion Fail");
                        //} catch (InterruptedException ex) {
                        //  Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                        } 
                }
                //unfreezing buttons    (if we will freezing buttons waiting for connection success in online mode only)
            }
        }.start();   
    }
    
    /*
    public void connectionChecker(){
        new Thread(){
            public void run(){
                try {
                    System.out.println("checkkkk");
                    while(true){
                        System.out.println(s.getKeepAlive());
                        System.out.println("conn");
                    }
                    //System.out.println("aft Checkkk");
                } catch (SocketException ex) {
                    System.out.println("faoldd");
                    stop();
                    Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
    }*/
    /*public void connectionChecker(){
        new Thread(){
            public void run(){
                while(true){                     //iuygfdsasdfghjkl
                    if (s.isConnected())
                        System.out.println("isConnected true");
                    else{
                        System.out.println("isConnected false");
                        stop();
                        
                    }
                }               
                //System.out.println("STOPPED");
            }
        }.start();
    }*/
    
    
}

/*
        new Thread() {
            public void run() {
                try {
                    objectInput = new ObjectInputStream(mySocket.getInputStream());
                    msg = (String) objectInput.readObject();
                    System.out.println(msg);
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
*/