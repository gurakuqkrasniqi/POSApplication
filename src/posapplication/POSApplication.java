/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posapplication;

import databaseutil.DBUtil;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Isa Krasniqi
 */
public class POSApplication extends Application {
    
    private DBUtil dbutil = DBUtil.getInstance();
    
    public static Stage mainStageReference;

    
    public void init() throws Exception {

        // Initialize DB
        dbutil.openDbConnection();        
  }

    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        mainStageReference = primaryStage;
        
        Parent loginScene = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        
        Scene scene = new Scene(loginScene);
        Image icon = new Image(getClass().getResourceAsStream("/resources/point-of-sale-icon-10656.png"));
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Point of Sale");
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        //primaryStage.setMaxWidth(800);
       // primaryStage.setMaxHeight(600);
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        

        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void stop() throws SQLException {

        // Shutdown the DB
        dbutil.closeDbConnection();
        

  }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(POSApplication.class, args);
    }
    
}
