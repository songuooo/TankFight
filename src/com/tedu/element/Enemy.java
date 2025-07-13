package com.tedu.element;

import com.tedu.show.GameJFrame;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Enemy extends ElementObj{

    public Enemy(){

    }

    @Override
    public ElementObj createElement(String str){
        Random random = new Random();
        this.setX(random.nextInt(GameJFrame.GameX));
        this.setY(random.nextInt(GameJFrame.GameY));

        ImageIcon img = new ImageIcon("image/tank/bot/bot_up.png");
        this.setIcon(img);
        this.setW(img.getIconWidth());
        this.setH(img.getIconHeight());

        return this;
    }

    @Override
    public void showElement(Graphics g){
        g.drawImage(this.getIcon().getImage(),
                this.getX(), this.getY(),
                this.getW(), this.getH(), null);
    }
}
