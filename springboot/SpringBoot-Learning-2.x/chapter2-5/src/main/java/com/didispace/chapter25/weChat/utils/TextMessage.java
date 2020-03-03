package com.didispace.chapter25.weChat.utils;

import lombok.Getter;
import lombok.Setter;

public class TextMessage {
    //开发者微信号

    private  String ToUserName;

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }

    //发送方帐号（一个

    private  String FromUserName;
    //消息创建时间 （整型

    private String CreateTime;
    //息类型，文本为text

    private String MsgType;
    //文本消息内容

    private String Content;
    //消息id，64位整型

    private  String MsgId;





}
