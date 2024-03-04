package gourmand.gui;

import gourmand.services.PersonneService;
import gourmand.entity.Personne;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AfficherPersonneController  {


    @FXML
    private Button BtnCmdLvr;
    @FXML
    private TableView<Personne> table_viw;
    @FXML
    private TableColumn<Personne, String> cNom;

    @FXML
    private TableColumn<Personne, String> cPrenom;

    @FXML
    private TableColumn<Personne, Integer> cAge;

    @FXML
    private TableColumn<Personne, Integer> cTel;

    @FXML
    private TableColumn<Personne, String> cAdresse;


    @FXML
    private Button Produit;

    @FXML
    private Button Restervation;

    @FXML
    private TableColumn<Personne, String> cEmail;

    @FXML
    private TableColumn<Personne, String> cMdp;

    @FXML
    private TableColumn<Personne, Void> deletTc;

    @FXML
    private TableColumn<Personne, Void> updateTc;
    @FXML
    private TableColumn<Personne, String> cRole;

    private  final PersonneService PersonneService =new PersonneService();

    @FXML
    private TextField rechercherTF;

    @FXML
    void rechercher(KeyEvent event) throws SQLException {
        String nom = rechercherTF.getText();
        if (!nom.isEmpty()) {
            Personne user = PersonneService.getBynom(nom);
            if (user != null) {
                ObservableList<Personne> observableList = FXCollections.observableArrayList(user);
                table_viw.setItems(observableList);
            } else {
                table_viw.setItems(FXCollections.emptyObservableList());
            }
        } else {
            List<Personne> users1= null;
            users1 = PersonneService.recuperer();
            ObservableList<Personne> observableList = FXCollections.observableList(users1);
            table_viw.setItems(observableList);
        }

    }
    @FXML
    public void initialize() throws SQLException {
        // Initialize TableView columns
        cNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        cPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        cAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        cTel.setCellValueFactory(new PropertyValueFactory<>("num_tel"));
        cAdresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        cEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        cMdp.setCellValueFactory(new PropertyValueFactory<>("passxord"));
        cRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        configureDeleteColumn();
        configureModifyColumn();
        loadPersonneData();
    }

    private void loadPersonneData()throws SQLException {
        List<Personne> Personne =PersonneService.recuperer();
        table_viw.getItems().addAll(Personne);

    }



    private void configureModifyColumn() {
        updateTc.setCellFactory(param -> new TableCell<>() {
            private final Button modifyButton = new Button("Modify");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(modifyButton);
                    modifyButton.setOnAction(event -> {
                        Personne PersonneToModify = getTableView().getItems().get(getIndex());
                        modifyBorne(PersonneToModify);
                    });
                }
            }
        });
    }

    private void modifyBorne(Personne borneToModify) {
        try {
            // Charger l'interface de modification
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierPersonne.fxml"));
            Parent root = loader.load();

            // Passer les données de la borne à modifier au contrôleur de l'interface de modification
            ModifierPersonneController modifierController = loader.getController();
            modifierController.initData(borneToModify);

            // Afficher l'interface de modification
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Modifier Borne");
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Une erreur s'est produite lors de l'ouverture de l'interface de modification.");
        }
    }


    public void afficherUser() throws SQLException {
        PersonneService ps = new PersonneService();
        List<Personne> p = new ArrayList<>();
        p=ps.recuperer();
        ObservableList<Personne> list = FXCollections.observableList(p);

        table_viw.setItems(list);
        cNom.setCellValueFactory(new PropertyValueFactory<Personne,String>("nom"));
        cPrenom.setCellValueFactory(new PropertyValueFactory<Personne,String>("prenom"));
        cAge.setCellValueFactory(new PropertyValueFactory<Personne,Integer>("age"));
        cTel.setCellValueFactory(new PropertyValueFactory<Personne,Integer>("num_tel"));
        cAdresse.setCellValueFactory(new PropertyValueFactory<Personne,String>("adresse"));
        cEmail.setCellValueFactory(new PropertyValueFactory<Personne,String>("email"));
        cMdp.setCellValueFactory(new PropertyValueFactory<Personne,String>("password"));
        cRole.setCellValueFactory(new PropertyValueFactory<Personne,String>("role"));

    }
    private void configureDeleteColumn() {
        // Set up the delete button column
        deletTc.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");


            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                    deleteButton.setOnAction(event -> {
                        Personne PersonneToDelete = getTableView().getItems().get(getIndex());

                        try {
                            deletePersonne(PersonneToDelete);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                    });
                }
                }
            });
        }

            private void deletePersonne(Personne personneToDelete) throws SQLException {
                PersonneService.supprimer(personneToDelete.getId());
                table_viw.getItems().remove(personneToDelete);
            }
    private void showAlert(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
// navigation
    @FXML
    void BtnCmdLvr(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainCommandeForm.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while loading the mainCommandeForm.");
        }
    }

    @FXML
    void Produit(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainForm.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while loading the mainForm.");
        }


    }

    @FXML
    void Restervation(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainReservationForm.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while loading the mainCommandeForm.");
        }

    }

        }



