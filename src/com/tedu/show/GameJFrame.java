package com.tedu.show;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * @说明 游戏窗体，主要实现的功能：显示、关闭、最大最小化
 * @author songuooo
 * @功能说明 需要嵌入面板，启动主线程等
 * @窗体说明 swing awt 窗体大小（需要记录用户上次使用软件的窗体样式）
 * @分析 1.面板绑定到窗体
 *      2.监听绑定
 *      3.游戏主线程启动
 *      4.显示窗体
 */
public class GameJFrame extends JFrame {
    public static int GameX = 775;
    public static int GameY = 600;
    private JPanel jPanel = null;// 面板
    private KeyListener keyListener = null;// 键盘监听
    private MouseMotionListener mouseMotionListener = null;// 鼠标监听
    private MouseListener mouseListener = null;// 鼠标监听
    private Thread mainThread = null;// 游戏主线程

    public GameJFrame() {
        init();
    }

    public void init(){
        this.setSize(GameX, GameY);
        this.setTitle("坦克大战");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 窗体关闭时程序退出
        this.setLocationRelativeTo(null);// 窗体居中显示
    }

    /**
     * set注入（ssm）：通过set方法注入配置文件中读取的数据
     * 构造注入
     * spring中ioc进行对象的自动生成、管理
     */
    public void setjPanel(JPanel jPanel) {
        this.jPanel = jPanel;
        this.jPanel.setBackground(Color.BLACK);
    }

    public void setKeyListener(KeyListener keyListener) {
        this.keyListener = keyListener;
    }

    public void setMouseMotionListener(MouseMotionListener mouseMotionListener) {
        this.mouseMotionListener = mouseMotionListener;
    }

    public void setMouseListener(MouseListener mouseListener) {
        this.mouseListener = mouseListener;
    }

    public void setThread(Thread thread) {
        this.mainThread = thread;
    }

    /**
     * （扩展）
     * 窗体布局 存档、读档等
     */
    public void addButton(){
//        this.setLayout(manager);// 布局格式，可以添加控件
    }

    /**
     * 启动方法
     */
    public void start(){
        if(jPanel != null){
            this.add(jPanel);
        }
        if(keyListener != null){
            this.addKeyListener(keyListener);
        }
        if(mouseMotionListener != null){
            this.addMouseMotionListener(mouseMotionListener);
        }
        if(mouseListener != null){
            this.addMouseListener(mouseListener);
        }
        if(mainThread != null){
            mainThread.start();// 启动主线程
        }

        this.setVisible(true);// 显示窗体

        // ???
        // 如果 jPanel 是 Runnable 的（子类）实体对象（即如果 jPanel 实现了 Runnable 接口），则执行 if 语句，并为 task 赋值 this.jPanel
        if(this.jPanel instanceof Runnable){
            Runnable run=(Runnable)this.jPanel;
            Thread thread=new Thread(run);// 线程启动会执行 this.jPanel 的 run 重写方法
            thread.start();
            System.out.println("启动");
        }
    }
}
