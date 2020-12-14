package serverapp;

import com.jfoenix.controls.JFXToggleButton;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class server_app_Base extends AnchorPane {

    protected final ImageView imageView;
    protected final Label label;
    protected final ImageView deactive_img;
    protected final ImageView active_img;
    public static Server server;

    public server_app_Base(Stage stage, Stage primaryStage) {

        imageView = new ImageView();
        label = new Label();
        deactive_img = new ImageView();
        active_img = new ImageView();

        setId("AnchorPane");
        setPrefHeight(500.0);
        setPrefWidth(800.0);
        setStyle("-fx-background-color: #025799;");
        getStyleClass().add("primary_stage");
        getStylesheets().add("/serverapp/fxml.css");

        imageView.setFitHeight(150.0);
        imageView.setFitWidth(200.0);
        imageView.setLayoutX(320.0);
        imageView.setLayoutY(48.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(getClass().getResource("/images/slogo.png").toExternalForm()));

        label.setLayoutX(255.0);
        label.setLayoutY(229.0);
        label.setText("Server Application Control");
        label.setTextFill(javafx.scene.paint.Color.WHITE);
        label.setFont(new Font("Segoe UI", 25.0));

        deactive_img.setFitHeight(150.0);
        deactive_img.setFitWidth(159.0);
        deactive_img.setLayoutX(320.0);
        deactive_img.setLayoutY(298.0);
        deactive_img.setPickOnBounds(true);
        deactive_img.setPreserveRatio(true);
        deactive_img.setImage(new Image(getClass().getResource("/images/deactive1.png").toExternalForm()));

        active_img.setFitHeight(150.0);
        active_img.setFitWidth(159.0);
        active_img.setLayoutX(320.0);
        active_img.setLayoutY(298.0);
        active_img.setPickOnBounds(true);
        active_img.setPreserveRatio(true);
        active_img.setVisible(false);
        active_img.setImage(new Image(getClass().getResource("/images/active1.png").toExternalForm()));

        getChildren().add(imageView);
        getChildren().add(label);
        getChildren().add(deactive_img);
        getChildren().add(active_img);
        

        deactive_img.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                deactive_img.setVisible(false);
                active_img.setVisible(true);
                
        

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
                        primaryStage.close();
                        stage.show();
                        deactive_img.setVisible(true);
                        active_img.setVisible(false);
                        server=new Server();
                    }
                });
                new Thread(sleeper).start();

            }
        });

    }
}
