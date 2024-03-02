package entities;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Member;
import java.util.Objects;

public class Personne {
private int id;

    private String nom;
    private  String prenom;
    private  int age;
    private  String adresse;
    private  String email;
    private  String password;
    private String token;
    private  String role;
    private  int num_tel;




    public  int getNum_tel() {
        return num_tel;
    }

    public  void setNum_tel(int num_tel) {
        this.num_tel = num_tel;
    }

    public  String getAdresse() {
        return adresse;
    }

    public  void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public  String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public  String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public  String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Personne() {

    }

    @Override
    public String toString() {
        return "Personne{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", age=" + age +
                ", adresse='" + adresse + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", role='" + role + '\'' +
                ", num_tel=" + num_tel +
                '}';
    }

    public int getId() {
        return id;
    }

    public  int getAge() {
        return age;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public Personne(int id, String nom, String prenom, int age, String adresse, String email, String password, String token, String role, int num_tel) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.adresse = adresse;
        this.email = email;
        this.password = password;
        this.token = token;
        this.role = role;
        this.num_tel = num_tel;
    }
    public Personne(String nom, String prenom, int age, String adresse, String email, String password, String token, String role, int num_tel) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.adresse = adresse;
        this.email = email;
        this.password = password;
        this.token = token;
        this.role = role;
        this.num_tel = num_tel;
    }
    public  Personne convertPresonne(String json )
    {
        Gson gson = new Gson() ;
        return gson.fromJson(json,Personne.class) ;
    }
    public  String loadJsonFromBinFile() {
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
    public  void saveJsonToBinFile(Personne personne) {

        Gson gson = new Gson();
        String json = gson.toJson(personne);
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
    public  boolean isBinFileEmpty() {
        String filePath ="Session.bin" ;
        File file = new File(filePath);
        return file.length() == 0;
    }
    public  void clearBinFile() {
        String filePath = "Session.bin" ;
        try (FileOutputStream fos = new FileOutputStream(filePath, false)) {
            // Truncate the file by opening FileOutputStream in overwrite mode (false)
            fos.getChannel().truncate(0);
            System.out.println("Binary file cleared: " + filePath);
        } catch (IOException e) {
            System.err.println("Error clearing binary file: " + e.getMessage());
        }
    }

}
