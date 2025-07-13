package com.tedu.element;

import com.tedu.manager.ElementManager;

import javax.swing.*;
import java.awt.*;

public class MapObj extends ElementObj {

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
            case "GRASS":icon = new ImageIcon("image/wall/grass.png"); break;
            case "BRICK":icon = new ImageIcon("image/wall/brick.png"); break;
            case "RIVER":icon = new ImageIcon("image/wall/river.png"); break;
            case "IRON":icon = new ImageIcon("image/wall/iron.png"); break;
        }
        this.setX(Integer.parseInt(arr[1]));
        this.setY(Integer.parseInt(arr[2]));
        this.setW(icon.getIconWidth());
        this.setH(icon.getIconHeight());
        this.setIcon(icon);
        return this;
    }
}
