package com.iminstage.dataxweb.controller;

import com.iminstage.dataxweb.constant.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController {
    private Map<String, File> readerPlugins = new HashMap<String, File>();
    private Map<String, File> writerPlugins = new HashMap<String, File>();

    @RequestMapping("index")
    public String showPlugins(ModelMap modelMap) throws FileNotFoundException {
        listPlugin(readerPlugins, new File(Constant.DATAX_READER_HOME));
        listPlugin(writerPlugins, new File(Constant.DATAX_WRITER_HOME));

        modelMap.put("readerPlugins", readerPlugins);
        modelMap.put("writerPlugins", writerPlugins);

        return "index";
    }

    private void listPlugin(Map<String, File> plugins, File dir) {
        for (File file : dir.listFiles()) {
            plugins.put(file.getName(), new File(file, "plugin_job_template.json"));
        }
    }
}