package com.kun.utils;

import java.util.Collection;
import java.util.Map;

public class EmptyChecker {

    private EmptyChecker() {
    }

    /**
     * Description: 判断对象是否为空, 空返回true
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
     * Description: 判断对象是否不为空, 空返回false
     */
    public static boolean notEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * Description: 判断数组是否为空, 空返回true
     */
    public static boolean isEmpty(Object[] array) {
        if (array == null || array.length == 0) {
            return true;
        }

        return false;
    }

    /**
     * Description: 判断数组是否不为空, 空返回false
     */
    public static boolean isAnyOneEmpty(Object... obj) {
        for (int i = 0; i < obj.length; i++) {
            boolean temp = isEmpty(obj[i]);
            if (temp) {
                return true;
            }
        }

        return false;
    }

    /**
     * Description: 判断数组是否全部为空, 空返回true
     */
    public static boolean isAllEmpty(Object... obj) {
        for (int i = 0; i < obj.length; i++) {
            boolean temp = notEmpty(obj[i]);
            if (temp) {
                return false;
            }
        }
        return true;
    }
}