/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 *
 * @author Isa Krasniqi
 */
public class DBUtil {
    
    public static volatile DBUtil instance;
    
    public static DBUtil getInstance() {
    if (instance == null) {
      synchronized (DBUtil.class) {
        if (instance == null) {
          instance = new DBUtil();
        }
      }
    }
    return instance;
  }
    
     private Connection connectionReference;

  /**
   * Initialize Database connection
   */
  public void openDbConnection() throws Exception {

    if (connectionReference != null) {
      throw new IllegalStateException("Cannot initialize connection twice");
    }

    try {
      Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
      String connectionUrl = "jdbc:sqlserver://localhost:7101;databaseName=posDB;user=POS_User;password=123456";

      connectionReference = DriverManager.getConnection(connectionUrl);

    } catch (SQLException sqlEx) {
      throw new IllegalStateException("Cannot initialize db connection, reason: " + sqlEx.getSQLState());
    }

  }
  
  public void closeDbConnection() throws SQLException {
      
      try{
          if(connectionReference != null && !connectionReference.isClosed())
              connectionReference.close();
      }catch(SQLException sqlEx){
       throw new IllegalStateException("Cannot shutdown db, reason: " + sqlEx.getSQLState());
      }
  }
  
  public Connection getDbConnection() {

    if (connectionReference == null)
      throw new IllegalStateException("Initialize DB Connection first!");

    return connectionReference;
  }
}
