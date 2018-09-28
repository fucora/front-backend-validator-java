package com.wmqe.web.validfx.models;

import java.util.HashMap;
import java.util.Map;

public abstract class ParameterMap {
    private Map<String,Object> params = new HashMap<>();

    /**
     * 设置参数
     * @param name
     * @param value
     */
    public void setParameter(String name, Object value){
        params.put(name,value);
    }

    public Map<String,Object> getParameters(){
        return  params;
    }

    public Object getParameter(String name){
        if(params.containsKey(name))
            return params.get(name);

        return null;
    }


    public int getParameterInt(String name, int defaultValue){
        if(params.containsKey(name)) {
            Object val = params.get(name);
            if(val == null)
                return defaultValue;
            return Integer.parseInt(val.toString());
        }

        return defaultValue;
    }

    /**
     *
     * @param name
     * @return
     */
    public String getParameterString(String name){
        if(params.containsKey(name)) {
            Object val = params.get(name);
            if(val == null)
                return null;
            return val.toString();
        }

        return null;
    }
}
