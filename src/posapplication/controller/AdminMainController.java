/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posapplication.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import posapplication.POSApplication;
import posapplication.boundary.BuyDao;
import posapplication.boundary.BuyDaoImpl;
import posapplication.boundary.FurnisherDao;
import posapplication.boundary.FurnisherDaoImpl;
import posapplication.boundary.ProductDao;
import posapplication.boundary.ProductDaoImpl;
import posapplication.boundary.SaleDao;
import posapplication.boundary.SaleDaoImpl;
import posapplication.boundary.UserDao;
import posapplication.boundary.UserDaoImpl;
import posapplication.entity.Buy;
import posapplication.entity.Furnisher;
import posapplication.entity.Product;
import posapplication.entity.Sale;
import posapplication.entity.Useri;


/**
 * FXML Controller class
 *
 * @author Gurakuq Krasniqi
 */
public class AdminMainController implements Initializable {
    
    @FXML
    private Label AdminPanel,welcomeLabel;
    
    @FXML
    private TextField newUsername,newPassword;
    
    @FXML
    private AnchorPane parentPane,AnchorPane,userPane;
    
    @FXML
    private SplitPane splitPane;
    
    @FXML
    private ListView listView;
    
    @FXML
    private AnchorPane dashPane,mainPane,furnisherPane,productPane,reportsPane;
    
    @FXML
    private Separator anchorSplit;
    
    @FXML
    private Button logoutButton,addUserButton,refreshTable,deleteUser,saleResultsButton,buyResultsButton,finishPayment;
    
    @FXML
    private HBox userHBox,furnisherHBox,productHBox,reportsHBox,logoutHBox;
    
    @FXML
    private TableColumn idColumn,userColumn,passwordColumn,roleColumn;
    
    @FXML
    private TableColumn barcodeColumn,productColumn,saleColumn,buyColumn,stockColumn,fColumn;
    
    @FXML
    private TableColumn saleIdColumn,saleUserIdColumn,customerIdColumn,timeOfSaleColumn,totalPriceColumn;
    
    @FXML
    private TableColumn buyIdColumn,buyUserIdColumn,furnisherNameColumn,timeOfBuyColumn,totalBuyPriceColumn,paidColumn;
    
    @FXML
    private TextField usernameField,passwordField,idField,furnisherField,fiscalField,phoneField;
    
    @FXML
    private TextField barcodeField,productField,saleField,buyField,stockField;
    
    @FXML
    private ComboBox roleComboBox,furnisherComboBox;
    
    @FXML
    private TableView<Useri> usersTable;
    
    @FXML
    private TableView<Furnisher> furnisherTable;
    
    @FXML
    private TableView<Product> productsTable;
    
    @FXML
    private TableView<Sale> saleTable;
    
    @FXML
    private TableView<Buy> buyTable;
    
    
    
    @FXML
    private TableColumn furnisherColumn,fiscalColumn,phoneColumn;
    
