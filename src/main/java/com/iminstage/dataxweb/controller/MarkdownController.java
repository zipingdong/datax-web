package com.iminstage.dataxweb.controller;

import com.iminstage.dataxweb.constant.Constant;
import com.iminstage.dataxweb.util.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MarkdownController {
    private List<String> readerPlugins = new ArrayList<>();
    private List<String> writerPlugins = new ArrayList<>();

    @ResponseBody
    @RequestMapping(value = "/markdown", produces = "text/markdown;charset=UTF-8")
    public String showPlugins(@RequestParam("plugin") String plugin) {
        File file = new File(Constant.DATAX_SRC_HOME, plugin + File.separator + "doc" + File.separator + plugin + ".md");

        String markdown = "";
        if (!file.exists())
            markdown = "找不到插件 " + plugin + " 的文档文件！";
        else
            markdown = FileUtil.read(file);

        return markdown;
    }
}