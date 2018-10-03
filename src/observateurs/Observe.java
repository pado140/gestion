/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observateurs;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Padovano
 */
public interface Observe {
    public Set<Observateurs> obs=new HashSet<Observateurs>();
    
    public void ajouterObservateur(Observateurs ob);
    public void retirerObservateur(Observateurs ob);
    
    public void alerter(Object... ob);
   
    
}