    public static UserDao user;
    public static FurnisherDao furnisher;
    public static ProductDao product;
    public ObservableList<Useri> oList;
    public ObservableList<Furnisher> furnisherList;
    public ObservableList<Product> productList;
    public ObservableList<Sale> sList; 
    public ObservableList<Buy> bList; 
    public static Stage addUserStage;
    public static int saleIdPass;
    public static Integer buyIdPass;
    public static Double totalSalePricePass;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            usersClicked();
        } catch (IOException ex) {
            Logger.getLogger(AdminMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        POSApplication.mainStageReference.setMinHeight(visualBounds.getHeight());
        POSApplication.mainStageReference.setMinWidth(visualBounds.getWidth());
        POSApplication.mainStageReference.setMaxHeight(visualBounds.getHeight());
        POSApplication.mainStageReference.setMaxWidth(visualBounds.getWidth());
        System.out.println(visualBounds.getWidth());
        System.out.println(visualBounds.getHeight());
        POSApplication.mainStageReference.setResizable(false);
        POSApplication.mainStageReference.centerOnScreen();
        idField.setDisable(true);
        roleComboBox.getItems().addAll("Admin","User");
        
        
       
        welcomeLabel.setText(" Welcome, "+LoginController.signedUser+" ");
        
        try {
            tableResults();
            furnisherTableResults();
            productTableResults();
            saleReports();
        } catch (SQLException ex) {
            Logger.getLogger(AdminMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
    }  
    
    //Going to back to login scene if logout button is clicked
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
    
    
    boolean usersClicked=false;
    boolean furnishersClicked=false;
    boolean productsClicked=false;
    
    //Users anchorpane is showed on click of Users HBox
    public void usersClicked() throws IOException{
        reportsClicked=false;
        productsClicked=false;
        furnishersClicked=false;
        usersClicked=true;

        furnisherPane.setStyle(null);
        furnisherPane.setVisible(false);
        productPane.setVisible(false);
        reportsPane.setVisible(false);
        productHBox.setStyle(null);
        reportsHBox.setStyle(null);
        
        
        userHBox.setStyle("-fx-background-color: #dae9ff"); //#ccddf7   
        userPane.setVisible(true);
        furnisherHBox.setStyle(null);
        clearFurnisherFields();
        clearProductFields();
        
    }
    
    //Furnishers anchorpane is showed on click of Furnishers HBox
    public void furnishersClicked() throws IOException{
        reportsClicked=false;
        productsClicked=false;
        usersClicked=false;
        userPane.setVisible(false);
        productPane.setVisible(false);
        reportsPane.setVisible(false);
        furnishersClicked=true;
        
        reportsHBox.setStyle(null);
        userHBox.setStyle(null);
        furnisherHBox.setStyle("-fx-background-color: #dae9ff"); //#ccddf7   
        furnisherPane.setVisible(true);
        userHBox.setStyle(null);
        productHBox.setStyle(null);
        usersTable.getSelectionModel().clearSelection();
        clearUserFields();
        clearProductFields();
        
    }
    
    //Delete user from database and table
    public void deleteUser() throws SQLException{
        if(usernameField.getText().trim().isEmpty() || passwordField.getText().trim().isEmpty() || roleComboBox.getSelectionModel().getSelectedIndex()==-1){
            showAlert("Fields are empty!");
            
        }else{
            user=(UserDao) new UserDaoImpl();
            Short deleteId=usersTable.getSelectionModel().getSelectedItem().getUserId();
            user.delete(deleteId);
            usersTable.refresh();
            tableResults();
            clearUserFields();
        }

    }
    
    //Add user in database and table
    public void addUserClicked() throws SQLException, IOException{
        if(usernameField.getText().trim().isEmpty() || passwordField.getText().trim().isEmpty() || roleComboBox.getSelectionModel().getSelectedIndex()==-1){
            showAlert("Fields are empty!");
        }else{
        String username=usernameField.getText().toString();
        String password=passwordField.getText().toString();
        String role;
        if(roleComboBox.getSelectionModel().getSelectedIndex()==0){
            role="admin";
        }else if(roleComboBox.getSelectionModel().getSelectedIndex()==1){
            role="user";
        }else{
            role="";
        }
        boolean userExists=false;
        
         for(int i=0;i<oList.size();i++){
            if(username.equals(oList.get(i).getUsername()))
                userExists=true;
        }
                
        if(userExists==false){
            user.create(new Useri(username,password,role));
            clearUserFields();
            usersTable.refresh();
            tableResults();
        }else{
            showAlert("User with this ID already exists!");
        }
        }
                       
    }
    
    //Set style of Users HBox when mouse enters 
    public void userMouseEnter(){
        userHBox.setStyle("-fx-background-color: #dae9ff"); //#ccddf7              
    }
    
    //Reset style of Users HBox when mouse exits
    public void userMouseExit(){
        if(!usersClicked)
        userHBox.setStyle(null);
    }
    
    //Set style of Furnishers HBox when mouse enters
    public void furnishersMouseEnter(){
        furnisherHBox.setStyle("-fx-background-color: #dae9ff"); //#ccddf7              
    }
    
    //Reset style of Furnishers HBox when mouse exits
    public void furnishersMouseExit(){
        if(!furnishersClicked)
        furnisherHBox.setStyle(null);
    }
    
    //Get results for user from the database and showing them in the users table
    public void tableResults() throws SQLException{
        user=new UserDaoImpl();
        List<Useri> list=user.read();
        oList= FXCollections.observableArrayList();
        Short userId;
        String username,password,role;
        for(int i=0;i<list.size();i++){
            userId=list.get(i).getUserId();
            username=list.get(i).getUsername();
            password=list.get(i).getPassword();
            role=list.get(i).getRole();
            oList.add(new Useri(userId,username,password,role));
        }
        
        
        idColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        userColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        usersTable.setItems(oList);
     
    }
    
    //Show results of users table row in textfields
    public void rowClicked(){
        if(usersTable.getSelectionModel().getSelectedItem()!=null){
   
            Useri clickedUser=usersTable.getSelectionModel().getSelectedItem();
            idField.setText(clickedUser.getUserId()+"");
            usernameField.setText(clickedUser.getUsername());
            passwordField.setText(clickedUser.getPassword());
            if(clickedUser.getRole().equals("admin")){
            roleComboBox.getSelectionModel().select(0);
            }else{
            roleComboBox.getSelectionModel().select(1);                
            }
        }else{
            System.out.println("not selected");
        }
     
    }
    
    //Show results of furnishers table row in textfields
    public void fRowClicked(){
        Furnisher clickedFurnisher=null;
        
        if(furnisherTable.getSelectionModel().getSelectedItem()!=null){
   
            clickedFurnisher=furnisherTable.getSelectionModel().getSelectedItem();
            furnisherField.setText(clickedFurnisher.getFName());
            fiscalField.setText(clickedFurnisher.getFiscalNr());
            phoneField.setText(clickedFurnisher.getPhoneNumber());
            
        }else{
            System.out.println("not selected");
        }        
        
    }
    
    
    //Clear user textfields
    public void clearUserFields(){
        idField.setText("");
        usernameField.setText("");
        passwordField.setText("");
        roleComboBox.getSelectionModel().clearSelection();
        usersTable.getSelectionModel().clearSelection();

    }
    
    //Clear furnisher textfields
    public void clearFurnisherFields(){
        furnisherField.setText("");
        fiscalField.setText("");
        phoneField.setText("");
        furnisherTable.getSelectionModel().clearSelection();

    }
    
    //Edit user in database and refresh users table
    public void editUser() throws SQLException{
        if(usernameField.getText().trim().isEmpty() && passwordField.getText().trim().isEmpty() && roleComboBox.getSelectionModel().getSelectedIndex()==-1){
            showAlert("Fields are empty!");
        }else{
            if(usersTable.getSelectionModel().getSelectedItem()!=null){
                Short userId=usersTable.getSelectionModel().getSelectedItem().getUserId();
                String username=usernameField.getText().toString();
                String password=passwordField.getText().toString();
                String role=roleComboBox.getSelectionModel().getSelectedItem()+"";

                user.update(new Useri(userId,username,password,role));        
                usersTable.refresh();
                tableResults();
                clearUserFields();
                
        }else{
            showAlert("You have not choosed user!");
        }
        
        }
    }
    
    //Get results for furnishers from the database and showing them in the furnishers table
    public void furnisherTableResults() throws SQLException{
        furnisher=new FurnisherDaoImpl();
        List<Furnisher> list=furnisher.read();
        furnisherList= FXCollections.observableArrayList();
        
        String fName,fiscalNr,phone;
        for(int i=0;i<list.size();i++){
            fName=list.get(i).getFName();
            fiscalNr=list.get(i).getFiscalNr();
            phone=list.get(i).getPhoneNumber();
            System.out.println(fName);
            furnisherList.add(new Furnisher(fName,fiscalNr,phone));
        }
        furnisherColumn.setCellValueFactory(new PropertyValueFactory<>("fName"));
        fiscalColumn.setCellValueFactory(new PropertyValueFactory<>("fiscalNr"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        furnisherTable.setItems(furnisherList);
    }
    
    //Add furnisher in database and refresh the table
    public void addFurnisherClicked() throws SQLException{
        if(furnisherField.getText().trim().isEmpty() || fiscalField.getText().trim().isEmpty() || phoneField.getText().trim().isEmpty()){
            showAlert("Fields are empty!");
        }else{
        
            String fName=furnisherField.getText().toString();
            String fiscalNr=fiscalField.getText().toString();
            String phone=phoneField.getText().toString();

            boolean furnisherExists=false;

             for(int i=0;i<furnisherList.size();i++){
                if(fName.equals(furnisherList.get(i).getFName()))
                    furnisherExists=true;
            }

            if(furnisherExists==false){
                furnisher.create(new Furnisher(fName,fiscalNr,phone));
                clearUserFields();
                furnisherTable.refresh();
                furnisherTableResults();
            }else{
                showAlert("Furnisher already exists!");
            }
        }
    }
    
    //Edit furnisher in database and refresh the table
    public void editFurnisherClicked() throws SQLException{
        if(furnisherTable.getSelectionModel().getSelectedItem()!=null){
            String fName=furnisherField.getText().toString();
            String fiscalNr=fiscalField.getText().toString();
            String phone=phoneField.getText().toString();

            furnisher.update(new Furnisher(fName,fiscalNr,phone));        
            furnisherTable.refresh();
            furnisherTableResults();
        }else{
            showAlert("You have not choosed furnisher!");
        }
    }
    public void deleteFurnisherClicked() throws SQLException{
        furnisher=new FurnisherDaoImpl();
        String fName=furnisherTable.getSelectionModel().getSelectedItem().getFName();
        furnisher.delete(fName);
        furnisherTable.refresh();
        furnisherTableResults();
        clearFurnisherFields();
    }
    
    //Show products scene if product HBox is clicked
    public void productsClicked(){
        productsClicked=true;
        usersClicked=false;
        userPane.setVisible(false);
        furnishersClicked=false;
        reportsClicked=false;
        furnisherPane.setVisible(false);
        userPane.setVisible(false);
        reportsPane.setVisible(false);
        
        userHBox.setStyle(null);
        furnisherHBox.setStyle(null);
        reportsHBox.setStyle(null);
        productHBox.setStyle("-fx-background-color: #dae9ff"); //#ccddf7   
        productPane.setVisible(true);
        
        for(int i=0;i<furnisherList.size();i++){
            furnisherComboBox.getItems().clear();
        }
        for(int i=0;i<furnisherList.size();i++){
            String fNameList =furnisherList.get(i).getFName();
            furnisherComboBox.getItems().addAll(fNameList);
                
        }
        
        usersTable.getSelectionModel().clearSelection();
        furnisherTable.getSelectionModel().clearSelection();
      
        clearUserFields();
        clearFurnisherFields();
        
    }
 
    //Set style of products HBox if mouse enters
    public void productsMouseEnter(){
        productHBox.setStyle("-fx-background-color: #dae9ff"); //#ccddf7                 
    }
    
    //Reset style of products HBox if mouse exits
    public void productsMouseExit(){
        if(!productsClicked)
        productHBox.setStyle(null);        
    }
    
    //Get results for products from the database and showing them in the products table
    public void productTableResults() throws SQLException{
        product=new ProductDaoImpl();
        List<Product> list=product.read();
        productList= FXCollections.observableArrayList();
        
        String barcode,productName,fName;
        double salePrice,buyPrice;
        Integer stock;
        for(int i=0;i<list.size();i++){
            barcode=list.get(i).getBarcode();
            productName=list.get(i).getProductName();
            salePrice=list.get(i).getSalePrice();
            buyPrice=list.get(i).getBuyPrice();
            stock=list.get(i).getStock();
            fName=list.get(i).getFurnisherName();
            productList.add(new Product(barcode,productName,salePrice,buyPrice,stock,fName));
            System.out.println(barcode);
        }
        
        barcodeColumn.setCellValueFactory(new PropertyValueFactory<>("barcode"));
        productColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        saleColumn.setCellValueFactory(new PropertyValueFactory<>("salePrice"));
        buyColumn.setCellValueFactory(new PropertyValueFactory<>("buyPrice"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        fColumn.setCellValueFactory(new PropertyValueFactory<>("furnisherName"));
        
        productsTable.setItems(productList);
    }
    
    //Clear product textfields
    public void clearProductFields(){
        barcodeField.setText("");
        productField.setText("");
        saleField.setText("");
        buyField.setText("");
        stockField.setText("");
        furnisherComboBox.getSelectionModel().clearSelection();
        productsTable.getSelectionModel().clearSelection();

    }
    
    //Get product table row clicked and show its results in textfields
    public void pRowClicked(){
        Product clickedProduct=null;
        
        if(productsTable.getSelectionModel().getSelectedItem()!=null){
   
            clickedProduct=productsTable.getSelectionModel().getSelectedItem();
            barcodeField.setText(clickedProduct.getBarcode());
            productField.setText(clickedProduct.getProductName());
            saleField.setText(clickedProduct.getSalePrice()+"");
            buyField.setText(clickedProduct.getBuyPrice()+"");
            stockField.setText(clickedProduct.getStock()+"");
            
            for(int i=0;i<furnisherComboBox.getItems().size();i++){
            if(clickedProduct.getFurnisherName().equals(furnisherComboBox.getItems().get(i).toString())){
                furnisherComboBox.getSelectionModel().select(i);
            }
            }
            
                     
        }else{
            System.out.println("not selected");
        }        
        
    }
    
    //Add a product to database and refresh table
    public void addProduct() throws SQLException{
        if(barcodeField.getText().trim().isEmpty() || productField.getText().trim().isEmpty() || saleField.getText().trim().isEmpty() || buyField.getText().trim().isEmpty() || stockField.getText().trim().isEmpty() || furnisherComboBox.getSelectionModel().getSelectedIndex()==-1){
            showAlert("Fields are empty!");
        }else{
            String barcode=barcodeField.getText().toString();
            String productName=productField.getText().toString();
            double salePrice=Double.parseDouble(saleField.getText().toString());
            double buyPrice=Double.parseDouble(buyField.getText().toString());
            int stock=Integer.parseInt(stockField.getText().toString());

            String fName=furnisherComboBox.getSelectionModel().getSelectedItem().toString();

            boolean productExists=false;

             for(int i=0;i<productList.size();i++){
                if(barcode.equals(productList.get(i).getBarcode()))
                    productExists=true;
            }

            if(productExists==false){
                product.create(new Product(barcode,productName,salePrice,buyPrice,stock,fName));
                clearProductFields();
                productsTable.refresh();
                productTableResults();
            }else{
                showAlert("Product already exists!");
            }
        }
    }
    
    //Edit product in database and refresh table
    public void editProduct() throws SQLException{
        if(productsTable.getSelectionModel().getSelectedItem()!=null){
            double salePrice=Double.parseDouble(saleField.getText().toString());
            double buyPrice=Double.parseDouble(buyField.getText().toString());
            Integer stock=Integer.parseInt(stockField.getText().toString());
            String fName=furnisherComboBox.getSelectionModel().getSelectedItem().toString();
            
            String barcode=productsTable.getSelectionModel().getSelectedItem().getBarcode();
            String productName=productsTable.getSelectionModel().getSelectedItem().getProductName();            
            product.update(new Product(barcode,productName,salePrice,buyPrice,stock,fName));        
            productsTable.refresh();
            productTableResults();
        }else{
            showAlert("You have not choosed product!");
        }
    }
    
    //Delete product from database and refresh table
    public void deleteProduct() throws SQLException{
        String barcode=barcodeField.getText().toString();
        product.delete(barcode);
        productsTable.refresh();
        productTableResults();
        clearProductFields();
        productsTable.getSelectionModel().clearSelection();
    }
    
    boolean reportsClicked=false;
    //Show reports scene if reports HBox is clicked
    public void reportsClicked(){
        reportsClicked=true;
        reportsPane.setVisible(true);
        productsClicked=false;
        usersClicked=false;
        userPane.setVisible(false);
        furnishersClicked=false;
        furnisherPane.setVisible(false);
        userPane.setVisible(false);
        productPane.setVisible(false);
        
        userHBox.setStyle(null);
        furnisherHBox.setStyle(null);
        productHBox.setStyle(null);
        reportsHBox.setStyle("-fx-background-color: #dae9ff"); //#ccddf7   
        reportsPane.setVisible(true);
        
        usersTable.getSelectionModel().clearSelection();
        furnisherTable.getSelectionModel().clearSelection();
        productsTable.getSelectionModel().clearSelection();
      
        clearUserFields();
        clearFurnisherFields();
        clearProductFields();
        
    }
 
    //Set style of reports HBox if mouse enters
    public void reportsMouseEnter(){
        reportsHBox.setStyle("-fx-background-color: #dae9ff"); //#ccddf7                 
    }
    
    //Reset style of reports HBox if mouse exits
    public void reportsMouseExit(){
        if(!reportsClicked)
        reportsHBox.setStyle(null);        
    }
    
    //Set style of logout HBox if mouse enters
    public void logoutMouseEnter(){
        logoutHBox.setStyle("-fx-background-color: #dae9ff"); //#ccddf7                 
    }
    
    //Reset style of logout HBox if mouse exits
    public void logoutMouseExit(){
        logoutHBox.setStyle(null);        
    }
    
    //Get sale results from database and show them in sale table
    public void saleReports() throws SQLException{
        SaleDao saleDao=new SaleDaoImpl();
        List<Sale> saleList = saleDao.read();
        sList=FXCollections.observableArrayList();
        Integer saleId,customerNr;
        Short saleUserId;
        String date;
        double total;
        
        for(int i=0;i<saleList.size();i++){
            saleId=saleList.get(i).getSaleId();
            saleUserId=saleList.get(i).getSaleUserId();
            customerNr=saleList.get(i).getCustomerNr();
            date=saleList.get(i).getTimeOfSale();
            
            total=saleList.get(i).getTotalPrice();
            Sale s=new Sale(saleId,saleUserId,date.substring(0,19),total);
            if(customerNr!=0)
                s.setCustomerNr(customerNr);
            System.out.println(customerNr);
            sList.add(s);
            
        }
        
        saleIdColumn.setCellValueFactory(new PropertyValueFactory<>("saleId"));
        saleUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("saleUserId"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerNr"));
        timeOfSaleColumn.setCellValueFactory(new PropertyValueFactory<>("timeOfSale"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        
        
        saleTable.setItems(sList);
        
    }
    
    //Show sale table if button for sale reports is clicked
    public void showSaleReports() throws SQLException{
        saleReports();
        saleTable.refresh();
        saleTable.getSelectionModel().clearSelection();
        saleTable.setVisible(true);
        saleResultsButton.setVisible(true);
        buyTable.setVisible(false);
        buyResultsButton.setVisible(false);
        finishPayment.setVisible(false);
    }
    
    //Get buy results from database and show them in buy table
    public void buyReports() throws SQLException{
        BuyDao buyDao=new BuyDaoImpl();
        List<Buy> buyList = buyDao.read();
        bList=FXCollections.observableArrayList();
        Integer buyId;
        Short buyUserId;
        String date,furnisherName;
        boolean isPaid;
        double total;
        
        for(int i=0;i<buyList.size();i++){
            buyId=buyList.get(i).getBuyId();
            buyUserId=buyList.get(i).getBuyUserId();
            furnisherName=buyList.get(i).getBuyFurnisherName();
            
            date=buyList.get(i).getTimeOfBuy();
            isPaid=buyList.get(i).getIsPaid();
            
            total=buyList.get(i).getTotalPrice();
            Buy b=new Buy(buyId,buyUserId,furnisherName,date.substring(0,19),total,isPaid);
            
            bList.add(b);
            
        }
        
        buyIdColumn.setCellValueFactory(new PropertyValueFactory<>("buyId"));
        buyUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("buyUserId"));
        furnisherNameColumn.setCellValueFactory(new PropertyValueFactory<>("buyFurnisherName"));
        timeOfBuyColumn.setCellValueFactory(new PropertyValueFactory<>("timeOfBuy"));
        totalBuyPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        paidColumn.setCellValueFactory(new PropertyValueFactory<>("isPaid"));

        
        
        buyTable.setItems(bList);
        
    }
    
    //Show buy table if button for buy reports is clicked
    public void showBuyReports() throws SQLException{
        buyReports();
        buyTable.refresh();
        buyTable.getSelectionModel().clearSelection();
        buyTable.setVisible(true);
        buyResultsButton.setVisible(true);
        finishPayment.setVisible(true);
        saleTable.setVisible(false);
        saleResultsButton.setVisible(false);
    }
    
    //Get sale details for the sale table row clicked and show them in a new window
    public void saleResults() throws IOException{
        if(saleTable.getSelectionModel().getSelectedItem()==null){
            showAlert("No sale selected!");
        }else{
            saleIdPass=saleTable.getSelectionModel().getSelectedItem().getSaleId();
            totalSalePricePass=saleTable.getSelectionModel().getSelectedItem().getTotalPrice();
            AnchorPane saleResultsScene = FXMLLoader.load(POSApplication.class.getResource("/fxml/SaleResults.fxml"));

            Stage stage = new Stage();
            stage.setTitle("Sale results");
            stage.setScene(new Scene(saleResultsScene, 700, 500));
            Image icon = new Image(getClass().getResourceAsStream("/resources/point-of-sale-icon-10656.png"));
            stage.getIcons().add(icon);
            stage.show();        
        }
    }
    
    //Get buy details for the buy table row clicked and show them in a new window
    public void buyResults() throws IOException{
        if(buyTable.getSelectionModel().getSelectedItem()==null){
            showAlert("No buy selected!");
        }else{
            buyIdPass=buyTable.getSelectionModel().getSelectedItem().getBuyId();

            AnchorPane buyResultsScene = FXMLLoader.load(POSApplication.class.getResource("/fxml/BuyResults.fxml"));

            Stage stage = new Stage();
            stage.setTitle("Buy results");
            stage.setScene(new Scene(buyResultsScene, 700, 450));
            Image icon = new Image(getClass().getResourceAsStream("/resources/point-of-sale-icon-10656.png"));
            stage.getIcons().add(icon);
            stage.show();        
        }
    }
    
    //Alert dialog method with message parameter
    public void showAlert(String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");

        // Header Text: null

        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
    }
    
    //Finish payment of a buy table row if it is not paid
    public void updateIsPaid() throws SQLException{
        BuyDao buyDao=new BuyDaoImpl();
        if(buyTable.getSelectionModel().getSelectedItem()==null){
            showAlert("No buy selected!");
        }else{
            buyDao.updateIsPaid(buyTable.getSelectionModel().getSelectedItem().getBuyId());
            showAlert("Payment finished successfully!");
        }
        bList.clear();
        buyTable.refresh();
        buyReports();
    }
    
}
