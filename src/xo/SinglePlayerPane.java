package xo;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.scene.text.Font;

public class SinglePlayerPane extends Pane {

    protected final AnchorPane anchorPane;
    protected final ImageView imageView;
    protected final TextField name_txt;
    protected final ImageView imageView0;
    protected final ImageView start_btn;
    protected final ImageView sound_btn;
    protected final ImageView soundoff_btn;
    protected final ImageView back_btn;
    protected final ImageView rec_btn;
    protected final ImageView recON_btn;

    public SinglePlayerPane() {

        anchorPane = new AnchorPane();
        imageView = new ImageView();
        name_txt = new TextField();
        imageView0 = new ImageView();
        start_btn = new ImageView();
        sound_btn = new ImageView();
        soundoff_btn = new ImageView();
        back_btn = new ImageView();
        rec_btn = new ImageView();
        recON_btn = new ImageView();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(500.0);
        setPrefWidth(380.0);

        anchorPane.setPrefHeight(520.0);
        anchorPane.setPrefWidth(400.0);
        anchorPane.getStyleClass().add("bg_img");
        anchorPane.getStylesheets().add("/xo/style.css");

        imageView.setFitHeight(242.0);
        imageView.setFitWidth(359.0);
        imageView.setLayoutX(33.0);
        imageView.setLayoutY(86.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(getClass().getResource("/images/Player1.png").toExternalForm()));

        name_txt.setLayoutX(82.0);
        name_txt.setLayoutY(219.0);
        name_txt.setPrefHeight(41.0);
        name_txt.setPrefWidth(223.0);
        name_txt.setPromptText("Enter Name");
        name_txt.getStyleClass().add("text-input");
        name_txt.setFont(new Font("Berlin Sans FB", 15.0));

        imageView0.setFitHeight(105.0);
        imageView0.setFitWidth(102.0);
        imageView0.setLayoutX(143.0);
        imageView0.setLayoutY(39.0);
        imageView0.setPickOnBounds(true);
        imageView0.setPreserveRatio(true);
        imageView0.setImage(new Image(getClass().getResource("/images/PLayer.png").toExternalForm()));

        start_btn.setFitHeight(73.0);
        start_btn.setFitWidth(193.0);
        start_btn.setLayoutX(99.0);
        start_btn.setLayoutY(292.0);
        start_btn.setPickOnBounds(true);
        start_btn.setPreserveRatio(true);
        start_btn.setImage(new Image(getClass().getResource("/images/Start.png").toExternalForm()));
        start_btn.setCursor(Cursor.HAND);

        sound_btn.setFitHeight(41.0);
        sound_btn.setFitWidth(47.0);
        sound_btn.setLayoutX(14.0);
        sound_btn.setLayoutY(14.0);
        sound_btn.setPickOnBounds(true);
        sound_btn.setPreserveRatio(true);
        sound_btn.setCursor(Cursor.HAND);
        sound_btn.setImage(new Image(getClass().getResource("/images/volume2x.png").toExternalForm()));

        soundoff_btn.setFitHeight(41.0);
        soundoff_btn.setFitWidth(47.0);
        soundoff_btn.setLayoutX(14.0);
        soundoff_btn.setLayoutY(14.0);
        soundoff_btn.setCursor(Cursor.HAND);
        soundoff_btn.setPickOnBounds(true);
        soundoff_btn.setPreserveRatio(true);
        soundoff_btn.setVisible(false);
        soundoff_btn.setImage(new Image(getClass().getResource("/images/volumemute2x.png").toExternalForm()));

        back_btn.setFitHeight(64.0);
        back_btn.setFitWidth(61.0);
        back_btn.setCursor(Cursor.HAND);
        back_btn.setLayoutX(14.0);
        back_btn.setLayoutY(431.0);
        back_btn.setPickOnBounds(true);
        back_btn.setPreserveRatio(true);
        back_btn.setImage(new Image(getClass().getResource("/images/back.png").toExternalForm()));

        rec_btn.setFitHeight(62.0);
        rec_btn.setFitWidth(96.0);
        rec_btn.setLayoutX(156.0);
        rec_btn.setLayoutY(400.0);
        rec_btn.setPickOnBounds(true);
        rec_btn.setPreserveRatio(true);
        rec_btn.setCursor(Cursor.HAND);
        rec_btn.setImage(new Image(getClass().getResource("/images/record2.png").toExternalForm()));

        recON_btn.setFitHeight(62.0);
        recON_btn.setFitWidth(96.0);
        recON_btn.setLayoutX(156.0);
        recON_btn.setLayoutY(400.0);
        recON_btn.setPickOnBounds(true);
        recON_btn.setPreserveRatio(true);
        recON_btn.setVisible(false);
        recON_btn.setCursor(Cursor.HAND);
        recON_btn.setImage(new Image(getClass().getResource("/images/record5.png").toExternalForm()));

        anchorPane.getChildren().add(imageView);
        anchorPane.getChildren().add(name_txt);
        anchorPane.getChildren().add(imageView0);
        anchorPane.getChildren().add(start_btn);
        anchorPane.getChildren().add(sound_btn);
        anchorPane.getChildren().add(soundoff_btn);
        anchorPane.getChildren().add(back_btn);
        anchorPane.getChildren().add(rec_btn);
        anchorPane.getChildren().add(recON_btn);
        getChildren().add(anchorPane);

        start_btn.setOnMouseClicked((event) -> {

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
                    AppManager.isMyTurn = true;
                    AppManager.gamePane.startNewGame();
                    AppManager.gamePane.firstPlayerName.setText(name_txt.getText());
                    AppManager.gamePane.secondPlayerName.setText("Computer");
                    AppManager.gamePane.firstPlayerScore.setText("0");
                    AppManager.gamePane.secondPlayerScore.setText("0");
                    AppManager.gamePane.comImg.setImage(new Image(getClass().getResource("/images/Com.png").toExternalForm()));
                    recON_btn.setVisible(false);
                    rec_btn.setVisible(true);

                    AppManager.challengeComputer = true;
                    AppManager.viewPane(AppManager.gamePane);

                    start_btn.setImage(new Image(getClass().getResource("/images/Start.png").toExternalForm()));
                }
            });
            new Thread(sleeper).start();

        });

        rec_btn.setOnMouseClicked((event) -> {
            AppManager.rec = !AppManager.rec;
            rec_btn.setVisible(false);
            recON_btn.setVisible(true);

        });
        recON_btn.setOnMouseClicked((event) -> {
            AppManager.rec = !AppManager.rec;
            recON_btn.setVisible(false);
            rec_btn.setVisible(true);
        });

        // when click on sound btn
        sound_btn.setOnMouseClicked((event) -> {
            sound_btn.setVisible(false);
            soundoff_btn.setVisible(true);
            LoginPane.audio.stop();
        });
        soundoff_btn.setOnMouseClicked((event) -> {
            soundoff_btn.setVisible(false);
            sound_btn.setVisible(true);
            LoginPane.audio.play();

        });

        back_btn.setOnMouseClicked((event) -> {
            AppManager.rec = false;
            recON_btn.setVisible(false);
            rec_btn.setVisible(true);
            AppManager.viewPane(AppManager.startPane);
        });
    }

}
