/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.objets;

/**
 * associates with Part_master in MAX
 * @author Padovano
 */
public class Sku_Master extends beans{
    private String Part_Num,Description,description2,size,color,style,brand,upc;
    private int style_id;
    private Style Proto;
    private Color col;
    public Sku_Master() {
        super();
    }

    public String getPart_Num() {
        return Part_Num;
    }

    public void setPart_Num(String Part_Num) {
        this.Part_Num = Part_Num;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public int getStyle_id() {
        return style_id;
    }

    public void setStyle_id(int style_id) {
        this.style_id = style_id;
    }

    public Style getProto() {
        return Proto;
    }

    public void setProto(Style Proto) {
        this.Proto = Proto;
    }

    public Color getCol() {
        return col;
    }

    public void setCol(Color col) {
        this.col = col;
    }
    
    
}
