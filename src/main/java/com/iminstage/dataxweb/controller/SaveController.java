package com.iminstage.dataxweb.controller;

import com.google.gson.*;
import com.iminstage.dataxweb.bean.Job;
import com.iminstage.dataxweb.constant.Constant;
import com.iminstage.dataxweb.status.Status;
import com.iminstage.dataxweb.util.FileUtil;
import com.iminstage.dataxweb.util.GsonUtil;
import com.iminstage.dataxweb.util.NullUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

@RestController
public class SaveController {

    private String readerName;
    private String writerName;
    private Map<String, String> readerMap = new HashMap<>();
    private Map<String, String> writerMap = new HashMap<>();

    private JsonParser parser = new JsonParser();

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Status pluginTemplate(@RequestParam Map<String, String> requestMap) throws FileNotFoundException {
        readerMap.clear();
        writerMap.clear();

        for (Map.Entry<String, String> entry : requestMap.entrySet()) {
            // oraclereader.parameter.username
            String[] keys = entry.getKey().split("\\.", 2);
            // 非读或者写插件的参数
            if (keys.length != 2)
                continue;
            // dw
            String value = entry.getValue();
            // 值为空，不考虑
            if (StringUtils.isBlank(value))
                continue;

            if (keys[0].endsWith("reader")) {
                readerName = keys[0];
                readerMap.put(keys[1], value);
            } else {
                writerName = keys[0];
                writerMap.put(keys[1], value);
            }
        }
        System.out.println("读插件 \t\t" + readerName);
        System.out.println("写插件 \t\t" + writerName);

        File readerFile = new File(Constant.DATAX_READER_HOME + File.separator + readerName + File.separator + "plugin_job_template.json");
        if (!readerFile.exists())
            return Constant.error1;

        File writerFile = new File(Constant.DATAX_WRITER_HOME + File.separator + writerName + File.separator + "plugin_job_template.json");
        if (!writerFile.exists())
            return Constant.error1;

        JsonObject readerJson = parse(readerMap, new FileReader(readerFile));
        JsonObject writerJson = parse(writerMap, new FileReader(writerFile));

        Job job = new Job();
        Job.JobBean jobBean = new Job.JobBean();

        Job.JobBean.SettingBean settingBean = new Job.JobBean.SettingBean();
        Job.JobBean.SettingBean.SpeedBean speedBean = new Job.JobBean.SettingBean.SpeedBean();
        //if (requestMap.containsKey("speed.channel") && StringUtils.isNotBlank(requestMap.get("speed.channel")))
        speedBean.setChannel(NullUtil.nvl(requestMap.get("speed.channel"), "1"));
        //if (requestMap.containsKey("speed.byte") && StringUtils.isNotBlank(requestMap.get("speed.byte")))
        speedBean.setByteX(Integer.parseInt(NullUtil.nvl(requestMap.get("speed.byte"), "10240")));
        Job.JobBean.SettingBean.ErrorLimitBean errorLimitBean = new Job.JobBean.SettingBean.ErrorLimitBean();
        //if (requestMap.containsKey("errorLimit.record") && StringUtils.isNotBlank(requestMap.get("errorLimit.record")))
        errorLimitBean.setRecord(Integer.parseInt(NullUtil.nvl(requestMap.get("errorLimit.record"), "16")));
        //if (requestMap.containsKey("errorLimit.percentage") && StringUtils.isNotBlank(requestMap.get("errorLimit.percentage")))
        errorLimitBean.setPercentage(Double.parseDouble(NullUtil.nvl(requestMap.get("errorLimit.percentage"), "0.06")));
        settingBean.setSpeed(speedBean);
        settingBean.setErrorLimit(errorLimitBean);
        jobBean.setSetting(settingBean);

        List<Job.JobBean.ContentBean> content = new ArrayList<>();
        Job.JobBean.ContentBean.ReaderBean readerBean = new Job.JobBean.ContentBean.ReaderBean();
        readerBean.setName(readerName);
        readerBean.setParameter(readerJson);
        Job.JobBean.ContentBean.WriterBean writerBean = new Job.JobBean.ContentBean.WriterBean();
        writerBean.setName(writerName);
        writerBean.setParameter(writerJson);
        Job.JobBean.ContentBean contentBean = new Job.JobBean.ContentBean();
        contentBean.setReader(readerBean);
        contentBean.setWriter(writerBean);
        content.add(contentBean);
        jobBean.setContent(content);

        job.setJob(jobBean);

        System.out.println("读 \t\t" + readerJson);
        System.out.println("写 \t\t" + writerJson);
        System.out.println("总 \t\t" + GsonUtil.toJson(job));

        File jobFile = new File(Constant.DATAX_JSON_HOME, requestMap.get("jobname") + ".json");
        return FileUtil.write(jobFile, GsonUtil.toJson(job));
    }

