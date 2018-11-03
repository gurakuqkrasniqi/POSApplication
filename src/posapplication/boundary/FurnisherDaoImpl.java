/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posapplication.boundary;

import databaseutil.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import posapplication.entity.Furnisher;

/**
 *
 * @author Gurakuq Krasniqi
 */
public class FurnisherDaoImpl implements FurnisherDao{
    
    private final DBUtil dbutil = DBUtil.getInstance();

    private final Connection connection = dbutil.getDbConnection();  

  public List<Furnisher> read() throws SQLException {
    List<Furnisher> furnishers = new ArrayList<>();

    PreparedStatement ps = connection.prepareStatement(Furnisher.selectStatement);

    try (ResultSet rs = ps.executeQuery()) {

      while (rs.next()){

        String name = rs.getString(1);
        String fiscalNr = rs.getString(2);
        String phoneNumber = rs.getString(3);

        Furnisher furnisher = new Furnisher(name,fiscalNr,phoneNumber);

        furnishers.add(furnisher);
      }
    }

    return furnishers;
  }

  public Furnisher update(Furnisher furnisher) throws SQLException {

    if (furnisher == null || furnisher.getFName() == null || furnisher.getFName().trim().isEmpty())
      throw new IllegalArgumentException("Furnisher cannot be null and cannot have Id null!");

    PreparedStatement ps = connection.prepareStatement(Furnisher.updateStatement);

    ps.setString(1, furnisher.getFiscalNr());
    ps.setString(2, furnisher.getPhoneNumber());
    ps.setString(3, furnisher.getFName());

    ps.executeUpdate();

    return furnisher;
  }

  public boolean delete(String name) throws SQLException {

    if (name == null || name.trim().isEmpty())
      throw new IllegalArgumentException("Name cannot be null!");

    PreparedStatement ps = connection.prepareStatement(Furnisher.deleteStatement);

    ps.setString(1, name);

    ps.executeUpdate();

    return false;
  }

  public Furnisher findById(String name) throws SQLException {
    if (name == null || name.trim().isEmpty())
      throw new IllegalArgumentException("Name cannot be empty!");

    Furnisher furnisher = null;

    PreparedStatement ps = connection.prepareStatement(Furnisher.findByIdStatement);

    ps.setString(1, name);

    try (ResultSet rs = ps.executeQuery()) {

      boolean validate = true;

      while (rs.next()){

        if (validate) {
          String fiscalNr = rs.getString(2);
          String phoneNumber = rs.getString(3);

          furnisher = new Furnisher(name,fiscalNr,phoneNumber);

          validate = false;
        }
        else throw new SQLException("More elements found by given PK!");
      }
    }

    return furnisher;
  }    

    public String create(Furnisher furnisher) throws SQLException {
        if (furnisher == null)
      throw new IllegalArgumentException("Furnisher cannot be null");

    PreparedStatement ps = connection.prepareStatement(Furnisher.insertStatement,Statement.RETURN_GENERATED_KEYS);

    ps.setString(1, furnisher.getFName());
    ps.setString(2, furnisher.getFiscalNr());
    ps.setString(3, furnisher.getPhoneNumber());

    ps.executeUpdate();

    try (ResultSet rs = ps.getGeneratedKeys()) {
      if (rs.next()) return rs.getString(1);
      else throw new SQLException("Failed to create Furnisher entry!");
    }
    }
    
}
