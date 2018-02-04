package com.iminstage.dataxweb.controller;

import com.iminstage.dataxweb.constant.Constant;
import com.iminstage.dataxweb.util.FileUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
public class DownloadController {

    @ResponseBody
    @RequestMapping(value = "/download", produces = "application/json;charset=UTF-8")
    public String pluginTemplate(@RequestParam("jobname") String jobname) {
        File jobFile = new File(Constant.DATAX_JSON_HOME, jobname + ".json");

        return FileUtil.read(jobFile);
    }

}