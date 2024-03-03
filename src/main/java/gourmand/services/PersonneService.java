package gourmand.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import gourmand.entity.Personne;
import gourmand.utils.Database;


public class PersonneService implements Service<Personne> {
    static Connection connection;
    public static int  idSessions  ;

    public PersonneService() {
        connection = Database.getInstance().getConn();
    }

    public void ajouter(Personne personne) throws SQLException {
        try {
            String req = "INSERT INTO `user` (`name`, `prenom`, `adresse`, `email`, `num_tel`, `password`, `age`, `token`, `role`) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(req);

            ps.setString(1, personne.getNom());      // Index 1 for name
            ps.setString(2, personne.getPrenom());   // Index 2 for prenom
            ps.setString(3, personne.getAdresse());  // Index 3 for adresse
            ps.setString(4, personne.getEmail());    // Index 4 for email
            ps.setInt(5, personne.getNum_tel());     // Index 5 for num_tel
            ps.setString(6, personne.getPassword()); // Index 6 for password
            ps.setInt(7, personne.getAge());         // Index 7 for age
            ps.setString(8, personne.getToken());    // Index 8 for token
            ps.setString(9, personne.getRole());     // Index 9 for role

            ps.executeUpdate();
            System.out.println("Person added successfully");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public void modifier(Personne personne) throws SQLException {
        try {
            String req = "UPDATE `user` SET `name`=?, `prenom`=?, `adresse`=?, `email`=?, `num_tel`=?, `password`=?, `age`=?, `token`=?, `role`=? WHERE `id`=?";
            PreparedStatement ps = connection.prepareStatement(req);

            ps.setString(1, personne.getNom());      // Index 1 for name
            ps.setString(2, personne.getPrenom());   // Index 2 for prenom
            ps.setString(3, personne.getAdresse());  // Index 3 for adresse
            ps.setString(4, personne.getEmail());    // Index 4 for email
            ps.setInt(5, personne.getNum_tel());     // Index 5 for num_tel
            ps.setString(6, personne.getPassword()); // Index 6 for password
            ps.setInt(7, personne.getAge());         // Index 7 for age
            ps.setString(8, personne.getToken());    // Index 8 for token
            ps.setString(9, personne.getRole());     // Index 9 for role
            ps.setInt(10, personne.getId());        // Index 10 for id

            ps.executeUpdate();
            System.out.println("Person updated successfully");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public void supprimer(int id) throws SQLException {
        try {
            String req = "DELETE FROM `user` WHERE `id`=?";
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Person with id " + id + " deleted successfully");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public List<Personne> recuperer() throws SQLException {
        List<Personne> personnes = new ArrayList<>();
        try {
            String req = "SELECT * FROM `user`";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(req);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String prenom = resultSet.getString("prenom");
                String adresse = resultSet.getString("adresse");
                String email = resultSet.getString("email");
                int num_tel = resultSet.getInt("num_tel");
                String password = resultSet.getString("password");
                int age = resultSet.getInt("age");
                String token = resultSet.getString("token");
                String role = resultSet.getString("role");

                Personne personne = new Personne(id, name, prenom, age,adresse, email, password,  token, role,num_tel);
                personnes.add(personne);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return personnes;
    }
    public Personne login(String email, String password) throws SQLException {
        String selectSql = "SELECT * FROM user WHERE email = ? AND password = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectSql)) {
            selectStatement.setString(1, email);
            selectStatement.setString(2, password);

            ResultSet resultSet = selectStatement.executeQuery();
            if (!resultSet.next()) {
                // Aucun utilisateur trouvé avec cet email et ce mot de passe
                return null;
            }
// get sision
            int Sessions = resultSet.getInt(1);
            idSessions = Sessions ;
            System.out.println(idSessions);
            // Créer et retourner un objet User avec les détails de l'utilisateur
            Personne user = new Personne();
            user.setId(resultSet.getInt("id"));
            user.setNom(resultSet.getString("name"));
            user.setPrenom(resultSet.getString("prenom"));
            user.setAge(resultSet.getInt("age"));
            user.setEmail(resultSet.getString("email"));
            user.setRole(resultSet.getString("role"));
            user.setAdresse(resultSet.getString("adresse"));
            user.setPassword(resultSet.getString("password"));
            user.setToken(resultSet.getString("token"));
            user.setNum_tel(resultSet.getInt("num_tel"));


            // ... (d'autres attributs)
            return user;
        }
    }
    public Personne getByEmail(String email) {
        try {
            String query = "SELECT * FROM utilisateur WHERE email = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, email);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        Personne resultUser = new Personne();
                        resultUser.setId(resultSet.getInt("id"));
                        resultUser.setNom(resultSet.getString("nom"));
                        resultUser.setPrenom(resultSet.getString("prenom"));
                        resultUser.setAdresse(resultSet.getString("adresse"));
                        resultUser.setEmail(resultSet.getString("email"));
                        resultUser.setNum_tel(resultSet.getInt("num_tel"));

                        resultUser.setPassword(resultSet.getString("password"));
                        resultUser.setAge(resultSet.getInt("age"));
                        resultUser.setToken(resultSet.getString("token"));
                        resultUser.setRole(resultSet.getString("role"));

                        // Ajoutez d'autres attributs si nécessaire
                        return resultUser;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Personne getBynom(String email) {
        try {
            String query = "SELECT * FROM user WHERE name like ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, "%"+email+"%");

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        Personne resultUser = new Personne();
                        resultUser.setId(resultSet.getInt("id"));
                        resultUser.setNom(resultSet.getString("name"));
                        resultUser.setPrenom(resultSet.getString("prenom"));
                        resultUser.setAdresse(resultSet.getString("adresse"));
                        resultUser.setEmail(resultSet.getString("email"));
                        resultUser.setNum_tel(resultSet.getInt("num_tel"));

                        resultUser.setPassword(resultSet.getString("password"));
                        resultUser.setAge(resultSet.getInt("age"));
                        resultUser.setToken(resultSet.getString("token"));
                        resultUser.setRole(resultSet.getString("role"));

                        // Ajoutez d'autres attributs si nécessaire
                        return resultUser;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }




}




