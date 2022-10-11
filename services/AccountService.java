package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import config.DB;

public class AccountService {

    public static boolean insertIntoUsers(String username, String password) {
        try {
            Connection con = DB.createConnection();
            PreparedStatement insert = con.prepareStatement("INSERT INTO users(uname, upass) values(?, ?) ");
            insert.setString(1, username);
            insert.setString(2, password);
            insert.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean checkIfUserNameExists(String username) {
        Connection con = DB.createConnection();
        try {
            // see if the account is in the users table
            PreparedStatement check = con.prepareStatement(
                    "SELECT * FROM users WHERE uname ='" + username + "';");
            ResultSet rs = check.executeQuery();
            if (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean checkCredentials(String username, String password) {
        try {
            ResultSet rs = getUserResultSet(username, password);
            if (rs != null) {
                if (rs.next()) {
                    return true;
                }
            }
            return false;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static String getUserId(String username, String password) {
        try {
            ResultSet rs = getUserResultSet(username, password);
            if (rs != null) {
                if (rs.next()) {
                    String id = rs.getString("id");
                    return id;
                }
            }
            return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static ResultSet getUserResultSet(String username, String password) {
        Connection con = DB.createConnection();
        try {
            // see if the account is in the users table
            PreparedStatement check = con.prepareStatement(
                    "SELECT * FROM users WHERE uname ='" + username + "' and upass ='" + password + "';");
            ResultSet rs = check.executeQuery();
            return rs;
        } catch (SQLException ex) {
            System.out.println("Failed to search for an account.");
            return null;
        }
    }

    public static boolean isLoggedIn(HttpServletRequest req) {
        HttpSession session = req.getSession(false);

        if (session == null || !req.isRequestedSessionIdValid()) {
            return false;
        } else {
            return true;
        }

    }

}