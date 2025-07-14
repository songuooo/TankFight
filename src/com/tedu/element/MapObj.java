package com.tedu.element;

import com.tedu.manager.ElementManager;

import javax.swing.*;
import java.awt.*;

public class MapObj extends ElementObj {

    private String name;
    private int score;

    public MapObj() {
        this.setHP(1);
        this.score = 1;
    }

    @Override
    public void showElement(Graphics g){
        g.drawImage(this.getIcon().getImage(),
                this.getX(), this.getY(),
                this.getW(), this.getH(), null);
    }

    @Override
    public ElementObj createElement(String str){
        String[] arr = str.split(",");// 地图元素,X,Y
        ImageIcon icon = null;// 地图元素图片
        switch (arr[0]){
            case "GRASS":icon = new ImageIcon("image/wall/grass.png");
            this.name = "grass";
            break;

            case "BRICK":icon = new ImageIcon("image/wall/brick.png");
            this.name = "brick";
            break;

            case "RIVER":icon = new ImageIcon("image/wall/river.png");
            this.name = "river";
            break;

            case "IRON":icon = new ImageIcon("image/wall/iron.png");
            this.name = "iron";
            this.setHP(4);
            break;
        }
        this.setX(Integer.parseInt(arr[1]));
        this.setY(Integer.parseInt(arr[2]));
        this.setW(icon.getIconWidth());
        this.setH(icon.getIconHeight());
        this.setIcon(icon);
        return this;
    }

    public void setLive(boolean TF) {
        this.setHP(this.getHP() - 1);
        if(this.getHP() >0) {
            return;
        }

        super.setLife(TF);
    }
}
