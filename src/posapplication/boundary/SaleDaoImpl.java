/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posapplication.boundary;

import databaseutil.DBUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import posapplication.entity.Sale;

/**
 *
 * @author Gurakuq Krasniqi
 */
public class SaleDaoImpl implements SaleDao {

    private final DBUtil dbutil = DBUtil.getInstance();

    private final Connection connection = dbutil.getDbConnection();
    
    @Override
    public void create(Sale sale) throws SQLException {
         if (sale == null)
          throw new IllegalArgumentException("Sale cannot be null");

        PreparedStatement ps;
        if(sale.getCustomerNr()!=null){
            ps = connection.prepareStatement(Sale.insertStatement, Statement.RETURN_GENERATED_KEYS);
            ps.setShort(1, sale.getSaleUserId());
            ps.setInt(2, sale.getCustomerNr());
//            ps.setTimestamp(3, new Timestamp(sale.getTimeOfSale().getTime()) );
            ps.setTimestamp(3, new Timestamp(Long.parseLong(sale.getTimeOfSale())));
//            ps.setString(3, sale.getTimeOfSale());
            ps.setDouble(4, sale.getTotalPrice());
        }else{
            ps = connection.prepareStatement(Sale.insertWithoutCustomerStatement, Statement.RETURN_GENERATED_KEYS);
            ps.setShort(1, sale.getSaleUserId());
//            ps.setTimestamp(2, new Timestamp(sale.getTimeOfSale().getTime()) );
            ps.setTimestamp(2, new Timestamp(Long.parseLong(sale.getTimeOfSale())));
            ps.setDouble(3, sale.getTotalPrice());

        }
        
        


        ps.executeUpdate();
        
    }

    @Override
    public List<Sale> read() throws SQLException {
        List<Sale> saleList = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement(Sale.selectStatement);

        try (ResultSet rs = ps.executeQuery()) {

          while (rs.next()){
            Integer saleId=rs.getInt(1);  
            Short saleUserId = rs.getShort(2);
            Integer customerId = rs.getInt(3);    
            Timestamp timestamp=rs.getTimestamp(4);
            
            String date=rs.getString(4);
            
            Double total = rs.getDouble(5);
            Sale s = new Sale(saleId, saleUserId, date, total);
            s.setCustomerNr(customerId);

            saleList.add(s);
          }
        }

        return saleList;
    }

    @Override
    public Sale update(Sale sale) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Sale findById(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
