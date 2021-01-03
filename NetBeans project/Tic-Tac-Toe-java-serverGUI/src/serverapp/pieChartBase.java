package serverapp;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.derby.jdbc.ClientDriver;

public class pieChartBase extends StackPane {

    protected final AnchorPane anchorPane;
    protected final AnchorPane layer1;
    protected final ImageView add_img;
    protected final JFXTextField fnam_txt;
    protected final JFXTextField lname_txt;
    protected final JFXTextField uname_txt;
    protected final ToggleGroup toggle_group;
    protected final JFXRadioButton radio_btn_f;
    protected final JFXRadioButton radio_btn_m;
    protected final Label gender_label;
    protected final JFXPasswordField pass_txt;
    protected final JFXButton add_btn;
    protected PieChart pieChart;
    protected final AnchorPane layer;
    protected final ImageView chart_img;
    protected final Label l1;
    protected final Label l2;
    protected final JFXButton add_btn1;
    protected final Label l21;
    protected final JFXButton chart_btn;
    protected final ImageView deactive_img;
    protected final ImageView active_img;
    protected final Label onLable;
    protected final Label offLable;
    protected final Label busyLable;
    

    ObservableList<PieChart.Data> data;
    Connection con;
    protected ResultSet rs;
    Statement stmt;
    int onNum = 0, offNum = 0, busyNum = 0;

    public pieChartBase(Stage stage, Stage primaryStage) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        anchorPane = new AnchorPane();
        layer1 = new AnchorPane();
        add_img = new ImageView();
        fnam_txt = new JFXTextField();
        lname_txt = new JFXTextField();
        uname_txt = new JFXTextField();
        radio_btn_f = new JFXRadioButton();
        radio_btn_m = new JFXRadioButton();
        gender_label = new Label();
        pass_txt = new JFXPasswordField();
        add_btn = new JFXButton();
        layer = new AnchorPane();
        chart_img = new ImageView();
        l1 = new Label();
        l2 = new Label();
        add_btn1 = new JFXButton();
        l21 = new Label();
        chart_btn = new JFXButton();
        deactive_img = new ImageView();
        active_img = new ImageView();
        toggle_group = new ToggleGroup();
        onLable = new Label();
        offLable = new Label();
        busyLable = new Label();
        data = FXCollections.observableArrayList(
                new PieChart.Data("OnLine   ", onNum),
                new PieChart.Data("OffLine  ", offNum),
                new PieChart.Data("Busy     ", busyNum)
        );
        
        
        onLable.setLayoutX(157.0);
        onLable.setLayoutY(415.0);
        onLable.setFont(new Font("System", 10.0));



        offLable.setLayoutX(225.0);
        offLable.setLayoutY(415.0);
        offLable.setFont(new Font("System", 10.0));


        busyLable.setLayoutX(297.0);
        busyLable.setLayoutY(415.0);
        busyLable.setFont(new Font("System", 10.0));


        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(500.0);
        setPrefWidth(800.0);
        getStylesheets().add("/serverapp/fxml.css");
        getStyleClass().add("stack_pane");

