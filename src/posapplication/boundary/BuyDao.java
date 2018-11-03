/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posapplication.boundary;

/**
 *
 * @author Gurakuq Krasniqi
 */
import java.sql.SQLException;
import java.util.List;
import posapplication.entity.Buy;

public interface BuyDao {

  void create(Buy buy) throws SQLException;

  List<Buy> read() throws SQLException;
  
  void updateIsPaid(Integer buyId) throws SQLException;
}
