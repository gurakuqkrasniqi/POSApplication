/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posapplication.boundary;

import java.sql.SQLException;
import java.util.List;
import posapplication.entity.BuyDetails;

/**
 *
 * @author Gurakuq Krasniqi
 */
public interface BuyDetailsDao {
    void create(BuyDetails buyDtl) throws SQLException;    

    List<BuyDetails> read() throws SQLException;

}
