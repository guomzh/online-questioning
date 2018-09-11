package com.guomzh.onlineq.async;

/**
 * @author zgm
 * @date 2018/9/10 10:55
 */
public enum EventType {
    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3),
    FOLLOW(4),
    UNFOLLOW(5);

    private int value;

    EventType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
