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
public class Customer implements Serializable {
    
    public static String selectStatement = "SELECT customer_id, firstname, lastname,address,card_points" +
    " FROM customer";
    
    public static String insertStatement = "INSERT INTO " +
      "customer(firstname, lastname,address,card_points) VALUES (?, ?,?,?)";

    public static String updateStatement = "UPDATE customer SET firstname = ?," +
      "lastname = ?,address= ?, card_points = ? WHERE customer_id = ?";
    
    public static String updatePointsStatement = "update customer set card_points=? where customer_id=?";

    public static String deleteStatement = "DELETE FROM customer WHERE customer_id = ?";
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "customer_id")
    private Integer customerId;
    @Basic(optional = false)
    @Column(name = "firstname")
    private String firstname;
    @Basic(optional = false)
    @Column(name = "lastname")
    private String lastname;
    @Basic(optional = false)
    @Column(name = "address")
    private String address;
    @Basic(optional = false)
    @Column(name = "card_points")
    private int cardPoints;

    public Customer() {
    }

    public Customer(Integer customerId) {
        this.customerId = customerId;
    }

    public Customer(Integer customerId, String firstname, String lastname, String address, int cardPoints) {
        this.customerId = customerId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.cardPoints = cardPoints;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCardPoints() {
        return cardPoints;
    }

    public void setCardPoints(int cardPoints) {
        this.cardPoints = cardPoints;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerId != null ? customerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "posapplication.entity.Customer[ customerId=" + customerId + " ]";
    }
    
}
