/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posapplication.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Gurakuq Krasniqi
 */
public class Sale implements Serializable {

    public static String insertStatement = "INSERT INTO " +
        "sale (sale_user_id, customer_nr, time_of_sale, total_price) VALUES (?, ?, ?, ?)";
    
    public static String selectStatement = "SELECT sale_id, sale_user_id, customer_nr, time_of_sale, total_price" +
        " FROM sale";
    
    public static String deleteStatement = "DELETE FROM sale WHERE sale_id = ?";
    
    public static String insertWithoutCustomerStatement="INSERT INTO " +
        "sale (sale_user_id, time_of_sale, total_price) VALUES (?, ?, ?)";
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "sale_id")
    private Integer saleId;
    @Basic(optional = false)
    @Column(name = "sale_user_id")
    private short saleUserId;
    private Integer customerNr;
    @Basic(optional = false)
    @Column(name = "time_of_sale")
    @Temporal(TemporalType.TIMESTAMP)
    private String timeOfSale;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "total_price")
    private Double totalPrice;

    public Sale() {
    }

    public Sale(Integer saleId) {
        this.saleId = saleId;
    }

    public Sale(Integer saleId, short saleUserId, String timeOfSale, Double totalPrice) {
        this.saleId = saleId;
        this.saleUserId = saleUserId;
        this.timeOfSale = timeOfSale;
        this.totalPrice = totalPrice;
    }

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    public short getSaleUserId() {
        return saleUserId;
    }

    public void setSaleUserId(short saleUserId) {
        this.saleUserId = saleUserId;
    }

    public String getTimeOfSale() {
        return timeOfSale;
    }

    public void setTimeOfSale(String timeOfSale) {
        this.timeOfSale = timeOfSale;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getCustomerNr() {
        return customerNr;
    }

    public void setCustomerNr(Integer customerNr) {
        this.customerNr = customerNr;
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
        if (!(object instanceof Sale)) {
            return false;
        }
        Sale other = (Sale) object;
        if ((this.saleId == null && other.saleId != null) || (this.saleId != null && !this.saleId.equals(other.saleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "posapplication.entity.Sale[ saleId=" + saleId + " ]";
    }
    
}
