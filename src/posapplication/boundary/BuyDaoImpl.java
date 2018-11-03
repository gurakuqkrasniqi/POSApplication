/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posapplication.boundary;

import databaseutil.DBUtil;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import posapplication.entity.Buy;
import java.sql.Connection;


/**
 *
 * @author Gurakuq Krasniqi
 */
public class BuyDaoImpl implements BuyDao{

    private final DBUtil dbutil = DBUtil.getInstance();

    private final Connection connection = dbutil.getDbConnection();
    
    @Override
    public void create(Buy buy) throws SQLException {
        if(buy==null) throw new IllegalArgumentException("Buy cannot be null");
       
        PreparedStatement ps = null;
       
      
        ps = connection.prepareStatement(Buy.insertStatement,Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, buy.getBuyId());
        ps.setString(2, buy.getBuyFurnisherName());
        ps.setShort(3, buy.getBuyUserId());
        ps.setTimestamp(4, new Timestamp(Long.parseLong(buy.getTimeOfBuy())));
        ps.setDouble(5, buy.getTotalPrice());
        ps.setBoolean(6, buy.getIsPaid());
       
        ps.executeUpdate();
        
        
    }

    @Override
    public List<Buy> read() throws SQLException {
        List<Buy> buyList = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement(Buy.selectStatement);

        try (ResultSet rs = ps.executeQuery()) {

          while (rs.next()){
            Integer buyId=rs.getInt(1);
            String furnisher=rs.getString(2);
            Short buyUserId = rs.getShort(3);
                
            Timestamp timestamp=rs.getTimestamp(4);
            
            String date=rs.getString(4);
            
            Double total = rs.getDouble(5);
            boolean isPaid=rs.getBoolean(6);
            Buy b = new Buy(buyId, buyUserId, furnisher, date, total, isPaid);
            buyList.add(b);
          }
        }

        return buyList;
    }

    @Override
    public void updateIsPaid(Integer buyId) throws SQLException {
        if (buyId == null || buyId<1)
                throw new IllegalArgumentException("Buy id cannot be null or less than zero!");

            PreparedStatement ps = connection.prepareStatement(Buy.updateIsPaidStatement);

            ps.setBoolean(1, true);
            ps.setInt(2, buyId);

            ps.executeUpdate();
    }
    
}
