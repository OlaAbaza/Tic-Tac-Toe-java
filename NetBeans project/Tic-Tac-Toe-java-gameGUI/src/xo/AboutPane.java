/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xo;

import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author Ola Abaza
 */
public class AboutPane extends Pane {

    protected final ImageView imageView;
    protected final ImageView imageView0;
    protected final Text text;
    protected final VBox vBox;
    protected final Text text0;
    protected final Text text1;
    protected final Text text2;
    protected final Text text3;
    protected final ImageView back;

    public AboutPane() {

        imageView = new ImageView();
        imageView0 = new ImageView();
        text = new Text();
        vBox = new VBox();
        text0 = new Text();
        text1 = new Text();
        text2 = new Text();
        text3 = new Text();
        back = new ImageView();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(500.0);
        setPrefWidth(380.0);

        imageView.setFitHeight(520.0);
        imageView.setFitWidth(425.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(getClass().getResource("/images/bg.png").toExternalForm()));

        imageView0.setFitHeight(150.0);
        imageView0.setFitWidth(200.0);
        imageView0.setLayoutX(90.0);
        imageView0.setLayoutY(23.0);
        imageView0.setPickOnBounds(true);
        imageView0.setPreserveRatio(true);
        imageView0.setImage(new Image(getClass().getResource("/images/Abouttxt.png").toExternalForm()));

        text.setFill(javafx.scene.paint.Color.WHITE);
        text.setLayoutX(15.0);
        text.setLayoutY(136.0);
        text.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        text.setStrokeWidth(0.0);
        text.setText("Tic Tac Toe game is made in 2020 by an ItI team of mobile native track intake 41, the team are:");
        text.setWrappingWidth(366.8984375);
        text.setFont(Font.loadFont(getClass().getResource("/fonts/Luducudu.ttf").toExternalForm(), 20.0));
   

        vBox.setLayoutX(55.0);
        vBox.setLayoutY(197.0);
        vBox.setPrefHeight(132.0);
        vBox.setPrefWidth(291.0);
        vBox.setSpacing(10.0);

        text0.setFill(javafx.scene.paint.Color.WHITE);
        text0.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        text0.setStrokeWidth(0.0);
        text0.setText("-  Ola Elwy Abaza");
        text0.setWrappingWidth(223.77734375);
        text0.setFont(Font.loadFont(getClass().getResource("/fonts/Luducudu.ttf").toExternalForm(), 24.0));
       

        text1.setFill(javafx.scene.paint.Color.WHITE);
        text1.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        text1.setStrokeWidth(0.0);
        text1.setText("- Tasneem Mostafa");
        text1.setWrappingWidth(227.77734375);
        text1.setFont(new Font("System Bold Italic", 24.0));
          text1.setFont(Font.loadFont(getClass().getResource("/fonts/Luducudu.ttf").toExternalForm(), 24.0));

        text2.setFill(javafx.scene.paint.Color.WHITE);
        text2.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        text2.setStrokeWidth(0.0);
        text2.setText("- Ahmed Amr");
        text2.setWrappingWidth(171.77734375);
        text2.setFont(new Font("System Bold Italic", 24.0));
  text2.setFont(Font.loadFont(getClass().getResource("/fonts/Luducudu.ttf").toExternalForm(), 24.0));
  
        text3.setFill(javafx.scene.paint.Color.WHITE);
        text3.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        text3.setStrokeWidth(0.0);
        text3.setText("- Aymen Omara");
        text3.setWrappingWidth(203.77734375);
        text3.setFont(new Font("System Bold Italic", 24.0));
          text3.setFont(Font.loadFont(getClass().getResource("/fonts/Luducudu.ttf").toExternalForm(), 24.0));

        back.setFitHeight(72.0);
        back.setFitWidth(63.0);
        back.setLayoutX(14.0);
        back.setLayoutY(422.0);
        back.setPickOnBounds(true);
        back.setPreserveRatio(true);
         back.setCursor(Cursor.HAND);
        back.setImage(new Image(getClass().getResource("/images/back.png").toExternalForm()));

        getChildren().add(imageView);
        getChildren().add(imageView0);
        getChildren().add(text);
        vBox.getChildren().add(text0);
        vBox.getChildren().add(text1);
        vBox.getChildren().add(text2);
        vBox.getChildren().add(text3);
        getChildren().add(vBox);
        getChildren().add(back);

        back.setOnMouseClicked((event) -> {
            AppManager.viewPane(AppManager.startPane);
        });

    }
}
