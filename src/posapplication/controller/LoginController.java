/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posapplication.controller;

import databaseutil.DBUtil;
import java.io.IOException;
import javafx.event.Event;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import posapplication.POSApplication;
import posapplication.boundary.UserDao;
import posapplication.boundary.UserDaoImpl;
import posapplication.entity.Useri;

/**
 * FXML Controller class
 *
 * @author Gurakuq Krasniqi
 */
public class LoginController implements Initializable {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private AnchorPane loginPane;
    @FXML
    private Label errorLabel;
    private final DBUtil dbUtil = DBUtil.getInstance();

    private final Connection connection = dbUtil.getDbConnection();
    
    public static String signedUser="";
    public static short signedUserId;
    
    PreparedStatement ps=null;
    ResultSet rs=null;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    String a;
    //Log into admin main scene or user main scene depending on role
    public void loginClicked(){
        String username=usernameField.getText().toString();
        String password=passwordField.getText().toString();
        
        
        try{
            
            
            ps=connection.prepareStatement("select * from useri where username=? and password=? "); //and user_role_id=? ... for role
            ps.setString(1,username);
            ps.setString(2, password);
            //ps.setInt(3, RoliComboBox.getSelectedIndex()+1);
            rs=ps.executeQuery();
            String roli;
            if(rs.next()){
                roli=rs.getString(4);
                signedUserId=rs.getShort(1);
                signedUser=rs.getString(2);
                rs.close();
                ps.close();
//                dbUtil.closeDbConnection();
                
                if(roli.equals("admin")){
                    FXMLLoader loader= new FXMLLoader();
                    Parent adminMainScene = loader.load(POSApplication.class.getResource("/fxml/AdminMain.fxml"));
        
                    Scene scene = new Scene(adminMainScene);
                    POSApplication.mainStageReference.setScene(scene);
                }else{
                    Parent userMainScene = FXMLLoader.load(POSApplication.class.getResource("/fxml/UserMain.fxml"));
        
                    Scene scene = new Scene(userMainScene);
                    POSApplication.mainStageReference.setScene(scene);
                }
                a="success";
                
            }else{
                a="fail";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    //Login if enter is pressed in scene
    public void enterPressed(){
        Scene sc=POSApplication.mainStageReference.getScene();
        sc.addEventFilter(KeyEvent.KEY_PRESSED, (event) -> {
            if(event.getCode()==KeyCode.ENTER){
                if(usernameField !=null && passwordField!=null)
                loginClicked();
                if(a.equals("fail"))
                    errorLabel.setText("Username or password incorrect");
                else
                    errorLabel.setText("Success login");
            }
        });
    }
    
    //Login if enter is pressed in password field
    public void passwordEnterPressed(){
        
        passwordField.addEventFilter(KeyEvent.KEY_PRESSED, (event) -> {
            if(event.getCode()==KeyCode.ENTER){
                if(!usernameField.getText().trim().toString().isEmpty() || !passwordField.getText().trim().toString().isEmpty())
                loginClicked();
                if(a.equals("fail"))
                    errorLabel.setText("Username or password incorrect");
                else
                    errorLabel.setText("Success login");
            }
        });
    }
    
    //Login if enter is pressed in username field
    public void usernameEnterPressed(){
        
        usernameField.addEventFilter(KeyEvent.KEY_PRESSED, (event) -> {
            if(event.getCode()==KeyCode.ENTER){
                if(!usernameField.getText().trim().toString().isEmpty() || !passwordField.getText().trim().toString().isEmpty())
                loginClicked();
                if(a.equals("fail"))
                    errorLabel.setText("Username or password incorrect");
                else
                    errorLabel.setText("Success login");
            }
        });
    }

}
