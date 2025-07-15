package com.tedu.element;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.show.GameJFrame;

import java.awt.*;
import java.util.List;

public class EnemyBullet extends ElementObj {
    private int attack; // 攻击力
    private int speed = 3; // 移动速度值
    private String fx;// 方向

    public EnemyBullet() {
        this.attack = 1;
        this.setHP(1);
    }

    @Override
    public ElementObj createElement(String str) {
        String[] split = str.split(",");
        for (String str1 : split) {
            String[] split2 = str1.split(":");
            switch (split2[0]) {
                case "bx":
                    this.setX(Integer.parseInt(split2[1]));
                    break;
                case "by":
                    this.setY(Integer.parseInt(split2[1]));
                    break;
                case "fx":
                    this.fx = split2[1];
                    break;
            }
        }
        this.setW(10);
        this.setH(10);
        return this;
    }

    @Override
    public void showElement(Graphics g) {
        g.setColor(Color.GRAY);
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

        // 保存当前位置
        int oldX = this.getX();
        int oldY = this.getY();

        // 移动子弹
        switch (this.fx) {
            case "up":
                this.setY(this.getY() - this.speed);
                break;
            case "left":
                this.setX(this.getX() - this.speed);
                break;
            case "right":
                this.setX(this.getX() + this.speed);
                break;
            case "down":
                this.setY(this.getY() + this.speed);
                break;
        }

        // 检查是否与墙壁碰撞
        ElementManager em = ElementManager.getManager();
        List<ElementObj> maps = em.getElementByKey(GameElement.MAPS);
        for (ElementObj mapObj : maps) {
            if (this.pk(mapObj)) {
                // 发生碰撞，恢复原来的位置并设置子弹为死亡状态
                this.setX(oldX);
                this.setY(oldY);
                this.setLife(false);
                return;
            }
        }
    }
}
