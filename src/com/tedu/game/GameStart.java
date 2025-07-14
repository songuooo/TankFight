package com.tedu.game;

import com.tedu.controller.GameListener;
import com.tedu.controller.GameThread;
import com.tedu.show.GameJFrame;
import com.tedu.show.GameMainJPanel;

/*JAVA开发实现思考的应该是：做继承或接口实现*/
/*监听负责改变状态，主线程负责改变数据*/
/*需要思考，父类该怎么抽象，子类该怎么实现*/
// 重写的方法的访问修饰符只能比父类更加宽泛
// 重写的方法所抛出的异常不能比父类更加宽泛
public class GameStart {
    /**
     * 程序唯一入口
     */
    public static void main(String[] args) {
        // 实例化
        GameJFrame jf = new GameJFrame();
        GameMainJPanel jp = new GameMainJPanel();
        GameListener listener = new GameListener(jp);
        GameThread mainth = new GameThread(jp);
        // 注入
        jf.setjPanel(jp);
        jf.setKeyListener(listener);
        jf.setThread(mainth);
        // 启动
        jf.start();
    }
}
