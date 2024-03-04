package gourmand.gui;

import gourmand.services.PersonneService;
import gourmand.entity.Personne;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterPersonneController {

    @FXML
    private Button fxadd;

    @FXML
    private TextField tfNom;

    @FXML
    private TextField tfPrenom;

    @FXML
    private TextField tfAge;

    @FXML
    private TextField tfTel;

    @FXML
    private TextField tfAdresse;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfMdp;
    @FXML
    private RadioButton RoleAdmin;

    @FXML
    private RadioButton RoleClient;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
  /*  void addperson(ActionEvent event) throws SQLException {
        String nom = tfNom.getText();
        String prenom = tfPrenom.getText();
        int num_tel = Integer.parseInt(tfTel.getText());
        int age = Integer.parseInt(tfAge.getText());
        String adresse = tfAdresse.getText();
        String email = tfEmail.getText();
        String mdp = tfMdp.getText();
        String role ="user" ,token="***";
        PersonneService ps=new PersonneService();
        Personne p = new Personne(nom,prenom,age,adresse,email,mdp,token,role,num_tel) ;
        ps.ajouter(p);
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/AfficherPersonne.fxml"));
        try {
            Parent root = loader.load();

            tfAdresse.getScene().setRoot(root);

        } catch (IOException e) {

            System.out.println(e.getMessage());
        }



    }*/


    void addperson(ActionEvent event) throws SQLException {
        if (isInputValid()) {
            String nom = tfNom.getText();
            String prenom = tfPrenom.getText();
            int num_tel = Integer.parseInt(tfTel.getText());
            int age = Integer.parseInt(tfAge.getText());
            String adresse = tfAdresse.getText();
            String email = tfEmail.getText();
            String mdp = tfMdp.getText();
           String role = "user",token = "***";

            PersonneService sc = new PersonneService();


            PersonneService ps = new PersonneService();
            Personne p = new Personne(nom, prenom, age, adresse, email, mdp, token, role, num_tel);
            ps.ajouter(p);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginPersonne.fxml"));
            try {
                Parent root = loader.load();
                tfAdresse.getScene().setRoot(root);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private boolean isInputValid() {
        String roleUtilisateur = null;
        if (tfNom.getText().isEmpty() || tfPrenom.getText().isEmpty() || tfTel.getText().isEmpty()
                || tfAge.getText().isEmpty() || tfAdresse.getText().isEmpty() || tfEmail.getText().isEmpty()
                || tfMdp.getText().isEmpty()) {
            showAlert("Error", "Enter a valid content !");
            return false;
        }

        if (tfNom.getText().matches("[0-9]+")) {
            showAlert("Error", "Nom must be string not number !");
            return false;
        }

        if (tfPrenom.getText().matches("[0-9]+")) {
            showAlert("Error", "Prenom must be string not number !");
            return false;
        }

        if (tfAdresse.getText().matches("[0-9]+")) {
            showAlert("Error", "Adresse must be string not number !");
            return false;
        }

        if (tfAge.getText().matches("[A-Z]+") || tfAge.getText().matches("[a-z]+")) {
            showAlert("Error", "Age must be numeric !");
            return false;
        }

        if (!tfEmail.getText().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            showAlert("Error", "L'adresse email est invalide. Veuillez saisir une adresse email valide (ex: nom_utilisateur@domaine.com) !");
            return false;
        }

        if (tfMdp.getText().length() < 8) {
            showAlert("Error", "Le mot de passe doit contenir au moins 8 caractères !");
            return false;


        }
        if(RoleAdmin.isSelected()){ roleUtilisateur = "Admin"; }
        if(RoleClient.isSelected()){ roleUtilisateur = "Client"; }

        // Ajoutez des conditions similaires pour les autres champs...

        return true;
    }

    void showAlert(String title, String contentText) {
        Alert alertType = new Alert(Alert.AlertType.ERROR);
        alertType.setTitle(title);
        alertType.setHeaderText(contentText);
        alertType.show();
    }




    @FXML
    void afficherPersonne(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/AfficherPersonne.fxml"));
        try {
            Parent root = loader.load();

            tfAdresse.getScene().setRoot(root);

        } catch (IOException e) {

            System.out.println(e.getMessage());
        }
    }


    public void retourne_login(ActionEvent actionEvent) throws IOException {
         root = FXMLLoader.load(getClass().getResource("/LoginPersonne.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
