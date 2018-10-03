/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.objets;

import java.util.Date;
import java.util.Set;

/**
 *
 * @author Padovano
 */
public class Style extends beans{
    private String style, proto, styfam,proto_fam, brand,gender;
    private Date Created;
    private Set<Parts> parts;

    public Style() {
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getProto() {
        return proto;
    }

    public void setProto(String proto) {
        this.proto = proto;
    }

    public String getStyfam() {
        return styfam;
    }

    public void setStyfam(String styfam) {
        this.styfam = styfam;
    }

    public String getProto_fam() {
        return proto_fam;
    }

    public void setProto_fam(String proto_fam) {
        this.proto_fam = proto_fam;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getCreated() {
        return Created;
    }

    public void setCreated(Date Created) {
        this.Created = Created;
    }

    public Set<Parts> getParts() {
        return parts;
    }

    public void setParts(Set<Parts> parts) {
        this.parts = parts;
    }
    
    
}
