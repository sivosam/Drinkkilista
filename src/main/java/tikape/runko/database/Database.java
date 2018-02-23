package tikape.runko.database;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database() throws ClassNotFoundException {
    }

    public static Connection getConnection() throws SQLException {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        if (dbUrl != null && dbUrl.length() > 0) {
            return DriverManager.getConnection(dbUrl);
        }
        File file1 = new File("db", "drinkit.db");
        return DriverManager.getConnection("jdbc:sqlite:" + file1.getAbsolutePath());
    }
//
//    public void init() {
//        List<String> lauseet = sqliteLauseet();
//
//        // "try with resources" sulkee resurssin automaattisesti lopuksi
//        try (Connection conn = getConnection()) {
//            Statement st = conn.createStatement();
//
//            // suoritetaan komennot
//            for (String lause : lauseet) {
//                System.out.println("Running command >> " + lause);
//                st.executeUpdate(lause);
//            }
//
//        } catch (Throwable t) {
//            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
//            System.out.println("Error >> " + t.getMessage());
//        }
//    }
//
//    private List<String> sqliteLauseet() {
//        ArrayList<String> lista = new ArrayList<>();
//
//        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
//        //lista.add("CREATE TABLE IF NOT EXISTS Drinkki (id integer PRIMARY KEY, nimi varchar(255));");
//        //lista.add("INSERT INTO Drinkki (nimi) VALUES ('Platon');");
//        //lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Aristoteles');");
//        //lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Homeros');");
//
//        return lista;
//    }
}
