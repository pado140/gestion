/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.jfoenix.controls.JFXButton;
import javafx.css.StyleableObjectProperty;
import javafx.scene.Node;

/**
 *
 * @author Padovano
 */
public class Button_menu extends JFXButton{

    public Button_menu() {
    }

    public Button_menu(String text) {
        super(text);
    }

    public Button_menu(String text, Node graphic) {
        super(text, graphic);
    }

    @Override
    public void setButtonType(ButtonType type) {
        super.setButtonType(type); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public StyleableObjectProperty<ButtonType> buttonTypeProperty() {
        return super.buttonTypeProperty(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
