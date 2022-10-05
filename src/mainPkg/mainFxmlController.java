/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainPkg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author kurdistan
 */
public class mainFxmlController implements Initializable {

    File fileVideo;

    @FXML
    private MediaView mediaView;
    MediaPlayer mediaPlayer;
    @FXML
    private Slider volumeSlider;
    @FXML
    private Slider timeSlider;
    @FXML
    private Text fTime;
    @FXML
    private Text sTime;
    @FXML
    private Text vText;
    @FXML
    private Text textFileName;
    @FXML
    private Button playBtn;
    @FXML
    private Button prevBtn;
    @FXML
    private Button nextBtn;
    @FXML
    private Button volumeBtn;
    @FXML
    private StackPane sp;
    int volume = 25;
    boolean volumeBool = false;
    @FXML
    private Button returnButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // set icon to button 
        playBtn.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/Play-icon.png"))));
        nextBtn.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/Skip-forward-icon.png"))));
        prevBtn.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/Skip-backward-icon.png"))));
        volumeBtn.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/Sound-on-icon.png"))));
        returnButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/Counterclockwise-arrow-icon.png"))));
        //########################################################
        //Slider ->
        timeSlider.setMin(0);
        timeSlider.setMax(100);
        //Current value ->
        timeSlider.setValue(0);
        timeSlider.setShowTickLabels(false);
        timeSlider.setShowTickMarks(false);
        timeSlider.setBlockIncrement(1);
        //########################################################
        //media view -> 
        mediaView.fitWidthProperty().bind(sp.widthProperty());
        mediaView.fitHeightProperty().bind(sp.heightProperty());
        //########################################################
        //volumeSlider->
        volumeSlider.setMin(0);
        volumeSlider.setMax(100);
        //Current value ->
        volumeSlider.setShowTickLabels(false);
        volumeSlider.setShowTickMarks(false);
        volumeSlider.setBlockIncrement(1);
        //############################################
        volumeSlider.setValue(volume);
        vText.setText("" + volume + "%");
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volumeSlider.getValue() / 100);
        }
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if (mediaPlayer != null) {
                    mediaPlayer.setVolume(volumeSlider.getValue() / 100);
                }
                volume = (int) (volumeSlider.getValue());
                vText.setText("" + volume + "%");
                if (volume != 0) {
                    volumeBtn.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/Sound-on-icon.png"))));
                } else {
                    volumeBtn.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/Sound-off-icon.png"))));
                }
            }
        });
        //hedin buttone
        returnButton.setVisible(false);

    }

    @FXML
    void onActionOpenSong(ActionEvent event) throws MalformedURLException {
        File file = openFileChooser();
        playVideo(file);
    }

    @FXML
    void onActionVolumeBtn(ActionEvent event) {
        //if valunebtn clicked->
        try {
            if (volumeBool) {
                volumeBtn.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/Sound-off-icon.png"))));
                mediaPlayer.setVolume(0);
                vText.setText("0%");
                volumeBool = false;
            } else {
                volumeBtn.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/Sound-on-icon.png"))));
                vText.setText("" + volume + "%");
                volumeBool = true;
                mediaPlayer.setVolume(volume);
            }
        } catch (Exception ex) {

        }

    }

    public void playVideo(File file) throws MalformedURLException {
        if (file != null) {
            if (mediaPlayer != null) {
                mediaPlayer.dispose();
            }
            Media media = new Media(file.toURI().toURL().toString());
            //this is for set file name
            textFileName.setText(file.getName());
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            // time slider
            mediaPlayer.setOnReady(new Runnable() {
                @Override
                public void run() {
                    timeSlider.setMin(0);
                    timeSlider.setMax(mediaPlayer.getMedia().getDuration().toMinutes());
                    timeSlider.setValue(0);
                    sTime.setText(getTDuration(mediaPlayer.getMedia().getDuration()));

                }
            });

            //listener on player ...
            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                    Duration duration = mediaPlayer.getCurrentTime();
                    fTime.setText(getTDuration(duration));
                    timeSlider.setValue(duration.toMinutes());
                    try {
                        showBtn();
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(mainFxmlController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            timeSlider.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                    if (timeSlider.isPressed()) {
                        double val = timeSlider.getValue();
                        mediaPlayer.seek(new Duration(val * 60 * 1000));
                    }
                }
            });
            volumeSlider.setValue(25);
            mediaPlayer.setVolume(0.25);
            vText.setText("" + 25 + "%");
            playAndChangeImage();
        }
    }

    public File openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            fileVideo = file;
            return file;
        } else {
            return null;
        }
    }

    @FXML
    void onActionPlay(ActionEvent event) {
        playAndChangeImage();
    }

    @FXML
    void onActionPauseMenu(ActionEvent event) {
        if (mediaPlayer != null) {
            MediaPlayer.Status status = mediaPlayer.getStatus();
            if (status == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
                // playBtn.setText("Play");
                playBtn.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/Play-icon.png"))));
            }
        }
    }

    @FXML
    void onActionPlayMenu(ActionEvent event) {
        if (mediaPlayer != null) {
            MediaPlayer.Status status = mediaPlayer.getStatus();
            if (status == MediaPlayer.Status.PAUSED) {
                mediaPlayer.play();
                //  playBtn.setText("Pause");
                playBtn.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/Pause-icon.png"))));
            }
        }
    }

    @FXML
    void onDragDroppedMediaView(DragEvent event) throws FileNotFoundException, MalformedURLException {
        List<File> files = event.getDragboard().getFiles();
        // System.out.println(files.get(0).getAbsolutePath());
        playVideo(files.get(0));
        fileVideo = files.get(0);
    }

    @FXML
    void onDragOverMediaView(DragEvent event) {
        // this is for accept file
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    public String getTDuration(Duration duration) {
        int mls = (int) duration.toMillis();
        int s = mls / 1000;
        int second = s % 60;
        int hour = s / 60;
        int minute = hour % 60;
        hour = hour / 60;

        String sHour, sMinute, sSecond;
        sHour = String.valueOf(hour);
        sMinute = String.valueOf(minute);
        sSecond = String.valueOf(second);

        if (hour < 10) {
            sHour = "0" + hour;
        }
        if (minute < 10) {
            sMinute = "0" + minute;
        }
        if (second < 10) {
            sSecond = "0" + second;
        }

        String s1 = String.format("%s : %s : %s", sHour, sMinute, sSecond);
        return s1;
        // 01:32:21
    }

    public void playAndChangeImage() {
        if (mediaPlayer != null) {
            MediaPlayer.Status status = mediaPlayer.getStatus();
            if (status == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
                // playBtn.setText("Play");
                playBtn.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/Play-icon.png"))));
            } else {
                mediaPlayer.play();
                //  playBtn.setText("Pause");
                playBtn.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/Pause-icon.png"))));
            }
        }
    }

    @FXML
    private void onActionAbout(ActionEvent event) throws IOException {
        //new MyClass().FXMLLoader("aboutFormFXML.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("aboutFormFXML.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(root1));
        stage.show();
    }

    @FXML
    private void onActionReturnVideo(ActionEvent event) throws MalformedURLException {
        if (fileVideo != null) {
            playVideo(fileVideo);
        }
    }

    void showBtn() throws MalformedURLException {
        if (fTime.getText().equals(sTime.getText())) {
            returnButton.setVisible(true);
            mediaView.setOpacity(0.5);
        } else {
            returnButton.setVisible(false);
            mediaView.setOpacity(1);
        }
    }

    @FXML
    private void onKeyReleasedPlayButton(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE) {
            if (fileVideo != null) {
                playAndChangeImage();
            }
        }
    }



}
