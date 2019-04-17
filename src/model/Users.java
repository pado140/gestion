/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Padovano
 */
public class Users implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private IntegerProperty userId;
    
    private StringProperty username,email,password,name,firstname,tel,sex,image;
    
    private ObjectProperty<Date> createTime;
    
    private BooleanProperty connecte;
    String code;

    public Users() {
        userId=new SimpleIntegerProperty(0);
        username=new SimpleStringProperty();
        email=new SimpleStringProperty();
        password=new SimpleStringProperty();
        name=new SimpleStringProperty();
        firstname=new SimpleStringProperty();
        tel=new SimpleStringProperty();
        sex=new SimpleStringProperty();
        image=new SimpleStringProperty();
        createTime=new SimpleObjectProperty<>();
        connecte=new SimpleBooleanProperty(false);
    }

    public Users(SimpleIntegerProperty userId) {
        this.userId = userId;
    }


    public int getUserId() {
        return userId.get();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setUserId(Integer userId) {
        this.userId.set(userId);
    }

    public BooleanProperty getConnecte() {
        return connecte;
    }

    public boolean isConnecte() {
        return connecte.get();
    } 
    
    public void setConnecteProperty(BooleanProperty connecte) {
        this.connecte = connecte;
    }

    public void setConnecte(boolean connecte) {
        this.connecte.set(connecte);
    }
    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public IntegerProperty getUserIdProperty() {
        return userId;
    }

    public void setUserIdProperty(IntegerProperty userId) {
        this.userId = userId;
    }

    public StringProperty getUsernameProperty() {
        return username;
    }

    public void setUsernameProperty(StringProperty username) {
        this.username = username;
    }

    public StringProperty getEmailProperty() {
        return email;
    }

    public void setEmailProperty(StringProperty email) {
        this.email = email;
    }

    public StringProperty getPasswordProperty() {
        return password;
    }

    public void setPasswordProperty(StringProperty password) {
        this.password = password;
    }

    public StringProperty getNameProperty() {
        return name;
    }

    public void setNameProperty(StringProperty name) {
        this.name = name;
    }

    public StringProperty getFirstnameProperty() {
        return firstname;
    }

    public void setFirstnameProperty(StringProperty firstname) {
        this.firstname = firstname;
    }

    public StringProperty getTelProperty() {
        return tel;
    }

    public void setTelProperty(StringProperty tel) {
        this.tel = tel;
    }

    public ObjectProperty<Date> getCreateTime() {
        return createTime;
    }

    public void setCreateTimeProperty(ObjectProperty<Date> createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTimeProperty() {
        return createTime.get();
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getFirstname() {
        return firstname.get();
    }

    public void setFirstname(String firstname) {
        this.firstname.set(firstname);
    }

    public String getTel() {
        return tel.get();
    }

    public void setTel(String tel) {
        this.tel.set(tel);
    }

    public void setCreateTime(Date createTime) {
        this.createTime.set(createTime);
    }

    public StringProperty getSexProperty() {
        return sex;
    }

    public void setSexProperty(StringProperty sex) {
        this.sex = sex;
    }
    
    public String getSex() {
        return sex.get();
    }

    public void setSex(String sex) {
        this.sex.set(sex);
    }
    
    public StringProperty getImageProperty() {
        return image;
    }

    public void setImageProperty(StringProperty image) {
        this.image = image;
    }
    
    public String getImage() {
        return image.get();
    }

    public void setImage(String image) {
        this.image.set(image);
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Users[ userId=" + userId + " ]";
    }
    
}
