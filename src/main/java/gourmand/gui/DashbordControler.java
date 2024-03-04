package gourmand.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.prefs.Preferences;

public class DashbordControler {

    @FXML
    private AnchorPane main_form;

    @FXML
    private AnchorPane reservation_form;

    @FXML
    private Label username;

    @FXML
    void L(ActionEvent event)   {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainCommandeClientForm.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
//mainClientForm
    @FXML
    void M(ActionEvent event)   {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainClientForm.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
//mainReservationClient
    @FXML
    void R(ActionEvent event)  {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainReservationClientForm.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }


    //clear session
    public void onLogout(ActionEvent actionEvent) throws IOException {

        Preferences preferences = Preferences.userNodeForPackage(LoginPersonneController.class);
        preferences.remove("user_connected");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginPersonne.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }
}