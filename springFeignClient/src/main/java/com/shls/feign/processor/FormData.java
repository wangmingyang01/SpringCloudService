package com.shls.feign.processor;

import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonMap;

/**
 * 处理表单数据，转换成form-data提交格式
 *
 */
public class FormData {
    //附件
    private Map<String, Object> filesData = new HashMap<>();
    //参数
    private Map<String, Object> paramsData = new HashMap<>();

    public FormData(Object form, boolean isIgnoreNull){
        try {
            getFormData(form, "", isIgnoreNull);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过反射获取字段名和值
     *  特殊类型需要重新处理
     */
    private void getFormData(Object obj, String head, boolean isIgnoreNull) throws Exception {
        head = head != null ? head : "";
        Field[] fields=obj.getClass().getDeclaredFields();
        for(int i=0;i<fields.length;i++){
            Object type = fields[i].getType();
            String name = fields[i].getName();
            Object value = getFieldValueByName(name, obj);

            //是否基本类型
            boolean isPrimitive = false;
            try {
                isPrimitive = ((Class<?>) value.getClass().getField("TYPE").get(null)).isPrimitive();
            } catch (Exception e){
                isPrimitive = false;
            }

            if(type.equals(List.class)){
                //处理list
                List list = (List) value;
                for (int k=0; (list!=null && k<list.size()); k++){
                    getFormData(list.get(k), head + name+"["+ k +"].", isIgnoreNull);
                }
            } else if (type.equals(MultipartFile.class)) {
                //处理MultipartFile
                if(value == null){
                    continue;
                }
                MultipartFile file = (MultipartFile) value;
                filesData.putAll(singletonMap(file.getName(), ((Object) file)));
                //System.out.println("file->name:"+ head + name + " ,value:"+value +" ,type:"+type.toString());
            } else if (!isPrimitive
                    && !type.toString().contains("class java.")){
                //自定义封装对象(class java. 为引用或系统封装对象)
                if(value == null){
                    continue;
                }
                getFormData(value, head + name+".", isIgnoreNull);
            } else {
                //是否忽略null
                if (isIgnoreNull && value == null){
                    continue;
                }
                paramsData.put(head + name, value);
                //System.out.println("name:"+ head + name + " ,value:"+value +" ,type:"+type.toString());
            }
        }
    }

    /**
     * 根据属性名获取属性值
     * */
    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, Object> getFilesData() {
        return filesData;
    }

    public void setFilesData(Map<String, Object> filesData) {
        this.filesData = filesData;
    }

    public Map<String, Object> getParamsData() {
        return paramsData;
    }

    public void setParamsData(Map<String, Object> paramsData) {
        this.paramsData = paramsData;
    }
}
