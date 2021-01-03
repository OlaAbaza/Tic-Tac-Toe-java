package xo;


import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GamePane extends Pane {

    protected final AnchorPane anchorPane;
    protected final ImageView bg;
    protected final ImageView playeriImg;
    protected final Label secondPlayerScore;
    protected final Label firstPlayerScore;
    protected final Label label;
    protected final ImageView comImg;
    protected final ImageView boardBackground;
    protected final ImageView newGame;
    protected final ImageView back;
    protected final ImageView sound;
    protected final Label secondPlayerName;
    protected final Label firstPlayerName;
    protected final Button button;
    protected final Label currentPlayerSymbol;
    protected final ImageView symbolX;
    protected final ImageView symbolO;
    protected final ImageView play_btn;
    protected final ImageView soundoff_btn;

    GridPane boardPane = new GridPane();
    Button[] boardButtons = new Button[3 * 3];
    String firstMove; //for saving in file 
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    DialogPane dialogPane = alert.getDialogPane();
    static String pathh = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "XO";
    MinimaxAlgorithm minimax = new MinimaxAlgorithm();
   int row = 0, col = 0,bestMove = -1;

    Timer tm = new Timer();
    Queue<String> q = new LinkedList<>(); //for saving indexes

    File f = new File(pathh);
    int fileNum = 1;  //counter for files,  used for naming new files
    String child;
    String indexesWinner = "";
    FileOutputStream fos;

    boolean isGameEnds;
    boolean isTie;
    boolean isFirstPlayerTurn = true;

    int XOCounter = 0;

    Random random = new Random();
    int randomNumber;

    ArrayList<String> moves;
    ArrayList<String> sendData;

    Color xForeground = Color.WHITE;
    Color oForeground = Color.YELLOW;
    Font fontXO =  Font.loadFont(getClass().getResource("/fonts/Orangina-Demo.otf").toExternalForm(), 40.0);
  
    
    
    /////////////////////////////////////////////////////////////////////

    EventHandler<ActionEvent> eventHandler = (ActionEvent e) -> {
        actionPerformed(e);
    };

    private void colorBackgroundWinnerButtons(Button b1, Button b2, Button b3) {
        b1.setStyle("-fx-background-color: #9fbfdf;");
        b2.setStyle("-fx-background-color: #9fbfdf;");
        b3.setStyle("-fx-background-color:  #9fbfdf;");
    }

    private void createGameBoard() {                ///  Do NOT call if isRec is TRUE

        int row = 0;
        int column = 0;

        for (int i = 0; i < boardButtons.length; i++) {
            boardButtons[i] = new Button();
            boardButtons[i].setPrefSize(90, 90);
            boardButtons[i].setFocusTraversable(false);
            GridPane.setMargin(boardButtons[i], new Insets(5));
            boardButtons[i].setFont(Font.font("Arial", FontWeight.BOLD, 40));
            boardPane.add(boardButtons[i], column, row);
            boardButtons[i].addEventHandler(ActionEvent.ACTION, e -> {
                actionPerformed(e);
            });

            column++;
            if (column == 3) {
                row++;
                column = 0;
            }
        }

    }

    private void checkIfGameEnds() {

        String t00 = boardButtons[0].getText();
        String t01 = boardButtons[1].getText();
        String t02 = boardButtons[2].getText();
        String t10 = boardButtons[3].getText();
        String t11 = boardButtons[4].getText();
        String t12 = boardButtons[5].getText();
        String t20 = boardButtons[6].getText();
        String t21 = boardButtons[7].getText();
        String t22 = boardButtons[8].getText();

        if (t00.equals(t01) && t00.equals(t02) && !t00.equals("")) {
            isGameEnds = true;
            colorBackgroundWinnerButtons(boardButtons[0], boardButtons[1], boardButtons[2]);
            indexesWinner = "012";
            chooseVideo(t00);
        }

        if (t10.equals(t11) && t10.equals(t12) && !t10.equals("")) {
            isGameEnds = true;
            colorBackgroundWinnerButtons(boardButtons[3], boardButtons[4], boardButtons[5]);
            indexesWinner = "345";
             chooseVideo(t10);
        }

        if (t20.equals(t21) && t20.equals(t22) && !t20.equals("")) {
            isGameEnds = true;
            colorBackgroundWinnerButtons(boardButtons[6], boardButtons[7], boardButtons[8]);
            indexesWinner = "678";
             chooseVideo(t20);
        }

        if (t00.equals(t10) && t00.equals(t20) && !t00.equals("")) {
            isGameEnds = true;
            colorBackgroundWinnerButtons(boardButtons[0], boardButtons[3], boardButtons[6]);
            indexesWinner = "036";
             chooseVideo(t00);
        }

        if (t01.equals(t11) && t01.equals(t21) && !t01.equals("")) {
            isGameEnds = true;
            colorBackgroundWinnerButtons(boardButtons[1], boardButtons[4], boardButtons[7]);
            indexesWinner = "147";
             chooseVideo(t01);
        }

        if (t02.equals(t12) && t02.equals(t22) && !t02.equals("")) {
            isGameEnds = true;
            colorBackgroundWinnerButtons(boardButtons[2], boardButtons[5], boardButtons[8]);
            indexesWinner = "258";
            chooseVideo(t02);
        }

        if (t00.equals(t11) && t00.equals(t22) && !t00.equals("")) {
            isGameEnds = true;
            colorBackgroundWinnerButtons(boardButtons[0], boardButtons[4], boardButtons[8]);
            indexesWinner = "048";
             chooseVideo(t00);
        }

        if (t02.equals(t11) && t02.equals(t20) && !t02.equals("")) {
            isGameEnds = true;
            colorBackgroundWinnerButtons(boardButtons[2], boardButtons[4], boardButtons[6]);
            indexesWinner = "246";
             chooseVideo(t02);
        }
        
        tieChecker();

        if (XOCounter >= 9) {
            isGameEnds = true;
            isFirstPlayerTurn = true;
            XOCounter = 0;
           
        }

        if (isGameEnds == true && AppManager.isOnline == false) {
            if (isTie == false) {
                if (isFirstPlayerTurn) {
                    firstPlayerScore.setText(Integer.valueOf(firstPlayerScore.getText()) + 1 + "");
                } else {
                    secondPlayerScore.setText(Integer.valueOf(secondPlayerScore.getText()) + 1 + "");
                }
            }
            XOCounter = 0;
            newGame.requestFocus();

            if (AppManager.rec) {
                saveInFile();
            }

            for (String s : q) {
                System.out.println(s + "**");
            }
        }
        if (isGameEnds == true && AppManager.isOnline == true) {
            if (isTie == false) {
                if (!AppManager.isMyTurn) {
                    firstPlayerScore.setText(Integer.valueOf(firstPlayerScore.getText()) + 1 + "");
                } else {
                    secondPlayerScore.setText(Integer.valueOf(secondPlayerScore.getText()) + 1 + "");
                }
            }
            XOCounter = 0;
            newGame.requestFocus();

            if (AppManager.rec) {
                saveInFile();
            }

            for (String s : q) {
                System.out.println(s + "**");
            }
        }

    }

    private void tieChecker() {
        if (XOCounter >= 9 && isGameEnds == false) {
            isTie = true;
            alert.setTitle("Game");
            alert.setContentText("game ended with a tie");
            alert.showAndWait();
        }
    }

    
    private void saveInFile() {

        if (!f.exists()) {
            f.mkdirs();    //f.mkdirs();
        }

        System.out.println("pathhh -> " + pathh);
        System.out.println("getPath() -> " + f.getPath());
        fileNum = f.list().length + 1;    //to get number of saved files in folder
        child = f.getPath() + File.separator + firstPlayerName.getText() + "vs" + secondPlayerName.getText() + " - " + Integer.toString(fileNum);
        System.out.println(child);
        try {
            fos = new FileOutputStream(child);
            fileNum++;
            DataOutputStream dos = new DataOutputStream(fos);
            System.out.println(firstPlayerName.getText() + " fPN");
            dos.writeUTF(firstPlayerName.getText() + "#" + secondPlayerName.getText() + "#" + firstPlayerScore.getText() + "#" + secondPlayerScore.getText() + "#");
            for (String s : q) {
                System.out.println("SvInFl  " + s);
                dos.writeUTF(s.toString() + "#");
            }
            dos.writeUTF(indexesWinner + "#");
            indexesWinner = new String();
            dos.close();
            fos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GamePane.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GamePane.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void startNewGame() {
       isTie = false;
        XOCounter = 0;
        q = new LinkedList<>();
        System.out.println("startNewGameWelcome");
        isGameEnds = false;
        setCurrentPlayerSymbol();
        for (Button boardButton : boardButtons) {              //*******DLT*******
            boardButton.setText("");
            boardButton.setStyle("-fx-background-color: none; -fx-cursor: hand;");
        }
    }

    private void setCurrentPlayerSymbol() {
        if (isFirstPlayerTurn == true) {
            currentPlayerSymbol.setText("X");
            currentPlayerSymbol.setTextFill(xForeground);
        } else {
            currentPlayerSymbol.setText("O");
            currentPlayerSymbol.setTextFill(oForeground);

        }

    }

    private void actionPerformed(ActionEvent e) {
        Button clickedButton = (Button) e.getSource();  //Returns: The object on which the Event initially occurred.
        boolean addToQueueXO = true;
        moves = new ArrayList<String>();

        if (isGameEnds == false && clickedButton.getText().equals("") && !AppManager.isRec && AppManager.isMyTurn) {
            if (AppManager.challengeComputer == false && AppManager.isOnline == false) {
                XOCounter++;
                if (isFirstPlayerTurn) {
                    clickedButton.setTextFill(xForeground);
                    clickedButton.setText("X");
                    clickedButton.setFont(fontXO);
                    if (AppManager.rec) {    //add steps to Queue
                        q.add((GridPane.getRowIndex(clickedButton)).toString() + "," + (GridPane.getColumnIndex(clickedButton)).toString());
                        System.out.println("add into queue q");
                    }
                } else {
                    clickedButton.setTextFill(oForeground);
                    clickedButton.setText("O");
                    clickedButton.setFont(fontXO);
                    if (AppManager.rec) {      //add steps to Queue
                        q.add((GridPane.getRowIndex(clickedButton)).toString() + "," + (GridPane.getColumnIndex(clickedButton)).toString());
                        System.out.println("add into queue q");
                    }
                }
                checkIfGameEnds();
                //setCurrentPlayerSymbol();
                isFirstPlayerTurn = !isFirstPlayerTurn;
                setCurrentPlayerSymbol();
            }

            if (AppManager.challengeComputer == true && AppManager.isOnline == false) {
                XOCounter++;
                isFirstPlayerTurn = true;
                clickedButton.setTextFill(xForeground);
                clickedButton.setText("X");
                clickedButton.setFont(fontXO);
                if (AppManager.hardLevel == true) {
                    System.out.println("harddd q" + row + " " + col);
                    row = GridPane.getRowIndex(clickedButton);
                    col = GridPane.getColumnIndex(clickedButton);
                    minimax.fillBoard(row, col);
                }
                if (AppManager.rec) {      //add steps to Queue
                    q.add((GridPane.getRowIndex(clickedButton)).toString() + "," + (GridPane.getColumnIndex(clickedButton)).toString());
                    System.out.println("add into queue q");
                }

                checkIfGameEnds();

                if (isGameEnds == false) {
                    for (Button boardButton : boardButtons) {
                        boardButton.removeEventHandler(ActionEvent.ACTION, eventHandler);
                    }
                    if (AppManager.hardLevel == true) {
                        bestMove = minimax.bestMove();
                        System.out.println("best== " + bestMove);
                        boardButtons[bestMove].setTextFill(oForeground);
                        boardButtons[randomNumber].setFont(fontXO);
                        boardButtons[bestMove].setText("O");
                        randomNumber = bestMove;
                    } else if (AppManager.midLevel == true) {
                        System.out.println("midLevel q");
                        while (true) {
                            randomNumber = random.nextInt(9);
                            if (boardButtons[randomNumber].getText().equals("")) {
                                boardButtons[randomNumber].setTextFill(oForeground);
                                boardButtons[randomNumber].setFont(fontXO);
                                boardButtons[randomNumber].setText("O");
                                break;
                            }
                        }

                    }
                    if (AppManager.easyLevel == true) {
                        easyLevelMove();
                        System.out.println("easyLevel " + randomNumber);

                    }////easy level
                    addToRecQueue();
                    XOCounter++;
                    isFirstPlayerTurn = false;

                    checkIfGameEnds();

                    for (Button boardButton : boardButtons) {
                        boardButton.addEventHandler(ActionEvent.ACTION, eventHandler);
                    }
                }

                ///////////////////////////////////
            }
            if (AppManager.isOnline == true) {
                XOCounter++;
                if (AppManager.amISender) {
                    clickedButton.setTextFill(xForeground);
                    clickedButton.setText("X");
                    clickedButton.setFont(fontXO);
                    moves.add((GridPane.getRowIndex(clickedButton)).toString() + "," + (GridPane.getColumnIndex(clickedButton)).toString());
                    moves.add(AppManager.opponentName);
                    AppManager.isMyTurn = false;
                    if (AppManager.rec) //Online and record
                    {
                        q.add((GridPane.getRowIndex(clickedButton)).toString() + "," + (GridPane.getColumnIndex(clickedButton)).toString());
                    }
                    try {
                        AppManager.objectOutput.writeObject(moves);
                    } catch (IOException ex) {
                        Logger.getLogger(GamePane.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    clickedButton.setTextFill(oForeground);
                    clickedButton.setText("O");
                    clickedButton.setFont(fontXO);
                    moves.add((GridPane.getRowIndex(clickedButton)).toString() + "," + (GridPane.getColumnIndex(clickedButton)).toString());
                    moves.add(AppManager.opponentName);
                    AppManager.isMyTurn = false;
                    if (AppManager.rec) //Online and record
                    {
                        q.add((GridPane.getRowIndex(clickedButton)).toString() + "," + (GridPane.getColumnIndex(clickedButton)).toString());
                    }
                    try {
                        AppManager.objectOutput.writeObject(moves);
                    } catch (IOException ex) {
                        Logger.getLogger(GamePane.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                checkIfGameEnds();
            }

        }
    }

    private void addToRecQueue() {
        if (AppManager.rec) {
            q.add(toIndex(randomNumber));
            System.out.println("add into queue q");
        }
    }
    
    
    private String toIndex(int n) {
        switch (n) {
            case 0:
                return "0,0";
            case 1:
                return "0,1";
            case 2:
                return "0,2";
            case 3:
                return "1,0";
            case 4:
                return "1,1";
            case 5:
                return "1,2";
            case 6:
                return "2,0";
            case 7:
                return "2,1";
            case 8:
                return "2,2";
            default:
                return "";
        }
    }

    public void placeMove(String move) {
        System.out.println("n the method PlaceMove  " + move);
        Color clr = xForeground;
        String ch = "X";
        Font f=fontXO;

        if (!AppManager.amISender) {
            clr = xForeground;
            ch = "X";
        } else {
            clr = oForeground;
            ch = "O";
        }
        //Switch case Does NOT work
        if (move.equals("0,0")) {
            System.out.println("n the switch case0,0" + move);
            boardButtons[0].setTextFill(clr);
            boardButtons[0].setText(ch);
            boardButtons[0].setFont(f);
        } else if (move.equals("0,1")) {
            boardButtons[1].setTextFill(clr);
            boardButtons[1].setText(ch);
            boardButtons[1].setFont(f);
        } else if (move.equals("0,2")) {
            boardButtons[2].setTextFill(clr);
            boardButtons[2].setText(ch);
            boardButtons[2].setFont(f);
        } else if (move.equals("1,0")) {
            boardButtons[3].setTextFill(clr);
            boardButtons[3].setText(ch);
            boardButtons[3].setFont(f);
        } else if (move.equals("1,1")) {
            boardButtons[4].setTextFill(clr);
            boardButtons[4].setText(ch);
            boardButtons[4].setFont(f);
        } else if (move.equals("1,2")) {
            boardButtons[5].setTextFill(clr);
            boardButtons[5].setText(ch);
            boardButtons[5].setFont(f);
        } else if (move.equals("2,0")) {
            boardButtons[6].setTextFill(clr);
            boardButtons[6].setText(ch);
            boardButtons[6].setFont(f);
        } else if (move.equals("2,1")) {
            boardButtons[7].setTextFill(clr);
            boardButtons[7].setText(ch);
            boardButtons[7].setFont(f);
        } else if (move.equals("2,2")) {
            boardButtons[8].setTextFill(clr);
            boardButtons[8].setText(ch);
            boardButtons[8].setFont(f);
        }
        XOCounter++;
        q.add(move);
        checkIfGameEnds();
    }

    public void showVedio(String vid) {
        String path;
        if (vid == "w") {
            path = "videos/win.mp4";
        } else {
            path = "videos/lose.mp4";
        }
        Stage stage = new Stage();
        Media media = new Media(getClass().getClassLoader().getResource(path).toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        mediaPlayer.setAutoPlay(true);

        Group rot = new Group();
        rot.getChildren().add(mediaView);
        Scene scen = new Scene(rot, 450, 350);

        stage.setScene(scen);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setTitle("Playing video");
        stage.show();
        AppManager.gamePane.setDisable(true);

        stage.setOnCloseRequest((event) -> {
            mediaPlayer.stop();
            AppManager.gamePane.setDisable(false);

        });

    }

    //**********************************************************
    //**********************************************************
    private class subtimer extends TimerTask {
        //run method

        boolean f = true;
        Color clr = xForeground;
        String ch = "X", s;
        Font font=fontXO;

        subtimer() {                                            //*******DLT*******
            for (Button boardButton : boardButtons) {
                boardButton.removeEventHandler(ActionEvent.ACTION, eventHandler);
            }
        }

        @Override
        public void run() {
            Platform.runLater(() -> {
                if (f) {
                    clr = xForeground;
                    ch = "X";
                } else {
                    clr = oForeground;
                    ch = "O";
                }

                if (RecordsPane.recQ.isEmpty()) {
                    System.out.println("qqq isss empty");
                    tm.cancel();
                    System.out.println(RecordsPane.recQ);
                    RecordsPane.recQ = new LinkedList<>();
                    System.out.println("newQ" + RecordsPane.recQ);
                    play_btn.setVisible(true);
                    //startNewGame();
                } else {
                    System.out.println("not emptyssss");
                    System.out.println("B4sss");
                    s = RecordsPane.recQ.remove();
                    System.out.println("s=  " + s);
                    //System.out.println("c=  " + Character.getNumericValue(s.charAt(4)));
                    
                    if (s.length() > 3) {
                        if (RecordsPane.recQ.isEmpty()) {
                           // colorBackgroundWinnerButtons(boardButtons[Character.getNumericValue(s.charAt(2))], boardButtons[Character.getNumericValue(s.charAt(3))], boardButtons[Character.getNumericValue(s.charAt(4))]);
                            /*if (s.length() > 5) // if player wins with two diagonals. (Cross win)(QDYA MMKN)
                            {
                                colorBackgroundWinnerButtons(boardButtons[Character.getNumericValue(s.charAt(5))], boardButtons[Character.getNumericValue(s.charAt(6))], boardButtons[Character.getNumericValue(s.charAt(7))]);
                            }*/
                        } else {
                            if (s != null) {
                                switch (s) {
                                    case " 0,0":
                                        boardButtons[0].setTextFill(clr);
                                        boardButtons[0].setText(ch);
                                        boardButtons[0].setFont(font);
                                        break;
                                    case " 0,1":
                                        boardButtons[1].setTextFill(clr);
                                        boardButtons[1].setText(ch);
                                        boardButtons[1].setFont(font);
                                        break;
                                    case " 0,2":
                                        boardButtons[2].setTextFill(clr);
                                        boardButtons[2].setText(ch);
                                        boardButtons[2].setFont(font);
                                        break;
                                    case " 1,0":
                                        boardButtons[3].setTextFill(clr);
                                        boardButtons[3].setText(ch);
                                        boardButtons[3].setFont(font);
                                        break;
                                    case " 1,1":
                                        boardButtons[4].setTextFill(clr);
                                        boardButtons[4].setText(ch);
                                        boardButtons[4].setFont(font);
                                        break;
                                    case " 1,2":
                                        boardButtons[5].setTextFill(clr);
                                        boardButtons[5].setText(ch);
                                        boardButtons[5].setFont(font);
                                        break;
                                    case " 2,0":
                                        boardButtons[6].setTextFill(clr);
                                        boardButtons[6].setText(ch);
                                        boardButtons[6].setFont(font);
                                        break;
                                    case " 2,1":
                                        boardButtons[7].setTextFill(clr);
                                        boardButtons[7].setText(ch);
                                        boardButtons[7].setFont(font);
                                        break;
                                    case " 2,2":
                                        boardButtons[8].setTextFill(clr);
                                        boardButtons[8].setText(ch);
                                        boardButtons[8].setFont(font);
                                        break;
                                }
                                f = !f;
                            }
                        }
                    }
                }
                System.out.println("every");
            });
        }
    }
    
    public void chooseVideo(String button){
        if (AppManager.isOnline) {
                if ((AppManager.amISender && button.equals("X")) || !AppManager.amISender && button.equals("O")) {
                    showVedio("w");
                } else {
                    showVedio("o");
                }
            } else if (AppManager.challengeComputer) {
                if (button.equals("X")) {
                    showVedio("w");
                } else {
                    showVedio("o");
                }
            }
    }

    //************************************************************************
    public GamePane() {
        dialogPane.getStylesheets().add( getClass().getResource("/xo/style.css").toExternalForm());
        anchorPane = new AnchorPane();
        bg = new ImageView();
        playeriImg = new ImageView();
        secondPlayerScore = new Label();
        firstPlayerScore = new Label();
        label = new Label();
        comImg = new ImageView();
        boardBackground = new ImageView();
        newGame = new ImageView();
        back = new ImageView();
        sound = new ImageView();
        secondPlayerName = new Label();
        firstPlayerName = new Label();
        button = new Button();
        currentPlayerSymbol = new Label();
        symbolX = new ImageView();
        symbolO = new ImageView();
        play_btn = new ImageView();
        soundoff_btn = new ImageView();

        boardPane.setPrefSize(300, 300);
        boardPane.setTranslateX(45);
        boardPane.setTranslateY(105);

        anchorPane.setPrefHeight(500.0);
        anchorPane.setPrefWidth(380.0);

        bg.setFitHeight(520.0);
        bg.setFitWidth(425.0);
        bg.setPickOnBounds(true);
        bg.setPreserveRatio(true);
        bg.setImage(new Image(getClass().getResource("/images/bg.png").toExternalForm()));

        playeriImg.setFitHeight(63.0);
        playeriImg.setFitWidth(63.0);
        playeriImg.setLayoutX(61.0);
        playeriImg.setLayoutY(11.0);
        playeriImg.setPickOnBounds(true);
        playeriImg.setPreserveRatio(true);
        playeriImg.setImage(new Image(getClass().getResource("/images/PLayer.png").toExternalForm()));
        

        secondPlayerScore.setLayoutX(223.0);
        secondPlayerScore.setLayoutY(11.0);
        secondPlayerScore.setText("0");
        secondPlayerScore.setTextFill(javafx.scene.paint.Color.WHITE);
        secondPlayerScore.setFont(new Font("Luducudu", 35.0));

        firstPlayerScore.setLayoutX(143.0);
        firstPlayerScore.setLayoutY(11.0);
        firstPlayerScore.setText("0");
        firstPlayerScore.setTextFill(javafx.scene.paint.Color.WHITE);
        firstPlayerScore.setFont(new Font("Luducudu", 35.0));

        label.setLayoutX(183.0);
        label.setLayoutY(11.0);
        label.setText("-");
        label.setTextFill(javafx.scene.paint.Color.WHITE);
        label.setFont(new Font("Luducudu", 39.0));

        comImg.setFitHeight(63.0);
        comImg.setFitWidth(63.0);
        comImg.setLayoutX(259.0);
        comImg.setLayoutY(11.0);
        comImg.setPickOnBounds(true);
        comImg.setPreserveRatio(true);
        comImg.setImage(new Image(getClass().getResource("/images/Com.png").toExternalForm()));

        boardBackground.setFitHeight(300.0);
        boardBackground.setFitWidth(300.0);
        boardBackground.setLayoutX(45.0);
        boardBackground.setLayoutY(105.0);
        boardBackground.setImage(new Image(getClass().getResource("/images/Gamebored.png").toExternalForm()));

        newGame.setFitHeight(68.0);
        newGame.setFitWidth(130.0);
        newGame.setLayoutX(232.0);
        newGame.setLayoutY(437.0);
        newGame.setCursor(Cursor.HAND);
        newGame.setPreserveRatio(true);
        newGame.setImage(new Image(getClass().getResource("/images/NewGame.png").toExternalForm()));

        play_btn.setFitHeight(72.0);
        play_btn.setFitWidth(71.0);
        play_btn.setLayoutX(286.0);
        play_btn.setLayoutY(415.0);
        play_btn.setPickOnBounds(true);
        play_btn.setPreserveRatio(true);
        play_btn.setVisible(false);
        play_btn.setCursor(Cursor.HAND);
        play_btn.setImage(new Image(getClass().getResource("/images/replay.png").toExternalForm()));

        back.setFitHeight(54.0);
        back.setFitWidth(56.0);
        back.setLayoutX(18.0);
        back.setLayoutY(423.0);
        back.setPickOnBounds(true);
        back.setPreserveRatio(true);
        back.setCursor(Cursor.HAND);
        back.setImage(new Image(getClass().getResource("/images/back.png").toExternalForm()));

        sound.setFitHeight(42.0);
        sound.setFitWidth(40.0);
        sound.setLayoutX(7.0);
        sound.setLayoutY(5.0);
        sound.setPickOnBounds(true);
        sound.setPreserveRatio(true);
        sound.setCursor(Cursor.HAND);
        sound.setImage(new Image(getClass().getResource("/images/volume2x.png").toExternalForm()));

        soundoff_btn.setFitHeight(42.0);
        soundoff_btn.setFitWidth(40.0);
        soundoff_btn.setLayoutX(7.0);
        soundoff_btn.setLayoutY(5.0);
        soundoff_btn.setCursor(Cursor.HAND);
        soundoff_btn.setPickOnBounds(true);
        soundoff_btn.setPreserveRatio(true);
        soundoff_btn.setVisible(false);
        soundoff_btn.setImage(new Image(getClass().getResource("/images/volumemute2x.png").toExternalForm()));

        secondPlayerName.setLayoutX(259.0);
        secondPlayerName.setLayoutY(74.0);
        secondPlayerName.setText("ahmed");
        secondPlayerName.setPrefHeight(37.0);
        secondPlayerName.setPrefWidth(116.0);
        secondPlayerName.setTextFill(javafx.scene.paint.Color.WHITE);
        secondPlayerName.setFont(new Font("Luducudu", 20.0));

        firstPlayerName.setLayoutX(77.0);
        firstPlayerName.setLayoutY(68.0);
        firstPlayerName.setPrefHeight(49.0);
        firstPlayerName.setPrefWidth(147.0);
        firstPlayerName.setText("ola");
        firstPlayerName.setTextFill(javafx.scene.paint.Color.WHITE);
        firstPlayerName.setFont(new Font("Luducudu", 20.0));

        button.setLayoutX(223.0);
        button.setLayoutY(431.0);
        button.setMnemonicParsing(false);
        button.setPrefHeight(55.0);
        button.setPrefWidth(147.0);
        button.setStyle("-fx-background-color: radial-gradient(focus-distance 0% , center 50% 50% , radius 55% ,#1485bc,#259ec8);; -fx-background-radius: 20; -fx-border-style: solid; -fx-border-radius: 20; -fx-border-color: #fff; -fx-border-width: 3;");
        button.setText("NEW GAME");
        button.setTextFill(javafx.scene.paint.Color.WHITE);
        button.setVisible(false);
        button.setFont(new Font("Luducudu", 24.0));

        currentPlayerSymbol.setLayoutX(181.0);
        currentPlayerSymbol.setLayoutY(50.0);
        currentPlayerSymbol.setPrefHeight(54.0);
        currentPlayerSymbol.setPrefWidth(19.0);
        currentPlayerSymbol.setText("x");
        currentPlayerSymbol.setTextFill(javafx.scene.paint.Color.YELLOW);
        currentPlayerSymbol.setFont(new Font("Luducudu", 20.0));

        symbolX.setFitHeight(54.0);
        symbolX.setFitWidth(53.0);
        symbolX.setLayoutX(164.0);
        symbolX.setLayoutY(223.0);
        symbolX.setPickOnBounds(true);
        symbolX.setPreserveRatio(true);
        symbolX.setVisible(false);
        symbolX.setImage(new Image(getClass().getResource("/images/X.png").toExternalForm()));

        symbolO.setFitHeight(90.0);
        symbolO.setFitWidth(90.0);
        symbolO.setLayoutX(174.0);
        symbolO.setLayoutY(233.0);
        symbolO.setPickOnBounds(true);
        symbolO.setPreserveRatio(true);
        symbolO.setVisible(false);
        symbolO.setImage(new Image(getClass().getResource("/images/O.png").toExternalForm()));

        createGameBoard();

        anchorPane.getChildren().add(bg);
        anchorPane.getChildren().add(playeriImg);
        anchorPane.getChildren().add(secondPlayerScore);
        anchorPane.getChildren().add(firstPlayerScore);
        anchorPane.getChildren().add(label);
        anchorPane.getChildren().add(comImg);
        anchorPane.getChildren().add(boardBackground);
        anchorPane.getChildren().add(newGame);
        anchorPane.getChildren().add(back);
        anchorPane.getChildren().add(sound);
        anchorPane.getChildren().add(soundoff_btn);
        anchorPane.getChildren().add(secondPlayerName);
        anchorPane.getChildren().add(firstPlayerName);
        anchorPane.getChildren().add(button);
        anchorPane.getChildren().add(currentPlayerSymbol);
        anchorPane.getChildren().add(symbolX);
        anchorPane.getChildren().add(symbolO);
        anchorPane.getChildren().add(play_btn);
        getChildren().add(anchorPane);
        getChildren().add(boardPane);

        System.out.println("isRec ==> " + AppManager.isRec);

        startNewGame();

        back.setOnMouseClicked((event) -> {
            tm.cancel(); //if recording, cancel it.
            playeriImg.setImage(new Image(getClass().getResource("/images/PLayer.png").toExternalForm()));
            StartPane.audioGame.stop();
            StartPane.audio.play();
            AppManager.soundON();
            minimax.emptyBoard();
            //addHandler();
            RecordsPane.indxsss=null;         
            AppManager.rec = false; //عشان لو كان بيسجل وبعدين داس باك   لازم ترجع فولس عشان ده ستاتك
            //new Queue q;  becuase if he was record then he pressed back suddenly.  (Don't save file and remove Queue q. 
            if (AppManager.isRec) {
                play_btn.setVisible(false);
                newGame.setVisible(true);
                AppManager.isRec = false;
                AppManager.viewPane(AppManager.recordsPane);
            } else if (AppManager.isOnline == true) {
                AvailablePlayers.th1=new Thread(AppManager.availablePane);
                AvailablePlayers.th1.start();
                AppManager.isNewGame=false;
                sendData = new ArrayList<String>();
                sendData.add("end entirly");
                sendData.add((firstPlayerScore.getText() + "-" + secondPlayerScore.getText()));
                sendData.add(AppManager.playerName);
                sendData.add(AppManager.opponentName);
                try {
                    AppManager.objectOutput.writeObject(sendData);
                    AppManager.objectOutput.flush();
                } catch (IOException ex) {
                    Logger.getLogger(GamePane.class.getName()).log(Level.SEVERE, null, ex);
                }
                AppManager.availablePane.setDisable(false);
                AppManager.viewPane(AppManager.availablePane);
            } else {
                if (AppManager.challengeComputer) {
                    AppManager.viewPane(AppManager.singlePlayerPane);
                } else {
                    AppManager.viewPane(AppManager.multiPlayerPane);
                }
            }
        startNewGame();
        firstPlayerScore.setText("0");
        secondPlayerScore.setText("0");
        });
        
        sound.setOnMouseClicked((event) -> {
            sound.setVisible(false);
            soundoff_btn.setVisible(true);
            StartPane.audioGame.stop();
          
        });
        soundoff_btn.setOnMouseClicked((event) -> {
            soundoff_btn.setVisible(false);
            sound.setVisible(true);
            StartPane.audioGame.play();

        });
        newGame.setOnMouseClicked((event) -> {
            newGame.setImage(new Image(getClass().getResource("/images/NewGamepressed.png").toExternalForm()));
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
                    newGame.setImage(new Image(getClass().getResource("/images/NewGame.png").toExternalForm()));
                    if (AppManager.isOnline == true) {
                        AppManager.isMyTurn = true;
                        AppManager.amISender = true;
                        //AppManager.isNewGame=true;
                        ArrayList<String> data = new ArrayList<String>();
                        data.add("New game");
                        data.add(AppManager.opponentName);
                        try {
                            AppManager.objectOutput.writeObject(data);
                            AppManager.objectOutput.flush();
                        } catch (IOException ex) {
                            Logger.getLogger(GamePane.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    System.out.println("newGM");
                    minimax.emptyBoard();
                    startNewGame();
                }
            });
            new Thread(sleeper).start();

        });

        play_btn.setOnMouseClicked((event) -> {
            startNewGame();
            AppManager.recordsPane.getMoves();
            play_rec();
            play_btn.setVisible(false);

        });
    }

    public void play_rec() {
        System.out.println("playTHISrec");
        if (!RecordsPane.recQ.isEmpty()) {
            System.out.println("TIMER START");
            tm = new Timer();
            tm.schedule(new GamePane.subtimer(), 0, 1500);
        }

    }
    
    
     public void easyLevelMove() {
        //Choose center if available
        if (boardButtons[4].getText().equals("")) {
            boardButtons[4].setTextFill(oForeground);
            boardButtons[4].setFont(fontXO);
            boardButtons[4].setText("O");
            randomNumber = 4;
        } //Choose a corner if available 
        else if (boardButtons[0].getText().equals("")) {
            boardButtons[0].setTextFill(oForeground);
            boardButtons[0].setFont(fontXO);
            boardButtons[0].setText("O");
            randomNumber = 0;
        } else if (boardButtons[2].getText().equals("")) {
            boardButtons[2].setTextFill(oForeground);
            boardButtons[2].setFont(fontXO);
            boardButtons[2].setText("O");
            randomNumber = 2;
        } else if (boardButtons[6].getText().equals("")) {
            boardButtons[6].setTextFill(oForeground);
            boardButtons[6].setFont(fontXO);
            boardButtons[6].setText("O");
            randomNumber = 6;
        } else if (boardButtons[8].getText().equals("")) {
            boardButtons[8].setTextFill(oForeground);
            boardButtons[8].setFont(fontXO);
            boardButtons[8].setText("O");
            randomNumber = 8;
        } else {
            while (true) {
                randomNumber = random.nextInt(9);
                if (boardButtons[randomNumber].getText().equals("")) {
                    boardButtons[randomNumber].setTextFill(oForeground);
                    boardButtons[randomNumber].setFont(fontXO);
                    boardButtons[randomNumber].setText("O");
                    //String[] index = toIndex(randomNumber).split(",");
                    //System.out.printf("ooooro :" + parseInt(index[0]) + " " + parseInt(index[1]) + "\n");
                    //minimax.fillBoard(parseInt(index[0]), parseInt(index[1]));

                    break;
                }
            }
        }

    }
     
     public void removeHandler(){
         for (Button boardButton : boardButtons) {
                        boardButton.removeEventHandler(ActionEvent.ACTION, eventHandler);
                    }
     }
     
     
     public void addHandler(){
         for (Button boardButton : boardButtons) {
                        boardButton.addEventHandler(ActionEvent.ACTION, eventHandler);
                    }
     }
    
}
