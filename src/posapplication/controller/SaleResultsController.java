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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import posapplication.boundary.SaleDetailsDao;
import posapplication.boundary.SaleDetailsDaoImpl;
import posapplication.entity.SaleDetails;

/**
 * FXML Controller class
 *
 * @author Gurakuq Krasniqi
 */
public class SaleResultsController implements Initializable {

    @FXML
    private TableView<SaleDetails> saleResultsTable;
    
    @FXML
    private TableColumn saleBarcodeColumn,saleProductNameColumn,saleQuantityColumn,salePriceColumn,saleTotalPriceColumn;
    
    @FXML
    private Label saleTotalLabel;
    
    public ObservableList<SaleDetails> saleResultsList;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            saleResults();
        } catch (SQLException ex) {
            Logger.getLogger(SaleResultsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
 
    //Get sale results from database and show them in table
    public void saleResults() throws SQLException{
        SaleDetailsDao saleDtlDao=new SaleDetailsDaoImpl();
        List<SaleDetails> saleList=saleDtlDao.read();
        saleResultsList=FXCollections.observableArrayList();
        for(int i=0;i<saleList.size();i++){
            if(AdminMainController.saleIdPass==saleList.get(i).getSaleNumber()){
                System.out.println(saleList.get(i).getSaleNumber());
                saleResultsList.add(saleList.get(i));
            }
        }
        
        saleBarcodeColumn.setCellValueFactory(new PropertyValueFactory<>("sellBarcode"));
        saleProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("sellProductName"));
        saleQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("qty"));
        salePriceColumn.setCellValueFactory(new PropertyValueFactory<>("sellQtyValue"));
        saleTotalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        
        saleResultsTable.setItems(saleResultsList);
        saleTotalLabel.setText("Total price : "+AdminMainController.totalSalePricePass);
    }
    
}
