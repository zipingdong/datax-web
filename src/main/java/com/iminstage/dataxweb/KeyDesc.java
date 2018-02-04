package com.iminstage.dataxweb;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class KeyDesc {
    private static Properties prop = new Properties();

    static {
        try {
            prop.load(new InputStreamReader(KeyDesc.class.getClassLoader().getResourceAsStream("KeyDesc.properties"), "UTF-8"));
        } catch (IOException e) {
        }
    }

    public static String get(String key) {
        if (StringUtils.isBlank(key))
            return null;

        if (prop.containsKey(key))
            return prop.getProperty(key);

        return null;
    }
}
