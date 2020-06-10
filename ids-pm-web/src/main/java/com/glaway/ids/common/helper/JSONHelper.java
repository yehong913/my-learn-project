/*
 * 文件名：JSONHelper.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：shitian
 * 修改时间：2019年6月20日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.common.helper;

public class JSONHelper
{
    private JSONHelper() {
        
    }
    
    public static String provide(String json) {
        json = json.replaceAll("\"", "'");
        return json;
    }
    
    public static String consume(String json) {
        json = json.substring(1, json.length()-1);
        json = json.replaceAll("'", "\"");
        return json;
    }
}
