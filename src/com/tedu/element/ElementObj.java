package com.tedu.element;

import javax.swing.*;
import java.awt.*;

/**
 * @说明 所有元素的基类
 */
public abstract class ElementObj {
    private int x;
    private int y;
    private int w;
    private int h;
    private ImageIcon icon;// ImageIcon相比Image可以读取图片参数

    private int HP;
    private boolean life;// 可以采用枚举值扩展生存状态，比如隐身、无敌

    public ElementObj() {
        this.life = true;
    }

    public ElementObj(int x, int y, int w, int h, ImageIcon icon) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.icon = icon;
        this.life = true;
    }

    // 封装创建对象方法
    public ElementObj createElement(String str){
        return null;
    }

    /**
     * @说明  抽象方法，显示元素
     * @param g 画笔，用于绘画
     */
    public abstract void showElement(Graphics g);

    /**
     * @说明 父类定义接收键盘事件的方法，需要的子类实现这个方法
     * @扩展 本方法可分为按下和松开两种
     * @param pressed 点击的类型，true按下false松开
     * @param key 触发的键盘code值
     */
    public void KeyClick(boolean pressed, int key) {

    }

    /**
     * @设计模式 模板模式：在模板模式中定义对象执行方法的先后顺序，子类选择性重写方法
     * 1.换装 2.移动 3.发射子弹
     */
    public final void model(long gameTime){
        updateImage(gameTime);
        move();
        shoot(gameTime);
    }

    protected void updateImage(long gameTime){

    }
    protected void move(){

    }
    protected void shoot(long gameTime){

    }

    public void die(){

    }

    /**
     * 只要是VO类，就要为属性生成get和set方法
     */
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getW() {
        return w;
    }
    public void setW(int w) {
        this.w = w;
    }
    public int getH() {
        return h;
    }
    public void setH(int h) {
        this.h = h;
    }
    public ImageIcon getIcon() {
        return icon;
    }
    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public void reduceHP(int damage) {
        if(this.HP <= 0){
            return;
        }

        this.HP -= damage;

        if(this.HP <= 0){
            this.HP = 0;
            this.setLife(false);
        }
    }

    public boolean getLife(){
        return life;
    }
    public void setLife(boolean life) {
        this.life = life;
    }

    /**
     * @说明 本方法实时返回元素的碰撞矩形对象
     * @return
     */
    public Rectangle getRectangle(){
        return new Rectangle(x, y, w, h);
    }

    /**
     * @说明 碰撞方法
     * @return boolean
     */
    public boolean pk(ElementObj obj){
        return this.getRectangle().intersects(obj.getRectangle());
    }
}
