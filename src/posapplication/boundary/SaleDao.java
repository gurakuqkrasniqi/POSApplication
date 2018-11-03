/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posapplication.boundary;

import java.sql.SQLException;
import java.util.List;
import posapplication.entity.Sale;

/**
 *
 * @author Gurakuq Krasniqi
 */
public interface SaleDao {
    void create(Sale sale) throws SQLException;

    List<Sale> read() throws SQLException;

    Sale update(Sale sale) throws SQLException;

    boolean delete(Integer id) throws SQLException;

    Sale findById(Integer id) throws SQLException;
}
