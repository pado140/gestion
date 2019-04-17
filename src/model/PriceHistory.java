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
public class PriceHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer phId;
    
    private double price;
    
    private Date created;

    public PriceHistory() {
    }

    public PriceHistory(Integer phId) {
        this.phId = phId;
    }

    public PriceHistory(Integer phId, double price, Date created) {
        this.phId = phId;
        this.price = price;
        this.created = created;
    }

    public Integer getPhId() {
        return phId;
    }

    public void setPhId(Integer phId) {
        this.phId = phId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
        hash += (phId != null ? phId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PriceHistory)) {
            return false;
        }
        PriceHistory other = (PriceHistory) object;
        if ((this.phId == null && other.phId != null) || (this.phId != null && !this.phId.equals(other.phId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.PriceHistory[ phId=" + phId + " ]";
    }
    
}
