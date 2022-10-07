package config;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import env.EnviromentVariables;

import java.sql.Connection;

public class DB {

    /**
     * @author Clayton Hunter
     * 
     *         Sets up the database itself and then sets up its tables
     */
    public static void setupDatabase(String username, String password) {
        // set up the database if not exists
        try {
            Connection con = DriverManager.getConnection(EnviromentVariables.DB_URL, username, password);
            PreparedStatement pstmt = con.prepareStatement("CREATE DATABASE IF NOT EXISTS " + "webappdb");
            pstmt.executeUpdate();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // set up the tables if not exists
            setupUsersTable();
            setupPhotosTable();
        }
    }

    /**
     * Creates an Connection to the DB
     * 
     * @author Ravinder Shokar
     * @return Connection
     * @throws SQLException
     */
    public static Connection createConnection() {
        try {
            return DriverManager.getConnection(
                    EnviromentVariables.DB_WEBAPP_URL,
                    EnviromentVariables.DB_USERNAME,
                    EnviromentVariables.DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * @author Clayton Hunter
     * @return void
     */
    public static void setupUsersTable() {
        System.out.println("Setting up users table");
        Connection con = createConnection();
        try {
            // Check database for table users. If not create one
            PreparedStatement create = con.prepareStatement(
                    "CREATE TABLE if not exists users(id int NOT NULL AUTO_INCREMENT, uname varchar(20), upass varchar(30), Primary key(id))");
            create.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Failed to create users table.");
            while (ex != null) {
                System.out.println(ex.getErrorCode());
                System.out.println(ex.getSQLState());
                ex = ex.getNextException();
            }
        }
    }

    /**
     * @author Clayton Hunter
     * @return void
     */
    public static void setupPhotosTable() {
        System.out.println("Setting up photos table");

        Connection con = createConnection();
        try {
            PreparedStatement pstmt = con.prepareStatement(
                    "CREATE TABLE if not exists photos("
                            + "id int NOT NULL AUTO_INCREMENT, "
                            + "userID int NOT NULL, "
                            + "fileName varchar(20) NOT NULL, "
                            + "caption MEDIUMTEXT, "
                            + "uploadDate DATE NOT NULL, "
                            + "image BLOB NOT NULL,"
                            + "constraint PK_Photo primary key(id), "
                            + "constraint FK_ID foreign key(userID) references users(id))");
            System.out.println(pstmt.toString());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Failed to create photos table.");
            while (ex != null) {
                System.out.println(ex.getErrorCode());
                System.out.println(ex.getSQLState());
                System.out.println(ex.getMessage());
                ex = ex.getNextException();
            }
        }
    }

    /**
     * Creates an Connection to the DB
     * 
     * @author Ravinder Shokar
     * @return Connection
     * @throws SQLException
     */
    public static Connection createConnection(String url, String username, String password) throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
