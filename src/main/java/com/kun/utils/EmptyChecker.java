package com.kun.utils;

import java.util.Collection;
import java.util.Map;
 
/**
 * ==================================================
 * 〈功能〉：空校验辅助类
 * ==================================================
 */
public class EmptyChecker {
 
    private EmptyChecker() {
    }

     /**
     *========================================
     * @方法说明 ： 空返回true
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null || "".equals(obj.toString())) {
            return true;
        }
 
        if (obj instanceof CharSequence) {
            return ((String) obj).trim().length() == 0;
        }
 
        if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        }
 
        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }
 
        return false;
    }

     /**
     *========================================
     * @方法说明 ： 判断非空 非空返回true
     */
    public static boolean notEmpty(Object obj) {
        return !isEmpty(obj);
    }

     /**
     *========================================
     * @方法说明 ：数组判空 空返回true
     */
    public static boolean isEmpty(Object[] array) {
        if (array == null || array.length == 0) {
            return true;
        }
 
        return false;
    }
 
    /**
     *========================================
     * @方法说明 ： 如果任意一个参数为空 返回true
     */
    public static boolean isAnyOneEmpty(Object ... obj) {
        for (int i = 0; i <obj.length ; i++) {
            boolean temp = isEmpty(obj[i]);
            if (temp){
                return true;
            }
        }
 
        return false;
    }
 
    /**
     *========================================
     * @方法说明 ： 如果所有参数为空 返回true
     */
    public static boolean isAllEmpty(Object ... obj) {
        for (int i = 0; i <obj.length ; i++) {
            boolean temp = notEmpty(obj[i]);
            if (temp){
                return false;
            }
        }
        return true;
    }
}