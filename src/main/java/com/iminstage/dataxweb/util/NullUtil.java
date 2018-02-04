package com.iminstage.dataxweb.util;

import org.apache.commons.lang3.StringUtils;

public class NullUtil {
    public static String nvl(String str, String defaultvalue) {
        if (StringUtils.isBlank(str))
            return defaultvalue;
        else
            return str;
    }

}
