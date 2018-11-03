/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posapplication.controller;

import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import posapplication.boundary.BuyDao;
import posapplication.boundary.BuyDaoImpl;
import posapplication.boundary.BuyDetailsDao;
import posapplication.boundary.BuyDetailsDaoImpl;
import posapplication.boundary.ProductDao;
import posapplication.boundary.ProductDaoImpl;
import static posapplication.controller.UserMainController.saleNumberID;
import posapplication.entity.Buy;
import posapplication.entity.BuyDetails;
import posapplication.entity.Product;
import posapplication.entity.SaleDetails;

/**
 * FXML Controller class
 *
 * @author Gurakuq Krasniqi
 */
public class BuyProductsController implements Initializable {

    @FXML
    private TableView<BuyDetails> buyTable;
    @FXML
    private TableColumn barcodeColumn,productColumn,qtyColumn,priceColumn,totalPriceColumn;
    @FXML
    private TableView<Product> lowStockTable;
    @FXML
    private TableColumn lowProductNameColumn,lowStockColumn;
    @FXML
    private TextField barcodeField;
    @FXML
    private Button buy_button;
    @FXML
    private TextField buy_total,addQuantityField;
    @FXML
    private Button finishBuy;

    public static BuyDetailsDao buyDtlDao;
    public ObservableList<BuyDetails> bDList=FXCollections.observableArrayList();
    public ObservableList<Product> productOList=FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            lowStockProducts();
        } catch (SQLException ex) {
            Logger.getLogger(BuyProductsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    Short quantity=1;
    BuyDetails bd=null;
    //Get products results from database and add them to the table
    public void buyTable() throws SQLException{
        ProductDao product=(ProductDao) new ProductDaoImpl();
        List<Product> list=product.read();
        String barcode = null,productName = null;
        Short qt=2;
        Double buyPrice = null;
        Double total = null;
        boolean ekziston=false;
        for(int i=0;i<list.size();i++){
            if(barcodeField.getText().toString().equals(list.get(i).getBarcode())){
                barcode=list.get(i).getBarcode();
                productName=list.get(i).getProductName();
                buyPrice=list.get(i).getBuyPrice();
                
                total=list.get(i).getBuyPrice()*quantity;
                ekziston=true;
            }  
        }
        
        if(ekziston){
        
        boolean duplicate=false;
        
        NumberFormat nf= new DecimalFormat("#0.00");
        Double totalFormat=Double.parseDouble(nf.format(total));
        if (bDList.isEmpty()){
            bd = new BuyDetails(barcode, productName, quantity, buyPrice, totalFormat);
            bDList.add(bd);
        }else{
            for(int i = 0 ; i<bDList.size() ; i++){
                 if(barcodeField.getText().toString().equals(bDList.get(i).getBuyProdBarcode())){
                    duplicate = true;
                 }
            }
            System.out.println(duplicate);
            if(duplicate == false){
                bd = new BuyDetails(barcode, productName, quantity, buyPrice, totalFormat);
                bDList.add(bd);                        
            }else{
                for(int i=0;i<bDList.size();i++){
                    if(bDList.get(i).getBuyProdBarcode().equals(barcodeField.getText().toString())){
                        Short t=bDList.get(i).getQty();
                        t++;
                        bDList.get(i).setQty(t);
                        Double totalPriceFormat=Double.parseDouble(nf.format(t*bDList.get(i).getBuyQtyValue()));
                        bDList.get(i).setTotalBillVal(totalPriceFormat);                                   
                    }                    
                }
            }

        }        
            barcodeColumn.setCellValueFactory(new PropertyValueFactory<>("buyProdBarcode"));
            productColumn.setCellValueFactory(new PropertyValueFactory<>("buyProductName"));
            qtyColumn.setCellValueFactory(new PropertyValueFactory<>("qty"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("buyQtyValue"));
            totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalBillVal"));
        }else{
            showAlert("Product does not exists");
            
        }
        addQuantityField.setText("");
    }
    
    //Alert dialog with message parameter
    public void showAlert(String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");

        // Header Text: null

        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
    }
    
    //Add product to buy table
    public void addBuyProd() throws SQLException{
        buyTable();        
        buyTable.setItems(bDList);
        buyTable.refresh();
    }
    
    //Finish buy and insert data to the database
    public void finishBuy() throws SQLException{
        buyDtlDao=new BuyDetailsDaoImpl();
        BuyDao buyDao=new BuyDaoImpl();
        boolean isPaid=false;
        if(bDList.isEmpty()){
            
            showAlert("There is no product in table to be bought");
            
        }else{
            
            ProductDao productDao=new ProductDaoImpl();
            List<Product> productList=productDao.read();
            List<BuyDetails> buyList;
            Integer updateStock=null;
            String furnisher=null;
            Double totalPrice=null;
            Integer tempId=null;
            for(int i=0;i<bDList.size();i++){
                
                buyDtlDao.create(bDList.get(i));
                buyList=buyDtlDao.read();
                tempId=buyList.get(buyList.size()-1).getBuyId();
                for(int j=0;j<productList.size();j++){
                    if(bDList.get(i).getBuyProdBarcode().equals(productList.get(j).getBarcode())){
                        updateStock=productList.get(j).getStock()+bDList.get(i).getQty();
                        furnisher=productList.get(j).getFurnisherName();
                        totalPrice=bDList.get(i).getTotalBillVal();
                    }

                }
                productDao.updateStock(updateStock,bDList.get(i).getBuyProdBarcode());
                Date d=new Date();
                DateFormat df=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Buy buy=new Buy(tempId,LoginController.signedUserId, furnisher, d.getTime()+"", totalPrice, isPaid );
                buyDao.create(buy);

            }
            // duhet me e kqyr a ekziston klienti me qat id tani me perdor finish payment nese nuk ekziston e qet ni alert
            
            showAlert("Payment finished successfully");
            barcodeField.setText("");
            bDList.clear();
            buyTable.refresh();
                   
        }
        productOList.clear();
        lowStockProducts();
        lowStockTable.refresh();
        addQuantityField.setText("");
    }
    
    //Show low stock products
    public void lowStockProducts() throws SQLException{
        ProductDao productDao=new ProductDaoImpl();
        List<Product> productList=productDao.read();
        
        for(int i=0;i<productList.size();i++){
            if(productList.get(i).getStock()<100){
                productOList.add(productList.get(i));
            }
        }
        
        lowProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        lowStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        
        lowStockTable.setItems(productOList);
        
        
    }
    
    Short qty=1;
    //Add products from low stock table to buy table 
    public void addProductsToBuy(){
        if(lowStockTable.getSelectionModel().getSelectedItem()==null){
            showAlert("No low stock product is selected!");
        }else{
            Product p=lowStockTable.getSelectionModel().getSelectedItem();
            String barcode=p.getBarcode();
            String productName=p.getProductName();

            Double buyPrice=p.getBuyPrice();
            Double total=p.getBuyPrice()*qty;
            boolean duplicate=false;
            NumberFormat nf= new DecimalFormat("#0.00");
            Double totalFormat=Double.parseDouble(nf.format(total));
            if (bDList.isEmpty()){
                bd = new BuyDetails(barcode, productName, qty, buyPrice, totalFormat);
                bDList.add(bd);
            }else{
                for(int i = 0 ; i<bDList.size() ; i++){
                     if(barcode.equals(bDList.get(i).getBuyProdBarcode())){
                        duplicate = true;
                     }
                }

                if(duplicate == false){
                    bd = new BuyDetails(barcode, productName, qty, buyPrice, totalFormat);
                    bDList.add(bd);                        
                }else{
                    for(int i=0;i<bDList.size();i++){
                        if(bDList.get(i).getBuyProdBarcode().equals(barcode)){
                            Short t=bDList.get(i).getQty();
                            t++;
                            bDList.get(i).setQty(t);
                            Double totalPriceFormat=Double.parseDouble(nf.format(t*bDList.get(i).getBuyQtyValue()));
                            bDList.get(i).setTotalBillVal(totalPriceFormat);                                   
                        }                    
                    }
                }

            }        
                barcodeColumn.setCellValueFactory(new PropertyValueFactory<>("buyProdBarcode"));
                productColumn.setCellValueFactory(new PropertyValueFactory<>("buyProductName"));
                qtyColumn.setCellValueFactory(new PropertyValueFactory<>("qty"));
                priceColumn.setCellValueFactory(new PropertyValueFactory<>("buyQtyValue"));
                totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalBillVal"));
                buyTable.setItems(bDList);
                buyTable.refresh();
        }
    }
    
    //Get buy product quantity
    public void getProductQuantity(){
        addQuantityField.setText(buyTable.getSelectionModel().getSelectedItem().getQty()+"");
        
    }
    
    //Set product quantity if enter is pressed
    public void quantityFieldEnter(){
        if(!addQuantityField.getText().toString().trim().isEmpty()){
            addQuantityField.setOnKeyPressed(new EventHandler<KeyEvent>(){
                @Override
                public void handle(KeyEvent event) {
                    if(event.getCode()==KeyCode.ENTER){
                        Short quantityAdd=Short.parseShort(addQuantityField.getText().toString());
                        buyTable.getSelectionModel().getSelectedItem().setQty(quantityAdd);
                        buyTable.getSelectionModel().getSelectedItem().setTotalBillVal(buyTable.getSelectionModel().getSelectedItem().getBuyQtyValue()*buyTable.getSelectionModel().getSelectedItem().getQty());
                        buyTable.refresh();
                        addQuantityField.setText("");

                    }

                }
            });
        }
    }
    
    
    //Add product to buy table if enter is pressed
    public void barcodeFieldEnter(){
        barcodeField.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode()==KeyCode.ENTER){
                    buy_button.fire();
                    
                }
                
            }
        });
    }
    
    //Delete product from buy table
    public void deleteProduct(){
        buyTable.getItems().remove(buyTable.getSelectionModel().getSelectedItem());
        buyTable.refresh();
    }
    
}

