/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posapplication.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import posapplication.POSApplication;
import posapplication.boundary.CustomerDao;
import posapplication.boundary.CustomerDaoImpl;
import posapplication.boundary.ProductDao;
import posapplication.boundary.ProductDaoImpl;
import posapplication.boundary.SaleDao;
import posapplication.boundary.SaleDaoImpl;
import posapplication.boundary.SaleDetailsDao;
import posapplication.boundary.SaleDetailsDaoImpl;
import posapplication.entity.Customer;
import posapplication.entity.Product;
import posapplication.entity.Sale;
import posapplication.entity.SaleDetails;

/**
 * FXML Controller class
 *
 * @author Gurakuq Krasniqi
 */
public class UserMainController implements Initializable {

    @FXML
    private AnchorPane parentPane,customerPane,paymentPane,infoPane;
    
    @FXML
    private Button addCustomer,addSaleProduct;
    
    @FXML
    private HBox newSaleHBox,manageCustomersHBox,finishPaymentHBox,discountHBox,logoutHBox;
    
    @FXML
    private TextField totalPriceField,cashField,changeField,barcodeField,customerIdField,firstNameField,lastNameField,quantityField;
    
    @FXML
    private TableView<SaleDetails> saleTable;
    
    @FXML
    private Label usernameLabel,dateLabel,timeLabel;
    
    @FXML
    private TableColumn barcodeCol,pNameCol,quantityCol,priceCol,totalPriceCol;
    
    @FXML
    private Pane paymentTitle;
    
    public static SaleDetailsDao saleDtlDao;
    public ObservableList<SaleDetails> sDList=FXCollections.observableArrayList();
    public ObservableList<Product> pList;
    public static int saleNumberID=1;
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date date = new Date();
    DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    Date time = new Date();
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        POSApplication.mainStageReference.setMinHeight(visualBounds.getHeight());
        POSApplication.mainStageReference.setMinWidth(visualBounds.getWidth());
        POSApplication.mainStageReference.setMaxHeight(visualBounds.getHeight());
        POSApplication.mainStageReference.setMaxWidth(visualBounds.getWidth());
        POSApplication.mainStageReference.setResizable(false);
        POSApplication.mainStageReference.centerOnScreen();
        
