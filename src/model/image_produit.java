/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Padovano
 */
public class image_produit implements Serializable{
    private int id,produit_id;
    private String imagePath;
    private final String prefixPath=new File(System.getProperty("user.home")).getPath()+"/gest/Produits/";
    private Produits produit;
    private Date created=new Date();
    private boolean defaut=true;
    private InputStream image;

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }

    public image_produit() {
    }

    public image_produit(int id, int produit_id, String imagePath, Produits produit) {
        this.id = id;
        this.produit_id = produit_id;
        this.imagePath = imagePath;
        this.produit = produit;
    }

    public image_produit(int id, int produit_id, String imagePath) {
        this.id = id;
        this.produit_id = produit_id;
        this.imagePath = imagePath;
        setImagePath(imagePath);
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduit_id() {
        return produit_id;
    }

    public void setProduit_id(int produit_id) {
        this.produit_id = produit_id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = prefixPath+produit.getCode()+"/"+imagePath;
    }

    public Produits getProduit() {
        return produit;
    }

    public void setProduit(Produits produit) {
        this.produit = produit;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public boolean isDefaut() {
        return defaut;
    }

    public void setDefaut(boolean defaut) {
        this.defaut = defaut;
    }
    
    
    
}
