package com.shls.utils;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

public class JsonUtil {

    /**
     * 生成json,忽略对象某些字段
     * @return
     */
    public static JSONArray jsonConfig(Object object,String[] strings){
        if(strings!=null && strings.length>0) {
            JsonConfig config = new JsonConfig();
            config.setExcludes(strings);
            return JSONArray.fromObject(object, config);
        }

        return JSONArray.fromObject(object);
    }
}
