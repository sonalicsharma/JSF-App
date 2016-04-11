/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author sonali
 */
public class DBHelper {
    public static Connection connect(String conString, String user, String password) {
        Connection conn = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        try {
            conn = DriverManager.getConnection(conString, user, password);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return conn;
    }

}
