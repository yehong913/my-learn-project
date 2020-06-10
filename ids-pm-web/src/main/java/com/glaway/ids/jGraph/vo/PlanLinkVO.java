package com.glaway.ids.jGraph.vo;
/**
 * 计划前后关系连线
 * 
 * @author zhangjie
 * @since 2014/12/10
 */
public class PlanLinkVO {

    private String source; // 源

    private String target; // 目标

    private String color; // 颜色

    private String[] point; // 折点 {'x,y', 'x,y', ...}

    private String type; // 类型 fs/ff/sf

    public PlanLinkVO() {}

    public PlanLinkVO(String source, String target, String color, String[] point, String type) {
        super();
        this.source = source;
        this.target = target;
        this.color = color;
        this.point = point;
        this.type = type;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String[] getPoint() {
        return point;
    }

    public void setPoint(String[] point) {
        this.point = point;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
