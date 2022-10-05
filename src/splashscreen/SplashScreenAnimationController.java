/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package splashscreen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Kurdistan
 */
public class SplashScreenAnimationController implements Initializable {

    @FXML
    private Circle c1;
    @FXML
    private Circle c2;
    @FXML
    private Circle c3;
    @FXML
    private Text textLoading;
    @FXML
    private AnchorPane ap;

    /**
     * Initializes the controller class.
     */
    class ShowSplashScreen extends Thread {

        @Override
        public void run() {
            try {
                for (double i = 0; i <= 100; i++) {
                    Thread.sleep(15);
                    if (i == 10 || i == 25 || i == 49 || i == 76 || i == 98) {
                        Thread.sleep(450);
                    }
                    textLoading.setText("Loading " + (int) (i) + " %");
                    if (i == 100) {
                        runApp();

                    }
                }
            } catch (InterruptedException ex) {
            }
        }
    }

    void runApp() {
        Platform.runLater(() -> {
            Stage stage = new Stage();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/mainPkg/mainFxml.fxml"));
            } catch (IOException ex) {

            }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            // application name 
            stage.setTitle("Media plyer");
            // Application icon
            Image img = new Image("/icons/iconap.png");
            stage.getIcons().add(img);
            stage.show();
            ap.getScene().getWindow().hide();
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new ShowSplashScreen().start();
        setRotate(c1, true, 360, 8);
        setRotate(c2, true, 180, 18);
        setRotate(c3, true, 145, 24);
    }

    void setRotate(Circle c, boolean reverse, int angle, int duration) {
        RotateTransition rt = new RotateTransition(Duration.seconds(duration), c);
        rt.setAutoReverse(reverse);
        rt.setByAngle(angle);
        rt.setRate(3);
        rt.setCycleCount(18);
        rt.play();
    }

}
