package com.guomzh.onlineq.domain;

import java.util.Date;

/**
 * @author zgm
 * @date 2018/9/9 10:03
 */
public class Message {
    private int id;
    private int fromId;
    private int toId;
    private String content;
    private int hasRead;
    private String conversationId;
    private Date createdDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getHasRead() {
        return hasRead;
    }

    public void setHasRead(int hasRead) {
        this.hasRead = hasRead;
    }

    public String getConversationId() {
        if(fromId<toId){
            return String.format("%d_%d",fromId,toId);
        } else{
            return String.format("%d_%d",toId,fromId);
        }
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
