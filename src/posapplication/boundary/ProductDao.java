/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posapplication.boundary;

import java.sql.SQLException;
import java.util.List;
import posapplication.entity.Product;

/**
 *
 * @author Gurakuq Krasniqi
 */
public interface ProductDao {
    String create(Product product) throws SQLException;

    List<Product> read() throws SQLException;

    Product update(Product product) throws SQLException;

    boolean delete(String barcode) throws SQLException;

    Product findByBarcode(String barcode) throws SQLException;
    
    void updateStock(Integer stock, String barcode) throws SQLException;
}
