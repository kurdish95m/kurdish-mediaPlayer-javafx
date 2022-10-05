package mainPkg;

import java.io.File;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MyClass {

    public static void showWErrorDialog(String message, String invkey) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public  void FXMLLoader(String fxmlDocuments) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlDocuments));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
    }

    public static void showDialog(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public static void showInformationDialog(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public static void showErrorDialog(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public static void showWarningDialog(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public static void showConfarmationDialog(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public static boolean isInteger(String input) { //Pass in string
        try { //Try to make the input into an integer
            Integer.parseInt(input);
            return true; //Return true if it works
        } catch (NumberFormatException e) {
            return false; //If it doesn't work return false
        }
    }

    public static boolean isDoubel(String input) { //Pass in string
        try { //Try to make the input into an Doubel
            Double.parseDouble(input);
            return true; //Return true if it works
        } catch (NumberFormatException e) {
            return false; //If it doesn't work return false
        }
    }
    public static void setIcon(String path) {
        // this is path of the icon on the windows ------
        String pathString = "/img/PadLock-icon.png";
        // Application icon
        Stage stage = new Stage();// this is null stage 
        Image img = new Image(path);// this is path of the icon
        stage.getIcons().add(img);// adding icon 
    }
    public File fileChooser() {
        File file = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(file);
        return file;
    }
}
