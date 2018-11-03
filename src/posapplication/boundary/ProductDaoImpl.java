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
import posapplication.entity.Product;

/**
 *
 * @author Gurakuq Krasniqi
 */
public class ProductDaoImpl implements ProductDao {
    
    private final DBUtil dbutil = DBUtil.getInstance();

    private final Connection connection = dbutil.getDbConnection();

    @Override
    public String create(Product product) throws SQLException {
        if (product == null)
          throw new IllegalArgumentException("Product cannot be null");

        PreparedStatement ps = connection.prepareStatement(Product.insertStatement, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, product.getBarcode());
        ps.setString(2, product.getProductName());
        ps.setDouble(3, product.getSalePrice());
        ps.setDouble(4, product.getBuyPrice());
        ps.setInt(5, product.getStock());
        ps.setString(6, product.getFurnisherName());
        


        ps.executeUpdate();

        try (ResultSet rs = ps.getGeneratedKeys()) {
          if (rs.next()) return rs.getString(1);
          else throw new SQLException("Failed to create Product entry!");
        }
    }

    @Override
    public List<Product> read() throws SQLException {
        List<Product> products = new ArrayList<>();

      PreparedStatement ps = connection.prepareStatement(Product.selectStatement);

      try (ResultSet rs = ps.executeQuery()) {

      while (rs.next()){

        String barcode = rs.getString(1);
        String productName = rs.getString(2);
        double salePrice = rs.getDouble(3);
        double buyPrice = rs.getDouble(4);
        Integer stock =rs.getInt(5);
        String fName=rs.getString(6);
        
        Product product = new Product(barcode,productName,salePrice,buyPrice,stock,fName);

        products.add(product);
      }
    }

    return products;
    }

    @Override
    public Product update(Product product) throws SQLException {
        if (product == null || product.getBarcode() == null)
                throw new IllegalArgumentException("Product cannot be null and cannot have barcode null!");

            PreparedStatement ps = connection.prepareStatement(Product.updateStatement);

            ps.setDouble(1, product.getSalePrice());
            ps.setDouble(2, product.getBuyPrice());
            ps.setInt(3, product.getStock());
            ps.setString(4, product.getFurnisherName());
            ps.setString(5, product.getBarcode());

            ps.executeUpdate();

            return product;
    }

    @Override
    public boolean delete(String barcode) throws SQLException {
        if (barcode == null || barcode.trim().isEmpty())
            throw new IllegalArgumentException("Barcode cannot be empty!");

        PreparedStatement ps = connection.prepareStatement(Product.deleteStatement);

        ps.setString(1, barcode);


        ps.executeUpdate();


        return false;
    }

    @Override
    public Product findByBarcode(String barcode) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void updateStock(Integer stock, String barcode) throws SQLException{
        if (barcode == null || barcode.trim().isEmpty())
                throw new IllegalArgumentException("Barcode cannot be null!");

            PreparedStatement ps = connection.prepareStatement(Product.updateStockStatement);

            ps.setInt(1, stock);
            ps.setString(2, barcode);

            ps.executeUpdate();
    }
}