        usernameLabel.setText(LoginController.signedUser+"");
        dateLabel.setText(dateFormat.format(date));
        Timeline clock = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {        
            int second = LocalDateTime.now().getSecond();
            int minute = LocalDateTime.now().getMinute();
            int hour = LocalDateTime.now().getHour();
            String m=minute+"";
            String h=hour+"";
            String s=second+"";
            if(minute<10){
                m="0"+minute;
            }
            if(hour<10)
                h="0"+hour;
            if(second<10)
                s="0"+second;


            timeLabel.setText(h + ":" + (m) + ":" + s);
        }),
            new KeyFrame(javafx.util.Duration.seconds(1))
         );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
        timeLabel.setText(timeFormat.format(time));
        newSaleHBox.setStyle("-fx-background-color: #dae9ff");
        manageCustomersHBox.setStyle("-fx-background-color: #dae9ff");
        finishPaymentHBox.setStyle("-fx-background-color: #dae9ff");
        discountHBox.setStyle("-fx-background-color: #dae9ff");
        logoutHBox.setStyle("-fx-background-color: #dae9ff");
        customerPane.setStyle("-fx-border-color: #C0C0C0");
        customerPane.setStyle("-fx-background-color: #dae9ff");
        paymentTitle.setStyle("-fx-border-color: #C0C0C0");        
        paymentTitle.setStyle("-fx-background-color: #dae9ff");
        paymentPane.setStyle("-fx-border-color: #dae9ff");
        infoPane.setStyle("-fx-background-color: #dae9ff");
        totalPriceField.setEditable(false);
        
      
    }    
    
    
    
    //Get back to the login scene if logout HBox is clicked
    public void logoutClicked() throws IOException{
        Parent loginScene = FXMLLoader.load(POSApplication.class.getResource("/fxml/Login.fxml"));
        
        Scene scene = new Scene(loginScene);
        POSApplication.mainStageReference.setScene(scene);
        POSApplication.mainStageReference.setMinWidth(800);
        POSApplication.mainStageReference.setMinHeight(600);
        POSApplication.mainStageReference.setMaxWidth(800);
        POSApplication.mainStageReference.setMaxHeight(600);
        POSApplication.mainStageReference.setResizable(false);
        POSApplication.mainStageReference.centerOnScreen();
        
        
    }
    
    //Get customers results from database and show them in a new window
    public void manageCustomers() throws IOException{
        AnchorPane customerScene = FXMLLoader.load(POSApplication.class.getResource("/fxml/Customer.fxml"));
        
        Stage stage = new Stage();
        stage.setTitle("Manage customers");
        stage.setScene(new Scene(customerScene, 1100, 600));
        Image icon = new Image(getClass().getResourceAsStream("/resources/point-of-sale-icon-10656.png"));
        stage.getIcons().add(icon);
        stage.show();        
       
    }
 
    //Get the amount of cash change when enter is pressed in cash field after writing the amount of cash
    public void paymentFinish(){
        if(!totalPriceField.getText().trim().isEmpty() && totalPriceField != null){
            cashField.setOnKeyPressed(new EventHandler<KeyEvent>(){
                @Override
                public void handle(KeyEvent event) {
                    if(event.getCode()==KeyCode.ENTER){
                        if(cashField.getText().toString().isEmpty()){
                            showAlert("Cannot finish payment:\nPaid cash field is empty!");
                        }else if(Double.parseDouble(cashField.getText().toString())<Double.parseDouble(totalPriceField.getText().toString())){
                            showAlert("Cannot finish payment:\nAmount of paid cash is lower than total price of sale");
                        }else{
                        changeButton();
                        }                    
                    }
                }
            });
        }
    }
    
    //Set the amount of change in change field 
    public void changeButton(){
        if(totalPriceField != null){
            double totali=Double.parseDouble(totalPriceField.getText().toString());
            double cash=Double.parseDouble(cashField.getText().toString());

            double change=cash-totali;
            NumberFormat nf= new DecimalFormat("#0.00");
            Double changeFormat=Double.parseDouble(nf.format(change));
            changeField.setText(changeFormat+"");
        }
    }
    
    Double totalP;
    Short qty=1;
    //Add product to sale
    public void addSaleProd() throws SQLException{
        saleTable();        
        saleTable.setItems(sDList);
        totalP=0.0;
        saleTable.refresh();
        
        for(int i=0;i<sDList.size();i++){
            totalP+=sDList.get(i).getTotal();
            
        }
        NumberFormat nf= new DecimalFormat("#0.00");
        Double totalFormat=Double.parseDouble(nf.format(totalP));
        if(!sDList.isEmpty())
        totalPriceField.setText(totalFormat+"");
    }
    
    Short quantity=1;
    SaleDetails sd=null;
    //Get results of products in database and add the product that cashier wants
    public void saleTable() throws SQLException{
        ProductDao product=(ProductDao) new ProductDaoImpl();
        List<Product> list=product.read();
        String barcode = null,productName = null;
        Short qt=2;
        Double salePrice = null;
        Double total = null;
        boolean exists=false;
        for(int i=0;i<list.size();i++){
            if(barcodeField.getText().toString().equals(list.get(i).getBarcode())){
                barcode=list.get(i).getBarcode();
                productName=list.get(i).getProductName();
                salePrice=list.get(i).getSalePrice();
                
                total=list.get(i).getSalePrice()*quantity;
                exists=true;
            }
            
            
        }
        
        if(exists){
        
        boolean duplicate=false;
        //Add data. Check first if personData is empty
        NumberFormat nf= new DecimalFormat("#0.00");
        Double totalFormat=Double.parseDouble(nf.format(total));
        if (sDList.isEmpty()){
            sd = new SaleDetails(saleNumberID,barcode,productName,quantity,salePrice,totalFormat);
            sDList.add(sd);
        }else{
            for(int i = 0 ; i<sDList.size() ; i ++){
                 if(barcodeField.getText().toString().equals(sDList.get(i).getSellBarcode())){
                    duplicate = true;
                    
                 }
            }
            
            if(duplicate == false){
                sd = new SaleDetails(saleNumberID,barcode,productName,quantity,salePrice,totalFormat);
                sDList.add(sd);                        
            }else{
                
                for(int i=0;i<sDList.size();i++){
                    if(sDList.get(i).getSellBarcode().equals(barcodeField.getText().toString())){
                        Short t=sDList.get(i).getQty();
                        t++;
                        sDList.get(i).setQty(t);
                        Double totalPriceFormat=Double.parseDouble(nf.format(t*sDList.get(i).getSellQtyValue()));
                        sDList.get(i).setTotal(totalPriceFormat);                                   
                    }                    
                }
            }

        }        
            barcodeCol.setCellValueFactory(new PropertyValueFactory<>("sellBarcode"));
            pNameCol.setCellValueFactory(new PropertyValueFactory<>("sellProductName"));
            quantityCol.setCellValueFactory(new PropertyValueFactory<>("qty"));
            priceCol.setCellValueFactory(new PropertyValueFactory<>("sellQtyValue"));
            totalPriceCol.setCellValueFactory(new PropertyValueFactory<>("total"));
        }else{
            showAlert("Product does not exists");
            
        }
        
        
    }
    
    //Reset the user main scene components to empty if new sale is clicked 
    public void newSale(){
        barcodeField.setText("");
        for(int i=0;i<sDList.size();i++){
            sDList.remove(i);
            newSale();
        }
        
        totalPriceField.setText("");
        cashField.setText("");
        changeField.setText("");
        
    }
    
    //Add product to sale by pressing enter when barcode is written
    public void barcodeFieldEnter(){
        barcodeField.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode()==KeyCode.ENTER){
                    try {
                        addSaleProd();
                    } catch (SQLException ex) {
                        Logger.getLogger(UserMainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                
            }
        });
    }
    
    //Finish payment and add data to database
    public void finishPayment() throws SQLException{
        SaleDao saleDao=new SaleDaoImpl();
        int lastSaleNumber;
        
        saleDtlDao=(SaleDetailsDao) new SaleDetailsDaoImpl();
        List<SaleDetails> saleDtlList=saleDtlDao.read();
        if(saleDtlList.size()==0){
            lastSaleNumber=1;
        }else{
            lastSaleNumber=saleDtlList.get(saleDtlList.size()-1).getSaleNumber();
            lastSaleNumber++;
        }
        if(sDList.isEmpty()){
            
            showAlert("There is no product in table to be sold");
            
        }else{
            
            if(cashField.getText().toString().isEmpty()){
                showAlert("Cannot finish payment:\nPaid cash field is empty!");
            }else if(Double.parseDouble(cashField.getText().toString())<Double.parseDouble(totalPriceField.getText().toString())){
                showAlert("Cannot finish payment:\nAmount of paid cash is lower than total price of sale");
            }else{
                CustomerDao customer=(CustomerDao) new CustomerDaoImpl();
                List<Customer> customerList=customer.read();
                ProductDao prod=new ProductDaoImpl();
                List<Product> productList=prod.read();
                boolean customerExists=false;
                Customer tempCustomer=null;
                for(int i=0;i<customerList.size();i++){
                    if(customerIdField.getText().trim().isEmpty() || customerIdField == null || Integer.parseInt(customerIdField.getText().toString()) == customerList.get(i).getCustomerId())
                        customerExists=true;
                    if(customerIdField!=null || Integer.parseInt(customerIdField.getText().toString()) == customerList.get(i).getCustomerId())
                        tempCustomer=customerList.get(i);
                    
                }
                if(customerExists){
                    Integer updateStock=null;
                    for(int i=0;i<sDList.size();i++){
                        sDList.get(i).setSaleNumber(lastSaleNumber);
                        saleDtlDao.create(sDList.get(i));
                        for(int j=0;j<productList.size();j++){
                            if(sDList.get(i).getSellBarcode().equals(productList.get(j).getBarcode()))
                                updateStock=productList.get(j).getStock()-sDList.get(i).getQty();
                        }
                        prod.updateStock(updateStock,sDList.get(i).getSellBarcode());

                    }
                    // duhet me e kqyr a ekziston klienti me qat id tani me perdor finish payment nese nuk ekziston e qet ni alert
                    Date d=new Date();
                    DateFormat df=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Sale sale=new Sale(lastSaleNumber, LoginController.signedUserId, d.getTime()+"", Double.parseDouble(totalPriceField.getText().toString()));
                    if(customerIdField.getText().trim().isEmpty() || customerIdField==null){
                    saleDao.create(sale);
                    }else{
                    sale.setCustomerNr(Integer.parseInt(customerIdField.getText().toString()));
                    saleDao.create(sale);
                    }
                    if(customerIdField!=null || !customerIdField.getText().trim().isEmpty())
                    tempCustomer.setCardPoints((int) (tempCustomer.getCardPoints()+Double.parseDouble(totalPriceField.getText().toString())));
                    customer.updateCardPoints(tempCustomer);
                    showAlert("Payment finished successfully");
                    customerIdField.setText("");
                    firstNameField.setText("");
                    lastNameField.setText("");
                    newSale();
                }else{
                    showAlert("Customer with this id doesnt exists");
                }
            }
        
        }
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
    
    //Customer card points
    public void customerPoints() throws SQLException{
        CustomerDao customer=(CustomerDao) new CustomerDaoImpl();
        List<Customer> customerList=customer.read();
        Integer customerId;
        
        
        customerIdField.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode()==KeyCode.ENTER){
                    String firstname = null;
                    String lastname = null;
                    boolean ekziston=false;
                    Integer tempId = null;
                    
                    for(int i=0;i<customerList.size();i++){
                        tempId=customerList.get(i).getCustomerId();
                        if(Integer.parseInt(customerIdField.getText().toString())==tempId){
                            ekziston=true;
                            firstname=customerList.get(i).getFirstname();
                            lastname=customerList.get(i).getLastname();
                        }
                            
                    }
                    if(ekziston){
                        firstNameField.setText(firstname);
                        lastNameField.setText(lastname);
                    }else{
                        showAlert("Client with id: "+customerIdField.getText().toString()+" does not exist");
                    }
                }
                
            }
        });
        
    }
    
    //Get 3$ discount for loyal customer who has at least 1000 card points
    public void loyaltyDiscount() throws SQLException{
        String customerId=customerIdField.getText().toString();
        if(customerId.trim().isEmpty() || customerIdField==null){
            showAlert("Customer id field is empty!");
        }else{
            if(totalPriceField==null || totalPriceField.getText().toString().trim().isEmpty()){
                showAlert("Cannot get discount without starting a sale!");
            }else{
                CustomerDao customer=(CustomerDao) new CustomerDaoImpl();
                List<Customer> customerList=customer.read();
                boolean exists=false;
                boolean hasDiscount=false;
                Customer tempCustomer=null;
                for(int i=0;i<customerList.size();i++){
                    if(Integer.parseInt(customerId)==customerList.get(i).getCustomerId()){
                        tempCustomer=customerList.get(i);
                        exists=true;
                    }
                }
                if(tempCustomer.getCardPoints()>=1000){
                    tempCustomer.setCardPoints(tempCustomer.getCardPoints()-1000);
                    double discount = Double.parseDouble(totalPriceField.getText().toString())-3;
                    totalPriceField.setText(discount+"");
                    customer.updateCardPoints(tempCustomer);
                }else{
                    showAlert("Customer must have at least 1000 card points to get the discount!");
                }
            }
        }
    }
    
    //Open a new window where cashier can buy products that are low in stock or not
    public void buyProducts() throws IOException{
        
        AnchorPane buyScene = FXMLLoader.load(POSApplication.class.getResource("/fxml/BuyProducts.fxml"));
        
        Stage stage = new Stage();
        
        stage.setTitle("Buy products");
        stage.setScene(new Scene(buyScene, 1100, 600));
        Image icon = new Image(getClass().getResourceAsStream("/resources/point-of-sale-icon-10656.png"));
        stage.getIcons().add(icon);
        stage.show();        
       
    
    }
    
    //Get product quantity in sale table
    public void getProductQuantity(){
        quantityField.setText(saleTable.getSelectionModel().getSelectedItem().getQty()+"");
        
    }
    Double totali = 0.0;
    //Set new quantity of sale table row if enter is pressed
    public void quantityFieldEnter(){
        if(!quantityField.getText().toString().trim().isEmpty()){
            quantityField.setOnKeyPressed(new EventHandler<KeyEvent>(){
                @Override
                public void handle(KeyEvent event) {
                    if(event.getCode()==KeyCode.ENTER){
                        NumberFormat nf= new DecimalFormat("#0.00");
                        Short quantityAdd=Short.parseShort(quantityField.getText().toString());
                        saleTable.getSelectionModel().getSelectedItem().setQty(quantityAdd);
                        Double finalTotal=Double.parseDouble(nf.format(saleTable.getSelectionModel().getSelectedItem().getSellQtyValue()*saleTable.getSelectionModel().getSelectedItem().getQty()));
                        
                        saleTable.getSelectionModel().getSelectedItem().setTotal(finalTotal);
                        
                        saleTable.refresh();
                        quantityField.setText("");
                        
                        totali=0.0;
                        for(int i=0;i<sDList.size();i++){
                            totali+=sDList.get(i).getTotal();
                            System.out.println(sDList.get(i).getTotal());
                        }
                        Double totalFormat=Double.parseDouble(nf.format(totali));
                        if(!sDList.isEmpty())
                        totalPriceField.setText(totalFormat+"");

                    }

                }
            });
        }
    }
    
    Double totalD=0.0;
    //Delete a product from sale
    public void deleteProduct(){
        saleTable.getItems().remove(saleTable.getSelectionModel().getSelectedItem());
        saleTable.refresh();
        totalD=0.0;
        for(int i=0;i<sDList.size();i++){
            totalD+=sDList.get(i).getTotal();
            System.out.println(sDList.get(i).getTotal());
        }
        NumberFormat nf= new DecimalFormat("#0.00");

        Double totalFormat=Double.parseDouble(nf.format(totalD));
        if(!sDList.isEmpty())
        totalPriceField.setText(totalFormat+"");
        else
            totalPriceField.setText("");
    }
}
