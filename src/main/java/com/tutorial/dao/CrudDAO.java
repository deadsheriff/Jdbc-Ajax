package com.tutorial.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.tutorial.model.User;
import com.tutorial.utility.DBUtility;
public class CrudDAO {

    private Connection connection;

    public CrudDAO() {
        connection = DBUtility.getConnection();
    }

    public void addUser(User user) {
        try {

            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into tblUser(userid,firstname,lastname,email) values (?,?, ?, ? )");
            // Parameters start with 1
            preparedStatement.setInt(1, user.getUserid());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int userid) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from tblUser where userid=?");
            // Parameters start with 1
            preparedStatement.setInt(1, userid);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User user) throws ParseException {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update tblUser set lastname=?,email=?" +
                            "where userid=?");
            // Parameters start with 1
            preparedStatement.setString(1, user.getLastName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setInt(3, user.getUserid());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from tblUser");
            while (rs.next()) {
                User user = new User();
                user.setUserid(rs.getInt("userid"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setEmail(rs.getString("email"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public User getUserById(int userid) {
        User user = new User();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from tblUser where userid=?");
            preparedStatement.setInt(1, userid);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                user.setUserid(rs.getInt("userid"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));

                user.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

}