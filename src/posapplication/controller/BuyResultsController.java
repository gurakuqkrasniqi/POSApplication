/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posapplication.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import posapplication.boundary.BuyDetailsDao;
import posapplication.boundary.BuyDetailsDaoImpl;
import posapplication.entity.BuyDetails;

/**
 * FXML Controller class
 *
 * @author Gurakuq Krasniqi
 */
public class BuyResultsController implements Initializable {

    @FXML
    private TableView<BuyDetails> buyResultsTable;
    
    @FXML
    private TableColumn buyBarcodeColumn,buyProductNameColumn,buyQuantityColumn,buyPriceColumn,buyTotalPriceColumn;
    
    public ObservableList<BuyDetails> buyResultsList;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            buyResults();
        } catch (SQLException ex) {
            Logger.getLogger(BuyResultsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    //Get buy results from database and show them in table
    public void buyResults() throws SQLException{
        BuyDetailsDao buyDtlDao=new BuyDetailsDaoImpl();
        List<BuyDetails> buyList=buyDtlDao.read();
        buyResultsList=FXCollections.observableArrayList();
        for(int i=0;i<buyList.size();i++){
            if(AdminMainController.buyIdPass==buyList.get(i).getBuyId()){
                buyResultsList.add(buyList.get(i));
            }
        }
        
        buyBarcodeColumn.setCellValueFactory(new PropertyValueFactory<>("buyProdBarcode"));
        buyProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("buyProductName"));
        buyQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("qty"));
        buyPriceColumn.setCellValueFactory(new PropertyValueFactory<>("buyQtyValue"));
        buyTotalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalBillVal"));
        
        buyResultsTable.setItems(buyResultsList);
    }
}