    private JsonObject parse(Map<String, String> pluginMap, FileReader reader) {
        JsonElement element = parser.parse(reader);

        // 获得 根节点 的实际 节点类型
        JsonObject root = element.getAsJsonObject();
        JsonPrimitive name = root.getAsJsonPrimitive("name");
        JsonObject parameter = root.getAsJsonObject("parameter");

        System.out.println("插件名称：" + name.getAsString());

        //System.out.println(parameter.toString());
        parseLoop(pluginMap, "parameter", parameter, parameter.entrySet());
        //JsonPrimitive nameJson = element.getAsJsonPrimitive("name");
        //String name = nameJson.getAsString();

        return parameter;
    }

    private void parseLoop(Map<String, String> pluginMap, String parent, JsonObject jsonObject, Set<Map.Entry<String, JsonElement>> entries) {
        String value;
        for (Map.Entry<String, JsonElement> entry : entries) {
            if (entry.getValue().getClass() == JsonPrimitive.class) {
                System.out.println("aaa\t\t" + parent + "." + entry.getKey() + "\t" + entry.getValue());
                if (pluginMap.containsKey(parent + "." + entry.getKey()))
                    jsonObject.add(entry.getKey(), new JsonPrimitive(pluginMap.get(parent + "." + entry.getKey())));
            } else if (entry.getValue().getClass() == com.google.gson.JsonArray.class) {
                if (entry.getValue().getAsJsonArray().size() == 0) {                        // 叶子数组
                    System.out.println("bbb\t\t" + parent + "." + entry.getKey() + " []");

                    if (pluginMap.containsKey(parent + "." + entry.getKey())) {
                        JsonArray jsonArray = new JsonArray();
                        String[] elements = pluginMap.get(parent + "." + entry.getKey()).split(",");
                        for (String element : elements) {
                            if (StringUtils.isNoneBlank(element.trim()))
                                jsonArray.add(element.trim());
                        }
                        jsonObject.add(entry.getKey(), jsonArray);
                    }
                } else {                                                                    // 有子元素，如connection里面有 jdbcUrl 等
                    Iterator<JsonElement> iterator = entry.getValue().getAsJsonArray().iterator();
                    while (iterator.hasNext()) {
                        JsonObject jsonObjectArr = (JsonObject) iterator.next();
                        parseLoop(pluginMap, parent + "." + entry.getKey(), jsonObjectArr, jsonObjectArr.entrySet());
                    }
                }
            } else if (entry.getValue().getClass() == JsonObject.class) {
                JsonObject jsonObjectArr = (JsonObject) entry.getValue();
                parseLoop(pluginMap, parent + "." + entry.getKey(), jsonObjectArr, jsonObjectArr.entrySet());
            } else {
                System.out.println("未知类型 " + entry.getValue().getClass());
            }
        }
    }

    // 数组的元素，用拼接处理
    private String parseLoopArr(Map<String, String> pluginMap, String parent, Iterator<JsonElement> jsonElementIterator) {
        StringBuffer stringBuffer = new StringBuffer();
        while (jsonElementIterator.hasNext()) {
            JsonElement jsonElement = jsonElementIterator.next();

            if (jsonElement.getClass() == JsonObject.class) {
                parseLoop(pluginMap, parent, jsonElement.getAsJsonObject(), jsonElement.getAsJsonObject().entrySet());
            } else if (jsonElement.getClass() == JsonPrimitive.class) {
                System.out.println("ddd\t\t" + parent + "." + jsonElement.getAsJsonPrimitive().getAsString() + "=====");
                stringBuffer.append(jsonElement.toString() + ",");
                //stringBuffer.append(jsonElement.getAsJsonPrimitive().getAsString() + ",");
            } else if (jsonElement.getClass() == com.google.gson.JsonArray.class) {
                System.out.println("eee\t\t" + jsonElement.toString());
                stringBuffer.append(parseLoopArr(pluginMap, parent, jsonElement.getAsJsonArray().iterator()));
            }
        }

        return stringBuffer.toString();
    }

}