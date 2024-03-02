package test;

import entities.Personne;
import Service.PersonneService;
import com.google.gson.Gson;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

public class Test {
    public static void saveJsonToBinFile(String json) {


        String filePath="Session.bin";
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            // Convert JSON string to bytes
            byte[] jsonBytes = json.getBytes();

            // Write bytes to the file
            fos.write(jsonBytes);

            System.out.println("JSON data saved to file: " + filePath);
        } catch (IOException e) {
            System.err.println("Error saving JSON data to file: " + e.getMessage());
        }
    }
    public static Personne convertPresonne(String json )
    {
        Gson gson = new Gson() ;
        return gson.fromJson(json,Personne.class) ;
    }

    public static String loadJsonFromBinFile() {
        String filePath ="Session.bin";
        StringBuilder jsonBuilder = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            int data;
            while ((data = fis.read()) != -1) {
                jsonBuilder.append((char) data);
            }
            System.out.println("JSON data loaded from file: " + filePath);
        } catch (IOException e) {
            System.err.println("Error loading JSON data from file: " + e.getMessage());
        }
        return jsonBuilder.toString();
    }
    public static void main (String [] arg) throws SQLException {




    }
}


