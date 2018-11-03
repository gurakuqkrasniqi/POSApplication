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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import posapplication.entity.Useri;

/**
 *
 * @author Gurakuq Krasniqi
 */
public class UserDaoImpl implements UserDao {

    private final DBUtil dbutil = DBUtil.getInstance();

    private final Connection connection = dbutil.getDbConnection();



    @Override
    public Short create(Useri useri) throws SQLException {
        if (useri == null)
          throw new IllegalArgumentException("User cannot be null");

        PreparedStatement ps = connection.prepareStatement(Useri.insertStatement, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, useri.getUsername());
        ps.setString(2, useri.getPassword());
        ps.setString(3, useri.getRole());


        ps.executeUpdate();

        try (ResultSet rs = ps.getGeneratedKeys()) {
          if (rs.next()) return rs.getShort(1);
          else throw new SQLException("Failed to create Buy entry!");
        }

    }

    @Override
    public List<Useri> read() throws SQLException {
        List<Useri> users = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement(Useri.selectStatement);

        try (ResultSet rs = ps.executeQuery()) {

          while (rs.next()){
            Short userId=rs.getShort(1);  
            String username = rs.getString(2);
            String password = rs.getString(3);
            String role = rs.getString(4);

            Useri useri = new Useri(userId, username,password,role);

            users.add(useri);
          }
        }

        return users;
    }

    @Override
    public Useri update(Useri useri) throws SQLException {
            if (useri == null || useri.getUserId() == null)
                throw new IllegalArgumentException("User cannot be null and cannot have Id null!");

            PreparedStatement ps = connection.prepareStatement(Useri.updateStatement);

            ps.setString(1, useri.getUsername());
            ps.setString(2, useri.getPassword());
            ps.setString(3, useri.getRole());
            ps.setShort(4, useri.getUserId());

            ps.executeUpdate();

            return useri;
    }

    @Override
    public boolean delete(Short id) throws SQLException {

    if (id == null || id==0)
      throw new IllegalArgumentException("Id cannot be null or 0!");

    PreparedStatement ps = connection.prepareStatement(Useri.deleteStatement);

    ps.setShort(1, id);
    

    ps.executeUpdate();
        

    return false;
  }

}
