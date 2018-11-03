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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Gurakuq Krasniqi
 */
public class Buy implements Serializable {
    
    

  public static String selectStatement = "select buy_id_dtl, buy_furnisher_name, buy_user_id, time_of_buy, " +
    "total_price, is_paid from buy";

  public static String deleteStatement = "DELETE FROM BUY_TBL WHERE BUY_ID = ?";

  public static String insertStatement = "insert into buy (buy_id_dtl, buy_furnisher_name, buy_user_id, time_of_buy, total_price, is_paid) VALUES (?,?, ?,?,?,?)";
  
  public static String updateIsPaidStatement = "update buy set is_paid = ? where buy_id_dtl = ?";
  
  //-------------------------------------
  
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "buy_id_dtl")
    private Integer buyId;
    @Basic(optional = false)
    @Column(name = "is_paid")
    private boolean isPaid;
    @JoinColumn(name = "buy_id_dtl", referencedColumnName = "buy_id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private BuyDetails buyDetails;
    @JoinColumn(name = "buy_furnisher_name", referencedColumnName = "f_name")
    @ManyToOne
    private String buyFurnisherName;
    @JoinColumn(name = "buy_user_id", referencedColumnName = "ID_user")
    @ManyToOne
    private Short buyUserId;
    private String timeOfBuy;
    private Double totalPrice;

    public Buy() {
    }

    public Buy(Integer buyId) {
        this.buyId = buyId;
    }

    public Buy(Integer buyId, Short buyUserId, String buyFurnisherName, String timeOfBuy, Double totalPrice, boolean isPaid) {
        this.buyId = buyId;
        this.buyUserId=buyUserId;
        this.buyFurnisherName=buyFurnisherName;
        this.timeOfBuy=timeOfBuy;
        this.totalPrice=totalPrice;
        this.isPaid = isPaid;
    }
    
    public Buy(Short buyUserId, String buyFurnisherName, String timeOfBuy, Double totalPrice, boolean isPaid) {
        this.buyUserId=buyUserId;
        this.buyFurnisherName=buyFurnisherName;
        this.timeOfBuy=timeOfBuy;
        this.totalPrice=totalPrice;
        this.isPaid = isPaid;
    }

    public Integer getBuyId() {
        return buyId;
    }

    public void setBuyId(Integer buyId) {
        this.buyId = buyId;
    }

    public boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public BuyDetails getBuyDetails() {
        return buyDetails;
    }

    public void setBuyDetails(BuyDetails buyDetails) {
        this.buyDetails = buyDetails;
    }

    public String getBuyFurnisherName() {
        return buyFurnisherName;
    }

    public void setBuyFurnisherName(String buyFurnisherName) {
        this.buyFurnisherName = buyFurnisherName;
    }

    public Short getBuyUserId() {
        return buyUserId;
    }

    public void setBuyUserId(Short buyUserId) {
        this.buyUserId = buyUserId;
    }

    public String getTimeOfBuy() {
        return timeOfBuy;
    }

    public void setTimeOfBuy(String timeOfBuy) {
        this.timeOfBuy = timeOfBuy;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
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
        if (!(object instanceof Buy)) {
            return false;
        }
        Buy other = (Buy) object;
        if ((this.buyId == null && other.buyId != null) || (this.buyId != null && !this.buyId.equals(other.buyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "posapplication.entity.Buy[ buyIdDtl=" + buyId + " ]";
    }
    
}
