/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Categorie;
import model.Commande;
import model.CommandeLine;
import model.Produits;
import model.Stocks;
import model.Suppliers;
import model.image_produit;
import services.ConnectionSql;

/**
 *
 * @author Padovano
 */
public class ProduitManager extends ModelManager<Produits>{

    public ProduitManager() {
    }

    public ProduitManager(ConnectionSql connection) {
        super(connection);
    }

    @Override
    public Produits ajouter(Produits objet) {
        boolean isSave=false;
        requete="insert into produits(Nom,code,Description,qty,prix_achat,prix_vente,barcode,tva,benefice) values(?,?,?,?,?,?,?,?,?)";
        Object [] ob=new Object[]{objet.getNom(),objet.getCode(),objet.getDescription(),objet.getQtyProperty().get(),
            objet.getPrix_achat(),objet.getPrix_vente(),objet.getCodebar(),
        objet.getTva().get(),objet.getBenefice().get()};
        if(this.connection.Update(requete,1, ob)){
            int pro_id=this.connection.getLastinsert();
            for(image_produit im:objet.getImageProuit()){
                im.setProduit_id(pro_id);
                ManagerFactory.createModel(ModelName.image).ajouter(im);
            }
            String requete1="insert into categorie_produits values(?,?)";
            for(Categorie cat:objet.getCategorieCollection()){
                this.connection.Update(requete1, 1, pro_id,cat.getCatId());
            }
           objet.setId(pro_id);
        }else
            objet=null;
        
         return objet;   
    }

    @Override
    public boolean supprimer(Produits Objet) {
        requete= "delete from produits where pro_id=?";
        return this.connection.Update(requete,0,Objet.getId());
    }

