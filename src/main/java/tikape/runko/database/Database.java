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
}
