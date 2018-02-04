package com.iminstage.dataxweb.bean;

public class Parameter {
    private String keys;
    private String desc;
    private String type;

    public Parameter(String keys, String desc, String type) {
        this.keys = keys;
        this.desc = desc;
        this.type = type;
    }

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
