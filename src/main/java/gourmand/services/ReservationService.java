package gourmand.services;

import gourmand.utils.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import gourmand.entity.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationService implements IService<Reservation> {
    private Connection connection;
    private PreparedStatement prepare;
    private ResultSet result;

    public ReservationService() {
        // Initialize your database connection here
        connection = Database.getInstance().getConn();
    }


    @Override
    public void create(Reservation reservation) throws SQLException {
        String query = "INSERT INTO reservation (customerName, dateTime, numberPersonnes,status,personneId) VALUES (?, ?, ?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, reservation.getCustomerName());
            statement.setTimestamp(2, Timestamp.valueOf(reservation.getDateTime()));
            statement.setInt(3, reservation.getNumberPersonnes());
            statement.setString(4,"passé");
            statement.setInt(5,reservation.getPersonneId());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                reservation.setId(generatedKeys.getInt(1));
            }
        }
    }

    @Override
    public Reservation getById(int id) throws SQLException {
        Reservation reservation = null;
        String query = "SELECT * FROM reservation WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                reservation = new Reservation(
                        resultSet.getInt("id"),
                        resultSet.getString("customer_name"),
                        resultSet.getTimestamp("reservation_datetime").toLocalDateTime(),
                        resultSet.getInt("numberPersonnes"),
                        resultSet.getString("status")

                );
            }
        }
        return reservation;
    }



    @Override
    public ObservableList<Reservation> getAll()  {
        ObservableList<Reservation> reservations = FXCollections.observableArrayList();

       try{
           String query = "SELECT * FROM reservation";
           try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
               while (resultSet.next()) {
                   Reservation reservation = new Reservation(
                           resultSet.getInt("id"),
                           resultSet.getString("customerName"),
                           resultSet.getTimestamp("dateTime").toLocalDateTime(),
                           resultSet.getInt("numberPersonnes"),
                           resultSet.getString("status"),
                           resultSet.getInt("personneId")
                   );

                   System.out.println(reservation.toString());
                   reservations.add(reservation);
               }
           }

       }catch (Exception e) {
           e.printStackTrace();
       }
        return reservations;
    }


    public ObservableList<Reservation> getAllByPer(int id) {
        ObservableList<Reservation> reservations = FXCollections.observableArrayList();

        try {
            String query = "SELECT * FROM reservation WHERE personneId = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Reservation reservation = new Reservation(
                                resultSet.getInt("id"),
                                resultSet.getString("customerName"),
                                resultSet.getTimestamp("dateTime").toLocalDateTime(),
                                resultSet.getInt("numberPersonnes"),
                                resultSet.getString("status"),
                                resultSet.getInt("personneId")
                        );
                        reservations.add(reservation);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }

    @Override
    public void update(int id, Reservation reservation) throws SQLException {
        String query = "UPDATE reservation SET customerName = ?, dateTime = ?, numberPersonnes = ? , personneId=? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, reservation.getCustomerName());
            statement.setTimestamp(2, Timestamp.valueOf(reservation.getDateTime()));
            statement.setInt(3, reservation.getNumberPersonnes());
            statement.setInt(4,reservation.getPersonneId());

            statement.setInt(5, id);

            statement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM reservation WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
