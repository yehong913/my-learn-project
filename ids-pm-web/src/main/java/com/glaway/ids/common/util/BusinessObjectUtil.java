package com.glaway.ids.common.util;

import com.glaway.foundation.fdk.dev.dto.rep.RepFileDto;

import java.util.List;
import java.util.regex.Pattern;

public class BusinessObjectUtil {

	/**
	 * Description: <br>
	 * 1、businessObject集合按照bizVersion进行排序<br>
	 *
	 * @param boList
	 * @see
	 */
	private void orderBusinessObjectByBizVersion(List<RepFileDto> boList) {
		if(boList != null && boList.size() > 0){
			//冒泡排序
			for(int i = 0; i< boList.size() ;i++){
				for( int j = i+1; j < boList.size(); j++){
					RepFileDto bo  = boList.get(i);
					RepFileDto bo2 = boList.get(j);
					if(bo != null && bo2 != null){
						//如果bo的版本小于bo2的版本，那么替换
						if( !compareBizVersion(bo.getBizVersion(),bo2.getBizVersion())){
							boList.set(i, bo2);
							boList.set(j, bo);
						}
					}
				}
			}
		}
	}


	/**
	 * Description: <br>
	 * 1、比较两个bizVersion的大小<br>
	 *
	 * @param version1
	 * @param version2
	 * @return version1 > version2 返回true， version1 < version2返回false，如果version1和version2相等，返回false
	 * @see
	 */
	private boolean compareBizVersion(String version1, String version2) {
		//如果version1为null，那么一定返回false
		if(version1 != null ){
			if(version2 != null){
				String[] array1 = version1.split("\\.");
				String[] array2 = version2.split("\\.");
				//比大版本
				String ver1 = array1[0];
				String ver2 = array2[0];
				int index = _compareString(ver1, ver2);
				if(index > 0){
					return true;
				}
				else if(index < 0){
					return false;
				}
				else{
					//一样的话再比小版本
					if(array1.length < 2){
						return false;
					}
					if(array2.length < 2){
						return true;
					}
					ver1 = array1[1];
					ver2 = array2[1];
					index = _compareString(ver1, ver2);
					//小版本只有version1显式的大于version2才返回true
					if(index > 0){
						return true;
					}
					else {
						return false;
					}
				}
			}
			//如果version1不为null，且version2为null，那么一定返回true
			else{
				return true;
			}
		}
		return false;
	}


	/**
	 * Description: <br>
	 * 1、…<br>
	 * 2、…<br>
	 * Implement: <br>
	 * 1、…<br>
	 * 2、…<br>
	 *
	 * @param ver1
	 * @param ver2
	 * @return ver1 > ver2 返回1，ver1 < ver2返回-1， ver1 == ver2返回0
	 * @see
	 */
	private int _compareString(String ver1, String ver2) {
		//两者都为数字，则转成数字比较
		Pattern p = Pattern.compile("[0-9]{1,}");
		if(p.matcher(ver1).matches() &&  p.matcher(ver2).matches()){
			int i = Integer.valueOf(ver1);
			int j = Integer.valueOf(ver2);
			if(i > j){
				return 1;
			}
			else if (i == j){
				return 0;
			}
			else{
				return -1;
			}
		}
		//两者都不为数字，则转成char数组比较
		else if (!p.matcher(ver1).matches() &&  !p.matcher(ver2).matches()){
			if(ver1.equals(ver2)){
				return 0;
			}
			//""字符串比较小
			if(ver1.length() > 0){
				if(ver2.length() > 0){
					//如果大于则返回>0，如果小于则返回<0，如果等于则判断下一位
					for(int i = 0;i<ver1.length();i++){
						if(ver1.charAt(i) == ver2.charAt(i)){
							continue;
						}
						if(ver1.charAt(i) > ver2.charAt(i)){
							return 1;
						}
						else{
							return -1;
						}
					}
					//完全相等返回0
					return 0;
				}
				else{
					return 1;
				}
			}
			else{
				return -1;
			}
		}
		//如果一个为数字，一个为字符串，那么字符串大于数字
		else{
			//ver1是否是数字
			if(!p.matcher(ver1).matches()){
				return 1;
			}
			else{
				return -1;
			}
		}
	}




}
