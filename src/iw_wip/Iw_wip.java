/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package iw_wip;

import java.util.concurrent.Executors;
import view.Principal;

/**
 *
 * @author Duvers
 */
public class Iw_wip {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("Number of cores present: " + cores);
        System.out.println("Using number of threads: " + cores/2);
        Executors.newFixedThreadPool(cores);
        Principal.main(args);
    }
    
}
