package gourmand.gui;

import gourmand.services.PersonneService;
import gourmand.entity.Personne;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifierPersonneController {

    @FXML
    private TextField nvNom;

    @FXML
    private TextField nvPrenom;

    @FXML
    private TextField nvAge;

    @FXML
    private TextField nvTel;

    @FXML
    private TextField nvAdresse;

    @FXML
    private TextField nvEmail;

    @FXML
    private TextField nvMdp;
    private final PersonneService bs = new PersonneService();
    private Personne PersonneToModify;

    public void initData(Personne personne) {
        this. PersonneToModify = personne;
        // Afficher les détails de la borne à modifier dans les champs de texte
        nvNom.setText(personne.getNom());
        nvPrenom.setText(personne.getPrenom());
        nvAge.setText(String.valueOf(Personne.getAge()));
        nvTel.setText(String.valueOf(Personne. getNum_tel()));
        nvAdresse.setText(Personne.getAdresse());
        nvEmail.setText(Personne.getEmail());
        nvMdp.setText(Personne.getPassword());

    }
    @FXML
    void modifierPersonne(ActionEvent event) {
        try {
            // Mettre à jour les données de la borne avec les nouvelles valeurs
            PersonneToModify.setNom(nvNom.getText());
            PersonneToModify.setPrenom(nvPrenom.getText());
            PersonneToModify.setAge(Integer.parseInt( nvAge.getText()));
            PersonneToModify.setNum_tel(Integer.parseInt( nvTel.getText()));
            PersonneToModify.setAdresse(nvAdresse.getText());
            PersonneToModify.setEmail(  nvEmail.getText());
            PersonneToModify.setPassword( nvMdp.getText());


            // Appeler la méthode de mise à jour dans le service de borne
            bs.modifier(PersonneToModify );
            Stage stage = (Stage) nvNom.getScene().getWindow();
            stage.close();
            // Afficher une alerte pour confirmer la modification
            showAlert("Modification réussie", "La borne a été modifiée avec succès.");
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur s'est produite lors de la modification de la borne.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }



}