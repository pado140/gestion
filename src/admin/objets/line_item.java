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
public class line_item extends beans{
    private double curqty,orgqty,dueqty,po_price,pbs,cm;
    private String w_order,sku,line,status;
    private Sku_Master part_id;
    private int plan,cut,soabar,pp,sew,mod,first,sec,packing,ship;

    public line_item() {
    }
    
    public double getCurqty() {
        return curqty;
    }

    public void setCurqty(double curqty) {
        this.curqty = curqty;
    }

    public double getOrgqty() {
        return orgqty;
    }

    public void setOrgqty(double orgqty) {
        this.orgqty = orgqty;
    }

    public double getDueqty() {
        return dueqty;
    }

    public void setDueqty(double dueqty) {
        this.dueqty = dueqty;
    }

    public double getPo_price() {
        return po_price;
    }

    public void setPo_price(double po_price) {
        this.po_price = po_price;
    }

    public double getPbs() {
        return pbs;
    }

    public void setPbs(double pbs) {
        this.pbs = pbs;
    }

    public double getCm() {
        return cm;
    }

    public void setCm(double cm) {
        this.cm = cm;
    }

    public String getW_order() {
        return w_order;
    }

    public void setW_order(String w_order) {
        this.w_order = w_order;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Sku_Master getPart_id() {
        return part_id;
    }

    public void setPart_id(Sku_Master part_id) {
        this.part_id = part_id;
    }

    public int getPlan() {
        return plan;
    }

    public void setPlan(int plan) {
        this.plan = plan;
    }

    public int getCut() {
        return cut;
    }

    public void setCut(int cut) {
        this.cut = cut;
    }

    public int getSoabar() {
        return soabar;
    }

    public void setSoabar(int soabar) {
        this.soabar = soabar;
    }

    public int getPp() {
        return pp;
    }

    public void setPp(int pp) {
        this.pp = pp;
    }

    public int getSew() {
        return sew;
    }

    public void setSew(int sew) {
        this.sew = sew;
    }

    public int getMod() {
        return mod;
    }

    public void setMod(int mod) {
        this.mod = mod;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getSec() {
        return sec;
    }

    public void setSec(int sec) {
        this.sec = sec;
    }

    public int getPacking() {
        return packing;
    }

    public void setPacking(int packing) {
        this.packing = packing;
    }

    public int getShip() {
        return ship;
    }

    public void setShip(int ship) {
        this.ship = ship;
    }
    
    
}