    @Override
    public ObservableList<Produits> Dynamicrecherche(String objet) {
        ObservableList<Produits> deps=FXCollections.observableArrayList();
        requete="select * from produits where dep_nom like ? or dep_code like ?";
        
        
        ResultSet rs=this.connection.select(requete,new Object[]{"%"+objet+"%","%"+objet+"%"});
        try {
            while(rs.next()){
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return deps;
    }

    @Override
    public ObservableList<Produits> Dynamicsearch(Produits objet) {
        return null;
    }

    @Override
    public ObservableList<Produits> liste() {
        ObservableList<Produits> pros=FXCollections.observableArrayList();
        requete="select * from produit";
        
        ResultSet rs=this.connection.select(requete);
        try {
            while(rs.next()){
                Produits p=new Produits();
                p.setId(rs.getInt("pro_id"));
                p.setNom(rs.getString("nom"));
                p.setDescription(rs.getString("description"));
                p.setCode(rs.getString("code"));
                p.setCreated(rs.getDate("date_ajout_produit").toLocalDate());
                p.setPrix_achat(rs.getDouble("prix_achat"));
                p.setPrix_vente(rs.getDouble("prix_vente"));
                p.setTva(rs.getDouble("tva"));
                p.setBenefice(rs.getDouble("benefice"));
                image_produit imp=new image_produit();
                imp.setId(rs.getInt("id"));
                imp.setProduit(p);
                imp.setImagePath(rs.getString("image_path"));
                imp.setImage(rs.getBinaryStream("image"));
                Stocks s=new Stocks();
                s.setQty(rs.getInt("qty"));
                s.setAlert_qty(rs.getInt("alert_qty"));
                s.setMinQty(rs.getInt("minqty"));
                s.setId(rs.getInt("idstock"));
                p.setStocks(s);
                p.setQty(rs.getInt("qty"));
                p.setCategorieCollection(Dynamicsearch(p.getId()));
                p.getImageProuit().add(imp);
                p.setCodebar(rs.getString("barcode"));
                p.setSupIdProperty((Suppliers)ManagerFactory.createModel(ModelName.supplier).searchById(rs.getInt("sup_id")));
                pros.add(p);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pros;
    }

    @Override
    public Produits search(Produits objet) {
        requete="select * from produit where code=?";
        
        ResultSet rs=this.connection.select(requete,objet.getCode());
        try {
            while(rs.next()){
                Produits p=new Produits();
                p.setId(rs.getInt("pro_id"));
                p.setNom(rs.getString("nom"));
                p.setDescription(rs.getString("description"));
                p.setCode(rs.getString("code"));
                p.setCreated(rs.getDate("date_ajout_produit").toLocalDate());
                p.setPrix_achat(rs.getDouble("prix_achat"));
                p.setPrix_vente(rs.getDouble("prix_vente"));
                p.setTva(rs.getDouble("tva"));
                p.setBenefice(rs.getDouble("benefice"));
                image_produit imp=new image_produit();
                imp.setId(rs.getInt("id"));
                imp.setProduit(p);
                imp.setImagePath(rs.getString("image_path"));
                imp.setImage(rs.getBinaryStream("image"));
                Stocks s=new Stocks();
                s.setQty(rs.getInt("qty"));
                s.setAlert_qty(rs.getInt("alert_qty"));
                s.setMinQty(rs.getInt("minqty"));
                s.setId(rs.getInt("idstock"));
                p.setStocks(s);
                p.setCategorieCollection(Dynamicsearch(p.getId()));
                p.getImageProuit().add(imp);
                p.setSupIdProperty((Suppliers)ManagerFactory.createModel(ModelName.supplier).searchById(rs.getInt("sup_id")));
                p.setCodebar(rs.getString("barcode"));
               return p;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Produits modifier(Produits ancien, Produits nouveau) {
        requete="update produits set nom=?,code=?,Description=?,prix_achat=?,prix_vente=?"
                + ",barcode=?,supplier_id=? where pro_id=?";
        Object [] ob=new Object[]{nouveau.getNom(),nouveau.getCode(),nouveau.getDescription()
                ,nouveau.getPrix_achat(),nouveau.getPrix_vente(),nouveau.getCodebar(),nouveau.getSupId().getId(),ancien.getId()};
        if(this.connection.Update(requete,0, ob))
            return nouveau;
        return null;
    }

    @Override
    public Produits searchById(int id) {
        requete="select * from produit where pro_id=?";
        
        ResultSet rs=this.connection.select(requete,id);
        try {
            while(rs.next()){
                Produits p=new Produits();
                p.setId(rs.getInt("pro_id"));
                p.setNom(rs.getString("nom"));
                p.setDescription(rs.getString("description"));
                p.setCode(rs.getString("code"));
                p.setCreated(rs.getDate("date_ajout_produit").toLocalDate());
                image_produit imp=new image_produit();
                imp.setId(rs.getInt("id"));
                imp.setProduit(p);
                imp.setImagePath(rs.getString("image_path"));
                imp.setImage(rs.getBinaryStream("image"));
                p.setAchatTransacCollection(((AchatLineManager)ManagerFactory.createModel(ModelName.line_achat)).search_by_produit(p));
                p.setCommandeLineCollection(vente_Produit(p.getId()));
                p.setCategorieCollection(Dynamicsearch(p.getId()));
                p.getImageProuit().add(imp);
                p.setSupIdProperty((Suppliers)ManagerFactory.createModel(ModelName.supplier).searchById(rs.getInt("sup_id")));
               return p;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return null;
    }

    @Override
    public Produits save(Produits objet) {
        if(objet.getId()==0)
            return ajouter(objet);
        else
            return modifier(objet, objet);
    }
    
    public ObservableList<Categorie> Dynamicsearch(int pro) {
        ObservableList<Categorie> categories=FXCollections.observableArrayList();
        requete="select * from categorie inner join categorie_produits using(cat_id) where pro_id=?";
        
        ResultSet rs=this.connection.select(requete,pro);
        try {
            while(rs.next()){
                Categorie categorie=new Categorie();
                categorie.setCatId(rs.getInt("cat_id"));
                categorie.setCategorie(rs.getString("categorie"));
                
                categories.add(categorie);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategorieManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categories;
    }

    @Override
    protected void aftersave(Produits ob) {
        if(ob.getSupId()!=null){
            modifier(ob, ob);
        }
    }
    
    public ObservableList<CommandeLine> vente_Produit(int pro_id){
        requete="select * from commandelines where pro_id=?";
        ObservableList<CommandeLine> liste=FXCollections.observableArrayList();
        ResultSet rs=connection.select(requete,pro_id);
        try {
            while(rs.next()){
               CommandeLine cl=new CommandeLine();
               cl.setId(rs.getInt("cl_id"));
               cl.setPrix(rs.getDouble("prix"));
               cl.setQty(rs.getInt("qty"));
               Commande c=new Commande();
               c.setId(rs.getInt("com_id"));
               c.setComCode(rs.getString("com_code"));
               c.setCreated(rs.getDate("created").toLocalDate());
               cl.setComId(c);
               
               liste.add(cl);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommandeLineManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return liste;
    }
}
