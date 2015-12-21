package mrsmyx.controls;

import javafx.scene.control.Alert;

/**
 * Created by cj on 12/21/15.
 */
public class MessageBox {
    public static Alert Builder(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert;
    }
}
