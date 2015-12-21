package mrsmyx.events;

import java.io.File;

/**
 * Created by cj on 12/21/15.
 */
public interface OnFileChooserListener {
    void OnSaveFileSelected(File file);
    void OnFileError(Exception ex);
    void OnOpenFileSelected(File file);
}
