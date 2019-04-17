/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Padovano
 */

public class PrixProduit implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer ppId;
    
    private double prix;
    
    private Date created;
    
    private Date modified;
   
    private Double offPercent;
    
    private Collection<CommandeLine> commandeLineCollection;
    
    private Produits proId;

    public PrixProduit() {
    }

    public PrixProduit(Integer ppId) {
        this.ppId = ppId;
    }

    public PrixProduit(Integer ppId, double prix, Date created) {
        this.ppId = ppId;
        this.prix = prix;
        this.created = created;
    }

    public Integer getPpId() {
        return ppId;
    }

    public void setPpId(Integer ppId) {
        this.ppId = ppId;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Double getOffPercent() {
        return offPercent;
    }

    public void setOffPercent(Double offPercent) {
        this.offPercent = offPercent;
    }

    @XmlTransient
    public Collection<CommandeLine> getCommandeLineCollection() {
        return commandeLineCollection;
    }

    public void setCommandeLineCollection(Collection<CommandeLine> commandeLineCollection) {
        this.commandeLineCollection = commandeLineCollection;
    }

    public Produits getProId() {
        return proId;
    }

    public void setProId(Produits proId) {
        this.proId = proId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ppId != null ? ppId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrixProduit)) {
            return false;
        }
        PrixProduit other = (PrixProduit) object;
        if ((this.ppId == null && other.ppId != null) || (this.ppId != null && !this.ppId.equals(other.ppId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.PrixProduit[ ppId=" + ppId + " ]";
    }
    
}
