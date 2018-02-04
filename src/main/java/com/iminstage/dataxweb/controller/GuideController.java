package com.iminstage.dataxweb.controller;

import com.iminstage.dataxweb.constant.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GuideController {
    private List<String> readerPlugins = new ArrayList<>();
    private List<String> writerPlugins = new ArrayList<>();

    @RequestMapping("/guide")
    public String showPlugins(ModelMap modelMap) {
        listPlugin(readerPlugins, new File(Constant.DATAX_READER_HOME));
        listPlugin(writerPlugins, new File(Constant.DATAX_WRITER_HOME));

        modelMap.put("readerPlugins", readerPlugins);
        modelMap.put("writerPlugins", writerPlugins);

        return "guide";
    }

    private void listPlugin(List<String> plugins, File dir) {
        for (File file : dir.listFiles()) {
            plugins.add(file.getName());
        }
    }
}