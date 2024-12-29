package com.raven.service;

import com.raven.connection.DatabaseConnection;
import com.raven.model.Model_Client;
import com.raven.model.Model_Login;
import com.raven.model.Model_Message;
import com.raven.model.Model_Register;
import com.raven.model.Model_User_Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ServiceUser {

    public ServiceUser() {
        this.con = DatabaseConnection.getInstance().getConnection();
    }

    public Model_Message register(Model_Register data) {
        //  Check user exit
        Model_Message message = new Model_Message();
        try {
            PreparedStatement p = con.prepareStatement(CHECK_USER);
            p.setString(1, data.getUserName());
            ResultSet r = p.executeQuery();
            if (r.first()) {
                message.setAction(false);
                message.setMessage("User Already Exit");
            } else {
                message.setAction(true);
            }
            r.close();
            p.close();
            if (message.isAction()) {
                //  Insert User Register
                con.setAutoCommit(false);
                p = con.prepareStatement(INSERT_USER, PreparedStatement.RETURN_GENERATED_KEYS);
                p.setString(1, data.getUserName());
                p.setString(2, data.getPassword());
                p.execute();
                r = p.getGeneratedKeys();
                r.first();
                int userID = r.getInt(1);
                r.close();
                p.close();
                //  Create user account
                p = con.prepareStatement(INSERT_USER_ACCOUNT);
                p.setInt(1, userID);
                p.setString(2, data.getUserName());
                p.execute();
                p.close();
                con.commit();
                con.setAutoCommit(true);
                message.setAction(true);
                message.setMessage("Ok");
                message.setData(new Model_User_Account(userID, data.getUserName(), "", "", true));
            }
        } catch (Exception e) {
            message.setAction(false);
            message.setMessage(""+e.getMessage());
            
            
            try {
                if (con.getAutoCommit() == false) {
                    con.rollback();
                    con.setAutoCommit(true);
                }
            } catch (SQLException e1) {
            }
        }
        return message;
    }

    public Model_User_Account login(Model_Login login) throws SQLException {
        Model_User_Account data = null;
        PreparedStatement p = con.prepareStatement(query);
        p.setString(1, login.getUserName());
        p.setString(2, login.getPassword());
        ResultSet r = p.executeQuery();
        if (r.first()) {
            int userID = r.getInt(1);
            String userName = r.getString(2);
            String gender = r.getString(3);
            String image = r.getString(4);
            data = new Model_User_Account(userID, userName, gender, image, true);
            System.out.println("user"+userID+""+userName);
        }
        r.close();
        p.close();
        
        return data;
        
    }

    public List<Model_User_Account> getUser(int exitUser) throws SQLException {
        List<Model_User_Account> list = new ArrayList<>();
        PreparedStatement p = con.prepareStatement(SELECT_USER_ACCOUNT);
        p.setInt(1, exitUser);
        ResultSet r = p.executeQuery();
        while (r.next()) {
            int userID = r.getInt(1);
            String userName = r.getString(2);
            String gender = r.getString(3);
            String image = r.getString(4);
            list.add(new Model_User_Account(userID, userName, gender, image, checkUserStatus(userID)));
        }
        r.close();
        p.close();
        return list;
    }

    private boolean checkUserStatus(int userID) {
        List<Model_Client> clients = Service.getInstance(null).getListClient();
        for (Model_Client c : clients) {
            if (c.getUser().getUserID() == userID) {
                return true;
            }
        }
        return false;
    }

    //  SQL
    String query = "SELECT * FROM [user] WHERE Username = ? AND password = ?";
    private final String LOGIN = "SELECT TOP 1 UserID, user_account.UserName, Gender, ImageString "+ "FROM [user] "+ "JOIN user_account ON user.UserID = user_account.UserID "
        + "WHERE user.UserName = ? COLLATE SQL_Latin1_General_CP1_CS_AS "
        + "AND user.[Password] = ? COLLATE SQL_Latin1_General_CP1_CS_AS "
        + "AND user_account.[Status] = '1'";

private final String SELECT_USER_ACCOUNT = "SELECT UserID, UserName, Gender, ImageString "
        + "FROM user_account "
        + "WHERE user_account.[Status] = '1' AND UserID <> ?";

private final String INSERT_USER = "INSERT INTO [user] (UserName, [Password]) VALUES (?, ?)";

private final String INSERT_USER_ACCOUNT = "INSERT INTO user_account (UserID, UserName) VALUES (?, ?)";

private final String CHECK_USER = "SELECT TOP 1 UserID FROM [user] WHERE UserName = ?";

    //  Instance
    private final Connection con;
}
