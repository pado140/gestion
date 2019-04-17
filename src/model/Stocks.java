/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author Padovano
 */
public class Stocks extends item{
    private ObjectProperty<Produits> produit;
    private ObjectProperty<Integer> qty,alert_qty,minQty;
    private ObjectProperty<LocalDate> created,mod;
    private ObjectProperty<Double> pump,prixvente;

    public Stocks() {
        this.produit = new SimpleObjectProperty<>();
        this.qty = new SimpleObjectProperty<>();
        this.alert_qty = new SimpleObjectProperty<>();
        this.minQty = new SimpleObjectProperty<>();
        this.created = new SimpleObjectProperty<>();
        this.mod = new SimpleObjectProperty<>();
        this.pump = new SimpleObjectProperty<>();
        prixvente= new SimpleObjectProperty<>();
    }

    public final ObjectProperty<Produits> getProduitProperty() {
        return produit;
    }

    public void setProduit(Produits produit) {
        this.produit.set(produit);
    }

    public final ObjectProperty<Integer> getQtyProperty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty.set(qty);
    }

    public final ObjectProperty<Integer> getAlert_qtyProperty() {
        return alert_qty;
    }

    public void setAlert_qty(Integer alert_qty) {
        this.alert_qty.set(alert_qty);
    }

    public final ObjectProperty<Integer> getMinQtyProperty() {
        return minQty;
    }

    public void setMinQty(Integer minQty) {
        this.minQty.set(minQty);
    }

    public final ObjectProperty<LocalDate> getCreatedProperty() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created.set(created);
    }

    public final ObjectProperty<LocalDate> getModProperty() {
        return mod;
    }

    public void setMod(LocalDate mod) {
        this.mod.set(mod);
    }

    public Produits getProduit() {
        return produit.get();
    }

    public Integer getQty() {
        return qty.get();
    }

    public Integer getAlert_qty() {
        return alert_qty.get();
    }

    public Integer getMinQty() {
        return minQty.get();
    }

    public LocalDate getCreated() {
        return created.get();
    }

    public LocalDate getMod() {
        return mod.get();
    }

    public ObjectProperty<Double> getPumpProperty() {
        return pump;
    }

    public void setPump(Double pump) {
        this.pump.set(pump);
    }

    public Double getPump() {
        return pump.get();
    }

    public ObjectProperty<Double> getPrixvente() {
        return prixvente;
    }

    public void setPrixvente(Double prixvente) {
        this.prixvente.set(prixvente);
    }
    
    public ObjectProperty<Double> valeurStock(){
        return new SimpleObjectProperty(produit.get().qty_reel_stock().get()*getPump());
    }
}
