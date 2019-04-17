/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalDate;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Padovano
 */
public class Produits extends item implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private IntegerProperty qty;
    
    private StringProperty nom;
    
    private StringProperty description;
    
    private StringProperty code,categorie,codebar;
    
    private DoubleProperty prix_achat,prix_vente,tva,benefice;
    
    private ObservableList<Categorie> categorieCollection;
    
    private ObservableList<CommandeLine> commandeLineCollection;
    
    private ObservableList<Achat_line> achatTransacCollection;
    
    private ObservableList<PrixProduit> prixProduitCollection;
    
    private ObservableList<image_produit> imageProuit;
    private ObjectProperty<LocalDate> created;
    
    private ObjectProperty<Stocks> stocks;
    
    private ObjectProperty<Suppliers> supId;

    public Produits() {
        categorieCollection=FXCollections.observableArrayList();
        commandeLineCollection=FXCollections.observableArrayList();
        achatTransacCollection=FXCollections.observableArrayList();
        prixProduitCollection=FXCollections.observableArrayList();
        created=new SimpleObjectProperty<>();
        imageProuit=FXCollections.observableArrayList();
        this.qty =new SimpleIntegerProperty(0);
        this.nom = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.code = new SimpleStringProperty();
        categorie=new SimpleStringProperty();
        prix_achat=new SimpleDoubleProperty(0);
        prix_vente=new SimpleDoubleProperty(0);
        tva=new SimpleDoubleProperty(0);
        benefice=new SimpleDoubleProperty(0);
        codebar=new SimpleStringProperty();
        stocks=new SimpleObjectProperty<>();
        supId=new SimpleObjectProperty<>();
    }

    public Produits(IntegerProperty proId) {
        this.id = proId;
        categorieCollection=FXCollections.observableArrayList();
        commandeLineCollection=FXCollections.observableArrayList();
        achatTransacCollection=FXCollections.observableArrayList();
        prixProduitCollection=FXCollections.observableArrayList(); 
    }

    public Produits(int proId, String nom, String description, String code) {
        this.id =new SimpleIntegerProperty(proId);
        this.nom = new SimpleStringProperty(nom);
        this.description = new SimpleStringProperty(description);
        this.code = new SimpleStringProperty(code);
    }

    public IntegerProperty getQtyProperty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty.set(qty);
    }
    
    public StringProperty getPropertyNom() {
        return nom;
    }

    public StringProperty getPropertyDescription() {
        return description;
    }

    public StringProperty getPropertyCode() {
        return code;
    }

    public StringProperty getPropertyCategorie() {
        return categorie;
    }

    public Suppliers getSupId() {
        return supId.get();
    }

    public void setSupIdProperty(Suppliers supId) {
        this.supId.set(supId);
    }
    
    public ObjectProperty<LocalDate> getPropertyCreated() {
        return created;
    }

    public ObjectProperty<Suppliers> getSupIdProperty() {
        return supId;
    }

    
    public String getNom() {
        return nom.get();
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public ObservableList<image_produit> getImageProuit() {
        return imageProuit;
    }

    public void setImageProuit(ObservableList<image_produit> imageProuit) {
        this.imageProuit = imageProuit;
    }

    public StringProperty getCategorie() {
        categorie.set("Aucun");
        if(getCategorieCollection()!=null && !getCategorieCollection().isEmpty()){
            categorie.set("");
            for(Categorie cat:getCategorieCollection()){
                categorie.set(cat.getCategorie()+" , ");
                System.out.println("ct:"+cat.getCategorie());
            }
            categorie.set(categorie.get().substring(0, categorie.get().lastIndexOf(" , ")));
        }
        
        return categorie;
    }

    
    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getCode() {
        return code.get();
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public ObjectProperty<LocalDate> getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created.set(created);
    }

    @XmlTransient
    public ObservableList<Categorie> getCategorieCollection() {
        return categorieCollection;
    }

    public void setCategorieCollection(ObservableList<Categorie> categorieCollection) {
        this.categorieCollection = categorieCollection;
    }

    @XmlTransient
    public ObservableList<CommandeLine> getCommandeLineCollection() {
        return commandeLineCollection;
    }

    public void setCommandeLineCollection(ObservableList<CommandeLine> commandeLineCollection) {
        this.commandeLineCollection = commandeLineCollection;
    }

    @XmlTransient
    public ObservableList<Achat_line> getAchatTransacCollection() {
        return achatTransacCollection;
    }

    public void setAchatTransacCollection(ObservableList<Achat_line> achatTransacCollection) {
        this.achatTransacCollection = achatTransacCollection;
    }

    @XmlTransient
    public ObservableList<PrixProduit> getPrixProduitCollection() {
        return prixProduitCollection;
    }

    public void setPrixProduitCollection(ObservableList<PrixProduit> prixProduitCollection) {
        this.prixProduitCollection = prixProduitCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Produits)) {
            return false;
        }
        Produits other = (Produits) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return this.getNom();
    }

    public StringProperty getCodebarProperty() {
        return codebar;
    }

    public void setCodebar(String codebar) {
        this.codebar.set(codebar);
    }

    public DoubleProperty getPrix_achatProperty() {
        return prix_achat;
    }

    public void setPrix_achat(Double prix_achat) {
        this.prix_achat.set(prix_achat);
    }

    public final DoubleProperty getPrix_venteProperty() {
        return prix_vente;
    }

    public void setPrix_vente(Double prix_vente) {
        this.prix_vente.set(prix_vente);
    }

    public String getCodebar() {
        return codebar.get();
    }

    public Double getPrix_achat() {
        return prix_achat.get();
    }

    public Double getPrix_vente() {
        return prix_vente.get();
    }

    public final Stocks getStocks() {
        return stocks.get();
    }

    public void setStocks(Stocks stocks) {
        this.stocks.set(stocks);
    }

    public ObjectProperty<Stocks> getStocksProperty() {
        return stocks;
    }
    
    public ObjectProperty<Integer> qty_achat(){
        int qt=0;
        for(Achat_line ach:achatTransacCollection)
            qt+=ach.getQty();
        return new SimpleObjectProperty(qt);
    }
    
    public ObjectProperty<Integer> qty_vente(){
        int qt=0;
        for(CommandeLine cmd:commandeLineCollection)
            qt+=cmd.getQty();
        return new SimpleObjectProperty(qt);
    }
    
    public ObjectProperty<Integer> qty_reel_stock(){
        return new SimpleObjectProperty(qty.get()+qty_achat().get()-qty_vente().get());
    }
    
    public boolean isAlert(){
        return qty_reel_stock().get()<=stocks.get().getAlert_qty();
    }

    public DoubleProperty getTva() {
        return tva;
    }

    public void setTva(Double tva) {
        this.tva.set(tva);
    }

    public DoubleProperty getBenefice() {
        return benefice;
    }

    public void setBenefice(Double benefice) {
        this.benefice.set(benefice);
    }
    
    
}
