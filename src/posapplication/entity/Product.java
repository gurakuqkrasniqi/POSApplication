/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posapplication.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Gurakuq Krasniqi
 */
public class Product implements Serializable {
    
    public static String selectStatement = "SELECT barcode, product_name, sale_price, buy_price, stock, furnisher_name" +
    " FROM product";
    
    public static String insertStatement = "INSERT INTO " +
      "product(barcode, product_name, sale_price, buy_price,stock,furnisher_name) VALUES (?, ?, ?,?,?,(SELECT f_name FROM furnisher WHERE f_name = ?))";

    public static String updateStatement = "UPDATE product SET sale_price = ?," +
      "buy_price = ?,stock = ?, furnisher_name = ? WHERE barcode = ?";
    
    public static String updateStockStatement = "update product set stock = ? where barcode = ?";

    public static String deleteStatement = "DELETE FROM product WHERE barcode = ?";

    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "barcode")
    private String barcode;
    @Basic(optional = false)
    @Column(name = "product_name")
    private String productName;
    @Basic(optional = false)
    @Column(name = "sale_price")
    private double salePrice;
    @Basic(optional = false)
    @Column(name = "buy_price")
    private double buyPrice;
    @Column(name = "stock")
    private Integer stock;
    @JoinColumn(name = "furnisher_name", referencedColumnName = "f_name")
    @ManyToOne(optional = false)
    private String furnisherName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sellProdBarcode")
    private List<SaleDetails> saleDetailsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "buyProdBarcode")
    private List<BuyDetails> buyDetailsList;

    public Product() {
    }

    public Product(String barcode) {
        this.barcode = barcode;
    }

    public Product(String barcode, String productName, double salePrice, double buyPrice) {
        this.barcode = barcode;
        this.productName = productName;
        this.salePrice = salePrice;
        this.buyPrice = buyPrice;
    }

    

    public Product(String barcode, String productName, double salePrice, double buyPrice, Integer stock, String furnisherName) {
        this.barcode = barcode;
        this.productName = productName;
        this.salePrice = salePrice;
        this.buyPrice = buyPrice;
        this.stock = stock;
        this.furnisherName = furnisherName;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getFurnisherName() {
        return furnisherName;
    }

    public void setFurnisherName(String furnisherName) {
        this.furnisherName = furnisherName;
    }

    @XmlTransient
    public List<SaleDetails> getSaleDetailsList() {
        return saleDetailsList;
    }

    public void setSaleDetailsList(List<SaleDetails> saleDetailsList) {
        this.saleDetailsList = saleDetailsList;
    }

    @XmlTransient
    public List<BuyDetails> getBuyDetailsList() {
        return buyDetailsList;
    }

    public void setBuyDetailsList(List<BuyDetails> buyDetailsList) {
        this.buyDetailsList = buyDetailsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (barcode != null ? barcode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.barcode == null && other.barcode != null) || (this.barcode != null && !this.barcode.equals(other.barcode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "posapplication.entity.Product[ barcode=" + barcode + " ]";
    }
    
}
