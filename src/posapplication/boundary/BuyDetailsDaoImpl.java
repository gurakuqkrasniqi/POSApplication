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
import posapplication.entity.BuyDetails;

/**
 *
 * @author Gurakuq Krasniqi
 */
public class BuyDetailsDaoImpl implements BuyDetailsDao{
    
    private final DBUtil dbutil = DBUtil.getInstance();

    private final Connection connection = dbutil.getDbConnection();

    @Override
    public void create(BuyDetails buyDtl) throws SQLException {
        if(buyDtl == null) throw new IllegalArgumentException("Buy cannot be null");
        PreparedStatement ps = connection.prepareStatement(BuyDetails.insertStatement,Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, buyDtl.getBuyProdBarcode());
        ps.setString(2, buyDtl.getBuyProductName());
        ps.setShort(3, buyDtl.getQty());
        ps.setDouble(4,buyDtl.getBuyQtyValue());
        ps.setDouble(5, buyDtl.getTotalBillVal());
        
        ps.executeUpdate();
    }

    @Override
    public List<BuyDetails> read() throws SQLException {
        List<BuyDetails> buyList = new ArrayList<>();
         PreparedStatement ps = connection.prepareStatement(BuyDetails.selectStatement);
         
         try (ResultSet rs = ps.executeQuery()) {

          while (rs.next()){
              Integer buyId = rs.getInt(1);
              String buyProdBarcode = rs.getString(2);
              String buyProductName = rs.getString(3);
              Short qty = rs.getShort(4);
              Double buyQtyValue = rs.getDouble(5);
              Double totalBillVal = rs.getDouble(6);
              
              BuyDetails b = new BuyDetails(buyId, buyProdBarcode, buyProductName, qty, buyQtyValue, totalBillVal);
              
              buyList.add(b);
              
          }
         }
         return buyList;
    }
    
}
