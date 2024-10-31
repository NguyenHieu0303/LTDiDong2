package com.example.bt_content_provider;

public class Sms {
    private String address;
    private String body;
    private long timestamp;
    private boolean isRead;

    public Sms(String address, String body, long timestamp, boolean isRead) {
        this.address = address;
        this.body = body;
        this.timestamp = timestamp;
        this.isRead = isRead;
    }

    public String getAddress() {
        return address;
    }

    public String getBody() {
        return body;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public boolean isRead() {
        return isRead;
    }
}
