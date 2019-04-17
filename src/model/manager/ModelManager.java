/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.manager;

import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import observateurs.Observateurs;
import observateurs.Observe;
import static observateurs.Observe.obs;
import services.ConnectionSql;

/**
 *
 * @author Padovano
 * @param <T>
 */
public abstract class ModelManager<T> implements Observe{
    protected ConnectionSql connection;
	protected String requete;
        protected ResultSet resultat;
	protected ObservableList<T> Liste;

	public ModelManager() {
		this.connection = ConnectionSql.instance();
	}

	public ModelManager(ConnectionSql connection) {
		super();
		this.connection = connection;
		Liste=FXCollections.observableArrayList();
	}

	public abstract T ajouter(T objet);
        
        public T save(T objet){
            return null;
        }
	
	public abstract boolean supprimer(T Objet);
	
	public  ObservableList<T> Dynamicrecherche(String objet){
            return null;
        }
	
	public  ObservableList<T> Dynamicsearch(T objet){
            return null;
        }
	
	public abstract ObservableList<T> liste();
	
	public  T search(T objet){
            return null;
        }
        
        public T searchById(int id){
            return null;
        }
	
	public abstract T modifier(T ancien,T nouveau);

    @Override
    public void ajouterObservateur(Observateurs ob) {
        obs.add(ob);
    }

    @Override
    public void retirerObservateur(Observateurs ob) {
        obs.remove(ob);
    }

    @Override
    public void alerter(Object... ob) {
        obs.stream().forEach((o) -> {
            o.executer(ob);
        });
    }
    
    protected void aftersave(T ob){
        
    }
}
