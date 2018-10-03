/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.Manager;

import connection.ConnectionDb;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;
import observateur.Observateurs;
import observateur.Observe;

/**
 *
 * @author Padovano
 * @param <T>
 */
public abstract class ModelManager<T> implements Observe{
    protected ConnectionDb connection;
	protected String requete;
        protected ResultSet resultat;
	protected Set<T> Liste;

	protected ModelManager() {
            this.connection = ConnectionDb.instance();
            Liste=new HashSet<>();
	}

	public ModelManager(ConnectionDb connection) {
		super();
		this.connection = connection;
		Liste=new HashSet<>();
	}
        
	public abstract boolean ajouter(T objet);
	
	public abstract boolean supprimer(T Objet);
	
	public abstract Set<T> Dynamicrecherche(String objet);
	
	public abstract Set<T> Dynamicsearch(T objet);
	
	public abstract Set<T> liste();
	
	public abstract T search(T objet);
        
        public abstract T searchById(int id);
	
	public abstract boolean modifier(T ancien,T nouveau);

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
        
}
