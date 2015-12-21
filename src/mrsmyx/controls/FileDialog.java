package mrsmyx.controls;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mrsmyx.events.OnFileChooserListener;

import java.io.File;

/**
 * Created by cj on 12/21/15.
 */
public class FileDialog {

    public enum OptionChooser{
        OPEN,SAVE;
    };
    public static void show(String title, String dir, OnFileChooserListener onFileChooserListener, OptionChooser option, FileChooser.ExtensionFilter... extensionFilter) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.setInitialDirectory(new File(dir));
        for (FileChooser.ExtensionFilter e : extensionFilter) {
            fileChooser.getExtensionFilters().add(e);
        }
        switch (option){
            case OPEN:
                File file = fileChooser.showOpenDialog(new Stage(StageStyle.DECORATED));
                if (file != null) {

                    onFileChooserListener.OnOpenFileSelected(file);
                }else{
                    onFileChooserListener.OnFileError(new NullPointerException("No File Selected"));
                }
                break;
            case SAVE:
                file = fileChooser.showSaveDialog(new Stage(StageStyle.DECORATED));
                if(file != null){
                    onFileChooserListener.OnSaveFileSelected(file);
                }
                else{
                    onFileChooserListener.OnFileError(new NullPointerException("No File Saved"));
                }
                break;
        }

    }

}
