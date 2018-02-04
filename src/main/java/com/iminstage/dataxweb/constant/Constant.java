package com.iminstage.dataxweb.constant;

import com.iminstage.dataxweb.status.Status;

import java.io.File;

public class Constant {
    // datax安装目录
    public static final String DATAX_HOME = System.getenv("DATAX_HOME");
    // datax源码目录
    public static final String DATAX_SRC_HOME = System.getenv("DATAX_SRC_HOME");
    // datax作业文件保存目录
    public static final String DATAX_JSON_HOME = System.getenv("DATAX_JSON_HOME");

    public static final String DATAX_PLUGIN_HOME = DATAX_HOME + File.separator + "plugin";
    public static final String DATAX_READER_HOME = DATAX_PLUGIN_HOME + File.separator + "reader";
    public static final String DATAX_WRITER_HOME = DATAX_PLUGIN_HOME + File.separator + "writer";

    public static final Status success = new Status(true, "保存成功！");
    public static final Status error1 = new Status(false, "配置错误，请检查是否为空或者有特殊字符！");

}
