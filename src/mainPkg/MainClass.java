/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainPkg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author kurdistan
 */
public class MainClass extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/splashscreen/splashScreenAnimation.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        // application name 
        stage.setTitle("Splash screen");
        // Application icon
        Image img = new Image("/icons/iconap.png");
        stage.getIcons().add(img);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