        pieChart = new PieChart(data);
        pieChart.setLabelLineLength(10);

//database connection
        try {
            DriverManager.registerDriver(new ClientDriver());
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/PlayersDB", "root", "root");
            stmt = con.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException ex) {
            Logger.getLogger(pieChartBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        // update from database
        new Thread() {
            @Override
            public void run() {
                while (true) {

                    try {
                        rs = stmt.executeQuery("SELECT COUNT(status)FROM profile WHERE status=1");
                        rs.next();
                        onNum = Integer.parseInt(rs.getString(1));
                        rs = stmt.executeQuery("SELECT COUNT(status)FROM profile WHERE status=0");
                        rs.next();
                        offNum = Integer.parseInt(rs.getString(1));
                        rs = stmt.executeQuery("SELECT COUNT(status)FROM profile WHERE status=2");
                        rs.next();
                        busyNum = Integer.parseInt(rs.getString(1));
                        addData("OnLine   ", onNum);
                        addData("OffLine  ", offNum);
                        addData("Busy     ", busyNum);
                        Platform.runLater(() -> {
                            busyLable.setText("(" + busyNum + "Users)");
                            offLable.setText("(" + offNum + "Users)");
                            onLable.setText("(" + onNum + "Users)");
                        });
                        //close thread when primaryStage closed
                        primaryStage.setOnCloseRequest((event) -> {
                            try {
                                PreparedStatement pst;
                                pst = con.prepareStatement("UPDATE profile SET status=?");
                                pst.setInt(1, 0);
                                pst.executeUpdate();
                                stop();
                                System.exit(0);
                            } catch (SQLException ex) {
                                Logger.getLogger(pieChartBase.class.getName()).log(Level.SEVERE, null, ex);
                            }
                           
                        });

                    } catch (SQLException ex) {
                        Logger.getLogger(pieChartBase.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }

        }.start();

        anchorPane.setPrefHeight(500.0);
        anchorPane.setPrefWidth(632.0);

        layer1.setLayoutX(309.0);
        layer1.setMaxHeight(USE_PREF_SIZE);
        layer1.setMaxWidth(USE_PREF_SIZE);
        layer1.setMinHeight(USE_PREF_SIZE);
        layer1.setMinWidth(USE_PREF_SIZE);
        layer1.setPrefHeight(500.0);
        layer1.setPrefWidth(491.0);

        add_img.setFitHeight(71.0);
        add_img.setFitWidth(75.0);
        add_img.setLayoutX(225.0);
        add_img.setLayoutY(56.0);
        add_img.setPickOnBounds(true);
        add_img.setPreserveRatio(true);
        add_img.setImage(new Image(getClass().getResource("/images/profile.png").toExternalForm()));

        fnam_txt.setLayoutX(130.0);
        fnam_txt.setLayoutY(141.0);
        fnam_txt.setFont(new Font("Segoe UI", 15.0));
        fnam_txt.setOpacity(0.75);
        fnam_txt.setPrefHeight(29.0);
        fnam_txt.setPrefWidth(270.0);
        fnam_txt.setLabelFloat(true);
        fnam_txt.setFocusColor(Paint.valueOf("#025799"));
        fnam_txt.setPromptText("FirstName");

        lname_txt.setLayoutX(130.0);
        lname_txt.setLayoutY(204.0);
        lname_txt.setFont(new Font("Segoe UI", 15.0));
        lname_txt.setOpacity(0.75);
        lname_txt.setPrefHeight(29.0);
        lname_txt.setPrefWidth(270.0);
        lname_txt.setLabelFloat(true);
        lname_txt.setFocusColor(Paint.valueOf("#025799"));
        lname_txt.setPromptText("LastName");

        uname_txt.setLayoutX(130.0);
        uname_txt.setLayoutY(262.0);
        uname_txt.setOpacity(0.75);
        uname_txt.setFont(new Font("Segoe UI", 15.0));
        uname_txt.setPrefHeight(29.0);
        uname_txt.setPrefWidth(270.0);
        uname_txt.setLabelFloat(true);
        uname_txt.setFocusColor(Paint.valueOf("#025799"));
        uname_txt.setPromptText("UserName");

        radio_btn_f.setLayoutX(220.0);
        radio_btn_f.setLayoutY(334.0);
        radio_btn_f.setPrefHeight(24.0);
        radio_btn_f.setPrefWidth(150.0);
        radio_btn_f.setStyle("-jfx-selected-color:#025799;");
        radio_btn_f.setText("Female");
        radio_btn_f.setToggleGroup(toggle_group);

        radio_btn_m.setStyle("-jfx-selected-color:#025799;");
        radio_btn_m.setText("Male");
        radio_btn_m.setLayoutX(310.0);
        radio_btn_m.setLayoutY(334.0);
        radio_btn_m.setPrefHeight(24.0);
        radio_btn_m.setPrefWidth(150.0);
        radio_btn_m.setToggleGroup(toggle_group);

        gender_label.setLayoutX(135.0);
        gender_label.setLayoutY(334.0);
        gender_label.setOpacity(0.75);
        gender_label.setText("Gender");
        gender_label.setFont(new Font(15.0));

        pass_txt.setLayoutX(130.0);
        pass_txt.setLayoutY(374.0);
        pass_txt.setFont(new Font("Segoe UI", 15.0));
        pass_txt.setOpacity(0.75);
        pass_txt.setPrefHeight(29.0);
        pass_txt.setPrefWidth(270.0);
        pass_txt.setLabelFloat(true);
        pass_txt.setFocusColor(Paint.valueOf("#025799"));
        pass_txt.setPromptText("Password");

        add_btn.setLayoutX(220.0);
        add_btn.setLayoutY(434.0);
        add_btn.getStyleClass().add("add_btn");
        add_btn.setText("Add");

        pieChart.setLayoutY(34.0);
        pieChart.setTitle("Users Status");

        StackPane.setAlignment(layer, javafx.geometry.Pos.CENTER_LEFT);
        layer.setCache(true);
        layer.setMaxHeight(USE_PREF_SIZE);
        layer.setMaxWidth(USE_PREF_SIZE);
        layer.setMinHeight(USE_PREF_SIZE);
        layer.setMinWidth(USE_PREF_SIZE);
        layer.setPrefHeight(500.0);
        layer.setPrefWidth(309.0);
        layer.getStyleClass().add("slideder");

        chart_img.setFitHeight(134.0);
        chart_img.setFitWidth(135.0);
        chart_img.setLayoutX(89.0);
        chart_img.setLayoutY(55.0);
        chart_img.setPickOnBounds(true);
        chart_img.setPreserveRatio(true);
        chart_img.setImage(new Image(getClass().getResource("/images/slogo.png").toExternalForm()));

        l1.setAlignment(javafx.geometry.Pos.CENTER);
        l1.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        l1.setLayoutX(50.0);
        l1.setLayoutY(203.0);
        l1.setPrefHeight(39.0);
        l1.setPrefWidth(211.0);
        l1.setText("Server App ");
        l1.setTextFill(javafx.scene.paint.Color.WHITE);
        l1.setFont(new Font("Arial Bold", 20.0));

        l2.setAlignment(javafx.geometry.Pos.CENTER);
        l2.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        l2.setLayoutX(27.0);
        l2.setLayoutY(250.0);
        l2.setPrefHeight(23.0);
        l2.setPrefWidth(267.0);
        l2.setText("To Add New User Click Here");
        l2.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        l2.setTextFill(javafx.scene.paint.Color.WHITE);

        l21.setAlignment(javafx.geometry.Pos.CENTER);
        l21.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        l21.setLayoutX(27.0);
        l21.setLayoutY(250.0);
        l21.setPrefHeight(23.0);
        l21.setPrefWidth(267.0);
        l21.setText("To See The Users Status Chart Click Here");
        l21.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        l21.setTextFill(javafx.scene.paint.Color.WHITE);

        add_btn1.setLayoutX(87.0);
        add_btn1.setLayoutY(293.0);
        add_btn1.setPrefWidth(135.0);
        add_btn1.setPrefHeight(39.0);
        add_btn1.setOnMouseClicked(this::btn);
        add_btn1.setText("Add");
        add_btn1.getStyleClass().add("slideder_btn");
        add_btn1.setFont(new Font(14.0));

        chart_btn.setLayoutX(87.0);
        chart_btn.setLayoutY(293.0);
        chart_btn.setOnMouseClicked(this::btn2);
        chart_btn.setPrefWidth(135.0);
        chart_btn.setPrefHeight(39.0);
        chart_btn.setText("Chart");
        chart_btn.getStyleClass().add("slideder_btn");
        chart_btn.setFont(new Font(14.0));

        deactive_img.setFitHeight(82.0);
        deactive_img.setFitWidth(80.0);
        deactive_img.setLayoutX(118.0);
        deactive_img.setLayoutY(361.0);
        deactive_img.setPickOnBounds(true);
        deactive_img.setPreserveRatio(true);
        deactive_img.setVisible(false);
        deactive_img.setImage(new Image(getClass().getResource("/images/deactive1.png").toExternalForm()));

        active_img.setFitHeight(82.0);
        active_img.setFitWidth(80.0);
        active_img.setLayoutX(118.0);
        active_img.setLayoutY(361.0);
        active_img.setPickOnBounds(true);
        active_img.setPreserveRatio(true);
        active_img.setImage(new Image(getClass().getResource("/images/active1.png").toExternalForm()));

        layer1.getChildren().add(add_img);
        layer1.getChildren().add(fnam_txt);
        layer1.getChildren().add(lname_txt);
        layer1.getChildren().add(uname_txt);
        layer1.getChildren().add(radio_btn_f);
        layer1.getChildren().add(radio_btn_m);
        layer1.getChildren().add(gender_label);
        layer1.getChildren().add(pass_txt);
        layer1.getChildren().add(add_btn);
        layer1.getChildren().add(pieChart);
        layer1.getChildren().add(onLable);
        layer1.getChildren().add(offLable);
        layer1.getChildren().add(busyLable);
        anchorPane.getChildren().add(layer1);
        getChildren().add(anchorPane);
        layer.getChildren().add(chart_img);
        layer.getChildren().add(l1);
        layer.getChildren().add(l2);
        layer.getChildren().add(add_btn1);
        layer.getChildren().add(l21);
        layer.getChildren().add(chart_btn);
        layer.getChildren().add(deactive_img);
        layer.getChildren().add(active_img);
        getChildren().add(layer);

        fnam_txt.setVisible(false);
        uname_txt.setVisible(false);
        lname_txt.setVisible(false);
        add_img.setVisible(false);
        add_btn.setVisible(false);
        pass_txt.setVisible(false);
        gender_label.setVisible(false);
        radio_btn_f.setVisible(false);
        radio_btn_m.setVisible(false);
        chart_btn.setVisible(false);
        l21.setVisible(false);

        //close server
        active_img.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                active_img.setVisible(false);
                deactive_img.setVisible(true);

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
                        primaryStage.show();
                        stage.close();
                        active_img.setVisible(true);
                        deactive_img.setVisible(false);
                        try {
                            server_app_Base.server.serverSocket.close();
                            server_app_Base.server.th.stop();
                        } catch (IOException ex) {
                            Logger.getLogger(pieChartBase.class.getName()).log(Level.SEVERE, null, ex);
                        }
                       
                    }
                });
                new Thread(sleeper).start();

            }
        });
        
        
        
        add_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String toogleGroupValue="";
                PreparedStatement pst;
                if (toggle_group.getSelectedToggle() != null) {
                RadioButton selectedRadioButton = (RadioButton) toggle_group.getSelectedToggle();
                toogleGroupValue = selectedRadioButton.getText();}
                try {
                    if (uname_txt.getText().equals("") || pass_txt.getText().equals("")||fnam_txt.getText().equals("") || lname_txt.getText().equals("")) {

                        alert.setTitle("Inserting in DB");
                        alert.setContentText("must insert in All fields except gender");
                        alert.showAndWait();

                    }
                    else{
                    pst = con.prepareStatement("INSERT INTO profile VALUES ( ?,?,?,?,?,?)");
                    pst.setString(1, uname_txt.getText());
                    pst.setString(2, pass_txt.getText());
                    pst.setInt(3, 0);
                    pst.setString(4, fnam_txt.getText());
                    pst.setString(5, lname_txt.getText());
                    pst.setString(6, toogleGroupValue);
                    pst.executeUpdate();
                        uname_txt.clear();
                        pass_txt.clear();
                        lname_txt.clear();
                        fnam_txt.clear();}
                } catch (SQLException ex) {
                    Logger.getLogger(pieChartBase.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        

    }
// when click on chart btn

    protected void btn2(javafx.scene.input.MouseEvent mouseEvent) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.7));
        slide.setNode(layer);

        slide.setToX(0);
        slide.play();
        layer1.setTranslateX(0);
        fnam_txt.setVisible(false);
        uname_txt.setVisible(false);
        lname_txt.setVisible(false);
        add_img.setVisible(false);
        add_btn.setVisible(false);
        pass_txt.setVisible(false);
        gender_label.setVisible(false);
        radio_btn_f.setVisible(false);
        radio_btn_m.setVisible(false);
        chart_btn.setVisible(false);
        l21.setVisible(false);

        l2.setVisible(true);
        pieChart.setVisible(true);
        add_btn1.setVisible(true);
        onLable.setVisible(true);
        offLable.setVisible(true);
        busyLable.setVisible(true);

        slide.setOnFinished((e -> {

        }));

    }

    // when click on add btn
    protected void btn(javafx.scene.input.MouseEvent mouseEvent) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.7));
        slide.setNode(layer);

        slide.setToX(491);
        slide.play();

        layer1.setTranslateX(-309);
        fnam_txt.setVisible(true);
        uname_txt.setVisible(true);
        lname_txt.setVisible(true);
        add_img.setVisible(true);
        add_btn.setVisible(true);
        l21.setVisible(true);
        chart_btn.setVisible(true);
        pass_txt.setVisible(true);
        gender_label.setVisible(true);
        radio_btn_f.setVisible(true);
        radio_btn_m.setVisible(true);

        l2.setVisible(false);
        pieChart.setVisible(false);
        add_btn1.setVisible(false);
        onLable.setVisible(false);
        offLable.setVisible(false);
        busyLable.setVisible(false);

        slide.setOnFinished((e -> {

        }));
        
    }

//updates existing Data-Object if name matches (pie chart update)
    public void addData(String name, double value) {

        for (PieChart.Data d : data) {
            if (d.getName().equals(name)) {
                d.setPieValue(value);
                return;
            }
        }
    }
}
