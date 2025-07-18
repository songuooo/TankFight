package com.tedu.show;

import com.tedu.controller.GameThread;
import com.tedu.element.Base;
import com.tedu.element.ElementObj;
import com.tedu.element.Player;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * @说明 游戏的主面板
 * @author songuooo
 * @功能说明 主要进行元素的显示，同时进行界面的刷新（多线程）
 * @多线程刷新 实现线程接口（重写接口Runnable的Run方法） 或 定义一个内部类
 */
public class GameMainJPanel extends JPanel implements Runnable {
    private ElementManager em;
    private static int score;// 总得分
    private static boolean isGameover;

    public GameMainJPanel(){
        init();
    }

    public void init()
    {
        em = ElementManager.getManager();// 得到元素管理器对象
        this.score = 0;
        this.isGameover = false;
    }

    /**
     * @说明 重写用于绘画元素的paint方法(Graphics 画笔的绘画方法）
     * @问题 后绘画会覆盖先绘画
     * @param g  the <code>Graphics</code> context in which to paint
     */
    @Override
    public void paint(Graphics g) {//
        super.paint(g);

        if (isGameover) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("End!", 400, 300);
        } else {
            // 获取每一个元素并显示
            // Map：Key-Value，Key是无序不可重复的
            Map<GameElement, List<ElementObj>> all = em.getGameElements();
            for (GameElement ge : GameElement.values()) {// values（）是隐藏方法，返回值是一个数组，数组的顺序是定义枚举的顺序
                List<ElementObj> list = all.get(ge);
                for (int i = 0; i < list.size(); i++) {
                    ElementObj obj = list.get(i);
                    obj.showElement(g);
                }
            }
        }

        // 计分板
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 10, 30);

        // 血量条
        g.setColor(Color.RED);
        List<ElementObj> plays = em.getElementByKey(GameElement.PLAYER);
        if (!plays.isEmpty()) {
            Player play = (Player) plays.get(0);
            g.drawString("Life: " + play.getHP(), 150, 30);
            if(play.getHP() <= 0) {
                setGameover(true);
                System.out.println("PLAYER DIE");
            }
        }
//        if (plays.isEmpty()) {
//            setGameover(true);
//            System.out.println("PLAYER DIE");
//        }

        List<ElementObj> Base = em.getElementByKey(GameElement.BASE);
        if(!Base.isEmpty()) {
            Base base = (Base) Base.get(0);
            if(base.getHP() <= 0) {
                setGameover(true);
                System.out.println("BASE DIE");
            }
        }
//        if(Base.isEmpty()) {
//            setGameover(true);
//            System.out.println("BASE DIE");
//        }
    }

    @Override
    public void run() {
        while(true){
            this.repaint();

            // 屏幕刷新率
            try{
                Thread.sleep(33);// 单位毫秒，30帧
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void setScore(String str, int num){
        if(str == "+"){
            score += num;
        }

        if(str == "-"){
            score -= num;
        }
    }

    public static void setGameover(boolean TF){// true of false
        isGameover = TF;
    }

    // 新增：获取游戏结束状态的方法
    public boolean isGameover() {
        return isGameover;
    }

    public void restartGame() {
        setGameover(false);
        score = 0;
        em.clearGameElements();
        // 重新加载游戏资源
        new GameThread(this).start();
    }
}
