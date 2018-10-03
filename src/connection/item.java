package connection;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author LCRT
 */
public class item {

        private int id;  
        private String description;  
  
        public item(int id, String description)  
        {  
            this.id = id;  
            this.description = description;  
        }  
  
        public int getId()  
        {  
            return id;  
        }  
  
        public String getDescription()  
        {  
            return description;  
        }  
  
        public String toString()  
        {  
            return description;  
        }  
    
}
