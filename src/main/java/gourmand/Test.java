/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gourmand;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Test extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/LoginPersonne.fxml"));
//AfficherPersonne dash
        // page login LoginPersonne.fxml
        Scene scene = new Scene(root);

        stage.setTitle("Gourmand Vert");

        stage.setScene(scene);
        stage.show();

        // LES INTERFACES ROUA MTAR :
        // partie Client : mainClientForm.fxml
        // partie Admin  : mainForm.fxml

        // LES INTERFACES ROUA TOUIHRI :
        // partie Client : mainCommandeClientForm.fxml
        // partie Admin : mainCommandeForm.fxml

        // LES INTERFACES CHAIMA MAZIGH :
        // partie Client : mainReservationClientForm.fxml
        // partie Admin : mainReservationForm.fxml



    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
/*

package gourmand;

        import javafx.application.Application;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.stage.Stage;

public class Test extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/LoginPersonne.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("Gourmand Vert");

        stage.setScene(scene);
        stage.show();




    }


    public static void main(String[] args) {
        launch(args);
    }

}

*/
