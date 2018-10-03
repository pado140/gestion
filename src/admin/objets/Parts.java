/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.objets;

/**
 *
 * @author Padovano
 */
public class Parts extends beans{
    private int nbre;
    private boolean OTM=false;//not cut on marker
    private String part_name;
    private String len; //length for OTM
    //pr

    public Parts() {
        super();
    }

    public int getNbre() {
        return nbre;
    }

    public void setNbre(int nbre) {
        this.nbre = nbre;
    }

    public boolean isOTM() {
        return OTM;
    }

    public void setOTM(boolean isOTM) {
        this.OTM = isOTM;
    }

    public String getPart_name() {
        return part_name;
    }

    public void setPart_name(String part_name) {
        this.part_name = part_name;
    }

    public String getLen() {
        return len;
    }

    public void setLen(String len) {
        this.len = len;
    }

    @Override
    protected boolean isValid() {
        super.isValid(); //To change body of generated methods, choose Tools | Templates.
        if(isOTM()){
            if(len==null){
                return false;
            }
        }
        return true;
    }
    
    
}
