package com.wmqe.web.validfx.utils;

public class StringUtil {
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     *
     * @param name
     * @return
     */
    public static String camelCase(String name){
        if(isEmpty(name)) return name;
        if(name.length() < 2) return name;
        return name.substring(0,1).toLowerCase() + name.substring(1);
    }

    /**
     *
     * @param name
     * @return
     */
    public static String pascalCase(String name){
        if(isEmpty(name)) return name;
        if(name.length() < 2) return name;
        return name.substring(0,1).toUpperCase() + name.substring(1);
    }
}
