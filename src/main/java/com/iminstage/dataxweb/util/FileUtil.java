package com.iminstage.dataxweb.util;

import com.iminstage.dataxweb.constant.Constant;
import com.iminstage.dataxweb.status.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class FileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static Status write(File file, String str) {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(str);
            writer.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
            return new Status(false, e.getMessage());
        }

        return Constant.success;
    }

    public static String read(File file) {
        if (!file.exists()) {
            logger.error("文件不存在！" + file.getPath());
            return null;
        }
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        try {
            return new String(filecontent, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}
