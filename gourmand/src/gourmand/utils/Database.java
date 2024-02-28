package gourmand.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    Connection conn;
    final String url = "jdbc:mysql://localhost:3306/mydb";
    final String user = "root";
    final String pwd = "";
    static Database instance; // behi kifh nhotlkom 5edma kol w a7d projet houni ?? jareb cnx hahi roua fared cnx jarbelh f pc mtaaha

    private Database(){
        try {
            conn = DriverManager.getConnection(url, user, pwd);
            System.out.println("connected");
        }catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
    }
    public static Database getInstance() {
        if (instance == null) {
            return instance = new Database();
        }
        return instance;
    }

    public Connection getConn() {
        return conn;
    }
}
