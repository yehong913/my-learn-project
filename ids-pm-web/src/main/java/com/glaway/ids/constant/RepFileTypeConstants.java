package com.glaway.ids.constant;

/**
 * 文件typeID常量类  
 * 原因：需求变动 
 *    RepFileType 现typeID转换成typeCode 作为唯一标示  后台所有写死的ID换成 该ID对应的Code 作为RepFileType的唯一标识  改类中每一个COde对应该实例的id
 *    该实例配置控制台--->配置管理--->系统类型管理 中配置
 * @author Administrator
 *
 */
public class RepFileTypeConstants {
	/**
	 * 对应项目文件 ID=4028ef2d504608ba0150462418bf0001  对应CODE
	 */
	public static final String REP_FILE_TYPE_PRO="projectFile";
	/**
	 * 对应评审文件  ID=4028efec51a96d260151a979fc490000 对应CODE
	 */
	public static final String REP_FILE_TYPE_REV="reviewFile";
	/**
	 * 对应质量文件 ID=4028ef585a886508015a8941e7cf055a  对应CODE
	 */
	public static final String REP_FILE_TYPE_QUAL="qualityFile";
    /**
     * 对应质量体系文件 ID=4028ef585aa1579f015ad1498130220c  对应CODE
     */
	public static final String REP_FILE_TYPE_QUALSYS="qualSysFile";
	
	
	/**
	 * 对应项目文件 ID=4028ef2d504608ba0150462418bf0001  对应CODEprojectFile
	 */
	public static final String REP_FILE_TYPE_PROID="4028ef2d504608ba0150462418bf0001";
	/**
	 * 对应评审文件  ID=4028efec51a96d260151a979fc490000 对应CODE reviewFile
	 */
	public static final String REP_FILE_TYPE_REVID="4028efec51a96d260151a979fc490000";
	/**
	 * 对应质量文件 ID=4028ef585a886508015a8941e7cf055a  对应CODEqualityFile
	 */
	public static final String REP_FILE_TYPE_QUALID="4028ef585a886508015a8941e7cf055a";
    /**
     * 对应质量体系文件 ID=4028ef585aa1579f015ad1498130220c  对应CODE qualSysFile
     */
	public static final String REP_FILE_TYPE_QUALSYSID="4028ef585aa1579f015ad1498130220c";

}
