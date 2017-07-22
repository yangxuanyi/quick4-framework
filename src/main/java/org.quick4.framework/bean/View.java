package org.quick4.framework.bean;

import java.util.Map;

/**
 * 返回视图对象
 */
public class View {
    /**
     * 视图路径
     */
    private String path;
    /**
     * 模型数据
     */
    private Map<String,Object> model;




    public View(String path, Map<String, Object> model) {
        this.path = path;
        this.model = model;
    }

    public View(String path) {
        this.path = path;
    }

    public View addModel(String key, Object value){
        model.put(key,value);
        return this;
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
