


package controllers;
import Service.PersonneService;
import entities.Personne;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utile.ApiMail;

import java.io.IOException;
import java.sql.SQLException;
import java.util.prefs.Preferences;

public class LoginPersonneController {
    ApiMail mail=new ApiMail() ;
    private TextField tfAdresse;

    @FXML
    private TextField emailInput;

    @FXML
    private TextField passwordInput;

    @FXML
    private Button bntLogin;

    private PersonneService personneService=new PersonneService();
    private Personne p=new Personne();
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    void login(ActionEvent event) throws SQLException {
        Alert alertType;
        p.clearBinFile();
        String email= emailInput.getText();
        String mdp=passwordInput.getText();
        Preferences prefs = Preferences.userNodeForPackage(LoginPersonneController.class);
        String emailRegex = "\\w+\\.?\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}";
        if (!email.isEmpty() && !mdp.isEmpty()) {
            if (!email.matches(emailRegex)) {
                alertType = new Alert(Alert.AlertType.ERROR);
                alertType.setTitle("Error");
                alertType.setHeaderText("L'adresse email est invalide. Veuillez saisir une adresse email valide (ex: nom_utilisateur@domaine.com) !");
                alertType.show();
            }
        else {
            p.setId(-1);
            p=personneService.login(email,mdp);
        if(p.getId()!=-1){

            if(p.getRole().equals("Admin")){
                try {

                    p.saveJsonToBinFile(p);
                    mail.sendEmail(p.getEmail(),"Login","login recu");
                    root = FXMLLoader.load(getClass().getResource("/AfficherPersonne.fxml"));
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }

            }
            else {
                try {

                    p.saveJsonToBinFile(p);

                    root = FXMLLoader.load(getClass().getResource("/User.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        }
        }}

    public void inscrire(ActionEvent actionEvent) throws IOException, SQLException {

        root = FXMLLoader.load(getClass().getResource("/AjouterPersonne.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }}












  /*  private     void login(ActionEvent event) throws IOException {
        String email = emailInput.getText();
        String mdp = passwordInput.getText();

        if (email.isEmpty() || mdp.isEmpty()) {
            // Gestion de la validation, affichez un message d'avertissement
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir l'email et le mot de passe.");
            alert.show();
        } else {
            try {
                Personne personne = personneService.login(email, mdp);

                if (personne != null) {
                    // Connexion réussie, récupérer les détails complets de l'utilisateur
                    Personne completePersonneDetails = personneService.getByEmail(email);

                    System.out.println("Connexion réussie !");
                    System.out.println("Nom d'utilisateur complet: " + completePersonneDetails.getNom());
                    System.out.println("Autres détails: " + completePersonneDetails);
                } else {
                    // Affichez un message d'erreur en cas d'échec de la connexion
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Email ou mot de passe invalide !");
                    alert.show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }*/

    // ... (autres méthodes ou champs si nécessaire)




