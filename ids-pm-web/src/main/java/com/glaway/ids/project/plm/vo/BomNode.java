package com.glaway.ids.project.plm.vo;

import java.io.Serializable;
import java.util.List;


public class BomNode implements Serializable {

    private String id;

    private String name;

    private String code;

    private String pid;

    private List<BomNode> rows;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public List<BomNode> getRows() {
        return rows;
    }

    public void setRows(List<BomNode> rows) {
        this.rows = rows;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
