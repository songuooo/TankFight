package com.tedu.controller;

import com.tedu.element.ElementObj;
import com.tedu.element.Enemy;
import com.tedu.element.Player;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoader;
import com.tedu.show.GameMainJPanel;

import javax.swing.*;
import java.util.List;
import java.util.Map;

/**
 * @说明 游戏的主线程：用于控制游戏加载，切换游戏关卡，游戏运行时自动化，游戏判定，资源释放和重新读取
 * @author songuooo
 */
public class GameThread extends Thread {

    private ElementManager em;
    private GameMainJPanel jp;
    private int currentLevel;
    private long gameTime;// 全局时间（数据刷新次数）

    public GameThread(GameMainJPanel jp){
        em = ElementManager.getManager();// 获取元素管理器对象
        this.currentLevel = 1;// 初始关卡为 1
        this.jp = jp;
        this.gameTime = 0;
    }

    @Override
    public void run() {
        while(true){
            // 游戏开始前
            gameLoad(currentLevel);
            // 游戏进行时
            gameRun(jp);
            // 游戏场景结束
            gameOver();

            System.out.println(em.getElementByKey(GameElement.ENEMY).isEmpty());

            try{
                Thread.sleep(50);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    private void gameLoad(int currentLevel){
        GameLoader.LoadImg(); //加载图片
        List<ElementObj> elementObjs = GameLoader.LoadMap(currentLevel);//可以变为 变量，每一关重新加载  加载地图
//		加载主角
        GameLoader.LoadPlayer();//也可以带参数，单机还是2人
//		加载敌人
        GameLoader.LoadEnemy(elementObjs);
//		加载敌人NPC等
    }

    /**
     * @任务说明 1.自动化玩家的移动、碰撞、死亡
     *         2.新元素的增加（比如NPC死亡后出现道具）
     *         3.游戏暂停等待...
     */
    private void gameRun(GameMainJPanel jp){
        while(true){
            Map<GameElement, List<ElementObj>> all = em.getGameElements();// Map：Key-Value，Key是无序不可重复的
            List<ElementObj> enemys = em.getElementByKey(GameElement.ENEMY);

            List<ElementObj> player = em.getElementByKey(GameElement.PLAYER);
            List<ElementObj> playBullets = em.getElementByKey(GameElement.PLAYBULLET);
            List<ElementObj> maps = em.getElementByKey(GameElement.MAPS);

            MoveAndUpdate(all, gameTime);

            ElementPK(playBullets, enemys, jp);
            ElementPK(playBullets, maps, jp);// 问题需解决：1.子弹与水碰撞无事发生；2.子弹与铁块碰撞子弹消失
            ElementPK(enemyBullets, player, jp);
            ElementPK(enemyBullets, maps, jp);

            gameTime++;// 唯一的时间控制

            // 数据刷新率
            try{
                Thread.sleep(5);
            } catch(Exception e){
                e.printStackTrace();
            }

            if (enemys.isEmpty()) {
                currentLevel++;
                em.clearGameElements();
                gameLoad(currentLevel);
            }
        }
    }

    /*游戏元素自动化方法*/
    private void MoveAndUpdate(Map<GameElement, List<ElementObj>> all, long gameTime){
        for(GameElement ge:GameElement.values()){// values（）是隐藏方法，返回值是一个数组，数组的顺序是定义枚举的顺序
            List<ElementObj> list = all.get(ge);
            for(int i=0; i<list.size(); i++){
                ElementObj obj = list.get(i);
                if(!obj.getLife()){// 死亡
                    obj.die();
                    list.remove(i--);// list删除一个元素会将后面的元素往前移，回退i防止漏判定
                }else{// 存活
                    // 进行移动动作
                    obj.model(gameTime);
                }
            }
        }
    }

    // 实时碰撞检测（GE1与GE2）并处理（GE1对GE2造成伤害）
    private void ElementPK(List<ElementObj> GE1,  List<ElementObj> GE2, GameMainJPanel jp){
        for(ElementObj ge1:GE1){
            for(ElementObj ge2:GE2){
                if(ge1.pk(ge2)){
                    // 扩展，给子弹设置伤害，给元素设置生命值，建立一个受攻击方法，生命值归零存货状态为false
                    ge1.reduceHP(1);
                    ge2.reduceHP(1);
                    jp.setScore();
                }
            }
        }
    }

    private void gameOver(){
        if (!em.getElementByKey(GameElement.ENEMY).isEmpty()) { // 判断敌人列表是否为空
            return;
        }

        // 重置游戏状态，例如清空敌人列表、重新加载主角等
        em.clearGameElements(); // 清空所有元素
        gameLoad(++currentLevel); // 加载下一关的元素

        System.out.println("通过关卡"+currentLevel);
    }
}

