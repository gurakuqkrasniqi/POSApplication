/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posapplication.boundary;

import java.sql.SQLException;
import java.util.List;
import posapplication.entity.Sale;
import posapplication.entity.SaleDetails;

/**
 *
 * @author Gurakuq Krasniqi
 */
public interface SaleDetailsDao {
    void create(SaleDetails sale) throws SQLException;

    List<SaleDetails> read() throws SQLException;

}
