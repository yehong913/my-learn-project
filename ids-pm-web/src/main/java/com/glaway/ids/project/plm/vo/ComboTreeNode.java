package com.glaway.ids.project.plm.vo;


/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author 朱聪
 * @version 2015年3月31日
 * @see ComboTreeNode
 * @since
 */
public class ComboTreeNode {

    /**
     * treeId<br>
     */
    private String id;
    /**
     * 父节点Id<br>
     */
    private String pid;
    /**
     * 标题名<br>
     */
    private String title;
    /**
     * 显示名<br>
     */
    private String name;
    /**
     * 节点是否展开<br>
     */
    private Boolean open;

    /**
     * 懒加载 是否为父节点使用
     * */
    private Boolean isParent;

    /**
     * icon图标<br>
     */
    private String icon;

    /**
     * 关闭时图标<br>
     */
    private String iconClose;

    /**
     * 打开时图标<br>
     */
    private String iconOpen;

    /**
     * 节点路径
     */
    private String path;

    /**
     * 图片样式
     */
    private String iconSkin;

    private Object dataObject;

    public Boolean getIsParent() {
        return isParent;
    }
    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }
    public String getPid() {
        return pid;
    }
    public void setPid(String pid) {
        this.pid = pid;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Boolean getOpen() {
        return open;
    }
    public void setOpen(Boolean open) {
        this.open = open;
    }

    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getIconClose() {
        return iconClose;
    }
    public void setIconClose(String iconClose) {
        this.iconClose = iconClose;
    }
    public String getIconOpen() {
        return iconOpen;
    }
    public void setIconOpen(String iconOpen) {
        this.iconOpen = iconOpen;
    }

    public ComboTreeNode() {
    }
    public ComboTreeNode(String id, String pid, String title, String name, Boolean open) {
        super();
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.name = name;
        this.open = open;
    }

    public ComboTreeNode(String id, String pid, String title, String name, Boolean open, String icon,
                         String iconOpen, String iconClose) {
        super();
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.name = name;
        this.open = open;
        this.icon = icon;
        this.iconClose = iconClose;
        this.iconOpen = iconOpen;
    }

    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }

    public String getIconSkin() {
        return iconSkin;
    }
    public void setIconSkin(String iconSkin) {
        this.iconSkin = iconSkin;
    }

    public Object getDataObject() {
        return dataObject;
    }
    public void setDataObject(Object dataObject) {
        this.dataObject = dataObject;
    }

}