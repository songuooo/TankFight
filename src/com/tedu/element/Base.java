package com.tedu.element;

import com.tedu.show.GameJFrame;

import javax.swing.*;
import java.awt.*;

public class Base extends ElementObj {

    public Base() {
        this.setHP(1);
    }

    @Override
    public ElementObj createElement(String str){
        ImageIcon icon = new ImageIcon("image/wall/base.png");
        this.setIcon(icon);
        this.setW(icon.getIconWidth());
        this.setH(icon.getIconHeight());
        this.setX(360);
        this.setY(521);

        return this;
    }

    @Override
    public void showElement(Graphics g){
        g.drawImage(this.getIcon().getImage(),
                this.getX(), this.getY(),
                this.getW(), this.getH(), null);
    }
}
