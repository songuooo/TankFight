package com.tedu.element;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoader;
import com.tedu.show.GameJFrame;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

// 玩家元素类
public class Player extends ElementObj {
    /**
     * 移动属性
     * 1.单属性 配合方向枚举类型使用，一次只能移动一个方向
     * 2.双属性 上下和左右 配合约定（1上/右0不动-1下/左）使用
     * 3.四属性 上下左右 配合boolean（true移动false不动）使用
     */
    private boolean left = false;
    private boolean up = false;
    private boolean right = false;
    private boolean down = false;
    private String fx = "up";// 方向属性，默认朝上
    private boolean atkType = false;// 攻击状态
    private long pTime = 0;

    public Player() {

    }

    @Override
    public ElementObj createElement(String str){
        String[] split = str.split(",");

        ImageIcon img = GameLoader.imgMap.get(split[2]);
        this.setIcon(img);
        this.setW(img.getIconWidth());
        this.setH(img.getIconHeight());

        this.setX(GameJFrame.GameX / 2 + this.getW() * 2);
        this.setY(GameJFrame.GameY - this.getH() * 2);// ???

        return this;
    }

    /**
     * 面向对象第一思想：对象自己的事情自己做，专门的事件用专门的方法做
     * @param g 画笔，用于绘画
     */
    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(),
                this.getW(), this.getH(), null);
    }

    @Override
    public String toString(){// 返回Json格式
        return "bx:"+this.getBulletX()+",by:"+this.getBulletY()+",fx:"+this.fx;
    }

    @Override
    public void KeyClick(boolean pressed, int key) {
        if(pressed){
            switch(key){
                case 37:
                    this.left = true; this.up = false; this.right = false; this.down = false;
                    fx = "left"; break;
                case 38:
                    this.left = false; this.up = true; this.right = false; this.down = false;
                    fx = "up"; break;
                case 39:
                    this.left = false; this.up = false; this.right = true; this.down = false;
                    fx = "right"; break;
                case 40:
                    this.left = false; this.up = false; this.right = false; this.down = true;
                    fx = "down"; break;
                case 32:
                    this.atkType = true; break;
            }
        }else{
            switch(key){
                case 37:this.left = false; break;
                case 38:this.up = false; break;
                case 39:this.right = false; break;
                case 40:this.down = false; break;
                case 32:this.atkType = false; break;
            }
        }
    }

    @Override
    protected void updateImage(long gameTime){
        this.setIcon(GameLoader.imgMap.get(fx));
    }

    @Override
    protected void move(){// 根据移动状态和边界自动移动
        if(this.left && this.getX()>0){
            this.setX(this.getX() - 1);
        }
        if(this.up && this.getY()>0){
            this.setY(this.getY() - 1);
        }
        if(this.right && this.getX()<GameJFrame.GameX - this.getW() - 15){// 注意图片原点在左上角
            this.setX(this.getX() + 1);
        }
        if(this.down && this.getY()<GameJFrame.GameY - this.getH() - 38){
            this.setY(this.getY() + 1);
        }
    }

    /**
     * 子弹间隔：
     * 1.使用本地时间与全局时间做比较，小于1s，将本地时间赋值为全局时间并发射子弹；
     * 2.shoot（）后将atkType改为false，按一次发射一次，手速越快发射越快
     */
    @Override
    public void shoot(long gameTime){
        if(!this.atkType || gameTime - pTime < 40){// 子弹间隔控制
            return;
        }
        pTime = gameTime;

        ElementObj bullet = new PlayerBullet().createElement(this.toString());// 使用子弹类的封装方法创建对象
        ElementManager.getManager().addElement(GameElement.PLAYBULLET, bullet);
    }

    public int getBulletX(){
        return switch (this.fx) {
            case "left" -> this.getX() - 5;
            case "up", "down" -> this.getX() + (this.getW() / 2) - 5;
            case "right" -> this.getX() + this.getW() - 5;
            default -> -1;
        };
    }

    public int getBulletY(){
        return switch (this.fx) {
            case "left", "right" -> this.getY() + (this.getH() / 2) - 5;
            case "up" -> this.getY() - 5;
            case "down" -> this.getY() + this.getH() - 5;
            default -> -1;
        };
    }
}