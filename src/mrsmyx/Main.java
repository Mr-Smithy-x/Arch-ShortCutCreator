package mrsmyx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mrsmyx.controls.FileDialog;
import mrsmyx.controls.MessageBox;
import mrsmyx.events.OnFileChooserListener;

import java.io.*;

public class Main extends Application implements EventHandler {

    private TextField shortcut_name, shortcut_location, shortcut_version, shortcut_desc;
    private ImageView shortcut_img;
    private Button shortcut_btn_save, shortcut_btn_open;
    private ComboBox<String> shortcut_cat;
    private String image = "";
    private String[] categories = new String[]{
            "AudioVideo", "Audio", "Video", "Development",
            "Education", "Game", "Graphics", "Network",
            "Office", "Science", "Settings", "System", "Utility"
    };

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Linux Shortcut Creator");
        primaryStage.setScene(new Scene(root));
        init(primaryStage.getScene());
        attach();
        fillCategories();
        primaryStage.show();
    }

    public void init(Scene scene) {
        shortcut_desc = (TextField) scene.lookup("#shortcut_desc");
        shortcut_version = (TextField) scene.lookup("#shortcut_version");
        shortcut_location = (TextField) scene.lookup("#shortcut_location");
        shortcut_name = (TextField) scene.lookup("#shortcut_name");
        shortcut_img = (ImageView) scene.lookup("#shortcut_img");
        shortcut_cat = (ComboBox<String>) scene.lookup("#shortcut_cat");
        shortcut_btn_open = (Button) scene.lookup("#shortcut_btn_open");
        shortcut_btn_save = (Button) scene.lookup("#shortcut_btn_save");
    }

    public void attach() {
        shortcut_btn_open.setOnAction(this);
        shortcut_btn_save.setOnAction(this);
        shortcut_img.setOnMouseClicked(this);
    }

    public void fillCategories() {
        shortcut_cat.getItems().addAll(categories);
    }

    @Override
    public void handle(Event event) {
        if (event instanceof ActionEvent) {
            if (event.getSource() == shortcut_btn_open) {
                FileDialog.show("Open File", System.getProperty( "user.home" ),new OnFileChooserListener() {

                    @Override
                    public void OnSaveFileSelected(File file) {

                    }

                    @Override
                    public void OnFileError(Exception ex) {

                    }

                    @Override
                    public void OnOpenFileSelected(File file) {
                        shortcut_location.setText(file.getAbsolutePath());
                    }
                }, FileDialog.OptionChooser.OPEN);
            } else if (event.getSource() == shortcut_btn_save) {
                FileDialog.show("Save File", System.getProperty( "user.home" ) + "/.local/share/applications/",new OnFileChooserListener() {

                    @Override
                    public void OnSaveFileSelected(File file) {
                        try {
                            String form = "[Desktop Entry]\nVersion=%s\nExec=\"%s\"\nTerminal=false\nCategories=%s;\nComment=%s\nIcon=%s\nName=%s\nType=Application";
                            FileOutputStream fos = new FileOutputStream(file + ".desktop");
                            String compiled = String.format(form, shortcut_version.getText(),shortcut_location.getText(), shortcut_cat.getSelectionModel().getSelectedItem(), shortcut_desc.getText(), image,shortcut_name.getText());
                            fos.write(compiled.getBytes(),0, compiled.getBytes().length);
                            fos.flush();
                            fos.close();
                            MessageBox.Builder(Alert.AlertType.INFORMATION, shortcut_name.getText(), "Shortcut Created!", "Check your application menu :)").showAndWait();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            MessageBox.Builder(Alert.AlertType.ERROR, "File Not Saved: " + shortcut_name.getText(), "Error", "Something went wrong").showAndWait();
                        } catch (IOException e) {
                            e.printStackTrace();
                            MessageBox.Builder(Alert.AlertType.ERROR, "IO Error " + shortcut_name.getText(), "Error", "Something went wrong").showAndWait();
                        }catch (Exception e){
                            MessageBox.Builder(Alert.AlertType.ERROR, "Not filled?" + shortcut_name.getText(), "Error", "Did you fill out everything?").showAndWait();
                        }

                    }

                    @Override
                    public void OnFileError(Exception ex) {

                    }

                    @Override
                    public void OnOpenFileSelected(File file) {

                    }
                }, FileDialog.OptionChooser.SAVE, new FileChooser.ExtensionFilter("Desktop File (*.desktop)","*.desktop"));
            }
        } else if (event instanceof MouseEvent) {
            if (event.getSource() == shortcut_img) {
                FileDialog.show("Select Image", System.getProperty( "user.home" ), new OnFileChooserListener() {

                    @Override
                    public void OnSaveFileSelected(File file) {

                    }

                    @Override
                    public void OnFileError(Exception ex) {
                        MessageBox.Builder(Alert.AlertType.ERROR,"Error","Invalid File","No File Selected").showAndWait();
                    }

                    @Override
                    public void OnOpenFileSelected(File file) {
                        try {
                            shortcut_img.setImage(new Image(new FileInputStream(file)));
                            image = file.getAbsolutePath();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            MessageBox.Builder(Alert.AlertType.ERROR,"File not found","Could not find the file","Press Ok to continue").showAndWait();
                        }
                    }

                }, FileDialog.OptionChooser.OPEN,new FileChooser.ExtensionFilter("ICO (*.ico)", "*.ico"),new FileChooser.ExtensionFilter("PNG (*.png)", "*.png"),new FileChooser.ExtensionFilter("JPG (*.jpg)", "*.jpg", "*.jpeg"),new FileChooser.ExtensionFilter("GIF (*.gif)", "*.gif"));
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
