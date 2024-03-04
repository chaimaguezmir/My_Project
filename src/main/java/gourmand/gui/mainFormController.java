package gourmand.gui;

import gourmand.entity.Categorie;
import gourmand.entity.Product;
import gourmand.services.ProductService;
import gourmand.services.ServiceCategorie;
import gourmand.utils.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;
/*
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
*/

public class mainFormController implements Initializable {

    public TableColumn qrCodeColumn1;
    private Alert alert;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private ProductService serviceProduct = new ProductService();
    private ServiceCategorie serviceCategorie = new ServiceCategorie();

    private ObservableList<Product> cardListData = FXCollections.observableArrayList();
    private ObservableList<Product> inventoryListData;
    private ObservableList<Categorie> listCat = FXCollections.observableArrayList();
    private ProductService productService;

   @FXML
    private TableColumn<Product,Image> qrCodeColumn;


    @FXML
    private AnchorPane main_form;

    @FXML
    private TextField cat_nom;

    @FXML
    private Label username;
    @FXML
    private Button dashboard_btn;

    @FXML
    private Button inventory_btn;

    @FXML
    private Button menu_btn;

    @FXML
    private Button customers_btn;

    @FXML
    private Button logout_btn;

    @FXML
    private AnchorPane inventory_form;

    @FXML
    private TableView<Product> inventory_tableView;

    @FXML
    private TableColumn<Product, String> inventory_col_productID;

    @FXML
    private TableColumn<Product, String> inventory_col_productName;

    @FXML
    private TableColumn<Product, String> inventory_col_type;

    @FXML
    private TableColumn<Product, String> inventory_col_stock;

    @FXML
    private TableColumn<Product, String> inventory_col_price;

    @FXML
    private TableColumn<Product, String> inventory_col_status;

    @FXML
    private ImageView inventory_imageView;

    @FXML
    private Button inventory_importBtn;

    @FXML
    private Button inventory_addBtn;

    @FXML
    private Button inventory_updateBtn;

    @FXML
    private Button inventory_clearBtn;

    @FXML
    private Button inventory_deleteBtn;

    @FXML
    private TextField inventory_productName;

    @FXML
    private TextField inventory_stock;

    @FXML
    private TextField inventory_price;

    @FXML
    private ComboBox<String> inventory_status;

    @FXML
    private Button tri;

    @FXML
    private ComboBox<String> inventory_type;

    @FXML
    private AnchorPane menu_form;

    @FXML
    private ScrollPane menu_scrollPane;

    @FXML
    private GridPane menu_gridPane;

    @FXML
    private TableView<Product> menu_tableView;

    @FXML
    private TableColumn<Product, String> menu_col_productName;

    @FXML
    private TableColumn<Product, String> menu_col_quantity;


    @FXML
    private TableColumn<Product, String> menu_col_price;

    @FXML
    private Label menu_total;

    @FXML
    private TextField menu_amount;

    @FXML
    private Label menu_change;

    @FXML
    private Button menu_payBtn;

    @FXML
    private Button menu_removeBtn;

    @FXML
    private Button menu_receiptBtn;

    @FXML
    private AnchorPane customers_form;

    @FXML
    private AnchorPane cat_form;

    @FXML
    private TableView<Categorie> categorie_tableView;

    @FXML
    private TableColumn<Categorie, String> cat_col_nom;

    @FXML
    private Button cat_btn;

    @FXML
    private Label dashboard_NSP;
    @FXML
    private PieChart stat;
    @FXML
    private AreaChart<?, ?> dashboard_incomeChart;

    @FXML
    private BarChart<?, ?> dashboard_CustomerChart;

