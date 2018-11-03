/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Screen;
import posapplication.POSApplication;
import posapplication.boundary.CustomerDao;
import posapplication.boundary.CustomerDaoImpl;
import posapplication.entity.Customer;

/**
 * FXML Controller class
 *
 * @author Butrint Metaj
 */
public class CustomerController implements Initializable {

    private ObservableList<Customer> customerList;
    private static CustomerDao customer;
    @FXML
    private TableView <Customer> customerTable;
    

    @FXML
    private TableColumn idColumn,fNameColumn,lNameColumn,addressColumn,cardPointsColumn;

    @FXML
    private TextField txt_customerId;

    @FXML
    private TextField txt_fname;

    @FXML
    private TextField txt_lname;

    @FXML
    private TextField txt_address;

    @FXML
    private TextField txt_cardPoints;

    @FXML
    private Button btn_addCostumer;

    @FXML
    private Button txt_editCostumer;

    @FXML
    private Button txt_deleteCostumer;
    
  @Override
    public void initialize(URL url, ResourceBundle rb) {
         Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        POSApplication.mainStageReference.setMinHeight(visualBounds.getHeight());
        POSApplication.mainStageReference.setMinWidth(visualBounds.getWidth());
        POSApplication.mainStageReference.setMaxHeight(visualBounds.getHeight());
        POSApplication.mainStageReference.setMaxWidth(visualBounds.getWidth());
        System.out.println(visualBounds.getWidth());
        System.out.println(visualBounds.getHeight());
        POSApplication.mainStageReference.setResizable(false);
        POSApplication.mainStageReference.centerOnScreen();
        // TODO
        try {
            customerTableResult();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Get results of customers from database and show them in customers table
    public void customerTableResult() throws SQLException{
        customer = (CustomerDao) new CustomerDaoImpl();
        List<Customer> list = customer.read();
        customerList = FXCollections.observableArrayList();
        String  fname,lname,address;
        Integer customerId;
        int cardPoints;
        for(int i=0; i<list.size();i++){
        customerId =list.get(i).getCustomerId();
        fname = list.get(i).getFirstname();
        lname = list.get(i).getLastname();
        address = list.get(i).getAddress();
        cardPoints=list.get(i).getCardPoints();
        
        customerList.add(new Customer(customerId, fname, lname, address, cardPoints));
        }
        System.out.println(customerList.size());

        idColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        fNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        lNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        cardPointsColumn.setCellValueFactory(new PropertyValueFactory<>("cardPoints"));
        customerTable.setItems(customerList);        
   }
    
    

    @FXML
    public  void handleAddCustomer() throws SQLException {
        if(txt_customerId==null || txt_fname == null || txt_lname == null || txt_address == null || txt_cardPoints==null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");

            // Header Text: null

            alert.setHeaderText(null);
            alert.setContentText("Fields are empty");

            alert.showAndWait();
            }
            else {
                int customerId =Integer.parseInt(txt_customerId.getText().toString());
                String firstName=txt_fname.getText().toString();
                String lastName=txt_lname.getText().toString();
                String address=txt_address.getText().toString();
                int cardPoints = Integer.parseInt(txt_cardPoints.getText().toString());
                
                boolean customerExists=false;

            
             for(int i=0;i<customerList.size();i++){
                if(customerId==customerList.get(i).getCustomerId())
                    customerExists=true;
          }
             if(customerExists==false){
               customer.create(new Customer(customerId,firstName,lastName,address,cardPoints));
                clearCustomerFields();
                 customerTable.refresh();
                    customerTableResult();
             }
             else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("");

                // Header Text: null

                alert.setHeaderText(null);
                alert.setContentText("Customer already exists!");

                alert.showAndWait();
             }
        }
        
    }
    
    //Clear customer fields
    public void clearCustomerFields(){
        txt_customerId.setText("");
        txt_fname.setText("");
        txt_lname.setText("");
        txt_address.setText("");
        txt_cardPoints.setText("");
       
        customerTable.getSelectionModel().clearSelection();

    }
    
    //Delete customer from database and refresh the table
    @FXML
    public void handleDeleteCustomer() throws SQLException {

         customer = new CustomerDaoImpl();
         int customerId = customerTable.getSelectionModel().getSelectedItem().getCustomerId();
         customer.delete(customerId);
         customerTable.refresh();
         customerTableResult();
         clearCustomerFields();
    }
    
    //Get customer table row clicked and show the results in textfields
    public void cRowClicked(){
       Customer clickedCustomer = null;
       
       if(customerTable.getSelectionModel().getSelectedItem() != null){
       clickedCustomer=customerTable.getSelectionModel().getSelectedItem();
       txt_customerId.setText(clickedCustomer.getCustomerId()+"");
       txt_fname.setText(clickedCustomer.getFirstname());
       txt_lname.setText(clickedCustomer.getLastname());
       txt_address.setText(clickedCustomer.getAddress());
       txt_cardPoints.setText(clickedCustomer.getCardPoints()+"");
       
       }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("");

                // Header Text: null

                alert.setHeaderText(null);
                alert.setContentText("Customer not selected!");

                alert.showAndWait();
        }        
   
    }
    
    //Edit customer in database and refresh the table
    @FXML
    public void handleEditCustomer() throws SQLException {
        if(customerTable.getSelectionModel().getSelectedItem()!= null){
        int customerId = Integer.parseInt(txt_customerId.getText().toString());
        String fname = txt_fname.getText().toString();
        String lname = txt_lname.getText().toString();
        String address= txt_address.getText().toString();
        int cardPoints = Integer.parseInt(txt_cardPoints.getText().toString());
        
        customer.update(new Customer(customerId, fname, lname, address, cardPoints));
        customerTable.refresh();
        customerTableResult();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");

            // Header Text: null

            alert.setHeaderText(null);
            alert.setContentText("You have not choosed customer!");

            alert.showAndWait();
        }
    }
        
    
}

