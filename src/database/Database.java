package src.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    private static final String URL = "jdbc:sqlite:src/database/akikatu.db"; // フルパスを指定
    private static final Logger LOGGER = Logger.getLogger(Database.class.getName());

    public static Connection connect() {
        Connection conn = null;
        try {
            // SQLite JDBCドライバをロード
            Class.forName("org.sqlite.JDBC");

            // データベース接続を確立
            conn = DriverManager.getConnection(URL);
            LOGGER.log(Level.INFO, "Connection to SQLite has been established.");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQLException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "ClassNotFoundException: " + e.getMessage());
        }
        return conn;
    }
}
