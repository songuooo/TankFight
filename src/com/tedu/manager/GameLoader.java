package com.tedu.manager;

import com.tedu.element.ElementObj;
import com.tedu.element.Enemy;
import com.tedu.element.MapObj;
import com.tedu.element.Player;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @说明 游戏加载器（工具类：用于读取配置文件，多为 static 方法）
 *      加载其实就是把元素添加到元素管理器中
 */
public class GameLoader {

    // 获取元素管理器
    private static ElementManager em = ElementManager.getManager();
    // 用于读取文件的类
    private static Properties pro = new Properties();
    // 图片集合
    public static Map<String, ImageIcon> imgMap = new HashMap<>();
    public static Map<String, List<ImageIcon>> imgMaps;
    // 存储类的反射
    private static Map<String, Class<?>> objMap = new HashMap<>();

    /**
     * @说明 传入地图ID，由加载方法根据文件规则自动产生地图。
     * @param mapID
     */
    public static List<ElementObj> LoadMap(int mapID){
        if(mapID == 11){
            return null;
        }

        String mapName = "com/tedu/text/" + mapID + ".map";
        ClassLoader classLoader = GameLoader.class.getClassLoader();
        InputStream maps = classLoader.getResourceAsStream(mapName);
        pro.clear();

        try{
            pro.load(maps);

            List<ElementObj> mapObjs = new ArrayList<>();// 用于返回

            Enumeration<?> names = pro.propertyNames();
            while(names.hasMoreElements()){
                String key = names.nextElement().toString();
                String[] arrs = pro.getProperty(key).split(";");
                for(int i=0;i<arrs.length;i++){
                    ElementObj element = new MapObj().createElement(key + "," + arrs[i]);// 例：GRASS,120,250
                    em.addElement(GameElement.MAPS, element);

                    mapObjs.add(element);
                }
            }

            return mapObjs;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public static void LoadImg(){
        String textUrl = "com/tedu/text/GameData.pro";
        ClassLoader classLoader = GameLoader.class.getClassLoader();
        InputStream texts = classLoader.getResourceAsStream(textUrl);
        pro.clear();

        try{
            pro.load(texts);
            Set<Object> set = pro.keySet();// 类似字典的键
            for(Object o:set){
                String url = pro.getProperty(o.toString());// 通过键获取值
                imgMap.put(o.toString(), new ImageIcon(url));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void LoadPlayer(){
        String PlayerStr = "500,500,up";// {X,Y,fx}

        //这一段相当于 new Play()
        //解耦，降低代码和代码之间的耦合度，可以直接通过接口或者是父类就可以获取到实体对象
        LoadObj();
        Class<?> class1 = objMap.get("Player");
        ElementObj obj = null;
        try{
            Object newInstance = class1.newInstance();
            if(newInstance instanceof ElementObj){
                obj = (ElementObj) newInstance;// 这个对象就和 new Play() 等价
            }
        } catch(InstantiationException e){
            e.printStackTrace();
        } catch(IllegalAccessException e){
            e.printStackTrace();
        }

//        em.addElement(GameElement.PLAY, new Player().createElement(PlayerStr));
        em.addElement(GameElement.PLAYER, obj.createElement(PlayerStr));
    }

    public static void LoadEnemy(List<ElementObj> elementObjs) {
        String EnemyStr = "0,0,up";

        for(int i=0;i<5;i++) {
            em.addElement(GameElement.ENEMY, new Enemy(elementObjs).createElement(EnemyStr));
        }
    }

    // 读取配置文件里的类载入objMap
    public static void LoadObj(){
        String textUrl = "com/tedu/text/Obj.pro";
        ClassLoader classLoader = GameLoader.class.getClassLoader();
        InputStream texts = classLoader.getResourceAsStream(textUrl);
        pro.clear();

        try{
            pro.load(texts);
            Set<Object> set = pro.keySet();// 类似字典的键
            for(Object o:set){
                String classUrl = pro.getProperty(o.toString());// 通过键获取值
                // 使用反射的方式获取类
                Class<?> forName = Class.forName(classUrl);
                objMap.put(o.toString(), forName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
