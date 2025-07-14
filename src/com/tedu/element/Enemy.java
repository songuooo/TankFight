package com.tedu.element;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoader;
import com.tedu.show.GameJFrame;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class Enemy extends ElementObj{
    private List<ElementObj> elementObjs;
    private int speedX; // 横向移动速度
    private int speedY; // 纵向移动速度
    private String fx;
    private int score;
    private long oldTime = 0;
    private long shootInterval; // 发射子弹的间隔时间（毫秒）

    public Enemy(List<ElementObj> elementObjs){
        this.elementObjs = elementObjs;
        this.fx = "null";
        this.score = 10; // 初始化得分为0
        this.setHP(1);
    }

    @Override
    public ElementObj createElement(String str){
        ImageIcon img = new ImageIcon("image/tank/bot/bot_up.png");
        this.setIcon(img);
        this.setW(img.getIconWidth());
        this.setH(img.getIconHeight());

        Random ran = new Random();
        int x;
        int y;
        boolean overlap;

        do {
            overlap = false;
            x = ran.nextInt(GameJFrame.GameX - 35); // 在游戏区域内随机生成X坐标
            y = ran.nextInt(GameJFrame.GameY - 35); // 在游戏区域内随机生成Y坐标

            // 检查生成的位置是否与任何现有的MapObj对象重叠
            for (ElementObj obj : elementObjs) {
                if (checkOverlap(x, y, obj.getX(), obj.getY(), obj.getW(), obj.getH())) {
                    overlap = true;
                    break; // 如果发现重叠，跳出循环
                }
            }
        } while (overlap);

        this.setX(x);
        this.setY(y);
        this.speedX = ran.nextInt(2) - 1; // 随机生成水平速度（-1到1）
        if(this.speedX == 0) {
            this.speedX = 1;
        }
        this.speedY = ran.nextInt(2) - 1; // 随机生成垂直速度（-1到1）
        if(this.speedY == 0) {
            this.speedY = 1;
        }
        this.shootInterval = ran.nextInt(300) + 200;

        return this;
    }

    // 辅助方法，用于检查两个矩形是否重叠
    private boolean checkOverlap(int x1, int y1, int x2, int y2, int w2, int h2) {
        return x1 < x2 + w2 && x1 + 35 > x2 && y1 < y2 + h2 && y1 + 35 > y2;
    }

    @Override
    public void showElement(Graphics g){
        g.drawImage(this.getIcon().getImage(),
                this.getX(), this.getY(),
                this.getW(), this.getH(), null);
    }

    @Override
    public String toString(){// 返回Json格式
        return "bx:"+this.getBulletX()+",by:"+this.getBulletY()+",fx:"+this.fx;
    }

    @Override
    protected void move() {
        // 在这里实现敌人的移动逻辑
        // 根据速度值更新敌人的位置
        int updateX = this.getX() + speedX;
        int updateY = this.getY() + speedY;

        // 检查位置是否越界
        if (updateX < 0) {
            updateX = 0;
            speedX = -speedX; // 反转横向速度
        } else if (updateX > GameJFrame.GameX - this.getW()) {
            updateX = GameJFrame.GameX - this.getW();
            speedX = -speedX; // 反转横向速度
        }

        if (updateY < 0) {
            updateY = 0;
            speedY = -speedY; // 反转纵向速度
        } else if (updateY > GameJFrame.GameY - this.getH()-25) {
            updateY = GameJFrame.GameY - this.getH()-25;
            speedY = -speedY; // 反转纵向速度
        }

        // 更新敌人的位置
        this.setX(updateX);
        this.setY(updateY);

        // 限制只能上下左右移动
        if (Math.abs(speedX) > Math.abs(speedY)) {
            speedY = 0; // 将纵向速度置为0
            if (speedX < 0) {
                this.setIcon(new ImageIcon("image/tank/bot/bot_left.png")); // 设置向左移动的图片
            } else {
                this.setIcon(new ImageIcon("image/tank/bot/bot_right.png")); // 设置向右移动的图片
            }
        } else {
            speedX = 0; // 将横向速度置为0
            if (speedY < 0) {
                this.setIcon(new ImageIcon("image/tank/bot/bot_up.png")); // 设置向上移动的图片
            } else {
                this.setIcon(new ImageIcon("image/tank/bot/bot_down.png")); // 设置向下移动的图片
            }
        }

        // 检测碰撞
        for (ElementObj obj : elementObjs) {
            if (this.pk(obj)) {
                // 碰撞发生，随机生成新的速度值
                Random random = new Random();
                speedX = random.nextInt(2) - 1; // 随机生成横向速度，范围为-1到1
                if(speedX == 0) {
                    speedX = 1;
                }
                speedY = random.nextInt(2) - 1; // 随机生成纵向速度，范围为-1到1
                if(speedY == 0) {
                    speedY = 1;
                }

                break;
            }
        }
    }

    @Override
    protected void shoot(long gameTime) {
        if(gameTime - this.oldTime < this.shootInterval){// 子弹间隔控制
            return;
        }
        this.oldTime = gameTime;

        this.fx = getDirection();

        ElementObj bullet = new EnemyBullet().createElement(this.toString());// 使用子弹类的封装方法创建对象
        ElementManager.getManager().addElement(GameElement.ENEMYBULLET, bullet);

    }

    private String getDirection() {
        if (speedX < 0) {
            return "left";
        } else if (speedX > 0) {
            return "right";
        } else if (speedY < 0) {
            return "up";
        } else {
            return "down";
        }
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
