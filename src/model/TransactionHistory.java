/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Padovano
 */
public class TransactionHistory implements Serializable {
    private static final long serialVersionUID = 1L;
   
    private Integer transacId;
    
    private String type;
    
    private Date created;

    public TransactionHistory() {
    }

    public TransactionHistory(Integer transacId) {
        this.transacId = transacId;
    }

    public TransactionHistory(Integer transacId, String type) {
        this.transacId = transacId;
        this.type = type;
    }

    public Integer getTransacId() {
        return transacId;
    }

    public void setTransacId(Integer transacId) {
        this.transacId = transacId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transacId != null ? transacId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionHistory)) {
            return false;
        }
        TransactionHistory other = (TransactionHistory) object;
        if ((this.transacId == null && other.transacId != null) || (this.transacId != null && !this.transacId.equals(other.transacId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.TransactionHistory[ transacId=" + transacId + " ]";
    }
    
}
