/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xo;

import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.scene.text.Font;

/**
 *
 * @author Ola Abaza
 */
public class ScoresPane extends Pane {

    protected final AnchorPane anchorPane;
    protected final ImageView imageView;
    protected final Label label;
    protected final TableView<Score> scoreTable;
    protected final TableColumn tableColumn;
    protected final TableColumn tableColumn0;
    protected final TableColumn tableColumn1;
    protected final TableColumn tableColumn2;
    protected final ImageView back;
    protected final ImageView sound;
    protected final ImageView soundoff_btn;
    ArrayList<String> opponent = new ArrayList<String>();
    ArrayList<String> PlayerOneScore = new ArrayList<String>();
    ArrayList<String> PlayerTwoScore = new ArrayList<String>();

    

    public ScoresPane() {
        anchorPane = new AnchorPane();
        imageView = new ImageView();
        label = new Label();
        scoreTable = new TableView<>();
        tableColumn = new TableColumn();
        tableColumn0 = new TableColumn();
        tableColumn1 = new TableColumn();
        tableColumn2 = new TableColumn();
        soundoff_btn = new ImageView();
        back = new ImageView();
        sound = new ImageView();

        

      

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(500.0);
        setPrefWidth(380.0);

        anchorPane.setMaxHeight(USE_PREF_SIZE);
        anchorPane.setMaxWidth(USE_PREF_SIZE);
        anchorPane.setMinHeight(USE_PREF_SIZE);
        anchorPane.setMinWidth(USE_PREF_SIZE);
        anchorPane.setPrefHeight(500.0);
        anchorPane.setPrefWidth(380.0);

        imageView.setFitHeight(520.0);
        imageView.setFitWidth(425.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(getClass().getResource("/images/bg.png").toExternalForm()));

        label.setLayoutX(85.0);
        label.setLayoutY(26.0);
        label.setText("Game History");
        label.setTextFill(javafx.scene.paint.Color.WHITE);
        label.setFont(new Font("Luducudu", 36.0));

        scoreTable.setLayoutX(40.0);
        scoreTable.setLayoutY(133.0);
        scoreTable.setPrefHeight(297.0);
        scoreTable.setPrefWidth(301.0);
        scoreTable.setEditable(false);
        scoreTable.getStylesheets().add("/xo/style.css");

        tableColumn.setEditable(false);
        tableColumn.setPrefWidth(100.0);
        tableColumn.setSortable(false);
        tableColumn.setCellValueFactory(new PropertyValueFactory<>("opponent"));
        tableColumn.setText("Opponent Name");

        tableColumn0.setPrefWidth(200.0);
        tableColumn0.setText("Score");

        tableColumn1.setPrefWidth(110.0);
        tableColumn1.setText("Player");
        tableColumn1.setCellValueFactory(new PropertyValueFactory<>("PlayerOneScore"));

        tableColumn2.setPrefWidth(110.0);
        tableColumn2.setText("Opponent");
        tableColumn2.setCellValueFactory(new PropertyValueFactory<>("PlayerTwoScore"));

        back.setFitHeight(60.0);
        back.setFitWidth(46.0);
        back.setLayoutX(14.0);
        back.setLayoutY(433.0);
        back.setPickOnBounds(true);
        back.setPreserveRatio(true);
        back.setCursor(Cursor.HAND);
        back.setImage(new Image(getClass().getResource("/images/back.png").toExternalForm()));

        sound.setFitHeight(42.0);
        sound.setFitWidth(50.0);
        sound.setLayoutX(13.0);
        sound.setLayoutY(14.0);
        sound.setPickOnBounds(true);
        sound.setPreserveRatio(true);
        sound.setCursor(Cursor.HAND);
        sound.setImage(new Image(getClass().getResource("/images/volume2x.png").toExternalForm()));

        soundoff_btn.setFitHeight(42.0);
        soundoff_btn.setFitWidth(50.0);
        soundoff_btn.setLayoutX(13.0);
        soundoff_btn.setLayoutY(14.0);
        soundoff_btn.setCursor(Cursor.HAND);
        soundoff_btn.setPickOnBounds(true);
        soundoff_btn.setPreserveRatio(true);
        soundoff_btn.setVisible(false);
        soundoff_btn.setImage(new Image(getClass().getResource("/images/volumemute2x.png").toExternalForm()));

        getChildren().add(anchorPane);
        getChildren().add(imageView);
        getChildren().add(label);
        scoreTable.getColumns().add(tableColumn);
        tableColumn0.getColumns().add(tableColumn1);
        tableColumn0.getColumns().add(tableColumn2);
        scoreTable.getColumns().add(tableColumn0);
        getChildren().add(scoreTable);
        getChildren().add(back);
        getChildren().add(sound);
        getChildren().add(soundoff_btn);

        back.setOnMouseClicked((event) -> {
            AvailablePlayers.th1=new Thread(AppManager.availablePane);
            AvailablePlayers.th1.start();
            AppManager.viewPane(AppManager.availablePane);

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

    }
    
    
    public void setScores(){
          for (int i = 0; i < AppManager.Scores.size(); i++) {
              System.out.println("set"+i);
            scoreTable.getItems().add(AppManager.Scores.get(i));
        }
    }
    

}