    public void initialize(URL location, ResourceBundle resources) {
        connect = Database.getInstance().getConn();

        inventory_form.setVisible(true);
        inventoryTypeList();
        inventoryStatusList();
        inventoryShowData();

        showCategoriesData();
        menuDisplayCard();

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        // Get all products
        List<Product> allProducts = serviceProduct.getAll();

        // Create a map to count the occurrences of each product type
        Map<String, Integer> typeCountMap;
        typeCountMap = new HashMap<>();
        for (Product product : allProducts) {
            String type = product.getType();
            typeCountMap.put(type, typeCountMap.getOrDefault(type, 0) + 1);
        }

        // Add each type and its count to the pie chart data
        for (Map.Entry<String, Integer> entry : typeCountMap.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        stat.setData(pieChartData);
    }

    /*
        public void inventoryAddBtn() throws SQLException {
            if (inventory_productName.getText().isEmpty() || inventory_type.getValue() == null || inventory_stock.getText().isEmpty()
                    || inventory_price.getText().isEmpty() || inventory_status.getValue() == null || data.path == null) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                Product product = new Product();
                product.setProductName(inventory_productName.getText());
                product.setType(inventory_type.getValue());
                product.setStock(Integer.parseInt(inventory_stock.getText()));
                product.setPrice(Double.parseDouble(inventory_price.getText()));
                product.setStatus(inventory_status.getValue());

                // Add product:
                serviceProduct.create(product);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Added!");
                alert.showAndWait();

                inventoryShowData();
                inventoryClearBtn();
            }
            addToPieChartData(pieChartData, product.getType());
        }*/
public void inventoryAddBtn() throws SQLException {
    if (inventory_productName.getText().isEmpty() || inventory_type.getValue() == null || inventory_stock.getText().isEmpty()
            || inventory_price.getText().isEmpty() || inventory_status.getValue() == null || data.path == null) {

        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Please fill all blank fields");
        alert.showAndWait();
    } else {
        // Declaration of product variable
        Product product = new Product();
        product.setProductName(inventory_productName.getText());
        product.setType(inventory_type.getValue());
        product.setStock(Integer.parseInt(inventory_stock.getText()));
        product.setPrice(Double.parseDouble(inventory_price.getText()));
        product.setStatus(inventory_status.getValue());

        // Add product:
        serviceProduct.create(product);

        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success Message");
        alert.setHeaderText(null);
        alert.setContentText("Successfully Added!");
        alert.showAndWait();

        inventoryShowData();
        inventoryClearBtn();

        // Assuming addToPieChartData method is declared within the same class
        addToPieChartData(pieChartData, product.getType());
    }
}

    private ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();


    public void inventoryUpdateBtn() {
        if (inventory_productName.getText().isEmpty() || inventory_type.getValue() == null || inventory_stock.getText().isEmpty()
                || inventory_price.getText().isEmpty() || inventory_status.getValue() == null || data.path == null || data.id == 0) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            try {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to update Product ID: " +
                        inventory_tableView.getSelectionModel().getSelectedItem().getId() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    Product product = new Product();
                    product.setProductName(inventory_productName.getText());
                    product.setType(inventory_type.getValue());
                    product.setStock(Integer.parseInt(inventory_stock.getText()));
                    product.setPrice(Double.parseDouble(inventory_price.getText()));
                    product.setStatus(inventory_status.getValue());

                    serviceProduct.update(data.id, product);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    inventoryShowData();
                    inventoryClearBtn();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled.");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void inventoryDeleteBtn() {
        if (data.id == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete Product ID: " +
                    inventory_tableView.getSelectionModel().getSelectedItem().getId() + "?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                try {
                    String deletePanierData = "DELETE FROM panier WHERE productId = ?";
                    prepare = connect.prepareStatement(deletePanierData);
                    prepare.setInt(1, data.id);
                    prepare.executeUpdate();

                    String deleteData = "DELETE FROM product WHERE id = ?";
                    prepare = connect.prepareStatement(deleteData);
                    prepare.setInt(1, data.id);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    inventoryShowData();
                    inventoryClearBtn();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Cancelled");
                alert.showAndWait();
            }
        }
    }

    public void inventoryClearBtn() {
        inventory_productName.setText("");
        inventory_type.getSelectionModel().clearSelection();
        inventory_stock.setText("");
        inventory_price.setText("");
        inventory_status.getSelectionModel().clearSelection();
        data.path = "";
        data.id = 0;
        inventory_imageView.setImage(null);
    }

    public void inventoryImportBtn() {
        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new ExtensionFilter("Open Image File", "*png", "*jpg"));

        File file = openFile.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {
            data.path = file.getAbsolutePath();
            Image image = new Image(file.toURI().toString(), 120, 127, false, true);
            inventory_imageView.setImage(image);
        }
    }

