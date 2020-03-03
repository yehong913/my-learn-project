package com.didispace.chapter25.weChat.pojo;

public class Buuton {
    private  String  Name;
    private  String type;
    private  Buuton []sub_button;


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return type;
    }

    public Buuton[] getSub_button() {
        return sub_button;
    }

    public void setSub_button(Buuton[] sub_button) {
        this.sub_button = sub_button;
    }

    public void setType(String type) {
        this.type = type;
    }


}
