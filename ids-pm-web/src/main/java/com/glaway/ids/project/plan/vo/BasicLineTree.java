/*
 * 文件名：BasicLineTree.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：xshen
 * 修改时间：2015年6月5日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.project.plan.vo;


import java.util.ArrayList;
import java.util.List;


public class BasicLineTree {

    private String leftName = null;

    private String rightName = null;

    private String basicLineName = null;

    private List<BasicLineTreeNode> roots = new ArrayList<BasicLineTreeNode>();

    private List<BasicLineColumn> coloums = new ArrayList<BasicLineColumn>();

    private List<BasicLineTreeNode> leftTree = new ArrayList<BasicLineTreeNode>();

    private List<BasicLineTreeNode> rightTree = new ArrayList<BasicLineTreeNode>();

    public String getLeftName() {
        return leftName;
    }

    public void setLeftName(String leftName) {
        this.leftName = leftName;
    }

    public String getRightName() {
        return rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    public List<BasicLineTreeNode> getRoots() {
        return roots;
    }

    public void setRoots(List<BasicLineTreeNode> roots) {
        this.roots = roots;
    }

    public List<BasicLineColumn> getColoums() {
        return coloums;
    }

    public void setColoums(List<BasicLineColumn> coloums) {
        this.coloums = coloums;
    }

    public String getBasicLineName() {
        return basicLineName;
    }

    public void setBasicLineName(String basicLineName) {
        this.basicLineName = basicLineName;
    }

    public List<BasicLineTreeNode> getLeftTree() {
        return leftTree;
    }

    public void setLeftTree(List<BasicLineTreeNode> leftTree) {
        this.leftTree = leftTree;
    }

    public List<BasicLineTreeNode> getRightTree() {
        return rightTree;
    }

    public void setRightTree(List<BasicLineTreeNode> rightTree) {
        this.rightTree = rightTree;
    }

    /**
     * 初始化对比的列
     * 
     * @return
     * @see
     */
    public static List<BasicLineColumn> initColumns() {

        List<BasicLineColumn> list = new ArrayList<BasicLineColumn>();

        BasicLineColumn column1 = new BasicLineColumn();
        column1.setField("name");
        column1.setTitle("计划名称");
        column1.setWidth(180);

        BasicLineColumn column2 = new BasicLineColumn();
        column2.setField("planLevel");
        column2.setTitle("计划等级");
        column2.setWidth(60);

        BasicLineColumn column3 = new BasicLineColumn();
        column3.setField("bizCurrent");
        column3.setTitle("状态");
        column3.setWidth(60);

        BasicLineColumn column4 = new BasicLineColumn();
        column4.setField("owner");
        column4.setTitle("负责人");
        column4.setWidth(60);

        BasicLineColumn column5 = new BasicLineColumn();
        column5.setField("planStartTime");
        column5.setTitle("开始时间");
        column5.setWidth(120);

        BasicLineColumn column6 = new BasicLineColumn();
        column6.setField("planEndTime");
        column6.setTitle("结束时间");
        column6.setWidth(120);

        BasicLineColumn column7 = new BasicLineColumn();
        column7.setField("workTime");
        column7.setTitle("工期");
        column7.setWidth(30);

        BasicLineColumn column8 = new BasicLineColumn();
        column8.setField("deliverables");
        column8.setTitle("输出");
        column8.setWidth(200);

        list.add(column1);
        list.add(column2);
        list.add(column3);
        list.add(column4);
        list.add(column5);
        list.add(column6);
        list.add(column7);
        list.add(column8);

        return list;
    }

    /**
     * 根据名称获取对比列
     * 
     * @param names
     * @return
     * @see
     */
    public static List<BasicLineColumn> setColumns(String names) {
        List<BasicLineColumn> list = new ArrayList<BasicLineColumn>();

        if (names.equals("all")) {
            list = initColumns();
            return list;
        }

        BasicLineColumn column1 = new BasicLineColumn();
        column1.setField("name");
        column1.setTitle("计划名称");
        column1.setWidth(180);
        list.add(column1);

        if (names.contains("计划等级")) {
            BasicLineColumn column2 = new BasicLineColumn();
            column2.setField("planLevel");
            column2.setTitle("计划等级");
            column2.setWidth(60);
            list.add(column2);
        }

        if (names.contains("状态")) {
            BasicLineColumn column3 = new BasicLineColumn();
            column3.setField("bizCurrent");
            column3.setTitle("状态");
            column3.setWidth(60);
            list.add(column3);

        }

        if (names.contains("负责人")) {
            BasicLineColumn column4 = new BasicLineColumn();
            column4.setField("owner");
            column4.setTitle("负责人");
            column4.setWidth(60);
            list.add(column4);
        }

        if (names.contains("开始时间")) {
            BasicLineColumn column5 = new BasicLineColumn();
            column5.setField("planStartTime");
            column5.setTitle("开始时间");
            column5.setWidth(120);
            list.add(column5);
        }

        if (names.contains("结束时间")) {
            BasicLineColumn column6 = new BasicLineColumn();
            column6.setField("planEndTime");
            column6.setTitle("结束时间");
            column6.setWidth(120);
            list.add(column6);
        }

        if (names.contains("工期")) {
            BasicLineColumn column7 = new BasicLineColumn();
            column7.setField("workTime");
            column7.setTitle("工期");
            column7.setWidth(30);
            list.add(column7);
        }

        if (names.contains("输出")) {
            BasicLineColumn column8 = new BasicLineColumn();
            column8.setField("deliverables");
            column8.setTitle("输出");
            column8.setWidth(200);
            list.add(column8);
        }

        return list;
    }
}
