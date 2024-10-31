package com.example.bt_content_provider;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SmsDetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SmsDetailAdapter smsDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_detail);

        recyclerView = findViewById(R.id.recyclerView_detail);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SmsAccount smsAccount = (SmsAccount) getIntent().getSerializableExtra("smsAccount");
        if (smsAccount != null) {
            List<Sms> messages = smsAccount.getMessages();
            smsDetailAdapter = new SmsDetailAdapter(messages);
            recyclerView.setAdapter(smsDetailAdapter);
        } else {
            Toast.makeText(this, "Không có dữ liệu để hiển thị.", Toast.LENGTH_SHORT).show();
        }
    }
}