    public ObservableList<Product> inventoryDataList() {
        ObservableList<Product> listData = FXCollections.observableArrayList();

        for (Product p : serviceProduct.getAll()) {
            listData.add(p);
        }

        return listData;
    }

    public void showCategoriesData() {
        listCat.clear();
        listCat.addAll(categoriesShowData());

        cat_col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        categorie_tableView.setItems(listCat);
    }

    public void inventoryShowData() {
        inventoryListData = inventoryDataList();

        inventory_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        inventory_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        inventory_col_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        inventory_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventory_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        //inventory_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        inventory_tableView.setItems(inventoryListData);
    }

    public void inventoryTypeList() {
        List<String> typeL = new ArrayList<>();

        for (Categorie data : serviceCategorie.getAll()) {
            typeL.add(data.getNom());
        }

        ObservableList<String> listData = FXCollections.observableArrayList(typeL);
        inventory_type.setItems(listData);
    }

    private String[] statusList = {"Available", "Unavailable"};

    public void inventoryStatusList() {
        ObservableList<String> listData = FXCollections.observableArrayList(statusList);
        inventory_status.setItems(listData);
    }

    public ObservableList<Product> menuGetData() {
        ObservableList<Product> listData = FXCollections.observableArrayList();

        for (Product p : serviceProduct.getAll()) {
            listData.add(p);
        }

        return listData;
    }

    public void menuDisplayCard() {
        cardListData.clear();
        cardListData.addAll(menuGetData());

        int row = 0;
        int column = 0;

        menu_gridPane.getChildren().clear();
        menu_gridPane.getRowConstraints().clear();
        menu_gridPane.getColumnConstraints().clear();

        for (int q = 0; q < cardListData.size(); q++) {
            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("/cardProduct.fxml"));
                AnchorPane pane = load.load();
                cardProductController cardC = load.getController();
                cardC.setData(cardListData.get(q));

                if (column == 3) {
                    column = 0;
                    row += 1;
                }

                menu_gridPane.add(pane, column++, row);

                GridPane.setMargin(pane, new Insets(10));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private ObservableList<Categorie> categoriesShowData() {
        ObservableList<Categorie> listData = FXCollections.observableArrayList();

        for (Categorie c : serviceCategorie.getAll()) {
            listData.add(c);
        }

        return listData;
    }

    private void categorieClearBtn() {
        cat_nom.setText("");
    }

    public void categoryAddBtn() throws SQLException {
        if (cat_nom.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            Categorie categorie = new Categorie();
            categorie.setNom(cat_nom.getText());

            // Add category:
            serviceCategorie.create(categorie);

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success Message");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Added!");
            alert.showAndWait();

            listCat.clear();
            listCat.addAll(serviceCategorie.getAll());
            categorie_tableView.setItems(listCat);
        }
    }

