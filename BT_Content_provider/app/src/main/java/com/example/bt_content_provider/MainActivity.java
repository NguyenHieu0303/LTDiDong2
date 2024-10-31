package com.example.bt_content_provider;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements SmsAdapter.OnSmsAccountClickListener {

    private static final int SMS_PERMISSION_CODE = 100;
    private RecyclerView recyclerView;
    private SmsAdapter smsAdapter;
    private List<SmsAccount> smsAccounts = new ArrayList<>();
    private Button startChatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Sửa lại thành activity_main

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        startChatButton = findViewById(R.id.startChatButton);

        startChatButton.setOnClickListener(v -> {
            Toast.makeText(this, "Starting new chat...", Toast.LENGTH_SHORT).show();
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, SMS_PERMISSION_CODE);
        } else {
            readSmsMessages();
        }
    }

    private void readSmsMessages() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = Uri.parse("content://sms/inbox");
        Cursor cursor = contentResolver.query(uri, null, null, null, "date DESC");

        if (cursor != null && cursor.moveToFirst()) {
            Map<String, SmsAccount> smsAccountMap = new HashMap<>();

            do {
                int addressIndex = cursor.getColumnIndex("address");
                int bodyIndex = cursor.getColumnIndex("body");
                int dateIndex = cursor.getColumnIndex("date");
                if (addressIndex >= 0 && bodyIndex >= 0 && dateIndex >= 0) {
                    String address = cursor.getString(addressIndex);
                    String body = cursor.getString(bodyIndex);
                    long timestamp = cursor.getLong(dateIndex);

                    Sms sms = new Sms(address, body, timestamp, true);

                    // Update the smsAccountMap
                    smsAccountMap.putIfAbsent(address, new SmsAccount(address));
                    smsAccountMap.get(address).addMessage(sms);
                }
            } while (cursor.moveToNext());

            cursor.close();

            // Convert map values to a list
            smsAccounts = new ArrayList<>(smsAccountMap.values());

            // Set the adapter
            smsAdapter = new SmsAdapter(smsAccounts, this);
            recyclerView.setAdapter(smsAdapter);
        } else {
            Toast.makeText(this, "Không có tin nhắn để hiển thị.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSmsAccountClick(SmsAccount smsAccount) {
        // Navigate to SmsDetailActivity with the selected SmsAccount
        Intent intent = new Intent(this, SmsDetailActivity.class);
        intent.putExtra("smsAccount", smsAccount);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readSmsMessages();
            } else {
                Toast.makeText(this, "Quyền truy cập SMS bị từ chối", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
