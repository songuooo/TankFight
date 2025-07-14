package com.tedu.controller;

import com.tedu.element.ElementObj;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.show.GameMainJPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @说明 监听类，用于监听用户的操作 KeyListener
 * @author songuooo
 */
public class GameListener implements KeyListener {

    private ElementManager em = ElementManager.getManager();
    private GameMainJPanel jp;

    private Set<Integer> set = new HashSet<Integer>();// 存储按键Code值，使按键作为位移开关

    public GameListener(GameMainJPanel jp) {
        super();

        this.jp = jp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * 左37上38右39下40
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        // 使一次按键只能触发一次位移，即让按键改变移动属性的状态，只作为移动的开关
        int key = e.getKeyCode();

        if(jp.isGameover() && key == KeyEvent.VK_SPACE){
            jp.restartGame();
            return;
        }

        if(set.contains(key)){
            return;
        }
        set.add(key);

        List<ElementObj> play = em.getElementByKey(GameElement.PLAYER);// 通过元素管理器获取玩家元素
        for(ElementObj obj:play){
            obj.KeyClick(true, e.getKeyCode());
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if(!set.contains(key)){
            return;
        }
        set.remove(key);

        List<ElementObj> play = em.getElementByKey(GameElement.PLAYER);
        for(ElementObj obj:play){
            obj.KeyClick(false, e.getKeyCode());
        }
    }
}
