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
public class Furnisher implements Serializable {

    public static String insertStatement = "INSERT INTO " +
      "FURNISHER(F_NAME, FISCAL_NR, PHONE_NUMBER) VALUES (?, ?, ?)";

    public static String updateStatement = "UPDATE FURNISHER SET FISCAL_NR = ?," +
      "phone_number = ? WHERE F_NAME = ?";

    public static String selectStatement = "SELECT f_name, FISCAL_NR, PHONE_NUMBER" +
      " FROM furnisher";

    public static String deleteStatement = "DELETE FROM FURNISHER WHERE F_NAME = ?";

    public static String findByIdStatement = "SELECT NAME, FISCAL_NR, PHONE_NUMBER" +
      " FROM FURNISHER WHERE NAME = ?";
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "f_name")
    private String fName;
    @Column(name = "fiscal_nr")
    private String fiscalNr;
    @Column(name = "phone_number")
    private String phoneNumber;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "furnisherName")
    private List<Product> productList;
    @OneToMany(mappedBy = "buyFurnisherName")
    private List<Buy> buyList;

    public Furnisher() {
    }

    public Furnisher(String fName) {
        this.fName = fName;
    }

    public Furnisher(String fName, String fiscalNr, String phoneNumber) {
        this.fName=fName;
        this.fiscalNr=fiscalNr;
        this.phoneNumber=phoneNumber;
    }

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getFiscalNr() {
        return fiscalNr;
    }

    public void setFiscalNr(String fiscalNr) {
        this.fiscalNr = fiscalNr;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @XmlTransient
    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @XmlTransient
    public List<Buy> getBuyList() {
        return buyList;
    }

    public void setBuyList(List<Buy> buyList) {
        this.buyList = buyList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fName != null ? fName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Furnisher)) {
            return false;
        }
        Furnisher other = (Furnisher) object;
        if ((this.fName == null && other.fName != null) || (this.fName != null && !this.fName.equals(other.fName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "posapplication.entity.Furnisher[ fName=" + fName + " ]";
    }
    
}
