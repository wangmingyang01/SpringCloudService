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
            //忽略某些属性
            config.setExcludes(strings);
            return JSONArray.fromObject(object, config);
        }

        return JSONArray.fromObject(object);
    }


    /**
     * 替换属性为null的
     * @param array
     * @return
     */
    public static JSONArray replaceNull(JSONArray array){
        if(array == null)
            return array;
        String arrayStr = array.toString();
        //替换
        arrayStr = arrayStr.replaceAll("\":null","\":{}");
        JSONArray arrayNew = JSONArray.fromObject(arrayStr);
        return arrayNew;
    }

}
