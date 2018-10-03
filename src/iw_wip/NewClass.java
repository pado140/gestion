/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iw_wip;

import java.awt.LayoutManager;
import javax.swing.JPanel;

/**
 *
 * @author Padovano
 */
public class NewClass extends JPanel{

    public NewClass(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    public NewClass(LayoutManager layout) {
        super(layout);
    }

    public NewClass(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    //default
    public NewClass() {
    }
    
}
