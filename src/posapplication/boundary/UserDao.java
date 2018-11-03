/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posapplication.boundary;

import java.sql.SQLException;
import java.util.List;
import posapplication.entity.Useri;

/**
 *
 * @author Gurakuq Krasniqi
 */
public interface UserDao {
 
    Short create(Useri useri) throws SQLException;

  List<Useri> read() throws SQLException;

  Useri update(Useri useri) throws SQLException;

  boolean delete(Short id) throws SQLException;
    
}
