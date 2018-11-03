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
public class Useri implements Serializable {
    public static String insertStatement = "INSERT INTO " +
    "useri (username, password, role) VALUES (?, ?, ?)";
    
    public static String selectStatement = "SELECT user_id, username, password, role" +
    " FROM useri";

    public static String deleteStatement = "DELETE FROM useri WHERE user_id = ?";
    
    public static String updateStatement = "UPDATE useri SET username = ?," +
    "password = ?,"+"role= ? "+ "WHERE user_id = ?";
            
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "user_id")
    private Short userId;
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "role")
    private String role;

    public Useri() {
    }

    public Useri(Short userId) {
        this.userId = userId;
    }

    public Useri(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Useri(Short userId, String username, String password, String role) {
        this.userId = userId;        
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Short getUserId() {
        return userId;
    }

    public void setUserId(Short userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Useri)) {
            return false;
        }
        Useri other = (Useri) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "posapplication.entity.Useri[ userId=" + userId + " ]";
    }
    
}
