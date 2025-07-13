package com.tedu.show;

import com.tedu.element.ElementObj;
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

    public GameMainJPanel(){
        init();
    }

    public void init()
    {
        em = ElementManager.getManager();// 得到元素管理器对象
    }

    /**
     * @说明 重写用于绘画元素的paint方法(Graphics 画笔的绘画方法）
     * @问题 后绘画会覆盖先绘画
     * @param g  the <code>Graphics</code> context in which to paint
     */
    @Override
    public void paint(Graphics g) {//
        super.paint(g);

        // 获取每一个元素并显示
        Map<GameElement, List<ElementObj>> all = em.getGameElements();// Map：Key-Value，Key是无序不可重复的
        for(GameElement ge:GameElement.values()){// values（）是隐藏方法，返回值是一个数组，数组的顺序是定义枚举的顺序
            List<ElementObj> list = all.get(ge);
            for (int i=0;i<list.size();i++) {
                ElementObj obj = list.get(i);
                obj.showElement(g);
            }
        }
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
}
