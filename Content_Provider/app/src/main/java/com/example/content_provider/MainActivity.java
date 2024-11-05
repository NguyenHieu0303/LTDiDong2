package com.example.content_provider;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Telephony;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
public class MainActivity extends Activity implements View.OnClickListener {
    private static final int PERMISSION_REQUEST_CODE = 2;
    Button btnshowallcontact, btnaccesscalllog, btnshowmessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnshowallcontact = findViewById(R.id.btnshowallcontact);
        btnaccesscalllog = findViewById(R.id.btnaccesscalllog);
        btnshowmessages = findViewById(R.id.btnshowmessages);

        btnshowallcontact.setOnClickListener(this);
        btnaccesscalllog.setOnClickListener(this);
        btnshowmessages.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v == btnshowallcontact) {
            Intent intent = new Intent(this, ShowAllContactActivity.class);
            startActivity(intent);
        } else if (v == btnaccesscalllog) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
                accessTheCallLog();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALL_LOG}, 1);
            }
        }else if (v == btnshowmessages) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {
                displayMessages();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, 3);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            accessTheCallLog();
        } else if (requestCode == 2 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            displayMessages();
        } else {
            Toast.makeText(this, "Quyền bị từ chối", Toast.LENGTH_SHORT).show();
        }
    }

    public void accessTheCallLog() {
        String[] projection = {CallLog.Calls.DATE, CallLog.Calls.NUMBER, CallLog.Calls.DURATION};
        Cursor c = getContentResolver().query(CallLog.Calls.CONTENT_URI, projection, CallLog.Calls.DURATION + "<?",
                new String[]{"30"}, CallLog.Calls.DATE + " ASC");
        StringBuilder s = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        if (c != null) {
            while (c.moveToNext()) {
                long dateMillis = c.getLong(0);
                String formattedDate = dateFormat.format(new Date(dateMillis));
                String phoneNumber = c.getString(1);
                String duration = c.getString(2);

                String contactName = getContactName(phoneNumber);

                s.append("Ngày: ").append(formattedDate)
                        .append("\nSĐT: ").append(phoneNumber)
                        .append("\nTên danh bạ: ").append(contactName)
                        .append("\nThời Gian: ").append(duration).append(" Giây\n")
                        .append("--------------------------------------------------------\n");
            }
            c.close();
        }
        new AlertDialog.Builder(this)
                .setTitle("Lịch sử cuộc gọi gần đây")
                .setMessage(s.toString().isEmpty() ? "Không có cuộc gọi nào" : s.toString())
                .setPositiveButton("Đóng", null)
                .show();
    }


    private String getContactName(String phoneNumber) {
        String contactName = "Số lạ";
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        String[] projection = {ContactsContract.PhoneLookup.DISPLAY_NAME};
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
                if (nameIndex >= 0) {
                    contactName = cursor.getString(nameIndex);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log any exceptions
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return contactName;
    }
    public void displayMessages() {
        Cursor cursor = getContentResolver().query(Telephony.Sms.Inbox.CONTENT_URI, null, null, null, null);
        StringBuilder messages = new StringBuilder();

        if (cursor != null) {
            int addressIndex = cursor.getColumnIndex(Telephony.Sms.ADDRESS);
            int bodyIndex = cursor.getColumnIndex(Telephony.Sms.BODY);
            while (cursor.moveToNext()) {
                String address = cursor.getString(addressIndex);
                String body = cursor.getString(bodyIndex);
                messages.append("Từ: ").append(address).append("\nNội dung: ").append(body).append("\n");
                messages.append("--------------------------------------------------------\n");
            }
            cursor.close();
        }

        new AlertDialog.Builder(this)
                .setTitle("Tin nhắn SMS")
                .setMessage(messages.toString().isEmpty() ? "Không có tin nhắn nào" : messages.toString())
                .setPositiveButton("Đóng", null)
                .show();
    }
}