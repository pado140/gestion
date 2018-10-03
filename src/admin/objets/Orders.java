/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.objets;

import java.util.Date;

/**
 *
 * @author Padovano
 */
public class Orders extends beans{
    private String po;
    private Date po_date,ship_date,x_fact;
    
    
    public Orders() {
        super();
    }

    
    public Date getPo_date() {
        return po_date;
    }

    public void setPo_date(Date po_date) {
        this.po_date = po_date;
    }

    public Date getShip_date() {
        return ship_date;
    }

    public void setShip_date(Date ship_date) {
        this.ship_date = ship_date;
    }

    public Date getX_fact() {
        return x_fact;
    }

    public void setX_fact(Date x_fact) {
        this.x_fact = x_fact;
    }

    public String getPo() {
        return po;
    }

    public void setPo(String po) {
        this.po = po;
    }

    
}
