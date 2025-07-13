package com.tedu.element;

import com.tedu.show.GameJFrame;

import java.awt.*;

/**
 * @说明 玩家子弹类 本类的实体对象由玩家对象调用和创建
 * @author songuooo
 */
public class PlayerBullet extends ElementObj {
    private int atk;
    private int speed;
    private String fx;
    private long pbTime = 0;

    public PlayerBullet(){

    }

    // 当一个类的创建步骤过多时，可以对创建这个对象的过程进行封装，外界只需要传输必要的参数，返回值就是对象实体
    @Override
    public ElementObj createElement(String str){
        // 解析Json格式字符串
        String[] split1 = str.split(",");
        for(String string:split1){
            String[] split2 = string.split(":");
            switch(split2[0]){
                case "bx":this.setX(Integer.parseInt(split2[1])); break;
                case "by":this.setY(Integer.parseInt(split2[1])); break;
                case "fx":this.fx = split2[1]; break;
            }
        }
        this.setW(10);
        this.setH(10);
        this.atk = 1;
        this.speed = 3;
        return this;
    }

    @Override
    public void showElement(Graphics g){
        g.setColor(Color.BLACK);
        g.fillOval(this.getX(), this.getY(), this.getW(), this.getH());
    }

    @Override
    protected void move() {
        // 边界生存判定
        if(this.getX()<0 || GameJFrame.GameX<this.getX() ||
                this.getY()<0 || GameJFrame.GameY<this.getY()){
            this.setLife(false);
            return;
        }

        // 移动控制
        switch(this.fx){
            case "left":this.setX(this.getX() - this.speed); break;
            case "up":this.setY(this.getY() - this.speed); break;
            case "right":this.setX(this.getX() + this.speed); break;
            case "down":this.setY(this.getY() + this.speed); break;
        }
    }
}
