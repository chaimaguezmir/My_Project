/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gourmand.gui;

import gourmand.entity.*;
import gourmand.services.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class mainCommandeClientFormController implements Initializable {

    @FXML
    public Button commande_addBtn;

    @FXML
    public Button commande_updateBtn;

    @FXML
    public Button commande_clearBtn;

    @FXML
    public Button commande_deleteBtn;


    @FXML
    public TextField reservation_customer_tv;

    @FXML
    public ComboBox<Integer> panier_cb;

    @FXML
    public DatePicker reservation_datetime_pk;

    @FXML
    public TextField reservation_nb_per_tv;


    @FXML
    public TableView<restaurantTable> tables_tableview;
    public Button commande_btn;
    public TextField commande_adr_dest_tv;
    public Label commande_prix_total_tv;
    public ComboBox<String> status_cb;
    public TableColumn commande_col_status;

    public TextField adresse_depart_tv;
    public TableColumn live_col_adr_depart;
    public TableColumn live_col_adr_arrive;
    public TableColumn live_col_commande;
    public TableColumn live_col_date;
    public TableColumn live_col_etat;
    public TextField adresse_dest_tv;
    public ComboBox<String> etat_cb;
    public ComboBox<Integer> commande_cb;
    public DatePicker liv_date_pk;

    CommandeService servicecommande = new CommandeService();

    public TextArea desc_tv;
    public TextField capacity_tv;
    public AnchorPane commande_form;
    public TableView<Commande> commande_tableView;
    public TableColumn commande_col_adr;
    public TableColumn commande_col_panier;
    public TableColumn commande_col_prix_total;

    LivraisonService livraisonService = new LivraisonService();


    @FXML
    private TextField cat_nom;

    @FXML
    private TableView<Reservation> reservation_tableView;

    private Alert alert;


    private ObservableList<Commande> reservationListData = FXCollections.observableArrayList();
    private ObservableList<Livraison> livListData = FXCollections.observableArrayList();
    public static  int getSessionId() {
        Preferences prefs
                = Preferences.userNodeForPackage(LoginPersonneController.class);
        return prefs.getInt("user_connected",0);


    }

    public void livShowData() {



        System.out.println("data===>" + livListData.toString());

    }


    public ObservableList<Commande> cmdList() {

        List<Commande> res = new ArrayList<>();
        // my commandes
        for (Commande data : servicecommande.getAllCommandClient(getSessionId())) {
            cmdListData.add(data);

        }
        return cmdListData;

    }

    public void cmdShowData() {


        cmdListData = cmdList();

        commande_col_adr.setCellValueFactory(new PropertyValueFactory<>("adresse_dest"));
        commande_col_prix_total.setCellValueFactory(new PropertyValueFactory<>("prix_total"));
        commande_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        commande_col_panier.setCellValueFactory(new PropertyValueFactory<>("idPanier"));

        commande_tableView.setItems(cmdListData);


    }


    public void switchForm(ActionEvent event) throws SQLException {



        if (event.getSource() == commande_btn) {
            commande_form.setVisible(true);


        }

    }


    int selected = 0;
    double currentPrixTotal = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        commande_form.setVisible(true);
        //  commandesList();
        commandesList();

        tablesList();
        livShowData();
        cmdShowData();
        etatListData();

        commande_cb.setOnAction(e -> {
            selectCmd = commande_cb.getValue();


        });


        etat_cb.setOnAction(e -> {
            selectEtat = etat_cb.getValue();


        });



        //commande status :
        commandeStatusList();

        // select status :


        //select :
        panier_cb.setOnAction(e -> {
            selected = panier_cb.getValue();


            System.out.println("Selected item: " + selected);
            // Perform actions based on the selected item

            Panier panier = new Panier();
            ServicePanier servicePanier = new ServicePanier();

            panier = servicePanier.getById(selected);

            commande_prix_total_tv.setText("Total comande:" + String.valueOf(panier.getPrix_total()));
            currentPrixTotal = panier.getPrix_total();
        });

        tablesList();
        // tableShowData();


    }


