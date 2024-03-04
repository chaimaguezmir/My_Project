/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gourmand.entity;

import java.sql.Date;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author WINDOWS 10
 */
public class Product {

    private Integer id;
    private String productId;
    private String productName;
    private static String type;
    private Integer stock;
    private Double price;
    private String status;
    private String image;
    private Date date;
    private Integer quantity;

    public Product(Integer id, String productId,
                   String productName, String type, Integer stock,
                   Double price, String status, String image, Date date) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.type = type;
        this.stock = stock;
        this.price = price;
        this.status = status;
        this.image = image;
        this.date = date;
    }

    public Product(Integer id, String productId, String productName,
                   String type, Integer quantity, Double price, String image, Date date){
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.type = type;
        this.price = price;
        this.image = image;
        this.date = date;
        this.quantity = quantity;
    }

    public Product() {

    }

    public Integer getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public static String getType(){
        return type;
    }

    public Integer getStock() {
        return stock;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public String getImage() {
        return image;
    }

    public Date getDate() {
        return date;
    }

    public Integer getQuantity(){
        return quantity;
    }




    // tri

   // private final IntegerProperty id = new SimpleIntegerProperty();
 //   private final StringProperty productName = new SimpleStringProperty();
   //private StringProperty type = new SimpleStringProperty();
 //   private final IntegerProperty stock = new SimpleIntegerProperty();
  //  private final DoubleProperty price = new SimpleDoubleProperty();
   // private final StringProperty status = new SimpleStringProperty();
  //  private final StringProperty image = new SimpleStringProperty();
   // private final ObjectProperty<Date> date = new SimpleObjectProperty<>();



  //  public void add(Product product) {
 //   }
}
