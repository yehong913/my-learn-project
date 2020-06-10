/*
 * 文件名：HitDoc.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：xshen
 * 修改时间：2015年8月11日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.common.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 检索命中结果
 * @author wqb
 * @version 2019年12月19日 17:46:19
 * @see HitDocVo
 * @since
 */
public class HitDocVo {

    /**知识条目id*/
    private String id;
    /**标题、描述*/
    private String title;
    /**没有样式的标题*/
    private String oldTitle;
    /**摘要*/
    private String abstruct;
    /**来源库id*/
    private String library;
    /**知识编号*/
    private String code;
    /**来源库名称*/
    private String libraryName;
    /**作者名称*/
    private String authorName;
    /**作者工号*/
    private String author;
    /**更新时间*/
    private String updateTime;
    /**所属分类id*/
    private String categories;
    /**所属分类名称*/
    private List<String> categorys = new ArrayList<String>();
    /**命中内容*/
    private String hitContent;
    /**附件名称*/
    private String attachment;
    /**附件下载地址*/
    private String attachUrl;
    /**所属模块id*/
    private String moduleId;
    /**所属模块名称*/
    private String moduleName;
    /**创建者id*/
    private String userId;
    /**创建者名称*/
    private String userName;
    /**所属部门id*/
    private String deptId;
    /**所属部门名称*/
    private String department;
    /**是否来自模板 */
    private String fromTemplate;
    /**是否被选中 */
    private String checked;
    /** 来源类型 */
    private String originType;
    /** 问题类型 */
    private String questionType;
    /** json格式文档 */
    private String docJson;
    
    public String getLibrary() {
        return library;
    }
    public void setLibrary(String library) {
        this.library = library;
    }
    public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCategories() {
        return categories;
    }
    public void setCategories(String categories) {
        this.categories = categories;
    }
    public String getModuleId() {
        return moduleId;
    }
    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }
    public String getModuleName() {
        return moduleName;
    }
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getDeptId() {
        return deptId;
    }
    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }
    public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
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
    public String getAbstruct() {
        return abstruct;
    }
    public void setAbstruct(String abstruct) {
        this.abstruct = abstruct;
    }
    public String getLibraryName() {
        return libraryName;
    }
    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }
    public String getAuthorName() {
        return authorName;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
    public List<String> getCategorys() {
        return categorys;
    }
    public void setCategorys(List<String> categorys) {
        this.categorys = categorys;
    }
    public String getHitContent() {
        return hitContent;
    }
    public void setHitContent(String hitContent) {
        this.hitContent = hitContent;
    }
    public String getAttachment() {
        return attachment;
    }
    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
    public String getAttachUrl() {
        return attachUrl;
    }
    public void setAttachUrl(String attachUrl) {
        this.attachUrl = attachUrl;
    }
    public String getOldTitle() {
        return oldTitle;
    }
    public void setOldTitle(String oldTitle) {
        this.oldTitle = oldTitle;
    }
	public String getFromTemplate() {
		return fromTemplate;
	}
	public void setFromTemplate(String fromTemplate) {
		this.fromTemplate = fromTemplate;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getOriginType() {
		return originType;
	}
	public void setOriginType(String originType) {
		this.originType = originType;
	}
	public String getQuestionType() {
		return questionType;
	}
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
    public String getDocJson() {
        return docJson;
    }
    public void setDocJson(String docJson) {
        this.docJson = docJson;
    }
}
