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
import posapplication.entity.Sale;
import posapplication.entity.SaleDetails;

/**
 *
 * @author Gurakuq Krasniqi
 */
public class SaleDetailsDaoImpl implements SaleDetailsDao {

    private final DBUtil dbutil = DBUtil.getInstance();

    private final Connection connection = dbutil.getDbConnection();

    @Override
    public void create(SaleDetails sale) throws SQLException {
        if (sale == null)
          throw new IllegalArgumentException("User cannot be null");

        PreparedStatement ps = connection.prepareStatement(SaleDetails.insertStatement, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, sale.getSaleNumber());
        ps.setString(2, sale.getSellBarcode());
        ps.setString(3, sale.getSellProductName());
        ps.setShort(4, sale.getQty());
        ps.setDouble(5, sale.getSellQtyValue());
        ps.setDouble(6, sale.getTotal());


        ps.executeUpdate();
    }

    @Override
    public List<SaleDetails> read() throws SQLException {
        List<SaleDetails> saleDetails = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement(SaleDetails.selectStatement);

        try (ResultSet rs = ps.executeQuery()) {

          while (rs.next()){
            Integer saleNumber=rs.getInt(1);  
            String sellBarcode = rs.getString(3);
            String sellProductName = rs.getString(4);            
            Short qty = rs.getShort(5);
            Double qtyVal = rs.getDouble(6);
            Double total = rs.getDouble(7);
            SaleDetails sd = new SaleDetails(saleNumber, sellBarcode, sellProductName,qty, qtyVal, total);

            saleDetails.add(sd);
          }
        }

        return saleDetails;
        
    }
 
}
