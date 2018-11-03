/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posapplication.boundary;

import java.sql.SQLException;
import java.util.List;
import posapplication.entity.Furnisher;

/**
 *
 * @author Gurakuq Krasniqi
 */
public interface FurnisherDao {
    

    String create(Furnisher furnisher) throws SQLException;

    List<Furnisher> read() throws SQLException;

    Furnisher update(Furnisher furnisher) throws SQLException;

    boolean delete(String name) throws SQLException;

    Furnisher findById(String name) throws SQLException;

}
