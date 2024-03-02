package controllers;

import entities.Personne;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;


public class UserController implements Initializable {
    private Personne personne =new Personne() ;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        personne=personne.convertPresonne(personne.loadJsonFromBinFile()) ;//how get session user or admin
        System.out.println(personne.getNom());

    }
}
