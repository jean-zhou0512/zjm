package com.example.srm.pojo;

/**
 * @ClassName: TemplateParam
 * @Description:微信推送模版model
 * @author: KingYiFan
 * @date: 2018年3月5日 下午4:16:33
 */
public class wxsmallTemplateParam {
    // 参数名称
    private String name;
    // 参数值
    private String value;
    // 颜色
    private String color;

    public wxsmallTemplateParam(String name, String value, String color) {
        this.name = name;
        this.value = value;
        this.color = color;
    }

    public wxsmallTemplateParam(String name, String value) {
        this.name = name;
        this.value = value;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
