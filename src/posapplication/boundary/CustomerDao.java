/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posapplication.boundary;

import java.sql.SQLException;
import java.util.List;
import posapplication.entity.Customer;

/**
 *
 * @author Gurakuq Krasniqi
 */
public interface CustomerDao {
    
    String create(Customer customer) throws SQLException;
    List<Customer> read() throws SQLException;
    Customer update(Customer customer)  throws SQLException;
    Customer updateCardPoints(Customer customer) throws SQLException;
    boolean delete(Integer customerId)  throws SQLException;
}
