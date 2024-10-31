package com.example.bt_content_provider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SmsAccount implements Serializable {
    private String address;
    private List<Sms> messages; // Chỉnh sửa để sử dụng kiểu Sms

    public SmsAccount(String address) {
        this.address = address;
        this.messages = new ArrayList<>(); // Khởi tạo danh sách
    }

    public String getAddress() {
        return address;
    }

    public List<Sms> getMessages() {
        return messages;
    }

    public void addMessage(Sms sms) {
        messages.add(sms);
    }

    public Sms getLatestMessage() {
        if (messages.isEmpty()) {
            return null;
        }
        return messages.get(messages.size() - 1); // Lấy tin nhắn mới nhất
    }
}
