/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gourmand.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hazem
 */
public class InterfaceMembreController implements Initializable {

    @FXML
    private AnchorPane rootPane3;
    @FXML
    private Pane paneId;
    @FXML
    private Button profilbtn;
    @FXML
    private Label wlcm;
    @FXML
    private Button logoffbtn1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void ActionProfilbtn(ActionEvent event) {
    }

    @FXML
    private void constr(ActionEvent event) throws IOException {
        Parent page2 = FXMLLoader.load(getClass().getResource("mainClientForm.fxml"));
        Scene scene2 = new Scene(page2);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(scene2);
        app_stage.show();
    }

    @FXML
    private void p(ActionEvent event) throws IOException {
        Parent page2 = FXMLLoader.load(getClass().getResource("mainReservationClient.fxml"));
        Scene scene2 = new Scene(page2);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(scene2);
        app_stage.show();
    }

    @FXML
    private void redservices(ActionEvent event) throws IOException {
        Parent page2 = FXMLLoader.load(getClass().getResource("mainCommandeClientForm.fxml"));
        Scene scene2 = new Scene(page2);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(scene2);
        app_stage.show();
    }

    @FXML
    private void consulteven(ActionEvent event) throws IOException {
    }

    @FXML
    private void ajouterreclam(ActionEvent event) throws IOException {
    }

    @FXML
    private void reds(ActionEvent event) throws IOException{
    }

    @FXML
    private void ActionLogOffbtn(ActionEvent event) throws IOException{
    }

}
