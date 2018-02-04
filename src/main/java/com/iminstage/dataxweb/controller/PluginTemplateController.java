package com.iminstage.dataxweb.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.iminstage.dataxweb.KeyDesc;
import com.iminstage.dataxweb.bean.Parameter;
import com.iminstage.dataxweb.constant.Constant;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

@RestController
public class PluginTemplateController {
    private JsonParser parser = new JsonParser();

    private List<Parameter> pluginParameters = new ArrayList<>();

    @ResponseBody
    @RequestMapping("/pluginTemplate")
    public List<Parameter> pluginTemplate(@RequestParam("plugin") String plugin) throws FileNotFoundException {
        pluginParameters.clear();

        File file = new File((plugin.endsWith("reader") ? Constant.DATAX_READER_HOME : Constant.DATAX_WRITER_HOME) +
                File.separator + plugin + File.separator + "plugin_job_template.json");

        parse(new FileReader(file));

        return pluginParameters;
    }

    private void parse(FileReader reader) {
        JsonElement element = parser.parse(reader);

        // 获得 根节点 的实际 节点类型
        JsonObject root = element.getAsJsonObject();
        JsonPrimitive name = root.getAsJsonPrimitive("name");
        JsonObject parameter = root.getAsJsonObject("parameter");

        System.out.println("插件名称：" + name.getAsString());

        //System.out.println(parameter.toString());
        parseLoop("parameter", parameter.entrySet());
        //JsonPrimitive nameJson = element.getAsJsonPrimitive("name");
        //String name = nameJson.getAsString();

        System.out.println("========================================================================================================================================");
        System.out.println(root);
        //root.add("name",new JsonPrimitive("ddddddddddddddd"));
        //System.out.println(root);
    }

    private void parseLoop(String parent, Set<Map.Entry<String, JsonElement>> entries) {
        for (Map.Entry<String, JsonElement> entry : entries) {
            if (entry.getValue().getClass() == JsonPrimitive.class) {
                System.out.println("111\t\t" + parent + "." + entry.getKey() + "\t" + entry.getValue());
                pluginParameters.add(new Parameter(parent + "." + entry.getKey(), KeyDesc.get(parent + "." + entry.getKey()), "JsonPrimitive"));
                //System.out.println(parent + "." + entry.getKey() + "\t" + entry.getValue().getAsJsonPrimitive().getAsString());
            } else if (entry.getValue().getClass() == com.google.gson.JsonArray.class) {
                if (entry.getValue().getAsJsonArray().size() == 0) {                        // 叶子数组
                    System.out.println("222\t\t" + parent + "." + entry.getKey() + " []");
                    pluginParameters.add(new Parameter(parent + "." + entry.getKey(), KeyDesc.get(parent + "." + entry.getKey()), "JsonArray"));
                } else {                                                                    // 有子元素，如connection里面有 jdbcUrl 等
                    Iterator<JsonElement> iterator = entry.getValue().getAsJsonArray().iterator();
                    while (iterator.hasNext()) {
                        JsonObject jsonObjectArr = (JsonObject) iterator.next();
                        parseLoop(parent + "." + entry.getKey(), jsonObjectArr.entrySet());
                    }
                }


            } else if (entry.getValue().getClass() == com.google.gson.JsonObject.class) {
                parseLoop(parent + "." + entry.getKey(), ((JsonObject) entry.getValue()).entrySet());
            } else {
                System.out.println("未知类型 " + entry.getValue().getClass());
            }
        }
    }

    // 数组的元素，用拼接处理
    private String parseLoopArr(String parent, Iterator<JsonElement> jsonElementIterator) {
        StringBuffer stringBuffer = new StringBuffer();
        while (jsonElementIterator.hasNext()) {
            JsonElement jsonElement = jsonElementIterator.next();

            if (jsonElement.getClass() == JsonObject.class) {
                parseLoop(parent, jsonElement.getAsJsonObject().entrySet());
            } else if (jsonElement.getClass() == JsonPrimitive.class) {
                System.out.println("444\t\t" + parent + "." + jsonElement.getAsJsonPrimitive().getAsString() + "=====");
                stringBuffer.append(jsonElement.toString() + ",");
                //stringBuffer.append(jsonElement.getAsJsonPrimitive().getAsString() + ",");
            } else if (jsonElement.getClass() == com.google.gson.JsonArray.class) {
                System.out.println("555\t\t" + jsonElement.toString());
                stringBuffer.append(parseLoopArr(parent, jsonElement.getAsJsonArray().iterator()));
            }
        }

        return stringBuffer.toString();
    }

}