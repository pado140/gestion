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
public class Color extends beans  {
    private Color comp;
    private String description,code;
    private boolean simple;

    public Color() {
    }

    public Color getComp() {
        return comp;
    }

    public void setComp(Color comp) {
        this.comp = comp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSimple() {
        return simple;
    }

    public void setSimple(boolean simple) {
        this.simple = simple;
    }
    
    
}
