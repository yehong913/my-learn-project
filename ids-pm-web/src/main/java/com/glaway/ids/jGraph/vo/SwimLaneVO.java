package com.glaway.ids.jGraph.vo;

/**
 * 级联网络图 泳道对象
 * 
 * @author zhangjie
 * @since 2014/12/10
 */
public class SwimLaneVO {

    private String type; // 0-线 1-矩形

    private String title; // 泳道标题

    private String source; // 起始坐标位置 x,y

    private String target; // 目标坐标位置 x,y

    private String wh; // width&height 只有当type为1时才有值

    public SwimLaneVO() {}

    public SwimLaneVO(String type, String title, String source, String target, String wh) {
        super();
        this.type = type;
        this.title = title;
        this.source = source;
        this.target = target;
        this.wh = wh;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getWh() {
        return wh;
    }

    public void setWh(String wh) {
        this.wh = wh;
    }

}