// reservationS CRUD :


    ObservableList<Reservation> listCat = FXCollections.observableArrayList();
    ObservableList<Commande> listCmd = FXCollections.observableArrayList();
    ObservableList<Livraison> listLiv = FXCollections.observableArrayList();


    ReservationService servicereservation = new ReservationService();


    RestaurantTableService restaurantTableService = new RestaurantTableService();



    public void tablesList() {

        List<Integer> tablesL = new ArrayList<>();
        System.out.println(getSessionId());


    ServicePanier servicePanier = new ServicePanier();

        for (Panier data : servicePanier.getAllPanierByUserId(getSessionId())) {
            tablesL.add(data.getId());
        }

        System.out.println(tablesL);

        ObservableList<Integer> listData = FXCollections.observableArrayList(tablesL);
        panier_cb.setItems(listData);
    }

    String selectStatus = "";

    public void commandeStatusList() {

        List<String> commandeStatus = new ArrayList<>();
        System.out.println("here");

        // cattegory dispaly









    }


    @FXML
    private void reservationClearBtn() {
        reservation_nb_per_tv.setText("");
        reservation_customer_tv.setText("");
        reservation_datetime_pk.setValue(null);
    }


    public void tableAddBtn() throws SQLException {

        if (!capacity_tv.getText().matches("\\d*")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please enter only numbers");
            alert.showAndWait();
            return;
        }
        if (capacity_tv.getText().isEmpty() || desc_tv.getText().isEmpty()) {


            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {

            restaurantTable restaurantTable = new restaurantTable();
            restaurantTable.setCapacity(Integer.parseInt(capacity_tv.getText()));
            restaurantTable.setDescription(desc_tv.getText());
            restaurantTable.setAvailable(true);


            //Add table:
            restaurantTableService.create(restaurantTable);


            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Added!");
            alert.showAndWait();


            listCmd.clear();
            listCmd = servicecommande.getAll();
            commande_tableView.setItems(listCmd);
        }
    }


    public void categoryDeleteBtn() {

    }

    public void commandesSelectData() {
        System.out.println("hi");

        Commande reservation = commande_tableView.getSelectionModel().getSelectedItem();
        int num = commande_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        commande_adr_dest_tv.setText(reservation.getAdresse_dest());

        commande_prix_total_tv.setText(String.valueOf(reservation.getPrix_total()));
        status_cb.setValue("en curs");
        panier_cb.setValue(reservation.getIdPanier());

    }



    public void tableUpdateBtn() {


        if (!capacity_tv.getText().matches("\\d*")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please enter only numbers");
            alert.showAndWait();
            return;
        }
        if (capacity_tv.getText().isEmpty() || desc_tv.getText().isEmpty()) {


            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {


            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to UPDATE tables ID: " +

                    tables_tableview.getSelectionModel().getSelectedItem().getId() + "?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                restaurantTable restaurantTable = new restaurantTable();
                restaurantTable.setDescription(desc_tv.getText());
                restaurantTable.setCapacity(Integer.parseInt(capacity_tv.getText()));


                restaurantTableService.update(tables_tableview.getSelectionModel().getSelectedItem().getId(), restaurantTable);


                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Updated!");
                alert.showAndWait();

                listCmd.clear();
                listCmd = servicecommande.getAllCommandClient(PersonneService.idSessions);
                commande_tableView.setItems(listCmd);
            }
        }
    }


    public void tableClearBtn() {
        commande_adr_dest_tv.setText("");
        panier_cb.setValue(0);
        commande_prix_total_tv.setText("");
    }

    public void tableDeleteBtn() {

        int id = tables_tableview.getSelectionModel().getSelectedItem().getId();
        if (id == 0) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to DELETE table ID: " + id + "?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                try {
                    // Then delete the product record

                    restaurantTableService.delete(id);


                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Success Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    listCmd.clear();
                    listCmd = servicecommande.getAllCommandClient(PersonneService.idSessions);
                    commande_tableView.setItems(listCmd);
                    // TO CLEAR YOUR FIELDS
                    tableClearBtn();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Cancelled");
                alert.showAndWait();
            }

        }

    }

    public void tableSelectData(MouseEvent mouseEvent) {
        System.out.println("hi");

        restaurantTable restaurantTable = tables_tableview.getSelectionModel().getSelectedItem();
        int num = tables_tableview.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        capacity_tv.setText(String.valueOf(restaurantTable.getCapacity()));
        desc_tv.setText(restaurantTable.getDescription());

    }


    public void commandeAddBtn() {
        if (commande_adr_dest_tv.getText().isEmpty() || commande_prix_total_tv.getText().isEmpty()
                || panier_cb.getValue() == null
        ) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
            return;
        } else {
            Commande commande = new Commande();
            commande.setDate(Date.valueOf(LocalDate.now())); // Set the date to the current system date
            commande.setAdresse_dest(commande_adr_dest_tv.getText());
            commande.setPrix_total(currentPrixTotal);
            commande.setIdPersonne(PersonneService.idSessions);
            System.out.println("select panier id =" + selected);
            commande.setIdPanier(selected);
            commande.setStatus("en cours");


            //Add table:
            servicecommande.create(commande);


            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Added!");
            alert.showAndWait();


            listCmd.clear();
            listCmd = servicecommande.getAllCommandClient(PersonneService.idSessions);
            commande_tableView.setItems(listCmd);
        }
    }

    public void commandeUpdateBtn() {
        if (commande_adr_dest_tv.getText().isEmpty() || commande_prix_total_tv.getText().isEmpty()
                || panier_cb.getValue() == null || status_cb.getValue() == null
        ) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
            return;
        } else {
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to UPDATE commande ID: " +

                    commande_tableView.getSelectionModel().getSelectedItem().getId() + "?");

            int idcommande = commande_tableView.getSelectionModel().getSelectedItem().getId();
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                Commande commande = new Commande();
                commande.setDate(Date.valueOf(LocalDate.now())); // Set the date to the current system date
                commande.setAdresse_dest(commande_adr_dest_tv.getText());
                commande.setPrix_total(currentPrixTotal);
                commande.setStatus(selectStatus);
                commande.setIdPersonne(PersonneService.idSessions);
                System.out.println("select panier id =" + selected);
                commande.setIdPanier(selected);


                //Add table:
                servicecommande.update(idcommande, commande);


                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Successfully updated!");
                alert.showAndWait();


                listCmd.clear();
                listCmd = servicecommande.getAllCommandClient(PersonneService.idSessions);
                commande_tableView.setItems(listCmd);
            }
        }

    }

    public void commandeClearBtn() {
        commande_adr_dest_tv.setText("");
        panier_cb.setValue(null);
        status_cb.setValue(null);
        commande_prix_total_tv.setText("");

    }

    public void commandeDeleteBtn(ActionEvent event) {
        int id = commande_tableView.getSelectionModel().getSelectedItem().getId();
        if (id == 0) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to DELETE commande ID: " + id + "?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                try {
                    // Then delete the product record

                    servicecommande.delete(id);


                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Success Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    listCmd.clear();
                    listCmd = servicecommande.getAllCommandClient(PersonneService.idSessions);
                    commande_tableView.setItems(listCmd);
                    // TO CLEAR YOUR FIELDS
                    tableClearBtn();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Cancelled");
                alert.showAndWait();
            }

        }

    }

    private ObservableList<Commande> cmdListData = FXCollections.observableArrayList();

    public void commandesList() {

        List<Integer> commandes = new ArrayList<>();
        System.out.println("here");

        // cattegory dispaly


        for (Commande data : servicecommande.getAllCommandClient(PersonneService.idSessions)) {
            commandes.add(data.getId());
        }


        ObservableList listData = FXCollections.observableArrayList(commandes);
        commande_cb.setItems(listData);
    }



    public void etatListData() {

        List<String> etats = new ArrayList<>();
        System.out.println("here");

        // cattegory dispaly

        etats.add("expédié"); // chnoi a5r roua touihri
        etats.add("livré");

        ObservableList listData = FXCollections.observableArrayList(etats);
        etat_cb.setItems(listData);
    }

    int selectCmd = 0;
    String selectEtat = "";


    public  void liveSelectData() {}

    @FXML
    void exit(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashbord.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {

        }
    }
}