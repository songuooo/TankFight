package com.tedu.manager;

import com.tedu.element.ElementObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @说明 元素管理器，专门存储所有元素，同时，提供方法让视图和控制获取数据
 * @说明 使用list（有序） map set（无序）存储数据
 */
public class ElementManager {
    /**
     * 所有元素都存放到map集合中
     * 枚举类型当作map的key来区分不同的元素资源
     * Map与List是抽象类
     */
    private Map<GameElement, List<ElementObj>> gameElements;

    private ElementManager(){
        init();
    }

    /**
     * ???
     * 构造方法不能被继承，本方法为将来可能出现的功能扩展，重写init方法准备
     */
    public void init(){
        gameElements = new HashMap<GameElement, List<ElementObj>>();
        // 将每种元素集合都放入到map中
        for(GameElement ge:GameElement.values()){
            gameElements.put(ge, new ArrayList<ElementObj>());
        }
    }

    public Map<GameElement, List<ElementObj>> getGameElements(){
        return gameElements;
    }

    public List<ElementObj> getElementByKey(GameElement ge){
        return gameElements.get(ge);
    }

    /**
     * 单例模式：内存中有且仅有一个实例
     * 饿汉模式-是启动就自动加载实例
     * 饱汉模式-是需要使用的时候才加载实例
     *
     * 编写方式：
     * 1.需要一个静态的属性（定义一个常量） 单例的引用
     * 2.提供一个静态的方法（返回这个实例） return单例的引用
     * 3.一般为防止其他人使用（类可以实例化），所以会私有化构造方法
     */
    private static ElementManager EM = null;

    // 饱汉模式
    // synchronized线程锁，保证本方法执行中只有一个线程
    public static synchronized ElementManager getManager(){
        if(EM == null){
            // 视图和控制会同时访问该方法，多线程加上线程间交替并行会造成有两个实例
            EM = new ElementManager();
        }

        return EM;
    }

    // 饿汉模式
//    static{// 静态语句块：在类被加载时直接运行一次
//        EM = new ELementManeger();
//    }

    public void addElement(GameElement ge, ElementObj obj){// 主要由加载器调用
        List<ElementObj> list = gameElements.get(ge);
        if(list == null) return;
        list.add(obj);
    }

    public void clearGameElements() {
        for(GameElement ge:GameElement.values()) {
            gameElements.get(ge).clear();
        }
    }
}
