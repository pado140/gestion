/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.manager;

/**
 *
 * @author Padovano
 */
public class ManagerFactory {
    //private final ConnectionSql conn=ConnectionSql.instance(1);
    private static ProduitManager prod;
    private static CategorieManager categ;
    private static imageManager im;
    private static UserManager usm;
    private static SupplierManager supm;
    private static AchatManager ac;
    private static AchatLineManager acl;
    private static StocksManager stc;
    private static CommandeManager com;
    private static CommandeLineManager col;
    private static CustomerManager cus;
    
    
    public static ModelManager createModel(ModelName type){
    ModelManager model=null;
    switch(type){
    case produit:
        if(prod==null)
            prod=new ProduitManager();
        model=prod;
    break;
    case categorie:
        if(categ==null)
            categ=new CategorieManager();
        model=categ;
    break;
    case image:
        if(im==null)
            im=new imageManager();
        model=im;
    break;
    case user:
        if(usm==null)
            usm=new UserManager();
        model=usm;
    break;
    case supplier:
        if(supm==null)
            supm=new SupplierManager();
        model=supm;
    break;
    case achat:
        if(ac==null)
            ac=new AchatManager();
        model=ac;
    break;
    case line_achat:
        if(acl==null)
            acl=new AchatLineManager();
        model=acl;
    break;
    case stock:
        if(stc==null)
            stc=new StocksManager();
        model=stc;
    break;
    
    case commande:
        if(com==null)
            com=new CommandeManager();
        model=com;
    break;
    
    case commandeLine:
        if(col==null)
            col=new CommandeLineManager();
        model=col;
    break;
    
    case customer:
        if(cus==null)
            cus=new CustomerManager();
        model=cus;
    break;
    }
    
    return model;
    }
}
