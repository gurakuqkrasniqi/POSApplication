/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posapplication.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Gurakuq Krasniqi
 */
public class SaleDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "sale_number")
    private int saleNumber;
    @Id
    @Basic(optional = false)
    @Column(name = "sale_id")
    private Integer saleId;
    @Column(name = "qty")
    private Short qty;
    @Column(name = "sell_qty_value")
    private Double sellQtyValue;
    @Column(name = "total")
    private Double total;
    private String sellBarcode;
    private String sellProductName;
    
    public static String insertStatement = "INSERT INTO " +
    "sale_details (sale_number,sell_prod_barcode, sell_prod_name,qty,sell_qty_value,total) VALUES (?, ?, ?, ?, ?, ?)";
    
    public static String selectStatement = "SELECT sale_number, sale_id ,sell_prod_barcode, sell_prod_name, qty, sell_qty_value, total" +
    " FROM sale_details";
    
    public static String deleteStatement = "DELETE FROM sale_details WHERE sale_number = ?";
    
    
    
    
    
    public SaleDetails() {
    }


    public SaleDetails(int saleNumber) {
        this.saleNumber = saleNumber;
    }

    public SaleDetails(int saleNumber, String sellBarcode, String sellProductName, Short qty, Double sellQtyValue, Double total) {
        this.saleNumber = saleNumber;
        this.sellBarcode = sellBarcode;
        this.sellProductName=sellProductName;
        this.qty = qty;
        this.sellQtyValue = sellQtyValue;
        this.total = total;
        
    }

    

    public int getSaleNumber() {
        return saleNumber;
    }

    public void setSaleNumber(int saleNumber) {
        this.saleNumber = saleNumber;
    }

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    public Short getQty() {
        return qty;
    }

    public void setQty(Short qty) {
        this.qty = qty;
    }

    public Double getSellQtyValue() {
        return sellQtyValue;
    }

    public void setSellQtyValue(Double sellQtyValue) {
        this.sellQtyValue = sellQtyValue;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getSellBarcode() {
        return sellBarcode;
    }

    public void setSellBarcode(String sellBarcode) {
        this.sellBarcode = sellBarcode;
    }

    public String getSellProductName() {
        return sellProductName;
    }

    public void setSellProductName(String sellProductName) {
        this.sellProductName = sellProductName;
    }
    
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (saleId != null ? saleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SaleDetails)) {
            return false;
        }
        SaleDetails other = (SaleDetails) object;
        if ((this.sellBarcode == null && other.sellBarcode != null) || (this.sellBarcode != null && !this.sellBarcode.equals(other.sellBarcode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "posapplication.entity.SaleDetails[ saleBarcode=" + sellBarcode + " ]";
    }
    
}
