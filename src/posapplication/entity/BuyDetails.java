/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posapplication.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Gurakuq Krasniqi
 */
public class BuyDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    public static String insertStatement = "INSERT INTO " + 
            "buy_details(buy_prod_barcode, buy_prod_name,qty, buy_qty_value, total) VALUES (?,?,?,?,?) ";
    public static String selectStatement = " SELECT buy_id, buy_prod_barcode, buy_prod_name, qty, buy_qty_value, total " + 
            " FROM buy_details";
    @Id
    @Basic(optional = false)
    @Column(name = "buy_id")
    private Integer buyId;
    @Column(name = "qty")
    private Short qty;
    @Column(name = "buy_qty_value")
    private Double buyQtyValue;
    @Column(name = "buy_number")
    private Integer buyNumber;
    @Column(name = "total_bill_val")
    private Double totalBillVal;
    @Column(name = "buy_prod_name")
    @Temporal(TemporalType.TIMESTAMP)
    private String buyProductName;
    private String buyProdBarcode;

    public BuyDetails() {
    }

    public BuyDetails(Integer buyId, String buyProdBarcode, String buyProductName, Short qty, Double buyQtyValue, Double totalBillVal) {
        this.buyId=buyId;
        this.buyProdBarcode=buyProdBarcode;
        this.buyProductName=buyProductName;
        this.qty=qty;
        this.buyQtyValue=buyQtyValue;
        this.totalBillVal=totalBillVal;
        
    }
    
    public BuyDetails(String buyProdBarcode, String buyProductName, Short qty, Double buyQtyValue, Double totalBillVal) {

        this.buyProdBarcode=buyProdBarcode;
        this.buyProductName=buyProductName;
        this.qty=qty;
        this.buyQtyValue=buyQtyValue;
        this.totalBillVal=totalBillVal;
        
    }

    public Integer getBuyId() {
        return buyId;
    }

    public void setBuyId(Integer buyId) {
        this.buyId = buyId;
    }

    public Short getQty() {
        return qty;
    }

    public void setQty(Short qty) {
        this.qty = qty;
    }

    public Double getBuyQtyValue() {
        return buyQtyValue;
    }

    public void setBuyQtyValue(Double buyQtyValue) {
        this.buyQtyValue = buyQtyValue;
    }

  
    public Integer getBuyNumber(){
    return buyNumber;
    }
    public void setBuyNumber(Integer buyNumber){
    this.buyNumber=buyNumber;
    }


    public Double getTotalBillVal() {
        return totalBillVal;
    }

    public void setTotalBillVal(Double totalBillVal) {
        this.totalBillVal = totalBillVal;
    }

    public String getBuyProductName() {
        return buyProductName;
    }

    public void setBuyProductName(String buyProductName) {
        this.buyProductName = buyProductName;
    }

    public String getBuyProdBarcode() {
        return buyProdBarcode;
    }

    public void setBuyProdBarcode(String buyProdBarcode) {
        this.buyProdBarcode = buyProdBarcode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (buyId != null ? buyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BuyDetails)) {
            return false;
        }
        BuyDetails other = (BuyDetails) object;
        if ((this.buyId == null && other.buyId != null) || (this.buyId != null && !this.buyId.equals(other.buyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "posapplication.entity.BuyDetails[ buyId=" + buyId + " ]";
    }
    
}
