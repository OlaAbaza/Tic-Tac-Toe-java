package xo;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.scene.text.Font;

public class MultiPlayerPane extends Pane {

    protected final AnchorPane anchorPane;
    protected final ImageView imageView;
    protected final TextField name_p1_txt;
    protected final ImageView imageView0;
    protected final ImageView start_btn;
    protected final ImageView sound_btn;
    protected final ImageView back_btn;
    protected final ImageView rec_btn;
    protected final TextField name_p2_txt1;
    protected final ImageView imageView1;
    protected final ImageView imageView2;
    protected final ImageView recON_btn;
    protected final ImageView soundoff_btn;

    public MultiPlayerPane() {
        anchorPane = new AnchorPane();
        imageView = new ImageView();
        name_p1_txt = new TextField();
        imageView0 = new ImageView();
        start_btn = new ImageView();
        sound_btn = new ImageView();
        back_btn = new ImageView();
        rec_btn = new ImageView();
        name_p2_txt1 = new TextField();
        recON_btn = new ImageView();
        soundoff_btn = new ImageView();
        imageView1 = new ImageView();
        imageView2 = new ImageView();

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

        imageView.setFitHeight(264.0);
        imageView.setFitWidth(300.0);
        imageView.setLayoutX(43.0);
        imageView.setLayoutY(115.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(getClass().getResource("/images/mult.png").toExternalForm()));

        imageView0.setFitHeight(105.0);
        imageView0.setFitWidth(102.0);
        imageView0.setLayoutX(63.0);
        imageView0.setLayoutY(47.0);
        imageView0.setPickOnBounds(true);
        imageView0.setPreserveRatio(true);
        imageView0.setImage(new Image(getClass().getResource("/images/PLayer.png").toExternalForm()));

        start_btn.setFitHeight(70.0);
        start_btn.setFitWidth(171.0);
        start_btn.setLayoutX(114.0);
        start_btn.setLayoutY(348.0);
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
        sound_btn.setImage(new Image(getClass().getResource("/images/volume2x.png").toExternalForm()));
        sound_btn.setCursor(Cursor.HAND);

        soundoff_btn.setFitHeight(41.0);
        soundoff_btn.setFitWidth(47.0);
        soundoff_btn.setLayoutX(14.0);
        soundoff_btn.setLayoutY(14.0);
        soundoff_btn.setPickOnBounds(true);
        soundoff_btn.setPreserveRatio(true);
        soundoff_btn.setVisible(false);
        soundoff_btn.setCursor(Cursor.HAND);
        soundoff_btn.setImage(new Image(getClass().getResource("/images/volumemute2x.png").toExternalForm()));

        back_btn.setFitHeight(64.0);
        back_btn.setFitWidth(61.0);
        back_btn.setLayoutX(14.0);
        back_btn.setLayoutY(431.0);
        back_btn.setPickOnBounds(true);
        back_btn.setPreserveRatio(true);
        back_btn.setCursor(Cursor.HAND);
        back_btn.setImage(new Image(getClass().getResource("/images/back.png").toExternalForm()));

        rec_btn.setFitHeight(62.0);
        rec_btn.setFitWidth(96.0);
        rec_btn.setLayoutX(150.0);
        rec_btn.setLayoutY(424.0);
        rec_btn.setPickOnBounds(true);
        rec_btn.setPreserveRatio(true);
        rec_btn.setCursor(Cursor.HAND);
        rec_btn.setImage(new Image(getClass().getResource("/images/record2.png").toExternalForm()));

        recON_btn.setFitHeight(62.0);
        recON_btn.setFitWidth(96.0);
        recON_btn.setLayoutX(150.0);
        recON_btn.setLayoutY(424.0);
        recON_btn.setPickOnBounds(true);
        recON_btn.setPreserveRatio(true);
        recON_btn.setVisible(false);
        recON_btn.setCursor(Cursor.HAND);
        recON_btn.setImage(new Image(getClass().getResource("/images/record5.png").toExternalForm()));

        name_p1_txt.setLayoutX(83.0);
        name_p1_txt.setLayoutY(200.0);
        name_p1_txt.setPrefHeight(41.0);
        name_p1_txt.setPrefWidth(223.0);
        name_p1_txt.setPromptText("Enter Name");
        name_p1_txt.setFont(new Font("Calibri", 18.0));
        name_p1_txt.getStyleClass().add("text-input");

        name_p2_txt1.setLayoutX(83.0);
        name_p2_txt1.setLayoutY(293.0);
        name_p2_txt1.setPrefHeight(41.0);
        name_p2_txt1.setPrefWidth(223.0);
        name_p2_txt1.setPromptText("Enter Name");
        name_p2_txt1.getStyleClass().add("text-input");
        name_p2_txt1.setFont(new Font("Calibri", 18.0));

        imageView1.setFitHeight(105.0);
        imageView1.setFitWidth(102.0);
        imageView1.setLayoutX(200.0);
        imageView1.setLayoutY(47.0);
        imageView1.setPickOnBounds(true);
        imageView1.setPreserveRatio(true);
        imageView1.setImage(new Image(getClass().getResource("/images/Avatar1.png").toExternalForm()));

        imageView2.setFitHeight(105.0);
        imageView2.setFitWidth(102.0);
        imageView2.setLayoutX(125.0);
        imageView2.setLayoutY(61.0);
        imageView2.setPickOnBounds(true);
        imageView2.setPreserveRatio(true);
        imageView2.setImage(new Image(getClass().getResource("/images/vstext.png").toExternalForm()));

        anchorPane.getChildren().add(imageView);
        anchorPane.getChildren().add(name_p1_txt);
        anchorPane.getChildren().add(imageView0);
        anchorPane.getChildren().add(start_btn);
        anchorPane.getChildren().add(sound_btn);
        anchorPane.getChildren().add(back_btn);
        anchorPane.getChildren().add(rec_btn);
        anchorPane.getChildren().add(name_p2_txt1);
        anchorPane.getChildren().add(imageView1);
        anchorPane.getChildren().add(imageView2);
        anchorPane.getChildren().add(recON_btn);
        anchorPane.getChildren().add(soundoff_btn);
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
                    AppManager.gamePane.firstPlayerName.setText(name_p1_txt.getText());
                    AppManager.gamePane.secondPlayerName.setText(name_p2_txt1.getText());
                    AppManager.gamePane.firstPlayerScore.setText("0");
                    AppManager.gamePane.secondPlayerScore.setText("0");
                    AppManager.gamePane.comImg.setImage(new Image(getClass().getResource("/images/Avatar1.png").toExternalForm()));
                    recON_btn.setVisible(false);
                    rec_btn.setVisible(true);

                    AppManager.challengeComputer = false;
                    AppManager.viewPane(AppManager.gamePane);

                    start_btn.setImage(new Image(getClass().getResource("/images/Start.png").toExternalForm()));
                }
            });
            new Thread(sleeper).start();

        });
        rec_btn.setOnMouseClicked((event) -> {
            AppManager.rec = true;
            rec_btn.setVisible(false);
            recON_btn.setVisible(true);

        });
        recON_btn.setOnMouseClicked((event) -> {
            AppManager.rec = false;
            recON_btn.setVisible(false);
            rec_btn.setVisible(true);
        });

        // when click on sound btn
        sound_btn.setOnMouseClicked((event) -> {
            sound_btn.setVisible(false);
            soundoff_btn.setVisible(true);
            AppManager.soundoff();
            StartPane.audio.stop();
        });
        soundoff_btn.setOnMouseClicked((event) -> {
            soundoff_btn.setVisible(false);
            sound_btn.setVisible(true);
            AppManager.soundON();
            StartPane.audio.play();

        });

        back_btn.setOnMouseClicked((event) -> {
            AppManager.rec = false;
            recON_btn.setVisible(false);
            rec_btn.setVisible(true);
            AppManager.viewPane(AppManager.startPane);
        });
    }

}
