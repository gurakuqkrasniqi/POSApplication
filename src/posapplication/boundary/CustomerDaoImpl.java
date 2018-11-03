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
import java.util.List;
import java.util.ArrayList;
import posapplication.entity.Customer;

/**
 *
 * @author Gurakuq Krasniqi
 */
public class CustomerDaoImpl implements CustomerDao{
    private final DBUtil dbutil = DBUtil.getInstance();

    private final Connection connection = dbutil.getDbConnection();

    @Override
    public String create(Customer customer) throws SQLException {
        if(customer==null)
        throw new IllegalArgumentException("Customer cannot be null");
         PreparedStatement ps = connection.prepareStatement(Customer.insertStatement, Statement.RETURN_GENERATED_KEYS);
         ps.setString(1, customer.getFirstname());
         ps.setString(2, customer.getLastname());
         ps.setString(3, customer.getAddress());
         ps.setInt(4, customer.getCardPoints());
           ps.executeUpdate();

        try (ResultSet rs = ps.getGeneratedKeys()) {
          if (rs.next()) return rs.getString(1);
          else throw new SQLException("Failed to create Customer entry!");
        }
    }

    @Override
    public List<Customer> read() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement(Customer.selectStatement);
        
        try (ResultSet rs = ps.executeQuery()) {

            while (rs.next()){
                int customerId = rs.getInt(1);
                String firstname = rs.getString(2);
                String lastname = rs.getString(3);
                String address = rs.getString(4);
                int cardpoints = rs.getInt(5);
                  System.out.println(customerId);
                 Customer customer = new Customer(customerId,firstname,lastname,address,cardpoints);

                 customers.add(customer);
            }
        }
        return customers;
    }
    @Override
    public Customer update(Customer customer) throws SQLException {
        if (customer == null ||customer.getCustomerId()== null)
                throw new IllegalArgumentException("Customer cannot be null and cannot have an ID!");

        PreparedStatement ps = connection.prepareStatement(Customer.updateStatement);
        ps.setString(1, customer.getFirstname());
        ps.setString(2, customer.getLastname());
        ps.setString(3, customer.getAddress());
        ps.setInt(4, customer.getCardPoints());
        ps.setInt(5, customer.getCustomerId());

        ps.executeUpdate();
        return customer;
    }

    @Override
    public boolean delete(Integer customerId) throws SQLException {
        if (customerId == null)
            throw new IllegalArgumentException("CustomerId cannot be empty");

        PreparedStatement ps = connection.prepareStatement(Customer.deleteStatement);

        ps.setInt(1,customerId);
        ps.executeUpdate();

        return false;
    }

    @Override
    public Customer updateCardPoints(Customer customer) throws SQLException {
        if (customer == null ||customer.getCustomerId()== null)
                throw new IllegalArgumentException("Customer cannot be null and cannot have an ID!");

        PreparedStatement ps = connection.prepareStatement(Customer.updatePointsStatement);
        ps.setInt(1, customer.getCardPoints());
        ps.setInt(2, customer.getCustomerId());
        

        ps.executeUpdate();
        return customer;
    }
}
