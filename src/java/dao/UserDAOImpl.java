/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.User;

/**
 *
 * @author sonali
 */
public class UserDAOImpl implements UserDAO {

    private static final String CONN_STR = "jdbc:derby://localhost:1527/Project353";

    public UserDAOImpl() {
    }

    public boolean persist(User user) {
        try {
            Connection conn = DBHelper.connect(CONN_STR, "itkstu", "student");
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();
            String insertString;
            insertString = "INSERT INTO project353.Users VALUES ('"
                    + user.getFirstName()
                    + "', '" + user.getLastName()
                    + "', '" + user.getUserID()
                    + "', '" + user.getEmail()
                    + "', '" + user.getSecurityQuestion()
                    + "', '" + user.getSecurityAnswer()
                    + "')";
            System.out.println("insert string =" + insertString);
            stmt.executeUpdate(insertString);

            insertString = "INSERT INTO project353.LOGININFO VALUES ('"
                    + user.getUserID()
                    + "', '" + user.getPassword()
                    + "')";
            System.out.println("insert string =" + insertString);
            stmt.executeUpdate(insertString);
            conn.commit();
            conn.close();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public boolean update(User user) {
        try {
            Connection conn = DBHelper.connect(CONN_STR, "itkstu", "student");
            String updateString;
            Statement stmt = conn.createStatement();
            updateString = "update project353.Users set ";
            updateString += (user.getFirstName().equals("") ? "" : "firstname='" + user.getFirstName() + "',");
            updateString += (user.getLastName().equals("") ? "" : "lastname='" + user.getLastName() + "',");
            updateString += (user.getEmail().equals("") ? "" : "email='" + user.getEmail() + "',");
            updateString += (user.getSecurityQuestion().equals("") ? "" : "secquestion='" + user.getSecurityQuestion() + "',");
            updateString += (user.getSecurityAnswer().equals("") ? "" : "secanswer='" + user.getSecurityAnswer() + "'");
            updateString += " where userID = '" + user.getUserID() + "'";

            System.out.println("update string =" + updateString);
            stmt.executeUpdate(updateString);

            updateString = "UPDATE project353.LOGININFO set ";
            updateString += "PASSWORD='" + user.getPassword() + "'";
            updateString += " where userID = '" + user.getUserID() + "'";

            System.out.println("update string =" + updateString);
            stmt.executeUpdate(updateString);

            conn.close();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public boolean login(User user) {
        boolean success = false;
        try {
            Connection conn = DBHelper.connect(CONN_STR, "itkstu", "student");
            String queryString = "select B.* from project353.LOGININFO A, project353.USERS B "
                    + "where A.USERID = '" + user.getUserID() + "' and A.PASSWORD = '" + user.getPassword()
                    + "'"
                    + " AND A.USERID = B.USERID";
            Statement stmt = conn.createStatement();

            System.out.println("query string =" + queryString);
            ResultSet rs = stmt.executeQuery(queryString);

            if (rs.next()) {
                user.setEmail(rs.getString("email"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setSecurityQuestion(rs.getString("SECQUESTION"));
                user.setSecurityAnswer(rs.getString("SECANSWER"));
                success = true;
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return success;
    }

    public boolean exists(User user) {
        boolean success = false;
        try {
            Connection conn = DBHelper.connect(CONN_STR, "itkstu", "student");
            String queryString = "select * from project353.Users where USERID = '" + user.getUserID() + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(queryString);
            boolean r = rs.next();
            conn.close();
            return r;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return success;
    }

}