    public void categoryDeleteBtn() {
        int id = categorie_tableView.getSelectionModel().getSelectedItem().getId();
        if (id == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                try {
                    String deleteData = "DELETE FROM categorie WHERE id = ?";
                    prepare = connect.prepareStatement(deleteData);
                    prepare.setInt(1, id);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    listCat.clear();
                    listCat.addAll(serviceCategorie.getAll());
                    categorie_tableView.setItems(listCat);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Cancelled.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    void dashboard(ActionEvent event) {
    }

    @FXML
    void inventory(ActionEvent event) {
    }

    @FXML
    void menu(ActionEvent event) {
    }

    @FXML
    void customers(ActionEvent event) {
    }

    @FXML
    void logout(ActionEvent event) {
    }

    @FXML
    void switchForm(ActionEvent event) {
        if (event.getSource() == inventory_btn) {
            inventory_form.setVisible(true);
            menu_form.setVisible(false);
            customers_form.setVisible(false);
            cat_form.setVisible(false);
            inventoryTypeList();
            inventoryStatusList();
            inventoryShowData();
        } else if (event.getSource() == cat_btn) {
            cat_form.setVisible(true);
            inventory_form.setVisible(false);
            menu_form.setVisible(false);
            customers_form.setVisible(false);
            inventoryTypeList();
            showCategoriesData();
        } else if (event.getSource() == menu_btn) {
            menu_form.setVisible(true);
            inventory_form.setVisible(false);
            customers_form.setVisible(false);
            cat_form.setVisible(false);
            inventoryTypeList();
            menuDisplayCard();
        }
    }
    @FXML
    void categoriesSelectData() {
        Categorie categorie = categorie_tableView.getSelectionModel().getSelectedItem();
        if (categorie != null) {
            cat_nom.setText(categorie.getNom());
        }
    }

    public void categoryUpdateBtn() {
        if (cat_nom.getText().isEmpty()) {


            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            try {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE categorie ID: " + categorie_tableView.getSelectionModel().getSelectedItem().getId() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    Categorie categorie = new Categorie();
                    categorie.setNom(cat_nom.getText());

                    // Make sure to pass the correct arguments to the update method
                    serviceCategorie.update(categorie_tableView.getSelectionModel().getSelectedItem().getId(), categorie);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    // TO UPDATE YOUR TABLE VIEW
                    listCat.clear();
                    listCat.addAll(serviceCategorie.getAll());
                    categorie_tableView.setItems(listCat);

                    // TO CLEAR YOUR FIELDS
                    categorieClearBtn();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled.");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    Image image;
    public void inventorySelectData() {

        Product prodData = inventory_tableView.getSelectionModel().getSelectedItem();
        int num = inventory_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        inventory_productName.setText(prodData.getProductName());
        inventory_stock.setText(String.valueOf(prodData.getStock()));
        inventory_price.setText(String.valueOf(prodData.getPrice()));

        data.path = prodData.getImage();

        String path = "File:" + prodData.getImage();
        data.date = String.valueOf(prodData.getDate());
        data.id = prodData.getId();

        image = new Image(path, 120, 127, false, true);
        inventory_imageView.setImage(image);
    }



    @FXML
    void menuRemove(ActionEvent event) {
    }

    @FXML
    void menuPay(ActionEvent event) {
    }

    @FXML
    void menuReceipt(ActionEvent event) {
    }
    @FXML
    void returedash(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPersonne.fxml"));
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
    private void showAlert(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


  // @FXML
   // public void initialize() {
        // Initialize cell value factories to extract data for each column
     // id_column.setCellValueFactory(data -> data.getValue().idProperty().asObject());
     //  inventory_col_productName.setCellValueFactory(data -> data.getValue().productNameProperty());
      // inventory_col_type.setCellValueFactory(data -> data.getValue().typeProperty());
    //   inventory_col_stock.setCellValueFactory(data -> data.getValue().stockProperty().asObject());
     // inventory_col_price.setCellValueFactory(data -> data.getValue().priceProperty().asObject());
    //   inventory_col_status.setCellValueFactory(data -> data.getValue().statusProperty());
      // qrCodeColumn.setCellValueFactory(data -> data.getValue().imageProperty());
//
   // }




  //  @FXML
   // public void Tri(ActionEvent actionEvent) {
     //   List<Product> products = productService.tri();
     //   ObservableList<Product> observableList = FXCollections.observableArrayList(products);
     //   menu_tableView.setItems(observableList);
        // Assuming you have TableColumn objects defined for each property of Product
        // and setCellValueFactory configured appropriately for each column
   // }
  private void addToPieChartData(ObservableList<PieChart.Data> pieChartData, String type) {
      // Searching for existing PieChart data with the same type
      for (PieChart.Data data : pieChartData) {
          if (data.getName().equals(type)) {
              data.setPieValue(data.getPieValue() + 1);
              return;
          }
      }
      // Adding new data for new types
      pieChartData.add(new PieChart.Data(type, 1));
  }

}
